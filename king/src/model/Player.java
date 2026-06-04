package model;

import java.util.ArrayList;

public class Player extends Person {
    private int level;
    private double winRate;
    private ArrayList<Hero> heroes;
    private Team team;

    public Player(String id, String name, String password, int level) {
        super(id, name, password);
    }

    @Override
    public boolean login(String inputPassword) { return false; }

    @Override
    public String getRole() { return null; }

    public int getLevel() { return 0; }
    public void setLevel(int level) { }
    public double getWinRate() { return 0.0; }
    public void setWinRate(double winRate) { }
    public ArrayList<Hero> getHeroes() { return null; }
    public void setHeroes(ArrayList<Hero> heroes) { }
    public Team getTeam() { return null; }
    public void setTeam(Team team) { }

    public void addHero(Hero hero) { }
    public int getHeroCount() { return 0; }
    public String getTeamName() { return null; }

    @Override
    public String toString() { return null; }
}