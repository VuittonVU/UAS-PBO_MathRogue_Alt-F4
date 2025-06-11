import java.util.*;

public class Buff {
    private int hpBuffCount = 0;
    private int atkBuffCount = 0;
    private int defBuffCount = 0;
    private int critRateBuffCount = 0;
    private int critDamageBuffCount = 0;

    private boolean ignoreEnemyDef = false;
    private boolean atkPlusDef = false;
    private boolean regenHP = false;

    private boolean guaranteedCrit = false;
    private boolean doubleCritMultiplier = false;
    private boolean usedGuaranteedCrit = false;
    private boolean usedDoubleCrit = false;

    public void applyBuff(String buffType, Stats stats) {
        switch (buffType) {
            case "HP" -> {
                stats.setHp(stats.getHp() + 20);
                hpBuffCount++;
            }
            case "ATK" -> {
                stats.setAttackDamage(stats.getAttackDamage() + 5);
                atkBuffCount++;
            }
            case "DEF" -> {
                stats.setDefense(stats.getDefense() + 3);
                defBuffCount++;
            }
            case "CRIT_RATE" -> {
                stats.increaseCritRate(5.0);
                critRateBuffCount++;
            }
            case "CRIT_DAMAGE" -> {
                stats.increaseCritDamage(20.0);
                critDamageBuffCount++;
            }
        }

        checkBuffBonus();
    }

    private void checkBuffBonus() {
        if (atkBuffCount >= 3) ignoreEnemyDef = true;
        if (defBuffCount >= 3) atkPlusDef = true;
        if (hpBuffCount >= 3) regenHP = true;

        if (critRateBuffCount >= 3) guaranteedCrit = true;
        if (critDamageBuffCount >= 3) doubleCritMultiplier = true;
    }

    public int calculateDamage(Stats stats, int enemyDef) {
        int baseAtk = stats.getAttackDamage();
        if (atkPlusDef) baseAtk += stats.getDefense();

        boolean isCrit = false;
        if (guaranteedCrit && !usedGuaranteedCrit) {
            isCrit = true;
            usedGuaranteedCrit = true;
        } else {
            int roll = new Random().nextInt(100) + 1;
            if (roll <= stats.getCritRate()) isCrit = true;
        }

        if (isCrit) {
            double multiplier = stats.getCritDamage() / 100.0;
            if (doubleCritMultiplier && !usedDoubleCrit) {
                multiplier *= 2;
                usedDoubleCrit = true;
            }
            baseAtk = (int)(baseAtk * multiplier);
        }

        return ignoreEnemyDef ? baseAtk : Math.max(0, baseAtk - enemyDef);
    }

    public void regenerate(Stats stats) {
        if (regenHP) stats.setHp(stats.getHp() + 5);
    }

    public List<String> getRandomBuffOptions() {
        List<String> buffs = Arrays.asList("HP", "ATK", "DEF", "CRIT_RATE", "CRIT_DAMAGE");
        Collections.shuffle(buffs);
        return buffs.subList(0, 2);
    }
}
