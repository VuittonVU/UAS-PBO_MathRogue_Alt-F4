public class QuestionLoader {
    static Map<String, Object> randomizeVars(String questionId) {
        Random rand = new Random();
        Map<String, Object> vars = new HashMap<>();

        switch (questionId) {
            case "q1" -> {
                int x = rand.nextInt(20, 100);
                int a = rand.nextInt(2, 10);
                int y = rand.nextInt(5, 20);
                int b = rand.nextInt(1, x - 1);
                int z = rand.nextInt(1, a * y - 1);
                vars.put("x", x);
                vars.put("a", a);
                vars.put("y", y);
                vars.put("b", b);
                vars.put("z", z);
            }
            case "q2" -> {
                vars.put("x", rand.nextInt(100, 500)); // tepung
                vars.put("y", rand.nextInt(50, 200));  // gula
                vars.put("z", rand.nextInt(1, 10));    // resep
            }
            case "q3" -> {
                vars.put("x", rand.nextInt(10, 100)); // panjang tangga
                int[] angles = {30, 45, 60};
                vars.put("y", angles[rand.nextInt(angles.length)]); // sudut khusus
            }
        }
        return vars;
    }

    static String fillTemplate(String template, Map<String, Object> vars) {
        for (String key : vars.keySet()) {
            template = template.replace("{" + key + "}", String.valueOf(vars.get(key)));
        }
        return template;
    }
}
