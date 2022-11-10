package ca.cmpt276.Calcium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameListActivity extends AppCompatActivity {

    public static final String SHAREDPREF = "Shared Preferences";
    public static final String SHAREDPREF_MANAGER = "Manager";
    private GameConfigManager manager;
    private GameConfiguration gameConfig;

    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        manager = GameConfigManager.getInstance(null);
        gameConfig = manager.getConfig(getIntent().getIntExtra("passing selected gameConfig", 0));

        populateListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        storeGameConfigManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        optionsMenu.findItem(R.id.save_button).setVisible(false);
        return true;
    }

    public void goToNewGame (View view){
        Intent intent = new Intent (this, NewGameActivity.class);
        startActivity(intent);
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
        ListView list = (ListView) findViewById(R.id.game_list);
        list.setAdapter(adapter);
        list.setEmptyView(findViewById(R.id.empty_game_list));

    }

    private class GameListAdapter extends ArrayAdapter<GameConfiguration.Game>{

        public GameListAdapter() {
            super(GameListActivity.this, R.layout.game_layout, gameConfig.getNumOfGames());
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
            gamePlayers.setText(currentGame.getNumPlayers());

            TextView gameScores = gameView.findViewById(R.id.game_score);
            gameScores.setText(currentGame.getScore());

            TextView gameAchievement = gameView.findViewById(R.id.game_achievement);
            int levelID = manager.getLevelID(currentGame.getAchievementLevel().ordinal());
            gameScores.setText(getString(levelID));

            TextView gameDateTime = gameView.findViewById(R.id.game_date);
            gameScores.setText(currentGame.getDateTimeCreated());

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