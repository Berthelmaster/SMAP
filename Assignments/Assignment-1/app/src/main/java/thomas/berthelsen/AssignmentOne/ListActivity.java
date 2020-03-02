package thomas.berthelsen.AssignmentOne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static thomas.berthelsen.AssignmentOne.DetailActivity.EDITED_ANIMAL_OBJECT_DETAIL;

public class ListActivity extends AppCompatActivity implements Serializable {

     Button exitButton;
     TextView myWords;
     String animalNames[] = {"buffalo", "camel", "cheetah", "crocodile", "elephant", "giraffe", "gnu", "kudo", "leopard", "lion", "oryx", "ostrich", "shark", "snake"};
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
     String animalRatings[] = {"3.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0"};
     String notes[] = {"","","","","","","","","","","","","",""};
     RecyclerView recyclerView;
     RecyclerView.Adapter adapter;
     static final int EDIT_ANIMAL_REQUEST = 1;

     List<AnimalComplete> animalSavedObjects = new ArrayList<>();
     List<AnimalComplete> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            List<AnimalComplete> animalObjects = (List<AnimalComplete>) savedInstanceState.getSerializable("key");

            for (AnimalComplete animal : animalObjects)
            {
                animalRatings[animal.getPosition()] = animal.getRating();
                notes[animal.getPosition()] = animal.getNotes();
            }

            // SAVE LIST FOR FUTURE USE
            animalSavedObjects = animalObjects;
        }

        myWords = findViewById(R.id.wordsTextView);
        exitButton = findViewById(R.id.exitButton);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        for (int i = 0; i <= animalImages.length-1; i++)
        {
            AnimalComplete animal = new AnimalComplete(animalImages[i], animalNames[i], animalDescribtions[i], animalPronunciations[i], animalRatings[i], i, notes[i]);

            listItems.add(animal);
        }

        adapter = new RecyclerAdaptor(listItems, this);

        recyclerView.setAdapter(adapter);

        //Exit
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("key", (Serializable) animalSavedObjects);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK){
                assert data != null;
                final AnimalComplete editedAnimalObject = (AnimalComplete) data.getSerializableExtra(EDITED_ANIMAL_OBJECT_DETAIL);

                assert editedAnimalObject != null;
                int position = editedAnimalObject.getPosition();

                animalSavedObjects.add(editedAnimalObject);
                listItems.set(position, editedAnimalObject);
                adapter.notifyItemChanged(position);

            }
    }

    //Recycler Adaptor inspired from this youtube series: https://www.youtube.com/watch?v=5T144CbTwjc&list=PLk7v1Z2rk4hjHrGKo9GqOtLs1e2bglHHA&index=2
    public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> implements Serializable{

        public RecyclerAdaptor(List<AnimalComplete> listitems, Context context) {
            this.listitems = listitems;
            this.context = context;
        }

        private List<AnimalComplete> listitems;
        private Context context;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listcontent, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final AnimalComplete listitem = listitems.get(position);

            holder.ImageViewHolder.setImageResource(listitem.getImage());
            holder.NameViewHolder.setText(listitem.getName());
            holder.RatingViewHolder.setText(listitem.getRating());
            holder.PronunciationViewHolder.setText(listitem.getPron());

            holder.LinearLayoutHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    intent.putExtra("AnimalComplete", listitem);
                    startActivityForResult(intent, EDIT_ANIMAL_REQUEST);
                }
            });


        }


        @Override
        public int getItemCount() {
            return listitems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ImageViewHolder;
            TextView NameViewHolder, RatingViewHolder, PronunciationViewHolder;
            LinearLayout LinearLayoutHolder;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                ImageViewHolder = (ImageView) itemView.findViewById(R.id.AnimalImage);
                NameViewHolder = (TextView) itemView.findViewById(R.id.AnimalName);
                RatingViewHolder = (TextView) itemView.findViewById(R.id.AnimalRating);
                PronunciationViewHolder = (TextView) itemView.findViewById(R.id.AnimalPronunciation);
                LinearLayoutHolder = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            }
        }
    }

}


