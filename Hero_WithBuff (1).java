
public class Hero {
    private Stats stats;
    private Buff buff;

    public Hero() {
        stats = new Stats(100, 10, 5); // Example base stats
        buff = new Buff();
    }

    public Stats getStats() {
        return stats;
    }

    public Buff getBuff() {
        return buff;
    }

    public void applyBuff(String buffType) {
        buff.applyBuff(buffType, stats);
    }

    public int calculateDamage(int enemyDef) {
        return buff.calculateDamage(stats, enemyDef);
    }

    public void regenerate() {
        buff.regenerate(stats);
    }
}
