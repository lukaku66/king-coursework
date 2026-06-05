package model;

import java.util.ArrayList;

public class Player extends Person {
    private int level;
    private double winRate;
    private ArrayList<Hero> heroes;
    private Team team;

    public Player(String id, String name, String password, int level) {
        super(id, name, password);
        this.level = level;
        this.winRate = 0.0;
        this.heroes = new ArrayList<>();
        this.team = null;
    }

    @Override
    public boolean login(String inputPassword) {
        return getPassword().equals(inputPassword);
    }

    @Override
    public String getRole() {
        return "Player";
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public double getWinRate() { return winRate; }
    public void setWinRate(double winRate) { this.winRate = winRate; }

    public ArrayList<Hero> getHeroes() { return heroes; }
    public void setHeroes(ArrayList<Hero> heroes) { this.heroes = heroes; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    // Bidirectional sync: Player owns Hero, Hero knows its owner
    public void addHero(Hero hero) {
        if (hero != null && !heroes.contains(hero)) {
            heroes.add(hero);
            hero.addOwner(this);   // sync reverse side
        }
    }

    public void removeHero(Hero hero) {
        if (hero != null && heroes.contains(hero)) {
            heroes.remove(hero);
            hero.removeOwner(this); // sync reverse side
        }
    }

    public int getHeroCount() { return heroes.size(); }

    public String getTeamName() {
        return (team != null) ? team.getName() : "None";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Level:%d | WinRate:%.0f%% | Heroes:%d | Team:%s",
                getId(), getName(), level, winRate * 100, getHeroCount(), getTeamName());
    }
}