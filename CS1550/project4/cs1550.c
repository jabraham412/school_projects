/*
	John Abraham
	CS1550 - Operating Systems
	Project4- FUSE File System

	FUSE: Filesystem in Userspace
	Copyright (C) 2001-2007  Miklos Szeredi <miklos@szeredi.hu>

	This program can be distributed under the terms of the GNU GPL.
	See the file COPYING.

	The root directory, subdirectories, and files are stored on disk
	under a file called .disk
	to format the disk file enter the command:
	dd bs=1K count=5K if=/dev/zero of=.disk
	this will create a 5MB disk image initialized to contain all zeros, named .disk
	replacing the old one.
*/

#define	FUSE_USE_VERSION 26

#include <fuse.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <fcntl.h>

/*
 * size of the free space tracking bitmap in by-tes.
 * each byte represents a .disk block number (starting from
 * the root) and contains information about whether that block
 * is allocated or free as an unsigned char (root is always
 * marked as allocated). the bigger the buffer size the more
 * space we can track and utilize. it is set to 7812 bytes for
 * now which can keep track of 7812 512-byte blocks. which is 
 * equivalent to approximately 4 MB of disk space. it is
 * stored at the end of the .disk file starting from byte
 * (.disk size - BITMAP_BUFFER_SIZE) and ending at the last byte
 * of the .disk file.
 */
#define BITMAP_BUFFER_SIZE 7812 //use a bitmap instead of a byte map. much more efficient. how to do in C ?

//size of a disk block
#define	BLOCK_SIZE 512

//we'll use 8.3 filenames
#define	MAX_FILENAME 8
#define	MAX_EXTENSION 3

//How many files can there be in one directory?
#define MAX_FILES_IN_DIR (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + (MAX_EXTENSION + 1) + sizeof(size_t) + sizeof(long))

//The attribute packed means to not align these things
struct cs1550_directory_entry
{
	int nFiles;	//How many files are in this directory.
				//Needs to be less than MAX_FILES_IN_DIR

	struct cs1550_file_directory
	{
		char fname[MAX_FILENAME + 1];	//filename (plus space for nul)
		char fext[MAX_EXTENSION + 1];	//extension (plus space for nul)
		size_t fsize;					//file size
		long nStartBlock;				//where the first block is on disk
	} __attribute__((packed)) files[MAX_FILES_IN_DIR];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.  
	char padding[BLOCK_SIZE - MAX_FILES_IN_DIR * sizeof(struct cs1550_file_directory) - sizeof(int)];
} ;

typedef struct cs1550_root_directory cs1550_root_directory;

#define MAX_DIRS_IN_ROOT (BLOCK_SIZE - sizeof(int)) / ((MAX_FILENAME + 1) + sizeof(long))

struct cs1550_root_directory
{
	int nDirectories;	//How many subdirectories are in the root
						//Needs to be less than MAX_DIRS_IN_ROOT
	struct cs1550_directory
	{
		char dname[MAX_FILENAME + 1];	//directory name (plus space for nul)
		long nStartBlock;				//where the directory block is on disk
	} __attribute__((packed)) directories[MAX_DIRS_IN_ROOT];	//There is an array of these

	//This is some space to get this to be exactly the size of the disk block.
	//Don't use it for anything.  
	char padding[BLOCK_SIZE - MAX_DIRS_IN_ROOT * sizeof(struct cs1550_directory) - sizeof(int)];
} ;

typedef struct cs1550_directory_entry cs1550_directory_entry;

//How much data can one block hold?
#define	MAX_DATA_IN_BLOCK (BLOCK_SIZE - sizeof(long))

struct cs1550_disk_block
{
	//The next disk block, if needed. This is the next pointer in the linked 
	//allocation list
	long nNextBlock;

	//And all the rest of the space in the block can be used for actual data
	//storage.
	char data[MAX_DATA_IN_BLOCK];
};

typedef struct cs1550_disk_block cs1550_disk_block;

// Function prototypes for helper functions
void parse_path_str(const char *path, char *directory, char *filename, char *extension);
static int get_path_type(const char *path, char *directory, char *filename, char *extension);
static void get_root(cs1550_root_directory *fill);
static void update_root_on_disk(cs1550_root_directory *new_root);
static void get_directory(cs1550_directory_entry *fill, char *directory);
static int directory_exists(char *directory);
static void create_empty_directory(char *directory_name);
static int file_exists(char *directory, char *filename, char *extension, int path_type);
static int get_next_free_block_number(void);
void update_directory_on_disk(cs1550_directory_entry *new_dir, char *directory);
static void update_bitmap(int index, const char *action);
void write_block_to_disk(cs1550_disk_block *file_block, long seek);


