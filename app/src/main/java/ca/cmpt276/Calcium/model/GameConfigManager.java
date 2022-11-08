package ca.cmpt276.Calcium.model;

import java.util.ArrayList;

/* GameConfigManager handles the adding, deleting and modifying of GameConfigurations. It also
*  stores a list of the current GameConfigurations that have already been created. It is a singleton
*  and supports returning a GameConfiguration object.
*
 */
public class GameConfigManager{
    private static GameConfigManager manager;
    private GameConfigManager(){}
    private ArrayList<GameConfiguration> configs = new ArrayList<>();


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
}
