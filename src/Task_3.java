public class Task_3
{
    public static void Do(Matrix A, Vector b, double epsilon)
    {
        int n = A.GetHeight();

        Matrix Alpha = new Matrix(n, n);
        Vector Beta = new Vector(n);

        FindAlphaBeta(A, b, Alpha, Beta);

        System.out.println("\nMatrix Alpha:");
        Alpha.Show();
        System.out.println("\nVector Beta:");
        Beta.Show();

        System.out.println("\nIteration method:");
        IterationMethod(Alpha, Beta, epsilon).Show();

        System.out.println("\nZeidel method:");
        ZeidelMethod(Alpha, Beta, epsilon).Show();

    }

    private static void FindAlphaBeta(Matrix A, Vector b, Matrix Alpha, Vector Beta)
    {
        int n = A.GetHeight();

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                Beta.Set(i, b.Get(i) / A.Get(i, i));
                if(i != j) Alpha.Set(i, j, -A.Get(i, j) / A.Get(i, i));
                else Alpha.Set(i, j, 0);
            }
        }
    }

    private static Vector IterationMethod(Matrix Alpha, Vector Beta, double epsilon)
    {
        Vector x = new Vector(Beta);

        Vector prev_x;
        double norma_alpha = Alpha.Norma();
        double epsilon_i;

        int k = 1;
        do
        {
            Vector m = Matrix.Multiply(Alpha, x);

            prev_x = new Vector(x);
            x = Vector.Add(Beta, m);

            epsilon_i = (norma_alpha / (1-norma_alpha)) * Vector.Sub(x, prev_x).Norma();

            k++;

        }
        while (epsilon_i > epsilon);

        System.out.println("\nIterations: " + k);
        return x;
    }

    private static void GetBC(Matrix Alpha, Matrix B, Matrix C)
    {
        int n = Alpha.GetHeight();

        for(int i=0; i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(j<i)
                {
                    B.Set(i, j, Alpha.Get(i, j));
                    C.Set(i, j, 0);
                }
                else
                {
                    B.Set(i, j, 0);
                    C.Set(i, j, Alpha.Get(i, j));
                }
            }
        }
    }

    private static Matrix InverseTriangleMatrix(Matrix triangle_matrix)
    {
        int n = triangle_matrix.GetHeight();

        Matrix res = new Matrix(n, n);

        Matrix E = Matrix.GetSingularMatrix(n);

        for(int i=0; i<n; i++)
        {
            Vector x = Task_1.Solve(triangle_matrix, E, E.GetLine(i));
            for(int j=0; j<n; j++) res.Set(j, i, x.Get(j));
        }

        return res;
    }

    private static Vector ZeidelMethod(Matrix Alpha, Vector Beta, double epsilon)
    {
        int n = Alpha.GetHeight();

        Vector x = new Vector(Beta);

        Matrix B = new Matrix(n, n);
        Matrix C = new Matrix(n, n);

        GetBC(Alpha, B, C);

        for(int i=0; i<n;i++)
        {
            for(int j=0; j<n; j++)
            {
                if(i==j) B.Set(i, j, 1);
                else if(j<i) B.Set(i, j, -B.Get(i, j));
            }
        }

        Matrix D = InverseTriangleMatrix(B);

        double epsilon_i;
        Vector prev_x;

        int k=1;
        do
        {
            Vector x1 = Matrix.Multiply(Matrix.Multiply(D, C), x);
            Vector x2 = Matrix.Multiply(D, Beta);

            prev_x = new Vector(x);
            x = Vector.Add(x1, x2);

            epsilon_i = (C.Norma() / (1 - Alpha.Norma())) * Vector.Sub(x, prev_x).Norma();
            k++;
        }
        while (epsilon_i > epsilon);

        System.out.println("\nIterations: " + k);

        return x;
    }
}
