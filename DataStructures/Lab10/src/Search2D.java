public class Search2D
{
    /**
     * Searches for the desiredItem in a rectangular matrix[][] where
     * elements are sorted within each row and within each column
     * If the element is found, prints its position,
     * otherwise prints "not found"
     *
     * @author  Matt Brierley
     * @version 4/8/2019
     *
     */

    private void search(int[][] matrix, int desiredItem)
    {
        // TODO Project 5 - done
        System.out.println("Searching for " + desiredItem);

        boolean found = false;
        int row = 0;
        int col = matrix[0].length-1;

        while(!found && row < matrix.length && col >= 0)
        {
            System.out.println("Checking " + matrix[row][col]);

            if(matrix[row][col] == desiredItem)
            {
                found = true;
            }
            else if(matrix[row][col] < desiredItem)
            {
                row++;
            }
            else
            {
                col--;
            }
        }

        if(found)
        {
            System.out.printf("%d found at [%d, %d]\n\n", desiredItem, row, col);
        }
        else
        {
            System.out.printf("%d not found\n\n",desiredItem);
        }
    }

    // driver to test search method
    public static void main(String[] args)
    {
        int matrix[][] = {
                {10, 20, 21, 40},
                {15, 25, 26, 45},
                {27, 29, 30, 48},
                {32, 33, 34, 50}};

        Search2D search2D = new Search2D();

        System.out.println("*** These should be successful searches ***");
        for (int r = 0; r < matrix.length; r++)
        {
            for (int c = 0; c < matrix[r].length; c++)
            {
                search2D.search(matrix, matrix[r][c]);
            }
        }

        System.out.println("\n*** These should be unsuccessful searches ***");
        search2D.search(matrix,28);
        search2D.search(matrix,5);
        search2D.search(matrix,100);
    }
}
