package magrow.project.application;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import magrow.project.application.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherDetailsDialog extends DialogFragment {

    TextView date,min_temp,max_temp,am_summary,am_rain,am_wind,pm_summary,pm_rain,pm_wind;
    ImageView dayIcon,nightIcon;

    private static final String TAG = "WeatherDetailsDialog";

    public static ArrayList list1 = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_weather_details, container, false);

        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        date = (TextView)view.findViewById(R.id.date);

        min_temp = (TextView)view.findViewById(R.id.min_temp);
        max_temp = (TextView)view.findViewById(R.id.max_temp);

        am_summary = (TextView)view.findViewById(R.id.am_summary);
        am_rain = (TextView)view.findViewById(R.id.am_rain);
        am_wind = (TextView)view.findViewById(R.id.am_wind);

        pm_summary = (TextView)view.findViewById(R.id.pm_summary);
        pm_rain = (TextView)view.findViewById(R.id.pm_rain);
        pm_wind = (TextView)view.findViewById(R.id.pm_wind);
        
        dayIcon = (ImageView)view.findViewById(R.id.dayIcon);
        nightIcon = (ImageView)view.findViewById(R.id.nightIcon);

        Bundle bundle = getArguments();
        String date_id = bundle.getString("weather_date");

        //Message.message(getContext(),imageLink);

        int start=list1.indexOf(date_id);
        String str="";

        for(int i=start;i<=start+10;i++)
        {
            str=str+list1.get(i)+"\n";
        }

        String date_weather=list1.get(start).toString();
        String min_temp1=list1.get(start+1).toString();
        String max_temp1=list1.get(start+2).toString();

        String am_icon1=list1.get(start+3).toString();
        String am_summary1=list1.get(start+4).toString();
        String am_rain1=list1.get(start+5).toString();
        String am_wind1=list1.get(start+6).toString();

        String pm_icon1=list1.get(start+7).toString();
        String pm_summary1=list1.get(start+8).toString();
        String pm_rain1=list1.get(start+9).toString();
        String pm_wind1=list1.get(start+10).toString();

       // Message.message(getContext(),date_weather+"\n"+min_temp+"\n"+max_temp+"\n"+am_icon+"\n"+am_summary+"\n"+am_rain+"\n"+am_wind+"\n"+pm_icon+"\n"+pm_summary+"\n"+pm_rain+"\n"+pm_wind);


        date.setText(date_weather+" "+year);
        min_temp.setText(min_temp1);
        max_temp.setText(max_temp1);

        am_summary.setText(am_summary1);
        am_rain.setText(am_rain1);
        am_wind.setText(am_wind1);

        pm_summary.setText(pm_summary1);
        pm_rain.setText(pm_rain1);
        pm_wind.setText(pm_wind1);

        switch (am_icon1) {
            case "1":
                dayIcon.setImageResource(R.drawable.a1);
                break;
            case "2":
                dayIcon.setImageResource(R.drawable.a2);
                break;
            case "3":
                dayIcon.setImageResource(R.drawable.a3);
                break;
            case "4":
                dayIcon.setImageResource(R.drawable.a4);
                break;
            case "5":
                dayIcon.setImageResource(R.drawable.a5);
                break;
            case "6":
                dayIcon.setImageResource(R.drawable.a6);
                break;
            case "7":
                dayIcon.setImageResource(R.drawable.a7);
                break;
            case "8":
                dayIcon.setImageResource(R.drawable.a8);
                break;
            case "11":
                dayIcon.setImageResource(R.drawable.a11);
                break;
            case "12":
                dayIcon.setImageResource(R.drawable.a12);
                break;
            case "13":
                dayIcon.setImageResource(R.drawable.a13);
                break;
            case "14":
                dayIcon.setImageResource(R.drawable.a14);
                break;
            case "15":
                dayIcon.setImageResource(R.drawable.a15);
                break;
            case "16":
                dayIcon.setImageResource(R.drawable.a16);
                break;
            case "17":
                dayIcon.setImageResource(R.drawable.a17);
                break;
            case "18":
                dayIcon.setImageResource(R.drawable.a18);
                break;
            case "32":
                dayIcon.setImageResource(R.drawable.a32);
                break;
            case "33":
                dayIcon.setImageResource(R.drawable.a33);
                break;
            case "34":
                dayIcon.setImageResource(R.drawable.a34);
                break;
            case "35":
                dayIcon.setImageResource(R.drawable.a35);
                break;
            case "36":
                dayIcon.setImageResource(R.drawable.a36);
                break;
            case "37":
                dayIcon.setImageResource(R.drawable.a37);
                break;
            case "38":
                dayIcon.setImageResource(R.drawable.a38);
                break;
            case "39":
                dayIcon.setImageResource(R.drawable.a39);
                break;
            case "40":
                dayIcon.setImageResource(R.drawable.a40);
                break;
            case "41":
                dayIcon.setImageResource(R.drawable.a41);
                break;
            case "42":
                dayIcon.setImageResource(R.drawable.a42);
                break;
        }

        switch (pm_icon1) {
            case "1":
                nightIcon.setImageResource(R.drawable.a1);
                break;
            case "2":
                nightIcon.setImageResource(R.drawable.a2);
                break;
            case "3":
                nightIcon.setImageResource(R.drawable.a3);
                break;
            case "4":
                nightIcon.setImageResource(R.drawable.a4);
                break;
            case "5":
                nightIcon.setImageResource(R.drawable.a5);
                break;
            case "6":
                nightIcon.setImageResource(R.drawable.a6);
                break;
            case "7":
                nightIcon.setImageResource(R.drawable.a7);
                break;
            case "8":
                nightIcon.setImageResource(R.drawable.a8);
                break;
            case "11":
                nightIcon.setImageResource(R.drawable.a11);
                break;
            case "12":
                nightIcon.setImageResource(R.drawable.a12);
                break;
            case "13":
                nightIcon.setImageResource(R.drawable.a13);
                break;
            case "14":
                nightIcon.setImageResource(R.drawable.a14);
                break;
            case "15":
                nightIcon.setImageResource(R.drawable.a15);
                break;
            case "16":
                nightIcon.setImageResource(R.drawable.a16);
                break;
            case "17":
                nightIcon.setImageResource(R.drawable.a17);
                break;
            case "18":
                nightIcon.setImageResource(R.drawable.a18);
                break;
            case "32":
                nightIcon.setImageResource(R.drawable.a32);
                break;
            case "33":
                nightIcon.setImageResource(R.drawable.a33);
                break;
            case "34":
                nightIcon.setImageResource(R.drawable.a34);
                break;
            case "35":
                nightIcon.setImageResource(R.drawable.a35);
                break;
            case "36":
                nightIcon.setImageResource(R.drawable.a36);
                break;
            case "37":
                nightIcon.setImageResource(R.drawable.a37);
                break;
            case "38":
                nightIcon.setImageResource(R.drawable.a38);
                break;
            case "39":
                nightIcon.setImageResource(R.drawable.a39);
                break;
            case "40":
                nightIcon.setImageResource(R.drawable.a40);
                break;
            case "41":
                nightIcon.setImageResource(R.drawable.a41);
                break;
            case "42":
                nightIcon.setImageResource(R.drawable.a42);
                break;
        }
        //show.setText(str);

        return view;
    }
    
    

}
