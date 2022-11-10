package ca.cmpt276.Calcium;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ca.cmpt276.Calcium.model.GameConfigManager;

public class GameListActivity extends AppCompatActivity {

    public static final String SHAREDPREF = "Shared Preferences";
    public static final String SHAREDPREF_MANAGER = "Manager";
    private GameConfigManager manager;
    private Menu optionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        storeGameConfigManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        optionsMenu = menu;
        optionsMenu.findItem(R.id.saveButton).setVisible(false);
        return true;
    }

    public void goToNewGame (View view){
        Intent intent = new Intent (this, NewGameActivity.class);
        startActivity(intent);
    }

    private void storeGameConfigManager() {
        Gson gson = new Gson();
        String currentManager = gson.toJson(manager);

        SharedPreferences preferences = getSharedPreferences(SHAREDPREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SHAREDPREF_MANAGER, currentManager);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.backButton:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}