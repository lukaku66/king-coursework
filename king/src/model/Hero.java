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
    private ArrayList<Equipment> compatibleEquipment;
    private ArrayList<Player> owners;

    public Hero(String id, String name, HeroType type, int hp, int attack, int defense, int speed) {
        // TODO: assign fields, init collections
    }

    public String getId() { return null; }
    public String getName() { return null; }
    public HeroType getType() { return null; }
    public int getHp() { return 0; }
    public int getAttack() { return 0; }
    public int getDefense() { return 0; }
    public int getSpeed() { return 0; }
    public ArrayList<Equipment> getCompatibleEquipment() { return null; }
    public ArrayList<Player> getOwners() { return null; }

    public void addCompatibleEquipment(Equipment eq) { }
    public void addOwner(Player player) { }
    public void removeOwner(Player player) { }
    public ArrayList<String> getOwnerNames() { return null; }

    @Override
    public boolean equals(Object obj) { return false; }

    @Override
    public int hashCode() { return 0; }

    @Override
    public String toString() { return null; }
}