package ca.cmpt276.Calcium.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/* GameConfiguration holds all the information needed about a specific Game Configuration
*  This includes the name, description, high expected per player score and low expected per
*  player score. Additionally it stores the list of associated Games. Game is a nested class as we
*  do not expect a Game to exist without an associated GameConfiguration.
*  It supports adding, deleting, and changing a Game as well as returning the appropriate achievement
*  level given the scores.
*
 */
public class GameConfiguration {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd @ HH:mm a");

    private ArrayList<Game> gameList = new ArrayList<>();
    private String name;
    private String scoreSystemDescription;
    private int greatPerPlayerScore;
    private int poorPerPlayerScore;

    public ArrayList<Game> getGames() {
        return gameList;
    }

    public enum AchievementLevel {
        LEVEL_1, //highest score
        LEVEL_2,
        LEVEL_3,
        LEVEL_4,
        LEVEL_5,
        LEVEL_6,
        LEVEL_7,
        LEVEL_8,
        LEVEL_9,
        LEVEL_10 //lowest score*/
    }

    public GameConfiguration(String name, String scoreSystemDescription, int highPerPlayerScore, int lowPerPlayerScore) {
        this.name = name;
        this.scoreSystemDescription = scoreSystemDescription;
        this.greatPerPlayerScore = highPerPlayerScore;
        this.poorPerPlayerScore = lowPerPlayerScore;
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

    public int getGreatPerPlayerScore() {
        return greatPerPlayerScore;
    }

    public void setGreatPerPlayerScore(int greatPerPlayerScore) {
        this.greatPerPlayerScore = greatPerPlayerScore;
    }

    public int getPoorPerPlayerScore() {
        return poorPerPlayerScore;
    }

    public void setPoorPerPlayerScore(int poorPerPlayerScore) {
        this.poorPerPlayerScore = poorPerPlayerScore;
    }

    public Game getGame(int index) {
        return gameList.get(index);
    }

    public void addGame(int numPlayers, int score) {
        AchievementLevel levelObtained = calculateAchievementLevel(numPlayers, score);

        gameList.add(new Game(LocalDateTime.now(), numPlayers, score, levelObtained));
    }

    public void deleteGame(int index) {
        gameList.remove(index);
    }

    public int getNumOfGames() { return gameList.size(); }

    public void changeGameNumberOfPlayers(int index, int newNumberOfPlayers) {
        Game gameToBeChanged = gameList.get(index);
        gameToBeChanged.setNumPlayers(newNumberOfPlayers);
        gameToBeChanged.setAchievementLevel(
                calculateAchievementLevel(gameToBeChanged.getNumPlayers(), gameToBeChanged.getScore())
        );
    }

    public void changeGameScore(int index, int newScore) {
        Game gameToBeChanged = gameList.get(index);
        gameToBeChanged.setScore(newScore);
        gameToBeChanged.setAchievementLevel(
                calculateAchievementLevel(gameToBeChanged.getNumPlayers(), gameToBeChanged.getScore())
        );
    }

    public ArrayList<Integer> getMinimumScoresForAchievementLevels(int numPlayers) {
        float scaledGreatScore = greatPerPlayerScore * numPlayers;
        float scaledPoorScore = poorPerPlayerScore * numPlayers;
        float interval = (scaledGreatScore - scaledPoorScore) / ((AchievementLevel.values().length) - 2);

        ArrayList<Integer> minScores = new ArrayList<>();
        minScores.add(0);
        for (int i = 0; i < AchievementLevel.values().length - 1; i++) {
            minScores.add((int) (scaledPoorScore + (interval * i))); //type casting into an integer floors the value
        }

        Collections.reverse(minScores);
        return minScores;
    }

    private AchievementLevel calculateAchievementLevel(int numPlayers, int score) {
        float scaledGreatScore = greatPerPlayerScore * numPlayers;
        float scaledPoorScore = poorPerPlayerScore * numPlayers;
        if(score < scaledPoorScore) {
            //return the ranking that is below the expected poor score
            return AchievementLevel.values()[AchievementLevel.values().length - 1];
        }else if(score > scaledGreatScore) {
            //return highest rank possible
            return AchievementLevel.values()[0];
        }

        //top and bottom tiers already accounted for
        float interval = (scaledGreatScore - scaledPoorScore) / ((AchievementLevel.values().length) - 2);
        int index = 0;
        for (int i = 0; i < AchievementLevel.values().length; i++) {
            if (score < (int)((scaledPoorScore + (interval * i)))) {
                index = i + 1;
                break;
            }
        }

        //since the ranking is highest to lowest
        return AchievementLevel.values()[AchievementLevel.values().length - index];
    }

    public class Game {
        private final String dateTimeCreated;
        private int numPlayers;
        private int score;
        private AchievementLevel achievementLevel;
        private ArrayList<Integer> scoreList;

        public Game(LocalDateTime dateTimeCreated, int numPlayers, int score, AchievementLevel achievementLevel) {
            //no setter for dateTimeCreated as it should only be set on the game creation
            this.dateTimeCreated = dateTimeCreated.format(FORMATTER);
            this.numPlayers = numPlayers;
            this.score = score;
            this.achievementLevel = achievementLevel;
        }

        public String getDateTimeCreated() {
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

        public int getPlayerScore(int playerNum) { return scoreList.get(playerNum); }

        public void setPlayerScore(int playerScore) { scoreList.add(playerScore); }

        public AchievementLevel getAchievementLevel() {
            return achievementLevel;
        }

        public void setAchievementLevel(AchievementLevel achievementLvl) {
            achievementLevel = achievementLvl;
        }

    }
}