/*
 * Called whenever the system wants to know the file attributes, including
 * simply whether the file exists or not. 
 *
 * UNIX Equivalent: man -s 2 stat will show the fields of a stat structure
 *
 * Return values:
 * 0 on success, with a correctly set structure
 * -ENOENT if the file is not found
 *
 * use the stat command to test
 */
static int cs1550_getattr(const char *path, struct stat *stbuf)
{
	int ret = 0;

	memset(stbuf, 0, sizeof(struct stat));

	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];

	parse_path_str(path, directory, filename, extension);

	int path_type = get_path_type(path, directory, filename, extension);

	if (path_type == 0) {
		/*
		 * Path is the root dir
		 */
		stbuf->st_mode = S_IFDIR | 0755;
		stbuf->st_nlink = 2;
	} else if (path_type == 1) {
		/*
		 * Path is subdirectory
		 */
		if (directory_exists(directory) == 1) {
			/*
			 * Subdirectory exists
			 */
			 stbuf->st_mode = S_IFDIR | 0755;
			 stbuf->st_nlink = 2;
		} else {
			/*
			 * else return that directory doesn't exist; thus, path doesn't exist
			 */
			printf("Directory doesn't exist\n");
			ret = -ENOENT;
		}

	} else if (path_type == 2 || path_type == 3) {
		/*
		 * Path is a file
		 * Check if file exists
		 */
		int file_size = file_exists(directory, filename, extension, path_type);
		if (file_size != -1) {
			stbuf->st_mode = S_IFREG | 0666; 
			stbuf->st_nlink = 1; //file links
			stbuf->st_size = (size_t)file_size;
		} else {
			printf("File doesn't exist\n");
			ret = -ENOENT;
		}
	} else {
		printf("Invalid path\n");
		ret = -ENOENT;
	}

	return ret;
}

/*
 * Creates a directory. We can ignore mode since we're not dealing with
 * permissions, as long as getattr returns appropriate ones for us.
 *
 * This function adds the new directory to the root level, and  updates
 * the .disk file appropriately.
 *
 * UNIX Equivalent: man -s 2 mkdir
 *
 * Return values:
 * 0 on success
 * -ENAMETOOLONG if the name is beyond 8 chars
 * -EPERM if the directory is not under the root dir only
 * -EEXIST if the directory already exists
 *
 * test with mkdir command
 */
static int cs1550_mkdir(const char *path, mode_t mode)
{
	(void) mode;
	int ret = 0;

	/*
	 * Start by parsing the path name.
	 */
	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];

	parse_path_str(path, directory, filename, extension);
	int path_type = get_path_type(path, directory, filename, extension);	
	int directory_len = strlen(directory);

	if (directory_len >= MAX_FILENAME) {
		/*
		 * Directory name is too long (beyond 8 characters)
		 */
		printf("Directory name is too long (beyond 8 characters)\n");
		ret = -ENAMETOOLONG;
	} else {
		 if (path_type != 1) {
			/*
			 * Directory is not under the root dir only
			 */
			printf("Directory is not under the root dir only\n");
			ret = -EPERM;
		} else {
			if (directory_exists(directory) == 1) {
				/*
				 * Directory already exists
				 */
				printf("Directory already exists\n");
				ret = -EEXIST;
			} else {
				/*
				 * Add the new directory to the root level, and update the .disk file appropriately
				 * with a new updated root entry and a new empty directory
				 */
				cs1550_root_directory root;
				get_root(&root);
				if (root.nDirectories >= MAX_DIRS_IN_ROOT) {
					printf("Maximum directories in root. Directory not created.\n");
				} else {
					// write a new empty directory to disk.
					create_empty_directory(directory);
				}
			}
		}
	}
	return ret;
}

/* 
 * Called whenever the contents of a directory are desired. Could be from an 'ls'
 * or could even be when a user hits TAB to do autocompletion
 *
 * UNIX Equivalent: man -s 2 readdir (However it’s not exactly equivalent)
 *
 * Return values:
 * 0 on success
 * -ENOENT if the directory is not valid or found
 *
 * test with ls -al command
 */
