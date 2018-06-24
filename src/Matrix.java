import javafx.util.Pair;

public class Matrix
{
    private int n;
    private int m;
    private double[][] matrix;

    public Matrix(int n, int m)
    {
        this.n=n;
        this.m=m;
        matrix = new double[n][m];
    }

    public Matrix(int n, int m, double[][] matrix)
    {
        this.n=n;
        this.m=m;
        this.matrix = matrix;
    }

    public Matrix(Matrix matrix)
    {
        this.n = matrix.GetHeight();
        this.m = matrix.GetLength();

        this.matrix = new double[n][m];
        for (int i=0; i<n; i++)
        {
            for (int j=0; j<m; j++)
            {
                this.matrix[i][j] = matrix.Get(i, j);
            }
        }
    }

    public int GetLength()
    {
        return m;
    }

    public int GetHeight()
    {
        return n;
    }

    public double Get(int i, int j)
    {
        return matrix[i][j];
    }

    public void Set(int i, int j, double value)
    {
        matrix[i][j] = value;
    }

    public Vector GetLine(int i)
    {
        return new Vector(m, matrix[i]);
    }

    public Vector GetColumn(int j)
    {
        Vector res = new Vector(m);
        for(int i=0; i<n; i++) res.Set(i, this.Get(i, j));
        return res;
    }

    public void Increment(int i, int j, double value)
    {
        matrix[i][j] += value;
    }

    public static Matrix Multiply(Matrix a, double digit)
    {
        int n = a.GetHeight();
        int m = a.GetLength();
        Matrix res = new Matrix(n, m);

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<m; j++)
            {
                res.Set(i, j, a.Get(i, j)*digit);
            }
        }
        return res;
    }

    public static Matrix Multiply(Matrix a, Matrix b) throws ArithmeticException
    {
        if (a.GetLength() != b.GetHeight()) throw new ArithmeticException("wrong dimensions");

        int n = a.GetHeight();
        int m = b.GetLength();

        Matrix res = new Matrix(n, m);

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<m; j++)
            {
                res.Set(i, j,0);
                for(int k = 0; k < n; k++) res.Increment(i, j, a.Get(i, k) * b.Get(k, j));
            }
        }

        return res;
    }

    public static Vector Multiply(Matrix matrix, Vector vector) throws ArithmeticException
    {
        if(matrix.GetLength() != vector.Length()) throw new ArithmeticException("wrong dimensions");

        int n = matrix.GetHeight();

        Vector res = new Vector(n);

        for(int i=0; i<n; i++)
        {
            //res[i] = 0;
            res.Set(i, 0);
            //for(int j=0; j<n; j++) res[i]+=matrix[i][j]*vector[j];
            for(int j=0; j<n; j++) res.Increment(i, matrix.Get(i, j)*vector.Get(j));
        }

        return res;
    }

    public static Matrix Add(Matrix a, Matrix b) throws ArithmeticException
    {
        int n = a.GetHeight();
        int m = a.GetLength();

        if((n != b.GetHeight()) || (m != b.GetLength())) throw new ArithmeticException("wrong dimensions");

        Matrix res = new Matrix(n, m);

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<m; j++)
            {
                res.Set(i,j, a.Get(i,j) + b.Get(i, j));
            }
        }

        return res;
    }

    public void Show()
    {
        for (double[] i: matrix)
        {
            for(double j: i)
            {
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }

    public double Norma()
    {
        double max = 0;
        for(double[] string : matrix)
        {
            double sum = 0;
            for (double s: string) sum+=Math.abs(s);
            if(max < sum) max = sum;
        }
        return max;
    }

    public void LU(Matrix L, Matrix U) throws ArithmeticException
    {
        if(m != n) throw new ArithmeticException("wrong dimensions");

        //U = new Matrix(this);

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < m; j++)
            {
                //U[0][i] = A[0][i];
                //L[i][0] = A[i][0] / U[0][0];

                U.Set(0, i, this.Get(0, i));
                L.Set(i, 0, this.Get(i, 0) / U.Get(0, 0));

                double sum = 0;
                //for (int k = 0; k < i; k++) sum += L[i][k] * U[k][j];
                for (int k = 0; k < i; k++) sum += L.Get(i, k) * U.Get(k, j);

                //U[i][j] = A[i][j] - sum;
                U.Set(i, j, this.Get(i, j) - sum);

                //if (i > j) L[j][i] = 0;
                if (i > j) L.Set(j, i, 0);
                else
                {
                    sum = 0;
                    //for (int k = 0; k < i; k++) sum += L[j][ k] * U[k][ i];
                    for (int k = 0; k < i; k++) sum += L.Get(j, k) * U.Get(k, i);
                    //L[j][i] = (A[j][ i] - sum) / U[i][ i];
                    L.Set(j, i, (this.Get(j, i) - sum) / U.Get(i, i));
                }
            }
        }
    }

    public static Matrix GetSingularMatrix(int n)
    {
        Matrix E = new Matrix(n, n);

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                if(i==j) E.Set(i, j, 1);
                else E.Set(i, j, 0);
            }
        }

        return E;
    }

    public Matrix Transpone()
    {
        Matrix transpone = new Matrix(this);
        double temp;

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                temp = transpone.Get(i,j);
                transpone.Set(i, j, transpone.Get(j, i));
                transpone.Set(j, i, temp);
            }
        }

        return transpone;
    }

    public Pair<Matrix, Matrix> QR()
    {
        Matrix R = new Matrix(this);
        Matrix Q = GetSingularMatrix(n);
        Vector v = new Vector(n);

        for(int k=0; k<n-1; k++)
        {
            for(int i=0; i<n; i++)
            {
                if(i<k) v.Set(i,0);
                else if (i == k)
                {
                    double norma=0;
                    Vector column = R.GetColumn(k);
                    for(int j=k; j<n; j++) norma += column.Get(j) * column.Get(j);
                    norma = Math.sqrt(norma);

                    v.Set(i, R.Get(i, i) + Math.signum(R.Get(i, i)) * norma);
                }
                else if (i > k) v.Set(i, R.Get(i, k));
            }

            Matrix temp = new Matrix(n, n);
            double p=0;
            for(int i=0; i<n; i++)
            {
                for(int j=0; j<n; j++)
                {
                    temp.Set(i, j, v.Get(i) * v.Get(j));
                }
                p+=v.Get(i)*v.Get(i);
            }

            p = -2 / p;
            Matrix H = Add(GetSingularMatrix(n), Multiply(temp, p));

            R = Matrix.Multiply(H, R);
            Q = Matrix.Multiply(Q, H);
        }

        return new Pair<>(Q, R);
    }
}
