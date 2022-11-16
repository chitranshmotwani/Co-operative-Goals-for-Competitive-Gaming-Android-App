package ca.cmpt276.Calcium;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameConfigurationActivity extends AppCompatActivity {

    private Menu optionsMenu;
    private GameConfigManager manager;
    private String displayedNameString;
    private String displayedDescriptionString;
    private int displayedPoorScore;
    private int displayedGreatScore;
    private ArrayList<Integer> displayedMinScores;

    private int index;

    private boolean valuesChanged = false;

    private EditText name;
    private EditText description;
    private EditText poorScore;
    private EditText greatScore;
    private EditText numPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
        manager = GameConfigManager.getInstance(null);

        Intent in = getIntent();
        index = getIntent().getIntExtra("passing selected gameConfig", 0);
        setTitle(manager.getConfig(index).getName());

        findGameConfigViews();
        setupSelectedGameConfigProperties();
        setupGameConfigPropertyTextWatchers();
        setupAchievementLevelsTextWatcher();
    }

    private void setupAchievementLevelsTextWatcher() {
        numPlayers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateAchievementList(Integer.parseInt(editable.toString()));
            }
        });
    }

    private void updateAchievementList(int numPlayers) {
        displayedMinScores = manager.getConfig(index).getMinimumScoresForAchievementLevels(numPlayers, GameConfiguration.DifficultyLevel.NORMAL);

        ArrayAdapter<Integer> adapter = new AchievementListAdapter(numPlayers);
        ListView list = findViewById(R.id.achievement_list);
        list.setAdapter(adapter);
    }

    private void setupGameConfigPropertyTextWatchers() {
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputName = name.getText().toString();
                if (!inputName.equals("")) {
                    valuesChanged = true;
                    displayedNameString = inputName;
                }
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputDescription = description.getText().toString();
                if (!inputDescription.equals("")) {
                    valuesChanged = true;
                    displayedDescriptionString = inputDescription;
                }
            }
        });

        poorScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputPoorScore = poorScore.getText().toString();
                if (!inputPoorScore.equals("")) {
                    valuesChanged = true;
                    displayedPoorScore = Integer.parseInt(inputPoorScore);
                }
            }
        });

        greatScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String inputGreatScore = greatScore.getText().toString();
                if (!inputGreatScore.equals("")) {
                    valuesChanged = true;
                    displayedGreatScore = Integer.parseInt(inputGreatScore);
                }
            }
        });
    }

    private void findGameConfigViews() {
        name = findViewById(R.id.game_config_name);
        description = findViewById(R.id.score_description);
        poorScore = findViewById(R.id.poor_score);
        greatScore = findViewById(R.id.great_score);
        numPlayers = findViewById(R.id.num_players);
    }

    private void setupSelectedGameConfigProperties() {
        GameConfiguration selected = manager.getConfig(index);

        name.setText(selected.getName());
        description.setText(selected.getScoreSystemDescription());
        poorScore.setText(String.format("%d", selected.getPoorPerPlayerScore()));
        greatScore.setText(String.format("%d", selected.getGreatPerPlayerScore()));

        displayedNameString = name.getText().toString();
        displayedDescriptionString = description.getText().toString();
        displayedPoorScore = Integer.parseInt(poorScore.getText().toString());
        displayedGreatScore = Integer.parseInt(greatScore.getText().toString());

    }

    public void goToGamesList(View view) {
        Intent intent = new Intent(this, GameListActivity.class);
        intent.putExtra("passing selected gameConfig", index);
        startActivity(intent);
        finish();
    }

    public void goToNewGame(View view) {
        Intent intent = new Intent(this, NewGameActivity.class);
        intent.putExtra("passing selected gameConfig", index);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_button:
                if (valuesChanged) {
                    confirmGameConfigurationSave();
                } else {
                    Toast.makeText(this, getString(R.string.no_changes), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_button:
                confirmGameConfigurationDelete();
                break;
            case R.id.back_button:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmGameConfigurationDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_game_config));

        builder.setPositiveButton(R.string.yes, (dialog, id) -> {
            manager.deleteConfig(index);
            finish();
        });

        builder.setNegativeButton(R.string.no, (dialog, id) -> {
            //Do nothing
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void confirmGameConfigurationSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.save_game_config));

        builder.setPositiveButton(R.string.yes, (dialog, id) -> {
            GameConfiguration modifiedGameConfig = manager.getConfig(index);

            modifiedGameConfig.setName(displayedNameString);
            modifiedGameConfig.setGreatPerPlayerScore(displayedGreatScore);
            modifiedGameConfig.setPoorPerPlayerScore(displayedPoorScore);
            modifiedGameConfig.setScoreSystemDescription(displayedDescriptionString);
            finish();
        });

        builder.setNegativeButton(R.string.no, (dialog, id) -> {
            finish();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private class AchievementListAdapter extends ArrayAdapter<Integer> {

        public AchievementListAdapter(int numPlayers) {
            super(GameConfigurationActivity.this,
                    R.layout.achievement_layout,
                    manager.getConfig(index).
                            getMinimumScoresForAchievementLevels(numPlayers, GameConfiguration.DifficultyLevel.NORMAL));
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View gameView = convertView;
            if (gameView == null) {
                gameView = getLayoutInflater().inflate(R.layout.achievement_layout, parent, false);
            }

            Integer minScore = displayedMinScores.get(position);
            String achievementLevel = getString(manager.getLevelID(position));

            TextView score = gameView.findViewById(R.id.min_score);
            score.setText(String.valueOf(minScore));
            TextView name = gameView.findViewById(R.id.achievement_level);
            name.setText(achievementLevel);
            return gameView;
        }
    }

}