static int cs1550_readdir(const char *path, void *buf, fuse_fill_dir_t filler,
			 off_t offset, struct fuse_file_info *fi)
{
	//Since we're building with -Wall (all warnings reported) we need
	//to "use" every parameter, so let's just cast them to void to
	//satisfy the compiler
	(void) offset;
	(void) fi;
	int ret = 0;

	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];
	parse_path_str(path, directory, filename, extension);
	int path_type = get_path_type(path, directory, filename, extension);

	if (path_type == 0) {
		filler(buf, ".", NULL, 0);
		filler(buf, "..", NULL, 0);
		cs1550_root_directory root;
		get_root(&root);
		int i;
		for(i = 0; i < root.nDirectories; i++) {
			filler(buf, root.directories[i].dname, NULL, 0);
		}
	} else if (path_type == 1) {
		int dir_exists = directory_exists(directory);
		if ( dir_exists == 1) {
			filler(buf, ".", NULL,0);
			filler(buf, "..", NULL, 0);
			cs1550_directory_entry current_diretory;
			get_directory(&current_diretory, directory);
			int i;
			for (i = 0; i < current_diretory.nFiles; i++) {
				if ((strcmp(current_diretory.files[i].fext, "\0") == 0)) {
					//print regular files
					filler(buf, current_diretory.files[i].fname, NULL, 0);
				} else {
					//print files with extensions. malloc extra space for '\0' and '.'
					char *filename_with_ext = (char *) malloc(2 + MAX_FILENAME + MAX_EXTENSION);
					strcpy(filename_with_ext, current_diretory.files[i].fname);
					strcat(filename_with_ext, ".");
					strcat(filename_with_ext, current_diretory.files[i].fext);
					filler(buf, filename_with_ext, NULL, 0);
				}
			}
		} else {
			ret = -ENOENT;			
		}
	} else {
		ret = -ENOENT;
	}
	return ret;
}

/*
 * Removes a directory. This function should not be modified.
 */
static int cs1550_rmdir(const char *path)
{
	(void) path;
    return 0;
}

/* 
 * Does the actual creation of a file. Mode and dev can be ignored.
 *
 * 0 on success
 * -ENAMETOOLONG if the name is beyond 8.3 chars
 * -EPERM if the file is trying to be created in the root dir
 * -EEXIST if the file already exists
 *
 * test with touch command
 */
static int cs1550_mknod(const char *path, mode_t mode, dev_t dev)
{
	(void) mode; (void) dev;
	int ret = 0;

	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];
	parse_path_str(path, directory, filename, extension);
	int path_type = get_path_type(path, directory, filename, extension);
	int file_size = file_exists(directory, filename, extension, path_type);

	if (path_type < 2) {
		// the file is trying to be created in the root dir
		printf("---Wrong directory to write a file to.\n");
		ret = -EPERM;
	} else {
		if (file_size != -1) {
			// file already exists
			printf("---File already exists.\n");
			ret = -EEXIST;
		} else {
			if (strlen(filename) > MAX_FILENAME || strlen(extension) > MAX_EXTENSION) {
				printf("---File name is too long.\n");
				ret = -ENAMETOOLONG;
			} else {
				// file doesn't exist. add it.
				// traverse from the root block, locate the subdirectory in which the new file will reside
				cs1550_root_directory root;
				get_root(&root);
				int i;
				for(i = 0; i < root.nDirectories; i++) {
					if (strcmp(root.directories[i].dname, directory) == 0) {
						// get a new starting block for this file according to bitmap
						int block_number = get_next_free_block_number();
						long file_nStartBlock = (long)(BLOCK_SIZE * block_number);
						// allocate the file by updating the bitmap
						const char *action = "allocate";
						update_bitmap(block_number, action);
						// update parent dir
						cs1550_directory_entry parent_dir;
						get_directory(&parent_dir, directory);
						strcpy(parent_dir.files[parent_dir.nFiles].fname, filename);
						strcpy(parent_dir.files[parent_dir.nFiles].fext, extension);
						parent_dir.files[parent_dir.nFiles].fsize = 0;
						parent_dir.files[parent_dir.nFiles].nStartBlock = file_nStartBlock;
						parent_dir.nFiles = parent_dir.nFiles + 1;
						// Write the updated subdirectory (with the new file entry) to .disk
						int parent_dir_nStartBlock = root.directories[i].nStartBlock;
						FILE *file_ptr = fopen(".disk", "rb+");
						if (file_ptr != NULL) {
							fseek(file_ptr, 0, SEEK_END);
							int disk_size = ftell(file_ptr);
							rewind(file_ptr);
							char *disk_buffer = (char *)malloc(disk_size);
							fread(disk_buffer, disk_size, 1, file_ptr);
							rewind(file_ptr);
							// write new updated parent directory to buffer
							memmove(disk_buffer+parent_dir_nStartBlock, &parent_dir, BLOCK_SIZE);
							// write updated disk_buffer to .disk
							fwrite(disk_buffer, disk_size, 1, file_ptr);
							fclose(file_ptr);
							free(disk_buffer);
						}
					}
				}
			}
		}
	}
	return ret;
}

