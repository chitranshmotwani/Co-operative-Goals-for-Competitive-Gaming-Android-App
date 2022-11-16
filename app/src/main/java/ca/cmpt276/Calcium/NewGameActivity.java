package ca.cmpt276.Calcium;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class NewGameActivity extends AppCompatActivity {

    private int numOfPlayers;
    private int combinedScores;
    private int difficulty;
    private GameConfigManager manager;
    private GameConfiguration gameConfig;
    private Menu optionsMenu;
    private boolean playersChanged = false;
    private boolean scoreChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent in = getIntent();
        int index = in.getIntExtra("passing selected gameConfig", 0);

        manager = GameConfigManager.getInstance(null);
        gameConfig = manager.getConfig(index);
        setTitle(gameConfig.getName());

        setupGameScoreDescription();
        setupGameNumPlayersTextWatcher();
        setupGameCombinedScoreTextWatcher();
        setupGameDifficultyRadioGroup();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        optionsMenu.findItem(R.id.delete_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_button:
                if (playersChanged && scoreChanged) {
                    GameConfiguration.DifficultyLevel lvl = getDifficultyLevelSelected();
                    gameConfig.addGame(numOfPlayers, combinedScores, lvl);
                    showAchievementLevelEarned();
                } else {
                    Toast.makeText(this, getString(R.string.incomplete_game_prompt), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.back_button:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupGameDifficultyRadioGroup() {
        RadioGroup group = findViewById(R.id.game_difficulty_radio_group);

        String[] gameDifficulties = getResources().getStringArray(R.array.game_difficulty);
        for (int i = 0; i < gameDifficulties.length; i++) {
            String difficulty = gameDifficulties[i];

            RadioButton btn = new RadioButton(this);
            btn.setText(difficulty);
            btn.setTextColor(getColor(R.color.white));
            btn.setTextSize(18);
            group.addView(btn);
        }
    }

    private void setupGameScoreDescription() {
        TextView description = findViewById(R.id.score_system_description);
        description.setText(gameConfig.getScoreSystemDescription());
    }

    private void setupGameNumPlayersTextWatcher() {
        EditText numPlayers = findViewById(R.id.num_players);
        numPlayers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!String.valueOf(numPlayers.getText()).equals("")) {
                    playersChanged = true;
                    numOfPlayers = Integer.parseInt(String.valueOf(numPlayers.getText()));
                }
            }
        });
    }

    private void setupGameCombinedScoreTextWatcher() {
        EditText combinedScore = findViewById(R.id.combined_score);
        combinedScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!String.valueOf(combinedScore.getText()).equals("")) {
                    scoreChanged = true;
                    combinedScores = Integer.parseInt(String.valueOf(combinedScore.getText()));
                }

            }
        });
    }

    private GameConfiguration.DifficultyLevel getDifficultyLevelSelected() {
        RadioGroup group = findViewById(R.id.game_difficulty_radio_group);
        int selected = group.getCheckedRadioButtonId();

        RadioButton btn = findViewById(selected);
        String difficulty = btn.getText().toString().toUpperCase(Locale.ROOT);

        for (GameConfiguration.DifficultyLevel level :
                GameConfiguration.DifficultyLevel.values()) {
            String levelStr = level.toString();
            if (difficulty.equals(levelStr)) {
                return level;
            }
        }

        //default is normal difficulty
        return GameConfiguration.DifficultyLevel.NORMAL;
    }

    private void showAchievementLevelEarned() {
        GameConfiguration.AchievementLevel lvl = gameConfig.getGame(gameConfig.getNumOfGames() - 1).getAchievementLevel();
        int index = lvl.ordinal();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.congratulations));
        String level = getString(manager.getLevelID(index));

        builder.setMessage(getString(R.string.achievement) + " " + level);
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }
}