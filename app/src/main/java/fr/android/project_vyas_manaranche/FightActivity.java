package fr.android.project_vyas_manaranche;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FightActivity extends AppCompatActivity {
    //déclaration de textview
    TextView textViewPlayer1, textViewPlayer2, textViewFight, HealtBar1, HealtBar2;
    Button KOP1, KOP2, UpperCJ1, UpperCJ2, CPJ2, CPJ1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        // Récupéreation dans le xml
        textViewPlayer1 = findViewById(R.id.textViewPlayer1);
        textViewPlayer2 = findViewById(R.id.textViewPlayer2);
        textViewFight = findViewById(R.id.textViewFight);
        HealtBar1 = findViewById(R.id.HealtBar1);
        HealtBar2 = findViewById(R.id.HealtBar2);
        KOP1 = findViewById(R.id.KOP1);
        KOP2 = findViewById(R.id.KOP2);
        UpperCJ1 = findViewById(R.id.UpperCJ1);
        UpperCJ2 = findViewById(R.id.UpperCJ2);
        CPJ2 = findViewById(R.id.CPJ2);
        CPJ1 = findViewById(R.id.CPJ1);


        // Récupération dans l'ancienne activié
        String name1 = getIntent().getStringExtra("Joueur 1");
        String name2 = getIntent().getStringExtra("Joueur 2");
        String TypeCombat = getIntent().getStringExtra("TypeCombat");

        // Insertion dans le textView actuel
        textViewPlayer1.setText(name1);
        textViewPlayer2.setText(name2);
        textViewFight.setText(TypeCombat);

        //Dégat du combat

        KOP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HealtBar1.setText(0);
                Toast.makeText(FightActivity.this, "Joueur 2 Gagnant", Toast.LENGTH_SHORT).show();
                Ko();
            }

            private void Ko() {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }
        });
        KOP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HealtBar2.setText(0);
                Toast.makeText(FightActivity.this, "Joueur 1 Gagnant", Toast.LENGTH_SHORT).show();
                Ko();
            }
        });




    }
}
