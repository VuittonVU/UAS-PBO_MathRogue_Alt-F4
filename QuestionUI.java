public class QuestionUI extends Application {

    @Override
    public void start(Stage stage) {
        String questionId = "q1"; // Change as needed
        String template = /* Load from DB or mock */ """
        Seorang pedagang buah memiliki {x} buah apel...
        """;

        Map<String, Object> vars = QuestionLoader.randomizeVars(questionId);
        String finalQuestion = QuestionLoader.fillTemplate(template, vars);

        TextArea questionArea = new TextArea(finalQuestion);
        questionArea.setWrapText(true);
        questionArea.setEditable(false);

        TextField answerField = new TextField();
        Button submitBtn = new Button("Submit");

        submitBtn.setOnAction(e -> {
            String userAnswer = answerField.getText();
            System.out.println("User's Answer: " + userAnswer);
            // You can log or save the input here
        });

        VBox layout = new VBox(10, questionArea, answerField, submitBtn);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 600, 300);
        stage.setTitle("Math Challenge");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
