package thomas.berthelsen.AssignmentOne;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Update;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import thomas.berthelsen.AssignmentOne.DataAccess.Animal;
import thomas.berthelsen.AssignmentOne.DataAccess.AnimalApplication;
import thomas.berthelsen.AssignmentOne.HttpService.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WordLearnerService extends Service {

    String animalNames[] = {"buffalo", "camel", "cheetah", "crocodile", "elephant", "giraffe", "gnu", "kudo", "leopard", "lion", "oryx", "ostrich", "shark", "snake"};

    public static final String BROADCAST_BACKGROUND_SERVICE_RESULT = "BACKGROUND_SERVICE_RESULT_SENT";
    public static final String BROADCAST_BACKGROUND_SERVICE_RESULT_DELETE = "BROADCAST_BACKGROUND_SERVICE_RESULT_DELETE";
    public static final String BROADCAST_BACKGROUND_SERVICE_RESULT_UPDATE = "BROADCAST_BACKGROUND_SERVICE_RESULT_UPDATE";
    public static final String EXTRA_TASK_RESULT_ANIMAL = "TASK_RESULT_ANIMAL";
    public static final String EXTRA_TASK_RESULT_ANIMAL_DELETED = "TASK_RESULT_ANIMAL_DELETED";
    public static final String EXTRA_TASK_RESULT_ANIMAL_UPDATE = "TASK_RESULT_ANIMAL_UPDATE";
    public static final String APP_FIRST_RUN = "APP_FIRST_RUN";
    public static final String NOTIFICATIONS_ID = "NOTIFICATIONS_ID";
    public static final String NOTIFICATIONS_NAME = "NOTIFICATIONS_NAME";
    public static final Integer NOTIFICATIONS_ID_INTEGER = 1;
    public static boolean isRunning = false;
    private static Context _context;


    public static List<Animal> animalList = new ArrayList<>();
    private boolean running = false;
    RequestQueue queue;
    private ExecutorService notificationsExecutor;
    private NotificationManagerCompat notificationManagerCompat;


    public class WordLearnerServiceBinder extends Binder {
        WordLearnerService getService() {return WordLearnerService.this;}
    }

    private final IBinder binder = new WordLearnerServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        WordLearnerService._context = getApplicationContext();

        Log.d("ONCREATE", "SERVICE");

        if(queue==null)
        {
            queue = Volley.newRequestQueue(this);
        }

        ApplyWordOnInitialRun();

        setupNotifications();

        Log.d("ISITEMPTY?", String.valueOf(animalList.isEmpty()));

        UpdateNotifications();
    }

    public void ApplyWordOnInitialRun()
    {
        Log.d("IM RUNNING NOW!", "TRUEEE");

        boolean hasAppRun = utilityAppHelper.getHasAppRun(this, APP_FIRST_RUN);
        Log.d("HASAPPRUN?", String.valueOf(hasAppRun));
        if(!hasAppRun)
        {
            for (String name : animalNames)
            {
                addWord(name);
            }

            utilityAppHelper.setHasAppRun(this, APP_FIRST_RUN, true);
        }
        else{
            getAllWordsTask task = new getAllWordsTask(this);
            task.execute();
        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ONSTARTCOMMAND", "ONSTARTCOMMAND");
        isRunning = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Log.d("ISRUNNINGFALSE", "NO LONGER RUNNINGER");
    }

    public List<Animal> getAllWords()
    {
        return animalList;
    };

    public void addWord(String w)
    {
        Log.d("NOW ADDING", "ADDING!");
        boolean doesNameExist = false;
            for (Animal animal : animalList)
            {
                if (animal.getName().equals(w))
                {
                    doesNameExist = true;
                }
            }

            if(!doesNameExist)
            {
                Log.d("Sending Animal to Add", w);
                sendRequest(w);
            }

    }


    public Animal getWord(String w)
    {
        Animal newanimalwd = new Animal();

        return newanimalwd;
    }

    public void deleteWord(Animal w)
    {
        deleteWordTask task = new deleteWordTask(this);
        task.execute(w);
    }

    public void updateWord(Animal w)
    {
        UpdateWordTask task = new UpdateWordTask(this);
        task.execute(w);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void broadcastTaskResult(Animal animal)
    {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_BACKGROUND_SERVICE_RESULT);
        broadcastIntent.putExtra(EXTRA_TASK_RESULT_ANIMAL, (Serializable) animal);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastTaskDeletedResult(Animal animal){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_BACKGROUND_SERVICE_RESULT_DELETE);
        broadcastIntent.putExtra(EXTRA_TASK_RESULT_ANIMAL_DELETED, (Serializable) animal);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private void broadcastTaskUpdatedResult(Animal animal){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(BROADCAST_BACKGROUND_SERVICE_RESULT_UPDATE);
        broadcastIntent.putExtra(EXTRA_TASK_RESULT_ANIMAL_UPDATE, (Serializable) animal);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private static class getAllWordsTask extends AsyncTask<Long, String, List<Animal>> {

        private WeakReference<WordLearnerService> wordLearnerServiceWeakReference;

        public getAllWordsTask(WordLearnerService wordLearnerService)
        {
            wordLearnerServiceWeakReference = new WeakReference<>(wordLearnerService);
        }

        @Override
        protected List<Animal> doInBackground(Long... longs)
        {
            return ((AnimalApplication)_context).getAppDatabase().animalDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Animal> animals)
        {
            Log.d("HERE WE ADD ALL ANIMALS", "ANIMALS ADD");

            for (Animal ani : animals)
                Log.d("SOME ANIMAL NAME", ani.getName());

            animalList = animals;

            for (Animal broadcastAnimal : animals)
            {
                wordLearnerServiceWeakReference.get().broadcastTaskResult(broadcastAnimal);
            }


        }
    }

    private static class deleteWordTask extends AsyncTask<Animal, String, Animal> {

        private WeakReference<WordLearnerService> wordLearnerServiceWeakReference;

        public deleteWordTask(WordLearnerService wordLearnerService)
        {
            wordLearnerServiceWeakReference = new WeakReference<>(wordLearnerService);
        }

        @Override
        protected Animal doInBackground(Animal... animal) {

            try {
                ((AnimalApplication)_context).getAppDatabase().animalDao().deleteAnimals(animal[0]);
                Log.d("Deleted from database", animal[0].getName());
            }catch (Exception e)
            {
                Log.d("NO DELETE from database", animal[0].getName());
                e.printStackTrace();
            }

            return animal[0];
        }

        @Override
        protected void onPostExecute(Animal animal) {
            super.onPostExecute(animal);

            // So for some reason animal.removeList(obj) doesnt work? I thought at first it had to be a bug, but it doesnt seem so, C#, what is this function for?
            for (Iterator<Animal> iterator = animalList.iterator(); iterator.hasNext();)
            {
                if(iterator.next().getName().equals(animal.getName()))
                {
                    iterator.remove();
                }
            }

            Log.d("Sending to BC Delete" , animal.getName());
            wordLearnerServiceWeakReference.get().broadcastTaskDeletedResult(animal);

        }
    }


    private void sendRequest(String name)
    {
        final String token = "0bf65096df9c3d7f3d080f0543a8d13a9404759b";
        String url = "https://owlbot.info/api/v4/dictionary/" + name;

        Log.d("SOMEMESSAGE", "MESSAGE");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Model jsonModel = parseJson(response);

                Log.d("JSONMODEL ADDED?", jsonModel.getWord());

                final Animal animal = transformJsonObjectToAnimalbase(jsonModel);

                Log.d("ANIMALTRANSFORMED", animal.getDesc());

                AddAnimalToDatabase addAnimalToDatabase = new AddAnimalToDatabase(WordLearnerService.this);

                addAnimalToDatabase.execute(animal);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("WORDLISTERNER", "Error", error);
            }
        }) {

            //Found this on the internet, i lost the resourse though.
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("Authorization", "token " + token);
                return headers;
            }
        };



        queue.add(stringRequest);

    }

    //Inspired from lesson 6 demos
    private Model parseJson(String json)
    {

        Gson gson = new GsonBuilder().create();

        Model jsonModel = gson.fromJson(json, Model.class);

        return jsonModel;
    }


    private Animal transformJsonObjectToAnimalbase(Model jsonModel)
    {
        return new Animal(jsonModel);
    }



    private static class AddAnimalToDatabase extends AsyncTask<Animal, String, Animal>
    {

        private WeakReference<WordLearnerService> wordLearnerServiceWeakReference;

        public AddAnimalToDatabase(WordLearnerService wordLearnerService)
        {
            wordLearnerServiceWeakReference = new WeakReference<>(wordLearnerService);
        }


        @Override
        protected Animal doInBackground(Animal... animal) {

            ((AnimalApplication)_context).getAppDatabase().animalDao().insertAnimals(animal[0]);

            return animal[0];
        }

        @Override
        protected void onPostExecute(Animal animal) {
            super.onPostExecute(animal);
            animalList.add(animal);
            wordLearnerServiceWeakReference.get().broadcastTaskResult(animal);
        }
    }

    public static class UpdateWordTask extends AsyncTask<Animal, String, Animal>
    {
        private WeakReference<WordLearnerService> wordLearnerServiceWeakReference;

        public UpdateWordTask(WordLearnerService wordLearnerService)
        {
            wordLearnerServiceWeakReference = new WeakReference<>(wordLearnerService);
        }

        @Override
        protected Animal doInBackground(Animal... animal) {
            ((AnimalApplication)_context).getAppDatabase().animalDao().updateAnimals(animal[0]);
            return animal[0];
        }

        @Override
        protected void onPostExecute(Animal animal) {
            super.onPostExecute(animal);

            int getIndex = getIndexOfArrayList(animal);

            Log.d("Index of Animal: ", String.valueOf(getIndex));

            animalList.set(getIndex, animal);

            wordLearnerServiceWeakReference.get().broadcastTaskUpdatedResult(animal);

        }
    }

    public static int getIndexOfArrayList(Animal animal)
    {
        for ( Animal ani : animalList)
        {
            if(ani.getName().equals(animal.getName()))
            {
                return animalList.indexOf(ani);
            }
        }

        return -1;
    }


    //Notifications from here - All this from Lesson L5
    private void setupNotifications(){
        notificationManagerCompat = NotificationManagerCompat.from(this);

        setupChannel();

        Notification notification = setupNotificationsCombat("Learn Animal Words - au592332", "First Word appears after 60s here!");

        notificationManagerCompat.notify(NOTIFICATIONS_ID_INTEGER, notification);

        startForeground(NOTIFICATIONS_ID_INTEGER, notification);

    }


    private void setupChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { //needed because channels are not supported on older versions

            Log.d("IM IN SETUP" , "SETUP");

            NotificationChannel mChannel = new NotificationChannel(NOTIFICATIONS_ID,
                    NOTIFICATIONS_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    private Notification setupNotificationsCombat(String title, String text)
    {
        Log.d("IM IN SETCOMBAT", "COMBAT");
        return new NotificationCompat.Builder(this,
                NOTIFICATIONS_ID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher_dog_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    private void UpdateNotifications(){
        if (notificationsExecutor == null)
        {
            Log.d("Notificationswasnull", "notifications1");
            notificationsExecutor = Executors.newSingleThreadExecutor();
        }

        notificationsExecutor.submit(UpdateNotificationsThread);
    }

    private Runnable UpdateNotificationsThread = new Runnable() {
        @Override
        public void run() {
            Log.d("RUN CALLED", "RUNNING");
            try {
                Log.d("SLEEP FOR 60s", "Sleeping...");
                Thread.sleep(60000);

            }catch (Exception e){
                e.printStackTrace();
            }

            Notification notification;

            if(!animalList.isEmpty())
            {
                int randomNumberGenerator = new Random().nextInt(animalList.size());

                Animal randomAnimal = animalList.get(randomNumberGenerator);

                notification = setupNotificationsCombat("Learn Animal Words - au592332", randomAnimal.getName());

            }
            else
            {
                notification = setupNotificationsCombat("Learn Animal Words - au592332", "No Words in Stack");
            }

            notificationManagerCompat.notify(NOTIFICATIONS_ID_INTEGER, notification);

            if(isRunning)
            {
                UpdateNotifications();
            }
        }
    };


}
