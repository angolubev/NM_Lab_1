public class NM_Lab_1
{
    public static void main(String[] args)
    {
        double[][] A1 = { {8, 8, -5, -8}, {8, -5, 9, -8}, {5, -4, -6, -2}, {8, 3, 6, 6}};
        double[] b1 = {13, 38, 14, -95};
        //Task_1.Do(new Matrix(4, 4, A1), new Vector(4, b1));

        double[][] A2 = { {-6, 5, 0, 0, 0}, {-1, 13, 6, 0, 0}, {0, -9, -15, -4, 0}, {0, 0, -1, -7, 1}, {0, 0, 0, 9, -18}};
        double[] b2 = {51, 100, -12, 47, 90};
        //Task_2.Do(new Matrix(5, 5, A2), new Vector(5, b2));

        double[][] A3 = { {-19, 2, -1, -8}, {2, 14, 0, -4}, {6, -5, -20, -6}, {-6, 4, -2, 15}};
        double[] b3 = {38, 20, 52, 43};
        double epsilon3 = 0.0001;
        //Task_3.Do(new Matrix(4, 4, A3), new Vector(4, b3), epsilon3);

        double[][] A4 = {{5, -3, -4}, {-3, -3, 4}, {-4, 4, 0}};
        double epsilon4 = 0.1;
        //Task_4.Do(new Matrix(3, 3, A4), epsilon4);

        //double[][] A5 = {{1, 3, 1}, {1, 1, 4}, {4, 3, 1}};
        //double epsilon5 = 0.02;
        double[][] A5 = {{-6, 1, -4}, {-6, 8, -2}, {2, -9, 5}};
        double epsilon5 = 0.01;
        Task_5.Do(new Matrix(3,3, A5), epsilon5);
    }
}
