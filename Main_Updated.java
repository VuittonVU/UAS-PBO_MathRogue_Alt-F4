import java.util.*;
import java.util.concurrent.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();

    private static volatile boolean inputReceived = false;
    private static volatile String playerAnswer = "";

    public static void main(String[] args) {
        Hero hero = new Hero("MathKnight", 100, 20, 0);
        List<Enemy> rooms = generateEnemies();

        System.out.println("🎮 Welcome to Math Roguelike!");
        System.out.println("Answer correctly to attack. Fail or timeout? Enemy strikes.");
        wait(2000);

        for (int i = 0; i < rooms.size(); i++) {
            Enemy enemy = rooms.get(i);

            System.out.println("\n" + "=".repeat(40));
            System.out.println("🚪 Entering Room " + (i + 1));
            System.out.println("⚔️  Enemy: " + enemy.getName());
            hero.showStats();
            enemy.showStats();

            while (hero.getHp() > 0 && enemy.getHp() > 0) {
                Question question = generateRandomQuestion();
                inputReceived = false;
                playerAnswer = "";

                System.out.println("\n🧠 Solve: " + question);
                System.out.println("⏳ You have 10 seconds to answer.");
                System.out.print(">> ");

                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<String> futureInput = executor.submit(() -> {
                    try {
                        return scanner.nextLine();
                    } catch (Exception e) {
                        return null;
                    }
                });

                try {
                    playerAnswer = futureInput.get(10, TimeUnit.SECONDS);
                    inputReceived = true;
                } catch (TimeoutException e) {
                    System.out.println("\n❌ Timeout! Enemy attacks!");
                    hero.takeDamage(Math.max(0, enemy.getAttackDamage() - hero.getBuffedDefense()));
                    System.out.printf("❤️ Hero: %d HP | 💀 Enemy: %d HP\n", hero.getHp(), enemy.getHp());
                    System.out.println("Enter any key to continue...");
                    inputReceived = false;
                } catch (Exception e) {
                    inputReceived = false;
                }

                futureInput.cancel(true);
                executor.shutdownNow();
                flushStuckInput(); // 🧹 bersihkan input yang nyangkut

                if (!inputReceived) {
                    wait(1000);
                    continue;
                }

                try {
                    int ans = Integer.parseInt(playerAnswer.trim());
                    if (ans == question.getAnswer()) {
                        System.out.println("✅ Correct! You attack.");
                        enemy.takeDamage(Math.max(0, hero.getBuffedAttack() - enemy.getDefense()));
                        if (rand.nextInt(4) == 0) {
                            hero.addBuff(new Buff("Power Surge", "attack_up", 3));
                        }
                    } else {
                        System.out.println("❌ Wrong! Enemy attacks.");
                        hero.takeDamage(Math.max(0, enemy.getAttackDamage() - hero.getBuffedDefense()));
                        System.out.printf("❤️ Hero: %d HP | 💀 Enemy: %d HP\n", hero.getHp(), enemy.getHp());
                    }
                } catch (Exception e) {
                    System.out.println("❌ Invalid input! Enemy attacks.");
                    hero.takeDamage(Math.max(0, enemy.getAttackDamage() - hero.getBuffedDefense()));
                    System.out.printf("❤️ Hero: %d HP | 💀 Enemy: %d HP\n", hero.getHp(), enemy.getHp());
                }

                System.out.printf("❤️ Hero: %d HP | 💀 Enemy: %d HP\n", hero.getHp(), enemy.getHp());
                hero.updateBuffs();
                hero.showBuffs();
                wait(1500);
            }

            if (hero.getHp() <= 0) {
                System.out.println("\n💀 You were defeated in Room " + (i + 1));
                return;
            } else {
                System.out.println("🏆 Enemy defeated!");
                wait(1000);
            }
        }

        System.out.println("\n🎉 You cleared all 12 rooms! Math master!");
    }

    public static List<Enemy> generateEnemies() {
        List<Enemy> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            if (i == 12) {
                list.add(new EnemyBoss("Final Boss", 80, 25, 5, "Dark Nova"));
            } else {
                list.add(new Enemy("Enemy " + i, 50, 15, 0));
            }
        }
        return list;
    }

    public static Question generateRandomQuestion() {
        int a = rand.nextInt(10) + 1;
        int b = rand.nextInt(10) + 1;
        String[] ops = {"+", "-", "*"};
        String op = ops[rand.nextInt(ops.length)];

        String text = a + " " + op + " " + b;
        int result = switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> 0;
        };

        return new Question(text, result);
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void flushStuckInput() {
        try {
            while (System.in.available() > 0) {
                System.in.read(); // buang karakter yang masih tersisa
            }
        } catch (Exception ignored) {}
    }
}
