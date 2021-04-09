package ap.travelassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendFeedbackActivity extends AppCompatActivity {
Button navigate,send;
EditText ed_feedback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        setTitle("FEEDBACK");
        ed_feedback=findViewById(R.id.edit_send_feedback);
        navigate=findViewById(R.id.btn_send_feedback_navigateup);
        send=findViewById(R.id.btn_send_feedback_send);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        ed_feedback.setClickable(true);
        ed_feedback.setSoundEffectsEnabled(true);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"We have received your Feedback.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}