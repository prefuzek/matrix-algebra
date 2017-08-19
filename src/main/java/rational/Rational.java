package rational;

/**
 * Created by Zach on 2017-05-24.
 */
public class Rational {

    private int num;

    private int denom;

    public Rational(int a, int b) {
        assert b != 0;
        num = a;
        denom = b;
    }

    public Rational(String s) {
        if (s.isEmpty()) {
            num = 0;
            denom = 1;
        } else {
            String[] chunks = s.split("/");
            num = Integer.parseInt(chunks[0]);
            denom = 1;
            for (int i = 1; i < chunks.length; i++) {
                denom *= Integer.parseInt(chunks[i]);
            }
        }
    }

    public int getNum() {
        return num;
    }

    public int getDenom() {
        return denom;
    }

    public float val() {
        return num/denom;
    }

    public static Rational toRational(int k) {
        return new Rational(k, 1);
    }

    private Rational reduce() {
        if (num == 0) {
            return new Rational(0, 1);
        }

        int q = Math.abs(num);
        int r = Math.abs(denom);

        while (q%r != 0) {
            int temp = q;
            q = r;
            r = temp % q;
        }
        if (denom < 0) {
            return new Rational(-num / r, -denom / r);
        } else {
            return new Rational(num/r, denom/r);
        }
    }

    public Boolean equals(Rational r) {
        return (reduce().getNum() == r.reduce().getNum()) &&
                (reduce().getDenom() == r.reduce().getDenom());
    }

    public Boolean equals(int k) {
        return this.equals(toRational(k));
    }

    public Rational add(Rational r) {
        Rational sum = new Rational(this.getNum()*r.getDenom() + r.getNum()*this.getDenom(),
                                    this.getDenom()*r.getDenom());
        return sum.reduce();
    }

    public Rational add(int k) {
        return this.add(toRational(k));
    }

    public Rational negate() {
        Rational neg = new Rational(-this.getNum(), this.getDenom());
        return neg.reduce();
    }

    public Rational sub(Rational k) {
        return this.add(k.negate());
    }

    public Rational sub(int k) {
        return this.sub(toRational(k));
    }

    public Rational mult(Rational r) {
        Rational prod = new Rational(this.getNum()*r.getNum(), this.getDenom()*r.getDenom());
        return prod.reduce();
    }

    public Rational mult(int k) {
        return this.mult(toRational(k));
    }

    public Rational invert() {
        assert this.getDenom() != 0;
        Rational inverse = new Rational(this.getDenom(), this.getNum());
        return inverse.reduce();
    }

    public Rational expt(int e) {
        Rational n = new Rational("1");
        for (int i=0; i<e; i++) {
            n = n.mult(this);
        }
        return n;
    }

    public Boolean isPositive() {
        return (reduce().num > 0);
    }

    public Rational print() {
        System.out.println(toString());
        return this;
    }

    public String toString() {
        Rational r = this.reduce();
        if (r.getDenom() == 1 || r.getNum() == 0) {
            return String.valueOf(r.getNum());
        } else {
            return String.valueOf(r.getNum()) + "/" + String.valueOf(r.getDenom());
        }
    }

    public int toInt() {
        return num/denom;
    }

    public String toSuperscript() {
        char[] supDigits = {'\u2070', '\u2081', '\u00B2', '\u00B3', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'};
        String expt = "";
        int val = toInt();
        for (int i=0; val/Math.pow(10, i) !=0; i++) {
        }
        return null;
        // TODO
    }
}
