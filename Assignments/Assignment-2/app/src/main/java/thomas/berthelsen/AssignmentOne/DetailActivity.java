package thomas.berthelsen.AssignmentOne;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import thomas.berthelsen.AssignmentOne.WordLearnerService;

import com.squareup.picasso.Picasso;

import java.io.Serializable;


import thomas.berthelsen.AssignmentOne.DataAccess.Animal;

import static thomas.berthelsen.AssignmentOne.EditActivity.EDITED_ANIMAL_OBJECT_EDIT;
import static thomas.berthelsen.AssignmentOne.ListActivity.EDIT_ANIMAL_REQUEST;


public class DetailActivity extends AppCompatActivity implements Serializable{

    public static String EDITED_ANIMAL_OBJECT_DETAIL = "edited_animal_object_DETAIL";
    private WordLearnerService wordLearnerService;
    private ServiceConnection wordLearnerServiceConnection;
    private boolean isBound = false;

    ImageView detailAnimalImage;
    TextView detailNameView, detailPronView, detailDescView, detailRatingView, notesTextView;
    Button cancelButton, editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupConnectionToService();

        detailAnimalImage = findViewById(R.id.detailImageView);
        detailNameView = findViewById(R.id.detailNameView);
        detailPronView = findViewById(R.id.detailPronView);
        detailDescView = findViewById(R.id.detailDescView);
        detailRatingView = findViewById(R.id.detailRatingView);
        notesTextView = findViewById(R.id.notesTextView);
        cancelButton = findViewById(R.id.detailCancelButton);
        editButton = findViewById(R.id.detailEditButton);
        deleteButton = findViewById(R.id.detailDelete);

        final Animal animalObject = (Animal)getIntent().getSerializableExtra("AnimalComplete");

        String image = animalObject.getImage();
        String name = animalObject.getName();
        String pron = animalObject.getPron();
        String desc = animalObject.getDesc();
        String rating = animalObject.getRating();
        String note = animalObject.getNotes();

        Picasso.with(this).load(image).placeholder(android.R.drawable.sym_def_app_icon).error(android.R.drawable.sym_def_app_icon).into(detailAnimalImage);
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wordLearnerService.deleteWord(animalObject);

                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        startServiceAsForeground();
        super.onStart();

    }

    @Override
    protected void onStop() {
        wordLearnerService = null;
        unbindService(wordLearnerServiceConnection);
        isBound = false;
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {

            Animal animal = (Animal) data.getSerializableExtra(EDITED_ANIMAL_OBJECT_EDIT);

            Intent newData = new Intent();
            newData.putExtra(EDITED_ANIMAL_OBJECT_DETAIL, animal);
            setResult(RESULT_OK, newData);

            finish();

            }


        }



    private void setupConnectionToService(){
        wordLearnerServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ONSERVICECONNECTED", "ONSERVICECONNECTED");
                wordLearnerService = ((WordLearnerService.WordLearnerServiceBinder)service).getService();
                isBound = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    public void startServiceAsForeground()
    {
        startService(new Intent(DetailActivity.this, WordLearnerService.class));
        bindService(new Intent(DetailActivity.this, WordLearnerService.class), wordLearnerServiceConnection, Context.BIND_AUTO_CREATE);
    }

}

