/* John Abraham
 * Project 5: Shell
 * File: myshell.c
 * CS 499
 *
 * myshell.c supports the following:
 * 1) internal shell command "exit"
 * 2) internal shell command "cd"
 * 3) UNIX command with or without arguments "pwd"
 * 4) UNIX command whose output is redirected to a file "pwd > foo"
 * 5) UNIX command is appended to a file "pwd >> foo"
 * 6) UNIX command whose input is redirected from a file "bc < foo"
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main()
{
	while(1)
	{
		//allocate memory for input string
		char str[20];
		//take input from stdin stream up to maximum size
		fgets(str, sizeof(str), stdin);
		char original_str[50];
		strcpy(original_str, str);
	    //token stores the tokens delimited by whitespace
	    char *token = strtok(str, " \n");
/***************************************************************/
		if( strncmp(token, "exit", 4) == 0 )
		{
			exit(EXIT_SUCCESS);
		}
/***************************************************************/
		else if( strcmp(token, "cd") == 0 )
		{
			token = strtok(NULL, " \n"); //changes to next token
			if(chdir(token) != 0) {
					printf("-bash: %s: No such file or directory\n", token);
			}
		}
/***************************************************************/
		else if( strcmp(token, "pwd") == 0 )
		{
			original_str[ strlen(original_str) - 1 ] = '\0';

			if(strcmp(original_str, "pwd") == 0 )
			{
				printf("%s\n", getcwd(NULL, 64));
			}
			else
			{
				token = strtok(NULL, " ");

				if( strcmp(token, ">") == 0 )
				{
					FILE *fp;
					token = strtok(NULL, " \n");
					fp = freopen(token, "w+", stdout);
					printf("%s\n", getcwd(NULL, 64));
					fclose(fp);
				}
				else if( strcmp(token, ">>") == 0 )
				{
					FILE *fp;
					token = strtok(NULL, " \n");
					fp = freopen(token, "a+", stdout);
					printf("%s\n", getcwd(NULL, 64));
					fclose(fp);
				}
				else
					printf("%s\n", getcwd(NULL, 64));
			}
		}
/***************************************************************/
		else if( strcmp(token, "bc") == 0 )
		{
			original_str[ strlen(original_str) - 1 ] = '\0';

			if( strcmp(original_str, "bc") == 0 )
			{
				execl("/usr/bin/bc", "/usr/bin/bc", NULL);
			}
			else
			{
				token = strtok(NULL, " ");

				if( strcmp(token, "<") == 0 )
				{
					/*
					FILE *fp;
					token = strtok(NULL, " \n");
					fp = fopen(token, "r");
					char buff[10];
 				    fscanf(fp, "%s", buff);
   					fclose(fp);
					*/

					token = strtok(NULL, " \n");

					int pid;	/* process ID */
					switch (pid = fork())
					{
						case 0:		/* a fork returns 0 to the child */
							execlp("bc", "bc", token, "/", (char*)0);
							perror("execlp");	/* if we get here, execlp failed */
							exit(1);
							break;

						default:	/* a fork returns a pid to the parent */
							break;

						case -1:	/* something went wrong */
							perror("fork");
							exit(1);
					}

				}
			}
		}
/***************************************************************/
		else
		{
			printf("-bash: %s: command not found\n", str);
		}
	}
	return 0;
}