package ca.cmpt276.Calcium.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        //attempt to get manager from getInstance
        GameConfigManager sameManager = GameConfigManager.getInstance(null);
    }

    @org.junit.jupiter.api.Test
    void getConfig() {
    }

    @org.junit.jupiter.api.Test
    void addConfig() {
    }

    @org.junit.jupiter.api.Test
    void deleteConfig() {
    }

    @org.junit.jupiter.api.Test
    void getNumOfConfigs() {
    }

    @org.junit.jupiter.api.Test
    void getLevelID() {
    }
}