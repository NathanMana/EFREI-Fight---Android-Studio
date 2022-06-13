package fr.android.project_vyas_manaranche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.services.FightService;

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

        // Intent intent = new Intent(this, Settings.class);
        // startActivity(intent);
    }

    /**
     * Gère l'ouverture de l'activité History
     * @param view
     */
    public void launchHistory(View view) {

        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}