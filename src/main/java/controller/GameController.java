package controller;

import game.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameController {
    @FXML private Label questionLabel, resultLabel;
    @FXML private Label lblHeroHp, lblHeroAttack;
    @FXML private Label lblEnemyName, lblEnemyHp, lblEnemyAttack;
    @FXML private TextField answerField;
    @FXML private Button submitButton;
    @FXML private AnchorPane rootPane;
    @FXML private Pane portalPane;
    @FXML private ImageView heroImage, enemyImage;
    @FXML private Label timerLabel;
    @FXML private VBox battleUI;
    @FXML private VBox enemyUI;
    @FXML private Label lblRoom;
    @FXML private Button btnStats;

    private Hero hero;
    private Enemy currentEnemy;
    private Question currentQuestion;
    private Timeline timer;
    private PortalManager portalManager = new PortalManager();

    public GameController() {
        this.portalManager = new PortalManager();
    }

    public void initialize() {
        Image backgroundImage = new Image(getClass().getResource("/background/Background2.png").toExternalForm());
        BackgroundImage bg = new BackgroundImage(
                backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        rootPane.setBackground(new Background(bg));

        hero = new Hero("Hero", 100, 10, 5);
        startRoom();
    }

    private void startRoom() {
        hideGameUI();
        lblRoom.setText("Room " + portalManager.getCurrentRoom());
        submitButton.setOnAction(null);
        if (portalManager.isBossRoom()) enterRedPortal();
        else showPortals(false);
    }

    private void showPortals(boolean onlyRed) {
        portalPane.getChildren().clear();

        ImageView red = createPortal("/assets/PortalRed.png", 200, 80, this::enterRedPortal);
        portalPane.getChildren().add(red);

        if (!onlyRed) {
            ImageView blue = createPortal("/assets/PortalBlue.png", 480, 80, this::enterBluePortal);
            portalPane.getChildren().add(blue);
        }

        updateHealthLabels();

        heroImage.setVisible(true);
        lblHeroHp.setVisible(true);
        lblHeroAttack.setVisible(true);

        enemyImage.setVisible(false);
        lblEnemyName.setVisible(false);
        lblEnemyHp.setVisible(false);
        lblEnemyAttack.setVisible(false);

        battleUI.setVisible(true);
        questionLabel.setVisible(true);
        answerField.setVisible(false);
        submitButton.setVisible(false);
        resultLabel.setVisible(false);
        questionLabel.setText("Pilih portal Red (musuh) atau Blue (waktu)");
    }

    private ImageView createPortal(String path, double x, double y, Runnable onClick) {
        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream(path)));
        iv.setFitWidth(120);
        iv.setFitHeight(180);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        iv.setOnMouseClicked(e -> onClick.run());
        return iv;
    }

    private void enterRedPortal() {
        portalManager.setPortalColor("red"); 
        portalPane.getChildren().clear();
        resultLabel.setText("Melawan musuh");

        currentEnemy = portalManager.isBossRoom()
                ? generateRandomBoss()
                : generateRandomEnemy();
        showBattleUI();
    }

    private void enterBluePortal() {
        portalManager.setPortalColor("blue"); 
        portalPane.getChildren().clear();

        updateHealthLabels();
        enemyImage.setVisible(false);
        lblEnemyName.setVisible(false);
        lblEnemyHp.setVisible(false);
        lblEnemyAttack.setVisible(false);
        enemyUI.setVisible(false);

        heroImage.setVisible(true);
        lblHeroHp.setVisible(true);
        lblHeroAttack.setVisible(true);

        battleUI.setVisible(true);
        questionLabel.setVisible(true);
        answerField.setVisible(true);
        submitButton.setVisible(true);
        resultLabel.setVisible(true);
        timerLabel.setVisible(true);

        generateQuestion();
        resultLabel.setText("");

        timerLabel.setText("Time left: 120");

        if (timer != null) timer.stop();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            int time = Integer.parseInt(timerLabel.getText().replaceAll("\\D+", "")) - 1;
            timerLabel.setText("Time left: " + time);
            if (time <= 0) {
                timer.stop();
                hero.reduceHpByPercentage(50);
                resultLabel.setText("Waktu habis! HP berkurang 50%.");
                Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                    portalManager.nextRoom();
                    startRoom();
                }));
                delay.setCycleCount(1);
                delay.play();
            }
        }));
        timer.setCycleCount(60);
        timer.play();

        submitButton.setDisable(false);
        submitButton.setOnAction(e -> {
            try {
                int answer = Integer.parseInt(answerField.getText().trim());
                if (currentQuestion.isCorrect(answer)) {
                    timer.stop();
                    resultLabel.setText("✅ Benar!");

                    Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                        applyBuffChoice();
                    }));
                    delay.setCycleCount(1);
                    delay.play();
                } else {
                    resultLabel.setText("❌ Jawaban salah. HP dikurang 50%");
                    hero.reduceHpByPercentage(50);
                    updateHealthLabels();
                    timer.stop();

                    Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                        portalManager.nextRoom();
                        startRoom();
                    }));
                    delay.setCycleCount(1);
                    delay.play();
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("⚠️ Masukkan angka yang valid!");
            }
        });
    }

    private void applyBuffChoice() {
        List<String> options = hero.getBuff().getRandomBuffOptions();

        questionLabel.setVisible(true);
        answerField.setVisible(true);
        submitButton.setVisible(true);
        resultLabel.setVisible(true);

        questionLabel.setText("Pilih Buff:\n1. " + options.get(0) + " / 2." + options.get(1));
        resultLabel.setText("Ketik 1 atau 2, lalu tekan Submit");
        answerField.clear();

        submitButton.setDisable(false);
        submitButton.setOnAction(e -> {
            String input = answerField.getText().trim();
            if (input.equals("1") || input.equals("2")) {
                int choice = Integer.parseInt(input);
                String chosenBuff = options.get(choice - 1);
                hero.applyBuff(chosenBuff);
                hero.regenerate();
                resultLabel.setText("✅ Buff \"" + chosenBuff + "\" diterapkan!");

                Timeline delay = new Timeline(new KeyFrame(Duration.seconds(2), ev -> {
                    portalManager.nextRoom();
                    startRoom();
                }));
                delay.setCycleCount(1);
                delay.play();
            } else {
                resultLabel.setText("❌ Pilihan tidak valid. Ketik 1 atau 2 saja.");
            }
        });
    }

    private void showBattleUI() {
        portalPane.getChildren().clear();

        questionLabel.setVisible(true);
        answerField.setVisible(true);
        submitButton.setVisible(true);
        resultLabel.setVisible(true);
        heroImage.setVisible(true);
        enemyImage.setVisible(true);
        lblHeroHp.setVisible(true);
        lblHeroAttack.setVisible(true);
        enemyUI.setVisible(true);
        lblEnemyName.setVisible(true);
        lblEnemyHp.setVisible(true);
        lblEnemyAttack.setVisible(true);

        updateHealthLabels();
        generateQuestion();

        submitButton.setDisable(false);
        submitButton.setOnAction(e -> {
            String input = answerField.getText().trim();
            if (input.isEmpty()) {
                resultLabel.setText("Jawaban tidak boleh kosong.");
                return;
            }
            try {
                int answer = Integer.parseInt(input);
                handleAnswer(answer);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Masukkan angka yang valid.");
            }
        });
    }

    private void hideGameUI() {
        timerLabel.setVisible(false);
        questionLabel.setVisible(false);
        answerField.setVisible(false);
        submitButton.setVisible(false);
        resultLabel.setVisible(false);
        heroImage.setVisible(false);
        enemyUI.setVisible(false);
        enemyImage.setVisible(false);
        lblHeroHp.setVisible(false);
        lblHeroAttack.setVisible(false);
        lblEnemyName.setVisible(false);
        lblEnemyHp.setVisible(false);
        lblEnemyAttack.setVisible(false);
    }

    private void handleAnswer(int answer) {
        submitButton.setDisable(true);
        boolean correct = currentQuestion.isCorrect(answer);
        int dmg;

        if (correct) {
            dmg = hero.calculateDamage(currentEnemy.getDefense());
            currentEnemy.takeDamage(dmg);
            resultLabel.setText("Jawaban benar! Musuh terkena " + dmg + " damage.");
        } else {
            dmg = new Random().nextInt(10) + 5;
            hero.takeDamage(dmg);
            resultLabel.setText("Jawaban salah! Kamu terkena " + dmg + " damage.\n");

            if (currentEnemy instanceof EnemyBoss boss) {
                boss.useSkill(hero);
            }
        }

        updateHealthLabels();

        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            resultLabel.setText("");
            if (hero.isDead()) {
                showGameOverScreen();
            } else if (currentEnemy.isDead()) {
                submitButton.setOnAction(null);
                if (portalManager.isBossRoom()) {
                    showVictoryScreen();
                } else {
                    applyBuffChoice();
                }
            } else {
                generateQuestion();
                submitButton.setDisable(false);
            }
        }));
        delay.setCycleCount(1);
        delay.play();
    }

    private void updateHealthLabels() {
        lblHeroHp.setText("HP: " + hero.getHp());
        lblHeroAttack.setText("Attack: " + hero.getAttackDamage());

        if (currentEnemy == null) return;
        lblEnemyName.setText("\uD83D\uDC80 " + currentEnemy.getName() + " \uD83D\uDC80");
        lblEnemyHp.setText("HP: " + currentEnemy.getHp());
        lblEnemyAttack.setText("Attack: " + currentEnemy.getAttackDamage());
        enemyImage.setImage(new Image(getClass().getResourceAsStream(getEnemyImagePath(currentEnemy.getCategory()))));
    }

    @FXML
    private void showHeroStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("\uD83D\uDCCA HERO STATS\n");
        sb.append("HP: ").append(hero.getHp()).append("\n");
        sb.append("Attack: ").append(hero.getAttackDamage()).append("\n\n");

        sb.append("\u2728 BUFF YANG DIPEROLEH:\n");
        List<String> activeBuffs = hero.getBuff().getActiveBuffs();
        if (activeBuffs.isEmpty()) {
            sb.append("Belum ada buff.\n");
        } else {
            for (int i = 0; i < activeBuffs.size(); i++) {
                sb.append(i + 1).append(". ").append(activeBuffs.get(i)).append("\n");
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Statistik Hero");
        alert.setHeaderText("Detail Hero dan Buff");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    private void generateQuestion() {
        int currentRoom = portalManager.getCurrentRoom();
        String currentColor = portalManager.getCurrentPortalColor();

        String query = "SELECT question_id, question FROM Question";
        List<String> filters = new ArrayList<>();

        if (currentColor.equalsIgnoreCase("red")) {
            filters.add("color = 'red'");
        } else if (currentColor.equalsIgnoreCase("blue")) {
            filters.add("color = 'blue'");
        }

        if (currentRoom < 6) {
            filters.add("difficulty = 'e'");
        } else if (currentRoom >= 6 && currentRoom < 11) {
            filters.add("difficulty = 'h'");
        }

        if (!filters.isEmpty()) {
            query += " WHERE " + String.join(" AND ", filters);
        }

        query += " ORDER BY RANDOM() LIMIT 1";

        try (Connection conn = DB.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                int qID = rs.getInt("question_id");
                String qText = rs.getString("question");
                currentQuestion = Question.fromTemplate(qText, qID);
                questionLabel.setText(currentQuestion.getQuestion());
                answerField.clear();

                System.out.println("[DEBUG] Jawaban benar: " + currentQuestion.getCorrectAnswer());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            questionLabel.setText("Gagal mengambil soal.");
        }
    }

    private EnemyBoss generateRandomBoss() {
        Random rand = new Random();
        int index = rand.nextInt(3);

        return switch (index) {
            case 0 -> new EnemyBoss("Lord Regenera", 200, 30, 12, EnemyCategory.BossSatu, "Regen");
            case 1 -> new EnemyBoss("Critigon", 170, 35, 10, EnemyCategory.BossDua, "Critical");
            case 2 -> new EnemyBoss("Defendrix", 250, 22, 20, EnemyCategory.BossTiga, "Def+");
            default -> throw new IllegalStateException("Unexpected boss index: " + index);
        };
    }

    private Enemy generateRandomEnemy() {
        Random rand = new Random();
        EnemyCategory category = EnemyCategory.values()[rand.nextInt(3)];
        if (portalManager.getCurrentRoom() < 6) {
            return new Enemy(category.toString(), 10 + rand.nextInt(30), 10 + rand.nextInt(10), 2, category);
        } else {
            return new Enemy(category.toString(), 10 + rand.nextInt(60), 10 + rand.nextInt(20), 2 + rand.nextInt(5), category);
        }
    }

    private String getEnemyImagePath(EnemyCategory category) {
        return switch (category) {
            case MonsterSatu, MonsterDua, MonsterTiga -> "/monsters/monster" + (new Random().nextInt(3) + 1) + ".png";
            case BossSatu -> "/bosses/boss1.png";
            case BossDua -> "/bosses/boss2.png";
            case BossTiga -> "/bosses/boss3.png";
        };
    }

    private void showVictoryScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VictoryScreen.fxml"));
            Scene victoryScene = new Scene(loader.load());
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(victoryScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGameOverScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameOverScreen.fxml"));
            Scene gameOverScene = new Scene(loader.load());
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(gameOverScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
