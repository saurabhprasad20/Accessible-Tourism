package ap.travelassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileView extends AppCompatActivity {
SessionHandler session;
User user;
ArrayAdapter<String> adapter;
ListView lv1;
TextView tv_name,tv_gender,tv_roll,tv_phone;
Button btn_navigateUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Profile Details");
        setContentView(R.layout.activity_profile_view);
        //lv1=findViewById(R.id.lv_profile_view);
        tv_name=findViewById(R.id.tv_profile_name);
        tv_gender=findViewById(R.id.tv_profile_gender);
        tv_roll=findViewById(R.id.tv_profile_roll);
        tv_phone=findViewById(R.id.tv_profile_phone);
        btn_navigateUp=findViewById(R.id.btn_profile_view_navigateUp);
        session=new SessionHandler(getApplicationContext());
        user=session.getUserDetails();
/*String[] details={
        user.fullName,
                user.gender,
            user.roll,
            user.phone
        };
*/
//ArrayList<String> details=new ArrayList<String>();
//details.add(user.fullName);
//details.add(user.gender);
//details.add(user.roll);
//details.add(user.phone);
//adapter=new ArrayAdapter<String>(this,R.layout.list_profile_details,details);

//lv1.setAdapter(adapter);
        btn_navigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

tv_name.setText("Name: "+user.fullName);
tv_gender.setText("Gender: "+user.gender);
tv_roll.setText("Roll: "+user.roll);
tv_phone.setText("Phone: "+user.phone);
//adapter.add(user.fullName);
//adapter.add(user.gender);
//adapter.add(user.roll);
//adapter.add(user.phone);
//        lv1.setAdapter(adapter);


    }
}