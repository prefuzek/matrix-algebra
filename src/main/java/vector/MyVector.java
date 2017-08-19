package vector;

import rational.Rational;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zach on 2017-05-06.
 */

public class MyVector {
    private List<Rational> vals;
    private int len;

    public MyVector(Rational[] array, int l) {
        this.vals = new ArrayList<Rational>();
        for (int i=0; i<l; i++) {
            this.vals.add(array[i]);
        }
        this.len = l;
    }

    public MyVector(int[] array, int l) {
        this.vals = new ArrayList<Rational>();
        for (int i=0; i<l; i++) {
            this.vals.add(Rational.toRational(array[i]));
        }
        this.len = l;
    }

    public Rational val(int i) {return vals.get(i);}

    public int getLen() {return len;}

    // vector.setVal(i, v) sets the ith entry of vector to v
    // effects: modifies vector

    public MyVector setVal(int i, Rational v) {
        this.vals.set(i, v);
        return this;
    }

    // vector.add(v) adds two vectors pairwise
    // effects: modifies vector

    public MyVector add(MyVector v) {
        assert this.getLen() == v.getLen();
        for (int i=0; i<v.getLen(); ++i) {
            this.setVal(i, this.val(i).add(v.val(i)));
        }
        return this;
    }

    // vector.scale(s) multiplies vector by scalar s
    // effects: modifies vector

    public MyVector scale(Rational s) {
        for (int i=0; i<this.getLen(); ++i) {
            this.setVal(i, this.val(i).mult(s));
        }
        return this;
    }

    public MyVector scale(int s) {
        scale(Rational.toRational(s));
        return this;
    }

    // vector.mult(v) returns the pairwise product of vector and v
    // effects: none

    public Rational mult(MyVector v) {
        assert this.getLen() == v.getLen();
        Rational prod = new Rational(0, 1);
        for (int i=0; i<this.getLen(); ++i) {
            prod = prod.add(this.val(i).mult(v.val(i)));
        }
        return prod;
    }

    // vector.copy() returns a copy of vector
    // effects: none

    public MyVector copy() {
        Rational[] vals = new Rational[this.getLen()];
        for (int i=0; i<this.getLen(); ++i) {
            vals[i] = this.val(i);
        }
        return new MyVector(vals, this.getLen());
    }

    public int firstNonZero(int from) {
        for (int i=from; i<getLen(); i++) {
            if (!val(i).equals(0)) {
                return i;
            }
        }
        return -1;
    }

    // vector.equal(v) checks pairwise if vector and v are equal
    // effects: none

    public boolean equals(MyVector v) {
        assert this.getLen() == v.getLen();
        for (int i=0; i<this.getLen(); ++i) {
            if (!this.val(i).equals(v.val(i))) {
                return false;
            }
        }
        return true;
    }

    public void print() {
        System.out.printf("(");
        val(0).print();
        for (int i=1; i<len; i++) {
            System.out.printf(" ");
            val(i).print();
        }
        System.out.printf(")");
    }
}
