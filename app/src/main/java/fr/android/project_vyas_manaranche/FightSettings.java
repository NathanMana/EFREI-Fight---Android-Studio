package fr.android.project_vyas_manaranche;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.InetAddresses;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.models.PlayerRoundStatistics;
import fr.android.project_vyas_manaranche.services.FightService;
import fr.android.project_vyas_manaranche.services.FighterService;
import fr.android.project_vyas_manaranche.services.PlayerRoundStatisticsService;

public class FightSettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Déclaration variable
    Button fightSettingsActivityBtn;
    Button locationBtn;
    TextView actual_location;
    TextView actual_location_Country;
    EditText player1;
    EditText player2;
    Spinner spinner;
    int fightType;
    String street = "";
    String country = "";
    Handler handler;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_settings_2);

        handler = new Handler();

        // Liaison des var du layout au Java
        initializeViewElement();

        // La liste déroulante, mise en place de la liste dans le layout
        initializeSpinner();

        initializeListeners();

        initializeProviders();
    }

    /**
     * Permet d'initialiser nos variables après l'instanciation de l'activité
     */
    private void initializeViewElement() {

        actual_location = findViewById(R.id.location);
        actual_location_Country = findViewById(R.id.locationCountry);
        locationBtn = findViewById(R.id.locationBtn);
        fightSettingsActivityBtn = findViewById(R.id.fightSettingsActivityBtn);
        player1 = findViewById(R.id.textPlayer1);
        player2 = findViewById(R.id.textPlayer2);
        spinner = findViewById(R.id.spinnerTypeFight);
    }

    /**
     * Regroupe les initialisations aux "providers", c-à-d aux services qui vont nous aider
     */
    private void initializeProviders() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Permet d'initialiser notre liste déroulante concernant le mode de combat
     */
    private void initializeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FightType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * Regroupe tous les event listeners
     */
    private void initializeListeners() {

        locationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Vérification des permission
                if (ActivityCompat.checkSelfPermission(FightSettings.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    return;
                }

                ActivityCompat.requestPermissions(FightSettings.this,
                        new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                        }, 44);
            }
        });

        fightSettingsActivityBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Vérification du formulaire rempli
                if (TextUtils.isEmpty(player1.getText().toString())){
                    //Toast pour indiquer il faut remplir quoi
                    Toast.makeText(FightSettings.this, R.string.fill_gap,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(player2.getText().toString())){
                    //Toast pour indiquer il faut remplir quoi
                    Toast.makeText(FightSettings.this, R.string.fill_gap,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        int fightId = addFightInDbb();
                        openActivityFight(fightId);
                    }
                }).start();
            }
        });
    }

    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // si l'utilisateur a refusé, ne rien faire, l'adresse sera vide par défaut
            return;
        }

        getLocation();
    }

    // Méthode pour obtenir la localisation
    private void getLocation() {

        try {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            // Initialisation de la var location
                            Location location = task.getResult();
                            if (location != null) {

                                try {

                                    // Utilisation d'une var Geocoder
                                    Geocoder geocoder = new Geocoder(FightSettings.this, Locale.getDefault());

                                    // L'adresse avec les coordonnées
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                    //Insertion de l'adresse dans la var
                                    street = Html.fromHtml(addresses.get(0).getAddressLine(0)) + "";

                                    //intersrtion de du pays dans la var
                                    country = Html.fromHtml(addresses.get(0).getCountryName()) + "";

                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            actual_location.setText(street);
                                            actual_location_Country.setText(country);
                                        }
                                    });

                                }  catch (IOException e) {
                                    System.out.println(e);
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }).start();
        }
        catch (SecurityException e) {
            System.out.println("Security exception");
        }
    }

    /**
     * Gère la création du match en bdd
     */
    private int addFightInDbb() {

        int fightId = FightService.addFight(
                new Fight(new Timestamp(System.currentTimeMillis()).getTime(), street, country));

        int fighter1Id = FighterService.addFighter(new Fighter(player1.getText().toString(), fightId));
        int fighter2Id = FighterService.addFighter(new Fighter(player2.getText().toString(), fightId));
        int[] arrayFightersId = new int[]{ fighter1Id, fighter2Id };

        // Créer les statistiques de rounds (vide)
        int nbRounds = fightType + 1;
        for (int i = 0; i < nbRounds; i++) {

            // Pour chaque joueur créer les rounds (pour les stats)
            for (int j = 0; j < 2; j++) {

                int fighterId = arrayFightersId[j];
                PlayerRoundStatisticsService.addPlayerRoundStatistics(new PlayerRoundStatistics(i+1, fighterId));
            }
        }

        return fightId;
    }

    // Changement de page avec validation
    public void openActivityFight(int fightId) {

        Intent intent = new Intent(this, FightActivity.class);
        intent.putExtra("player1", player1.getText().toString());
        intent.putExtra("player2", player2.getText().toString());
        intent.putExtra("NbRound", fightType + 1);
        intent.putExtra("FightId", fightId);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        fightType = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void handlerBack(View view) { finish(); }
}
