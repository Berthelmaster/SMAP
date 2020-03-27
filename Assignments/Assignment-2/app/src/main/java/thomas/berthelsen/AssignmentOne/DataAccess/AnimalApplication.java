package thomas.berthelsen.AssignmentOne.DataAccess;

import android.app.Application;
import androidx.room.Room;

public class AnimalApplication extends Application {

    private AppDatabase db;

    public AppDatabase getAppDatabase(){
        if(db == null){
            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "myDatabase").build();
        }

        return db;
    }
}
