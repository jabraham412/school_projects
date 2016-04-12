/*
John Abraham
CS449: 4pm
Project2: mystrings.c
*/

#include <stdio.h>
#include <string.h>

int main( int argc, char *argv[] )
{
	/*Use fopen with read "r" mode (reads text. Binary or not). If "rb" was used instead
	  then the program wouldn't be able to read regular text files. In this program we
	  are not doing anything special based upon file type. Stream labeled "input_file."
	*/
	FILE *input_file = fopen( argv[1], "r" );

	//If the file entered in the command line is not found print message and exit program.
	if ( input_file == NULL ) {
		printf("Failed to open file. \n");
		return;
	}

	/* string: holds the ASCII characters as we loop through the
	   file byte-by-byte (character-by-character) because ASCII characters
	   have a size of 1 byte (8 bits). string holds up to 500 characters.

	   c: stores the next character as we loop through the file.

	   Algorithm: For every character as we loop through the stream if the character is
	   an ASCII character (between 32 and 126 decimal) we append it to the string until
	   we reach a non-ASCII char then if string is longer than 3 characters long we print
	   it to the screen then we clear the contents of string
	   (regardless if it was printed to the screen or not).
	*/

	char string[500];
	char c;
	
	while( !feof(input_file)  )
	{
		c = fgetc( input_file );
		if( c >= 32 && c <= 126  ) {
			strncat( string, &c, 1  );
		}else {
			if( strlen(string) >= 4  )
				printf( "%s \n", string );

			strcpy(string, "");
		}
	}
}
