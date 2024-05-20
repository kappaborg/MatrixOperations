import java.util.Arrays;
import java.util.Scanner;

public class MatrixOperations {
    public static double determinant(double[][] mat) {
        int kap = mat.length;
        // Determine 1x1 matrix for smaller matrix
        if (kap == 1) return mat[0][0]; 

        double det = 0;
        double[][] submat = new double[kap - 1][kap - 1];

        for (int i = 0; i < kap; ++i) {
            int subi = 0;
            for (int j = 1; j < kap; ++j) {
                int subj = 0;
                for (int k = 0; k < kap; ++k) {
                    if (k == i) continue;
                    submat[subi][subj] = mat[j][k];
                    ++subj;
                }
                ++subi;
            }
            double sign = (i % 2 == 0) ? 1 : -1;
            det += sign * mat[0][i] * determinant(submat);
        }
        return det;
    }

    public static void adjoint(double[][] mat, double[][] adj) {
        int kap = mat.length;
        double[][] temp = new double[kap - 1][kap - 1];

        for (int i = 0; i < kap; ++i) {
            for (int j = 0; j < kap; ++j) {
                int subi = 0;
                for (int ii = 0; ii < kap; ++ii) {
                    if (ii == i) continue;
                    int subj = 0;
                    for (int jj = 0; jj < kap; ++jj) {
                        if (jj == j) continue;
                        temp[subi][subj] = mat[ii][jj];
                        ++subj;
                    }
                    ++subi;
                }
                double sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = sign * determinant(temp);
            }
        }
    }

    public static void inverse(double[][] mat, double[][] inv) {
        int kap = mat.length;
        double det = determinant(mat);
        if (det == 0) {
            System.out.println("Matrix is singular, cannot find inverse.");
            return;
        }
        double[][] adj = new double[kap][kap];
        adjoint(mat, adj);

        for (int i = 0; i < kap; ++i) {
            for (int j = 0; j < kap; ++j) {
                inv[i][j] = adj[i][j] / det;
            }
        }
    }

    public static void displayMatrix(double[][] mat) {
        for (double[] row : mat) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void multiplyMatrices(double[][] a, double[][] b, double[][] result) {
        int m = a.length;
        int n = b[0].length;
        int p = b.length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0;
                for (int k = 0; k < p; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }
    }

    public static void main(String[] args) {
        Scanner kappa = new Scanner(System.in);

        System.out.print("Enter the number of rows for the matrix: ");
        int rows = kappa.nextInt();
        System.out.print("Enter the number of columns for the matrix: ");
        int columns = kappa.nextInt();

        double[][] matrix = new double[rows][columns];

        System.out.println("Enter the matrix elements:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = kappa.nextDouble();
            }
        }

        System.out.println("Original Matrix:");
        displayMatrix(matrix);

        System.out.println("Select the operation:");
        System.out.println("1. Calculate Determinant");
        System.out.println("2. Calculate Adjoint");
        System.out.println("3. Calculate Inverse");
        System.out.println("4. Multiply by Inverse");
        System.out.print("Enter your choice: ");
        int choice = kappa.nextInt();

        switch (choice) {
            case 1:
                double det = determinant(matrix);
                System.out.println("Determinant: " + det);
                break;
            case 2:
                double[][] adjointMatrix = new double[rows][columns];
                adjoint(matrix, adjointMatrix);
                System.out.println("Adjoint:");
                displayMatrix(adjointMatrix);
                break;
            case 3:
                double[][] inverseMatrix = new double[rows][columns];
                inverse(matrix, inverseMatrix);
                System.out.println("Inverse:");
                displayMatrix(inverseMatrix);
                break;
            case 4:
                double[][] inverseMat = new double[rows][columns];
                inverse(matrix, inverseMat);

                System.out.print("Enter the number of rows for the matrix to multiply with the inverse: ");
                int rows2 = kappa.nextInt();
                System.out.print("Enter the number of columns for the matrix to multiply with the inverse: ");
                int columns2 = kappa.nextInt();

                double[][] matrixToMultiply = new double[rows2][columns2];

                System.out.println("Enter the matrix elements to multiply with the inverse:");
                for (int i = 0; i < rows2; i++) {
                    for (int j = 0; j < columns2; j++) {
                        matrixToMultiply[i][j] = kappa.nextDouble();
                    }
                }

                if (columns != rows2) {
                    System.out.println("The number of columns of the first matrix must be equal to the number of rows of the second matrix for multiplication.");
                    break;
                }

                double[][] result = new double[rows][columns2];
                multiplyMatrices(inverseMat, matrixToMultiply, result);
                System.out.println("Result of multiplying by inverse:");
                displayMatrix(result);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        kappa.close();
    }
}
