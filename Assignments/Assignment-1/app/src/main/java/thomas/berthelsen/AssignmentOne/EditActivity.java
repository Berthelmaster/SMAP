package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity implements Serializable{

    Button cancelButton, okButton;
    SeekBar seekBarEdit;
    TextView ratingTextViewEdit, name, inputTextView;
    EditText editText;
    public static String EDITED_ANIMAL_OBJECT_EDIT = "edited_animal_object_EDIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        okButton = findViewById(R.id.buttonOK);
        cancelButton = findViewById(R.id.buttonCancel);
        seekBarEdit = findViewById(R.id.seekBarEdit);
        ratingTextViewEdit = findViewById(R.id.textRating);
        inputTextView = findViewById(R.id.inputTextView);
        editText = findViewById(R.id.editPlainText);
        name = findViewById(R.id.nameOfWord);

        final AnimalComplete animalObject = (AnimalComplete)getIntent().getSerializableExtra("AnimalComplete");

        assert animalObject != null;
        name.setText(animalObject.getName());
        ratingTextViewEdit.setText(animalObject.getRating());

        float progressValue = Float.parseFloat(animalObject.getRating()) * 10.0f;
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


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animalObject.setRating(ratingTextViewEdit.getText().toString());

                if (editText.getText() != null)
                {
                    animalObject.setNotes(editText.getText().toString());
                }
                else
                {
                    animalObject.setNotes("");
                }

                Intent data = new Intent();
                data.putExtra(EDITED_ANIMAL_OBJECT_EDIT, animalObject);
                setResult(RESULT_OK, data);

                finish();
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
