package tsugumi.seii.bankai.advisoryapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoginSharedPreference {
    private static final String EMAIL_PREF = "EMAIL_PREF";
    private static final String PASSWORD_PREF = "PASSWORD_PREF";

    static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("Login", Context.MODE_PRIVATE);
    }


    public static void persistLoginSession(Context context, String email, String password) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(EMAIL_PREF,email);
        editor.putString(PASSWORD_PREF,password);
        editor.apply();
    }

    public static void endLoginSession(Context context){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

    public static boolean loginSessionIsOngoing(Context context) {
        return !getPreferences(context).getString(PASSWORD_PREF,"").equals("");
    }

    public static String getEmail(Context context) {
        return getPreferences(context).getString(EMAIL_PREF,"");
    }

    public static String getPassword(Context context) {
        return getPreferences(context).getString(PASSWORD_PREF,"");
    }
}
