package ca.cmpt276.Calcium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ca.cmpt276.Calcium.model.GameConfigManager;
import ca.cmpt276.Calcium.model.GameConfiguration;

public class GameConfigurationListActivity extends AppCompatActivity {
    GameConfigManager gameconfigmngr= GameConfigManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_configuration_list);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameConfigurationListActivity.this, NewGameConfigurationActivity.class);
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        gameconfigmngr= (GameConfigManager) intent.getSerializableExtra("Passing newGameConfigmngr");

        populateList();
        registerClick();


    }

    private void populateList() {




        ArrayList<String> gameConfigs = new ArrayList<String>();



        for(int i=0;i<1;i++ ){

            gameConfigs.add(gameconfigmngr.getConfig(i).getName());

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.game_configs_layout, gameConfigs);

        ListView list= findViewById(R.id.listViewGameConfiguration);
        list.setAdapter(adapter);
    }
    private void registerClick() {
        ListView list= findViewById(R.id.listViewGameConfiguration);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
