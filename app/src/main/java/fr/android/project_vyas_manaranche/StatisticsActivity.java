package fr.android.project_vyas_manaranche;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.LinkedList;
import java.util.List;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.models.Fighter;
import fr.android.project_vyas_manaranche.services.FightService;

public class StatisticsActivity extends AppCompatActivity {

    private Handler handler;
    private TextView duration;
    private TextView nbRoundElement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        handler = new Handler();

        duration = findViewById(R.id.durationField);
        nbRoundElement = findViewById(R.id.roundNumberField);

        updateUI(getIntent());
    }

    public void handlerBack(View view) {

        finish();
    }

    private void updateUI(Intent intent) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                // Récupérer les données du combnat
                int fightId = intent.getIntExtra("fight_id", 0);
                if (fightId == 0) return;

                Fight fight = FightService.GetFight(fightId);
                if (fight == null) return;

                LinearLayout statContainer = findViewById(R.id.statistics_container);

                // Afficher le résultat
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        duration.setText(fight.getDuration() + "");
                        nbRoundElement.setText(fight.getListFighter().get(0).getStatistics().size() + "");

                        statContainer.addView(getRoundViewElement(fight));
                    }
                });
            }
        }).start();
    }

    private LinearLayout getRoundViewElement(Fight fight) {

        LinearLayout roundViewElement = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        roundViewElement.setLayoutParams(layoutParams);
        roundViewElement.setOrientation(LinearLayout.VERTICAL);

        // Boucler sur le nbre de round
        for (int i = 0; i < fight.getListFighter().get(0).getStatistics().size(); i++) {

            int round = i + 1;

            // Ajouter titre du round
            roundViewElement.addView(getTitleRoundViewElement(round));

            for (int j = 0; j < 2; j++) {

                Fighter fighter = fight.getListFighter().get(j);

                Fighter enemyFighter = j == 0 ? fight.getListFighter().get(1) : fight.getListFighter().get(0);

                // Ajouter légende du "tableau"
                roundViewElement.addView(getLegendRoundStatsViewElement(fighter.getFullname()));

                // Ajouter data pour ce joueur et ce round
                List<String> listHookData = new LinkedList<String>();
                listHookData.add(getResources().getString(R.string.hook));
                listHookData.add(fighter.getStatistics().get(i).getNbHook() + "");
                listHookData.add(enemyFighter.getStatistics().get(i).getNbHook() + "");

                List<String> listOvercute = new LinkedList<String>();
                listOvercute.add(getResources().getString(R.string.overcute));
                listOvercute.add(fighter.getStatistics().get(i).getNbOvercute() + "");
                listOvercute.add(enemyFighter.getStatistics().get(i).getNbOvercute() + "");

                roundViewElement.addView(getContentStatsViewElement(listOvercute));

                List<String> listDirect = new LinkedList<String>();
                listDirect.add(getResources().getString(R.string.direct));
                listDirect.add(fighter.getStatistics().get(i).getNbDirect() + "");
                listDirect.add(enemyFighter.getStatistics().get(i).getNbDirect() + "");

                roundViewElement.addView(getContentStatsViewElement(listDirect));

                List<String> listKick = new LinkedList<String>();
                listKick.add(getResources().getString(R.string.kick));
                listKick.add(fighter.getStatistics().get(i).getNbKick() + "");
                listKick.add(enemyFighter.getStatistics().get(i).getNbKick() + "");

                roundViewElement.addView(getContentStatsViewElement(listKick));
            }
        }

        return roundViewElement;
    }

    private TextView getTitleRoundViewElement(int round) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 40, 0, 0);

        TextView roundTitleViewElement = new TextView(this);
        roundTitleViewElement.setLayoutParams(layoutParams);

        roundTitleViewElement.setPadding(30, 5, 0, 5);
        roundTitleViewElement.setBackgroundResource(R.color.statistics_round_title);
        roundTitleViewElement.setTextColor(ContextCompat.getColor(this, R.color.white));

        roundTitleViewElement.setText("Round " + round);

        return roundTitleViewElement;
    }

    private LinearLayout getLegendRoundStatsViewElement(String fullname) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);

        LinearLayout legendLayout = new LinearLayout(this);

        // Ajouter espace
        /* space = new Space(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 2));*/

        // Ajouter donné

        TextView takenHits = new TextView(this);
        takenHits.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 1));

        takenHits.setPadding(5,5,5,5);
        takenHits.setText(getResources().getString(R.string.taken));

        TextView name = new TextView(this);
        name.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 1));
        name.setText(fullname);

        TextView givenHits = new TextView(this);
        givenHits.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 1));

        givenHits.setPadding(5,5,5,5);
        givenHits.setText(getResources().getString(R.string.given));

        //legendLayout.addView(space);
        legendLayout.addView(name);
        legendLayout.addView(givenHits);
        legendLayout.addView(takenHits);

        return legendLayout;
    }

    private LinearLayout getContentStatsViewElement(List<String> listContent) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 0);

        LinearLayout legendLayout = new LinearLayout(this);

        TextView hitTypeViewElement = new TextView(this);
        hitTypeViewElement.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 1.28f));

        hitTypeViewElement.setPadding(5,5,5,5);
        hitTypeViewElement.setText(listContent.get(0));

        // Ajouter donné
        TextView givenHits = new TextView(this);
        givenHits.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 1));

        givenHits.setPadding(5,5,5,5);
        givenHits.setText(listContent.get(1));
        givenHits.setGravity(Gravity.CENTER);

        TextView takenHits = new TextView(this);
        takenHits.setLayoutParams(new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, 1));

        takenHits.setPadding(5,5,5,5);
        takenHits.setText(listContent.get(2));
        takenHits.setGravity(Gravity.CENTER);

        legendLayout.addView(hitTypeViewElement);
        legendLayout.addView(givenHits);
        legendLayout.addView(takenHits);

        return legendLayout;
    }

}


