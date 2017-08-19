package matrix;

import rational.Rational;
import vector.MyVector;

/**
 * Created by Zach on 2017-05-18.
 */
public class SquareMatrix extends Matrix {

    public SquareMatrix(MyVector[] vectors, int s) {
        super(vectors, s, s);
    }

    public SquareMatrix(Rational[][] data, int s) {
        super(data, s, s);
    }

    public SquareMatrix(int[][] data, int s) {
        super(data, s, s);
    }

    public SquareMatrix(int s) {
        super(s, s);
    }

    public Rational det() {
        if (getWidth() == 1) {
            return this.getVal(0, 0);
        } else {
            Rational det = Rational.toRational(0);
            for (int k=0; k<this.getWidth(); k++) {
                SquareMatrix subMatrix = this.subMatrix(k);
                det = det.add(subMatrix.det().mult((int) Math.pow(-1, k)).mult(this.getVal(k, 0)));
            }
            return det;
        }
    }

    public SquareMatrix subMatrix(int n) {
        // TODO: Allow submatrix of any coordinates
        SquareMatrix subMatrix = new SquareMatrix(getWidth() - 1);
        for (int i=0; i<subMatrix.getWidth(); i++) {
            if (n <= i) {
                for (int j = 0; j < subMatrix.getWidth(); j++) {
                    subMatrix.setVal(i, j, getVal(i+1, j + 1));
                }
            } else {
                for (int j = 0; j < subMatrix.getWidth(); j++) {
                    subMatrix.setVal(i, j, getVal(i, j + 1));
                }
            }
        }
        return subMatrix;
    }

    public boolean invertible() {
        if (rank() == getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    public SquareMatrix invert() {
        if (invertible()) {
            Matrix augMatrix = new Matrix(getWidth()*2, getWidth());
            Matrix id = Matrix.identity(getWidth());
            for (int i=0; i<getWidth(); i++) {
                for (int j=0; j<getWidth(); j++) {
                    augMatrix.setVal(i, j, getVal(i, j));
                    augMatrix.setVal(i, j+getWidth(), id.getVal(i, j));
                }
            }
            augMatrix.reduce();
            for (int i=0; i<getWidth(); i++) {
                for (int j=0; j<getWidth(); j++) {
                    setVal(i, j, augMatrix.getVal(i, j+getWidth()));
                }
            }
            return this;
        } else return null;
    }
}
