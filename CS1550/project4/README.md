#Project4: FUSE File System. 

This is an implementation of a file system using FUSE (Filesystem in Userspace), managed via a single file that represents our disk device. Through FUSE and this implementation, it will be possible to interact with a newly created file system using standard UNIX/Linux programs in a transparent way.

From a user interface perspective, this file system is a two level directory system, with the following restrictions/simplifications:<br />
1. The root directory “/” will only contain other subdirectories, and no regular files<br />
2. The subdirectories will only contain regular files, and no subdirectories of their own<br />
3. All files will be full access (i.e., chmod 0666), with permissions to be mainly ignored<br />
4. Many file attributes such as creation and modification times will not be accurately stored<br />
5. Files cannot be truncated<br />

From an implementation perspective, the file system will keep data on “disk” via a linked list allocation strategy.<br />
In order to manage the free or empty space, a bookkeeping block(s) is created in .disk that records what blocks have been previously allocated or not. A simple bitmap is used. <br />

![alt tag](https://github.com/abrahamjj/School_Projects/blob/master/CS1550/project4/linked_list_allocation.gif)

The root directory, subdirectories, and files are stored on disk in a file called .disk <br />
To format the disk file enter the command: <br />
dd bs=1K count=5K if=/dev/zero of=.disk <br />
This will create a 5MB disk image initialized to contain all zeros, named .disk replacing the old one.


