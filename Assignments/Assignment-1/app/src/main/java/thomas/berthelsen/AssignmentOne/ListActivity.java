package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class ListActivity extends AppCompatActivity {

    ListView listVIew;
    Button exitButton;
    CardView cardView;
    ImageView animalImageView;
    TextView myWords, animalImageTitle, animalImageScoreButton, animalPronunciation;
    String animalTitles[] = {"buffalo", "camel", "cheetah", "crocodile", "elephant", "giraffe", "gnu", "kudo", "leopard", "lion", "oryx", "ostrich", "shark", "snake"};
    String animalPronunciations[] = {"ˈbəf(ə)ˌlō", "ˈkaməl", "ˈCHēdə", "ˈkräkəˌdīl", "ˈeləfənt", "jəˈraf", "n(y)o͞o", "ˈko͞odo͞o", "ˈlepərd", "ˈlīən", "null", "ˈästriCH", "SHärk", "snāk"};
    String animalDescribtions[] = {"a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics","a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps",
    "a large slender spotted cat found in Africa and parts of Asia. It is the fastest animal on land", "a large predatory semiaquatic reptile with long jaws, long tail, short legs, and a horny textured skin",
    "a very large plant-eating mammal with a prehensile trunk, long curved ivory tusks, and large ears, native to Africa and southern Asia. It is the largest living land animal", "a large African mammal with a very long neck and forelegs, having a coat patterned with brown patches separated by lighter lines. It is the tallest living animal",
    "a large dark antelope with a long head, a beard and mane, and a sloping back", "an African antelope that has a greyish or brownish coat with white vertical stripes, and a short bushy tail. The male has long spirally curved horns",
    "a large solitary cat that has a fawn or brown coat with black spots, native to the forests of Africa and southern Asia", "a large tawny-coloured cat that lives in prides, found in Africa and NW India. The male has a flowing shaggy mane and takes little part in hunting, which is done cooperatively by the females",
    "a large antelope living in arid regions of Africa and Arabia, having dark markings on the face and long horns", "a flightless swift-running African bird with a long neck, long legs, and two toes on each foot. It is the largest living bird, with males reaching a height of up to 2.75 m",
    "a long-bodied chiefly marine fish with a cartilaginous skeleton, a prominent dorsal fin, and tooth-like scales. Most sharks are predatory, though the largest kinds feed on plankton, and some can grow to a large size", "a long limbless reptile which has no eyelids, a short tail, and jaws that are capable of considerable extension. Some snakes have a venomous bite"};
    int animalImages[] = {R.drawable.buffalo, R.drawable.camel, R.drawable.cheetah,
            R.drawable.crocodile, R.drawable.elephant, R.drawable.giraffe,
            R.drawable.gnu, R.drawable.kudo, R.drawable.leopard,
            R.drawable.lion, R.drawable.oryx, R.drawable.ostrich,
            R.drawable.shark, R.drawable.snake};
    double animalRatings[] = {5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWords = findViewById(R.id.wordsTextView);
        listVIew = findViewById(R.id.listView);
        exitButton = findViewById(R.id.exitButton);


        customListAdaptor listAdapter = new customListAdaptor();

        listVIew.setAdapter(listAdapter);




        //Exit
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class customListAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return animalTitles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listcontent,parent, false);

            //Set relations
            cardView = convertView.findViewById(R.id.cardView);
            animalImageView = convertView.findViewById(R.id.animalImage);
            animalImageTitle = convertView.findViewById(R.id.animalTitle);
            animalPronunciation = convertView.findViewById(R.id.animalPronunciation);
            animalImageScoreButton = convertView.findViewById(R.id.animalRatingButton);


            //Set each view
            animalImageView.setImageResource(animalImages[position]);
            animalImageTitle.setText(animalTitles[position]);
            animalPronunciation.setText(animalPronunciations[position]);
            animalImageScoreButton.setText(String.valueOf(animalRatings[position]));


            final int animalImage = animalImages[position];
            final String animalName = animalTitles[position];
            final String animalDesc = animalDescribtions[position];
            final String animalPron = animalPronunciations[position];
            final double animalRating = animalRatings[position];

            //Goto DetailsActivity
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);

                    intent.putExtra("AnimalImage", animalImage);
                    intent.putExtra("AnimalName", animalName);
                    intent.putExtra("AnimalPron", animalPron);
                    intent.putExtra("AnimalDesc", animalDesc);
                    intent.putExtra("AnimalRating", animalRating);


                    startActivity(intent);
                }
            });

            return convertView;
        }
    }


    // Will be used later in the course
    public class AnimalComplete implements Serializable {

        private int Image;
        private double Rating;
        private String Name, Desc, Pron;

        AnimalComplete(int image, String name, String desc, String pron, double rating){
            this.Image = image;
            this.Name = name;
            this.Desc = desc;
            this.Pron = pron;
            this.Rating = rating;
        }

        public int getImage() {return Image;}
        public void setImage(int image) {this.Image = image;}

        public String getName() {return Name;}
        public void setName(String name) {this.Name = name;}

    }


}

