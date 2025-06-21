package game;

public class Enemy extends Stats {
    private EnemyCategory category;

    public Enemy(String name, int hp, int attackDamage, int defense, EnemyCategory category) {
        super(name, hp, attackDamage, defense);
        this.category = category;
    }

    public EnemyCategory getCategory() {
        return category;
    }
}
