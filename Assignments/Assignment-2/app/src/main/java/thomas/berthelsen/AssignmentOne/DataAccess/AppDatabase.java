package thomas.berthelsen.AssignmentOne.DataAccess;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;

@Database(entities = {Animal.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AnimalDao animalDao();
}
