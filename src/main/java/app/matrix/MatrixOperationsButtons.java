package app.matrix;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import matrix.Matrix;
import matrix.SquareMatrix;

/**
 * Created by Zach on 2017-07-17.
 */
public class MatrixOperationsButtons extends VBox {

    private Button addButton;
    private Button subtractButton;
    private Button multiplyButton;
    private Button divideButton;

    private Matrix addMatrices(Matrix matrixA, Matrix matrixB, MatrixDisplay matrixDisplay) {
        Matrix sum = matrixA.add(matrixB);
        matrixDisplay.setMatrix(sum);
        return sum;
    }

    private Matrix subtractMatrices(Matrix matrixA, Matrix matrixB, MatrixDisplay matrixDisplay) {
        Matrix diff = matrixA.add(matrixB.scale(-1));
        matrixDisplay.setMatrix(diff);
        return diff;
    }

    private Matrix multMatrices(Matrix matrixA, Matrix matrixB, MatrixDisplay matrixDisplay) {
        Matrix prod = matrixA.mult(matrixB);
        matrixDisplay.setMatrix(prod);
        return prod;
    }

    private Matrix divideMatrices(SquareMatrix matrixA, SquareMatrix matrixB, MatrixDisplay matrixDisplay) {
        Matrix quot = matrixA.mult(matrixB.invert());
        matrixDisplay.setMatrix(quot);
        return quot;
    }

    public MatrixOperationsButtons(MatrixPane matrixA, MatrixPane matrixB, MatrixDisplay matrixDisplay) {
        addButton = new Button("A+B");
        addButton.setPrefWidth(40);
        addButton.setOnAction(actionEvent ->addMatrices(matrixA.getMatrix(), matrixB.getMatrix(), matrixDisplay));
        subtractButton = new Button("A-B");
        subtractButton.setPrefWidth(40);
        subtractButton.setOnAction(actionEvent -> subtractMatrices(matrixA.getMatrix(), matrixB.getMatrix(), matrixDisplay));
        multiplyButton = new Button("A*B");
        multiplyButton.setPrefWidth(40);
        multiplyButton.setOnAction(actionEvent -> multMatrices(matrixA.getMatrix(), matrixB.getMatrix(), matrixDisplay));
        divideButton = new Button("A/B");
        divideButton.setPrefWidth(40);
        divideButton.setOnAction(actionEvent -> divideMatrices(matrixA.getSquareMatrix(), matrixB.getSquareMatrix(), matrixDisplay));

        getChildren().addAll(addButton, subtractButton, multiplyButton, divideButton);
    }
}
