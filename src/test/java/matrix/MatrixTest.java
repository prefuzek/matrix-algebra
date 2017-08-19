package matrix;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import vector.MyVector;

/**
 * Created by Zach on 2017-05-15.
 */
public class MatrixTest {
    int[][] vId = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    int[][] vZero = {{0,0,0},{0,0,0},{0,0,0}};
    int[][] vMain = {{1, 2, 3}, {2, 4, 0}, {-1, 3, 5}};
    int[][] vSwap = {{-1, 3, 5}, {2, 4, 0}, {1, 2, 3}};
    int[][] vScale = {{1, 2, 3}, {2, 4, 0}, {-3, 9, 15}};
    int[][] vRowAdd = {{5, 10, 3}, {2, 4, 0}, {-1, 3, 5}};
    int[][] vSquared = {{2, 19, 18}, {10, 20, 6}, {0, 25, 22}};
    int[][] vSingular = {{1, 2, 3}, {0, 4, -2}, {2, 8, 4}};
    private SquareMatrix mId, mZero, mMain, mSwap, mScale, mRowAdd, mSquared, mSingular;
    private Matrix mTest;

    int[] vSolution = {2, -1, 3};
    MyVector solution = new MyVector(vSolution, 3);

    @BeforeMethod
    public void setup() {
        mId = new SquareMatrix(vId, 3);
        mZero = new SquareMatrix(vZero, 3);
        mMain = new SquareMatrix(vMain, 3);
        mSwap = new SquareMatrix(vSwap, 3);
        mScale = new SquareMatrix(vScale, 3);
        mRowAdd = new SquareMatrix(vRowAdd, 3);
        mSquared = new SquareMatrix(vSquared, 3);
        mSingular = new SquareMatrix(vSingular, 3);
        mTest = mSingular.augment(solution);
    }

    @Test
    public void basicTests() {
        Assert.assertTrue(mId.equals(Matrix.identity(3)));
        Assert.assertTrue(mZero.equals(new SquareMatrix(3)));
        Assert.assertTrue(mMain.equals(mMain.mult(mId)));
    }

    @Test
    public void swapTest() {
        Assert.assertTrue(mMain.switchRows(0,2).equals(mSwap));
    }

    @Test
    public void scaleRowTest() {
        Assert.assertTrue(mMain.scaleRow(2, 3).equals(mScale));
    }

    @Test
    public void addRowTest() {
        Assert.assertTrue(mMain.addRow(0, 1, 2).equals(mRowAdd));
    }

    @Test
    public void multTest() {
        Assert.assertTrue(mMain.mult(mMain).equals(mSquared));
    }
    
    @Test
    public void checkDeterminant() {
        Assert.assertTrue(mId.det().equals(1));
        Assert.assertTrue(mZero.det().equals(0));
        Assert.assertTrue(mMain.det().equals(30));
    }

    @Test
    public void checkReduce() {
        mId.reduce().print();
        System.out.printf("\n");
        mMain.reduce().print();
        System.out.printf("\n");
        mZero.reduce().print();
        System.out.printf("\n");
        mSwap.reduce().print();
        System.out.printf("\n");
        mSingular.reduce().print();
    }

    @Test
    public void checkInvert() {
        mId.invert().print();
        mMain.invert().print();
    }

    @Test
    public void checkSolve() {
        mId.solve(solution);
        mMain.solve(solution);
        mZero.solve(solution);
        mSingular.solve(solution);
        mSingular.solve(solution);
    }

    @Test
    public void checkRank() {
        System.out.println(mId.rank());
        System.out.println(mMain.rank());
        System.out.println(mSingular.rank());
        System.out.println(mTest.rank());
        System.out.println(mZero.rank());
    }
}
