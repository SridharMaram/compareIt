package com.citi.mi.compareit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class TextViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML // fx:id="myTextArea"
    private TextArea myTextArea;

    @FXML
    void draggingOver(DragEvent event) {
      Dragboard board = event.getDragboard();
      if (board.hasFiles()) {
        event.acceptTransferModes(TransferMode.ANY);
      }
      
    }

    @FXML
    void dropping(DragEvent event) {
      try {
        Dragboard board = event.getDragboard();
        List<File> phil = board.getFiles();
        FileInputStream fis;
        fis = new FileInputStream(phil.get(0));
      
        StringBuilder builder = new StringBuilder();
        int ch;
        while((ch = fis.read()) != -1){
            builder.append((char)ch);
        }
        
        fis.close();
        
        myTextArea.setText(builder.toString());

      } catch (FileNotFoundException fnfe) {
        fnfe.printStackTrace();
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
     }

    @FXML
    void initialize() {}
}
