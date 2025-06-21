package game;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    private Stage primaryStage;
    private static Main instance;

    @Override
    public void start(Stage stage) {
        instance = this;
        this.primaryStage = stage;

        //Kunci ukuran jendela sejak awal
        primaryStage.setTitle("Math Roguelike");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);

        showStartScreen();
    }

    public static Main getInstance() {
        return instance;
    }

    public void restartGame() {
        showGameScene();
    }

    private void showStartScreen() {
        StackPane startRoot = new StackPane();

        // Background image
        BackgroundImage bgImage = new BackgroundImage(
                new Image(getClass().getResource("/background/Background.jpg").toExternalForm(), 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        startRoot.setBackground(new Background(bgImage));

        // Tombol Play sebagai gambar
        ImageView playButtonImage = new ImageView(new Image(getClass().getResource("/assets/play_button.png").toExternalForm()));
        playButtonImage.setFitWidth(200);
        playButtonImage.setFitHeight(100);
        playButtonImage.setPreserveRatio(true);
        playButtonImage.setSmooth(true);

        // Efek hover
        playButtonImage.setOnMouseEntered(e -> {
            playButtonImage.setScaleX(1.05);
            playButtonImage.setScaleY(1.05);
            playButtonImage.setEffect(new javafx.scene.effect.DropShadow(20, Color.YELLOW));
        });

        playButtonImage.setOnMouseExited(e -> {
            playButtonImage.setScaleX(1.0);
            playButtonImage.setScaleY(1.0);
            playButtonImage.setEffect(null);
        });

        // Efek klik
        playButtonImage.setOnMousePressed(e -> {
            playButtonImage.setScaleX(0.95);
            playButtonImage.setScaleY(0.95);
        });

        playButtonImage.setOnMouseReleased(e -> {
            playButtonImage.setScaleX(1.0);
            playButtonImage.setScaleY(1.0);
        });

        // Efek getar/pulse
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(1), playButtonImage);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.03);
        pulse.setToY(1.03);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        // Cursor tangan
        playButtonImage.setStyle("-fx-cursor: hand;");

        // Event klik pada tombol play
        playButtonImage.setOnMouseClicked(e -> showGameScene());

        // Teks "Tap to Play" berkedip
        Label tapToPlay = new Label("Tap to Play");
        tapToPlay.setFont(Font.font("Comic Sans", 18));
        tapToPlay.setTextFill(Color.WHITESMOKE);
        tapToPlay.setStyle("-fx-font-weight: bold");

        FadeTransition blink = new FadeTransition(Duration.seconds(0.8), tapToPlay);
        blink.setFromValue(2.0);
        blink.setToValue(0.4);
        blink.setCycleCount(Animation.INDEFINITE);
        blink.setAutoReverse(true);
        blink.play();

        VBox contentBox = new VBox(20, playButtonImage, tapToPlay);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPadding(new Insets(360, 0, 0, 0));

        startRoot.getChildren().add(contentBox);

        Scene startScene = new Scene(startRoot, 800, 600);
        primaryStage.setTitle("Math Roguelike");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    private void showGameScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
            Scene gameScene = new Scene(loader.load(), 800, 600);
            //ukuran tetap saat masuk game
            primaryStage.setScene(gameScene);
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
