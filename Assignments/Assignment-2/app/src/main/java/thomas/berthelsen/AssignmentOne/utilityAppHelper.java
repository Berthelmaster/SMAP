package thomas.berthelsen.AssignmentOne;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


// Found on https://stackoverflow.com/questions/7217578/check-if-application-is-on-its-first-run
public class utilityAppHelper {

    public static void setHasAppRun(Context context, String key, boolean value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getHasAppRun(Context context, String key)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

    public static void setIsServiceRunning(Context context, String key, boolean value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getIsServiceRunning(Context context, String key)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, false);
    }

}
