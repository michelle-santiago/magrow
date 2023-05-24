package magrow.project.application.fragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import magrow.project.application.MainActivity;
import magrow.project.application.Message;

import magrow.project.application.NetworkUtils12Hour;
import magrow.project.application.R;

import magrow.project.application.WeatherAdapter2;
import magrow.project.application.WeatherDetailsDialog;
import magrow.project.application.WeatherDetailsDialog2;
import magrow.project.application.WeatherModel2;

public class Hour12Weather extends Fragment {


    public Hour12Weather() {
        // Required empty public constructor
    }

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<WeatherModel2> weatherArrayList = new ArrayList<>();

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hour12_weather,
                container, false);

        listView = view.findViewById(R.id.idListView);

        URL weatherUrl = NetworkUtils12Hour.buildUrlForWeather();
        new Hour12Weather.FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                            /*Toast.makeText(MainActivity.this,

                                    "You have selected : " + myList.get(position),

                                    Toast.LENGTH_SHORT).show();*/

                WeatherModel2 dataModel = weatherArrayList.get(position);

                //Message.message(getContext(),dataModel.getDate());

                WeatherDetailsDialog2 dialogFragment = new WeatherDetailsDialog2();


                Bundle bundle = new Bundle();
                bundle.putString("weather_date",dataModel.getDate());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"WeatherDetailsDialog2");

                /*Bundle bundle = new Bundle();
                bundle.putString("weather_date",dataModel.getDate()); // set your parameteres
                Weather_details nextFragment = new Weather_details();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Seven");


                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();*/

                //overridePendingTransition(R.anim.mysplashanimation,R.anim.slide_in);



            }

        });

        return view;
    }

    private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {
        //final AlertDialog alertDialog= new SpotsDialog.Builder().setContext(getContext()).setMessage("Loading...").build();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //alertDialog.show();

        }

        @Override
        protected String doInBackground(URL... urls) {



            URL weatherUrl = urls[0];
            String weatherSearchResults = null;

            try {
                weatherSearchResults = NetworkUtils12Hour.getResponseFromHttpUrl(weatherUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "doInBackground: weatherSearchResults: " + weatherSearchResults);
            return weatherSearchResults;
        }



        @Override
        protected void onPostExecute(String weatherSearchResults) {


            if(weatherSearchResults != null && !weatherSearchResults.equals("")) {
                weatherArrayList = parseJSON(weatherSearchResults);
                //Just for testing
                Iterator itr = weatherArrayList.iterator();
                while(itr.hasNext()) {
                    WeatherModel2 weatherInIterator = (WeatherModel2) itr.next();
                    Log.i(TAG, "onPostExecute: Date: " + weatherInIterator.getDate()+
                            " Min: " + weatherInIterator.getTemp());
                }
            }
            //alertDialog.dismiss();
            super.onPostExecute(weatherSearchResults);
        }
    }





    private ArrayList<WeatherModel2> parseJSON(String weatherSearchResults) {
        if(weatherArrayList != null) {
            weatherArrayList.clear();
        }

        if(weatherSearchResults != null) {
            try {

                JSONArray jsonarray = new JSONArray(weatherSearchResults);

                for (int i = 0; i < jsonarray.length(); i++) {
                    WeatherModel2 weather = new WeatherModel2();
                    JSONObject resultsObj = jsonarray.getJSONObject(i);

                    String date_temp = resultsObj.getString("DateTime");
                    String date=getTime(date_temp);
                    //Message.message(getContext(),date);
                    String day=getDate(date_temp);
                    weather.setDate(day+" "+date);
                    WeatherDetailsDialog2.list1.add(day+" "+date);

                    JSONObject temperatureObj = resultsObj.getJSONObject("Temperature");
                    String minTemperature = temperatureObj.getString("Value");
                    weather.setTemp(minTemperature);
                    WeatherDetailsDialog2.list1.add(minTemperature);


                    //******************************DAY***********************************************
                    int dayIcon = resultsObj.getInt("WeatherIcon");
                    weather.setDayIcon(dayIcon);
                    WeatherDetailsDialog2.list1.add(dayIcon);

                    String iconPhrase = resultsObj.getString("IconPhrase");
                    weather.setIconPhrase(iconPhrase);
                    WeatherDetailsDialog2.list1.add(iconPhrase);

                    String rain = resultsObj.getString("RainProbability");
                    WeatherDetailsDialog2.list1.add(rain+" %");

                    String windNight = resultsObj.getJSONObject("Wind").getJSONObject("Speed").getString("Value");
                    WeatherDetailsDialog2.list1.add(windNight+" km/h");

                    String humidity = resultsObj.getString("RelativeHumidity");
                    WeatherDetailsDialog2.list1.add(humidity+" %");

                    String uv_value=resultsObj.getString("UVIndex");
                    String uv_sum=resultsObj.getString("UVIndexText");
                    WeatherDetailsDialog2.list1.add(uv_value+"\n"+uv_sum);

                   /* Log.i(TAG, "parseJSON: date: " + date + " " +
                            "Min: " + minTemperature + " " +
                            "Max: " + maxTemperature + " " +
                            "Link: " + link);*/

                    weatherArrayList.add(weather);
                }

                if(weatherArrayList != null) {
                    if(getContext()!=null)
                    {
                        WeatherAdapter2 weatherAdapter = new WeatherAdapter2(getContext(),weatherArrayList);
                        listView.setAdapter(weatherAdapter);
                    }

                }

                return weatherArrayList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getMonthName (int month){
        String monthName="";

        switch (month)
        {
            case 1:
                monthName="Jan";
                break;

            case 2:
                monthName="Feb";
                break;

            case 3:
                monthName="Mar";
                break;

            case 4:
                monthName="Apr";
                break;

            case 5:
                monthName="May";
                break;

            case 6:
                monthName="Jun";
                break;

            case 7:
                monthName="Jul";
                break;

            case 8:
                monthName="Aug";
                break;

            case 9:
                monthName="Sep";
                break;

            case 10:
                monthName="Oct";
                break;

            case 11:
                monthName="Nov";
                break;

            case 12:
                monthName="Dec";
                break;
            default:
                monthName="No Month";
                break;

        }

        return monthName;
    }

    public String getLocation(String loc_key){
        String a="";

        switch (loc_key)
        {
            case "265099":
                a="ALIAGA";
                break;

            case "265084":
                a="BONGABON";
                break;

            case "265082":
                a="CABANATUAN CITY";
                break;

            case "265085":
                a="CABIAO";
                break;

            case "265086":
                a="CARRANGLAN";
                break;

            case "265087":
                a="CUYAPO";
                break;

            case "265088":
                a="GABALDON";
                break;

            case "265079":
                a="GAPAN";
                break;

            case "265100":
                a="GENERAL NATIVIDAD";
                break;

            case "265089":
                a="GENERAL TINIO";
                break;

            case "265080":
                a="GUIMBA";
                break;

            case "265102":
                a="JAEN";
                break;

            case "265090":
                a="LAUR";
                break;

            case "265103":
                a="LICAB";
                break;

            case "265104":
                a="LLANERA";
                break;

            case "265091":
                a="LUPAO";
                break;

            case "265092":
                a="MUÑOZ";
                break;

            case "265105":
                a="NAMPICUAN";
                break;

            case "265083":
                a="PALAYAN";
                break;

            case "265106":
                a="PANTABANGAN";
                break;

            case "265093":
                a="PEÑARANDA";
                break;

            case "265107":
                a="QUEZON";
                break;

            case "265108":
                a="RIZAL";
                break;
            case "265094":
                a="SAN ANTONIO";
                break;

            case "265095":
                a="SAN ISIDRO";
                break;

            case "265081":
                a="SAN JOSE CITY";
                break;

            case "265096":
                a="SAN LEONARDO";
                break;

            case "265097":
                a="SANTA ROSA";
                break;

            case "265098":
                a="SANTO DOMINGO";
                break;

            case "265110":
                a="TALAVERA";
                break;

            case "3429768":
                a="TALUGTUG";
                break;

            case "265112":
                a="ZARAGOZA";
                break;


        }

        return a;

    }


    public String getDate(String date)
    {
        String output="";
        String[] date_temp=date.split("");
        for(int i=0;i<=date_temp.length;i++)
        {
            if(!date_temp[i].equals("T"))
            {
                output=output+date_temp[i];
            }
            else if(date_temp[i].equals("T"))
            {
                break;
            }
        }

        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(output, df);

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM dd");
        String expected_day=dtfOut.print(dateTime);


        return expected_day;
    }

    public String getTime(String date)
    {
        String output="";
        int flag=0;
        String[] time_temp=date.split("");
        for(int i=0;i<=time_temp.length;i++)
        {
            if(flag==0)
            {
                if(time_temp[i].equals("T"))
                {
                    flag=1;
                }
            }
            if(flag==1)
            {
                if(!time_temp[i].equals("+"))
                {
                    output=output+time_temp[i];
                }
                else
                {
                    break;
                }
            }


        }
        output=output.replace("T","");

        String outputf="";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm:ss");
            final Date dateObj = sdf.parse(output);
            SimpleDateFormat ff = new SimpleDateFormat("K:mm a");
            outputf=ff.format(dateObj);
        } catch (final ParseException e) {
            e.printStackTrace();
        }


        return outputf;
    }

}
