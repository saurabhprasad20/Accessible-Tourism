package ap.travelassist;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import org.json.JSONObject;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button RegisterButton,backButton;
    URLs URLObject = new URLs();
    String BASE_URL = URLObject.URL_REGISTER;
    private ProgressDialog pDialog;
    private SessionHandler session;
    AutoCompleteTextView UserName, UserPhone, UserP, UserC;
    RadioButton rMale, rFemale, rVo, rVi;
    String user_roll, user_gender,user_full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);
        UserName = findViewById(R.id.userName);
        UserPhone = findViewById(R.id.userNumber);
        UserP = findViewById(R.id.userPassword);
        UserC = findViewById(R.id.userCPassword);
        RegisterButton = findViewById(R.id.RegisterButton);
        backButton=findViewById(R.id.btn_register_back);
        rMale=findViewById(R.id.user_male);
        rFemale=findViewById(R.id.user_female);
        rVi=findViewById(R.id.user_vi);
        rVo=findViewById(R.id.user_vo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FirstActivity.class));
            }
        });

        rMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    user_gender="";
                    user_gender="male";
                    Toast.makeText(getApplicationContext(),"you have selected "+user_gender,Toast.LENGTH_SHORT).show();
                }
            }
        });

rFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            user_gender="";
            user_gender="female";
            Toast.makeText(getApplicationContext(),"you have selected "+user_gender,Toast.LENGTH_SHORT).show();
        }
    }
});

rVi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            user_roll="";
            user_roll="traveller";
            Toast.makeText(getApplicationContext(),"you have chosen to join as a VI user",Toast.LENGTH_SHORT).show();
        }
    }
});
rVo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            user_roll="";
            user_roll="volunteer";
            Toast.makeText(getApplicationContext(),"you have chosen to join as a volunteer",Toast.LENGTH_SHORT).show();
        }
    }
});

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((UserP.getText().toString()).equals(UserC.getText().toString())) AttemptRegisterUser();
                else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    protected void AttemptRegisterUser() {
        // Reset errors
        UserName.setError(null);
        UserPhone.setError(null);
        UserP.setError(null);

        // Store values at the time of the login attempt.
         String name = UserName.getText().toString();
         user_full_name=name;
        String phone = UserPhone.getText().toString();
        String password = UserP.getText().toString();
        Boolean cancel=false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            UserName.setError(getString(R.string.error_empty_field));
            focusView = UserName;
            cancel = true;
        }
        else if(TextUtils.isEmpty(phone)) {
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
            UserP.setError(getString(R.string.error_invalid_password));
            focusView = UserP;
            cancel = true;
        }
        if (!cancel) RegisterUser();
    }
    private boolean phone_invalid(String phone) {
        if(phone.length()!=10) return true;
        return false;
    }
    private boolean password_invalid(String password) {
        if(password.length()<6) return true;
        return false;
    }
    private void displayLoader() {
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    private void RegisterUser() {
        displayLoader();
        try {
            HashMap<String, String> param = new HashMap<>();
            param.put("name", UserName.getText().toString().trim());
            param.put("phone", UserPhone.getText().toString().trim());
            param.put("c_password", UserP.getText().toString().trim());
            param.put("password", UserC.getText().toString().trim());
            param.put("gender",user_gender);
            param.put("roll",user_roll);


            AQuery aq = new AQuery(this);
            aq.ajax(BASE_URL, param,  JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject json, AjaxStatus status) {
                    super.callback(url, json, status);


                    Log.v("CALLBACK RECEIVED" , String.valueOf(json) );
                    JSONObject jsonObject;
                    try {
                        if (json != null) {
                            jsonObject = json.getJSONObject("success");
                            String token = jsonObject.getString("token");
                            Log.v("SUCCESS", "TOKEN GENERATED");
                            pDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "You've been successfully registered!", Toast.LENGTH_LONG).show();
                            session.loginUser(UserPhone.getText().toString().trim(), token,user_full_name,user_gender,user_roll);

                            if (user_roll.equalsIgnoreCase("volunteer"))
                                startActivity(new Intent(getApplicationContext(),VolunteerActivity.class));
                            else
                            mainActivity();

                        } else {
                            pDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "You're already registered! Please Log In.", Toast.LENGTH_LONG).show();
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