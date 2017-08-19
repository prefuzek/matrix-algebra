package app.matrix;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import matrix.Matrix;

/**
 * Created by Zach on 2017-07-17.
 */
public class MatrixDisplay extends GridPane {
    Label title;
    Label[][] displayGrid;

    public void setTitle(String newTitle) {
        if (title == null) {
            title = new Label(newTitle);
        } else {
            title.setText(newTitle);
        }
    }

    public void setMatrix(Matrix matrix) {
        displayGrid = new Label[matrix.getHeight()][matrix.getWidth()];
        getChildren().clear();
        for (int i=0;i<displayGrid.length;i++) {
            for (int j=0;j<displayGrid[0].length;j++) {
                Label cell = new Label(matrix.getVal(i, j).toString());
                cell.setPrefSize(50, 40);
                cell.setStyle("-fx-border-color: #000");
                displayGrid[i][j] = cell;
                add(cell, j, i);
            }
        }
    }
}
