/* John Abraham
 * Project 4: /dev/dice
 * File: dice_driver.c
 * CS 449
 * 
 * NOTE: driver returns random numbers from 1 to 6
 * when "cat /dev/dic" is typed into the emulator
 * but was not able to get it to work with main program
 */

#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <linux/random.h>
#include <asm/uaccess.h>

unsigned char get_random_byte (int max);

static int dice_read(void) {
	printk( "%u\n", get_random_byte(7) );
	return 0;
}

unsigned char get_random_byte (int max) {
    unsigned char c;

    do {
			get_random_bytes(&c, 1);
	} while(c%max == 0);

    return c%max;
}

static const struct file_operations dice_fops = {
	.owner		= THIS_MODULE,
	.read		= dice_read,
};

static struct miscdevice dice_driver = {
	/*
	 * We don't care what minor number we end up with, so tell the
	 * kernel to just pick one.
	 */
	MISC_DYNAMIC_MINOR,
	/*
	 * Name ourselves /dev/dice.
	 */
	"dice_driver",
	/*
	 * What functions to call when a program performs file
	 * operations on the device.
	 */
	&dice_fops
};

static int __init
dice_init(void)
{
	int ret;

	/*
	 * Create the "dice_driver" device in the /sys/class/misc directory.
	 * Udev will automatically create the /dev/dice device using
	 * the default rules.
	 */
	ret = misc_register(&dice_driver);
	if (ret)
		printk(KERN_ERR
		       "Unable to register \"/dev/dice\" misc device\n");

	return ret;
}

module_init(dice_init);

static void __exit
dice_exit(void)
{
	misc_deregister(&dice_driver);
}

module_exit(dice_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("John Abraham <jpa18@pitt.edu>");
MODULE_DESCRIPTION("/dev/dice minimal module");
MODULE_VERSION("dev");