/* 
 * Write size bytes from buf into file starting from offset
 *
 * Return values:
 * size on success
 * -EFBIG if the offset is beyond the file size (but handle appends)
 *
 * test with the echo command
 */
static int cs1550_write(const char *path, const char *buf, size_t size, 
			  off_t offset, struct fuse_file_info *fi)
{
	int ret = 0;
	(void) fi;

	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];
	parse_path_str(path, directory, filename, extension);
	int path_type = get_path_type(path, directory, filename, extension);
	int file_size = file_exists(directory, filename, extension, path_type);

	//check to make sure path exists and check that size is > 0
	if (directory_exists(directory) == 1 && path_type >= 2 && file_size != -1 && size > 0) {
		// get the parent directory of the file
		cs1550_directory_entry parent_dir;
		get_directory(&parent_dir, directory);
		int i;
		// find the file in the parent directory
		for (i = 0; i < parent_dir.nFiles; i++) {
			if ((path_type==2 && strcmp(parent_dir.files[i].fname, filename)==0) || (path_type==3 && strcmp(parent_dir.files[i].fname, filename) == 0 && strcmp(parent_dir.files[i].fext, extension) == 0)) {
				//check that offset is <= to the file size
				if (offset > parent_dir.files[i].fsize) {
					ret = -EFBIG;
				} else {
					//Locate the starting block of the file
					long file_start_block = parent_dir.files[i].nStartBlock;
					//find the block number the offset is located in
					int block_num_w_offset = offset / BLOCK_SIZE;
					//locate the start of the block that contains the offset (store in block_start)
					int j;
					long seek = file_start_block;
					long block_start = 0;
					FILE *file_ptr = fopen(".disk", "rb+");
					for (j = 0; j <= block_num_w_offset; j++) {
						block_start = seek;
						fseek(file_ptr, seek, SEEK_SET);
						cs1550_disk_block file_block;
						fread(&file_block, BLOCK_SIZE, 1, file_ptr);
						seek = file_block.nNextBlock;
					}
					rewind(file_ptr);
					//Locate the first byte to be modified relative to the BLOCK (not file) using: the given "global" offset
					//and the number of the block that contains the offset
					int offset_from_file_block = (int)offset - (block_num_w_offset * BLOCK_SIZE);
					//start overwriting from the offset that was just found relative to the file block
					int buf_char;
					int count = offset_from_file_block;
					seek = block_start;
					fseek(file_ptr, seek, SEEK_SET);
					cs1550_disk_block curr_file_block;
					fread(&curr_file_block, BLOCK_SIZE, 1, file_ptr);
					for (buf_char = 0; buf_char < strlen(buf); buf_char++) {
						//keep writing until the end of block is reached
						if (count < MAX_DATA_IN_BLOCK) {
							curr_file_block.data[count] = (char)buf[buf_char];
							count++;
						} else {
							count = 0; //reset the counter
							//move on to the next block
							if (curr_file_block.nNextBlock != 0) {
								//write the block up to this point in the buffer to disk
								write_block_to_disk(&curr_file_block, seek);
								//there exists an already allocated block past this current block that we can continue writing to
								seek = curr_file_block.nNextBlock;
								fseek(file_ptr, seek, SEEK_SET);
								fread(&curr_file_block, BLOCK_SIZE, 1, file_ptr);
							} else {
								//this was the last block in the file 
								//update the block that was just written with the new next block pointer
								//allocate a new block and continue
								long curr_seek = seek;
								int next_free_index_from_bitmap = get_next_free_block_number();
								seek = next_free_index_from_bitmap * BLOCK_SIZE;
								//write the block up to this point in the buffer to disk
								curr_file_block.nNextBlock = seek;
								write_block_to_disk(&curr_file_block, curr_seek);
								fseek(file_ptr, seek, SEEK_SET);
								fread(&curr_file_block, BLOCK_SIZE, 1, file_ptr);
								//update bit map to indicate new blocks have been allocated
								const char *action = "allocate";
								update_bitmap(next_free_index_from_bitmap, action);
							}
						}
						// if the end of block is not reached and buffer is done we still want to write this block to disk
						if (buf_char == strlen(buf)-1) {
							write_block_to_disk(&curr_file_block, seek);
							count = 0;//reset the counter
						}
					}
					fclose(file_ptr);
					//set size (should be same as input) and return, or error
					//in case an offset other than 0 is given calculate the change in size and return an updated size
					//change in size = [old - (old-offset)] + [new - (old-offset)]
					int old = parent_dir.files[i].fsize;
					int new_full_size = (old - (old - offset)) + (size - (old - offset));
					parent_dir.files[i].fsize = new_full_size;
					//update file size in parent directory on disk
					update_directory_on_disk(&parent_dir, directory);
					ret = size;
				}
			}
		}
	}
	return ret;
}

