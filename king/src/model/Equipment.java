package model;

public interface Equipment {
    String getId();
    String getName();
    EquipmentType getType();
    int getAttackBonus();
    int getDefenseBonus();
}