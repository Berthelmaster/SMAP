package thomas.berthelsen.AssignmentOne;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;

import static java.lang.Integer.parseInt;
import static thomas.berthelsen.AssignmentOne.ListActivity.EDIT_ANIMAL_REQUEST;

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

        final AnimalComplete animalObject = (AnimalComplete)getIntent().getSerializableExtra("AnimalComplete");

        int image = animalObject.getImage();
        String name = animalObject.getName();
        String pron = animalObject.getPron();
        String desc = animalObject.getDesc();
        String rating = animalObject.getRating();

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
                intent.putExtra("AnimalComplete", animalObject);
                startActivityForResult(intent, EDIT_ANIMAL_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
