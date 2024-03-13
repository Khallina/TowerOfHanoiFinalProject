
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TowerOfHanoiGame extends Instruction {

    @Override
    public void start(Stage primaryStage) {
        // Create a VBox layout
        VBox root = new VBox();

        // Create an Alert with instructions
        Alert instructions = new Alert(AlertType.INFORMATION);
        instructions.setTitle("Tower of Hanoi Instructions");
        instructions.setHeaderText("Tower of Hanoi Game");
        instructions.setContentText("Instructions:\n\n"
                + "1. Move all discs from one peg to another.\n"
                + "2. Only one disc can be moved at a time.\n"
                + "3. A disc can only be placed on top of a larger disc or an empty peg.\n\n"
                + "Click 'OK' to start the game!");

        // Set the alert to close on OK button press
        instructions.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
            // Add your Tower of Hanoi game logic here
            // You can create a separate class for the game logic and instantiate it here
            // For simplicity, we will exit the application after closing the instructions
            primaryStage.close();
        });

        // Set up the scene
        Scene scene = new Scene(root, 400, 300);

        // Set up the stage
        primaryStage.setTitle("Tower of Hanoi Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
