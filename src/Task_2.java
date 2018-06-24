public class Task_2
{
    public static void Do(Matrix A, Vector d)
    {
        int n = A.GetHeight();

        Vector a = new Vector(n);
        Vector b = new Vector(n);
        Vector c = new Vector(n);

        GetABC(A, a, b, c);

        System.out.println("\nVector a:");
        a.Show();
        System.out.println("\nVector b:");
        b.Show();
        System.out.println("\nVector c:");
        c.Show();

        Vector P = new Vector(n);
        Vector Q = new Vector(n);

        GetPQ(a, b, c, d, P, Q);

        System.out.println("\nVector P:");
        P.Show();
        System.out.println("\nVector Q:");
        Q.Show();

        System.out.println("\nVector x:");
        Solve(P,Q).Show();
    }

    private static void GetABC(Matrix A, Vector a, Vector b, Vector c)
    {
        int n = A.GetHeight();

        a.Set(0, 0);
        b.Set(0, A.Get(0, 0));
        c.Set(0, A.Get(0, 1));

        for(int i=1; i<n-1; i++)
        {
            a.Set(i, A.Get(i, i-1));
            b.Set(i, A.Get(i, i));
            c.Set(i, A.Get(i, i+1));
        }

        a.Set(n-1, A.Get(n-1, n-2));
        b.Set(n-1, A.Get(n-1, n-1));
        c.Set(n-1, 0);
    }

    private static void GetPQ(Vector a, Vector b, Vector c, Vector d, Vector P, Vector Q)
    {
        int n = a.Length();

        P.Set(0, -c.Get(0) / b.Get(0));
        Q.Set(0, d.Get(0) / b.Get(0));

        for(int i=1; i<n-1; i++)
        {
            P.Set(i, -c.Get(i) / (b.Get(i) + a.Get(i)*P.Get(i-1)));
            Q.Set(i, (d.Get(i) - a.Get(i)*Q.Get(i-1)) / (b.Get(i) + a.Get(i)*P.Get(i-1)));
        }

        P.Set(n-1, 0);
        Q.Set(n-1, (d.Get(n-1) - a.Get(n-1)*Q.Get(n-2)) / (b.Get(n-1) + a.Get(n-1)*P.Get(n-2)));
    }

    private static Vector Solve(Vector P, Vector Q)
    {
        int n = P.Length();

        Vector x = new Vector(n);

        x.Set(n-1, Q.Get(n-1));
        for(int i=2; i<=n; i++) x.Set(n-i, P.Get(n-i)*x.Get(n-i+1) + Q.Get(n-i));

        return x;
    }
}
