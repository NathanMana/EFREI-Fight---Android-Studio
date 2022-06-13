package fr.android.project_vyas_manaranche;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.List;

import fr.android.project_vyas_manaranche.models.Fight;
import fr.android.project_vyas_manaranche.services.FightService;

public class HistoryActivity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("create");

        setContentView(R.layout.activity_history);

        handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Fight> listFights = FightService.GetListFights();
                LinearLayout container = findViewById(R.id.ListMatchHistory);

                for (Fight fight: listFights) {

                    // Afficher le résultat
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            container.addView(getFightViewElement(fight));
                        }
                    });
                }
            }
        }).start();
    }

    private LinearLayout getFightViewElement(Fight fight) {

        LinearLayout fightViewElement = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);

        fightViewElement.setLayoutParams(layoutParams);
        fightViewElement.setPadding(5,5,5,5);
        fightViewElement.setBackgroundResource(R.color.history_background_fights);

        fightViewElement.addView(getElementViewLeftSide(fight));
        fightViewElement.addView(getElementViewCenter());
        fightViewElement.addView(getElementViewRightSide(fight));

        fightViewElement.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                handleDisplayStatistics(fight);
            }
        });

        return fightViewElement;
    }

    private LinearLayout getElementViewLeftSide(Fight fight) {

        LinearLayout fightersNameViewElement = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        fightersNameViewElement.setLayoutParams(layoutParams);
        fightersNameViewElement.setOrientation(LinearLayout.VERTICAL);

        System.out.println("FIGHT : " + fight.getListFighter().size());

        fightersNameViewElement.addView(createTextView(fight.getListFighter().get(0).getFullname()));
        fightersNameViewElement.addView(createTextView("vs"));
        fightersNameViewElement.addView(createTextView(fight.getListFighter().get(1).getFullname()));

        return fightersNameViewElement;
    }

    private Space getElementViewCenter() {

        Space space = new Space(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f);

        space.setLayoutParams(layoutParams);

        return space;
    }

    private LinearLayout getElementViewRightSide(Fight fight) {

        LinearLayout fightsInfosViewElement = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        fightsInfosViewElement.setLayoutParams(layoutParams);
        fightsInfosViewElement.setOrientation(LinearLayout.VERTICAL);
        fightsInfosViewElement.setGravity(Gravity.RIGHT);

        fightsInfosViewElement.addView(
                createTextView(fight.getStreetName() + " - " + fight.getCity()));
        fightsInfosViewElement.addView(
                createTextView(fight.getDate().toString()));
        fightsInfosViewElement.addView(createTextView("1500 spectateurs"));

        return fightsInfosViewElement;
    }

    private TextView createTextView(String text) {

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);

        return textView;
    }


    public void handleDisplayStatistics(Fight fight) {

            Intent intent = new Intent(this, StatisticsActivity.class);
            intent.putExtra("fight_id", fight.getFightId());

            startActivity(intent);
    }

    public void handlerBack(View view) {
        finish();
    }
}
