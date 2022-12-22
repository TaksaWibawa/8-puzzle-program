package Logic;

import java.util.*;

public class Logic {
    int dimension = 3; //dimensi dar 8-puzzle yaitu 3x3
    int[][] initial; //menyimpan nilai dari puzzle yang teracak
    int[][] goal; //menyimpan nilai tujuan dari puzzle
    int[] row = { 1, 0, -1, 0 }; // Bottom, left, top, right
    int[] col = { 0, -1, 0, 1 }; // Bottom, left, top, right

    public Logic(List<Integer> solutionList) { //menginisialisasi constructor yang menerima argument berupa list integer
        initial = new int[][]{ //menginisialisasi initial dengan nilai dari puzzle yang teracak
                {solutionList.get(0), solutionList.get(1), solutionList.get(2)},
                {solutionList.get(3), solutionList.get(4), solutionList.get(5)},
                {solutionList.get(6), solutionList.get(7), solutionList.get(8)}
        };
        goal = new int[][]{ //kondisi tujuan puzzle
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
    }

    public void solve(int[][] initial, int[][] goal, int x, int y){ //method utama penyelesaian 8-puzzle dengan algoritma branch and bound
        PriorityQueue<Node> pq = new PriorityQueue<Node>(10, (a, b) -> (a.cost + a.level) - (b.cost + b.level)); //membuat priority queue untuk tree yang digunakan untuk implementasi branch and bound
        Node root = new Node(initial, x, y, x, y, 0, null); //menginisialisasi pertama kali tree
        root.cost = calculateCost(initial, goal); //menghitung berapa banyak kotak pada puzzle yang tidak sesuai pada tempatnya
        pq.add(root); //menambah root ke dalam list dari live nodes

        while (!pq.isEmpty()) { //selama antrean belum kosong (masih ada live node) maka perulangan terus berjalan
            Node min = pq.peek(); //mencari live node dengan jumlah cost terkecil
            pq.poll(); //digunakan untuk mengambil dan menghapus head dari antrean, yang akan disimpan pada variabel min
            if (min.cost == 0) { //jika nilai cost dari min atau head yang sedang ditunjuk bernilai 0 atau puzzle sudah disusun sesuai tujuan, maka akan mencetak langkah - langkah penyelesaian
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) { //perulangan ini digunakan untuk membuat anakan dari min atau cabang penyelesaiannya berdasarkan cabang dengan cost yang terendah
                if (isSafe(min.x + row[i], min.y + col[i])) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.matrix, goal); //menghitung jumlah cost dari anakan tersebut
                    pq.add(child); //menambah anakan ke dalam list live node
                }
            }
        }
    }

    public int calculateCost(int[][] initial, int[][] goal) { //digunakan untuk mencari cost
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

    //digunakan untuk mengecek apakah matrix(x, y) merupakan koordinat matriks yang valid
    public boolean isSafe(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    public boolean isSolvable(int[][] matrix) { //digunakan untuk mengecek, apakah sususan puzzle teracak saat ini mungkin untuk diselesaikan atau tidak, dengan melihat inversionnya
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
