public class Hero extends Stats {
    private Buff buff = new Buff();

    public Hero(String name, int hp, int attackDamage, int defense) {
        super(name, hp, attackDamage, defense);
    }

    public Buff getBuff() {
        return buff;
    }

    public void applyBuff(String buffType) {
        buff.applyBuff(buffType, this); // this karena Hero adalah Stats
    }

    public int calculateDamage(int enemyDef) {
        return buff.calculateDamage(this, enemyDef);
    }

    public void regenerate() {
        buff.regenerate(this);
    }

    @Override
    public void showStats() {
        System.out.printf("🧝 Hero: %s | HP: %d | ATK: %d | DEF: %d\n",
                getName(), getHp(), getAttackDamage(), getDefense());
    }
}
