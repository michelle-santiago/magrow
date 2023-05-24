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
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import magrow.project.application.MainActivity;
import magrow.project.application.Message;
import magrow.project.application.NetworkUtils;
import magrow.project.application.R;
import magrow.project.application.WeatherAdapter;
import magrow.project.application.WeatherDetailsDialog;
import magrow.project.application.WeatherModel;

public class Day5Weather extends Fragment {


    public Day5Weather() {
        // Required empty public constructor
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<WeatherModel> weatherArrayList = new ArrayList<>();

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day5_weather,
                container, false);

        listView = view.findViewById(R.id.idListView);

        URL weatherUrl = NetworkUtils.buildUrlForWeather();
        new Day5Weather.FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                            /*Toast.makeText(MainActivity.this,

                                    "You have selected : " + myList.get(position),

                                    Toast.LENGTH_SHORT).show();*/

                WeatherModel dataModel = weatherArrayList.get(position);

                //Message.message(getContext(),dataModel.getDate());

                WeatherDetailsDialog dialogFragment = new WeatherDetailsDialog();


                Bundle bundle = new Bundle();
                bundle.putString("weather_date",dataModel.getDate());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"WeatherDetailsDialog");

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
                weatherSearchResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
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
                    WeatherModel weatherInIterator = (WeatherModel) itr.next();
                    Log.i(TAG, "onPostExecute: Date: " + weatherInIterator.getDate()+
                            " Min: " + weatherInIterator.getMinTemp() +
                            " Max: " + weatherInIterator.getMaxTemp());
                }
            }
            //alertDialog.dismiss();

            super.onPostExecute(weatherSearchResults);
        }
    }





    private ArrayList<WeatherModel> parseJSON(String weatherSearchResults) {
        if(weatherArrayList != null) {
            weatherArrayList.clear();
        }

        if(weatherSearchResults != null) {
            try {
                JSONObject rootObject = new JSONObject(weatherSearchResults);
                JSONArray results = rootObject.getJSONArray("DailyForecasts");

                for (int i = 0; i < results.length(); i++) {
                    WeatherModel weather = new WeatherModel();
                    JSONObject resultsObj = results.getJSONObject(i);

                    String date = resultsObj.getString("Date");

                    try{
                        DateTime d = DateTime.parse(date);
                        String output = ISODateTimeFormat.date().print(d);
                        String[] output2 = output.split("-");
                        int month=Integer.parseInt(output2[1]);
                        String dateFinal =output2[2]+" "+getMonthName(month);

                        weather.setDate(dateFinal);
                        WeatherDetailsDialog.list1.add(dateFinal);

                    }
                    catch (Exception e)
                    {
                        Message.message(getContext(),e.getMessage());
                        weather.setDate(date);
                    }


                    JSONObject temperatureObj = resultsObj.getJSONObject("Temperature");
                    String minTemperature = temperatureObj.getJSONObject("Minimum").getString("Value");
                    weather.setMinTemp(minTemperature);
                    WeatherDetailsDialog.list1.add(minTemperature+" °C");

                    String maxTemperature = temperatureObj.getJSONObject("Maximum").getString("Value");
                    weather.setMaxTemp(maxTemperature);
                    WeatherDetailsDialog.list1.add(maxTemperature+" °C");

                    //******************************DAY***********************************************
                    JSONObject dayObj = resultsObj.getJSONObject("Day");
                    int dayIcon = dayObj.getInt("Icon");
                    weather.setDayIcon(dayIcon);
                    WeatherDetailsDialog.list1.add(dayIcon);

                    String iconPhrase = dayObj.getString("IconPhrase");
                    weather.setIconPhrase(iconPhrase);
                    WeatherDetailsDialog.list1.add(iconPhrase);

                    String rain = dayObj.getString("RainProbability");
                    WeatherDetailsDialog.list1.add(rain+" %");

                    String wind = dayObj.getJSONObject("Wind").getJSONObject("Speed").getString("Value");
                    WeatherDetailsDialog.list1.add(wind+" km/h");

                    //******************************NIGHT***********************************************
                    JSONObject nightObj = resultsObj.getJSONObject("Night");
                    int nightIcon = nightObj.getInt("Icon");
                    WeatherDetailsDialog.list1.add(nightIcon);

                    String iconPhraseNight = nightObj.getString("IconPhrase");
                    WeatherDetailsDialog.list1.add(iconPhraseNight);

                    String rainNight = nightObj.getString("RainProbability");
                    WeatherDetailsDialog.list1.add(rainNight+" %");

                    String windNight = nightObj.getJSONObject("Wind").getJSONObject("Speed").getString("Value");
                    WeatherDetailsDialog.list1.add(windNight+" km/h");




                   /* Log.i(TAG, "parseJSON: date: " + date + " " +
                            "Min: " + minTemperature + " " +
                            "Max: " + maxTemperature + " " +
                            "Link: " + link);*/

                    weatherArrayList.add(weather);
                }

                if(weatherArrayList != null) {
                    if(getContext()!=null)
                    {
                        WeatherAdapter weatherAdapter = new WeatherAdapter(getContext(), weatherArrayList);
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

    public String getLocation(String loc_key)
    {
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
}
