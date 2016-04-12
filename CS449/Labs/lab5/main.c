#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

int main() {
    void *handle;
    void (*my_str_copy)(char *, char *);
    char* (*my_str_cat)(char *, char *);
    char *error;
    handle = dlopen("mystr.so", RTLD_LAZY);
    if(!handle) { //handle == NULL
        printf("%s\n", dlerror()); //dlerror gives us a string with the error
        exit(1);
    }
    dlerror();    // Clear any existing error 
    my_str_copy = dlsym(handle, "my_strcpy"); //lookup the function by name
    my_str_cat = dlsym(handle, "my_strcat");
    if ((error = dlerror()) != NULL)  {
        printf ("%s\n", error);
        exit(1);
    }
    //Let’s test it.
    char dest[100];
    char src[] = "Hello World!";
    my_str_copy(dest, src);
    printf ("%s\n", dest);

    //testing my_strcat
    char cat_dest[100] = "Hello ";
    char cat_src[] = "World!";
    printf("%s \n",  my_str_cat(cat_dest, cat_src));

    dlclose(handle);
    return 0;
}
