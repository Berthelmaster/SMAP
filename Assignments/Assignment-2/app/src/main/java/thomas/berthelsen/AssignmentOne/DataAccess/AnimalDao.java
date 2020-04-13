package thomas.berthelsen.AssignmentOne.DataAccess;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnimalDao {
    @Query("SELECT * FROM animal")
    List<Animal> getAll();

    @Insert
    void insertAnimals(Animal animal);

    @Delete
    void deleteAnimals(Animal animal);

    @Update
    void updateAnimals(Animal animal);


}
