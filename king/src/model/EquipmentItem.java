package model;

public class EquipmentItem implements Equipment {
    private String id;
    private String name;
    private EquipmentType type;
    private int attackBonus;
    private int defenseBonus;

    public EquipmentItem(String id, String name, EquipmentType type, int attackBonus, int defenseBonus) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public EquipmentType getType() { return type; }

    @Override
    public int getAttackBonus() { return attackBonus; }

    @Override
    public int getDefenseBonus() { return defenseBonus; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EquipmentItem)) return false;
        return id.equals(((EquipmentItem) obj).id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) ATK+%d DEF+%d", id, name, type, attackBonus, defenseBonus);
    }
}
