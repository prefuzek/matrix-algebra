package algebra;

import rational.Rational;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Zach on 2017-06-29.
 */
public class Monomial implements Comparable<Monomial> {
    private ArrayList<Variable> vars;
    private Rational coefficient;

    public Rational getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Rational coefficient) {
        this.coefficient = coefficient;
    }

    public Monomial() {
        vars = new ArrayList<>();
    }

    public Monomial(Monomial m) {
        coefficient = m.coefficient;
        vars = new ArrayList<>();
        for (Variable v : m.vars) {
            vars.add(new Variable(v));
        }
    }

    public Monomial(Rational c, Variable var) {
        vars = new ArrayList<>();
        vars.add(var);
        coefficient = c;
    }

    public Monomial(String s) {
        //assumes that string is in standard form eg 24ab
        vars = new ArrayList<>();
        if (s.isEmpty()) {
            return;
        }

        int i=0;
        while (i<s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '-')) {
            i++;
        }

        String sCoefficient = s.substring(0,i);
        if (sCoefficient.isEmpty()) {
            sCoefficient = "1";
        } else if (sCoefficient.equals("-")) {
            sCoefficient = "-1";
        }
        coefficient = new Rational(sCoefficient);

        while (i<s.length() && Character.isAlphabetic(s.charAt(i))) {
            vars.add(new Variable(s.charAt(i), Rational.toRational(1)));
            i++;
        }
        vars.sort(Variable::compareTo);
        simplify();
    }

    public Monomial mult(Monomial m) {
        Monomial prod = new Monomial();
        prod.coefficient = coefficient.mult(m.coefficient);
        for (Variable v : vars) {
            prod.vars.add(new Variable(v));
        }
        for (Variable v : m.vars) {
            prod.vars.add(new Variable(v));
        }
        prod.vars.sort(Variable::compareTo);
        prod.simplify();
        return prod;
    }

    public Monomial add(Monomial m) {
        assert(varsEqual(m));
        setCoefficient(coefficient.add(m.coefficient));
        return this;
    }

    public Monomial negate() {
        Monomial neg = new Monomial();
        neg.vars = this.vars;
        neg.coefficient = coefficient.negate();
        return neg;
    }

    public Boolean isPositive() {
        return coefficient.isPositive();
    }

    public Monomial simplify() {
        ListIterator<Variable> iter = vars.listIterator();
        while (iter.hasNext()) {
            Variable cur = iter.next();
            int pos = iter.nextIndex();
            for (int i=pos; i<vars.size(); i++) {
                if (cur.var == vars.get(i).var) {
                    vars.get(i).expt = vars.get(i).expt.add(cur.expt);
                    iter.remove();
                    break;
                }
            }
            if (cur.expt.equals(0)) {
                iter.remove();
            }
        }
        vars.sort(Variable::compareTo);
        return this;
    }

    public String toString() {
        if (vars.isEmpty()) {
            return coefficient.toString();
        } else if (coefficient.equals(0)) {
            return "";
        }

        String s;
        if (coefficient.equals(1)) {
            s = "";
        } else if (coefficient.equals(-1)) {
            s = "-";
        } else if (coefficient.getDenom() != 1) {
            s = "("+ coefficient.toString() + ")";
        } else {
            s = coefficient.toString();
        }

        for (Variable v : vars) {
            s = s.concat(v.toString());
        }
        return s;
    }

    public int compareTo(Monomial m) {
        if (vars.isEmpty() && m.vars.isEmpty()) {
            return 0;
        } else if (vars.isEmpty()) {
            return 1;
        } else if (m.vars.isEmpty()) {
            return -1;
        } else {
            return vars.get(0).compareTo(m.vars.get(0));
        }
    }

    public Boolean varsEqual(Monomial m) {
        this.simplify();
        m.simplify();
        if (vars.size() != m.vars.size()) return false;

        vars.sort(Variable::compareTo);
        m.vars.sort(Variable::compareTo);

        for (int i=0; i<vars.size(); i++) {
            if (!vars.get(i).equals(m.vars.get(i))) {
                return false;
            }
        }
        return true;
    }
}
