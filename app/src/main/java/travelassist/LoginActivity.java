package ap.travelassist;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaRouter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private SessionHandler session;
    private ProgressDialog pDialog;
    Button LoginButton,backButton;
    AutoCompleteTextView UserPhone, UserPassword;
    String BASE_URL = new URLs().URL_LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_login);
        setTitle("TRAVEL ASSIST");
        LoginButton = findViewById(R.id.LoginButton);
        backButton=findViewById(R.id.btn_login_back);
        UserPhone = findViewById(R.id.userNumber);
        UserPassword = findViewById(R.id.loginPassword);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin();
            }
        });
backButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),FirstActivity.class));
    }
});
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    protected void AttemptLogin() {
        // Reset errors
        UserPhone.setError(null);
        UserPassword.setError(null);

        // Store values at the time of the login attempt.
        String phone = UserPhone.getText().toString();
        String password = UserPassword.getText().toString();
        Boolean cancel=false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(phone)) {
            UserPhone.setError(getString(R.string.error_empty_field));
            focusView = UserPhone;
            cancel = true;
        }
        else if(phone_invalid(phone)) {
            UserPhone.setError(getString(R.string.error_invalid_phone));
            focusView = UserPhone;
            cancel = true;
        }
        else if (!TextUtils.isEmpty(password)&&password_invalid(password)){
            UserPassword.setError(getString(R.string.error_invalid_password));
            focusView = UserPassword;
            cancel = true;
        }
        if (!cancel) LoginUser();
    }
    private boolean phone_invalid(String phone) {
        if(phone.length()!=10) return true;
        return false;
    }
    private boolean password_invalid(String password) {
        if(password.length()<6) return true;
        return false;
    }
    private void LoginUser() {
        displayLoader();
        try {
            final HashMap<String, String> param = new HashMap<>();
            param.put("phone", UserPhone.getText().toString().trim());
            param.put("password", UserPassword.getText().toString().trim());

            AQuery aq = new AQuery(this);

            aq.ajax(BASE_URL, param,  JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    super.callback(url, json, status);

                    Log.v("CALLBACK RECEIVED" , String.valueOf(json) + status);
//User user=new User();
//user=session.getUserDetails();

                    try {
                        if (json != null) {
                            pDialog.dismiss();
                            JSONObject h = json.getJSONObject("success");

                            String token = h.getString("token");

                            Log.v("SUCCESS" , "TOKEN RECIEVED");
                            session.loginUser(UserPhone.getText().toString().trim(), token);


                            Toast.makeText(LoginActivity.this, "You've been successfully logged in!", Toast.LENGTH_LONG).show();
                            //user=session.getUserDetails();
                            //if (user.roll.equalsIgnoreCase("volunteer"))
                            //startActivity(new Intent(getApplicationContext(),VolunteerActivity.class));
                            //else
                            mainActivity();
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                            Log.v("ERROR", "ERROR");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }.method(AQuery.METHOD_POST));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
