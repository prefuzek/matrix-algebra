package algebra;

import rational.Rational;

/**
 * Created by Zach on 2017-06-29.
 */
public class Variable implements Comparable<Variable> {
    char var;
    Rational expt;

    public Variable(char v, Rational e) {
        var = v;
        expt = e;
    }

    public Variable(Variable v) {
        var = v.var;
        expt = v.expt;
    }

    public String toString() {
        if (expt.equals(0)) {
            return "";
        } else if (expt.equals(1)) {
            return String.valueOf(var);
        } else {
            return String.valueOf(var) + "^(" + expt.toString() + ")";
        }
    }

    public int compareTo(Variable v) {
        return Character.compare(var, v.var);
    }

    public Boolean equals(Variable v) {
        return var == v.var && expt.equals(v.expt);
    }
}
