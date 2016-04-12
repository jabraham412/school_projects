#Project 5: A Shell#

In this assignment you are to implement a Unix shell program. A shell is simply a program that conveniently allows you to run other programs. Read up on your favorite shell to see what it does.

Your shell must support the following:

1. The internal shell command "exit" which terminates the shell.
Example: exit
Concepts: shell commands, exiting the shell
System calls: exit()
 
2. The internal shell command "cd" which changes the present working directory
Example: cd private
Details: This command takes a relative or absolute path and changes the present working directory to that path
Concepts: present working directory, absolute and relative paths
System calls: chdir()
 
3. Any UNIX command, with or without arguments
Example commands: ls, pico, pwd, ls –l, wc –l, ps -a
Details: Your shell must block until the command completes and, if the return code is abnormal, print out a message to that effect. Argument 0 is the name of the command
Concepts: Forking a child process, waiting for it to complete, synchronous execution, Command-line parameters
System calls: fork(), execvp(), exit(), wait()
 
4. A command, with or without arguments, whose output is redirected to a file
Example: ls -l > foo
Details: This takes the output of the command and put it in the named file
Concepts: File operations, output redirection
System calls: freopen()
 
5. A command, with or without arguments, whose output is appended to a file
Example: ls -l >> foo
Details: This takes the output of the command and appends it to the named file
Concepts: File operations, output redirection
System calls: freopen()
 
6. A command, with or without arguments, whose input is redirected from a file
Example:  bc < foo
Details: This takes the named file and redirects it as stdin.
Concepts: File operations, output redirection
System calls: freopen()
