package fr.android.project_vyas_manaranche;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    int fightId;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Récupérer l'image et l'id du combat
        Intent intent = getIntent();
        fightId = intent.getIntExtra("fight_id", 0);

        ImageView imageView = findViewById(R.id.imageView);
        Bitmap captureImage = (Bitmap) intent.getExtras().get("image");
        imageView.setImageBitmap(captureImage);
    }

    public void handlerNext(View view) {

        Intent intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("fight_id", fightId);
        startActivity(intent);
    }

    public void handlerBack(View view) { finish(); }

}
