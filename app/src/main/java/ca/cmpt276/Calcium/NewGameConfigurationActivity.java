package ca.cmpt276.Calcium;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class NewGameConfigurationActivity extends AppCompatActivity {

    private String name = "";
    private String scoreDescription = "";
    private String hiScore = "0";
    private String lowScore = "0";
    private int highScore = 0;
    private int lowerScore = 0;
    private GameConfigManager manager;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_configuration);
        manager = GameConfigManager.getInstance(null);

        setupGameConfigNameTextWatcher();
        setupGameConfigScoreDescriptionTextWatcher();
        setupGameConfigScoreRangeTextWatchers();

    }

    private void setupGameConfigScoreRangeTextWatchers() {
        EditText greatScore = findViewById(R.id.great_score);
        greatScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hiScore = greatScore.getText().toString();
                if (!hiScore.equals("")) {
                    highScore = Integer.parseInt(hiScore);
                }
            }
        });


        EditText poorScore = findViewById(R.id.poor_score);
        poorScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lowScore = poorScore.getText().toString();
                if (!lowScore.equals("")) {
                    lowerScore = Integer.parseInt(NewGameConfigurationActivity.this.lowScore);
                }
            }
        });
    }

    private void setupGameConfigScoreDescriptionTextWatcher() {
        EditText description = findViewById(R.id.score_description);
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                scoreDescription = description.getText().toString();
            }
        });
    }

    private void setupGameConfigNameTextWatcher() {
        EditText gameConfigName = findViewById(R.id.game_config_name);
        gameConfigName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name = gameConfigName.getText().toString();
            }
        });
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
                GameConfiguration newGameconfig = new GameConfiguration(name, scoreDescription, highScore, lowerScore);
                manager.addConfig(newGameconfig);
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