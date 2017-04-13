package mobile.maps.itsbusdriver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "session";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_ACTIVE = "IsActive";
    public static final String KEY_USER = "key";

    public SessionManager(Context context_) {
        this.context = context_;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String key) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER, key);
        editor.commit();
    }

    public void logout() {
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void turnOn() {
        editor.putBoolean(IS_ACTIVE, true);
        editor.commit();
    }

    public void turnOff() {
        editor.putBoolean(IS_ACTIVE, false);
        editor.commit();
    }

    public boolean isActive() {
        return sharedPreferences.getBoolean(IS_ACTIVE, false);
    }
}