package ca.cmpt276.Calcium;

import android.content.Intent;
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

    String name = "";
    String scoreDescription = "";
    String hiScore = "0";
    String poorScore = "0";
    int highScore = 0;
    int lowerScore = 0;
    GameConfiguration newGameconfig;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_configuration);

        EditText gameConfigName = findViewById(R.id.GameConfigurationName);
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

        EditText description = findViewById(R.id.DescriptionofScore);
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

        EditText hScore = findViewById(R.id.GreatScore);
        hScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hiScore = hScore.getText().toString();
                if (!hiScore.equals("")) {
                    highScore = Integer.parseInt(hiScore);
                }
            }
        });


        EditText pScore = findViewById(R.id.PoorScore);
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                poorScore = String.valueOf(pScore.getText());
                if (!poorScore.equals("")) {
                    lowerScore = Integer.parseInt(poorScore);
                }
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
                newGameconfig = new GameConfiguration(name, scoreDescription, highScore, lowerScore);
                GameConfigManager newGameConfigmngr = GameConfigManager.getInstance();
                newGameConfigmngr.addConfig(newGameconfig);
                Intent bck = new Intent(NewGameConfigurationActivity.this, GameConfigurationListActivity.class);
                startActivity(bck);
                break;

            case R.id.backButton:
                Intent back = new Intent(NewGameConfigurationActivity.this, GameConfigurationListActivity.class);
                startActivity(back);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);

    }

}