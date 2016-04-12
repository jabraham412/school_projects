/* John Abraham
 * Project 4: /dev/dice
 * File: yahtzee.c
 * CS 449
 *
 * NOTE: driver returns random numbers from 1 to 6
 * when "cat /dev/dic" is typed into the emulator
 * but was not able to get it to work with main program.
 * The RNG function at the end of the code is just a 
 * a tester instead of /dev/dice
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int rand_choice(); //temp tester RNG

/* Two structs to store the data.
 * One for upper section.
 * One for lower section.
 */ 

struct upper_section
{
	unsigned short ones;
	unsigned short twos;
	unsigned short threes;
	unsigned short fours;
	unsigned short fives;
	unsigned short sixes;
};

struct lower_section
{
	unsigned short three_of_a_kind;
	unsigned short four_of_a_kind;
	unsigned short full_house;
	unsigned short small_straight;
	unsigned short large_straight;
	unsigned short yahtzee;
	unsigned short chance;
};


int main()
{
	struct upper_section us;
	struct lower_section ls;
	//initializing space before using it
	us.ones = 0;	us.twos = 0;	us.threes = 0;	us.fours = 0;	us.fives = 0;	us.sixes = 0;
	ls.three_of_a_kind = 0;	ls.four_of_a_kind = 0;	ls.full_house = 0;	ls.small_straight = 0;
	ls.large_straight = 0;	ls.yahtzee = 0;	ls.chance = 0;

	int dice[4];
	int bonus = 0;
	int total = 0;
	int i; //used for for loops only
	int turns_played = 0; // keeps track of turns played to know when to end the game



/***********************************LOOPS*************************************/
	while (turns_played != 13)
	{
	/*
		FILE *fp = popen("cat /dev/dice", "r");
		char d[100];
		fread(d, sizeof(d), 1, fp);
		printf("\n%s\n", d);
		pclose(fp);
	*/
		srand(time(NULL));

		printf("Your roll: \n\n");

		for( i=0; i<=4; i++ ) {
			dice[i] = rand_choice();
		}

		for( i=0; i<=4; i++ ){
			printf(" %d ", dice[i]);
		}

		printf("\n\nwhich dice to reroll? ");
		dice[0] = rand_choice();

		printf("\n\n");

		for( i=0; i<=4; i++ ){
			printf(" %d ", dice[i]);
		}

		printf("\n\nwhich dice to reroll? ");
		dice[0] = rand_choice();

		printf("\n\n");
		for( i=0; i<=4; i++ ){
			printf(" %d ", dice[i]);
		}

		printf("\n\nPlace dice into: \n1) Upper Section\n2) Lower Section\n\nSelection? ");

		int Selection;
		scanf("%d", &Selection);

		if(Selection == 1)
		{
			printf("\nPlace dice into:\n1) Ones\n2) Twos\n3) Threes\n4) Fours\n5) Fives\n6) Sixes\n\nSelection? ");
			int Selection;
			scanf("%d", &Selection);
			if(Selection == 1){}
			if(Selection == 2){}
			if(Selection == 3){}
			if(Selection == 4){}
			if(Selection == 5){}
			if(Selection == 6){}
			if(us.ones+us.twos+us.threes+us.fours+us.fives+us.sixes >= 63) {
				total += 35;
			}
		}

		if(Selection == 2)
		{
			printf("\nPlace dice into:\n1) Three of a Kind\n2) Four of a Kind\n3) Full House\n4) Small Straight\n5) large Straight\n6) Yahtzee\n7) Chance\n\nSelection? " );
			int Selection;
			scanf("%d", &Selection);
			if(Selection == 1){}
			if(Selection == 2){}
			if(Selection == 3){}
			if(Selection == 4){}
			if(Selection == 5){}
			if(Selection == 6){}
			if(Selection == 7){}
		}

		// add new total score
		total = bonus+us.ones+us.twos+us.threes+us.fours+us.fives+us.sixes+ls.three_of_a_kind+ls.four_of_a_kind+ls.full_house+ls.small_straight+ls.large_straight+ls.yahtzee+ls.chance;

		printf("\nYour score so far is: %d\n\n", total);
		printf("Ones: %d \t\t\t Twos: %d\n", us.ones, us.twos);
		printf("Threes: %d \t\t\t Fours: %d\n", us.threes, us.fours);
		printf("Fives: %d \t\t\t Sixes: %d\n", us.fives, us.sixes);

		printf("Upper Section Bonus: 0\n");

		printf("Three of a Kind: %d \t\t Four of a Kind: %d\n", ls.three_of_a_kind, ls.four_of_a_kind);
		printf("Small Straight: %d \t\t Large Straight: %d\n", ls.small_straight, ls.large_straight);
		printf("Full House: %d \t\t\t Yahtzee: %d\n", ls.full_house, ls.yahtzee);
		printf("Chance: %d\n", ls.chance);
		printf("\n----------------------------------------------------------------------------\n");

		turns_played++;
	}
}

int rand_choice() //temp tester RNG
{
	int rand_int = rand()%(6 -1 +1)+1;

	return rand_int;
}
