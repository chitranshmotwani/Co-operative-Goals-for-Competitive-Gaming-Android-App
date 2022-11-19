package ca.cmpt276.Calcium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.Locale;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameListActivity extends AppCompatActivity {

    public static final String SHAREDPREF = "Shared Preferences";
    public static final String SHAREDPREF_MANAGER = "Manager";
    private GameConfigManager manager;
    private GameConfiguration gameConfig;
    private int index;

    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        manager = GameConfigManager.getInstance(null);
        index = getIntent().getIntExtra("passing selected gameConfig", 0);
        gameConfig = manager.getConfig(index);
        setTitle(gameConfig.getName());

        populateListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        storeGameConfigManager();
        populateListView();
        registerListItemClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        optionsMenu.findItem(R.id.save_button).setVisible(false);
        optionsMenu.findItem(R.id.delete_button).setVisible(false);

        return true;
    }

    public void goToNewGame (View view){
        Intent intent = new Intent (this, NewGameActivity.class);
        intent.putExtra("passing selected gameConfig", index);
        startActivity(intent);
        finish();
    }

    private void storeGameConfigManager() {
        Gson gson = new Gson();
        String currentManager = gson.toJson(manager);

        SharedPreferences preferences = getSharedPreferences(SHAREDPREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SHAREDPREF_MANAGER, currentManager);
        editor.commit();
    }
    private void populateListView() {
        ArrayAdapter<GameConfiguration.Game> adapter = new GameListAdapter();
        ListView list = findViewById(R.id.game_list);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.empty_game_list));

    }
    private void registerListItemClick() {
        ListView list = findViewById(R.id.game_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(GameListActivity.this, GameActivity.class);
                in.putExtra("passing selected game", position);
                in.putExtra("passing selected gameConfig", index);
                startActivity(in);
            }
        });
    }

    private class GameListAdapter extends ArrayAdapter<GameConfiguration.Game>{

        public GameListAdapter() {
            super(GameListActivity.this, R.layout.game_layout, gameConfig.getGames());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View gameView = convertView;
            if(gameView == null) {
                gameView = getLayoutInflater().inflate(R.layout.game_layout, parent, false);
            }

            GameConfiguration.Game currentGame = gameConfig.getGame(position);

            TextView gamePlayers = gameView.findViewById(R.id.game_players);
            gamePlayers.setText(getString(R.string.players) + currentGame.getNumPlayers());

            TextView gameScores = gameView.findViewById(R.id.game_score);
            gameScores.setText(getString(R.string.score) + String.valueOf(currentGame.getScore()));

            TextView gameAchievement = gameView.findViewById(R.id.game_achievement);
            int levelID = manager.getLevelID(currentGame.getAchievementLevel().ordinal());
            gameAchievement.setText(getString(levelID));

            TextView gameDateTime = gameView.findViewById(R.id.game_date);
            gameDateTime.setText(currentGame.getDateTimeCreated());

            TextView gameDifficulty = gameView.findViewById(R.id.game_difficulty);
            String difficulty = currentGame.getDifficultyLevel().toString().toLowerCase(Locale.ROOT);
            difficulty = difficulty.substring(0,1).toUpperCase(Locale.ROOT) + difficulty.substring(1);
            gameDifficulty.setText(getString(R.string.difficulty_prefix) + difficulty);

            return gameView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back_button:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}