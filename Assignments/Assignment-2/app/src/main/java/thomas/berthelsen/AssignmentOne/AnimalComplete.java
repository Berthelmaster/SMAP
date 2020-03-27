package thomas.berthelsen.AssignmentOne;

import java.io.Serializable;
import java.util.List;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class AnimalComplete implements Serializable {

    private String _name;

    private int _image, _position;
    private String _desc, _pron, _rating, _notes;

    public AnimalComplete(){

    }

    public AnimalComplete(int image, String name, String desc, String pron, String rating, int position, String notes){
        this._image = image;
        this._name = name;
        this._desc = desc;
        this._pron = pron;
        this._rating = rating;
        this._position = position;
        this._notes = notes;


    }

    public int getImage() {return _image;}
    public void setImage(int image) {this._image = image;}

    public String getName() {return _name;}
    public void setName(String name) {this._name = name;}

    public String getDesc() {return _desc;}
    public void setDesc(String desc) {this._desc = desc;}

    public String getPron() {return _pron;}
    public void setPron(String pron) {this._pron = pron;}

    public String getRating() {return _rating;}
    public void setRating(String rating) {this._rating = rating;}

    public int getPosition() {return _position;}
    public void setPosition(int position) {this._position = position;}

    public String getNotes() {return _notes;}
    public void setNotes(String notes) {this._notes = notes;}


}
