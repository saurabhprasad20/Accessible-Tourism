package ap.travelassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class FirstActivity extends AppCompatActivity {
        SessionHandler sessionHandler;
        Button LButton, RButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionHandler = new SessionHandler(getApplicationContext());
        if(sessionHandler.isLoggedIn()) {
            loadMainActivity();
        }
        setContentView(R.layout.activity_first);
        LButton = (Button) findViewById(R.id.LButton);
        RButton = (Button) findViewById(R.id.RButton);
        LButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1= new Intent(FirstActivity.this,LoginActivity.class);
                startActivity(i1);
            }
        });
        RButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(FirstActivity.this,RegisterActivity.class);
                startActivity(i2);
            }
        });
    }
    public void loadMainActivity() {
        Intent intent = new Intent(FirstActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
