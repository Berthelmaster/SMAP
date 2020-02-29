package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    AnimalComplete animalGlobal = new AnimalComplete();
    AnimalComplete animalObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);




        if (savedInstanceState != null)
        {
             animalObject = (AnimalComplete) savedInstanceState.getSerializable("keyEdit");
        }



        okButton = findViewById(R.id.buttonOK);
        cancelButton = findViewById(R.id.buttonCancel);
        seekBarEdit = findViewById(R.id.seekBarEdit);
        ratingTextViewEdit = findViewById(R.id.textRating);
        inputTextView = findViewById(R.id.inputTextView);
        editText = findViewById(R.id.editPlainText);
        name = findViewById(R.id.nameOfWord);

        animalObject = (AnimalComplete)getIntent().getSerializableExtra("AnimalComplete");

        assert animalObject != null;
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
                    animalObject.setRating(String.valueOf(value));

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                animalGlobal = animalObject;
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animalGlobal.setNotes(editText.getText().toString());

            }
        });




        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER)
                {
                    closeKeyboard();
                }
                return false;
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


    //This is taken from this youtube video: https://www.youtube.com/watch?v=CW5Xekqfx3I
    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("keyEdit", animalGlobal);
    }
}
