package magrow.project.application;



import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import magrow.project.application.R;

import magrow.project.application.fragment.Calendar_pane;
import magrow.project.application.fragment.Crops;
import magrow.project.application.fragment.Harvest_Record;
import magrow.project.application.fragment.Home;
import magrow.project.application.fragment.Logged_in;
import magrow.project.application.fragment.More;
import magrow.project.application.fragment.Others;
import magrow.project.application.fragment.Register;
import magrow.project.application.fragment.Weather;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    BottomNavigationView bottomNavigationView;
    boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart",true);
                if(isFirstStart)
                {
                    startActivity(new Intent(MainActivity.this,MyIntro.class));
                    finish();
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart",false);
                    e.apply();
                }


            }
        });

        thread.start();

}


    Calendar_pane calendarFragment = new Calendar_pane();
    Crops cropsFragment = new Crops();
    Home homeFragment = new Home();
    Weather weatherFragment = new Weather();
    Others othersFragment = new Others();
    Logged_in logged_inFragment = new Logged_in();
    Register registerFragment = new Register();
    Harvest_Record harvestRecord = new Harvest_Record();
    More more = new More();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, calendarFragment).commit();

                return true;

            case R.id.nav_crop:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, cropsFragment).commit();


                return true;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, homeFragment).commit();

                return true;

           /* case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, weatherFragment).commit();

                return true;*/

            case R.id.nav_others:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, logged_inFragment).commit();
               /* PopupMenu popup = new PopupMenu(MainActivity.this,findViewById(R.id.nav_others));

                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item2) {
                        switch (item2.getItemId()) {
                            case R.id.account:
                                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, logged_inFragment).commit();
                                return true;

                            case R.id.harvest_record:
                                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, harvestRecord).commit();
                                return true;

                        }
                        return false;
                    }
                });*/
                return true;

            case R.id.nav_archive:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, more).commit();

                return true;


        }

        return false;
    }

    public void SetNavigationVisibiltity (boolean b) {
        if (b) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void showUpButton() { getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
    public void hideUpButton() { getSupportActionBar().setDisplayHomeAsUpEnabled(false); }



}

