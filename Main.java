import java.util.*;
import java.util.concurrent.*;
import java.sql.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();

    private static volatile boolean inputReceived = false;
    private static volatile String playerAnswer = "";

    public static void main(String[] args) {
        QuestionDAO.initTable(); // ✅ Buat table jika belum ada
        Hero hero = new Hero("Marco", 100, 20, 0);
        List<Enemy> rooms = generateEnemies();

        System.out.println("🎮 Welcome to Math Roguelike!");
        System.out.println("Answer correctly to attack. Fail or timeout? Enemy strikes.");
        wait(1000);

        for (int i = 0; i < rooms.size(); i++) {
            Enemy enemy = rooms.get(i);

            System.out.println("\n" + "=".repeat(40));
            System.out.println("🚪 Entering Room " + (i + 1));
            System.out.println("⚔️  Enemy: " + enemy.getName());
            hero.showStats();
            enemy.showStats();

            while (hero.getHp() > 0 && enemy.getHp() > 0) {
                Question question = QuestionDAO.getRandomFilledQuestion(); // 🧠 DARI DATABASE
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
                    hero.takeDamage(Math.max(0, enemy.getAttackDamage() - hero.getDefense()));
                    hero.showStats();
                } catch (Exception e) {
                    inputReceived = false;
                }

                executor.shutdownNow();

                if (!inputReceived) {
                    wait(1000);
                    continue;
                }

                try {
                    int ans = Integer.parseInt(playerAnswer.trim());
                    if (ans == question.getAnswer()) {
                        System.out.println("✅ Correct! You attack.");
                        int damage = hero.calculateDamage(enemy.getDefense());
                        enemy.takeDamage(damage);
                    } else {
                        System.out.println("❌ Wrong! Enemy attacks.");
                        hero.takeDamage(Math.max(0, enemy.getAttackDamage() - hero.getDefense()));
                        hero.showStats();
                    }
                } catch (Exception e) {
                    System.out.println("❌ Invalid input! Enemy attacks.");
                    hero.takeDamage(Math.max(0, enemy.getAttackDamage() - hero.getDefense()));
                    hero.showStats();
                }

                System.out.printf("❤️ Hero: %d HP | 💀 Enemy: %d HP\n", hero.getHp(), enemy.getHp());
                hero.regenerate();
                wait(1500);
            }

            if (hero.getHp() <= 0) {
                System.out.println("\n💀 You were defeated in Room " + (i + 1));
                return;
            } else {
                System.out.println("🏆 Enemy defeated!");

                List<String> buffs = hero.getBuff().getRandomBuffOptions();
                System.out.println("✨ Choose a buff: " + buffs);
                String choice = scanner.nextLine().trim().toUpperCase();
                if (buffs.contains(choice)) {
                    hero.applyBuff(choice);
                } else {
                    System.out.println("❌ Invalid buff choice.");
                }
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

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
