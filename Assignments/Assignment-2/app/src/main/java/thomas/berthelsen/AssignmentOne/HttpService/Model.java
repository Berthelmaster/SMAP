package thomas.berthelsen.AssignmentOne.HttpService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("example")
    @Expose
    private Object example;
    @SerializedName("image_url")
    @Expose
    private Object imageUrl;
    @SerializedName("emoji")
    @Expose
    private Object emoji;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Object getExample() {
        return example;
    }

    public void setExample(Object example) {
        this.example = example;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getEmoji() {
        return emoji;
    }

    public void setEmoji(Object emoji) {
        this.emoji = emoji;
    }

}
