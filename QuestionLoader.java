import java.util.*;

public class QuestionLoader {
    public static Map<String, Object> randomizeVars(String questionId) {
        Random rand = new Random();
        Map<String, Object> vars = new HashMap<>();

        switch (questionId) {
            case "q1" -> {
                int x = rand.nextInt(20, 100);
                int a = rand.nextInt(2, 10);
                int y = rand.nextInt(5, 20);
                int b = rand.nextInt(1, x - 1);
                int z = rand.nextInt(1, a * y - 1);
                vars.put("x", x); vars.put("a", a); vars.put("y", y);
                vars.put("b", b); vars.put("z", z);
            }
            case "q2" -> {
                vars.put("x", rand.nextInt(100, 500));
                vars.put("y", rand.nextInt(50, 200));
                vars.put("z", rand.nextInt(1, 10));
            }
            case "q3" -> {
                vars.put("x", rand.nextInt(10, 100));
                int[] angles = {30, 45, 60};
                vars.put("y", angles[rand.nextInt(angles.length)]);
            }
        }

        return vars;
    }

    public static String fillTemplate(String template, Map<String, Object> vars) {
        for (String key : vars.keySet()) {
            template = template.replace("{" + key + "}", String.valueOf(vars.get(key)));
        }
        return template;
    }

    public static int getAnswer(String questionId, Map<String, Object> vars) {
        return switch (questionId) {
            case "q1" -> ((int) vars.get("x") - (int) vars.get("b")) +
                    ((int) vars.get("a") * (int) vars.get("y") - (int) vars.get("z"));
            case "q2" -> ((int) vars.get("x") * (int) vars.get("z")) +
                    ((int) vars.get("y") * (int) vars.get("z"));
            case "q3" -> {
                double hypotenuse = (int) vars.get("x");
                int angle = (int) vars.get("y");
                double radians = Math.toRadians(angle);
                int height = (int) (hypotenuse * Math.sin(radians));
                yield height;
            }
            default -> 0;
        };
    }
}
