import java.util.*;

/**
 * @author Matt Brierley
 * @version 2/25/2019
 * https://www.cs.bu.edu/teaching/alg/maze/
 */
public class MazeSolver
{
    private char[][] maze;

    public MazeSolver(char[][] grid)
    {
        setMaze(grid);
    }

    public void setMaze(char[][] grid)
    {
        this.maze = new char[grid.length][];
        for (int r = 0; r < grid.length; r++)
        {
            this.maze[r] = new char[grid[r].length];
            for (int c = 0; c < grid[r].length; c++)
                this.maze[r][c] = grid[r][c];
        }
    }

    private boolean findPath(int r, int c)
    {
        // TODO Project #5 - done

        //if ([x,y] outside maze) return false
        if(!isInsideMaze(r,c))
        {
            return false;
        }
        //if ([x,y] is goal) return true
        else if(isGoal(r,c))
        {
            return true;
        }
        //if ([x,y] not open) return false
        else if(!isOpen(r,c))
        {
            return false;
        }else
        {
            //mark [x,y] as part of solution path
            maze[r][c] = '+';

            //if (FIND-PATH(North of x,y) == true) return true
            if(findPath(r-1,c))
            {
                return true;
            }
            //if (FIND-PATH(East of x,y) == true) return true
            if(findPath(r,c+1))
            {
                return true;
            }
            //if (FIND-PATH(South of x,y) == true) return true
            if(findPath(r+1,c))
            {
                return true;
            }
            //if (FIND-PATH(West of x,y) == true) return true
            if(findPath(r,c-1))
            {
                return true;
            }

            return false;
        }
    }

    private boolean isInsideMaze(int r, int c)
    {
        // TODO Project #5 - done
        boolean inside = true;

        if(r < 0 || r > maze.length - 1 || c < 0 || c > maze[r].length - 1)
        {
            inside = false;
        }

        return inside;
    }

    private boolean isGoal(int r, int c)
    {
        return (this.maze[r][c] == 'G');
    }

    private boolean isOpen(int r, int c)
    {
        // TODO Project #5 - done
         // ., S, or G would be considered open
        return (maze[r][c] == '.' || maze[r][c] == 'S' || maze[r][c] == 'G');
    }

    private boolean setGoal(int r, int c)
    {
        boolean goalOK = false;
        if (isInsideMaze(r, c) && isOpen(r, c))
        {
            this.maze[r][c] = 'G';
            goalOK = true;
        }
        return goalOK;
    }

    private boolean setStart(int r, int c)
    {
        boolean startOK = false;
        if (isInsideMaze(r, c) && isOpen(r, c))
        {
            this.maze[r][c] = 'S';
            startOK = true;
        }
        return startOK;
    }

    private void resetStart(int r, int c)
    {
        this.maze[r][c] = 'S';
    }

    public void displayMaze()
    {
        System.out.printf("      ");
        for (int c = 0; c < this.maze[0].length; c++)
        {
            System.out.printf("[%1$2s] ", c);
        }
        System.out.println();
        for (int r = 0; r < this.maze.length; r++)
        {
            System.out.printf("[%1$2s]", r);
            for (int c = 0; c < this.maze[0].length; c++)
            {
                System.out.printf("%1$5s", this.maze[r][c]);
            }
            System.out.println();
        }
    }

    public static void main(String args[])
    {
        char[][] grid = {{'.', '#', '#', '#', '#', '#'},
                         {'.', '.', '.', '.', '.', '#'},
                         {'#', '.', '#', '#', '#', '#'},
                         {'#', '.', '#', '.', '#', '#'},
                         {'.', '.', '.', '#', '.', '.'},
                         {'#', '#', '.', '.', '.', '#'}};

        MazeSolver searchGrid = new MazeSolver(grid);
        System.out.print("\n        *** SEARCH THE MAZE ***\n");
        searchGrid.displayMaze();
        Scanner keyboard = new Scanner(System.in);

        int rGoal;
        int cGoal;
        int rStart;
        int cStart;
        boolean inputOK;

        do
        {
            inputOK = true;
            System.out.println("Enter the START row");
            rStart = keyboard.nextInt();
            System.out.println("Enter the START column");
            cStart = keyboard.nextInt();
            if (!searchGrid.setStart(rStart, cStart))
            {
                System.out.println("Incorrect START coordinates, please try again.");
                inputOK = false;
            }
        } while (!inputOK);

        do
        {
            inputOK = true;
            System.out.println("Enter the GOAL row");
            rGoal = keyboard.nextInt();
            System.out.println("Enter the GOAL column");
            cGoal = keyboard.nextInt();
            if (rGoal == rStart && cGoal == cStart)
            {
                System.out.println("GOAL is the same as START, try different coordinates.");
                inputOK = false;
            }
            else if (!searchGrid.setGoal(rGoal, cGoal))
            {
                System.out.println("Incorrect GOAL coordinates, please try again.");
                inputOK = false;
            }
        } while (!inputOK);

        searchGrid.displayMaze();
        if (searchGrid.findPath(rStart, cStart))
            System.out.println("\n---> The GOAL [" + rGoal + "," + cGoal + "] was found!");
        else
            System.out.println("\n---> The GOAL [" + rGoal + "," + cGoal + "]  was not reached!");
        searchGrid.resetStart(rStart, cStart);
        System.out.println("\nThe search results:");
        searchGrid.displayMaze();
    }
}
