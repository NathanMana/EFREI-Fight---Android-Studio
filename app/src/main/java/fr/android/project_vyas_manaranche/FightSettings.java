package fr.android.project_vyas_manaranche;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.InetAddresses;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FightSettings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Déclaration variable
    Button fightSettingsActivityBtn;
    Button locationBtn;
    TextView actual_location;
    TextView actual_location_Country;
    EditText player1;
    EditText player2;
    Spinner spinner;


    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_settings);
        //Liaison des var du layout au Java
        actual_location = findViewById(R.id.location);
        actual_location_Country = findViewById(R.id.locationCountry);
        locationBtn = findViewById(R.id.locationBtn);
        fightSettingsActivityBtn = findViewById(R.id.fightSettingsActivityBtn);
        player1 = findViewById(R.id.textPlayer1);
        player2 = findViewById(R.id.textPlayer2);
        spinner = findViewById(R.id.spinnerTypeFight);
        // La liste déroulante, mise en place de la liste dans le layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FightType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        //Permet d'obtneir des info pour savoir si c'est remplis
        int selectedItemOfMySpinner = spinner.getSelectedItemPosition();
        String actualPositionOfMySpinner = (String) spinner.getItemAtPosition(selectedItemOfMySpinner);
        //Initialisation de fused
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //On Click du boutton Localisation
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vérification du formulaire rempli
                if (TextUtils.isEmpty(player1.getText().toString())){
                    //Toast pour indiquer il faut remplir quoi
                    Toast.makeText(FightSettings.this, "Fill the gap (Player 1)",
                    Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(player2.getText().toString())){
                    //Toast pour indiquer il faut remplir quoi
                    Toast.makeText(FightSettings.this, "Fill the gap (Player 2)",
                            Toast.LENGTH_SHORT).show();
                }
                if (actualPositionOfMySpinner.isEmpty()) {
                    //Toast pour indiquer il faut remplir quoi
                    Toast.makeText(FightSettings.this, "Fill the gap (Spinner)",
                            Toast.LENGTH_SHORT).show();
                }

                //Vérification des permission
                if (ActivityCompat.checkSelfPermission(FightSettings.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Ici c'est le code lorsque la permission == Ok
                    getLocation();
                } // Lorsque la permission == KO
                else {
                    // Faire une demande d'acces a la localisation
                    ActivityCompat.requestPermissions(FightSettings.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });

        //On click du boutton Validation
        fightSettingsActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityFight();
            }
        });


    }

    // Méthode pour obtenir la localisation
    private void getLocation() {
        // Second check des permission (générer automatiquement)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
                    actual_location.setText(Html.fromHtml(addresses.get(0).getAddressLine(0)
                    ));
                    //intersrtion de du pays dans la var
                    actual_location_Country.setText(Html.fromHtml(
                            addresses.get(0).getCountryName()

                    ));


                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
        });

    }
    //Déclaration du type de combat
    String FightType;
    @Override
    public  void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //récuperation de la selection de l'option
        FightType  = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Changement de page avec validation
    public void openActivityFight() {
    Intent intent = new Intent(this, FightActivity.class);
    String name1 = player1.getText().toString();
    intent.putExtra("Joueur 1", name1);
        String name2 = player2.getText().toString();
        intent.putExtra("Joueur 2", name2);
        intent.putExtra("TypeCombat", FightType);
    startActivity(intent);
    }


}
