package thomas.berthelsen.AssignmentOne;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;


import static thomas.berthelsen.AssignmentOne.EditActivity.EDITED_ANIMAL_OBJECT_EDIT;
import static thomas.berthelsen.AssignmentOne.ListActivity.EDIT_ANIMAL_REQUEST;


public class DetailActivity extends AppCompatActivity implements Serializable{

    public static String EDITED_ANIMAL_OBJECT_DETAIL = "edited_animal_object_DETAIL";

    ImageView detailAnimalImage;
    TextView detailNameView, detailPronView, detailDescView, detailRatingView, notesTextView;
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
        notesTextView = findViewById(R.id.notesTextView);
        cancelButton = findViewById(R.id.detailCancelButton);
        editButton = findViewById(R.id.detailEditButton);

        final AnimalComplete animalObject = (AnimalComplete)getIntent().getSerializableExtra("AnimalComplete");

        int image = animalObject.getImage();
        String name = animalObject.getName();
        String pron = animalObject.getPron();
        String desc = animalObject.getDesc();
        String rating = animalObject.getRating();
        String note = animalObject.getNotes();

        detailAnimalImage.setImageResource(image);
        detailNameView.setText(name);
        detailPronView.setText(pron);
        detailDescView.setText(desc);
        notesTextView.setText(note);
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

        if (resultCode == RESULT_OK)
        {
            Log.d("__InD", "IN");
            assert data != null;
            AnimalComplete animal = (AnimalComplete) data.getSerializableExtra(EDITED_ANIMAL_OBJECT_EDIT);

            assert animal != null;
            Intent newData = new Intent();
            newData.putExtra(EDITED_ANIMAL_OBJECT_DETAIL, animal);
            setResult(RESULT_OK, newData);

            finish();

            }


        }

    }

