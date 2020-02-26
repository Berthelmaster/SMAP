package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity implements Serializable{

    Button cancelButton;
    SeekBar seekBarEdit;
    TextView ratingTextViewEdit, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cancelButton = findViewById(R.id.buttonCancel);
        seekBarEdit = findViewById(R.id.seekBarEdit);
        ratingTextViewEdit = findViewById(R.id.textRating);
        name = findViewById(R.id.nameOfWord);

        final AnimalComplete animalObject = (AnimalComplete)getIntent().getSerializableExtra("AnimalComplete");

        name.setText(animalObject.getName());
        ratingTextViewEdit.setText(animalObject.getRating());

        float progressValue = Float.valueOf(animalObject.getRating()) * 10.0f;
        seekBarEdit.setProgress((int) progressValue);


        seekBarEdit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                {
                    float value = ((float)progress / 10.0f);
                    ratingTextViewEdit.setText(String.valueOf(value));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
