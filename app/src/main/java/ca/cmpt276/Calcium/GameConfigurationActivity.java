package ca.cmpt276.Calcium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.EventLogTags;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameConfigurationActivity extends AppCompatActivity {

    private Menu optionsMenu;
    GameConfigManager manager = GameConfigManager.getInstance(null);
    private String hiScore = "0";
    private String lowScore = "0";
    private int highScore = 0;
    private int lowerScore = 0;
    private String GameName ="";
    private String GameDescription ="";
    private int index =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
        TextView Name = findViewById(R.id.nameGameConfig);
        EditText Description = findViewById(R.id.dcsrptionScore);
        EditText PoorScore = findViewById(R.id.poorScoreField);
        EditText GreatScore = findViewById(R.id.greatScoreField);
        Intent in = getIntent();
        index = getIntent().getIntExtra("passing selected gameConfig",0);
        highScore= manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getHighPerPlayerScore();
        lowerScore= manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getLowPerPlayerScore();
        GameName = manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getName();
        GameDescription= manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getScoreSystemDescription();
        Description.setText((CharSequence) manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getScoreSystemDescription());
        PoorScore.setText(Integer.toString(manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getLowPerPlayerScore()));
        GreatScore.setText(Integer.toString(manager.getConfig(getIntent().getIntExtra("passing selected gameconfig",0)).getHighPerPlayerScore()));
        Name.setText((CharSequence) manager.getConfig(getIntent().getIntExtra("passing selected gameConfig",0)).getName());

        GreatScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hiScore = GreatScore.getText().toString();
                if (!hiScore.equals("")) {
                    highScore = Integer.parseInt(hiScore);
                }
            }
        });
        PoorScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lowScore = PoorScore.getText().toString();
                if (!lowScore.equals("")) {
                    lowerScore = Integer.parseInt(lowScore);
                }
            }
        });
        Description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!Description.getText().equals("")) {
                    GameDescription= String.valueOf(Description.getText());
                }
            }
        });
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
                newGameConfig.setHighPerPlayerScore(highScore);
                newGameConfig.setLowPerPlayerScore(lowerScore);
                newGameConfig.setScoreSystemDescription(GameDescription);
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