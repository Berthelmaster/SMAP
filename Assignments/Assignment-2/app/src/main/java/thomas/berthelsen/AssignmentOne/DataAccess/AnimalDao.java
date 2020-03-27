package thomas.berthelsen.AssignmentOne.DataAccess;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AnimalDao {
    @Query("SELECT * FROM animal")
    List<Animal> getAll();

    @Query("SELECT * FROM animal WHERE _uid IN (:userIds)")
    List<Animal> loadAllbyIds(int[] userIds);

    @Insert
    void insertAnimals(Animal... animals);


}
