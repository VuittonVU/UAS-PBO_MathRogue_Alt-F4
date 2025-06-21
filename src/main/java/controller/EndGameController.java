package controller;

import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EndGameController {

    @FXML
    private Button btnRetry;

    @FXML
    private Button btnExit;

    @FXML
    private void initialize() {
        btnRetry.setOnAction(this::handleRetry);
        btnExit.setOnAction(this::handleExit);
    }

    @FXML
    private void handleRetry(ActionEvent event) {
        // Kembali ke scene permainan (game.fxml)
        Main.getInstance().restartGame();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        // Keluar dari aplikasi
        System.exit(0);
    }
}
