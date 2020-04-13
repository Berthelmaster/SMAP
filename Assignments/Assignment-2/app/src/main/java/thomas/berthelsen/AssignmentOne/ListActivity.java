package thomas.berthelsen.AssignmentOne;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import thomas.berthelsen.AssignmentOne.DataAccess.Animal;
import thomas.berthelsen.AssignmentOne.DataAccess.AnimalApplication;
import thomas.berthelsen.AssignmentOne.DataAccess.AppDatabase;
import thomas.berthelsen.AssignmentOne.DataAccess.AnimalDao;

import static thomas.berthelsen.AssignmentOne.DetailActivity.EDITED_ANIMAL_OBJECT_DETAIL;
import static thomas.berthelsen.AssignmentOne.WordLearnerService.EXTRA_TASK_RESULT_ANIMAL;
import static thomas.berthelsen.AssignmentOne.WordLearnerService.EXTRA_TASK_RESULT_ANIMAL_DELETED;
import static thomas.berthelsen.AssignmentOne.WordLearnerService.EXTRA_TASK_RESULT_ANIMAL_UPDATE;
import static thomas.berthelsen.AssignmentOne.WordLearnerService.animalList;
import static thomas.berthelsen.AssignmentOne.WordLearnerService.isRunning;

public class ListActivity extends AppCompatActivity implements Serializable {

    public static final String IS_SERVICE_RUNNING = "IS_SERVICE_RUNNING";

     Button exitButton, addButton;
     EditText editText;
     TextView myWords;
     //String animalPronunciations[] = {"ˈbəf(ə)ˌlō", "ˈkaməl", "ˈCHēdə", "ˈkräkəˌdīl", "ˈeləfənt", "jəˈraf", "n(y)o͞o", "ˈko͞odo͞o", "ˈlepərd", "ˈlīən", "null", "ˈästriCH", "SHärk", "snāk"};
     //String animalDescribtions[] = {"a heavily built wild ox with backward-curving horns, found mainly in the Old World tropics","a large, long-necked ungulate mammal of arid country, with long slender legs, broad cushioned feet, and either one or two humps on the back. Camels can survive for long periods without food or drink, chiefly by using up the fat reserves in their humps",
    //"a large slender spotted cat found in Africa and parts of Asia. It is the fastest animal on land", "a large predatory semiaquatic reptile with long jaws, long tail, short legs, and a horny textured skin",
    //"a very large plant-eating mammal with a prehensile trunk, long curved ivory tusks, and large ears, native to Africa and southern Asia. It is the largest living land animal", "a large African mammal with a very long neck and forelegs, having a coat patterned with brown patches separated by lighter lines. It is the tallest living animal",
    //"a large dark antelope with a long head, a beard and mane, and a sloping back", "an African antelope that has a greyish or brownish coat with white vertical stripes, and a short bushy tail. The male has long spirally curved horns",
    //"a large solitary cat that has a fawn or brown coat with black spots, native to the forests of Africa and southern Asia", "a large tawny-coloured cat that lives in prides, found in Africa and NW India. The male has a flowing shaggy mane and takes little part in hunting, which is done cooperatively by the females",
    //"a large antelope living in arid regions of Africa and Arabia, having dark markings on the face and long horns", "a flightless swift-running African bird with a long neck, long legs, and two toes on each foot. It is the largest living bird, with males reaching a height of up to 2.75 m",
    //"a long-bodied chiefly marine fish with a cartilaginous skeleton, a prominent dorsal fin, and tooth-like scales. Most sharks are predatory, though the largest kinds feed on plankton, and some can grow to a large size", "a long limbless reptile which has no eyelids, a short tail, and jaws that are capable of considerable extension. Some snakes have a venomous bite"};
     //int animalImages[] = {R.drawable.buffalo, R.drawable.camel, R.drawable.cheetah,
      //      R.drawable.crocodile, R.drawable.elephant, R.drawable.giraffe,
       //     R.drawable.gnu, R.drawable.kudo, R.drawable.leopard,
        //    R.drawable.lion, R.drawable.oryx, R.drawable.ostrich,
        //    R.drawable.shark, R.drawable.snake};
     //String animalRatings[] = {"3.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0", "5.0"};
     //String notes[] = {"","","","","","","","","","","","","",""};
     public RecyclerView recyclerView;
     public RecyclerView.Adapter adapter;
     static final int EDIT_ANIMAL_REQUEST = 1;

