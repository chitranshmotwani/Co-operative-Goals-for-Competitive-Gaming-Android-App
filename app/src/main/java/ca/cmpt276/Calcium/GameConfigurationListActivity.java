package ca.cmpt276.Calcium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.cmpt276.Calcium.model.GameConfigManager;

public class GameConfigurationListActivity extends AppCompatActivity {

    public static final String SHAREDPREF = "Shared Preferences";
    public static final String SHAREDPREF_MANAGER = "Manager";
    private GameConfigManager manager;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_configuration_list);

        setupNewGameConfigFAB();
        getGameConfigManager();
        populateGameConfigList();
        registerListItemClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        optionsMenu.findItem(R.id.delete_button).setVisible(false);
        optionsMenu.findItem(R.id.save_button).setVisible(false);
        optionsMenu.findItem(R.id.back_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_button:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        storeGameConfigManager();
        populateGameConfigList();
    }

    private void setupNewGameConfigFAB() {
        FloatingActionButton fab = findViewById(R.id.add_new_game);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(GameConfigurationListActivity.this, NewGameConfigurationActivity.class);
            startActivity(intent);
        });
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
        Type type = new TypeToken<GameConfigManager>() {
        }.getType();
        Gson gson = new Gson();

        GameConfigManager stored = gson.fromJson(managerString, type);
        manager = GameConfigManager.getInstance(stored);
    }

    private void populateGameConfigList() {
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Bitmap> icon = new ArrayList<>();

        for (int i = 0; i < manager.getNumOfConfigs(); i++) {
            name.add(manager.getConfig(i).getName());
            icon.add(manager.getConfig(i).getIcon());
        }

        ImageList im = new ImageList(this, name);

        ListView list = findViewById(R.id.game_config_list);
        list.setAdapter(im);
        list.setEmptyView(findViewById(R.id.empty_game_configs));
    }

    private void registerListItemClick() {
        ListView list = findViewById(R.id.game_config_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(GameConfigurationListActivity.this, GameConfigurationActivity.class);
                in.putExtra("passing selected gameConfig", position);
                startActivity(in);
            }
        });
    }
}