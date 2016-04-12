/**
* Project4.java
*
* @author John Abraham
*/

import java.io.*;
import java.util.*;

public class Project4
{
        static String[][] grid; //accessable from all methods.
        static int[] rdelta = {-1,-1,-1, 0, 0, 1, 1, 1};
        static int[] cdelta = {-1, 0, 1,-1, 1,-1, 0, 1};
        static List<String> wordsFound = new ArrayList<String>();
        static PrefixTree t = new PrefixTree();//instance of Trie class
        static Project4 p = new Project4();    //instance of class Project4
        
        public static void main(String args []) throws Exception
	    {
            double start = System.currentTimeMillis();

            grid = loadLetters( args[0] );
            BufferedReader dictionaryFile = new BufferedReader( new FileReader("dictionary.txt") );

            //only insert words from the dictionary that start with a letter from the grid
            //and the second letter of the word be adjacent to the letter in the grid to reduce the overall execution time.
            int g = grid.length * grid.length;
            while( dictionaryFile.ready() )
            {
                String previousLine = dictionaryFile.readLine();
                if( previousLine.length() >= 3 && previousLine.length() <= g)
                {
                    for (int row = 0; row < grid.length; row++)
                    {
                        for (int col = 0; col < grid.length; col++)
                        {
                            if( previousLine.startsWith(grid[row][col]) )
                            {
                                t.insert( previousLine );
                                row = grid.length;
                                col = grid.length;
                            }
                         }
                     }
                }
            }
            dictionaryFile.close();

            p.findAllWords();

            double end = System.currentTimeMillis();
            System.out.println("Execution Time: " +((end-start)/1000) + " s");
        }
        
	    private static String[][] loadLetters( String infileName ) throws Exception
        {
            Scanner infile = new Scanner( new File(infileName) );
            int row = infile.nextInt();
            int col = row;
            String[][] grid = new String[row][col];

            for(int r = 0; r < row ; r++)
                for(int c = 0; c < col; c++)
                    grid[r][c] = infile.next();

            infile.close();
            return grid;
        }

        private void findAllWords() throws Exception
        {
            for (int row = 0; row < grid.length; row++)
                for (int col = 0; col < grid.length; col++)
                    findAllWordsHelper("", row, col);
        }
        
        private void findAllWordsHelper(String words, int row, int col) throws Exception
        {
            if (row >= 0 && row < grid.length && col >= 0 && col < grid.length && !grid[row][col].equals("*") )
            {
                words += grid[row][col];
                if(words.length() >= 3)
                {
                    if( t.search(words) )
                    {
                        if(!wordsFound.contains(words)){
                            System.out.println(words);
                            wordsFound.add(words);
                        }
                    }
                }

                if(t.isPrefix(words))
                {
                    String temp = grid[row][col];
                    grid[row][col] = "*";
                    for(int k=0; k < rdelta.length; k++)
                          findAllWordsHelper(words, row+rdelta[k], col+cdelta[k]);
                    grid[row][col] = temp;
                }
            }
        }
    }

class PrefixTree {

    private TreeNode root;

    public PrefixTree() {
        root = new TreeNode();
    }

    public void insert(String dicWord)
    {
        TreeNode auxVar = root;
        for (char c : dicWord.toCharArray())
        {
            TreeNode child = auxVar.traverse(c);
            if (child == null)
                auxVar = auxVar.addEdge(c);
            else
                auxVar = child;
        }
        auxVar.setKey(dicWord);
    }

    public boolean search(String words)
    {
            TreeNode auxVar = root;
            for(char c : words.toCharArray())
            {
                TreeNode child = auxVar.traverse(c);
                if(child == null) return false;
                auxVar = child;
            }
            if(auxVar.getKey() == null)
                return false;
            else
                return true;
    }
        
    public boolean isPrefix(String wordPrefix)
    {
        TreeNode auxVar = root;
        for (char c : wordPrefix.toCharArray())
        {
            TreeNode child = auxVar.traverse(c);
            if (child == null)
                return false;
            else
                auxVar = child;
        }
        return true;
    }
}

class TreeNode {

    private String key;
    private SortedMap<Character, TreeNode> edges;

    TreeNode addEdge(char c)
    {
            if (edges == null)
                edges = new TreeMap<Character, TreeNode>();

            TreeNode childNode = new TreeNode();
            edges.put(c, childNode);
            return childNode;
    }

    TreeNode traverse(char c)
    {
        if(edges == null)
            return null;
        else
            return edges.get(c);
    }

    void setKey(String key)
    {
        this.key = key;
    }
    
    String getKey() {
        return key;
    }
}
