package algebra;

import org.testng.annotations.Test;

/**
 * Created by Zach on 2017-07-19.
 */
public class PolynomialTest {

    @Test
    public void basicTests() {
        System.out.println(new Polynomial("1a+2b+3c"));
        System.out.println(new Polynomial("-12zxy + 3a"));
    }

    @Test
    public void testSimplify() {
        System.out.println(new Polynomial("1a + 2a").simplify());
        System.out.println(new Polynomial("2ab + 3y - 8ab + 2y").simplify());
        System.out.println(new Polynomial("-z + t").simplify());
        System.out.println(new Polynomial("3a+0b-0c-d").simplify());
        System.out.println(new Polynomial("3a + 2b - 3a").simplify());
        System.out.println(new Polynomial("a").negate());
    }

    @Test
    public void testAdd() {
        System.out.println(new Polynomial("a+b").add(new Polynomial("a+c")));
        System.out.println(new Polynomial("3x-2y+z").add(new Polynomial("-x+2y+5z")));
    }

    @Test
    public void testMult() {
        System.out.println(new Polynomial("a+b").mult(new Polynomial("a+b")));
        System.out.println(new Polynomial("a+b").mult(new Polynomial("a-b")));
    }
}
