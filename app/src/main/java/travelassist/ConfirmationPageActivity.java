package ap.travelassist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmationPageActivity extends AppCompatActivity {
Button btn_call, btn_cancel;
TextView tv;
String pName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        btn_call=findViewById(R.id.btn_custom_activity_call);
btn_cancel=findViewById(R.id.btn_custom_activity_cancell);
tv=findViewById(R.id.tv_custom_activity_90);

tv.setText("Please wait, soon a volunteer will accept your request. ");
btn_cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"your trip has been cancelled!",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
});

btn_call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"alert calling",Toast.LENGTH_SHORT).show();
        Intent callIntent=new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+"9760599211"));
        startActivity(callIntent);
    }
});

    }
}