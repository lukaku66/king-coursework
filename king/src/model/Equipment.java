package model;

import java.io.Serializable;

public interface Equipment extends Serializable {
    String getId();
    String getName();
    EquipmentType getType();
    int getAttackBonus();
    int getDefenseBonus();
}
