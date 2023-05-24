package magrow.project.application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MyIntro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Plan Activities","Schedule your tasks with ease. Simply select the time and date of your activity",R.drawable.introcalendar, Color.parseColor("#008078")));
        addSlide(AppIntroFragment.newInstance("Manage Crops","Handling crops can be tricky. We're here to simplify it for you.",R.drawable.introcrop, Color.parseColor("#8787f5")));
        addSlide(AppIntroFragment.newInstance("Weather Forecast","We'll provide current and 12 hours to 5 days ahead weather details.",R.drawable.introweather, Color.parseColor("#ff9595")));
        addSlide(AppIntroFragment.newInstance("Sync Record","Want to know your progress? Synchronize your data to get results.",R.drawable.introsync, Color.parseColor("#2fccce")));

        showStatusBar(false);
        //setBarColor(Color.parseColor("#333639"));
        setSeparatorColor(Color.parseColor("#ffffff"));


    }



    @Override
    public void onDonePressed()
    {
        startActivity(new Intent(this,MainActivity.class));
        finish();

    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        //Message.message(getApplicationContext(),"On Skip Clicked");
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = getPrefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();

        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged()
    {
        //Message.message(getApplicationContext(),"On Slide change");
    }
}
