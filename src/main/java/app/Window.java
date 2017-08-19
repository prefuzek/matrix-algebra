package app;

import app.algebra.AlgebraPane;
import app.matrix.MatrixDisplay;
import app.matrix.MatrixOperationsButtons;
import app.matrix.MatrixPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Created by Zach on 2017-07-13.
 */
public class Window extends Application {

    private void switchToAlgebra(Stage primaryStage, Scene algebraScene) {
        primaryStage.setScene(algebraScene);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene matrixScene, algebraScene;

        primaryStage.setTitle("Algebra Calculator");

        MatrixPane matrixA = new MatrixPane(3,3);
        MatrixPane matrixB = new MatrixPane(3, 3);

        MatrixDisplay answer = new MatrixDisplay();
        MatrixOperationsButtons ops = new MatrixOperationsButtons(matrixA, matrixB, answer);
        ops.setSpacing(10);
        ops.setAlignment(Pos.CENTER);

        VBox calcInterface = new VBox(ops, answer);
        calcInterface.setMinWidth(225);
        calcInterface.setSpacing(10);
        calcInterface.setPadding(new Insets(50, 20, 20, 50));

        Button switchToAlgebraButton = new Button("Algebra");

        HBox matricesContainer = new HBox(matrixA, calcInterface, matrixB, switchToAlgebraButton);
        matricesContainer.setPrefWidth(1000);
        matricesContainer.setSpacing(30);

        matrixScene = new Scene(matricesContainer, 1000, 400);
        VBox algebraPane = new AlgebraPane(primaryStage, matrixScene);
        algebraScene = new Scene(algebraPane, 400, 400);
        switchToAlgebraButton.setOnAction(actionEvent -> Utils.switchToScene(primaryStage, algebraScene));
        primaryStage.setScene(matrixScene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
