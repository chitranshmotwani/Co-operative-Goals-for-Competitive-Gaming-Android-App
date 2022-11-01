package ca.cmpt276.Calcium.model;

import java.util.ArrayList;

public class GameConfiguration {
    private ArrayList<Game> gameList = new ArrayList<>();
    private String name = null;
    private int highPerPlayerScore = 0;
    private int lowPerPlayerScore = 0;

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void setGameList(ArrayList<Game> gameList) {
        this.gameList = gameList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHighPerPlayerScore() {
        return highPerPlayerScore;
    }

    public void setHighPerPlayerScore(int highPerPlayerScore) {
        this.highPerPlayerScore = highPerPlayerScore;
    }

    public int getLowPerPlayerScore() {
        return lowPerPlayerScore;
    }

    public void setLowPerPlayerScore(int lowPerPlayerScore) {
        this.lowPerPlayerScore = lowPerPlayerScore;
    }

    public class Game {
        private int numPlayers = 0;
        private int score = 0;

    }
}
