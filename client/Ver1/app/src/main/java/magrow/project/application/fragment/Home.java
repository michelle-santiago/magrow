package magrow.project.application.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import magrow.project.application.CardItemString;
import magrow.project.application.CardPagerAdapterS;
import magrow.project.application.FarmDetails;
import magrow.project.application.MainActivity;
import magrow.project.application.Message;
import magrow.project.application.MyCustomDialog;
import magrow.project.application.R;
import magrow.project.application.ShadowTransformer;
import magrow.project.application.myDbAdapter;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements MyCustomDialog.OnInputSelected {

    private static final String TAG = "HomeFragment";

    public static String location="";
    public static String id="";

    @Override
    public void sendInput(String input,String input2) {

        String[] farm = input.split("");
        if(farm.length<=14)
        {
            farm_name.setText("\t"+input);
        }
        else
        {
            String newName="";
            for(int i=0;i<=14;i++)
            {
                newName=newName+farm[i];
            }
            farm_name.setText("\t"+newName+"...");
        }

        location=locationKeys(input2);
    }

    myDbAdapter helper;


    private Object Context=getContext();


    public Home() {
        // Required empty public constructor
    }

    private ViewPager mViewPager;
    TextView status;
    ImageButton settings;
    Button settings_profile;
    public TextView farm_name,farm_location,farm_size;

    TextView currentTemp;
    ImageButton dayNight;
    ImageButton reload;
    LinearLayout header;

    private CardPagerAdapterS mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_home,
                container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((MainActivity)getActivity()).SetNavigationVisibiltity(true);

        Context = this;
        helper = new myDbAdapter(getContext());
        //new currentConditions().execute();
        currentTemp = (TextView)view.findViewById(R.id.currentTemp);
        dayNight = (ImageButton) view.findViewById(R.id.dayNight);
        reload=(ImageButton)view.findViewById(R.id.reload);

        header=(LinearLayout)view.findViewById(R.id.linearLayout5);

        farm_name=(TextView)view.findViewById(R.id.farm_name);
        settings=(ImageButton)view.findViewById(R.id.settings);
        settings_profile=(Button)view.findViewById(R.id.settings_profile);


        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("MyPrefs",0);
        final SharedPreferences.Editor editor = sharedPref.edit();
        String usernameKey=sharedPref.getString("nameKey", null);


        if(usernameKey!=null)
        {
            String[] user = usernameKey.split("");
            if(user.length<=12)
            {
                settings_profile.setText(usernameKey);
            }
            else
            {
                String newName="";
                for(int i=0;i<=12;i++)
                {
                    newName=newName+user[i];
                }
                settings_profile.setText(newName+"...");
            }
        }

        Cursor dbres4 = helper.checkWeather();
        {
            if(dbres4.getCount()==0)
            {
                Calendar c = Calendar.getInstance();
                int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                if(timeOfDay >= 0 && timeOfDay < 12){
                    dayNight.setImageResource(R.drawable.a1);
                }else if(timeOfDay >= 12 && timeOfDay < 18){
                    dayNight.setImageResource(R.drawable.a1);
                }else if(timeOfDay >= 18 && timeOfDay < 21){
                    dayNight.setImageResource(R.drawable.a33);
                }else if(timeOfDay >= 21 && timeOfDay < 24){
                    dayNight.setImageResource(R.drawable.a33);
                }
                currentTemp.setText("25 °C");
            }
            else
            {
                String temp="";
                String icon="";
                while (dbres4.moveToNext()) {
                    temp=String.format(dbres4.getString(1));
                    icon=String.format(dbres4.getString(2));
                }

                currentTemp.setText(temp+" °C");
                setIcon(icon);
            }
        }



        changeStatus();

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new currentConditions().execute();
            }
        });

        dayNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Weather nextFragment = new Weather();
                nextFragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                fragmentManager.addToBackStack(null);*/
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Profile");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

            }
        });

        Cursor dbres2 = helper.getFarmDetails();
        if(dbres2.getCount() == 0)
        {
            //FragmentManager fm = getActivity().getFragmentManager();
            MyCustomDialog dialog = new MyCustomDialog();
            dialog.setTargetFragment(Home.this,0);
            dialog.show(getFragmentManager(),"MyCustomDialog");
            dialog.setCancelable(false);

        }
        else
        {
            String location_temp="";
            String fname="";
            while (dbres2.moveToNext()) {

                id = String.format(dbres2.getString(0));
                fname = String.format(dbres2.getString(1));
                location_temp = String.format(dbres2.getString(2));
                /*String farea = String.format(dbres2.getString(3));
                String fmeasure = String.format(dbres2.getString(4));*/
                // char initials=fname.charAt(0);
                //settings.setText(""+initials);
               /*farm_location.setText("\t\t\tFarm Location:\t"+flocation);
                farm_size.setText("\t\t\tFarm Size:\t\t\t\t\t"+farea+" "+fmeasure);
                location=locationKeys(flocation);*/
            }

            String[] farm = fname.split("");
            if(farm.length<=14)
            {
                farm_name.setText("\t"+fname);
            }
            else
            {
                String newName="";
                for(int i=0;i<=14;i++)
                {
                    newName=newName+farm[i];
                }
                farm_name.setText("\t"+newName+"...");
            }


            location=locationKeys(location_temp);

        }

        String date_today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar cal = Calendar.getInstance();
        long dateChecker = cal.getTimeInMillis();
        String epochTime=String.valueOf(dateChecker);
        Cursor dbres = helper.getEventDataList(epochTime,date_today);


        status=(TextView)view.findViewById(R.id.status);
        mViewPager = (ViewPager)view.findViewById(R.id.viewPager);
        mCardAdapter = new CardPagerAdapterS();

        if(dbres.getCount() == 0)
        {

            mCardAdapter.addCardItemS(new CardItemString( "0", "No Activity\nScheduled","","",0));
            status.setText("No upcoming activities at this moment");
        }

        else {

            String event;
            String time_event="";
            String crop="";
            String crop_name="";
            String variety="";
            int no_events=0;
            while (dbres.moveToNext()) {

                String event_name=String.format(dbres.getString(3));
                String event_time=String.format(dbres.getString(5));
                String event_id=String.format(dbres.getString(0));

                String color2=String.format(dbres.getString(2));
                String event_date=String.format(dbres.getString(4));
                int color=Integer.parseInt(color2);
                String date_id=String.format(dbres.getString(6));

                String temp_icon=String.format(dbres.getString(7));
                int icon=Integer.parseInt(temp_icon);

                String temp_crop_id=String.format(dbres.getString(8));
                Cursor dbres8 = helper.getCropData(temp_crop_id);
                while (dbres8.moveToNext()) {

                    crop=String.format(dbres8.getString(2));
                    crop_name=String.format(dbres8.getString(1));
                    variety=String.format(dbres8.getString(3));

                }

                //dataModels2.add(new DataModelEventList(event_name, event_time,event_date,event_id,date_id,color,icon,crop,crop_name,variety));
                mCardAdapter.addCardItemS(new CardItemString( event_name, event_time,crop_name+"\n("+variety+")",crop,icon));
                //Message.message(getContext(),event_name+" "+event_time+" "+crop_name+" "+crop+" "+icon);
                no_events++;

            }
            if(no_events==1)
            {
                status.setText(no_events+" Upcoming activity today");
            }
            else
            {
                status.setText(no_events+" Upcoming activities today");
            }

        }


        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);



        settings_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Logged_in nextFragment = new Logged_in();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                fragmentManager.addToBackStack(null);*/


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Profile");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Profile nextFragment = new Profile();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                fragmentManager.addToBackStack(null);*/


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment FarmDetails");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

                /*Intent intent = new Intent(v.getContext(), FarmDetails.class);
                v.getContext().startActivity(intent);*/



            }
        });



        return view;
    }

    private class currentConditions extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {

            String str="http://dataservice.accuweather.com/currentconditions/v1/"+Home.location+"?apikey=0fVqDydv09HEPY2v7I6yiqDhnNeL0Ysz&details=true";
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
            if(response != null)
            {
                String temperature="";
                String icon="";

                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        temperature = jsonobject.getJSONObject("Temperature").getJSONObject("Metric").getString("Value");
                        icon = jsonobject.getString("WeatherIcon");

                    }

                    Cursor dbres4 = helper.checkWeather();
                    {
                        if(dbres4.getCount()==0)
                        {
                            long result = helper.insertWeather("1",temperature,icon);
                            if(result != -1)
                            {
                                Message.message(getContext(), "Success");
                            }

                        }
                        else
                        {
                            int a = helper.updateWeather("1",temperature,icon);
                            if (a <= 0) {
                                Message.message(getContext(), "Unsuccessful");

                            } else {
                                Message.message(getContext(), "Success");
                            }
                        }
                    }

                    currentTemp.setText(temperature+" °C");

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
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }

    public void setIcon(String icon)
    {
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
    }

    public String locationKeys(String fflocation)
    {
        String a="";

        switch (fflocation)
        {
            case "ALIAGA":
                a="265099";
                break;

            case "BONGABON":
                a="265084";
                break;

            case "CABANATUAN CITY":
                a="265082";
                break;

            case "CABIAO":
                a="265085";
                break;

            case "CARRANGLAN":
                a="265086";
                break;

            case "CUYAPO":
                a="265087";
                break;

            case "GABALDON":
                a="265088";
                break;

            case "GAPAN":
                a="265079";
                break;

            case "GENERAL NATIVIDAD":
                a="265100";
                break;

            case "GENERAL TINIO":
                a="265089";
                break;

            case "GUIMBA":
                a="265080";
                break;

            case "JAEN":
                a="265102";
                break;

            case "LAUR":
                a="265090";
                break;

            case "LICAB":
                a="265103";
                break;

            case "LLANERA":
                a="265104";
                break;

            case "LUPAO":
                a="265091";
                break;

            case "MUÑOZ":
                a="265092";
                break;

            case "NAMPICUAN":
                a="265105";
                break;

            case "PALAYAN":
                a="265083";
                break;

            case "PANTABANGAN":
                a="265106";
                break;

            case "PEÑARANDA":
                a="265093";
                break;

            case "QUEZON":
                a="265107";
                break;

            case "RIZAL":
                a="265108";
                break;
            case "SAN ANTONIO":
                a="265094";
                break;

            case "SAN ISIDRO":
                a="265095";
                break;

            case "SAN JOSE CITY":
                a="265081";
                break;

            case "SAN LEONARDO":
                a="265096";
                break;

            case "SANTA ROSA":
                a="265097";
                break;

            case "SANTO DOMINGO":
                a="265098";
                break;

            case "TALAVERA":
                a="265110";
                break;

            case "TALUGTUG":
                a="3429768";
                break;

            case "ZARAGOZA":
                a="265112";
                break;


        }

        return a;

    }

    public void changeStatus()
    {
        String operation1="";
        Calendar cal = Calendar.getInstance();
        long dateChecker = cal.getTimeInMillis();
        String epochTime=String.valueOf(dateChecker);

        Cursor dbres_update = helper.getEventList(epochTime,"0");
        if(dbres_update.getCount() != 0)
        {
            while (dbres_update.moveToNext()) {

                String trans_log_id1=String.format(dbres_update.getString(0));
                String crop_id1=String.format(dbres_update.getString(8));
                operation1=String.format(dbres_update.getString(3));

                int a= helper.updateEventLog(trans_log_id1,"1");
                if(a<=0)
                {
                    //Message.message(getContext(),"Unsuccessful");
                }
                else
                {
                    // Message.message(getContext(),"Success");
                }
                String new_status="Completed "+operation1;
                int b= helper.updateCropStatus(crop_id1,new_status);
                if(b<=0)
                {
                    //Message.message(getContext(),"Unsuccessful");
                }
                else
                {
                    //Message.message(getContext(),"Success");
                }

            }

        }
    }




}

