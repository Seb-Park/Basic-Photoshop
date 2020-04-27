import java.util.Arrays;

public class Matrix {
    public double[][] values;
    public int height, width;

    public Matrix(int pHeight, int pWidth) {
        height = pHeight;
        width = pWidth;
        values = new double[height][width];
    }

    public Matrix(double[][] pValues) {
        values = pValues;
        height = values.length;
        width = values[0].length;
    }

    public Matrix(double a, double b, double c) {
        values = new double[][]{{a,b,c}};
        height = values.length;
        width = values[0].length;
    }

    public static Matrix multiplyMatricies(Matrix m1, Matrix m2) {
//        System.out.println(m1.height + " * " + m1.width);
        double returnableDoubles[][] = new double[m2.width][m1.height];
        if (m1.width == m2.height) {
            for (int i = 0; i < m1.height; i++) {
                for (int j = 0; j < m2.width; j++) {
                    double currentValue = 0;
                    for (int k = 0; k < m1.width; k++) {
//                        System.out.println(m1.values[i][k] * m2.values[k][j]);
                        currentValue+=m1.values[i][k] * m2.values[k][j];
                    }
                    returnableDoubles[i][j]=currentValue;
                    System.out.println(currentValue);
                }
            }
        }
        System.out.println(Arrays.deepToString(returnableDoubles));
        return null;
    }
}
