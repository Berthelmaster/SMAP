package thomas.berthelsen.AssignmentOne.DataAccess;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import thomas.berthelsen.AssignmentOne.HttpService.Definition;
import thomas.berthelsen.AssignmentOne.HttpService.Model;

@Entity
public class Animal {

    // Yes uids? maybe not so fitting when i think about it, more like animalids ?
    @PrimaryKey(autoGenerate = true)
    protected int _uid;

    @ColumnInfo(name = "_name")
    private String _name;

    private int _position;
    private String _desc, _pron, _rating, _notes, _image;


    public Animal(){

    }

    public Animal(Model jsonModel)
    {
        Definition definition = jsonModel.getDefinitions().get(0);

        _image = definition.getImageUrl();
        _desc = definition.getDefinition();
        _pron =  jsonModel.getPronunciation();
        _name = jsonModel.getWord();

        if(getRating() == null)
            _rating = "5";
        if(getNotes() == null)
            _notes = "Default note";
    }

    @Ignore
    public Animal(String image, String name, String desc, String pron, String rating, int position, String notes){
        this._image = image;
        this._name = name;
        this._desc = desc;
        this._pron = pron;
        this._rating = rating;
        this._position = position;
        this._notes = notes;
    }

    public int getId() {return _uid;}
    public void setUid(int uid) {this._uid = uid; }

    public String getImage() {return _image;}
    public void setImage(String image) {this._image = image;}

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
