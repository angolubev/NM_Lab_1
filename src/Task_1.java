public class Task_1
{
    // Решение СЛАУ при помощи LU разложения
    public static void Do(Matrix A, Vector b)
    {
        int n = A.GetHeight();

        System.out.println("\nMatrix A:");
        A.Show();

        Matrix U = new Matrix(n, n);
        Matrix L = new Matrix(A);

        A.LU(L, U);

        System.out.println("\nMatrix L:");
        L.Show();

        System.out.println("\nMatrix U:");
        U.Show();

        System.out.println("\nVector x:");
        Solve(L, U, b).Show();

        System.out.println("\nDet A: " + Determinant(U));

        System.out.println("\nInverse Matrix:");
        InverseMatrix(L, U).Show();
    }

    public static Vector Solve(Matrix L, Matrix U, Vector b)
    {
        int n = L.GetHeight();

        // Решаем СЛАУ Lz = b
        Vector z = new Vector(n);
        z.Set(0, b.Get(0));

        for(int i=1; i<n; i++)
        {
            double sum = 0;
            for(int j=0; j<i; j++) sum += L.Get(i, j) * z.Get(j);
            z.Set(i, b.Get(i) - sum);
        }

        // Решаем СЛАУ Ux = z
        Vector x = new Vector(n);
        x.Set(n-1, z.Get(n-1) / U.Get(n-1, n-1));

        for(int i=n-2; i>=0; i--)
        {
            double sum = 0;
            for(int j=i+1; j<n;j++) sum += U.Get(i, j) * x.Get(j);
            x.Set(i, (z.Get(i) - sum) / U.Get(i, i));
        }

        return x;
    }

    // Нахождение обратной матрицы, зная разложение L U
    private static Matrix InverseMatrix(Matrix L, Matrix U)
    {
        int n = L.GetHeight();

        Matrix E = Matrix.GetSingularMatrix(n);
        Matrix X = new Matrix(n, n);

        for(int i=0; i<n; i++)
        {
            Vector y = Solve(L, E, E.GetLine(i));
            Vector x = Solve(E, U, y);
            for(int j=0; j<n; j++) X.Set(j, i, x.Get(j));
        }

        return X;
    }

    // определитель треугольной матрицы (произведение диагональных элементов)
    private static double Determinant(Matrix triangle_matrix)
    {
        double det = 1;
        for(int i=0; i<triangle_matrix.GetLength(); i++) det*= triangle_matrix.Get(i, i);
        return det;
    }

}
