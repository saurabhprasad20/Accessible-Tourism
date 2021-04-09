package ap.travelassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button request_now, plan_trip;
    TextView tv_wellcome_name;
    SessionHandler sessionHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionHandler = new SessionHandler(getApplicationContext());
        setTitle("TRAVEL ASSIST");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        request_now=findViewById(R.id.btn_main_request);
        plan_trip=findViewById(R.id.btn_main_plan_trip);
        User user=new User();
        user=sessionHandler.getUserDetails();
        tv_wellcome_name=findViewById(R.id.text_main_top);
        tv_wellcome_name.setText("Hi "+user.fullName+"! You can now request for volunteer or plan your trip.");

request_now.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),RequestNow.class);
        startActivity(intent);
    }
});
        plan_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please provide the details",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),PlanTripActivity.class);
                startActivity(intent);
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addEquipmentButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = (Intent) new Intent(MainActivity.this,AddEquipment.class);
                startActivity(intent);
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(sessionHandler.isLoggedIn()) {
                finishAffinity();
                finish();
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            sessionHandler.logoutUser();
            finish();
            loadFirstActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {


        } else if (id == R.id.nav_gallery) {
startActivity(new Intent(getApplicationContext(),ProfileView.class));
        }
         else if (id == R.id.nav_send) {
startActivity(new Intent(getApplicationContext(),SendFeedbackActivity.class));
        }
else if (id==R.id.nav_share){
            startActivity(new Intent(getApplicationContext(),AboutActivity.class));
        }
else if (id==R.id.nav_faq){
Intent intentInvite=new Intent(Intent.ACTION_SEND);
intentInvite.setType("text/plain");
        String link="https://travelAssist";
String subject="TRAVEL ASSIST";
        intentInvite.putExtra(Intent.EXTRA_TEXT,link);
        intentInvite.putExtra(Intent.EXTRA_SUBJECT,subject);
startActivity(Intent.createChooser(intentInvite,"Share Using"));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadFirstActivity() {
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(intent);
    }

}