/* 
 * Read size bytes from file into buf starting from offset
 *
 * test with the cat command
 */
static int cs1550_read(const char *path, char *buf, size_t size, off_t offset,
			  struct fuse_file_info *fi)
{
	int ret = 0;
	(void) fi;

	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];
	parse_path_str(path, directory, filename, extension);
	int path_type = get_path_type(path, directory, filename, extension);
	int file_size = file_exists(directory, filename, extension, path_type);

	if (path_type < 2) {
		ret = -EISDIR; //path is a directory
	} else {
		if (directory_exists(directory) == 1 && file_size != -1 && size > 0) {
			// get the parent directory of the file
			cs1550_directory_entry parent_dir;
			get_directory(&parent_dir, directory);
			int i;
			// find the file in the parent directory
			for (i = 0; i < parent_dir.nFiles; i++) {
				if ((path_type==2 && strcmp(parent_dir.files[i].fname, filename)==0) || (path_type==3 && strcmp(parent_dir.files[i].fname, filename) == 0 && strcmp(parent_dir.files[i].fext, extension) == 0)) {
					//check that offset is <= to the file size
					if (offset <= parent_dir.files[i].fsize) {
						//Locate the starting block of the file
						long file_start_block = parent_dir.files[i].nStartBlock;
						//find the block number the offset is located in
						int block_num_w_offset = offset / BLOCK_SIZE;
						//locate the start of the block that contains the offset (store in block_start)
						int j;
						long seek = file_start_block;
						long block_start = 0;
						FILE *file_ptr = fopen(".disk", "rb+");
						for (j = 0; j <= block_num_w_offset; j++) {
							block_start = seek;
							fseek(file_ptr, seek, SEEK_SET);
							cs1550_disk_block file_block;
							fread(&file_block, BLOCK_SIZE, 1, file_ptr);
							seek = file_block.nNextBlock;
						}
						rewind(file_ptr);
						//Locate the first byte to be modified relative to the BLOCK (not file) using: the given "global" offset
						//and the number of the block that contains the offset
						int offset_from_file_block = (int)offset - (block_num_w_offset * BLOCK_SIZE);
						//start reading from the offset that was just found relative to the file block
						int count = offset_from_file_block;
						int buf_char = 0;
						seek = block_start;
						while(seek != 0) {
							fseek(file_ptr, seek, SEEK_SET);
							cs1550_disk_block curr_file_block;
							fread(&curr_file_block, BLOCK_SIZE, 1, file_ptr);
							//keep reading  until the end of block is reached
							if (count < MAX_DATA_IN_BLOCK) {
								buf[buf_char] = (char)curr_file_block.data[count];
								count++;
								buf_char++;
							} else {
								seek = curr_file_block.nNextBlock;
								count = 0;
							}
						}
						fclose(file_ptr);
						ret = size;
					}
				}
			}
		}
	}
	return ret;
}

/*
 * Deletes a file
 *
 * test with the rm command
 */
