package ca.cmpt276.Calcium;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameConfigurationActivity extends AppCompatActivity {

    private Menu optionsMenu;
    private GameConfigManager manager;
    private String newNameString;
    private String newDescriptionString;
    private int newPoorScore;
    private int newGreatScore;
    private int index;

    private String hiScore = "0";
    private String lowScore = "0";
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
        index = getIntent().getIntExtra("passing selected gameConfig",0);

        findGameConfigViews();
        setupSelectedGameConfigProperties(index);
        setupGameConfigPropertyTextWatchers();
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
                    newNameString = inputName;
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
                    newDescriptionString = inputDescription;
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
                    newPoorScore = Integer.parseInt(inputPoorScore);
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
                    newGreatScore = Integer.parseInt(inputGreatScore);
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

    private void setupSelectedGameConfigProperties(int index) {
        GameConfiguration selected = manager.getConfig(index);

        name.setText(selected.getName());
        description.setText(selected.getScoreSystemDescription());
        poorScore.setText(selected.getLowPerPlayerScore());
        greatScore.setText(selected.getHighPerPlayerScore());

    }

    public void goToGamesList (View view){
        Intent intent = new Intent (this, GameListActivity.class);
        startActivity(intent);
    }
    public void goToNewGame (View view){
        Intent intent = new Intent (this, NewGameActivity.class);
        startActivity(intent);
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
            case R.id.saveButton:
                GameConfiguration newGameConfig = manager.getConfig(index);
                newGameConfig.setHighPerPlayerScore(newGreatScore);
                newGameConfig.setLowPerPlayerScore(newPoorScore);
                newGameConfig.setScoreSystemDescription(newDescriptionString);
                finish();
                break;

            case R.id.backButton:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}