package thomas.berthelsen.AssignmentOne;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import thomas.berthelsen.AssignmentOne.DataAccess.Animal;
import thomas.berthelsen.AssignmentOne.DataAccess.AnimalApplication;
import thomas.berthelsen.AssignmentOne.HttpService.Definition;
import thomas.berthelsen.AssignmentOne.HttpService.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WordLearnerService extends Service {

    public static final String BROADCAST_BACKGROUND_SERVICE_RESULT = "BACKGROUND_SERVICE_RESULT_SENT";
    public static final String EXTRA_TASK_RESULT_ANIMAL = "TASK_RESULT_ANIMAL";

    private List<Animal> animalList;
    private boolean running = false;
    RequestQueue queue;

    public class WordLearnerServiceBinder extends Binder {
        WordLearnerService getService() {return WordLearnerService.this;}
    }

    private final IBinder binder = new WordLearnerServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("ONCREATE", "SERVICE");

        if(queue==null)
        {
            queue = Volley.newRequestQueue(this);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ONSTARTCOMMAND", "ONSTARTCOMMAND");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getAllWords()
    {
        new getAllWordsTask().execute();
    };

    public void addWord(String w)
    {

    }

    public Animal getWord(String w)
    {
        String owl = "owl";

        sendRequest(owl);

        return null;
    }

    public void deleteWord(Animal w)
    {
        for (Animal animal : animalList)
        {
            if(animal.equals(w))
                animalList.remove(animal);
        }
    }

    public void updateWord(AnimalComplete w)
    {

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
        broadcastIntent.putExtra(EXTRA_TASK_RESULT_ANIMAL, (Serializable) animal); //Maybe this needs fixing
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    private static class getAllWordsTask extends AsyncTask<Long, String, String> {

        @Override
        protected String doInBackground(Long... longs)
        {
            return null;
        }

        @Override
        protected void onPostExecute(String stringResult)
        {

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

                Log.d("JSONMODEL ADDED?", jsonModel.getPronunciation());

                Animal animal = transformJsonObjectToAnimalbase(jsonModel);

                Log.d("ANIMALTRANSFORMED", animal.getDesc());

                saveAnimalToDatabase(animal);

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
        Log.d("IN PARSE JSON", "MESSAGE FROM JSONPARSER");
        Log.d("JSON!", json);
        Gson gson = new GsonBuilder().create();

        Model jsonModel = gson.fromJson(json, Model.class);

        return jsonModel;
    }

    private void saveAnimalToDatabase(Animal animal)
    {
        ((AnimalApplication)getApplicationContext()).getAppDatabase().animalDao().insertAnimals(animal);
    }

    private Animal transformJsonObjectToAnimalbase(Model jsonModel)
    {
        return new Animal(jsonModel);
    }




}
