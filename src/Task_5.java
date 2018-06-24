import javafx.util.Pair;

public class Task_5
{
    public static void Do(Matrix A, double epsilon)
    {
        int k=1;
        do
        {
            Pair<Matrix, Matrix> pair = A.QR();
            Matrix Q = pair.getKey();
            Matrix R = pair.getValue();

            A = Matrix.Multiply(R, Q);
            k++;
        }
        while (!Finish(A,epsilon));

        System.out.println("\nMatrix A:");
        A.Show();
    }

    private static boolean Finish(Matrix matrix, double epsilon)
    {
        int m=matrix.GetLength();
        double sum1, sum2;
        for(int j=0; j<m; j++)
        {
            sum1 = SquareSumColumn(matrix, j, j+1);
            sum2 = SquareSumColumn(matrix, j, j+2);

            //System.out.println("\nsum1: " + sum1);
            //System.out.println("\nsum2: " + sum2);
            if (sum2 > epsilon) return false;
            else if(sum1 <= epsilon)
            {
                System.out.println("lambda" + j + ": " + matrix.Get(j, j));
            }
            else if(sum1 > epsilon)
            {
                if(j==0) return false;

                double aii = matrix.Get(j,j);
                double ajj = matrix.Get(j+1, j+1);
                double aij = matrix.Get(j, j+1);
                double aji = matrix.Get(j+1, j);

                double x = (aii + ajj) / 2;
                double y = Math.sqrt(-(aii+ajj)*(aii+ajj) + 4*(aii*ajj - aij*aji)) / 2;

                System.out.println("lambda" + j + ": " + x + " + " + y +"i");
                System.out.println("lambda" + (j + 1) + ": " + x + " - " + y +"i");
                j++;
            }
        }
        return true;
    }

    private static double SquareSumColumn(Matrix matrix, int column_number, int first_index)
    {

        int n = matrix.GetHeight();

        double sum =0;
        for(int i=first_index; i<n;i++) sum+= matrix.Get(i, column_number) * matrix.Get(i, column_number);

        return Math.sqrt(sum);
    }

}
