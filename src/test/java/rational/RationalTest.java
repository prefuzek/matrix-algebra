package rational;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Zach on 2017-05-24.
 */

public class RationalTest {

    Rational r1 = new Rational(3,2);
    Rational r2 = new Rational(2, 4);
    Rational r3 = new Rational(6, 4);
    Rational r4 = new Rational(120, 240);
    Rational r5 = new Rational(3, 4);
    Rational r6 = new Rational(2, 1);
    Rational r7 = new Rational( -5, 3);
    Rational r8 = new Rational(1, -6);
    Rational r9 = new Rational(1, 1);
    Rational r10 = new Rational(0, 8);

    @Test
    public void testReduce() {
        Assert.assertTrue(r4.equals(r2));
        Assert.assertTrue(r1.equals(r3));
        Assert.assertFalse(r1.equals(r2));
        Assert.assertTrue(r1.mult(r2).equals(r5));
        Assert.assertTrue(r2.invert().equals(r6));
        Assert.assertTrue(r1.add(r2).equals(r6));
        Assert.assertTrue(r1.add(r2.negate()).equals(r9));
        Assert.assertTrue(r1.add(r7).equals(r8));
        Assert.assertTrue(r8.mult(r8.invert()).equals(r9));
    }

    @Test
    public void testPrint() {
        r1.print();
        System.out.printf("\n");
        r4.print();
        System.out.printf("\n");
        r6.print();
        System.out.printf("\n");
        r7.print();
        System.out.printf("\n");
        r8.print();
    }

    @Test
    public void testMult() {
        Assert.assertTrue(r1.mult(r10).equals(r10));
    }
}
