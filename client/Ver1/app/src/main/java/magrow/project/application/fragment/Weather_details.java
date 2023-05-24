package magrow.project.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import magrow.project.application.MainActivity;
import magrow.project.application.R;

import java.util.ArrayList;

public class Weather_details extends Fragment {

    TextView show;


    public static ArrayList list1 = new ArrayList();


    public Weather_details() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_weather_details,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle("Weather_detailss");


        show = (TextView)view.findViewById(R.id.show);
        String date_id= this.getArguments().getString("weather_date");


        int start=list1.indexOf(date_id);
        String str="";

        for(int i=start;i<=start+10;i++)
        {
            str=str+list1.get(i)+"\n";
        }

        show.setText(str);



        return view;
    }

}
