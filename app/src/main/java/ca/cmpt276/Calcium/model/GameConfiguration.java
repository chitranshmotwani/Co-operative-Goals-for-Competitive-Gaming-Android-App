package ca.cmpt276.Calcium.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GameConfiguration {
    private ArrayList<Game> gameList = new ArrayList<>();
    private String name = null;
    private String scoreSystemDescription = null;
    private int highPerPlayerScore = 0;
    private int lowPerPlayerScore = 0;

    public enum AchievementLevel {
        LEVEL_1, //highest score
        LEVEL_2,
        LEVEL_3,
        LEVEL_4,
        LEVEL_5,
        LEVEL_6,
        LEVEL_7,
        LEVEL_8,
        LEVEL_9 //lowest score*/
    }

    public GameConfiguration(String name, String scoreSystemDescription, int highPerPlayerScore, int lowPerPlayerScore) {
        this.name = name;
        this.scoreSystemDescription = scoreSystemDescription;
        this.highPerPlayerScore = highPerPlayerScore;
        this.lowPerPlayerScore = lowPerPlayerScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScoreSystemDescription() {
        return scoreSystemDescription;
    }

    public void setScoreSystemDescription(String description) {
        this.scoreSystemDescription = description;
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

    public Game getGame(int index) {
        return gameList.get(index);
    }

    public void addGame(int numPlayers, int score) {
        int achievementLevelIndex = calculateAchievementLevel(numPlayers, score);
        AchievementLevel levelObtained = AchievementLevel.values()[achievementLevelIndex];

        gameList.add(new Game(LocalDateTime.now(), numPlayers, score, levelObtained));
    }

    private int calculateAchievementLevel(int numPlayers, int score) {
        float scaledHighScore = highPerPlayerScore * numPlayers;
        float scaledLowScore = lowPerPlayerScore * numPlayers;
        if(score < scaledLowScore) {
            //return the ranking that is below the expected low score
            return AchievementLevel.values().length - 1;
        }else if(score > scaledHighScore) {
            //return highest rank possible
            return 0;
        }

        //top and bottom tiers already accounted for
        float interval = (scaledHighScore - scaledLowScore) / ((AchievementLevel.values().length) - 2);
        int index = 0;
        for (int i = 0; i < AchievementLevel.values().length; i++) {
            if (score < (scaledLowScore + (interval * i))) {
                index = i + 1;
                break;
            }
        }

        //since the ranking is highest to lowest
        return AchievementLevel.values().length - index;
    }

    public class Game {
        private LocalDateTime dateTimeCreated;
        private int numPlayers = 0;
        private int score = 0;
        private AchievementLevel achievementLevel;

        public Game(LocalDateTime dateTimeCreated, int numPlayers, int score, AchievementLevel achievementLevel) {
            //no setter for dateTimeCreated as it should only be set on the game creation
            this.dateTimeCreated = dateTimeCreated;
            this.numPlayers = numPlayers;
            this.score = score;
            this.achievementLevel = achievementLevel;
        }

        public LocalDateTime getDateTimeCreated() {
            return dateTimeCreated;
        }


        public int getNumPlayers() {
            return numPlayers;
        }

        public void setNumPlayers(int numPlayers) {
            this.numPlayers = numPlayers;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public AchievementLevel getAchievementLevel() {
            return achievementLevel;
        }

        public void setAchievementLevel(AchievementLevel achievementLvl) {
            achievementLevel = achievementLvl;
        }
    }
}
