package ca.cmpt276.Calcium.model;

import java.util.ArrayList;

import ca.cmpt276.Calcium.R;

/* GameConfigManager handles the adding, deleting and modifying of GameConfigurations. It also
*  stores a list of the current GameConfigurations that have already been created. It is a singleton
*  and supports returning a GameConfiguration object.
*
 */
public class GameConfigManager{
    private static GameConfigManager manager;
    private GameConfigManager(){}
    private ArrayList<GameConfiguration> configs = new ArrayList<>();

    private final int[] levelNames = {
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

    public static synchronized GameConfigManager getInstance(GameConfigManager storedManager){
        if (manager == null) {
            if(storedManager == null){
                manager = new GameConfigManager();
            }else {
                manager = storedManager;
            }
        }
        return manager;
    }

    public GameConfiguration getConfig(int index)   { return configs.get(index); }

    public void setConfig(int index, GameConfiguration newConfig)  { configs.set(index,newConfig); }

    public void addConfig(GameConfiguration newConfig)  { configs.add(newConfig); }

    public void deleteConfig(int index)  { configs.remove(index); }

    public int getNumOfConfigs()    { return configs.size(); }

    public int getLevelID(int position) {
        return levelNames[position];
    }
}
