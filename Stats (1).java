public class Stats {
    private String name;
    private int hp;
    private int attackDamage;
    private int defense;

    private double critRate = 20.0;     // Base crit rate: 20%
    private double critDamage = 150.0;  // Base crit damage: 150%

    public Stats(String name, int hp, int attackDamage, int defense) {
        this.name = name;
        this.hp = hp;
        this.attackDamage = attackDamage;
        this.defense = defense;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getAttackDamage() { return attackDamage; }
    public int getDefense() { return defense; }
    public double getCritRate() { return critRate; }
    public double getCritDamage() { return critDamage; }

    public void setHp(int hp) { this.hp = hp; }
    public void setAttackDamage(int attackDamage) { this.attackDamage = attackDamage; }
    public void setDefense(int defense) { this.defense = defense; }

    public void increaseCritRate(double amount) { this.critRate += amount; }
    public void increaseCritDamage(double amount) { this.critDamage += amount; }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
        System.out.println("💢 " + name + " took " + damage + " damage!");
    }

    public void showStats() {
        System.out.printf("Name: %s | HP: %d | ATK: %d | DEF: %d | CRIT RATE: %.1f%% | CRIT DMG: %.1f%%\n",
                name, hp, attackDamage, defense, critRate, critDamage);
    }
}
