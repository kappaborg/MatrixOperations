import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MatrixOperationsApp extends JFrame implements ActionListener {
    private JTextField[][] matrixFields;
    private JButton createMatrixBtn, determinantBtn, adjointBtn, inverseBtn, multiplyBtn, clearBtn;
    private JTextArea matrixDisplayArea, resultArea;

    public MatrixOperationsApp() {
        //Our frame name
        setTitle("Matrix Operations");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(128, 128, 128, 200)); // Set frame background to transparent gray

        JLabel infoLabel = new JLabel("Enter the number of rows and columns and click 'Create Matrix':");
        //For each setBound we create layout positions for each elements and buttons
        infoLabel.setBounds(10, 10, 500, 20);
        add(infoLabel);

        JLabel rowsLabel = new JLabel("Rows:");
        rowsLabel.setBounds(10, 40, 50, 20);
        add(rowsLabel);

        JTextField rowsField = new JTextField();
        rowsField.setBounds(60, 40, 50, 20);
        rowsField.setToolTipText("Enter the number of rows for the matrix");
        add(rowsField);

        JLabel colsLabel = new JLabel("Cols:");
        colsLabel.setBounds(120, 40, 50, 20);
        add(colsLabel);

        JTextField colsField = new JTextField();
        colsField.setBounds(170, 40, 50, 20);
        colsField.setToolTipText("Enter the number of columns for the matrix");
        add(colsField);

        //We are creating button to creating matrix
        createMatrixBtn = new JButton("Create Matrix");
        createMatrixBtn.setBounds(230, 40, 150, 20);
        createMatrixBtn.setToolTipText("Create a matrix with the specified number of rows and columns");
        createMatrixBtn.addActionListener(e -> createMatrix(Integer.parseInt(rowsField.getText()), Integer.parseInt(colsField.getText())));
        add(createMatrixBtn);

        //We are creating button to display matrix
        matrixDisplayArea = new JTextArea();
        matrixDisplayArea.setBounds(10, 70, 100, 150);
        matrixDisplayArea.setEditable(false);
        add(matrixDisplayArea);

        //We are creating information panel for matrix
        infoLabel = new JLabel("Input Matrix Values Here");
        infoLabel.setBounds(10, 220, 250, 40);
        infoLabel.setBackground(Color.ORANGE);
        infoLabel.setOpaque(true);
        //If you want thicker border increase thickness1
        float thickness1 = 5;
        float radius1 = 10;
        boolean roundedCorners1 = true;
        Border roundedBorder1 = new CompoundBorder(new LineBorder(Color.BLACK, (int) thickness1), new RoundedBorder(Color.BLACK, thickness1, radius1, roundedCorners1));
        add(infoLabel);
        infoLabel.setBorder(roundedBorder1);

        //We are creating button to get determinant of entered matrix
        determinantBtn = new JButton("Calculate Determinant");
        determinantBtn.setBounds(10, 500, 180, 30);
        determinantBtn.addActionListener(this);
        determinantBtn.setToolTipText("Calculate the determinant of the input matrix");
        add(determinantBtn);

        //We are creating button to get adjoint of entered matrix
        adjointBtn = new JButton("Calculate Adjoint");
        adjointBtn.setBounds(200, 500, 150, 30);
        adjointBtn.addActionListener(this);
        adjointBtn.setToolTipText("Calculate the adjoint of the input matrix");
        add(adjointBtn);

        //We are creating button to calculate inverse of entered matrix
        inverseBtn = new JButton("Calculate Inverse");
        inverseBtn.setBounds(10, 470, 150, 30);
        inverseBtn.addActionListener(this);
        inverseBtn.setToolTipText("Calculate the inverse of the input matrix");
        add(inverseBtn);

        //We are creating button to multiply matrix by inverse
        multiplyBtn = new JButton("Multiply by Inverse");
        multiplyBtn.setBounds(200, 470, 150, 30);
        multiplyBtn.addActionListener(this);
        multiplyBtn.setToolTipText("Multiply the input matrix by its inverse");
        add(multiplyBtn);

        //Here we set clear button to refresh matrix
        clearBtn = new JButton("Clear");
        clearBtn.setBounds(400, 35, 80, 30);
        clearBtn.addActionListener(this);
        clearBtn.setToolTipText("Clear the input matrix and result area");
        add(clearBtn);

        //I added my instagram link here. You can also add to your intagram for personal use
        JButton instaBtn = new JButton("Instagram");
        instaBtn.setBounds(400, 60, 100, 30);
        instaBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        try {
            //You can add your instagram or web page url in URI
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/kappasutra/"));
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
});
        add(instaBtn);

        //Here I add some source web page for matrix equations
        JButton sourceBtn = new JButton("Web Example");
        sourceBtn.setBounds(400,85,110,30);
        sourceBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    Desktop.getDesktop().browse(new URI("https://matrix.reshish.com/matrixMethod.php"));
                } catch (IOException | URISyntaxException ex){
                    ex.printStackTrace();
                }
            }
        });
        add(sourceBtn);

        JPanel middlePanel=new JPanel();
        middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        middlePanel.add(scroll);
        resultArea.setBounds(400, 150, 460, 200);
        resultArea.setBackground(Color.ORANGE);
        resultArea.setVisible(true);
        resultArea.setOpaque(true); 
        resultArea.setEditable(false);
        add(resultArea);
        //Here we are creating border radius 
        float thickness = 9;
        float radius = 20;
        boolean roundedCorners = true;
        Border roundedBorder = new CompoundBorder(new LineBorder(Color.BLACK, (int) thickness), new RoundedBorder(Color.BLACK, thickness, radius, roundedCorners));
        resultArea.setBorder(roundedBorder);
        setVisible(true);
    }
    //From this part there we are functions for our buttons

    private void createMatrix(int rows, int cols) {
        matrixFields = new JTextField[rows][cols];
        StringBuilder matrixDisplay = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JTextField field = new JTextField();
                //Matrix input area localization
                field.setBounds(10 + j * 50, 290 + i * 30, 40, 20);
                field.setToolTipText("Enter the value for row " + (i + 1) + " and column " + (j + 1));
                matrixFields[i][j] = field;
                add(field);
                matrixDisplay.append("0 ");
            }
            matrixDisplay.append("\n");
        }
        matrixDisplayArea.setText(matrixDisplay.toString());
        repaint();
    }

    private double[][] getMatrixFromFields() {
        int rows = matrixFields.length;
        int cols = matrixFields[0].length;
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Double.parseDouble(matrixFields[i][j].getText());
            }
        }
        return matrix;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == determinantBtn || e.getSource() == adjointBtn || e.getSource() == inverseBtn || e.getSource() == multiplyBtn) {
            double[][] matrix = getMatrixFromFields();
            if (matrix == null) {
                JOptionPane.showMessageDialog(this, "Please create a matrix first.");
                return;
            }

            if (e.getSource() == determinantBtn) {
                double det = MatrixOperations.determinant(matrix);
                resultArea.setText("Determinant: " + det);
            } else if (e.getSource() == adjointBtn) {
                double[][] adjointMatrix = new double[matrix.length][matrix[0].length];
                MatrixOperations.adjoint(matrix, adjointMatrix);
                resultArea.setText("Adjoint:\n" + MatrixOperations.matrixToString(adjointMatrix));
            } else if (e.getSource() == inverseBtn) {
                double[][] inverseMatrix = new double[matrix.length][matrix[0].length];
                MatrixOperations.inverse(matrix, inverseMatrix);
                resultArea.setText("Inverse:\n" + MatrixOperations.matrixToString(inverseMatrix));
            } else if (e.getSource() == multiplyBtn) {
                double[][] inverseMatrix = new double[matrix.length][matrix[0].length];
                MatrixOperations.inverse(matrix, inverseMatrix);
                double[][] matrixToMultiply = getMatrixFromFields(); // Assuming user enters the second matrix
                double[][] result = new double[matrix.length][matrixToMultiply[0].length];
                MatrixOperations.multiplyMatrices(inverseMatrix, matrixToMultiply, result);
                resultArea.setText("Result of multiplying by inverse:\n" + MatrixOperations.matrixToString(result));
            }
        } else if (e.getSource() == clearBtn) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the matrix and result area?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                clearFields();
            }
        }
    }

    private void clearFields() {
        matrixDisplayArea.setText("");
        resultArea.setText("");
        if (matrixFields != null) {
            for (JTextField[] row : matrixFields) {
                for (JTextField field : row) {
                    remove(field);
                }
            }
        }
        matrixFields = null;
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatrixOperationsApp::new);
    }
}

class MatrixOperations {
    public static double determinant(double[][] mat) {
        int kap = mat.length;
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

    public static String matrixToString(double[][] mat) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : mat) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
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
}
//For button and needed area borders
class RoundedBorder implements Border {
    private final Color color;
    private final float thickness;
    private final float radius;
    private final boolean roundedCorners;

    public RoundedBorder(Color color, float thickness, float radius, boolean roundedCorners) {
        this.color = color;
        this.thickness = thickness;
        this.radius = radius;
        this.roundedCorners = roundedCorners;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (roundedCorners) {
            g2d.draw(new RoundRectangle2D.Float(x, y, width - 1, height - 1, radius, radius));
        } else {
            g2d.draw(new Rectangle2D.Float(x, y, width - 1, height - 1));
        }
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets((int) thickness, (int) thickness, (int) thickness, (int) thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
