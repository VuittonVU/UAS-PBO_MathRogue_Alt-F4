
public class Buff {
    private int attackBuffs = 0;
    private int defenseBuffs = 0;
    private int hpBuffs = 0;

    private boolean ignoreEnemyDefense = false;
    private boolean defenseBecomesAttack = false;
    private boolean regenHp = false;

    private int attack = 10;
    private int defense = 10;
    private int hp = 100;

    public void applyBuff(String type) {
        switch (type.toLowerCase()) {
            case "attack":
                attack += 10;
                attackBuffs++;
                if (attackBuffs >= 2) {
                    ignoreEnemyDefense = true;
                }
                break;
            case "defense":
                defense += 10;
                defenseBuffs++;
                if (defenseBuffs >= 2) {
                    defenseBecomesAttack = true;
                }
                break;
            case "hp":
                hp += 20;
                hpBuffs++;
                if (hpBuffs >= 2) {
                    regenHp = true;
                }
                break;
            default:
                System.out.println("Invalid buff type.");
                break;
        }
    }

    public void showStats() {
        System.out.println("\nCurrent Stats:");
        System.out.println("Attack: " + attack + (ignoreEnemyDefense ? " (Ignores Enemy Defense!)" : ""));
        System.out.println("Defense: " + defense + (defenseBecomesAttack ? " (Also Adds to Attack!)" : ""));
        System.out.println("HP: " + hp + (regenHp ? " (Regenerates 5 HP per turn)" : ""));
    }

    public int getAttack() {
        return attack + (defenseBecomesAttack ? defense / 2 : 0);
    }

    public int getDefense() {
        return defense;
    }

    public int getHp() {
        return hp;
    }

    public boolean canIgnoreEnemyDefense() {
        return ignoreEnemyDefense;
    }

    public boolean canRegenHp() {
        return regenHp;
    }

    public static String[] getTwoRandomStats() {
        String[] all = {"attack", "defense", "hp"};
        java.util.Random rand = new java.util.Random();
        int i = rand.nextInt(3);
        int j;
        do {
            j = rand.nextInt(3);
        } while (j == i);
        return new String[]{all[i], all[j]};
    }
}
