package algebra.expression;

import algebra.Monomial;
import algebra.Polynomial;

/**
 * Created by Zach on 2017-07-13.
 */
public class ExpressionNode {
    String v;
    ExpressionNode lChild;
    ExpressionNode rChild;

    public ExpressionNode(){};

    public ExpressionNode(String v) {
        this.v = v;
    }

    public ExpressionNode(ExpressionNode other) {
        this.v = other.v;
        this.lChild = other.lChild;
        this.rChild = other.rChild;
    }

    public Polynomial expand() {
        Polynomial p = new Polynomial();
        switch (this.v) {
            case "+":
                return lChild.expand().add(rChild.expand());
            case "-":
                return lChild.expand().add(rChild.expand().negate());
            case "*":
                return lChild.expand().mult(rChild.expand());
            case "/":
                // TODO
            case "^":
                return lChild.expand().expt(rChild.expand());
            default:
                return p.add(new Monomial(v));
        }
    }
}
