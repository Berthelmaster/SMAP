package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;

import static java.lang.Integer.parseInt;

public class DetailActivity extends AppCompatActivity implements Serializable{

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

        ListActivity.AnimalComplete animalList = (ListActivity.AnimalComplete)getIntent().getSerializableExtra("AnimalObject");

        int image = animalList.getImage();
        String name = animalList.getName();
        String pron = animalList.getPron();
        String desc = animalList.getDesc();
        double rating = animalList.getRating();

        detailAnimalImage.setImageResource(image);
        detailNameView.setText(name);
        detailPronView.setText(pron);
        detailDescView.setText(desc);
        detailRatingView.setText(String.valueOf(rating));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);

                startActivity(intent);
            }
        });


    }
}
