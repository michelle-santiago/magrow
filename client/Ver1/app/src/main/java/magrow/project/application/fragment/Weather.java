package magrow.project.application.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.MainActivity;
import magrow.project.application.Message;
import magrow.project.application.NetworkUtils;
import magrow.project.application.R;
import magrow.project.application.WeatherAdapter;
import magrow.project.application.WeatherDetailsDialog;
import magrow.project.application.WeatherModel;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Weather extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<WeatherModel> weatherArrayList = new ArrayList<>();
    //private ListView listView;
    TextView currentTemp,weatherText,humidity,wind,location,date,meridiem;
    ImageView dayNight;
    Button weather_details;
    ProgressBar progressBar;
    ViewPager viewPager;
    ImageButton back;


    public Weather() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_weather,
                container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //((MainActivity)getActivity()).showUpButton();
        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);

        //Message.message(getContext(),""+Home.location);

        //listView = view.findViewById(R.id.idListView);

        meridiem = (TextView)view.findViewById(R.id.meridiem);
        currentTemp = (TextView)view.findViewById(R.id.currentTemp);
        weatherText = (TextView)view.findViewById(R.id.WeatherText);
        humidity = (TextView)view.findViewById(R.id.Humidity);
        wind = (TextView)view.findViewById(R.id.Wind);

        location=(TextView)view.findViewById(R.id.location);
        date=(TextView)view.findViewById(R.id.date);

        dayNight = (ImageView) view.findViewById(R.id.dayNight);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        back=(ImageButton) view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String cur_date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        date.setText(cur_date);

        String location_name=getLocation(Home.location);
        location.setText(location_name);

        /*URL weatherUrl = NetworkUtils.buildUrlForWeather();
        new FetchWeatherDetails().execute(weatherUrl);
        Log.i(TAG, "onCreate: weatherUrl: " + weatherUrl);*/

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            meridiem.setText(" Good Morning ! ");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            meridiem.setText(" Good Afternoon ! ");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            meridiem.setText(" Good Evening ! ");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            meridiem.setText(" Good Night ! ");
        }



        new currentConditions().execute();

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                            /*Toast.makeText(MainActivity.this,

                                    "You have selected : " + myList.get(position),

                                    Toast.LENGTH_SHORT).show();*/

                /*WeatherModel dataModel = weatherArrayList.get(position);

                Message.message(getContext(),dataModel.getDate());

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



           /* }

        });*/

        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Day5Weather(), "5 Days");
        viewPagerAdapter.addFragment(new Hour12Weather(), "12 hours");

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }

    }

   /* private class FetchWeatherDetails extends AsyncTask<URL, Void, String> {
        //final AlertDialog alertDialog= new SpotsDialog.Builder().setContext(getContext()).setMessage("Loading...").build();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //alertDialog.show();
            progressBar.setVisibility(View.VISIBLE);

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
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(weatherSearchResults);
        }
    }





    /*private ArrayList<WeatherModel> parseJSON(String weatherSearchResults) {
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

                 /*   weatherArrayList.add(weather);
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
    }*/


    private class currentConditions extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            progressBar.setVisibility(View.VISIBLE);

            String str="http://dataservice.accuweather.com/currentconditions/v1/"+Home.location+"?apikey=ddjX9PgYrC5uGAQXSZ0FGGvjVZUN9Wqg&details=true";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new String(stringBuffer.toString());
            }
            catch(Exception ex)
            {
                Log.e("App", "yourDataTask", ex);
                return null;
            }
            finally
            {
                if(bufferedReader != null)
                {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String response)
        {
            progressBar.setVisibility(View.GONE);

            if(response != null)
            {
                String temperature="";
                String icon="";
                String weatherText2="";
                String humidity2="";
                String wind2="";
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        temperature = jsonobject.getJSONObject("Temperature").getJSONObject("Metric").getString("Value");
                        icon = jsonobject.getString("WeatherIcon");
                        weatherText2 = jsonobject.getString("WeatherText");
                        humidity2 = jsonobject.getString("RelativeHumidity");
                        wind2 = jsonobject.getJSONObject("Wind").getJSONObject("Speed").getJSONObject("Metric").getString("Value");
                    }

                    currentTemp.setText(temperature+" °C");
                    weatherText.setText(weatherText2);
                    humidity.setText(humidity2+"%");
                    wind.setText(wind2+ "km/h");

                    switch (icon)
                    {
                        case "1":
                            dayNight.setImageResource(R.drawable.a1);
                            break;
                        case "2":
                            dayNight.setImageResource(R.drawable.a2);
                            break;
                        case "3":
                            dayNight.setImageResource(R.drawable.a3);
                            break;
                        case "4":
                            dayNight.setImageResource(R.drawable.a4);
                            break;
                        case "5":
                            dayNight.setImageResource(R.drawable.a5);
                            break;
                        case "6":
                            dayNight.setImageResource(R.drawable.a6);
                            break;
                        case "7":
                            dayNight.setImageResource(R.drawable.a7);
                            break;
                        case "8":
                            dayNight.setImageResource(R.drawable.a8);
                            break;
                        case "11":
                            dayNight.setImageResource(R.drawable.a11);
                            break;
                        case "12":
                            dayNight.setImageResource(R.drawable.a12);
                            break;
                        case "13":
                            dayNight.setImageResource(R.drawable.a13);
                            break;
                        case "14":
                            dayNight.setImageResource(R.drawable.a14);
                            break;
                        case "15":
                            dayNight.setImageResource(R.drawable.a15);
                            break;
                        case "16":
                            dayNight.setImageResource(R.drawable.a16);
                            break;
                        case "17":
                            dayNight.setImageResource(R.drawable.a17);
                            break;
                        case "18":
                            dayNight.setImageResource(R.drawable.a18);
                            break;
                        case "32":
                            dayNight.setImageResource(R.drawable.a32);
                            break;
                        case "33":
                            dayNight.setImageResource(R.drawable.a33);
                            break;
                        case "34":
                            dayNight.setImageResource(R.drawable.a34);
                            break;
                        case "35":
                            dayNight.setImageResource(R.drawable.a35);
                            break;
                        case "36":
                            dayNight.setImageResource(R.drawable.a36);
                            break;
                        case "37":
                            dayNight.setImageResource(R.drawable.a37);
                            break;
                        case "38":
                            dayNight.setImageResource(R.drawable.a38);
                            break;
                        case "39":
                            dayNight.setImageResource(R.drawable.a39);
                            break;
                        case "40":
                            dayNight.setImageResource(R.drawable.a40);
                            break;
                        case "41":
                            dayNight.setImageResource(R.drawable.a41);
                            break;
                        case "42":
                            dayNight.setImageResource(R.drawable.a42);
                            break;

                    }

                } catch (JSONException ex) {
                    progressBar.setVisibility(View.GONE);
                    Log.e("App", "Failure", ex);
                }
            }
        }
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