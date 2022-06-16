package fr.android.project_vyas_manaranche;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.sql.Timestamp;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.models.PlayerRoundStatistics;
import fr.android.project_vyas_manaranche.services.FightService;

public class FightActivity extends AppCompatActivity {

    Fight fight;
    int round;
    Handler handler;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        Intent intent = getIntent();
        handler = new Handler();

        startTime = new Timestamp(System.currentTimeMillis()).getTime();

        // Récupérer le combat en bdd pour modifier l'objet (notamment ses enfants) au fur et à mesure
        int fightId = intent.getIntExtra("FightId", 0);
        if (fightId == 0) return;

        new Thread(new Runnable() {
            @Override
            public void run() {

                fight = FightService.GetFight(fightId);
                round = 0;

                initializeClickListeners();

                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        setupScoreBoard(intent);
                    }
                });

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        managerRoundTimer();
                    }
                }).start();
            }
        }).start();
    }

    private void setupScoreBoard(Intent intent) {

        int nbRound = intent.getIntExtra("NbRound", 0);

        for (int k = 1; k < 3; k++) {

            String player = intent.getStringExtra("player" + k);

            TextView controlPanelNamePlayer = findViewById(getIdWithString("controlPanel_namePlayer" + k));
            controlPanelNamePlayer.setText(player);

            TextView playerName = findViewById(getIdWithString("namePlayer" + k));
            playerName.setText(player);

            LinearLayout score_board_legend_round = findViewById(getIdWithString("score_board_legend_round_" + k));
            LinearLayout scoreBoardHook = findViewById(getIdWithString("score_board_hook_" + k));
            LinearLayout scoreBoardOvercute = findViewById(getIdWithString("score_board_overcute_" + k));
            LinearLayout scoreBoardDirect = findViewById(getIdWithString("score_board_direct_" + k));
            LinearLayout scoreBoardKick = findViewById(getIdWithString("score_board_kick_" + k));

            // Boucle sur les colonnes
            for (int i = 0; i < nbRound; i++) {

                score_board_legend_round.addView(addtextViewToScoreBoard("R" + (i + 1)));
                scoreBoardHook.addView(addtextViewToScoreBoard("0"));
                scoreBoardOvercute.addView(addtextViewToScoreBoard("0"));
                scoreBoardDirect.addView(addtextViewToScoreBoard("0"));
                scoreBoardKick.addView(addtextViewToScoreBoard("0"));
            }
        }
    }

    /**
     * Permet de récupérer l'id (int) d'une ressource via l'id (string)
     * @param id
     * @return
     */
    private int getIdWithString(String id) {
        return getResources().getIdentifier(id,"id", this.getPackageName());
    }

    private TextView addtextViewToScoreBoard(String content) {

        TextView newText = new TextView(this);
        newText.setLayoutParams(new LinearLayout.LayoutParams(
                100, RelativeLayout.LayoutParams.WRAP_CONTENT));
        newText.setText(content);

        return newText;
    }

    public void handlerBack(View view) { finish(); }

    private void initializeClickListeners() {

        String[] listHits = new String[] { "hook", "overcute", "direct", "kick" };

        // Pour chaque joueur
        for (int i = 1; i < 3; i ++) {

            Fighter fighter = fight.getListFighter().get(i - 1);

            // Pour chaque bouton dans le control panel
            for (int k = 0; k < listHits.length; k++) {

                String btnName = listHits[k];

                Button btn = findViewById(getIdWithString("controlPanel_p" + i + "_" + btnName));
                int finalI = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        PlayerRoundStatistics roundStats = fighter.getStatistics().get(round);
                        String capitalizedAction = btnName.substring(0, 1).toUpperCase() + btnName.substring(1);

                        try {
                            roundStats.getClass().getDeclaredMethod("add" + capitalizedAction).invoke(roundStats);

                            ViewGroup scoreBoardHook = findViewById(getIdWithString("score_board_" + btnName + "_" + finalI));
                            TextView textView = (TextView)scoreBoardHook.getChildAt(round + 1);

                            // Mettre à jour l'affichage
                            updateTextView(
                                    textView,
                                    roundStats.getClass().getDeclaredMethod("getNb" + capitalizedAction).invoke(roundStats) + "");

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                    }
                });
            }
        }
    }

    private void updateTextView(TextView textView, String content) {
        textView.setText(content);
    }

    private void managerRoundTimer() {

        int limitRound = fight.getListFighter().get(0).getStatistics().size();

        while (true) {
            try {

                Thread.sleep(15000);

                if (round + 1 == limitRound) {

                    long endTime = new Timestamp(System.currentTimeMillis()).getTime();
                    fight.setDuration(Math.round((endTime - startTime) / 1000));

                    FightService.updateFight(fight);

                    if (ContextCompat.checkSelfPermission(FightActivity.this, Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_GRANTED) {
                        displayCamera();
                        return;
                    }

                    ActivityCompat.requestPermissions(FightActivity.this,
                            new String[]{
                                    Manifest.permission.CAMERA
                            }, 100);
                    return;
                }

                round++;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FightActivity.this, R.string.going_next_round,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(FightActivity.this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            displayCamera();
            return;
        }

        // Match terminé
        Intent intent = new Intent(FightActivity.this, StatisticsActivity.class);
        intent.putExtra("fight_id", fight.getFightId());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode) {
            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra("fight_id", fight.getFightId());
            intent.putExtra("image", (Bitmap) data.getExtras().get("data"));
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("fight_id", fight.getFightId());
        startActivity(intent);
    }
}
