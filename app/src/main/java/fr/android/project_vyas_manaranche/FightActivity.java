package fr.android.project_vyas_manaranche;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FightActivity extends AppCompatActivity {
    //déclaration de textview
    TextView textViewPlayer1, textViewPlayer2, textViewFight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);
        // Récupéreation dans le xml
        textViewPlayer1 = findViewById(R.id.textViewPlayer1);
        textViewPlayer2 = findViewById(R.id.textViewPlayer2);
        textViewFight = findViewById(R.id.textViewFight);

        // Récupération dans l'ancienne activié
        String name1 = getIntent().getStringExtra("Joueur 1");
        String name2 = getIntent().getStringExtra("Joueur 2");
        String TypeCombat = getIntent().getStringExtra("TypeCombat");

        // Insertion dans le textView actuel
        textViewPlayer1.setText(name1);
        textViewPlayer2.setText(name2);
        textViewFight.setText(TypeCombat);


    }
}
