/**
* John Abraham
* Email: jpa18@pitt.edu
* CS 449: Project 1
* EXIF Viewer (70 points) 
*/
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct header;
struct tags;

// this structure represents the JPEG/TIFF/EXIF header data elements that we want to display
struct header
{
	char *manufacturer;
	char *model;
	float exposure_time;
	float f_stop;
	int ISO_speed;
	char *date_taken;
	float focal_length;
	int width;
	int height;
};

// this structure represents a TIFF tag
struct tags
{
	int manufacturer_tagID;
	int model_tagID;
	int exposure_time_tagID;
	int f_stop_tagID;
	int ISO_speed_tagID;
	int date_taken_tagID;
	int focal_length_tagID;
	int width_tagID;
	int length_tagID;
};

main( int argc, char *argv[] )
{
	struct header h;
	struct tags t;

	// tag indentifiers
	t.manufacturer_tagID = 0x010F;
	t.model_tagID = 0x0110;
	t.exposure_time_tagID = 0x829A;
	t.f_stop_tagID = 0x829D;
	t.ISO_speed_tagID = 0x8827;
	t.date_taken_tagID = 0x9003;
	t.focal_length_tagID = 0x92A0;
	t.width_tagID = 0xA002;
	t.length_tagID = 0xA003;

	FILE *img_file = fopen(argv[1], "rb");

	if(img_file == NULL)
	{
		printf("\nThe file \"%s\" does not exist \n\n", argv[1]);
		return;
	}

	//EXIT IF "Exif" ISN'T WHERE IT SHOULD BE (Offset of 6)
	char exif_buffer[10];
	fseek(img_file, 6, SEEK_SET);
	fread(exif_buffer, 1, 10, img_file);
	if( strcmp(exif_buffer, "Exif") != 0 )
	{
		printf("\nERROR: The tag was not found\n\n");
		return;
	}

	//IF THE TIFF HEADER CONTAINS MM INSTEAD OF II, PRINT AN ERROR MESSAGE THT WE DO NOT SUPPORT ENDIANNESS
	char endianness_buffer[2];
	fseek(img_file, 12, SEEK_SET);
	fread(endianness_buffer, 1, 2, img_file);
	if( strcmp(endianness_buffer, "II")  != 0)
	{
		printf("\nERROR: We do not support endianness \n\n");
		return;
	}

	// manufacturer
	char tag_buffer[10];
	fseek(img_file, t.manufacturer_tagID-47, SEEK_SET);
	fread(tag_buffer, 1, 10, img_file);
	h.manufacturer = tag_buffer;
	printf("Manufacturer: %s \n", h.manufacturer);

	// model
	char model_buffer[10];
	fseek(img_file, t.model_tagID-30, SEEK_SET);
	fread(model_buffer, 1, 10, img_file);
	h.model = model_buffer;
	printf("Model \t: %s \n", h.model);


	//expose_time
	printf("Exposure Time: %f \n", h.exposure_time);

	//f_stop
	printf("F-stop: %f \n", h.f_stop);

	//ISO_speed
	printf("ISO: %d \n", h.ISO_speed);

	//date_taken
	printf("Date Taken: %s \n", h.date_taken);

	//focal_length
	printf("Focal Length: %f \n", h.focal_length);

	//width
	printf("Width: %d \n", h.width);

	//height
	printf("Height: %d \n", h.height);


	fclose(img_file);
}
