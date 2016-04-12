#include <stdio.h>

int main()
{
	int w;
	printf("Please enter the weight you'd like to convert: ");
	scanf("%d", &w);
	printf("\nHere is you weight on other planets:\n");
	printf("Mercury\t%0.f lbs\nVenus\t%0.f lbs\n", 0.38*w, 0.91*w );
	printf("Mars\t%0.f lbs\nJupiter\t%0.f lbs\n", 0.38*w, 2.54*w);
	printf("Saturn\t%0.f lbs\nUranus\t%0.f lbs\n", 1.08*w, 0.91*w );
	printf("Neptune\t%0.f lbs\nPluto\t%0.f lbs\n", 1.19*w, 0.06*w );
}
