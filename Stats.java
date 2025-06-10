public class Stats {
    private String name;
    private int hp;
    private int attackDamage;
    private int defense;

    public Stats(String name, int hp, int attackDamage, int defense) {
        this.name = name;
        this.hp = hp;
        this.attackDamage = attackDamage;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getDefense() {
        return defense;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
        System.out.println("💢 " + name + " took " + damage + " damage!");
    }

    public void showStats() {
        System.out.println("Name: " + name + " | HP: " + hp + " | ATK: " + attackDamage + " | DEF: " + defense);
    }
}
