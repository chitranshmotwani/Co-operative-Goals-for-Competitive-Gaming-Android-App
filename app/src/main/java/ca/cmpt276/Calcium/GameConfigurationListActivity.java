package ca.cmpt276.Calcium;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameConfigurationListActivity extends AppCompatActivity {

    public static final String SHAREDPREF = "Shared Preferences";
    public static final String SHAREDPREF_MANAGER = "Manager";
    private GameConfigManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_configuration_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameConfigurationListActivity.this, NewGameConfigurationActivity.class);
                startActivity(intent);
            }
        });

        getGameConfigManager();
        populateList();
        registerClick();
        updateListView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        storeGameConfigManager();
        populateList();
    }

    private void storeGameConfigManager() {
        Gson gson = new Gson();
        String currentManager = gson.toJson(manager);

        SharedPreferences preferences = getSharedPreferences(SHAREDPREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SHAREDPREF_MANAGER, currentManager);
        editor.commit();
    }

    private void getGameConfigManager() {
        SharedPreferences preferences = getSharedPreferences(SHAREDPREF, MODE_PRIVATE);
        String managerString = preferences.getString(SHAREDPREF_MANAGER, null);
        Type type = new TypeToken<GameConfigManager>() {}.getType();
        Gson gson = new Gson();

        GameConfigManager stored = gson.fromJson(managerString, type);
        manager = GameConfigManager.getInstance(stored);
    }

    private void populateList() {
        ArrayList<String> gameConfigs = new ArrayList<String>();

        for (int i = 0; i < manager.getNumOfConfigs(); i++) {
            gameConfigs.add(manager.getConfig(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.game_configs_layout, gameConfigs);

        ListView list = findViewById(R.id.listViewGameConfiguration);
        list.setAdapter(adapter);
    }

    private void registerClick() {
        ListView list = findViewById(R.id.listViewGameConfiguration);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(GameConfigurationListActivity.this,GameConfigurationActivity.class);
                in.putExtra("passing gameConfigs", position);
                startActivity(in);

            }
        });
    }
    private void updateListView() {
        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.listViewGameConfiguration);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(GameConfigurationListActivity.this, R.layout.game_configs_layout, manager.getNumOfConfigs());
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View GameConfigView = convertView;
            if (GameConfigView == null) {
                GameConfigView = getLayoutInflater().inflate(R.layout.game_configs_layout, parent, false);
            }

            GameConfiguration currentConfig = GameConfigManager.getInstance(position);

            TextView textName = GameConfigView.findViewById(R.id.nameGameConfig);
            textName.setText(currentConfig.getName());

            TextView textPoorScore = GameConfigView.findViewById(R.id.poorScoreField);
            textPoorScore.setText(currentConfig.getLowPerPlayerScore());

            TextView textGreatScore = GameConfigView.findViewById(R.id.greatScoreField);
            textGreatScore.setText(currentConfig.getHighPerPlayerScore());

            return GameConfigView;
        }

    }
}
