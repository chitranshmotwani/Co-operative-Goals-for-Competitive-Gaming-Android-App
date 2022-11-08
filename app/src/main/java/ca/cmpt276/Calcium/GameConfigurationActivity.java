package ca.cmpt276.Calcium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ca.cmpt276.Calcium.model.GameConfiguration;
import ca.cmpt276.Calcium.model.GameConfigManager;

public class GameConfigurationActivity extends AppCompatActivity {

    private GameConfiguration newGameconfig;
    private GameConfigManager manager;
    private String name = "";
    private String scoreDescription = "";
    private int highScore = 0;
    private int lowerScore = 0;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_configuration);
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
                newGameconfig = new GameConfiguration (name, scoreDescription, highScore, lowerScore);
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