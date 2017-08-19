package vector;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Zach on 2017-05-13.
 */
public class MyVectorTest {

    int[] a1 = {1, 2, 3, 4, 5};
    int[] a2 = {2, 2, 2, 2, 2};
    int[] a3 = {3, 4, 5, 6, 7};
    int[] a4 = {3, 6, 9, 12, 15};
    MyVector v1, v2, v3, v4;

    @BeforeMethod
    public void setup() {
        v1 = new MyVector(a1, 5);
        v2 = new MyVector(a2, 5);
        v3 = new MyVector(a3, 5);
        v4 = new MyVector(a4, 5);
    }

    @Test
    public void testLength() {
        Assert.assertEquals(v1.getLen(), 5);
        Assert.assertEquals(v2.getLen(), 5);
        Assert.assertEquals(v3.getLen(), 5);
    }

    @Test
    public void testGetVal() {
        Assert.assertTrue(v1.val(3).equals(4));
        Assert.assertTrue(v2.val(2).equals(2));
        Assert.assertTrue(v3.val(4).equals(7));
    }

    @Test
    public void testAdd() {
        v1.add(v2);
        Assert.assertTrue(v1.equals(v3));
    }

    @Test
    public void testScale() {
        v1.scale(3);
        Assert.assertTrue(v1.equals(v4));
    }

    @Test
    public void testMult() {
        Assert.assertTrue(v1.mult(v2).equals(30));
        Assert.assertTrue(v3.mult(v1).equals(85));
    }

    @Test
    public void testPrint() {
        v1.print();
        System.out.print("\n");
        v2.print();
        System.out.print("\n");
        v4.print();
    }
}