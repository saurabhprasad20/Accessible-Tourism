package ap.travelassist;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_TOKEN ="token";
    private static final String KEY_GENDER="gender";
    private static final String KEY_ROLL="roll";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     */
    public void loginUser(String phone, String token,String full_name,String  gender, String roll) {
        mEditor.putString(KEY_PHONE, phone);
        mEditor.putString(KEY_TOKEN, token);
        mEditor.putString(KEY_FULL_NAME,full_name);
        mEditor.putString(KEY_GENDER,gender);
        mEditor.putString(KEY_ROLL,roll);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public void loginUser(String phone, String token) {
        mEditor.putString(KEY_PHONE, phone);
        mEditor.putString(KEY_TOKEN, token);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }



    /**
     * Checks whether user is logged in
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();
        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     */
    public User getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        User user = new User();
        user.setPhone(mPreferences.getString(KEY_PHONE, KEY_EMPTY));
        user.setFullName(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        user.setToken(mPreferences.getString(KEY_TOKEN, KEY_EMPTY));
        user.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));
        user.setGender(mPreferences.getString(KEY_GENDER,KEY_EMPTY));
        user.setRoll(mPreferences.getString(KEY_ROLL,KEY_EMPTY));
        return user;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }
}