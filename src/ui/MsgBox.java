package ui;
/*
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;*/

import javafx.scene.control.Alert;

public final class MsgBox {

    private static Alert alert = new Alert(Alert.AlertType.ERROR) ;

    public static void show(String text) {

        alert.setHeaderText("Erreur de conversion");
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.showAndWait() ;

    }

    public static void show(String header, String text, Alert.AlertType alertType) {

        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.setAlertType(alertType);
        alert.showAndWait() ;

    }

}