static int cs1550_unlink(const char *path)
{
	int ret = 0;

	char directory[MAX_FILENAME + 1];
	char filename[MAX_FILENAME + 1];
	char extension[MAX_EXTENSION + 1];
	parse_path_str(path, directory, filename, extension);
	int path_type = get_path_type(path, directory, filename, extension);
	int file_size = file_exists(directory, filename, extension, path_type);

	if (path_type < 2) {
		ret = -EISDIR; //path is a directory
	} else {
		if (file_size == -1) {
			ret = -ENOENT; //file is not found
		} else {
			//file exists
			//get the parent directory of the file
			cs1550_directory_entry parent_dir;
			get_directory(&parent_dir, directory);
			int i;
			// find the file in the parent directory
			for (i = 0; i < parent_dir.nFiles; i++) {
				if ((path_type==2 && strcmp(parent_dir.files[i].fname, filename)==0) || (path_type==3 && strcmp(parent_dir.files[i].fname, filename) == 0 && strcmp(parent_dir.files[i].fext, extension) == 0)) {
					//Locate the starting block of the file
					long file_start_block = parent_dir.files[i].nStartBlock;
					long seek = file_start_block;
					FILE *file_ptr = fopen(".disk", "rb");
					while(seek != 0) {
						fseek(file_ptr, seek, SEEK_SET);
						cs1550_disk_block curr_file_block;
						fread(&curr_file_block, BLOCK_SIZE, 1, file_ptr);
						//remove current block from disk by replacing it with an empty one
						cs1550_disk_block empty_block;
						memset(&empty_block, 0, BLOCK_SIZE);
						write_block_to_disk(&empty_block, seek);
						//update bitmap
						const char *action = "free";
						int bitmap_index = seek / BLOCK_SIZE;
						update_bitmap(bitmap_index, action);
						//if there's a next block set seek to it
						if (curr_file_block.nNextBlock != 0) {
							seek = curr_file_block.nNextBlock;
						} else {
							seek = 0;
						}
					}
					fclose(file_ptr);
					//update parent directory entry
					int j;
					for (j = 0; j < parent_dir.nFiles; j++) {
						if (j >= i && j != parent_dir.nFiles-1) {
							//shift cells back by one at index we want to remove
							strcpy(parent_dir.files[j].fname, parent_dir.files[j+1].fname);
							strcpy(parent_dir.files[j].fext, parent_dir.files[j+1].fext);
							parent_dir.files[j].fsize = parent_dir.files[j+1].fsize;
							parent_dir.files[j].nStartBlock = parent_dir.files[j+1].nStartBlock;
						}
					}
//destory parent_dir.nFiles file reference in root ?
					// decrement total number of files
					parent_dir.nFiles = parent_dir.nFiles - 1;
					update_directory_on_disk(&parent_dir, directory);
				}
			}
		}
	}
    return ret;
}

/******************************************************************************
 *
 *  DO NOT MODIFY ANYTHING BELOW THIS LINE
 *
 *****************************************************************************/

/*
 * truncate is called when a new file is created (with a 0 size) or when an
 * existing file is made shorter. We're not handling deleting files or 
 * truncating existing ones, so all we need to do here is to initialize
 * the appropriate directory entry.
 *
 */
static int cs1550_truncate(const char *path, off_t size)
{
	(void) path;
	(void) size;

    return 0;
}


/* 
 * Called when we open a file
 *
 */
static int cs1550_open(const char *path, struct fuse_file_info *fi)
{
	(void) path;
	(void) fi;
    /*
        //if we can't find the desired file, return an error
        return -ENOENT;
    */

    //It's not really necessary for this project to anything in open

    /* We're not going to worry about permissions for this project, but 
	   if we were and we don't have them to the file we should return an error

        return -EACCES;
    */

    return 0; //success!
}

/*
 * Called when close is called on a file descriptor, but because it might
 * have been dup'ed, this isn't a guarantee we won't ever need the file 
 * again. For us, return success simply to avoid the unimplemented error
 * in the debug log.
 */
static int cs1550_flush (const char *path , struct fuse_file_info *fi)
{
	(void) path;
	(void) fi;

	return 0; //success!
}

/**************************************************************************************************************************************************/
/*************************************************Helper functions: used by the previous functions*************************************************/
/**************************************************************************************************************************************************/
/*
 * Parse a given path name into directory, filename, and extension.
 * From a user interface perspective, our file system will be a two level
 * directory system, with the following restrictions/simplifications:
 * 1. The root directory “/” will only contain other subdirectories, and no regular files
 * 2. The subdirectories will only contain regular files, and no subdirectories of their own
 */
void parse_path_str(const char *path, char *directory, char *filename, char *extension) {
	/*
	 * Initialize strings
	 */
	directory[0] = '\0';
	filename[0] = '\0';
	extension[0] = '\0';
	/*
	 * sscanf or strtok() can both be used to parse the path name
	 * I chose sscanf to do this
	 */
	sscanf(path, "/%[^/]/%[^.].%s", directory, filename, extension);
	/*
	 * Add a null terminator to the endo of the  parsed strings to determine the end of
	 * the file/directory.
	 */
	directory[MAX_FILENAME] = '\0';
	filename[MAX_FILENAME] = '\0';
	extension[MAX_EXTENSION] = '\0';
}

/*
 * Check the type of path name
 * if root, return 0.
 * if subdirectory, return 1
 * if regualr file, return 2
 * if file with extension, return 3
 * if path is null it doesn't exist, return -1
 */
