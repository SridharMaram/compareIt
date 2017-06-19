package com.citi.mi.compareit;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
  @Override
  public void start(Stage stage) {
    try {
      URL url = 
          getClass()
          .getResource("classpath:FxmlTextView.fxml");
      VBox root = FXMLLoader.load(url);

      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage
      .setTitle("Drop Text");
      stage.show();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}