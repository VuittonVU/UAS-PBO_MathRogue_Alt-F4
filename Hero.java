import java.util.ArrayList;
import java.util.List;

public class Hero extends Stats {
    private List<Buff> activeBuffs;

    public Hero(String name, int hp, int attackDamage, int defense) {
        super(name, hp, attackDamage, defense);
        this.activeBuffs = new ArrayList<>();
    }

    // Tambah buff baru ke hero
    public void addBuff(Buff buff) {
        activeBuffs.add(buff);
        System.out.println("✨ Buff applied: " + buff.getName());
    }

    // Kurangi durasi buff, hapus kalau expired
    public void updateBuffs() {
        List<Buff> toRemove = new ArrayList<>();
        for (Buff buff : activeBuffs) {
            buff.decreaseDuration();
            if (buff.isExpired()) {
                toRemove.add(buff);
            }
        }
        if (!toRemove.isEmpty()) {
            System.out.println("⏳ Buff expired:");
            for (Buff b : toRemove) {
                System.out.println(" - " + b.getName());
            }
        }
        activeBuffs.removeAll(toRemove);
    }

    // Hitung total serangan termasuk buff
    public int getBuffedAttack() {
        int bonus = 0;
        for (Buff buff : activeBuffs) {
            if (buff.getEffect().equals("attack_up")) {
                bonus += 5; // bonus attack per buff
            }
        }
        return getAttackDamage() + bonus;
    }

    // Hitung total defense termasuk buff
    public int getBuffedDefense() {
        int bonus = 0;
        for (Buff buff : activeBuffs) {
            if (buff.getEffect().equals("defense_up")) {
                bonus += 3; // bonus defense per buff
            }
        }
        return getDefense() + bonus;
    }

    // Tampilkan buff aktif
    public void showBuffs() {
        if (activeBuffs.isEmpty()) {
            System.out.println("🌀 No active buffs.");
        } else {
            System.out.println("🌀 Active Buffs:");
            for (Buff buff : activeBuffs) {
                System.out.println(" - " + buff);
            }
        }
    }

    // Tampilkan status hero
    @Override
    public void showStats() {
        System.out.printf("🧝 Hero: %s | HP: %d | ATK: %d | DEF: %d\n",
                getName(), getHp(), getBuffedAttack(), getBuffedDefense());
    }
}