static int get_path_type(const char *path, char *directory, char *filename, char *extension) {
	int ret = -1;
	if (strcmp(path, "/") == 0) { ret = 0; }
	if (strcmp(directory, "\0") != 0)      { ret = 1; }
	if (strcmp(filename, "\0") != 0)       { ret = 2; }
	if (strcmp(extension, "\0") != 0)      { ret = 3; }
	return ret;
}

/*
 * Get root directory struct from .disk
 */
static void get_root(cs1550_root_directory *fill) {
	FILE *file_ptr = fopen(".disk", "rb");
	if (file_ptr != NULL) {
		// The root entry is under block 0. No need to seek anywhere futher.
		fread(fill, sizeof(cs1550_root_directory), 1, file_ptr);
		fclose(file_ptr);
	}
}

/*
 * Update/replace the old root on disk a new root
 */
static void update_root_on_disk(cs1550_root_directory *new_root) {
	FILE *file_ptr = fopen(".disk", "rb+");
	if (file_ptr != NULL) {
		fseek(file_ptr, 0, SEEK_END);
		int disk_size = ftell(file_ptr);
		rewind(file_ptr);
		char *disk_buffer = (char *)malloc(disk_size);
		fread(disk_buffer, disk_size, 1, file_ptr);
		rewind(file_ptr);
		memmove(disk_buffer, new_root, BLOCK_SIZE);
		fwrite(disk_buffer, disk_size, 1, file_ptr);
		fclose(file_ptr);
		free(disk_buffer);
	}
}

/*
 * Get directory struct from .disk via the root
 */
static void get_directory(cs1550_directory_entry *fill, char *directory) {
	// get the start block number of the directory we're interested in
	long dir_nStartBlock = 0;
	cs1550_root_directory root;
	get_root(&root);
	int i;
	for (i = 0; i < root.nDirectories; i++) {
		if (strcmp(directory, root.directories[i].dname) == 0) {
			dir_nStartBlock = root.directories[i].nStartBlock;
		}
	}

	FILE *file_ptr = fopen(".disk", "rb");
	if (file_ptr != NULL) {
		// set the fill directory entry passed in to the directory we found
		fseek(file_ptr, dir_nStartBlock, SEEK_SET);
		fread(fill, BLOCK_SIZE, 1, file_ptr);
		fclose(file_ptr);
	}
}

/*
 * update a directory's entry in root
 */
void update_directory_on_disk(cs1550_directory_entry *new_dir, char *directory) {
	cs1550_root_directory root;
	get_root(&root);
	int i;
	for (i = 0; i < root.nDirectories; i++) {
		if (strcmp(directory, root.directories[i].dname) == 0) {
			// get start block of this directory on .disk
			long dir_nStartBlock = root.directories[i].nStartBlock;
			//replace it on disk wit the new updated directory
			FILE *file_ptr = fopen(".disk", "rb+");
			if (file_ptr != NULL) {
				fseek(file_ptr, 0, SEEK_END);
				int disk_size = ftell(file_ptr);
				rewind(file_ptr);
				char *disk_buffer = (char *)malloc(disk_size);
				fread(disk_buffer, disk_size, 1, file_ptr);
				rewind(file_ptr);
				memmove(disk_buffer+(int)dir_nStartBlock, new_dir, BLOCK_SIZE);
				fwrite(disk_buffer, disk_size, 1, file_ptr);
				free(disk_buffer);
				fclose(file_ptr);
			}
			break;
		}
	}
}

/*
 * Check if directory exists.
 * If directory exists, returns 1.
 * If directory doesn't exist, returns 0.
 */
static int directory_exists(char *directory) {
	int ret = 0;
	cs1550_root_directory root;
	get_root(&root);
	int i;
	for (i = 0; i < root.nDirectories; i++) {
		if (strcmp(directory, root.directories[i].dname) == 0) {
			ret = 1;
		}
	}
	return ret;
}

/*
 * Check if file exists
 * If file exists, returns fize size (integer).
 * If file doesn't exist returns -1.
 */
static int file_exists(char *directory, char *filename, char *extension, int path_type) {
	int ret = -1;

	if (directory_exists(directory) == 0) {
		ret = -1;
	} else {
		// parent directory exists. now get the struct for the parent directory
		cs1550_directory_entry parent_dir;
		get_directory(&parent_dir, directory);

		// search files in the parent directory to see if the requested directory exists
		int i;
		for (i = 0; i < parent_dir.nFiles; i++) {
			if (path_type == 2 && strcmp(filename, parent_dir.files[i].fname) == 0) {
				ret = (int)parent_dir.files[i].fsize;
			} else if (path_type == 3 && strcmp(filename, parent_dir.files[i].fname) == 0 && strcmp(extension, parent_dir.files[i].fext) == 0 ) {
					ret = (int)parent_dir.files[i].fsize;
			}
		}
	}
	return ret;
}

