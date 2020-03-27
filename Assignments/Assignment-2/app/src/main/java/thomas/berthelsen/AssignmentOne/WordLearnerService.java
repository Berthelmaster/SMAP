package thomas.berthelsen.AssignmentOne;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import thomas.berthelsen.AssignmentOne.DataAccess.Animal;
import thomas.berthelsen.AssignmentOne.DataAccess.AnimalApplication;

public class WordLearnerService extends Service {

    public static final String BROADCAST_BACKGROUND_SERVICE_RESULT = "BACKGROUND_SERVICE_RESULT_SENT";
    public static final String EXTRA_TASK_RESULT_ANIMAL = "TASK_RESULT_ANIMAL";

    private List<Animal> animalList;
    private boolean running = false;





    public void getAllWords()
    {
        new getAllWordsTask().execute();
    };

    public void addWord(String w)
    {

    }

    public Animal getWord(String w)
    {
        for (Animal animal : animalList)
        {
            if(animal.getName().equals(w))
                return animal;
        }

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
        return null;
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


}
