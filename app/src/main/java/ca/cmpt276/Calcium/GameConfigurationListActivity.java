package ca.cmpt276.Calcium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GameConfigurationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab_1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameConfigurationListActivity.this, GameConfigurationActivity.class);
                startActivity(intent);

            }
        });

        populateList();
        registerClick();


    }

    private void populateList() {

        String[] gameConfigs = {"1","2","3"}; //Here's where the array of game configs go

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.game_configs_layout, gameConfigs);

        ListView list= findViewById(R.id.list_main);
        list.setAdapter(adapter);
    }
    private void registerClick() {
        ListView list= findViewById(R.id.list_main);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}