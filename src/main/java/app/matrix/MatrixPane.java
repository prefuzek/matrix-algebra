package app.matrix;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import matrix.Matrix;
import matrix.SquareMatrix;
import algebra.expression.ExpressionParser;
import rational.Rational;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Zach on 2017-07-13.
 */
public class MatrixPane extends VBox {
    private int rows, cols;
    private ArrayList<ArrayList<TextField>> textFields;
    private Label[][] answers;
    private GridPane matrixGrid, answerGrid;
    private Button addRowButton, removeRowButton, addColButton, removeColButton, invertButton;
    private HBox topButtonsBox, bottomButtonsBox;
    private Button calcDet;
    private Label messageLabel;

    private void addRow() {
        ArrayList<TextField> newRow = new ArrayList<TextField>();
        for (int i=0;i<cols;i++) {
            TextField field = new TextField();
            field.setPrefWidth(50);
            field.setPrefHeight(40);
            newRow.add(field);
            matrixGrid.add(newRow.get(i), i, rows, 1, 1);
        }
        textFields.add(newRow);
        rows++;
    }

    private void removeRow() {
        if (rows>0) {
            rows--;
            for (TextField field : textFields.get(rows)) {
                matrixGrid.getChildren().remove(field);
            }
            textFields.remove(rows);
        }
    }

    private void addCol() {
        for (int i=0;i<rows;i++) {
            TextField field = new TextField();
            field.setPrefWidth(50);
            field.setPrefHeight(40);
            textFields.get(i).add(field);
            matrixGrid.add(textFields.get(i).get(cols), cols, i, 1, 1);
        }
        cols++;
    }

    private void removeCol() {
        if (cols>0) {
            cols--;
            for (ArrayList<TextField> row : textFields) {
                matrixGrid.getChildren().remove(row.get(cols));
                row.remove(cols);
            }
        }
    }

    public Matrix getMatrix() {
        Rational[][] vals = new Rational[rows][cols];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<cols;j++) {
                String fieldVal = textFields.get(i).get(j).getText();
                if (!fieldVal.isEmpty()) {
                    try {
                        vals[i][j] = new ExpressionParser().eval(textFields.get(i).get(j).getText());
                    } catch (ParseException e) {
                        messageLabel.setText("Bad input at " + i + ", " + j);
                    }
                } else {
                    vals[i][j] = new Rational(0,1);
                }
            }
        }
        return new Matrix(vals, cols, rows);
    }

    public SquareMatrix getSquareMatrix() {
        assert(rows == cols);
        Rational[][] vals = new Rational[rows][cols];
        for (int i=0;i<rows;i++) {
            for (int j=0;j<cols;j++) {
                String fieldVal = textFields.get(i).get(j).getText();
                if (!fieldVal.isEmpty()) {
                    try {
                        vals[i][j] = new ExpressionParser().eval(textFields.get(i).get(j).getText());
                    } catch (ParseException e) {
                        messageLabel.setText("Bad input at " + i + ", " + j);
                    }
                } else {
                    vals[i][j] = new Rational(0,1);
                }
            }
        }
        return new SquareMatrix(vals, cols);
    }

    private Rational calculateDet() {
        if (cols != rows) {
            messageLabel.setText("Matrix must be square!");
            return null;
        } else {
            Rational det = getSquareMatrix().det();
            messageLabel.setText("Determinant: " + det.toString());
            return det;
        }
    }

    private SquareMatrix invertMatrix() {
        if (cols != rows) {
            messageLabel.setText("Matrix must be square!");
            return null;
        } else {
            answers = new Label[rows][cols];
            answerGrid.getChildren().clear();
            SquareMatrix inverse = getSquareMatrix().invert();
            for (int i=0;i<rows;i++) {
                for (int j=0;j<cols;j++) {
                    Label cell = new Label(inverse.getVal(i, j).toString());
                    cell.setPrefSize(50, 40);
                    cell.setStyle("-fx-border-color: #000");
                    answers[i][j] = cell;
                    answerGrid.add(cell, j, i);
                }
            }
            return inverse;
        }
    }

    public MatrixPane(int w, int h) {
        super();

        rows = h;
        cols = w;

        textFields = new ArrayList<ArrayList<TextField>>();
        matrixGrid = new GridPane();
        ArrayList<TextField> curRow;
        for (int i=0;i<rows;i++) {
            curRow = new ArrayList<TextField>();
            for (int j=0;j<cols;j++) {
                TextField field = new TextField();
                field.setPrefWidth(50);
                field.setPrefHeight(40);
                curRow.add(field);
                matrixGrid.add(field, j, i, 1,1);
            }
            textFields.add(curRow);
        }
        matrixGrid.setAlignment(Pos.CENTER);

        addRowButton = new Button("Add row");
        addRowButton.setOnAction(actionEvent -> addRow());
        removeRowButton = new Button("Remove row");
        removeRowButton.setOnAction(actionEvent -> removeRow());
        addColButton = new Button("Add column");
        addColButton.setOnAction(actionEvent -> addCol());
        removeColButton = new Button("Remove column");
        removeColButton.setOnAction(actionEvent -> removeCol());
        topButtonsBox = new HBox(addRowButton, removeRowButton, addColButton, removeColButton);

        calcDet = new Button("Determinant");
        calcDet.setPrefWidth(100);
        calcDet.setOnAction(actionEvent -> calculateDet());
        invertButton = new Button("Invert");
        invertButton.setPrefWidth(100);
        invertButton.setOnAction(actionEvent -> invertMatrix());
        bottomButtonsBox = new HBox(calcDet, invertButton);
        bottomButtonsBox.setSpacing(20);
        bottomButtonsBox.setAlignment(Pos.CENTER);

        messageLabel = new Label();
        answerGrid = new GridPane();
        answerGrid.setAlignment(Pos.CENTER);

        getChildren().addAll(topButtonsBox, matrixGrid, bottomButtonsBox, messageLabel, answerGrid);
    }
}
