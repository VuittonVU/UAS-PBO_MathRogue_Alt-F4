
import java.util.*;

public class Buff {
    private int hpBuffCount = 0;
    private int atkBuffCount = 0;
    private int defBuffCount = 0;

    private boolean ignoreEnemyDef = false;
    private boolean atkPlusDef = false;
    private boolean regenHP = false;

    public void applyBuff(String buffType, Stats stats) {
        switch (buffType) {
            case "HP":
                stats.setHp(stats.getHp() + 20);
                hpBuffCount++;
                break;
            case "ATK":
                stats.setAtk(stats.getAtk() + 5);
                atkBuffCount++;
                break;
            case "DEF":
                stats.setDef(stats.getDef() + 3);
                defBuffCount++;
                break;
        }

        checkBuffBonus();
    }

    private void checkBuffBonus() {
        if (atkBuffCount >= 3) ignoreEnemyDef = true;
        if (defBuffCount >= 3) atkPlusDef = true;
        if (hpBuffCount >= 3) regenHP = true;
    }

    public int calculateDamage(Stats stats, int enemyDef) {
        int baseAtk = stats.getAtk();
        if (atkPlusDef) {
            baseAtk += stats.getDef();
        }

        if (ignoreEnemyDef) {
            return baseAtk;
        } else {
            return Math.max(0, baseAtk - enemyDef);
        }
    }

    public void regenerate(Stats stats) {
        if (regenHP) {
            stats.setHp(stats.getHp() + 5);
        }
    }

    public List<String> getRandomBuffOptions() {
        List<String> buffs = Arrays.asList("HP", "ATK", "DEF");
        Collections.shuffle(buffs);
        return buffs.subList(0, 2);
    }
}
