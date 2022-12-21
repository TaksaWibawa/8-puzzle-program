package Logic;

import java.util.*;

public class Logic {
    int dimension = 3;
    int[][] initial;
    int[][] goal;
    // Bottom, left, top, right
    int[] row = { 1, 0, -1, 0 };
    int[] col = { 0, -1, 0, 1 };

    public Logic(List<Integer> solutionList) {
        initial = new int[][]{
                {solutionList.get(0), solutionList.get(1), solutionList.get(2)},
                {solutionList.get(3), solutionList.get(4), solutionList.get(5)},
                {solutionList.get(6), solutionList.get(7), solutionList.get(8)}
        };
        goal = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
    }

    public void solve(int[][] initial, int[][] goal, int x, int y) throws Exception {
        PriorityQueue<Node> pq = new PriorityQueue<Node>(10, (a, b) -> (a.cost + a.level) - (b.cost + b.level));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = calculateCost(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
            if (min.cost == 0) {
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i])) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.matrix, goal);
                    pq.add(child);
                }
            }
        }
    }

    public int calculateCost(int[][] initial, int[][] goal) {
        int count = 0;
        int n = initial.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        printMatrix(root.matrix);
        System.out.println();
    }

    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        SliderPuzzle.addClick();
    }

    public boolean isSafe(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    public boolean isSolvable(int[][] matrix) {
        int linearPuzzle[] = new int[9];
        int k = 0;

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix.length; j++){
                linearPuzzle[k++] = matrix[i][j];
            }
        }

        int invCount = getInvCount(linearPuzzle);
        return invCount % 2 == 0;
    }

    private int getInvCount(int[] linearPuzzle) {
        int invCount = 0;
        for(int i = 0; i < 9; i++){
            for(int j = i + 1; j < 9; j++){
                if(linearPuzzle[i] > 0 && linearPuzzle[j] > 0 && linearPuzzle[i] > linearPuzzle[j]){
                    invCount++;
                }
            }
        }
        return invCount;
    }
}
