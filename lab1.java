import java.util.Random;

public class lab1{

    public static void main(String[] args) {
        try {
            final int rows = 4;
            final int cols = 5;
            final int a = 2; // константа
            char[][] B = new char[rows][cols];
            int[][] C = new int[rows][cols];
            Random rand = new Random();

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    B[i][j] = (char) ('A' + rand.nextInt(26)); // A-Z
                }
            }

            System.out.println("Matrix B (char):");
            for (char[] row : B) {
                for (char c : row) {
                    System.out.print(c + " ");
                }
                System.out.println();
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    C[i][j] = a * B[i][j];
                }
            }

            System.out.println("\nMatrix C = a * B:");
            for (int[] row : C) {
                for (int val : row) {
                    System.out.print(val + " ");
                }
                System.out.println();
            }

            int sum = 0;
            for (int i = 0; i < rows; i++) {
                int min = C[i][0];
                int max = C[i][0];
                for (int j = 1; j < cols; j++) {
                    if (C[i][j] < min) min = C[i][j];
                    if (C[i][j] > max) max = C[i][j];
                }

                if ((i + 1) % 2 == 0) {
                    sum += min;
                } else {
                    sum += max;
                }
            }

            System.out.println("\nSum of max (odd rows) and min (even rows): " + sum);

        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
