package fr.android.project_vyas_manaranche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Gère l'ouverture de l'activité MatchSettings
     * @param view
     */
    public void launchMatch(View view) {

        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    /**
     * Gère l'ouverture de l'activité History
     * @param view
     */
    public void launchHistory(View view) {

        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
}