     List<Animal> animalSavedObjects = new ArrayList<>();
     List<Animal> listItems = new ArrayList<>();

     private WordLearnerService wordLearnerService;
     private ServiceConnection wordLearnerServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isRunning)
        {
            startServiceAsForeground();
        }


        myWords = findViewById(R.id.wordsTextView);
        exitButton = findViewById(R.id.exitButton);
        addButton = findViewById(R.id.addButton);
        editText = findViewById(R.id.editText);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerAdaptor(listItems, this);
        recyclerView.setAdapter(adapter);

        //Exit
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wordLearnerService != null)
                {
                    String text = editText.getText().toString();
                    wordLearnerService.addWord(text);
                }
                else
                {
                    Log.d("WORDLEARNERSERVICE", "DIDNT WORK");
                }

            }
        });

        setupConnectionToService();

    }

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();


    }

    @Override
    protected void onStop() {
        super.onStop();
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
                final Animal editedAnimalObject = (Animal) data.getSerializableExtra(EDITED_ANIMAL_OBJECT_DETAIL);

                assert editedAnimalObject != null;



                //animalSavedObjects.add(editedAnimalObject);
                //listItems.set(position, editedAnimalObject);
                //adapter.notifyItemChanged(position);

            }
    }

    public void getAll()
    {
        List<Animal> animals = ((AnimalApplication)getApplicationContext()).getAppDatabase().animalDao().getAll();

        for (Animal animal : animals)
        {
            Log.d("ANIMALLOG", animal.getName());
        }
    }


    //Recycler Adaptor inspired from this youtube series: https://www.youtube.com/watch?v=5T144CbTwjc&list=PLk7v1Z2rk4hjHrGKo9GqOtLs1e2bglHHA&index=2
    public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> implements Serializable{

        public RecyclerAdaptor(List<Animal> listitems, Context context) {
            this.listitems = listitems;
            this.context = context;
        }

        private List<Animal> listitems;
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
            final Animal listitem = listitems.get(position);

            holder.NameViewHolder.setText(listitem.getName());
            holder.RatingViewHolder.setText(listitem.getRating());
            holder.PronunciationViewHolder.setText(listitem.getPron());

            //Picasso from https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library
            Picasso.with(context).load(listitem.getImage()).placeholder(android.R.drawable.sym_def_app_icon).error(android.R.drawable.sym_def_app_icon).into(holder.ImageViewHolder);

            holder.LinearLayoutHolder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    intent.putExtra("AnimalComplete", (Serializable) listitem);
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

    private void setupConnectionToService(){
        wordLearnerServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ONSERVICECONNECTED", "ONSERVICECONNECTED");
                wordLearnerService = ((WordLearnerService.WordLearnerServiceBinder)service).getService();


                List<Animal> animals = wordLearnerService.getAllWords();
                //listItems = animals != null ? animals : new ArrayList<Animal>();
                //adapter.notifyDataSetChanged();
                if(listItems.size() == 0 && animals.size() != 0)
                {
                    Log.d("IM IN HERE", "IN HERE");
                    for (Animal a : animals)
                    {
                        listItems.add(a);
                    }
                    adapter.notifyDataSetChanged();
                }

                Log.d("SIZE OF ANIMALS", String.valueOf(animals.size()));


            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean isServiceRunning = utilityAppHelper.getIsServiceRunning(this, IS_SERVICE_RUNNING);

        Log.d("IsServiceRunning?", String.valueOf(isRunning));

        bindService(new Intent(ListActivity.this, WordLearnerService.class), wordLearnerServiceConnection, Context.BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WordLearnerService.BROADCAST_BACKGROUND_SERVICE_RESULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(ListAcitvityBroadCastReceiver, filter);

        IntentFilter filterDelete = new IntentFilter();
        filterDelete.addAction(WordLearnerService.BROADCAST_BACKGROUND_SERVICE_RESULT_DELETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(ListAcitivtyBroadCastReceiverDelete, filterDelete);

        IntentFilter filterUpdate = new IntentFilter();
        filterUpdate.addAction(WordLearnerService.BROADCAST_BACKGROUND_SERVICE_RESULT_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(ListAcitivtyBroadCastReceiverUpdate, filterUpdate);

    }


    public void populateList(Animal animal)
    {
        if(!listContainsName(animal))
        {
            listItems.add(animal);
            adapter.notifyDataSetChanged();
        }
    }

    public void deleteAnimal(Animal animal)
    {
        for (Iterator<Animal> iterator = listItems.iterator(); iterator.hasNext();)
        {
            if(iterator.next().getName().equals(animal.getName()))
            {
                iterator.remove();
            }
        }
        adapter.notifyDataSetChanged();

    }

    public void updateAnimal(Animal animal)
    {
        int getIndex = getIndexOfArrayList(animal);

        listItems.set(getIndex, animal);

        adapter.notifyDataSetChanged();
    }

    // This is a patch for some code i did earlier, not the best idea to iterate like this, but it works for now.
    private boolean listContainsName(Animal animal)
    {
        boolean doesContain = false;

        for (Animal eaAnimal : listItems)
        {
            if (eaAnimal.getName().equals(animal.getName())) {
                doesContain = true;
                break;
            }
        }

        return doesContain;
    }


    public void startServiceAsForeground()
    {
        startService(new Intent(ListActivity.this, WordLearnerService.class));
        utilityAppHelper.setIsServiceRunning(this, IS_SERVICE_RUNNING, true);
    }

    public void stopService()
    {
        if(wordLearnerService != null)
        {
            stopService(new Intent(ListActivity.this, WordLearnerService.class));
            unbindService(wordLearnerServiceConnection);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(ListAcitvityBroadCastReceiver);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(ListAcitivtyBroadCastReceiverDelete);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(ListAcitivtyBroadCastReceiverUpdate);

            utilityAppHelper.setIsServiceRunning(this, IS_SERVICE_RUNNING, false);
            Log.d("IVE BEEN CALLED", "CALLED");
        }
    }

    private BroadcastReceiver ListAcitvityBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle animalBundle = intent.getExtras();

             Animal animal = (Animal)animalBundle.getSerializable(EXTRA_TASK_RESULT_ANIMAL);

             Log.d("ANIMAL ON RECEIVE", animal.getName());

             populateList(animal);

        }
    };

    private BroadcastReceiver ListAcitivtyBroadCastReceiverDelete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle animalBundle = intent.getExtras();

            Animal animal = (Animal)animalBundle.getSerializable(EXTRA_TASK_RESULT_ANIMAL_DELETED);

            deleteAnimal(animal);

            Log.d("Deleted BroadCast", animal.getName());
        }
    };

    private BroadcastReceiver ListAcitivtyBroadCastReceiverUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle animalBundle = intent.getExtras();

            Animal animal = (Animal)animalBundle.getSerializable(EXTRA_TASK_RESULT_ANIMAL_UPDATE);

            updateAnimal(animal);

            Log.d("Updated Broadcast", animal.getName());
        }
    };

    public int getIndexOfArrayList(Animal animal)
    {
        for ( Animal ani : listItems)
        {
            if(ani.getName().equals(animal.getName()))
            {
                return listItems.indexOf(ani);
            }
        }

        return -1;
    }


}



