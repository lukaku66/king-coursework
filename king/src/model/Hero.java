package model;

import java.util.ArrayList;

public class Hero {
    private String id;
    private String name;
    private HeroType type;
    private int hp;
    private int attack;
    private int defense;
    private int speed;

    // Aggregation: Hero ──◇ Equipment (interface type)
    private ArrayList<Equipment> compatibleEquipment;

    // Bidirectional: Hero knows which Players own it
    private ArrayList<Player> owners;

    public Hero(String id, String name, HeroType type, int hp, int attack, int defense, int speed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.compatibleEquipment = new ArrayList<>();
        this.owners = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public HeroType getType() { return type; }
    public int getHp() { return hp; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public ArrayList<Equipment> getCompatibleEquipment() { return compatibleEquipment; }
    public ArrayList<Player> getOwners() { return owners; }

    public void addCompatibleEquipment(Equipment eq) {
        if (eq != null && !compatibleEquipment.contains(eq)) {
            compatibleEquipment.add(eq);
        }
    }

    // Called by Player.addHero() to maintain bidirectional sync
    public void addOwner(Player player) {
        if (player != null && !owners.contains(player)) {
            owners.add(player);
        }
    }

    // Called by Player.removeHero() to maintain bidirectional sync
    public void removeOwner(Player player) {
        owners.remove(player);
    }

    public ArrayList<String> getOwnerNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Player p : owners) {
            names.add(p.getName());
        }
        return names;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Hero)) return false;
        return id.equals(((Hero) obj).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[%s] %s  Type: %s  HP:%d  ATK:%d  DEF:%d  SPD:%d",
                id, name, type, hp, attack, defense, speed));
        sb.append("\n  Compatible Equipment: ");
        if (compatibleEquipment.isEmpty()) {
            sb.append("None");
        } else {
            for (Equipment eq : compatibleEquipment) {
                sb.append("\n    - ").append(eq.getName());
            }
        }
        sb.append("\n  Owned by: ");
        if (owners.isEmpty()) {
            sb.append("None");
        } else {
            for (String ownerName : getOwnerNames()) {
                sb.append("\n    - ").append(ownerName);
            }
        }
        return sb.toString();
    }
}