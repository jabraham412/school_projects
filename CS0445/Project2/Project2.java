//John Abraham
//CS 445
//Project 2

import java.io.*;
import java.util.*;

public class Project2
{
    public static void main(String[] args) throws Exception
	{
		int[] dropInPt = new int[2]; // row and col will be on the 2nd line of input file;
		int[][] swamp = loadSwamp( args[0], dropInPt );
		int row=dropInPt[0], col = dropInPt[1];

		printSwamp(          "\n   SWAMP: dropped in at: ["+row+","+col+"]\n",swamp );
		System.out.println("\n   ESCAPE PATHS:\n" );

		// YOUR CODE HERE. DECLARE WHATEVER OBJECTS AND VARIABLES NEEDED
		// CALL YOUR METHOD(s) TO PRINT ALL ESCAPE PATHS

                String path = "";
                escape(swamp, row, col, path);


	} // END MAIN
	// ###################################################
    
	private static void escape (int [][] maze, int r, int c, String path)
    {
                path = path + "["+r +","+ c+"]";

                //Base Case 1: Attempted move is outside of grid
		if(r < 0 || r > maze[0].length -1 || c < 0 || c > maze[0].length -1)
                        return;

                //Base Case 2: when side of grid is reached or when r || c is zero
		if(c == maze[0].length -1 || r == maze[0].length - 1 || r == 0 || c == 0 )
                {
                        //print line when grid end is reached
                        System.out.println(path);
                        return;
                }
                    

				//Recursive Case
				if(maze[r][c+1] == 1)      //check right
                {
                    maze[r][c] = 2;
                    escape(maze, r, c+1, path);
                    maze[r][c] = 1;
                }
                
                if(maze[r+1][c+1] == 1)   //bottom right
                {
                    maze[r][c] = 2;
                    escape(maze, r+1, c+1, path);
                    maze[r][c] = 1;
                }
                
                if(maze[r+1][c] == 1)     //check bottom
                {
                    maze[r][c] = 2;
					escape(maze, r+1, c, path);
                    maze[r][c] = 1;
                }

                if(maze[r+1][c-1] == 1)   //bottom left
                {
                    maze[r][c] = 2;
                    escape(maze, r+1, c-1, path);
                    maze[r][c] = 1;
                }
                
                if (maze[r][c-1] == 1)    //check left
                {
                    maze[r][c] = 2;
                    escape(maze, r, c-1, path);
                    maze[r][c] = 1;
                }

                if(maze[r-1][c-1] == 1)   //top left
                {
                    maze[r][c] = 2;
                    escape(maze, r-1, c-1, path);
                    maze[r][c] = 1;
                }

                if(maze[r-1][c] == 1)     //check top
                {
                    maze[r][c] = 2;
                    escape(maze, r-1, c, path);
                    maze[r][c] = 1;
                }

                if(maze[r-1][c+1] == 1)   //top right
                {
                    maze[r][c] = 2;
                    escape(maze, r-1, c+1, path);
                    maze[r][c] = 1;
                }
    }
        
  	// DO NOT MODIFY THIS METHOD
	// ----------------------------------------------------------------
	private static void printSwamp(String label, int[][] swamp )
    {
        System.out.println( label );
        System.out.print("   ");
        for(int c = 0; c < swamp.length; c++)
        	System.out.print( c + " " ) ;
        System.out.print( "\n   ");
        for(int c = 0; c < swamp.length; c++)
        	System.out.print("- ");
       System.out.print( "\n");

        for(int r = 0; r < swamp.length; r++)
        {	System.out.print( r + "| ");
            for(int c = 0; c < swamp[r].length; c++)
                 System.out.print( swamp[r][c] + " ");
            System.out.println("|");
        }
        System.out.print( "   ");
        for(int c = 0; c < swamp.length; c++)
        	System.out.print("- ");
       System.out.print( "\n");
    }

	// DO NOT MODIFY THIS METHOD
   	// ----------------------------------------------------------------
	private static int[][] loadSwamp( String infileName, int[] dropInPt  ) throws Exception
    {
        Scanner infile = new Scanner( new File(infileName) );
        int rows=infile.nextInt();
        int cols = rows;  		// ASSUME A SQUARE GRID
        dropInPt[0]=infile.nextInt();  dropInPt[1]=infile.nextInt();
        int[][] swamp = new int[rows][cols];
        for(int r = 0; r < rows ; r++)
        	for(int c = 0; c < cols; c++)
                 swamp[r][c] = infile.nextInt();

        infile.close();
        return swamp;
    }
}