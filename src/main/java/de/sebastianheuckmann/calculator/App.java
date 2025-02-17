package de.sebastianheuckmann.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class App extends Application
{
    private static Stage primaryStage;
    @Override
    public void start(Stage stage){
        primaryStage = stage;
        primaryStage.setTitle("Java Calculator");
        switchScene("/de/sebastianheuckmann/view/calculator.fxml");
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public static void main( String[] args )
    {
        System.out.println("Here we go");
        launch();
    }

    public static void switchScene(String fxmlScene){
        try {
            Parent root = FXMLLoader.load(App.class.getResource(fxmlScene));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(App.class.getResource("/css/styles.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.sizeToScene(); // Automatically adjust the stage size
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}


