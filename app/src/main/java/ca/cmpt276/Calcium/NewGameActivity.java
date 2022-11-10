package ca.cmpt276.Calcium;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class NewGameActivity extends AppCompatActivity {

    private String DescriptionOfGame;
    private int NoofPlayers;
    private int CombinedScores;
    private GameConfigManager manager;
    private GameConfiguration gameconfig;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        manager = GameConfigManager.getInstance(null);

        setupGameScoreDescriptionTextWatcher();
        setupGameNoofPlayersTextWatcher();
        setupGameCombinedScoreTextWatcher();
    }

    private void setupGameScoreDescriptionTextWatcher() {
        EditText description = findViewById(R.id.DescriptionofGame);
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                DescriptionOfGame = description.getText().toString();
            }
        });
    }

    private void setupGameNoofPlayersTextWatcher() {
        EditText NoPlayers = findViewById(R.id.NoofPlayer_Input);
        NoPlayers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                NoofPlayers = Integer.parseInt(String.valueOf(NoPlayers.getText()));
            }
        });
    }

    private void setupGameCombinedScoreTextWatcher() {
        EditText combinedscore = findViewById(R.id.CombinedScore_Input);
        combinedscore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                CombinedScores = Integer.parseInt(String.valueOf(combinedscore.getText()));
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
            case R.id.save_button:
                String rankName = "temp_string";
                String rank = rankName;       //  assign it to the rank name of the game
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Congratulations!");
                builder.setMessage("You reached the rank: " + rank + " in this game!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });

                finish();
                break;

            case R.id.back_button:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);

    }
}