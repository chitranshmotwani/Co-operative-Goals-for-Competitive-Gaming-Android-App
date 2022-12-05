package ca.cmpt276.Calcium;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView picture;
    private Intent intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_configuration);
        manager = GameConfigManager.getInstance(null);

        setTitle(R.string.new_game_config_title);
        setupGameConfigNameTextWatcher();
        setupGameConfigScoreDescriptionTextWatcher();
        setupGameConfigScoreRangeTextWatchers();
        setupTakePhotoButton();
        setupPhoto();
    }

    private void setupTakePhotoButton(){
        Button takePhoto = findViewById(R.id.take_photo);
        picture = findViewById(R.id.picture);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dynamically apply for permission
                if (ContextCompat.checkSelfPermission(NewGameConfigurationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewGameConfigurationActivity.this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                } else {
                    startActivity(intent2);
                }
            }
        });
        intent2=new Intent(this,Camera.class);
    }

    private void setupPhoto(){
        ImageView photo = findViewById(R.id.picture);
        photo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_default_box));


    }


    private void setupGameConfigScoreRangeTextWatchers() {
        EditText greatScore = findViewById(R.id.new_great_score);
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

        EditText poorScore = findViewById(R.id.new_poor_score);
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
        EditText description = findViewById(R.id.new_score_description);
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
        EditText gameConfigName = findViewById(R.id.new_game_config_name);
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
        optionsMenu.findItem(R.id.delete_button).setVisible(false);
        optionsMenu.findItem(R.id.about_button).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_button:
                GameConfiguration newGameConfig = new GameConfiguration(name, scoreDescription, highScore, lowerScore);
                manager.addConfig(newGameConfig);
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