package game;

public class EnemyBoss extends Enemy {
    private String skill;
    private int skillCounter = 0;

    public EnemyBoss(String name, int hp, int attackDamage, int defense, EnemyCategory category, String skill) {
        super(name, hp, attackDamage, defense, category);
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void useSkill(Hero hero) {
        System.out.println("[SKILL] Boss uses skill: " + skill);
        switch (skill) {
            case "Regen":
                this.setHp(this.getHp() + 10);
                System.out.println("Boss memulihkan 10 HP!");
                break;
            case "Critical":
                int critDmg = (int) (getAttackDamage() * 2.0);
                hero.takeDamage(critDmg);
                System.out.println("Boss menyerang critical! Hero terkena " + critDmg + " damage!");
                break;
            case "Def+":
                this.setDefense(this.getDefense() + 3);
                System.out.println("Boss menaikkan defense sebanyak 3!");
                break;
        }
    }
}
