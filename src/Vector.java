public class Vector
{
    private int n;
    private double[] vector;

    public Vector(int n)
    {
        this.n = n;
        vector = new double[n];
    }

    public Vector(int n, double[] v)
    {
        this.n = n;
        vector = v;
    }

    public Vector(Vector vector)
    {
        this.n = vector.Length();
        this.vector = new double[n];

        for(int i=0; i<n; i++) this.vector[i] = vector.Get(i);
    }

    public int Length()
    {
        return n;
    }

    public double Get(int i)
    {
        return vector[i];
    }

    public void Set(int i, double value)
    {
        vector[i] = value;
    }

    public void Increment(int i, double value)
    {
        vector[i] += value;
    }

    public void Show()
    {
        for (double i : vector)
        {
            System.out.println(i + " ");
        }
    }

    public static Vector Add(Vector vector1, Vector vector2) throws ArithmeticException
    {
        if(vector1.Length() != vector2.Length()) throw new ArithmeticException("wrong dimensions");

        int n = vector1.Length();

        Vector res = new Vector(n);

        for (int i=0; i<n; i++) res.Set(i, vector1.Get(i) + vector2.Get(i));

        return res;
    }

    public static Vector Sub(Vector vector1, Vector vector2) throws ArithmeticException
    {
        if(vector1.Length() != vector2.Length()) throw new ArithmeticException("wrong dimensions");

        int n = vector1.Length();

        Vector res = new Vector(n);

        for (int i=0; i<n; i++) res.Set(i, vector1.Get(i) - vector2.Get(i));

        return res;
    }

    public double Norma()
    {
        double max = vector[0];

        for (double i: vector)
        {
            double abs = Math.abs(i);
            if(max < abs) max = abs;
        }

        return max;
    }

    public double EuclidNorma()
    {
        double sum=0;
        for (double i: vector) sum+=i*i;

        return Math.sqrt(sum);
    }
}
