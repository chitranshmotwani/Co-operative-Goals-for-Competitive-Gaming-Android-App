package ca.cmpt276.Calcium.model;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigManagerTest {

    @org.junit.jupiter.api.Test
    public void testGetNewInstance() {
        GameConfigManager manager = null;
        manager = GameConfigManager.getInstance(null);

        assertNotNull(manager);
    }

    @org.junit.jupiter.api.Test
    public void testGetInstance() {
        //create test manager
        GameConfigManager manager = GameConfigManager.getInstance(null);
        manager.addConfig(new GameConfiguration("test", "test description", 1, 10));
        GameConfiguration original = manager.getConfig(0);

        //attempt to get manager from getInstance
        GameConfigManager sameManager = GameConfigManager.getInstance(null);
        GameConfiguration instanceCopy = sameManager.getConfig(0);
        assertAll("Compare GameConfigManager objeects",
                () -> assertEquals(manager.getNumOfConfigs(), sameManager.getNumOfConfigs()),
                () -> assertEquals(original.getName(), instanceCopy.getName()),
                () -> assertEquals(original.getScoreSystemDescription(), instanceCopy.getScoreSystemDescription()),
                () -> assertEquals(original.getPoorPerPlayerScore(), instanceCopy.getPoorPerPlayerScore()),
                () -> assertEquals(original.getGreatPerPlayerScore(), instanceCopy.getGreatPerPlayerScore())
        );

        manager.deleteConfig(0);
    }

    @org.junit.jupiter.api.Test
    void getConfig() {
        //create test manager
        GameConfigManager manager = GameConfigManager.getInstance(null);
        manager.addConfig(new GameConfiguration("test", "test description", 10, 1));
        GameConfiguration gameConfig = manager.getConfig(0);

        assertAll("Test getConfig",
                () -> assertEquals(gameConfig.getName(), "test"),
                () -> assertEquals(gameConfig.getScoreSystemDescription(), "test description"),
                () -> assertEquals(gameConfig.getPoorPerPlayerScore(), 1),
                () -> assertEquals(gameConfig.getGreatPerPlayerScore(), 10)
        );
        manager.deleteConfig(0);
    }

    @org.junit.jupiter.api.Test
    void getNonExistentConfig() {
        //create test manager
        GameConfigManager manager = GameConfigManager.getInstance(null);
        manager.addConfig(new GameConfiguration("test", "test description", 10, 1));

        assertThrows(ArrayIndexOutOfBoundsException.class, ()->manager.getConfig(-1));
        manager.deleteConfig(0);
    }

//    @org.junit.jupiter.api.Test
//    void addConfig() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void deleteConfig() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getNumOfConfigs() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getLevelID() {
//    }
}