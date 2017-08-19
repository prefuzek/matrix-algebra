package algebra.expression;

import rational.Rational;

import java.text.ParseException;

/**
 * Created by Zach on 2017-07-13.
 */
public class ExpressionParser {

    private static int getOpPrecedence(String op) {
        switch (op) {
            case "+": return 3;
            case "-": return 3;
            case "*": return 2;
            case "/": return 2;
            case "^": return 1;
            default: return 0;
        }
    }

    public static String addImplicitMults(String s) {
        // TODO: See if this actually works properly

        int l = s.length();
        int lMarker = 0;
        String newString = "";
        for (int i=0; i<l-1; i++) {
            if ((Character.isDigit(s.charAt(i)) && Character.isAlphabetic(s.charAt(i+1))) ||
                    (Character.isAlphabetic(s.charAt(i)) && Character.isAlphabetic(s.charAt(i+1)))) {
                newString = newString + s.substring(lMarker, i+1) + "*";
                lMarker = i+1;
        }
        }
        newString += s.substring(lMarker);
        return newString;
    }

    private ExpressionNode parseStringOp(String s, ExpressionNode p) throws ParseException {
        if (s.isEmpty()) {
            return p;
        }

        String ops = "+-*/^";
        String op;
        ExpressionNode newNode;

        if (s.charAt(0) == '(') {
            op = "*";
        } else if (ops.contains(String.valueOf(s.charAt(0)))) {
            op = String.valueOf(s.charAt(0));
        } else {
            throw new ParseException("Unexpected character in expression", 0);
        }

        try {
            ExpressionNode runner = p;
            while (runner != null && getOpPrecedence(runner.v) > getOpPrecedence(op)) {
                runner = runner.rChild;
            }

            newNode = new ExpressionNode(runner);
            runner.v = op;
            runner.lChild = newNode;
            if (s.charAt(0) == '(') {
                ExpressionNode nextTerm = parseStringTerm(s);
                runner.rChild = nextTerm;
                return parseStringOp(s.substring(nextTerm.v.length()), p);
            } else {
                ExpressionNode nextTerm = parseStringTerm(s.substring(1));
                runner.rChild = nextTerm;
                return parseStringOp(s.substring(nextTerm.v.length() + 1), p);
            }
        } catch (Exception e) {
            throw new ParseException("Bad input", 0);
        }
    }

    private ExpressionNode parseStringTerm(String s) throws ParseException {
        char cur;
        String ops = "+-*/(^";

        try {
            if (s.charAt(0) == '(') {
                int bracketCount = 1;
                for (int i = 1; i < s.length(); i++) {
                    cur = s.charAt(i);
                    if (cur == '(') {
                        bracketCount++;
                    } else if (cur == ')') {
                        bracketCount--;
                        if (bracketCount == 0) {
                            ExpressionNode newNode = new ExpressionNode(s.substring(0, i + 1));
                            return newNode;
                        }
                    }
                }
                return new ExpressionNode(s);
            } else {
                for (int i = 0; i < s.length(); i++) {
                    cur = s.charAt(i);
                    if (ops.contains(String.valueOf(cur))) {
                        if (i != 0 || cur != '-') { // handles negative terms
                            ExpressionNode newNode = new ExpressionNode();
                            newNode.v = s.substring(0, i);
                            return newNode;
                        }
                    }
                }
                return new ExpressionNode(s);
            }
        } catch (Exception e) {
            throw new ParseException("Bad input", 0);
        }
    }

    private ExpressionNode expandParseTree(ExpressionNode s) throws ParseException {
        if (s.lChild != null) {
            s.lChild = expandParseTree(s.lChild);
            s.rChild = expandParseTree(s.rChild);
        } else if (s.v.charAt(0)=='(') {
            s = parseString(s.v.substring(1, s.v.length()-1));
        }
        return s;
    }

    ExpressionNode parseString(String s) throws ParseException {
        s = s.replace(" ", "");
        s = addImplicitMults(s);
        ExpressionNode firstTerm = parseStringTerm(s);
        ExpressionNode parseTree = parseStringOp(s.substring(firstTerm.v.length()), firstTerm);
        return expandParseTree(parseTree);
    }

    private Rational evalNode(ExpressionNode node) {
        switch (node.v) {
            case "+":
                return evalNode(node.lChild).add(evalNode(node.rChild));
            case "-":
                return evalNode(node.lChild).add(evalNode(node.rChild).negate());
            case "*":
                return evalNode(node.lChild).mult(evalNode(node.rChild));
            case "/":
                return evalNode(node.lChild).mult(evalNode(node.rChild).invert());
            case "^":
                return evalNode(node.lChild).expt(evalNode(node.rChild).toInt());
            default:
                return new Rational(node.v);
        }
    }

    public Rational eval(String s) throws ParseException {
        return evalNode(parseString(s));
    }
}