package algebra.parser;

import algebra.Polynomial;
import algebra.expression.Expression;
import algebra.expression.ExpressionParser;
import org.testng.annotations.Test;

import java.text.ParseException;

/**
 * Created by Zach on 2017-07-14.
 */
public class ParseTest {
    ExpressionParser parser = new ExpressionParser();

    @Test
    public void testParse() {
        try {
            parser.eval("1+2").print();
            parser.eval("-2+ -4").print();
            parser.eval("2*3+1").print();
            parser.eval("(1+2)*3").print();
            parser.eval("(2+(3/4)+5(3-9))*5").print();
            parser.eval("1+2*5").print();
            parser.eval("3/5*2/5").print();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExpressionExpand() {
        try{
            System.out.println(new Expression("1a+2a").expand());
            System.out.println(new Expression("5(3x+y)").expand());
            System.out.println(new Expression("2x(y+z)+(-2xy+z)").expand());
            System.out.println(new Expression("a-b").expand());
            System.out.println(new Expression("(a+b)(a+b)(a+b)").expand());
            System.out.println(new Expression("aaa+3aab+3abb+bbb").expand());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExpt() {
        Polynomial p = new Polynomial("a+b");
        System.out.println(p.expt(new Polynomial("4")));
    }
}
