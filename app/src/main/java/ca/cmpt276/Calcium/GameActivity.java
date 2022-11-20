package ca.cmpt276.Calcium;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import java.util.Locale;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameActivity extends AppCompatActivity {

    private int numOfPlayers;
    private int currNumOfPlayers = 0;
    private int currSumScore = 0;
    private GameConfigManager manager;
    private GameConfiguration gameConfig;
    private GameConfiguration.Game game;
    private Menu optionsMenu;
    ArrayList<Integer> scoreList = new ArrayList<>();
    private int index = 0;
    private boolean ini = true;
    private boolean playersChanged = true;
    private boolean playerIndScoreChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent in = getIntent();
        index = in.getIntExtra("passing selected gameConfig", 0);
        manager = GameConfigManager.getInstance(null);
        gameConfig = manager.getConfig(index);
        setTitle(gameConfig.getName());
        index = in.getIntExtra("passing selected game", 0);
        game = gameConfig.getGame(index);
        currNumOfPlayers = numOfPlayers = game.getNumPlayers();

        setupGameScoreDescription();
        setupNumOfPlayers();
        setupPlayerScores();
        setupCombinedScore();
        setupGameNumPlayersTextWatcher();
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
                if (playersChanged && playerIndScoreChanged) {
                    GameConfiguration.DifficultyLevel lvl = getDifficultyLevelSelected();
                    for (int i = 0; i < game.getNumPlayers(); i++){
                        game.setPlayerScore(i, scoreList.get(i));
                    }
                    for (int i = game.getNumPlayers(); i < scoreList.size(); i++){
                        game.addPlayerScore(scoreList.get(i));
                        Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
                    }
                    game.setNumPlayers(numOfPlayers);
                    game.setScore(currSumScore);
                    game.setDifficultyLevel(lvl);
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

            if (game.getDifficultyLevel().toString().charAt(0) == difficulty.charAt(0)){
                group.check(btn.getId());
            }
        }

    }

    private void setupGameScoreDescription() {
        TextView description = findViewById(R.id.score_system_description);
        description.setText(gameConfig.getScoreSystemDescription());
    }

    private void setupNumOfPlayers(){
        TextView combScore = findViewById(R.id.num_players);
        combScore.setText(String.valueOf(numOfPlayers));
    }

    private void setupPlayerScores(){
        LinearLayout scoreListView = findViewById(R.id.individual_score_list);
        TextView combScore = findViewById(R.id.combined_score);

        combScore.setText("");
        scoreListView.removeAllViews();
        currNumOfPlayers = 0;
        scoreList = new ArrayList<>();

        for (int i = 0; i < numOfPlayers; i++){
            currNumOfPlayers++;
            addOnePlayer(scoreListView);
        }
    }

    private void setupCombinedScore(){
        TextView combinedScore = findViewById(R.id.combined_score);
        currSumScore = 0;

        for (int i = 0; i < currNumOfPlayers; i++){
            currSumScore += scoreList.get(i);
        }
        combinedScore.setText(String.valueOf(currSumScore));
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
                if (!String.valueOf(numPlayers.getText()).equals("")){
                    playersChanged = true;
                    numOfPlayers = Integer.parseInt(String.valueOf(numPlayers.getText()));
                    setupPlayerScores();
                }
                else {
                    playersChanged = false;
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

    private void setupPlayerScoreListTextWatcher(EditText score, int indexPlayerScore) {
        score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!String.valueOf(score.getText()).equals("")){
                    scoreList.set(indexPlayerScore, Integer.parseInt(String.valueOf(score.getText())));
                    playerIndScoreChanged = true;
                    setupCombinedScore();
                }
                else {
                    playerIndScoreChanged = false;
                }
            }
        });
    }

    private void addOnePlayer(LinearLayout scoreListView){
        TextView addPlayerTitle = new TextView(this);
        EditText addPlayerScore = new EditText(this);
        String txt = getResources().getString(R.string.player_and_space) + currNumOfPlayers;

        addPlayerTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        addPlayerTitle.setText(txt);
        addPlayerTitle.setTextColor(Color.WHITE);
        addPlayerTitle.setTextSize(20);
        addPlayerTitle.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        addPlayerScore.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
        addPlayerScore.setTextColor(Color.WHITE);
        addPlayerScore.setInputType(InputType.TYPE_CLASS_NUMBER);
        addPlayerScore.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        addPlayerScore.setHintTextColor(getColor(R.color.hint_color));
        addPlayerScore.setHint(getResources().getString(R.string.enter_player_score) + currNumOfPlayers);
        scoreListView.addView(addPlayerTitle);
        scoreListView.addView(addPlayerScore);

        if (ini){
            if (currNumOfPlayers == numOfPlayers){
                ini = false;
            }
            addPlayerScore.setText(String.valueOf(game.getPlayerScore(currNumOfPlayers - 1)));
            scoreList.add(game.getPlayerScore(currNumOfPlayers-1));
            playerIndScoreChanged = true;
        }
        else {
            scoreList.add(0);
            playerIndScoreChanged = false;
        }
        setupPlayerScoreListTextWatcher(addPlayerScore, currNumOfPlayers-1);
    }


    private void showAchievementLevelEarned() {
        GameConfiguration.Game newestGame = gameConfig.getGame(gameConfig.getNumOfGames() - 1);
        GameConfiguration.AchievementLevel lvl = newestGame.getAchievementLevel();
        int index = lvl.ordinal();

        String difficulty = newestGame.getDifficultyLevel().toString().toLowerCase(Locale.ROOT);
        difficulty = difficulty.substring(0, 1).toUpperCase(Locale.ROOT) + difficulty.substring(1);
        String level = getString(manager.getLevelID(index));

        MediaPlayer mp = MediaPlayer.create(this, R.raw.achievement_sound);
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

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        String s = getString(R.string.achievement_name) + "\n" + level + getString(R.string.on_difficulty_lvl) + difficulty;
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_achievement);
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