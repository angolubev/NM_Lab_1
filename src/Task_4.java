import javafx.util.Pair;

public class Task_4
{
    public static void Do(Matrix A, double epsilon)
    {
        int n = A.GetHeight();

        Vector lambda = new Vector(n);
        Matrix X = JakobiMethod(A, epsilon, lambda);

        System.out.println("\nVector lambda:");
        lambda.Show();
        System.out.println("\nMatrix X:");
        X.Show();
    }

    public static Matrix JakobiMethod(Matrix A, double epsilon, Vector lambda)
    {
        int n = A.GetHeight();
        Matrix X = Matrix.GetSingularMatrix(n);
        double p;
        double angle;

        do
        {
            Pair<Integer, Integer> pair = FindMax(A);
            int i = pair.getKey();
            int j = pair.getValue();

            angle = FindAngle(A, i, j);

            double sin = Math.sin(angle);
            double cos = Math.cos(angle);

            Matrix U = Matrix.GetSingularMatrix(n);
            U.Set(i, i, cos);
            U.Set(i, j, -sin);
            U.Set(j, i, sin);
            U.Set(j, j, cos);

            X = Matrix.Multiply(X, U);

            //Matrix temp = U.Transpone();
            Matrix temp = new Matrix(U);
            temp.Set(i, j, sin);
            temp.Set(j, i, -sin);

            Matrix temp1 = Matrix.Multiply(temp, A);
            A = Matrix.Multiply(temp1, U);

            //A = Matrix.Multiply(Matrix.Multiply(U.Transpone(), A), U);
            p = SquareSum(A);
        }
        while ((epsilon < p) && (angle != 0));

        for(int i=0; i<n; i++) lambda.Set(i, A.Get(i, i));

        return X;
    }

    private static Pair<Integer, Integer> FindMax(Matrix matrix)
    {
        Pair<Integer, Integer> pair = new Pair<>(0, 1);

        int n = matrix.GetHeight();
        double max = Math.abs(matrix.Get(0,1));

        for(int i=0; i<n; i++)
        {
            for (int j=i+1; j<n; j++)
            {
                if(max < Math.abs(matrix.Get(i, j)))
                {
                    max = Math.abs(matrix.Get(i, j));
                    //_i = i;
                    //_j = j;
                    pair = new Pair<>(i, j);
                }
            }
        }

        return pair;
    }

    private static double FindAngle(Matrix A, int i, int j)
    {
        if(A.Get(i, i) == A.Get(j, j)) return Math.PI / 4;
        else return 0.5 * Math.atan(2 * A.Get(i, j) / (A.Get(i, i) - A.Get(j, j)));
    }

    private static double SquareSum(Matrix matrix)
    {
        int n = matrix.GetHeight();
        double sum=0;

        for(int i=1; i<n; i++)
        {
            for(int j=0; j<i; j++)
            {
                sum += matrix.Get(i, j) * matrix.Get(i, j);
            }
        }

        return Math.sqrt(sum);
    }
}



















