package thomas.berthelsen.AssignmentOne;

import java.io.Serializable;
import java.util.List;

public class AnimalComplete implements Serializable {

    private int _image, _position;
    private String _name, _desc, _pron, _rating;
    private List<String> _notes;

    public AnimalComplete(int image, String name, String desc, String pron, String rating, int position){
        this._image = image;
        this._name = name;
        this._desc = desc;
        this._pron = pron;
        this._rating = rating;
        this._position = position;


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

    public List<String> getNotes() {return _notes;}
    public void setNotes(String note) {this._notes.add(note);}


}
