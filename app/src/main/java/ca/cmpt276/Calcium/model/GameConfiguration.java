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
    private static final double EASY_MULTIPLIER = 0.75;
    private static final double NORMAL_MULTIPLIER = 1;
    private static final double HARD_MULTIPLIER = 1.25;

    private ArrayList<Game> gameList = new ArrayList<>();

    private ArrayList<Integer> achievementObtainedList = new ArrayList<Integer>(Collections.nCopies(AchievementLevel.values().length, 0));
    private String name;
    private String scoreSystemDescription;
    private int greatPerPlayerScore;
    private int poorPerPlayerScore;
    private final int defaultNumPlayers = 2;

    public ArrayList<Game> getGames() {
        return gameList;
    }

    public int getDefaultNumPlayers() {
        return defaultNumPlayers;
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

    public enum DifficultyLevel {
        EASY,
        NORMAL,
        HARD
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

    public void addGame(int numPlayers, int score, DifficultyLevel difficultyLevel) {
        AchievementLevel levelObtained = calculateAchievementLevel(numPlayers, score, difficultyLevel);
        Integer numTimesAchieved = achievementObtainedList.get(levelObtained.ordinal());
        achievementObtainedList.set(levelObtained.ordinal(), ++numTimesAchieved);
        gameList.add(new Game(LocalDateTime.now(), numPlayers, score, levelObtained, difficultyLevel));
    }

    public void deleteGame(int index) {
        gameList.remove(index);
    }

    public int getNumOfGames() { return gameList.size(); }

    public ArrayList<Integer> getAchievementObtainedList() {
        return achievementObtainedList;
    }

    public void changeGameNumberOfPlayers(int index, int newNumberOfPlayers) {
        Game gameToBeChanged = gameList.get(index);
        gameToBeChanged.setNumPlayers(newNumberOfPlayers);
        gameToBeChanged.setAchievementLevel(
                calculateAchievementLevel(gameToBeChanged.getNumPlayers(), gameToBeChanged.getScore(), gameToBeChanged.getDifficultyLevel())
        );
    }

    public void changeGameScore(int index, int newScore) {
        Game gameToBeChanged = gameList.get(index);
        AchievementLevel prevLevel = gameToBeChanged.getAchievementLevel();
        gameToBeChanged.setScore(newScore);
        gameToBeChanged.setAchievementLevel(
                calculateAchievementLevel(gameToBeChanged.getNumPlayers(), gameToBeChanged.getScore(), gameToBeChanged.getDifficultyLevel())
        );

        AchievementLevel currLevel = gameToBeChanged.getAchievementLevel();
        if(prevLevel != currLevel) {
            Integer prev = achievementObtainedList.get(prevLevel.ordinal());
            achievementObtainedList.set(prevLevel.ordinal(), prev - 1);

            Integer curr = achievementObtainedList.get(currLevel.ordinal());
            achievementObtainedList.set(currLevel.ordinal(), curr + 1);
        }
    }

    public ArrayList<Integer> getMinimumScoresForAchievementLevels(int numPlayers, DifficultyLevel difficultyLevel) {
        double scoreMultiplier = getDifficultyLvlMultiplier(difficultyLevel);

        double scaledGreatScore = greatPerPlayerScore * numPlayers * scoreMultiplier;
        double scaledPoorScore = poorPerPlayerScore * numPlayers * scoreMultiplier;
        double interval = (scaledGreatScore - scaledPoorScore) / ((AchievementLevel.values().length) - 2);

        ArrayList<Integer> minScores = new ArrayList<>();
        minScores.add(0);
        for (int i = 0; i < AchievementLevel.values().length - 1; i++) {
            minScores.add((int) (scaledPoorScore + (interval * i))); //type casting into an integer floors the value
        }

        Collections.reverse(minScores);
        return minScores;
    }

    private AchievementLevel calculateAchievementLevel(int numPlayers, int score, DifficultyLevel difficultyLevel) {
        double scoreMultiplier = getDifficultyLvlMultiplier(difficultyLevel);

        double scaledGreatScore = greatPerPlayerScore * numPlayers * scoreMultiplier;
        double scaledPoorScore = poorPerPlayerScore * numPlayers * scoreMultiplier;

        if(score < scaledPoorScore) {
            //return the ranking that is below the expected poor score
            return AchievementLevel.values()[AchievementLevel.values().length - 1];
        }else if(score > scaledGreatScore) {
            //return highest rank possible
            return AchievementLevel.values()[0];
        }

        //top and bottom tiers already accounted for
        double interval = (scaledGreatScore - scaledPoorScore) / ((AchievementLevel.values().length) - 2);
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

    private double getDifficultyLvlMultiplier(DifficultyLevel difficultyLevel) {
        switch (difficultyLevel.ordinal()){
            case 0:
                return EASY_MULTIPLIER;
            case 2:
                return HARD_MULTIPLIER;
            default:
                return NORMAL_MULTIPLIER;
        }
    }

    public class Game {
        private final String dateTimeCreated;
        private int numPlayers;
        private int score;
        private AchievementLevel achievementLevel;
        private DifficultyLevel difficultyLevel;
        private ArrayList<Integer> scoreList;

        public Game(LocalDateTime dateTimeCreated, int numPlayers, int score, AchievementLevel achievementLevel, DifficultyLevel difficultyLevel) {
            //no setter for dateTimeCreated as it should only be set on the game creation
            this.dateTimeCreated = dateTimeCreated.format(FORMATTER);
            this.numPlayers = numPlayers;
            this.score = score;
            this.achievementLevel = achievementLevel;
            this.difficultyLevel = difficultyLevel;
            this.scoreList = new ArrayList<>();
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

        public void addPlayerScore(int playerScore) { this.scoreList.add(playerScore); }

        public void setPlayerScore(int index, int score) { this.scoreList.set(index, score); }

        public AchievementLevel getAchievementLevel() {
            return achievementLevel;
        }

        public void setAchievementLevel(AchievementLevel achievementLvl) {
            achievementLevel = achievementLvl;
        }

        public DifficultyLevel getDifficultyLevel() {
            return difficultyLevel;
        }

        public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
            this.difficultyLevel = difficultyLevel;
        }


    }
}
