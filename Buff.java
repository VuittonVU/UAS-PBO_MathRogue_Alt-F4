public class Buff {
    private String name;
    private String effect; // Contoh: "damage_up", "defense_up"
    private int duration;  // berapa turn aktif

    public Buff(String name, String effect, int duration) {
        this.name = name;
        this.effect = effect;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public int getDuration() {
        return duration;
    }

    public void decreaseDuration() {
        duration--;
    }

    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String toString() {
        return name + " (" + effect + ", " + duration + " turns)";
    }
}
