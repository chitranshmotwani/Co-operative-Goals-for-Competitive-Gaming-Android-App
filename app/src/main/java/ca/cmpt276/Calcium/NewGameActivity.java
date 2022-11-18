package ca.cmpt276.Calcium;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private Spinner spThemes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_new_game);
        Intent in = getIntent();
        int index = in.getIntExtra("passing selected gameConfig", 0);

        manager = GameConfigManager.getInstance(null);
        gameConfig = manager.getConfig(index);
        setTitle(gameConfig.getName());

        setupSpinnerItemSelection();
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

        if(btn != null) {
            String difficulty = btn.getText().toString().toUpperCase(Locale.ROOT);

            for (GameConfiguration.DifficultyLevel level :
                    GameConfiguration.DifficultyLevel.values()) {
                String levelStr = level.toString();
                if (difficulty.equals(levelStr)) {
                    return level;
                }
            }
        }

        //default is normal difficulty
        return GameConfiguration.DifficultyLevel.NORMAL;
    }

    private void setupSpinnerItemSelection() {
        spThemes = (Spinner) findViewById(R.id.spThemes);
        spThemes.setSelection(ThemeApplication.currentPosition);
        ThemeApplication.currentPosition = spThemes.getSelectedItemPosition();

        spThemes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (ThemeApplication.currentPosition != position) {
                    Utils.changeToTheme(NewGameActivity.this, position);
                }
                ThemeApplication.currentPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void showAchievementLevelEarned() {
        GameConfiguration.Game newestGame = gameConfig.getGame(gameConfig.getNumOfGames() - 1);
        GameConfiguration.AchievementLevel lvl = newestGame.getAchievementLevel();
        int index = lvl.ordinal();

        String difficulty = newestGame.getDifficultyLevel().toString().toLowerCase(Locale.ROOT);
        difficulty = difficulty.substring(0, 1).toUpperCase(Locale.ROOT) + difficulty.substring(1);
        String level = getString(manager.getLevelID(index));
        if (ThemeApplication.currentPosition==0) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.achievement_sound1);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
        else if (ThemeApplication.currentPosition==1) {
            MediaPlayer mp = MediaPlayer.create(this, R.raw.achievement_sound2);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
        else if (ThemeApplication.currentPosition==2){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.achievement_sound3);
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
            String s = getString(R.string.achievement_name) + "\n" + level + getString(R.string.on_difficulty_lvl) + difficulty;
            Dialog dialog = new Dialog(this);
            if (ThemeApplication.currentPosition==0) {
                dialog.setContentView(R.layout.popup_achievement1);
            }
            else if (ThemeApplication.currentPosition==1) {
                dialog.setContentView(R.layout.popup_achievement2);
            }
            else if (ThemeApplication.currentPosition==2) {
                dialog.setContentView(R.layout.popup_achievement3);
            }
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView tv = dialog.findViewById(R.id.achievement_name);
            ImageView iv = dialog.findViewById(R.id.achievement_star);
            iv.setAnimation(animation);
            tv.setText(s);
            tv.setAnimation(animation);
            dialog.setCancelable(false);
            Button popupBtn = dialog.findViewById(R.id.popup_ok_button);
            popupBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            dialog.show();
        }
}