package algebra.expression;

import algebra.Polynomial;

import java.text.ParseException;

/**
 * Created by Zach on 2017-07-23.
 */
public class Expression {
    ExpressionNode root;

    public Expression(ExpressionNode r) {
        root = r;
    }

    public Expression(String s) throws ParseException {
        root = new ExpressionParser().parseString(s);
    }

    public Polynomial expand() {
        return root.expand();
    }
}
