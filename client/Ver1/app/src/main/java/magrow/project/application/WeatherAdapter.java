package magrow.project.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class WeatherAdapter extends ArrayAdapter<WeatherModel> {
    public WeatherAdapter(@NonNull Context context, ArrayList<WeatherModel> weatherArrayList) {
        super(context, 0, weatherArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WeatherModel weather = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView dateTextView = convertView.findViewById(R.id.tvDate);
        TextView minTextView = convertView.findViewById(R.id.tvLowTemperature);
        TextView maxTextView = convertView.findViewById(R.id.tvHighTemperature);
        ImageView dayIcon = convertView.findViewById(R.id.dayIcon);
        TextView iconPhrase = convertView.findViewById(R.id.iconPhrase);


        dateTextView.setText(weather.getDate());
        minTextView.setText("Min :\t"+weather.getMinTemp()+" °C");
        maxTextView.setText("Max :\t"+weather.getMaxTemp()+" °C");

        String a=String.valueOf(weather.getDayIcon());


        switch (a) {
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
        iconPhrase.setText(weather.getIconPhrase());


        return convertView;

    }
}
