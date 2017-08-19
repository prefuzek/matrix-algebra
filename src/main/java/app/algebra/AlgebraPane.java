package app.algebra;

import app.Utils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import algebra.expression.ExpressionParser;
import algebra.expression.Expression;

import java.text.ParseException;

/**
 * Created by Zach on 2017-07-18.
 */
public class AlgebraPane extends VBox {

    TextField algebraInputField;
    Button algebraSimplifyButton, switchToMatrixButton;
    Label algebraOutputLabel;
    ExpressionParser parser;

    private void simplify(TextField algebraField) {
        try {
            algebraOutputLabel.setText(new Expression(algebraField.getText()).expand().toString());
        } catch (ParseException e) {
            algebraOutputLabel.setText("Bad input!");
        }
    }

    public AlgebraPane(Stage stage, Scene matrixScene) {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        algebraInputField = new TextField();
        algebraSimplifyButton = new Button("Simplify");
        algebraSimplifyButton.setOnAction(actionEvent -> simplify(algebraInputField));
        algebraOutputLabel = new Label();
        switchToMatrixButton = new Button("Matrix Calculator");
        switchToMatrixButton.setOnAction(actionEvent -> Utils.switchToScene(stage, matrixScene));
        parser = new ExpressionParser();

        getChildren().addAll(algebraInputField,algebraSimplifyButton,algebraOutputLabel,switchToMatrixButton);
    }
}
