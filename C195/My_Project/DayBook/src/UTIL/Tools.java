package UTIL;

import CONTROLLER.Main_C;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Tools {

    /**
     * Performs Navigation throughout the program
     *
     * @param event scene_switch
     * @param view VIEW
     * @param view_title View Title
     * @throws IOException
     */
    public static void scene_switch(ActionEvent event, String view, String view_title) throws IOException {
        Stage Scene = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Main_C.class.getResource("/VIEWS/" + view + ".fxml"));
        Scene.setTitle(view_title);
        Scene.setScene(new Scene(scene));
        Scene.show();

    }


    /**
     * Displays Alert Messages to the users
     *
     * @param message message
     */
    public static void Alert_msg(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ALERT MESSAGE");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> confirmation = alert.showAndWait();


    }

    // Search
}
