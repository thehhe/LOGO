package pl.edu.agh.to2.DreamLogoIDE.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.edu.agh.to2.DreamLogoIDE.Main;

import java.io.File;
import java.io.IOException;

public class LogoAppController {

    private Stage primaryStage;

    public LogoAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("DreamLogoIDE");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/pl.edu.agh.to2.DreamLogoIDE/view/MainWindow.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();

            MainWindowController controller = loader.getController();
            controller.setLogoAppController(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File showFileChooserAndReturnFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose script. . .");

            return fileChooser.showOpenDialog(primaryStage);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public File showFileChooserAndSaveFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save script. . .");

            return fileChooser.showSaveDialog(primaryStage);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
