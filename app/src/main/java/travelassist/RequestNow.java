package ap.travelassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class RequestNow extends AppCompatActivity {
    Spinner sp_time, sp_payment;
    Button view_volunteers,navigate_up;
ArrayAdapter<String> arrayAdapter1;
ArrayAdapter<String> arrayAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_now);
sp_time=findViewById(R.id.spinner_sp_time);
sp_payment=findViewById(R.id.spinner_sp_payment);
view_volunteers=findViewById(R.id.btn_requestnow_confirm);
navigate_up=findViewById(R.id.btn_requestnow_navigateup);
navigate_up.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
});
arrayAdapter1=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.array_time_duration)){
    @Override
    public boolean isEnabled(int position) {
if (position==0)
    return false;
else
    return true;
    }
};
sp_time.setAdapter(arrayAdapter1);
sp_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position>0)
            Toast.makeText(getApplicationContext(),"you have selected "+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
arrayAdapter2=new ArrayAdapter<String >(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,getResources().getStringArray(R.array.array_payment)){
    @Override
    public boolean isEnabled(int position) {
if (position==0)
    return  false;
else
    return  true;
    }
};
sp_payment.setAdapter(arrayAdapter2);
sp_payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position>0)
            Toast.makeText(getApplicationContext(),"you have selected "+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
view_volunteers.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Toast.makeText(getApplicationContext(),"please wait until a volunteer accepts your request.",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),ConfirmationPageActivity.class));


        //Toast.makeText(getApplicationContext(),"not implemented yet",Toast.LENGTH_SHORT).show();
    }
});

    }
}