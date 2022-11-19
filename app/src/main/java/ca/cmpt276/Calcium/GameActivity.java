package ca.cmpt276.Calcium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameActivity extends AppCompatActivity {


    private GameConfigManager manager;
    private GameConfiguration gameConfig;
    private GameConfiguration.Game game;
    private int index = 0;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        manager = GameConfigManager.getInstance(null);
        Intent in = getIntent();
        index = in.getIntExtra("passing selected gameConfig", 0);
        gameConfig = manager.getConfig(index);
        index = in.getIntExtra("passing selected game", 0);
        game = gameConfig.getGame(index);

        setupViews();
    }

    private void setupViews(){
        LinearLayout scoreListView = findViewById(R.id.individual_score_list);
        TextView descriptionView = findViewById(R.id.score_system_description);
        TextView numPlayerView = findViewById(R.id.num_players);
        TextView combScoreView = findViewById(R.id.combined_score);

        setTitle(getResources().getString(R.string.game_title));
        descriptionView.setText(gameConfig.getScoreSystemDescription());
        numPlayerView.setText(String.valueOf(game.getNumPlayers()));
        combScoreView.setText(String.valueOf(game.getScore()));

        for (int i = 0; i < game.getNumPlayers(); i++){
            TextView addPlayerTitle = new TextView(this);
            String txt = getResources().getString(R.string.player_and_space) + (i + 1) + getResources().getString(R.string.colon_and_space) + game.getPlayerScore(i) + getResources().getString(R.string.space_and_points);
            addPlayerTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
            addPlayerTitle.setText(txt);
            addPlayerTitle.setTextColor(Color.WHITE);
            addPlayerTitle.setTextSize(20);
            addPlayerTitle.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            scoreListView.addView(addPlayerTitle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        optionsMenu.findItem(R.id.delete_button).setVisible(false);
        optionsMenu.findItem(R.id.save_button).setVisible(false);
        return true;
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