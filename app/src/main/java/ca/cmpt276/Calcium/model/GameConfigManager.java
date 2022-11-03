package ca.cmpt276.Calcium.model;

import java.util.ArrayList;

public class GameConfigManager {
    private static GameConfigManager manager;
    private GameConfigManager(){}

    public static synchronized GameConfigManager getInstance(){
        if (manager == null) {
            manager = new GameConfigManager();
        }
        return manager;
    }


    private ArrayList<GameConfiguration> configs = new ArrayList<>();

    public GameConfiguration getConfig(int index)   { return configs.get(index); }

    public void setConfig(int index, GameConfiguration newConfig)  { configs.set(index,newConfig); }

    public void addConfig(GameConfiguration newConfig)  { configs.add(newConfig); }

    public void deleteConfig(int index)  { configs.remove(index); }

    public int getNumOfConfigs()    { return configs.size(); }
}
