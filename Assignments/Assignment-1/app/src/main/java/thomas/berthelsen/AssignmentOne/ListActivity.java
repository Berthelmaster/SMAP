package thomas.berthelsen.AssignmentOne;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListActivity extends AppCompatActivity {

    TextView myWords;
    ListView listVIew;
    Button exitButton;
    String animalTitle[] = {"buffalo", "camel", "cheetah", "crocodile", "elephant", "giraffe", "gnu", "kudo", "leopard", "lion", "oryx", "ostrich", "shark", "snake"};
    String animalDescribtion[] = {"a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics","a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps",
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
            return animalTitle.length;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.listcontent,parent, false);

            double score = 5.00;
            ImageView animalImageView;
            TextView animalImageTitle;
            TextView animalImageDescribtion;
            Button animalImageScoreButton;




            animalImageView = convertView.findViewById(R.id.animalImage);
            animalImageTitle = convertView.findViewById(R.id.animalTitle);
            animalImageDescribtion = convertView.findViewById(R.id.animalDescribtion);
            animalImageScoreButton = convertView.findViewById(R.id.animalRatingButton);

            animalImageView.setImageResource(animalImages[position]);
            animalImageTitle.setText(animalTitle[position]);
            animalImageDescribtion.setText(animalDescribtion[position]);
            animalImageScoreButton.setText(String.valueOf(score));



            return convertView;
        }
    }
}

