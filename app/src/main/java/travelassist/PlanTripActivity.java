package ap.travelassist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PlanTripActivity extends AppCompatActivity {
    int fyear,fmonth,fday,tyear,tmonth,tday,hour1,minute1;
Calendar calendar1,calendar2;
DatePickerDialog datePickerDialog1,datePickerDialog2;
Button SubmitButton, availableFrom, availableTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);
        availableFrom = (Button) findViewById(R.id.AvailableFrom);
        availableTo = (Button) findViewById(R.id.AvailableTo);
        SubmitButton = findViewById(R.id.SubmitEquipment);
        availableFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar1 = Calendar.getInstance();
                fyear = calendar1.get(Calendar.YEAR);
                fmonth = calendar1.get(Calendar.MONTH);
                fday = calendar1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog1 = new DatePickerDialog(PlanTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                availableFrom.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, 2022, 0, 0);
                datePickerDialog1.show();
            }
        });
        availableTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar2 = Calendar.getInstance();
                tyear = calendar2.get(Calendar.YEAR);
                tmonth = calendar2.get(Calendar.MONTH);
                tday = calendar2.get(Calendar.DAY_OF_MONTH);
                datePickerDialog2 = new DatePickerDialog(PlanTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                availableTo.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, 2022, 0, 0);
                datePickerDialog2.show();
            }
        });
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"your request has been recorded. We'll notify you once a volunteer accepts it.",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });





    }
}