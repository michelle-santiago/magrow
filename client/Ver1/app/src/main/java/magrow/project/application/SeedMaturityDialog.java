package magrow.project.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import magrow.project.application.R;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SeedMaturityDialog extends DialogFragment {

    public String tag="SeedMaturityDialog";
    myDbAdapter helper;

    Button yes,no;

    TextView crop_variety,pane;
    ImageView crop_type;

    int mPickedColor = Color.WHITE;
    String operation="";
    String start_time="00:00:00";

    public static AlarmManager alarmManager;
    public static PendingIntent broadcast;
    public static Intent notificationIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_seed_maturity, container, false);

        helper = new myDbAdapter(getContext());
        yes=(Button)view.findViewById(R.id.yes);


        crop_type=(ImageView)view.findViewById(R.id.crop_type);
        pane=(TextView)view.findViewById(R.id.pane);
        crop_variety=(TextView)view.findViewById(R.id.crop_variety);


        //GETTING DATA NEEDED********************************************************************
        int icon=5;
        int mpickedcolor= Color.WHITE;


        Bundle mArgs = getArguments();
        String title = mArgs.getString("title");
        final int crop_id=Integer.parseInt(mArgs.getString("crop_id"));
        String start_date= mArgs.getString("start_date");
        String variety= mArgs.getString("variety");

        String new_title="";
        String crop_type_temp="";

        int maturity=getMaturity(variety,title);

        final String expected_date=getExpectedDate(start_date,maturity);

        String expected_date_f=expected_date;

        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(expected_date_f, df);

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM dd yyyy");
        String expected_day=dtfOut.print(dateTime);

        String timestamp=expected_date+" "+start_time;
        final long millisSinceEpoch =epochConverter(timestamp);





        //GET CROP TYPE IF CROP ID***************************************************************

        Cursor dbres = helper.getCropData(String.valueOf(crop_id));

        if(dbres.getCount()!= 0)
        {

            while (dbres.moveToNext()) {

                crop_type_temp = String.format(dbres.getString(2));

            }
        }

        //Message.message(getContext(),crop_type_temp);


        //SET DATA TO XML************************************************************************
        if(crop_type_temp.equalsIgnoreCase("rice"))
        {
            crop_type.setImageResource(R.drawable.rice_white);
            operation="reaping";
        }

        else if(crop_type_temp.equalsIgnoreCase("onion"))
        {
            crop_type.setImageResource(R.drawable.onion_white);
            operation="harvesting";
        }

        if(title.equalsIgnoreCase("Transplanting"))
            new_title="transplanted";
        else if(title.equalsIgnoreCase("Direct Seeding"))
            new_title="direct seeded";



        crop_variety.setText(variety);

        String src="You just <b>"+new_title+"</b> this crop,<br>it will reach its maturity at <b>"+maturity+" days</b>.<br>The expected day of <b>"+operation+"</b><br>is on <b>"+expected_day+"</b>.<br>Have a great day!";
        pane.setText(Html.fromHtml(src));




        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
                dismiss();
            }
        });





        //Message.message(getContext(),icon+" "+mpickedcolor+" "+start_time+" "+title+" "+crop_id+" "+start_date+" "+date_id);


        return view;
    }

    public long epochConverter(String f_timestamp){


        String input = f_timestamp.replace( " " , "T" );
        LocalDateTime ldt = LocalDateTime.parse( input ) ;


        ZoneId z = ZoneId.of( "Asia/Singapore" ) ;
        ZonedDateTime zdt = ldt.atZone( z ) ;
        Instant instant = zdt.toInstant() ;
        long millisSinceEpoch = instant.toEpochMilli() ;

        return millisSinceEpoch;
    }

    public String getExpectedDate(String date_start,int maturity)
    {
        String expected_date="";
        String expected_date2="";

        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");
        long millis = df.parseMillis(date_start);
        Date date = new Date(millis);

        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(maturity);

        Date fdate = dateTime.toDate();

        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        expected_date=dt1.format(fdate);



        return expected_date;
    }

    public int getMaturity(String variety,String action)
    {
        int days=0;

        switch (variety)
        {

            //RICE************************************************************************************
            case "NSIC Rc160":
                if(action.equalsIgnoreCase("Transplanting"))
                    days=107;
                else
                    days=122;
                break;

            case "NSIC Rc222":
                if(action.equalsIgnoreCase("Transplanting"))
                    days=114;
                else
                    days=106;
                break;

            case "NSIC Rc238":
                days=110;
                break;

            case "NSIC Rc216":
                if(action.equalsIgnoreCase("Transplanting"))
                    days=112;
                else
                    days=104;
                break;

            case "NSIC Rc9":
                days=119;
                break;

            case "PSB Rc14":
                days=110;
                break;

            case "NSIC Rc18":
                days=123;
                break;

            case "NSIC Rc68":
                days=116;
                break;

            case "NSIC Rc194":
                days=112;
                break;

            case "NSIC Rc300":
                if(action.equalsIgnoreCase("Transplanting"))
                    days=115;
                else
                    days=105;
                break;

            case "NSIC Rc402":
                if(action.equalsIgnoreCase("Transplanting"))
                    days=114;
                else
                    days=107;
                break;

            case "NSIC Rc354":
                days=112;
                break;

            case "NSIC Rc218":
                days=120;
                break;

            case "PSB Rc10":
                days=106;
                break;

            //ONION************************************************************************************

            case "CX-12":
                days=110;
                break;

            case "Grannex 429":
                days=95;
                break;

            case "Red Creole":
                days=110;
                break;

            case "Red Pinoy":
                days=110;
                break;

            case "Rio Bravo":
                days=95;
                break;

            case "Rio Hondo":
                days=95;
                break;

            case "Rio Raji Red":
                days=110;
                break;

            case "Rio Tinto":
                days=80;
                break;

            case "Super Pinoy":
                if(action.equalsIgnoreCase("Transplanting"))
                    days=95;
                else
                    days=125;
                break;

            case "SuperX":
                days=160;
                break;

            case "Texas Grano":
                days=95;
                break;

            case "Yellow Grannex":
                days=95;
                break;


        }

        return days;
    }

    public String getOperationID(String op_id)
    {
        String id="0";
        String priority="0";
        switch (op_id)
        {
            case "Plowing":
                id="1";
                priority="1";
                break;
            case "Harrowing":
                id="2";
                priority="1";
                break;
            case "Levelling":
                id="3";
                priority="1";
                break;
            case "Soaking":
                id="4";
                priority="1";
                break;
            case "Direct Seeding":
                id="4";
                priority="1";
                break;
            case "Pulling of Seedlings":
                id="5";
                priority="1";
                break;
            case "Seedlings Planting":
                id="5";
                priority="1";
                break;
            case "Transplanting":
                id="6";
                priority="1";
                break;
            case "Reaping":
                id="7";
                priority="1";
                break;
            case "Harvesting":
                id="7";
                priority="1";
                break;
            case "Threshing":
                id="8";
                priority="1";
                break;
            case "Drying":
                id="9";
                priority="1";
                break;
            case "Milling":
                id="10";
                priority="1";
                break;
            default:
                id="0";
                priority="0";
                break;
        }
        String id_priority=id+"-"+priority;
        return id_priority;
    }
}
