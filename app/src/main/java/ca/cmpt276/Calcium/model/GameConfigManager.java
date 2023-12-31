package ca.cmpt276.Calcium.model;

import java.util.ArrayList;

import ca.cmpt276.Calcium.R;

/* GameConfigManager handles the adding, deleting and modifying of GameConfigurations. It also
 *  stores a list of the current GameConfigurations that have already been created. It is a singleton
 *  and supports returning a GameConfiguration object.
 *
 */
public class GameConfigManager {
    private static GameConfigManager manager;
    private static final int[] LEVEL_NAMES = {
            R.string.level_1,
            R.string.level_2,
            R.string.level_3,
            R.string.level_4,
            R.string.level_5,
            R.string.level_6,
            R.string.level_7,
            R.string.level_8,
            R.string.level_9,
            R.string.level_10
    };
    private final ArrayList<GameConfiguration> configs = new ArrayList<>();

    private GameConfigManager() {
    }

    public static synchronized GameConfigManager getInstance(GameConfigManager storedManager) {
        if (manager == null) {
            if (storedManager == null) {
                manager = new GameConfigManager();
            } else {
                manager = storedManager;
            }
        }
        return manager;
    }

    public GameConfiguration getConfig(int index) {
        if(index > configs.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return configs.get(index);
    }

    public void addConfig(GameConfiguration newConfig) {
        configs.add(newConfig);
    }

    public void deleteConfig(int index) {
        configs.remove(index);
    }

    public int getNumOfConfigs() {
        return configs.size();
    }

    public static int getLevelID(int position) {
        return LEVEL_NAMES[position];
    }
}
