package com.bishal.invoicegenerator;

import com.bishal.invoicegenerator.Model.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class InvoiceApp extends Application {

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        Database db =  new Database();
        db.getDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(InvoiceApp.class.getResource("new-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 747, 450);
        stage.setTitle("Invoice Generator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}