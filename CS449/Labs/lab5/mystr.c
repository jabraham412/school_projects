#include <stdio.h>

void my_strcpy(char *dest, char *src)
{
        while(*dest++ = *src++);
}

char *my_strcat(char *dest, const char *src )
{
	char *temp = dest;
	while(*temp)
		++temp;
	while((*temp++ = *src++) != '\0');
	return(dest);
}
