package de.sebastianheuckmann.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/sebastianheuckmann/view/calculator.fxml"));

        // Setting scene first

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Java Calculator");
        stage.setScene(scene);
        stage.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

        stage.show();

    }
    public static void main( String[] args )
    {
        System.out.println("Here we go");
        launch();
    }
}
