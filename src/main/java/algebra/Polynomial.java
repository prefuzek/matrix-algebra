package algebra;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Zach on 2017-06-29.
 */
public class Polynomial {
    ArrayList<Monomial> terms;

    public Polynomial() {
        terms = new ArrayList<Monomial>();
    }

    public Polynomial(ArrayList<Monomial> p) {
        terms = p;
        terms.sort(Monomial::compareTo);
    }

    public Polynomial(String s) {
        s = s.replace(" ", "");
        if (s.equals("")) {
            new Polynomial();
        }

        terms = new ArrayList<>();
        int lMarker = 0;
        char prevSign = '+';
        char cur;
        for (int i = 0, n = s.length(); i < n; i++) {
            cur = s.charAt(i);
            if (cur == '+' || cur == '-') {
                if (!s.substring(lMarker, i).isEmpty()) {
                    if (prevSign == '+') {
                        terms.add(new Monomial(s.substring(lMarker, i)).simplify());
                    } else {
                        terms.add(new Monomial("-" + s.substring(lMarker, i)).simplify());
                    }
                    lMarker = i + 1;
                    prevSign = cur;
                }
            }
        }
        if (prevSign == '+') {
            terms.add(new Monomial(s.substring(lMarker)));
        } else {
            terms.add(new Monomial("-" + s.substring(lMarker)));
        }
        terms.sort(Monomial::compareTo);
    }

    public Polynomial simplify() {

        ListIterator<Monomial> iter = terms.listIterator();
        while (iter.hasNext()) {
            Monomial cur = iter.next();
            int pos = iter.nextIndex();
            for (int i=pos; i<terms.size(); i++) {
                if (cur.varsEqual(terms.get(i))) {
                    terms.get(i).add(cur);
                    iter.remove();
                    break;
                }
            }
            if (cur.getCoefficient().equals(0)) {
                iter.remove();
            }
        }

        for (Monomial m : terms) {
            m.simplify();
        }
        terms.sort(Monomial::compareTo);
        return this;
    }

    public Polynomial add(Polynomial p) {
        Polynomial sum = new Polynomial();
        sum.terms.addAll(terms);
        sum.terms.addAll(p.terms);
        return sum.simplify();
    }

    public Polynomial add(Monomial m) {
        Polynomial sum = new Polynomial();
        sum.terms.addAll(terms);
        sum.terms.add(m);
        return sum.simplify();
    }

    public Polynomial negate() {
        Polynomial neg = new Polynomial();
        for (Monomial m : terms) {
            neg = neg.add(m.negate());
        }
        return neg.simplify();
    }

    public Polynomial mult(Polynomial p) {
        Polynomial prod = new Polynomial();
        for (Monomial m1 : terms) {
            for (Monomial m2 : p.terms) {
                prod.terms.add(m1.mult(m2));
            }
        }
        prod.terms.sort(Monomial::compareTo);
        return prod.simplify();
    }

    public Polynomial expt(Polynomial p) {
        int e = p.toInt();
        Polynomial n = new Polynomial("1");
        for (int i=0; i<e; i++) {
            n = n.mult(this);
        }
        return n.simplify();
    }

    public String toString() {
        String s = "";
        for (Monomial m : terms) {
            if (m.isPositive()) {
                s = s.concat("+" + m.toString());
            } else {
                s = s.concat(m.toString());
            }
        }

        if (s.isEmpty()) return "0";

        if (s.charAt(0) == '+') {
            return s.substring(1);
        }

        return s;
    }

    public int toInt() {
        for (Monomial m : terms) {
            if (m.varsEqual(new Monomial("1"))) {
                return m.getCoefficient().toInt();
            }
        }
        return 0;
    }
}