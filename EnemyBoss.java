public class EnemyBoss extends Enemy {
    private String skill;

    public EnemyBoss(String name, int hp, int attackDamage, int defense, String skill) {
        super(name, hp, attackDamage, defense);
        this.skill = skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    @Override
    public void showStats() {
        super.showStats();
        System.out.println("Skill: " + skill);
    }

    public void useSkill() {
        System.out.println("Boss uses skill: " + skill);
    }
}
