package matrix;

import rational.Rational;
import vector.MyVector;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Zach on 2017-05-06.
 */

public class Matrix {
    protected List<MyVector> vals;
    private int width;
    private int height;

    public Matrix(MyVector[] vectors, int w, int h) {
        this.vals = new ArrayList<MyVector>();
        this.width = w;
        this.height = h;
        for (int i=0; i<h; ++i) {
            vals.add(vectors[i]);
        }
    }

    public Matrix(Rational[][] data, int w, int h) {
        this.vals = new ArrayList<MyVector>();
        this.width = data[0].length;
        this.height = data.length;
        for (int i=0; i<this.height; ++i) {
            vals.add(new MyVector(data[i], this.width));
        }
    }

    public Matrix(int[][] data, int w, int h) {
        this.vals = new ArrayList<MyVector>();
        this.width = w;
        this.height = h;
        for (int i=0; i<h; ++i) {
            this.vals.add(new MyVector(data[i], w));
        }
    }

    public Matrix(int w, int h) {
        this.vals = new ArrayList<MyVector>();
        this.width = w;
        this.height = h;
        int[] blankArray = new int[w];
        for (int i=0; i<h; ++i) {
            vals.add(new MyVector(blankArray, w));
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rational getVal(int i, int j) {
        return vals.get(i).val(j);
    }

    public Matrix setVal(int i, int j, Rational v) {
        vals.set(i, this.getRow(i).setVal(j, v));
        return this;
    }

    public MyVector getRow(int i) {
        return vals.get(i);
    }

    public Matrix setRow(int i, MyVector v) {
        vals.set(i, v);
        return this;
    }

    public MyVector getCol(int j) {
        Rational[] colVals = new Rational[this.getHeight()];
        for (int i=0; i<this.getHeight(); i++) {
            colVals[i] = this.getVal(i, j);
        }
        return new MyVector(colVals, this.getHeight());
    }

    public Matrix add(Matrix m) {
        assert this.getHeight() == m.getHeight();
        assert this.getWidth() == m.getWidth();
        for (int i=0; i<this.getHeight(); ++i) {
            this.setRow(i, this.getRow(i).add(m.getRow(i)));
        }
        return this;
    }

    public Matrix scale(Rational s) {
        for (int i=0; i<this.getHeight(); ++i) {
            this.setRow(i, this.getRow(i).scale(s));
        }
        return this;
    }

    public Matrix scale(int s) {
        for (int i=0; i<this.getHeight(); ++i) {
            this.setRow(i, this.getRow(i).scale(s));
        }
        return this;
    }

    // Since matrix multiplication produces a fundamentally different matrix, mult(m) is purely functional

    public Matrix mult(Matrix m) {
        assert this.getWidth() == m.getHeight();
        Matrix prod = new Matrix(m.getWidth(), this.getHeight());
        for (int i=0; i<prod.getHeight(); ++i) {
            for (int j=0; j<prod.getWidth(); ++j) {
                prod.setVal(i, j, this.getRow(i).mult(m.getCol(j)));
            }
        }
        return prod;
    }

    public Matrix switchRows(int r1, int r2) {
        MyVector temp = this.getRow(r1);
        this.setRow(r1, this.getRow(r2));
        this.setRow(r2, temp);
        return this;
    }

    public Matrix scaleRow(int r, Rational s) {
        this.setRow(r, this.getRow(r).scale(s));
        return this;
    }

    public Matrix scaleRow(int r, int s) {
        this.setRow(r, this.getRow(r).scale(s));
        return this;
    }

    public Matrix addRow(int r1, int r2, Rational s) {
        this.setRow(r1, this.getRow(r1).add(this.getRow(r2).copy().scale(s)));
        return this;
    }

    public Matrix addRow(int r1, int r2, int s) {
        addRow(r1,r2, Rational.toRational(s));
        return this;
    }

    public static Matrix identity(int s) {
        Matrix identity = new SquareMatrix(s);
        for (int i=0; i<s; i++) {
            identity.setVal(i, i, new Rational(1, 1));
        }
        return identity;
    }

    public Matrix copy() {
        Matrix copy = new Matrix(getWidth(), getHeight());
        for (int i=0; i<getHeight(); i++) {
            for (int j=0; j<getWidth(); j++) {
                copy.setVal(i, j, getVal(i, j));
            }
        }
        return copy;
    }

    // TODO: MAKE PRINT BETTER

    public void print() {
        for (int i=0; i<height; i++) {
            getRow(i).print();
            System.out.printf("\n");
        }
    }

    public Boolean equals(Matrix m) {
        for (int i=0; i<height; i++) {
            if (!this.getRow(i).equals(m.getRow(i))) {
                return false;
            }
        }
        return true;
    }

    public Matrix reduce() {
        return reduce(0, 0);
    }

    public Matrix reduce(int pivoti, int pivotj) {
        while (pivotj<getWidth() && pivoti<getHeight()) {
            if (getVal(pivoti, pivotj).equals(0)) {
                if (getCol(pivotj).firstNonZero(pivoti+1) < 0) {
                    pivotj++;
                } else {
                    switchRows(pivoti, getCol(pivotj).firstNonZero(pivoti+1));
                    reduce(pivoti, pivotj);
                }
            } else {
                scaleRow(pivoti, getVal(pivoti, pivotj).invert());
                for (int i=0; i<getHeight(); i++) {
                    if (i != pivoti) {
                        if (!getVal(i, pivotj).equals(0)) {
                            Rational s = getVal(i, pivotj).negate();
                            addRow(i, pivoti, s);
                        }
                    }
                }
                pivoti++;
                pivotj++;
            }
        }
        return this;
    }

    public int rank() {
        int rank = 0;
        Matrix reduced = this.copy().reduce();
        for (int i=0; i<getHeight(); i++) {
            for (int j=0; j<getWidth(); j++) {
                if (reduced.getVal(i, j).equals(1)) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    public Matrix augment(MyVector v) {
        Matrix augMatrix = new Matrix(getWidth()+1, getWidth());
        for (int i=0; i<getHeight(); i++) {
            for (int j=0; j<getWidth(); j++) {
                augMatrix.setVal(i, j, getVal(i, j));
            }
            augMatrix.setVal(i, getWidth(), v.val(i));
        }

        return augMatrix;
    }

    public void solve(MyVector v) {
        Matrix augMatrix = augment(v);
        int rank = augMatrix.rank();
        if (rank() < rank) {
            System.out.println("No solutions exist!");
        } else if (rank < augMatrix.getHeight()) {
            System.out.println("There are infinitely many solutions! Here's the general form:");
            augMatrix.reduce();
            for (int i=0; i<rank; i++) {
                for (int j=0; j<augMatrix.getWidth()-1; j++) {
                    if (augMatrix.getVal(i, j).equals(1)) {
                        System.out.printf("x"+j+" = ");
                        for (int k=j+1; k<augMatrix.getWidth()-1; k++) {
                            if (!augMatrix.getVal(i, k).equals(0)) {
                                augMatrix.getVal(i, k).negate().print();
                                System.out.printf("x"+k+" + ");
                            }
                        }
                        augMatrix.getVal(i, augMatrix.getWidth()-1).print();
                        System.out.println();
                        continue;
                    }
                }
            }
        } else {
            augMatrix.reduce();
            System.out.println("Solution:");
            for (int i = 0; i < getHeight(); i++) {
                System.out.println();
                System.out.printf("x%d: ", i);
                augMatrix.getVal(i, getWidth()).print();
            }
            System.out.println();
        }
    }
}
