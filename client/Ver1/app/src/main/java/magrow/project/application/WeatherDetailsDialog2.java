package magrow.project.application;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherDetailsDialog2 extends DialogFragment {

    TextView date,temperature,summary,rain,wind,humidity,uv;
    ImageView icon;

    public static ArrayList list1 = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_weather_details_dialog2, container, false);

        date = (TextView)view.findViewById(R.id.date);
        temperature = (TextView)view.findViewById(R.id.temperature);
        summary = (TextView)view.findViewById(R.id.summary);
        rain = (TextView)view.findViewById(R.id.rain);
        wind = (TextView)view.findViewById(R.id.wind);
        humidity = (TextView)view.findViewById(R.id.humidity);
        uv = (TextView)view.findViewById(R.id.uv);

        icon = (ImageView)view.findViewById(R.id.icon);

        Bundle bundle = getArguments();
        String date_id = bundle.getString("weather_date");

        int start=list1.indexOf(date_id);
        String str="";

        for(int i=start;i<=start+7;i++)
        {
            str=str+list1.get(i)+"\n";
        }

        String date_weather=list1.get(start).toString();
        String temperaturef=list1.get(start+1).toString();
        String iconf=list1.get(start+2).toString();
        String summaryf=list1.get(start+3).toString();
        String rainf=list1.get(start+4).toString();
        String windf=list1.get(start+5).toString();
        String humidityf=list1.get(start+6).toString();
        String uvf=list1.get(start+7).toString();

        String[] date_temp=date_weather.split(" ");
        date.setText(date_temp[0]+" "+date_temp[1]+"\n"+date_temp[2]+" "+date_temp[3]);
        temperature.setText(temperaturef+" °C");
        summary.setText(summaryf);
        rain.setText(rainf);
        wind.setText(windf);
        humidity.setText(humidityf);
        uv.setText(uvf);


        switch (iconf) {
            case "1":
                icon.setImageResource(R.drawable.a1);
                break;
            case "2":
                icon.setImageResource(R.drawable.a2);
                break;
            case "3":
                icon.setImageResource(R.drawable.a3);
                break;
            case "4":
                icon.setImageResource(R.drawable.a4);
                break;
            case "5":
                icon.setImageResource(R.drawable.a5);
                break;
            case "6":
                icon.setImageResource(R.drawable.a6);
                break;
            case "7":
                icon.setImageResource(R.drawable.a7);
                break;
            case "8":
                icon.setImageResource(R.drawable.a8);
                break;
            case "11":
                icon.setImageResource(R.drawable.a11);
                break;
            case "12":
                icon.setImageResource(R.drawable.a12);
                break;
            case "13":
                icon.setImageResource(R.drawable.a13);
                break;
            case "14":
                icon.setImageResource(R.drawable.a14);
                break;
            case "15":
                icon.setImageResource(R.drawable.a15);
                break;
            case "16":
                icon.setImageResource(R.drawable.a16);
                break;
            case "17":
                icon.setImageResource(R.drawable.a17);
                break;
            case "18":
                icon.setImageResource(R.drawable.a18);
                break;
            case "32":
                icon.setImageResource(R.drawable.a32);
                break;
            case "33":
                icon.setImageResource(R.drawable.a33);
                break;
            case "34":
                icon.setImageResource(R.drawable.a34);
                break;
            case "35":
                icon.setImageResource(R.drawable.a35);
                break;
            case "36":
                icon.setImageResource(R.drawable.a36);
                break;
            case "37":
                icon.setImageResource(R.drawable.a37);
                break;
            case "38":
                icon.setImageResource(R.drawable.a38);
                break;
            case "39":
                icon.setImageResource(R.drawable.a39);
                break;
            case "40":
                icon.setImageResource(R.drawable.a40);
                break;
            case "41":
                icon.setImageResource(R.drawable.a41);
                break;
            case "42":
                icon.setImageResource(R.drawable.a42);
                break;
        }
        
        //Message.message(getContext(),str);

        return view;
    }
}
