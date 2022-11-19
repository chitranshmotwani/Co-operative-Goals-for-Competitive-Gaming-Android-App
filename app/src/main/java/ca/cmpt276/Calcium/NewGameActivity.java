package ca.cmpt276.Calcium;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class NewGameActivity extends AppCompatActivity {

    private int numOfPlayers;
    private int currNumOfPlayers = 0;
    private int currSumScore = 0;
    private GameConfigManager manager;
    private GameConfiguration gameConfig;
    private Menu optionsMenu;
    ArrayList<Integer> scoreList = new ArrayList<>();
    private int index = 0;
    private boolean playersChanged = false;
    private boolean playerIndScoreChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        Intent in = getIntent();
        index = in.getIntExtra("passing selected gameConfig", 0);

        manager = GameConfigManager.getInstance(null);
        gameConfig = manager.getConfig(index);
        setTitle(gameConfig.getName());

        setupGameScoreDescription();
        setupGameNumPlayersTextWatcher();
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
                if (playersChanged && playerIndScoreChanged && currNumOfPlayers == numOfPlayers) {
                    gameConfig.addGame(numOfPlayers, currSumScore);
                    GameConfiguration.Game g = gameConfig.getGame(gameConfig.getNumOfGames() - 1);
                    for (int i = 0; i < scoreList.size(); i++){
                        g.addPlayerScore(scoreList.get(i));
                    }
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
                if (!String.valueOf(numPlayers.getText()).equals("")){
                    numOfPlayers = Integer.parseInt(String.valueOf(numPlayers.getText()));
                    LinearLayout scoreListView = findViewById(R.id.individual_score_list);
                    TextView combScore = findViewById(R.id.combined_score);

                    combScore.setText("");
                    scoreListView.removeAllViews();
                    currNumOfPlayers = 0;
                    scoreList = new ArrayList<>();
                    playersChanged = true;

                    for (int i = 0; i < numOfPlayers; i++){
                        currNumOfPlayers++;
                        addOnePlayer(scoreListView);
                    }
                }
            }
        });
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
                TextView combinedScore = findViewById(R.id.combined_score);
                if (!String.valueOf(score.getText()).equals("")){
                    scoreList.set(indexPlayerScore, Integer.parseInt(String.valueOf(score.getText())));
                    currSumScore = 0;
                    playerIndScoreChanged = true;

                    for (int i = 0; i < currNumOfPlayers; i++){
                        currSumScore += scoreList.get(i);
                    }
                    combinedScore.setText(String.valueOf(currSumScore));
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
        scoreList.add(0);
        setupPlayerScoreListTextWatcher(addPlayerScore, currNumOfPlayers-1);
    }

    private void showAchievementLevelEarned() {
        GameConfiguration.AchievementLevel lvl = gameConfig.getGame(gameConfig.getNumOfGames() - 1).getAchievementLevel();
        int index = lvl.ordinal();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.congratulations));
        builder.setMessage(getString(R.string.achievement) + getString(manager.getLevelID(index)));
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