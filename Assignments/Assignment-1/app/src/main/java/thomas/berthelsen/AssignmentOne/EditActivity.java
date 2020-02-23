package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class EditActivity extends AppCompatActivity implements Serializable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }
}