/*
 * Write the given directory to the .disk file and update the root entry on disk.
 *
 * Returns the start block number on .disk of that newly created directory on success.
 * Returns -1 on failure.
 */
static void create_empty_directory(char *directory_name) {
	// first get the block number of the next free block (from the bitmap).
	int block_number = get_next_free_block_number();
	if (block_number != -1) {
			// Update root entry, update free block tracking bitmap
			// update root directory information on .disk
			cs1550_root_directory root;
			get_root(&root);
			strcpy(root.directories[root.nDirectories].dname, directory_name);
			root.directories[root.nDirectories].nStartBlock = (long)(BLOCK_SIZE * block_number);
			root.nDirectories = root.nDirectories + 1;
			update_root_on_disk(&root);
			// update the bitmap that keeps track of free blocks
			const char *action = "allocate";
			update_bitmap(block_number, action);
	} else {
		printf("No free blocks available.\n");
	}
}

/*
 * Search the bitmap to get the block number of the next free block
 *
 * Returns the block number of free block on success.
 * Returns -1 on failure.
 */
static int get_next_free_block_number(void) {
	int ret = -1;
	FILE *file_ptr = fopen(".disk", "rb");
	int seek_offset = 0 - BITMAP_BUFFER_SIZE;
	fseek(file_ptr, seek_offset, SEEK_END);
	int i;
	for (i = 0; i < BITMAP_BUFFER_SIZE; i++) {
		unsigned char block_byte = fgetc(file_ptr);
		if (i != 0 && block_byte == 0) {
			ret = i;
			break;
		}
		seek_offset = seek_offset + 1;
		fseek(file_ptr, seek_offset, SEEK_END);
	}
	fclose(file_ptr);
	return ret;
}

/*
 * Given a block bumber, and an action update
 * the bitmap at that index/block number by
 * just flipping the bit value at that index
 * based on the given action.
 */ 
static void update_bitmap(int index, const char *action) {
	FILE *file_ptr = fopen(".disk", "rb+");
    fseek(file_ptr, 0, SEEK_END);
    int disk_size = ftell(file_ptr);
    int bitmap_start_offset = disk_size - BITMAP_BUFFER_SIZE;
    rewind(file_ptr);
    char *buffer = (char *)malloc(disk_size);
    fread(buffer, disk_size, 1, file_ptr);
    rewind(file_ptr);
    if (strcmp(action, "allocate") == 0) { buffer[bitmap_start_offset + index] = 1; }
    if (strcmp(action, "free") == 0) { buffer[bitmap_start_offset + index] = 0; }
    fwrite(buffer, disk_size, 1, file_ptr);
    fclose(file_ptr);
    free(buffer);
}

/*
 * Write disk block to disk given a bloc and a seek position
 */
void write_block_to_disk(cs1550_disk_block *file_block, long seek) {
	FILE *file_ptr = fopen(".disk", "rb+");
	if (file_ptr != NULL) {
		fseek(file_ptr, 0, SEEK_END);
		int disk_size = ftell(file_ptr);
		rewind(file_ptr);
		char *disk_buffer = (char *)malloc(disk_size);
		fread(disk_buffer, disk_size, 1, file_ptr);
		rewind(file_ptr);
		memmove(disk_buffer+seek, file_block, BLOCK_SIZE);
		fwrite(disk_buffer, disk_size, 1, file_ptr);
		fclose(file_ptr);
		free(disk_buffer);
	}
}


/**************************************************************************************************************************************************/
/****************************************************************End Helper functions**************************************************************/
/**************************************************************************************************************************************************/

//register our new functions as the implementations of the syscalls
static struct fuse_operations hello_oper = {
    .getattr	= cs1550_getattr,
    .readdir	= cs1550_readdir,
    .mkdir	= cs1550_mkdir,
	.rmdir = cs1550_rmdir,
    .read	= cs1550_read,
    .write	= cs1550_write,
	.mknod	= cs1550_mknod,
	.unlink = cs1550_unlink,
	.truncate = cs1550_truncate,
	.flush = cs1550_flush,
	.open	= cs1550_open,
};

//Don't change this.
int main(int argc, char *argv[])
{
	return fuse_main(argc, argv, &hello_oper, NULL);
}
