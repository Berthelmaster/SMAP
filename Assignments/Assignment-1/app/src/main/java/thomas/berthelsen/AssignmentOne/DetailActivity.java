package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

public class DetailActivity extends AppCompatActivity {

    ImageView detailAnimalImage;
    TextView detailNameView, detailPronView, detailDescView, detailRatingView;
    Button cancelButton, editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailAnimalImage = findViewById(R.id.detailImageView);
        detailNameView = findViewById(R.id.detailNameView);
        detailPronView = findViewById(R.id.detailPronView);
        detailDescView = findViewById(R.id.detailDescView);
        detailRatingView = findViewById(R.id.detailRatingView);
        cancelButton = findViewById(R.id.detailCancelButton);
        editButton = findViewById(R.id.detailEditButton);

        int image = getIntent().getExtras().getInt("AnimalImage");
        String name = getIntent().getExtras().getString("AnimalName");
        String pron = getIntent().getExtras().getString("AnimalPron");
        String desc = getIntent().getExtras().getString("AnimalDesc");
        Double rating = getIntent().getExtras().getDouble("AnimalRating");

        detailAnimalImage.setImageResource(image);
        detailNameView.setText(name);
        detailPronView.setText(pron);
        detailDescView.setText(desc);
        detailRatingView.setText(Double.toString(rating));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
