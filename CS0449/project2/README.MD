#Project 2: What's the Password ?#

The objective of this project is to deconstruct existing programs that each have a secret password or passphrase that needs to be input in order to unlock the program.
The mystrings program takes a filename from the command line and reads the bytes of the file, looking for strings of printable characters (ASCII values between 32 and 126 decimal). A string is a run of at least 4 consecutive printable characters and ends whenever a non-printable character is encountered.  Whenever such a string is found, it gets printed out, one per line.
