import java.util.*;

/**
 * @author Matt Brierley
 * @version 4/27/2019
 */

public class WorkingWithBinaryTrees <T extends Comparable<? super T>>
{
    public boolean isBST(BinaryNode<T> root)
    {


        // TODO Project 3 - done
        // implement iteratively without iterator
        boolean bst = true;
        Stack<BinaryNode<T>> bnStack = new Stack<>();
        BinaryNode<T> currentNode = root;
        BinaryNode<T> previousNode = null;

        System.out.print("Traversing the tree \"InOrder\" to check if it is BST: ");

        while(bst && (currentNode != null || !bnStack.isEmpty()))
        {
            //find leftmost node with no child
            while(currentNode != null)
            {
                bnStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            //visit leftmost node, then traverse its right subtree
            if(!bnStack.isEmpty())
            {
                currentNode = bnStack.pop();
                System.out.print(currentNode.getData() + " ");

                if(previousNode != null && previousNode.getData().compareTo(currentNode.getData()) > 0)
                {
                    bst = false;
                }

                previousNode = currentNode;
                currentNode = currentNode.getRightChild();
            }
        }
        System.out.println();

        /*
        //works, but doesn't check inorder, commenting out
        bnStack.push(root);
        while(bst && !bnStack.isEmpty())
        {
            BinaryNode<T> current = bnStack.pop();
            if(current.hasLeftChild())
            {
                if(current.getLeftChild().getData().compareTo(current.getData()) > 0)
                {
                    bst = false;
                }
                bnStack.push(current.getLeftChild());
            }

            if(current.hasRightChild())
            {
                if(current.getRightChild().getData().compareTo(current.getData()) < 0)
                {
                    bst = false;
                }
                bnStack.push(current.getRightChild());
            }
        }
        */

        return bst;
    }

    public T getSmallest(BinaryNode<T> root)
    {

        // TODO Project 3 - done
        // implement recursively
        if(!root.hasLeftChild())
        {
            return root.getData();
        }

        return getSmallest(root.getLeftChild());
    }

    public T getSecondLargest(BinaryNode<T> root)
    {

        // TODO Project 3 - done
        // implement iteratively
        BinaryNode<T> parentNode = root;
        BinaryNode<T> largestNode = parentNode.getRightChild();
        BinaryNode<T> secondLargestNode = null;

        if(root.hasLeftChild() || root.hasRightChild())
        {
            if(largestNode != null)
            {
                while(largestNode.hasRightChild())
                {
                    parentNode = largestNode;
                    largestNode = largestNode.getRightChild();
                }
            }
            else
            {
                largestNode = root;
            }

            if(largestNode.hasLeftChild())
            {
                secondLargestNode = largestNode.getLeftChild();

                while(secondLargestNode.hasRightChild())
                {
                    secondLargestNode = secondLargestNode.getRightChild();
                }
            }
            else
            {
                secondLargestNode = parentNode;
            }
        }

        return (secondLargestNode != null ? secondLargestNode.getData() : null);
    }

    public BinaryNode<T> buildBSTfromSortedArray(T[] sortedArray, int left, int right)
    {

        // TODO Project 3 - done
        // implement recursively
        if(left > right)
        {
            return null;
        }

        int mid = left + (right - left) / 2;
        BinaryNode<T> root = new BinaryNode<>(sortedArray[mid]);

        root.setLeftChild(buildBSTfromSortedArray(sortedArray, left, mid - 1));
        root.setRightChild(buildBSTfromSortedArray(sortedArray,mid + 1, right));

        return root;
    }

    public void printBSTinLevelOrder(BinaryNode<T> root)
    {

        // TODO Project 3 - done
        // implement iteratively
        Queue<BinaryNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        int level = 0;

        while(!queue.isEmpty())
        {
            int queueSize = queue.size();
            for(int i = 0; i < queueSize; i++)
            {
                BinaryNode<T> current = queue.poll();
                System.out.print(current.getData() + " ");

                if(current.hasLeftChild())
                {
                    queue.offer(current.getLeftChild());
                }

                if(current.hasRightChild())
                {
                    queue.offer(current.getRightChild());
                }
            }
            System.out.println("<-- level " + ++level);
        }
    }

    public boolean areTwoBSTsSame(BinaryNode<T> root1, BinaryNode<T> root2)
    {
        boolean same = false;

        if ((root1 != null) && (root2 != null))
        {
            Iterator<T> itTree1 = new InorderIterator(root1);
            Iterator<T> itTree2 = new InorderIterator(root2);

            // TODO Project 3 - done
            same = true;

            if(root1.getNumberOfNodes() != root2.getNumberOfNodes())
            {
                same = false;
            }
            else
            {
                while(same && itTree1.hasNext())
                {
                    if(!itTree1.next().equals(itTree2.next()))
                    {
                        same = false;
                    }
                }
            }
        }

        return same;
    }

    private class InorderIterator implements Iterator<T>
    {
        private ArrayDeque<BinaryNode<T>> stack;
        private BinaryNode<T> current ;

        public InorderIterator(BinaryNode<T> root)
        {
            stack = new ArrayDeque<>();
            current = root;
        } // end default constructor

        public boolean hasNext()
        {
            return !stack.isEmpty() || (current != null);
        } // end hasNext

        public T next()
        {
            // TODO Project 3 - done
            BinaryNode<T> nextNode = null;

            //find leftmost node with no child
            while(current != null)
            {
                stack.push(current);
                current = current.getLeftChild();
            }

            //visit leftmost node, then traverse its right subtree
            if(!stack.isEmpty())
            {
                nextNode = stack.pop();
                assert nextNode != null;

                current = nextNode.getRightChild();
            }

            return (nextNode != null ? nextNode.getData() : null);
        } // end next

        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove
    } // end InorderIterator


    // helper methods for testing:
    public BinaryNode<String> createTree1()
    {
        // Leaves
        BinaryNode<String> dTree = new BinaryNode<>("D");
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> gTree = new BinaryNode<>("G");

        // Subtrees:
        BinaryNode<String> bTree = new BinaryNode<>("B", dTree, eTree);
        BinaryNode<String> cTree = new BinaryNode<>("C", fTree, gTree);

        System.out.println("\nTree 1:\n");
        System.out.println("     A      ");
        System.out.println("   /   \\   "); // '\\' is the escape character for backslash
        System.out.println("  B     C   ");
        System.out.println(" / \\   / \\");
        System.out.println("D   E  F  G ");
        System.out.println();
        return new BinaryNode<>("A", bTree, cTree);
    } // end createTree1

    public BinaryNode<String> createTree1a()
    {
        // Leaves
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> cTree = new BinaryNode<>("C");
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> gTree = new BinaryNode<>("G");

        // Subtrees:
        BinaryNode<String> bTree = new BinaryNode<>("B", aTree, cTree);
        BinaryNode<String> fTree = new BinaryNode<>("F", eTree, gTree);

        System.out.println("\nTree 1a:\n");
        System.out.println("     D      ");
        System.out.println("   /   \\   "); // '\\' is the escape character for backslash
        System.out.println("  B     F   ");
        System.out.println(" / \\   / \\");
        System.out.println("A   C  E  G ");
        System.out.println();
        return new BinaryNode<>("D", bTree, fTree);

    } // end createTree1a

    public BinaryNode<String> createTree2() //  B has no left child
    {
        // Leaves
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> gTree = new BinaryNode<>("G");

        // Subtrees:
        BinaryNode<String> bTree = new BinaryNode<>("B", null, eTree);
        BinaryNode<String> cTree = new BinaryNode<>("C", fTree, gTree);

        System.out.println("\nTree 2:\n");
        System.out.println("     A      ");
        System.out.println("   /   \\   ");
        System.out.println("  B     C   ");
        System.out.println("   \\   / \\");
        System.out.println("    E  F  G ");
        System.out.println();
        return new BinaryNode<>("A", bTree, cTree);
    } // end createTree2

    public BinaryNode<String> createTree2a() //  B has no left child
    {
        // Leaves
        BinaryNode<String> bTree = new BinaryNode<>("B");
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> gTree = new BinaryNode<>("G");

        // Subtrees:
        BinaryNode<String> aTree = new BinaryNode<>("A", null, bTree);
        BinaryNode<String> fTree = new BinaryNode<>("F", eTree, gTree);

        System.out.println("\nTree 2a:\n");
        System.out.println("     C      ");
        System.out.println("   /   \\   ");
        System.out.println("  A     F   ");
        System.out.println("   \\   / \\");
        System.out.println("    B  E  G ");
        System.out.println();
        return new BinaryNode<>("C", aTree, fTree);
    } // end createTree2a

    public BinaryNode<String> createTree3()
    {
        // Leaves
        BinaryNode<String> dTree = new BinaryNode<>("D");
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> gTree = new BinaryNode<>("G");

        // Subtrees:
        BinaryNode<String> fTree = new BinaryNode<>("F", null, gTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", dTree, eTree);
        BinaryNode<String> cTree = new BinaryNode<>("C", fTree, null);

        System.out.println("\nTree 3:\n");
        System.out.println("     A      ");
        System.out.println("   /   \\  ");
        System.out.println("  B     C  ");
        System.out.println(" / \\   /  ");
        System.out.println("D   E  F   ");
        System.out.println("        \\ ");
        System.out.println("         G ");
        System.out.println();
        return new BinaryNode<>("A", bTree, cTree);
    } // end createTree3

    public BinaryNode<String> createTree3a()
    {
        // Leaves
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> cTree = new BinaryNode<>("C");
        BinaryNode<String> fTree = new BinaryNode<>("F");

        // Subtrees:
        BinaryNode<String> bTree = new BinaryNode<>("B", aTree, cTree);
        BinaryNode<String> eTree = new BinaryNode<>("E", null, fTree);
        BinaryNode<String> gTree = new BinaryNode<>("G", eTree, null);

        System.out.println("\nTree 3a:\n");
        System.out.println("     D      ");
        System.out.println("   /   \\  ");
        System.out.println("  B     G  ");
        System.out.println(" / \\   /  ");
        System.out.println("A   C  E   ");
        System.out.println("        \\ ");
        System.out.println("         F ");
        System.out.println();
        return new BinaryNode<>("D", bTree, gTree);
    } // end createTree3a

    public BinaryNode<String> createTree4() //  D has H as right child
    {
        // Leaves
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> gTree = new BinaryNode<>("G");
        BinaryNode<String> hTree = new BinaryNode<>("H");

        // Subtrees:
        BinaryNode<String> dTree = new BinaryNode<>("D", null, hTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", dTree, eTree);
        BinaryNode<String> cTree = new BinaryNode<>("C", fTree, gTree);

        System.out.println("\nTree 4:\n");
        System.out.println("     A      ");
        System.out.println("   /   \\   ");
        System.out.println("  B     C   ");
        System.out.println(" / \\   / \\");
        System.out.println("D   E  F  G ");
        System.out.println(" \\         ");
        System.out.println("  H         ");
        System.out.println();
        return new BinaryNode<>("A", bTree, cTree);
    } // end createTree4

    public BinaryNode<String> createTree4a()
    {
        // Leaves
        BinaryNode<String> bTree = new BinaryNode<>("B");
        BinaryNode<String> dTree = new BinaryNode<>("D");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> hTree = new BinaryNode<>("H");

        // Subtrees:
        BinaryNode<String> aTree = new BinaryNode<>("A", null, bTree);
        BinaryNode<String> cTree = new BinaryNode<>("C", aTree, dTree);
        BinaryNode<String> gTree = new BinaryNode<>("G", fTree, hTree);

        System.out.println("\nTree 4a:\n");
        System.out.println("     E      ");
        System.out.println("   /   \\   ");
        System.out.println("  C     G   ");
        System.out.println(" / \\   / \\");
        System.out.println("A   D  F  H ");
        System.out.println(" \\         ");
        System.out.println("  B         ");
        System.out.println();
        return new BinaryNode<>("E", cTree, gTree);
    } // end createTree4a

    public BinaryNode<String> createTree5()
    {
        // Leaves
        BinaryNode<String> dTree = new BinaryNode<>("D");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> gTree = new BinaryNode<>("G");
        BinaryNode<String> hTree = new BinaryNode<>("H");

        // Subtrees:
        BinaryNode<String> eTree = new BinaryNode<>("E");
        eTree.setRightChild(hTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", dTree, eTree);
        BinaryNode<String> cTree = new BinaryNode<>("C", fTree, gTree);

        System.out.println("\nTree 5:\n");
        System.out.println("     A      ");
        System.out.println("   /   \\   ");
        System.out.println("  B     C   ");
        System.out.println(" / \\   / \\");
        System.out.println("D   E  F  G ");
        System.out.println("     \\     ");
        System.out.println("      H     ");
        System.out.println();
        return new BinaryNode<>("A", bTree, cTree);
    } // end createTree5

    public BinaryNode<String> createTree5a()
    {
        // Leaves
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> dTree = new BinaryNode<>("D");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> hTree = new BinaryNode<>("H");

        // Subtrees:
        BinaryNode<String> cTree = new BinaryNode<>("C");
        cTree.setRightChild(dTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", aTree, cTree);
        BinaryNode<String> gTree = new BinaryNode<>("G", fTree, hTree);

        System.out.println("\nTree 5a:\n");
        System.out.println("     E      ");
        System.out.println("   /   \\   ");
        System.out.println("  B     G   ");
        System.out.println(" / \\   / \\");
        System.out.println("A   C  F  H ");
        System.out.println("     \\     ");
        System.out.println("      D     ");
        System.out.println();
        return new BinaryNode<>("E", bTree, gTree);
    } // end createTree5a

    public BinaryNode<String> createTree6()
    {
        // Leaves:
        BinaryNode<String> dTree = new BinaryNode<>("D");
        BinaryNode<String> fTree = new BinaryNode<>("F");
        BinaryNode<String> gTree = new BinaryNode<>("G");
        BinaryNode<String> hTree = new BinaryNode<>("H");

        // Subtrees:
        BinaryNode<String> eTree = new BinaryNode<>("E", fTree, gTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", dTree, eTree);
        BinaryNode<String> cTree = new BinaryNode<>("C");
        cTree.setRightChild(hTree);

        System.out.println("\nTree 6:\n");
        System.out.println("     A      ");
        System.out.println("   /   \\   ");
        System.out.println("  B     C   ");
        System.out.println(" / \\     \\");
        System.out.println("D   E     H ");
        System.out.println("   / \\     ");
        System.out.println("  F   G     ");
        System.out.println();
        return new BinaryNode<>("A", bTree, cTree);
    } // end createTree6

    public BinaryNode<String> createTree6a()
    {
        // Leaves:
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> cTree = new BinaryNode<>("C");
        BinaryNode<String> eTree = new BinaryNode<>("E");
        BinaryNode<String> hTree = new BinaryNode<>("H");

        // Subtrees:
        BinaryNode<String> dTree = new BinaryNode<>("D", cTree, eTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", aTree, dTree);
        BinaryNode<String> gTree = new BinaryNode<>("G");
        gTree.setRightChild(hTree);

        System.out.println("\nTree 6a:\n");
        System.out.println("     F      ");
        System.out.println("   /   \\   ");
        System.out.println("  B     G   ");
        System.out.println(" / \\     \\");
        System.out.println("A   D     H ");
        System.out.println("   / \\     ");
        System.out.println("  C   E     ");
        System.out.println();
        return new BinaryNode<>("F", bTree, gTree);
    } // end createTree6a

    public BinaryNode<String> createTree7()
    {
        // Leaves:

        BinaryNode<String> aTree = new BinaryNode<>("A");

        // Subtrees:
        BinaryNode<String> eTree = new BinaryNode<>("E", null, aTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", null, eTree);

        System.out.println("\nTree 7:\n");
        System.out.println("      G      ");
        System.out.println("       \\     ");
        System.out.println("        B     ");
        System.out.println("         \\  ");
        System.out.println("          E   ");
        System.out.println("           \\");
        System.out.println("            A ");
        System.out.println();
        return new BinaryNode<>("G", null, bTree);
    } // end createTree7

    public BinaryNode<String> createTree7a()
    {
        // Leaves:

        BinaryNode<String> gTree = new BinaryNode<>("G");

        // Subtrees:
        BinaryNode<String> eTree = new BinaryNode<>("E", null, gTree);
        BinaryNode<String> bTree = new BinaryNode<>("B", null, eTree);

        System.out.println("\nTree 7a:\n");
        System.out.println("      A      ");
        System.out.println("       \\     ");
        System.out.println("        B     ");
        System.out.println("         \\  ");
        System.out.println("          E   ");
        System.out.println("           \\");
        System.out.println("            G ");
        System.out.println();
        return new BinaryNode<>("A", null, bTree);
    } // end createTree7a

    public BinaryNode<String> createTree8a()
    {
        // Leaves
        BinaryNode<String> bTree = new BinaryNode<>("B");
        BinaryNode<String> fTree = new BinaryNode<>("F");

        System.out.println("\nTree 8a:\n");
        System.out.println("     D      ");
        System.out.println("   /   \\   "); // '\\' is the escape character for backslash
        System.out.println("  B     F   ");
        System.out.println();
        return new BinaryNode<>("D", bTree, fTree);
    } // end createTree8a

    public BinaryNode<String> createTree9a()
    {
        // Leaves
        BinaryNode<String> bTree = new BinaryNode<>("B");

        System.out.println("\nTree 9a:\n");
        System.out.println("     D      ");
        System.out.println("   /        ");
        System.out.println("  B         ");
        System.out.println();
        return new BinaryNode<>("D", bTree, null);
    } // end createTree9a

    public BinaryNode<String> createTree10a()
    {
        // Leaves
        BinaryNode<String> bTree = new BinaryNode<>("B");

        System.out.println("\nTree 10a:\n");
        System.out.println("     D      ");
        System.out.println("   /        ");
        System.out.println("  B         ");
        System.out.println();
        return new BinaryNode<>("D", bTree, null);
    } // end createTree10a

    public BinaryNode<String> createTree11a()
    {
        System.out.println("\nTree 11a:\n");
        System.out.println("     D      ");
        System.out.println();
        return new BinaryNode<String>("D", null, null);
    } // end createTree11a

    public BinaryNode<String> createTree12a()
    {
        // Leaves:
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> cTree = new BinaryNode<>("C");
        BinaryNode<String> eTree = new BinaryNode<>("E");

        // Subtrees:
        BinaryNode<String> dTree = new BinaryNode<>("D", cTree, eTree);

        System.out.println("\nTree 12a:\n");
        System.out.println("     B      ");
        System.out.println("   /   \\   ");
        System.out.println("  A     D   ");
        System.out.println("       / \\ ");
        System.out.println("      C   E ");
        System.out.println();
        return new BinaryNode<>("B", aTree, dTree);
    } // end createTree12a

    public BinaryNode<String> createTree12b()
    {
        // Leaves:
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> dTree = new BinaryNode<>("D");

        // Subtrees:
        BinaryNode<String> eTree = new BinaryNode<>("E", dTree, null);
        BinaryNode<String> cTree = new BinaryNode<>("C", null, eTree);

        System.out.println("\nTree 12b:\n");
        System.out.println("     B      ");
        System.out.println("   /   \\   ");
        System.out.println("  A     C   ");
        System.out.println("         \\ ");
        System.out.println("          E ");
        System.out.println("         /  ");
        System.out.println("        D   ");
        System.out.println();
        return new BinaryNode<>("B", aTree, cTree);
    } // end createTree12b

    public BinaryNode<String> createTree12c()
    {
        // Leaves:
        BinaryNode<String> aTree = new BinaryNode<>("A");
        BinaryNode<String> dTree = new BinaryNode<>("D");

        // Subtrees:
        BinaryNode<String> fTree = new BinaryNode<>("F", dTree, null);
        BinaryNode<String> cTree = new BinaryNode<>("C", null, fTree);

        System.out.println("\nTree 12c:\n");
        System.out.println("     B      ");
        System.out.println("   /   \\   ");
        System.out.println("  A     C   ");
        System.out.println("         \\ ");
        System.out.println("          F ");
        System.out.println("         /  ");
        System.out.println("        D   ");
        System.out.println();
        return new BinaryNode<>("B", aTree, cTree);
    } // end createTree12c

    public BinaryNode<String> createTree12d()
    {
        // Leaves:
        BinaryNode<String> eTree = new BinaryNode<>("E");

        // Subtrees:
        BinaryNode<String> dTree = new BinaryNode<>("D", null, eTree );
        BinaryNode<String> cTree = new BinaryNode<>("C", null, dTree );
        BinaryNode<String> bTree = new BinaryNode<>("B", null, cTree );

        System.out.println("\nTree 12d:\n");
        System.out.println("      A         ");
        System.out.println("       \\       ");
        System.out.println("        B       ");
        System.out.println("         \\     ");
        System.out.println("          C     ");
        System.out.println("           \\   ");
        System.out.println("            D   ");
        System.out.println("             \\ ");
        System.out.println("              E ");
        System.out.println();
        return new BinaryNode<>("A", null, bTree);
    } // end createTree12d

    public BinaryNode<String> createTree12e()
    {
        // Leaves:
        BinaryNode<String> aTree = new BinaryNode<>("A");

        // Subtrees:
        BinaryNode<String> bTree = new BinaryNode<>("B", aTree, null);
        BinaryNode<String> cTree = new BinaryNode<>("C", bTree, null);
        BinaryNode<String> dTree = new BinaryNode<>("D", cTree, null);


        System.out.println("\nTree 12e:\n");
        System.out.println("        E ");
        System.out.println("       /  ");
        System.out.println("      D   ");
        System.out.println("     /    ");
        System.out.println("    C     ");
        System.out.println("   /      ");
        System.out.println("  B       ");
        System.out.println(" /        ");
        System.out.println("A         ");
        System.out.println();
        return new BinaryNode<>("E", dTree, null);
    } // end createTree12e


    public static void main(String[] args)
    {
        WorkingWithBinaryTrees tester = new WorkingWithBinaryTrees<String>();
        BinaryNode<String> aTree = tester.createTree1();
        boolean result = tester.isBST(aTree);
        System.out.print("tree1 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree1a();
        result = tester.isBST(aTree);
        System.out.print("tree1a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");


        aTree = tester.createTree2();
        result = tester.isBST(aTree);
        System.out.print("tree2 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree2a();
        result = tester.isBST(aTree);
        System.out.print("tree2a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree3();
        result = tester.isBST(aTree);
        System.out.print("tree3 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree3a();
        result = tester.isBST(aTree);
        System.out.print("tree3a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree4();
        result = tester.isBST(aTree);
        System.out.print("tree4 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree4a();
        result = tester.isBST(aTree);
        System.out.print("tree4a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree5();
        result = tester.isBST(aTree);
        System.out.print("tree5 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree5a();
        result = tester.isBST(aTree);
        System.out.print("tree5a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree6();
        result = tester.isBST(aTree);
        System.out.print("tree6 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree6a();
        result = tester.isBST(aTree);
        System.out.print("tree6a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree7();
        result = tester.isBST(aTree);
        System.out.print("tree7 is BST = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        aTree = tester.createTree7a();
        result = tester.isBST(aTree);
        System.out.print("tree7a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree8a();
        result = tester.isBST(aTree);
        System.out.print("tree8a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree9a();
        result = tester.isBST(aTree);
        System.out.print("tree9a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree10a();
        result = tester.isBST(aTree);
        System.out.print("tree10a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        aTree = tester.createTree11a();
        result = tester.isBST(aTree);
        System.out.print("tree11a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree));
        } else
            System.out.println(" -> INCORRECT");

        BinaryNode<String> aTree12a = tester.createTree12a();
        result = tester.isBST(aTree12a);
        System.out.print("tree12a is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree12a));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree12a));
        }
        else
            System.out.println(" -> INCORRECT");

        BinaryNode<String> aTree12b = tester.createTree12b();
        result = tester.isBST(aTree12b);
        System.out.print("tree12b is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree12b));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree12b));
        }
        else
            System.out.println(" -> INCORRECT");

        result = tester.areTwoBSTsSame(aTree12a, aTree12b);
        System.out.print("Are elements in tree12a and tree12b the same = " + result);
        if (result)
            System.out.println(" -> CORRECT");
        else
            System.out.println(" -> INCORRECT");

        BinaryNode<String> aTree12c = tester.createTree12c();
        result = tester.isBST(aTree12c);
        System.out.print("tree12c is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree12c));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree12c));
        }
        else
            System.out.println(" -> INCORRECT");

        result = tester.areTwoBSTsSame(aTree12a, aTree12c);
        System.out.print("Are elements in tree12a and tree12c the same = " + result);
        if (result)
            System.out.println(" -> INCORRECT");
        else
            System.out.println(" -> CORRECT");

        BinaryNode<String> aTree12d = tester.createTree12d();
        result = tester.isBST(aTree12d);
        System.out.print("tree12d is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree12d));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree12d));
        }
        else
            System.out.println(" -> INCORRECT");

        result = tester.areTwoBSTsSame(aTree12a, aTree12d);
        System.out.print("Are elements in tree12a and tree12d the same = " + result);
        if (result)
            System.out.println(" -> CORRECT");
        else
            System.out.println(" -> INCORRECT");

        BinaryNode<String> aTree12e = tester.createTree12e();
        result = tester.isBST(aTree12e);
        System.out.print("tree12d is BST = " + result);
        if (result)
        {
            System.out.println(" -> CORRECT");
            System.out.println("The smallest element = " + tester.getSmallest(aTree12e));
            System.out.println("The second largest element = " + tester.getSecondLargest(aTree12e));
        }
        else
            System.out.println(" -> INCORRECT");

        result = tester.areTwoBSTsSame(aTree12a, aTree12e);
        System.out.print("Are elements in tree12a and tree12e the same = " + result);
        if (result)
            System.out.println(" -> CORRECT");
        else
            System.out.println(" -> INCORRECT");

        String[] a = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"};
        System.out.println("\nWorking with tree built from the array: " + Arrays.toString(a));
        BinaryNode<String> root = tester.buildBSTfromSortedArray(a, 0, a.length - 1);
        if (root != null)
        {
            result = tester.isBST(root);
            System.out.println("The tree is BST = " + result);
            System.out.println("The tree has " + root.getNumberOfNodes()
                    + " nodes and the height of " + root.getHeight());
            System.out.println("The tree in level order: ");
            tester.printBSTinLevelOrder(root);

        } else
            System.out.println("The created tree is empty");
    }
}
