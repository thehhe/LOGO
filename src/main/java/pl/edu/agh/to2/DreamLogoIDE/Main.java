package pl.edu.agh.to2.DreamLogoIDE;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.edu.agh.to2.DreamLogoIDE.controller.LogoAppController;

public class Main extends Application {
    /*@Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/pl.edu.agh.to2.DreamLogoIDE/view/MainWindow.fxml"));
        primaryStage.setTitle("DreamLogoIDE");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }*/

    private Stage primaryStage;
    private LogoAppController logoAppController;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DreamLogoIDE");

        this.logoAppController = new LogoAppController(primaryStage);
        this.logoAppController.initRootLayout();
    }
}
