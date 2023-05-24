package magrow.project.application;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import magrow.project.application.R;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Event_Summary extends AppCompatActivity {
    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc;
    TextView btn,task_title;
    Button save,del;
    ImageButton landPrep,crop,care,pest,harvest,others;
    Spinner desc_choice,crop_name;
    RadioGroup crop_type;
    RadioButton onion,rice;
    ImageView ops_img;
    ImageButton back;
    EditText amount;

    String id="";
    String color="";
    String event_name="";
    String event_date="";
    String event_time="";
    String request_code="";
    String crop_type_checker="";

    String crop_variety_checker="";

    String event_date_start="";
    String event_date_end="";

    int checker=1;
    int icon=1;

    int flag;

    double amount1=0.00;

    public static String title;
    public static String start_date;
    public static String end_date;
    public static String start_time;

    public static int mPickedColor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__summary);
        AndroidThreeTen.init(getApplicationContext());
        Event_Summary.this.getSupportActionBar().hide();
        helper = new myDbAdapter(getApplicationContext());
        ops_img=findViewById(R.id.ops_img);

        back = findViewById(R.id.back);

        save=findViewById(R.id.save2);
        del=findViewById(R.id.del);
        pane =  findViewById(R.id.textView);
        time =  findViewById(R.id.time);
        r1 =  findViewById(R.id.r1);
        desc =  findViewById(R.id.desc2);
        task_title = findViewById(R.id.task_title);

        landPrep =  findViewById(R.id.landPrep);
        crop =  findViewById(R.id.crop);
        care =  findViewById(R.id.care);
        pest =  findViewById(R.id.pest);
        harvest =  findViewById(R.id.harvest);
        others =  findViewById(R.id.others);

        desc_choice = findViewById(R.id.desc_choice);
        crop_name = findViewById(R.id.crop_name);

        crop_type = findViewById(R.id.crop_type);

        onion = findViewById(R.id.onion);
        rice = findViewById(R.id.rice);

        amount=(EditText)findViewById(R.id.amount);

        Intent intent = getIntent();
        String event_id=intent.getStringExtra("event_id");
        String date_id=intent.getStringExtra("date_id");
        final List<String> spinnerArrayCrop =  new ArrayList<String>();

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArrayCrop);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*Intent intent = new Intent(ResetPinActivity.this, Summary.class);
                startActivity(intent);
                finish();*/
            }
        });

        pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = Event_Summary.this.getFragmentManager();
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.show(fm,"Date Picker");
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = Event_Summary.this.getFragmentManager();
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.show(fm,"TimePicker");
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a GridView object from ColorPicker class
                GridView gv = (GridView) ColorPicker.getColorPicker(getApplicationContext());



                // Initialize a new AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(Event_Summary.this, R.style.MyAlertDialogTheme);

                // Set the alert dialog content to GridView (color picker)
                builder.setTitle(Html.fromHtml("<font color='#000000'>Choose the color</font>"));
                builder.setView(gv);

                // Initialize a new AlertDialog object
                final AlertDialog dialog = builder.create();

                // Show the color picker window
                dialog.show();

                // Set the color picker dialog size


                // Set an item click listener for GridView widget
                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Get the pickedColor from AdapterView
                        mPickedColor = (int) parent.getItemAtPosition(position);

                        // Set the layout background color as picked color
                        r1.setBackgroundColor(mPickedColor);

                        // close the color picker
                        dialog.dismiss();
                    }
                });
            }
        });

        final List<String> spinnerArray =  new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        desc_choice.setAdapter(adapter);

        desc.setVisibility(View.GONE);
        desc_choice.setVisibility(View.VISIBLE);


        landPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setVisibility(View.GONE);
                ops_img.setImageResource(R.drawable.mm1);
                crop.setVisibility(View.VISIBLE);
                care.setVisibility(View.VISIBLE);
                pest.setVisibility(View.VISIBLE);
                harvest.setVisibility(View.VISIBLE);
                others.setVisibility(View.VISIBLE);

                icon=1;
                task_title.setText("Land Preparation");
                spinnerArray.clear();
                spinnerArray.add("Plowing");
                spinnerArray.add("Harrowing");
                spinnerArray.add("Levelling");

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops_img.setImageResource(R.drawable.mm2);
                landPrep.setVisibility(View.VISIBLE);
                crop.setVisibility(View.GONE);
                care.setVisibility(View.VISIBLE);
                pest.setVisibility(View.VISIBLE);
                harvest.setVisibility(View.VISIBLE);
                others.setVisibility(View.VISIBLE);

                icon=2;
                task_title.setText("Crop Operation");
                spinnerArray.clear();


                if(crop_type_checker.equalsIgnoreCase("rice"))
                {
                    spinnerArray.add("Transplanting");
                    spinnerArray.add("Soaking");
                    spinnerArray.add("Pulling of Seedlings");
                    spinnerArray.add("Direct Seeding");
                }
                else if(crop_type_checker.equalsIgnoreCase("onion"))
                {
                    spinnerArray.add("Transplanting");
                    spinnerArray.add("Seedlings Planting");
                    spinnerArray.add("Direct Seeding");
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);


                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops_img.setImageResource(R.drawable.mm3);
                landPrep.setVisibility(View.VISIBLE);
                crop.setVisibility(View.VISIBLE);
                care.setVisibility(View.GONE);
                pest.setVisibility(View.VISIBLE);
                harvest.setVisibility(View.VISIBLE);
                others.setVisibility(View.VISIBLE);

                icon=3;
                task_title.setText("Water and Maintenance");
                spinnerArray.clear();
                spinnerArray.add("Irrigation");
                spinnerArray.add("Water Pump");
                spinnerArray.add("Weeding");


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        pest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops_img.setImageResource(R.drawable.mm4);
                landPrep.setVisibility(View.VISIBLE);
                crop.setVisibility(View.VISIBLE);
                care.setVisibility(View.VISIBLE);
                pest.setVisibility(View.GONE);
                harvest.setVisibility(View.VISIBLE);
                others.setVisibility(View.VISIBLE);

                icon=4;
                task_title.setText("Pest and Disease Control");
                spinnerArray.clear();
                spinnerArray.add("Molluscicide");
                spinnerArray.add("Pesticide");
                spinnerArray.add("Herbicide");
                spinnerArray.add("Fungicide");


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                desc_choice.setAdapter(adapter);

                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        harvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops_img.setImageResource(R.drawable.mm5);
                landPrep.setVisibility(View.VISIBLE);
                crop.setVisibility(View.VISIBLE);
                care.setVisibility(View.VISIBLE);
                pest.setVisibility(View.VISIBLE);
                harvest.setVisibility(View.GONE);
                others.setVisibility(View.VISIBLE);

                icon=5;
                task_title.setText("Harvest and Post Harvest");
                spinnerArray.clear();

                if(crop_type_checker.equalsIgnoreCase("rice"))
                {
                    spinnerArray.add("Reaping");
                    spinnerArray.add("Threshing");
                    spinnerArray.add("Transportation");
                    spinnerArray.add("Drying");
                    spinnerArray.add("Milling");

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                }

                else if(crop_type_checker.equalsIgnoreCase("onion"))
                {
                    desc_choice.setAdapter(null);
                    spinnerArray.add("Harvesting");

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);

                }




                desc.setVisibility(View.GONE);
                desc_choice.setVisibility(View.VISIBLE);

                checker=1;

            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ops_img.setImageResource(R.drawable.mm6);
                landPrep.setVisibility(View.VISIBLE);
                crop.setVisibility(View.VISIBLE);
                care.setVisibility(View.VISIBLE);
                pest.setVisibility(View.VISIBLE);
                harvest.setVisibility(View.VISIBLE);
                others.setVisibility(View.GONE);

                icon=6;
                task_title.setText("Others");


                desc.setVisibility(View.VISIBLE);
                desc_choice.setVisibility(View.GONE);

                checker=0;

            }
        });


        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerArrayCrop.clear();
                String crop_type_choice="rice";
                crop_type_checker="rice";
                Cursor dbres15 = helper.getAllDataCropSpes(crop_type_choice);
                while (dbres15.moveToNext()) {

                    String crop = String.format(dbres15.getString(1));
                    String variety2 = String.format(dbres15.getString(3));


                    spinnerArrayCrop.add(crop + "(" + variety2 + ")");


                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);

                }

                if(icon==5)
                {
                    spinnerArray.clear();

                    if(crop_type_checker.equalsIgnoreCase("rice"))
                    {
                        spinnerArray.add("Reaping");
                        spinnerArray.add("Threshing");
                        spinnerArray.add("Transportation");
                        spinnerArray.add("Drying");
                        spinnerArray.add("Milling");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }
                    else
                    {
                        desc_choice.setAdapter(null);
                        spinnerArray.add("Harvesting");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }

                }

                else if(icon==2)
                {
                    spinnerArray.clear();
                    if(crop_type_checker.equalsIgnoreCase("rice"))
                    {
                        spinnerArray.add("Transplanting");
                        spinnerArray.add("Soaking");
                        spinnerArray.add("Pulling of Seedlings");
                        spinnerArray.add("Direct Seeding");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }
                    else
                    {
                        spinnerArray.add("Transplanting");
                        spinnerArray.add("Seedlings Planting");
                        spinnerArray.add("Direct Seeding");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }


                }



            }
        });

        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerArrayCrop.clear();
                String crop_type_choice2="onion";
                crop_type_checker="onion";
                Cursor dbres16 = helper.getAllDataCropSpes(crop_type_choice2);
                while (dbres16.moveToNext()) {

                    String crop = String.format(dbres16.getString(1));
                    String variety2 = String.format(dbres16.getString(3));


                    spinnerArrayCrop.add(crop + "(" + variety2 + ")");


                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);
                }

                if(icon==5)
                {
                    spinnerArray.clear();

                    if(crop_type_checker.equalsIgnoreCase("rice"))
                    {
                        spinnerArray.add("Reaping");
                        spinnerArray.add("Threshing");
                        spinnerArray.add("Transportation");
                        spinnerArray.add("Drying");
                        spinnerArray.add("Milling");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }
                    else
                    {
                        desc_choice.setAdapter(null);
                        spinnerArray.add("Harvesting");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }

                }

                else if(icon==2)
                {
                    spinnerArray.clear();
                    if(crop_type_checker.equalsIgnoreCase("rice"))
                    {
                        spinnerArray.add("Transplanting");
                        spinnerArray.add("Soaking");
                        spinnerArray.add("Pulling of Seedlings");
                        spinnerArray.add("Direct Seeding");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }
                    else
                    {
                        spinnerArray.add("Transplanting");
                        spinnerArray.add("Seedlings Planting");
                        spinnerArray.add("Direct Seeding");
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        desc_choice.setAdapter(adapter);
                    }


                }

            }
        });

        /*crop_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rice:


                        break;

                    case R.id.onion:

                        break;




                }
            }

        });*/



        Cursor dbres = helper.getEventInfo(date_id);
        if(dbres.getCount() == 1)
        {
            flag=1;

            int icon_choice=0;
            String crop_type="";
            String crop_name2="";
            String variety="";
            String crop_id="";
            String temp_amount="";

            while (dbres.moveToNext()){

                request_code=String.format(dbres.getString(0));
                id= String.format(dbres.getString(6));
                color = String.format(dbres.getString(2));
                event_name = String.format(dbres.getString(3));
                event_date = String.format(dbres.getString(4));
                event_time = String.format(dbres.getString(5));
                icon_choice=Integer.parseInt(String.format(dbres.getString(7)));
                crop_id=String.format(dbres.getString(8));
                temp_amount = String.format(dbres.getString(12));

                Cursor dbres8 = helper.getCropData(crop_id);
                while (dbres8.moveToNext()) {

                    crop_type=String.format(dbres8.getString(2));
                    crop_name2=String.format(dbres8.getString(1));
                    variety=String.format(dbres8.getString(3));

                }

                crop_variety_checker=crop_name2 + "(" + variety + ")";
                spinnerArrayCrop.add(crop_name2 + "(" + variety + ")");


            }

            amount.setText(temp_amount);



            //Message.message(getContext(),crop_type);

            if(crop_type.equalsIgnoreCase("rice"))
            {
                rice.setChecked(true);
                onion.setChecked(false);
                crop_type_checker="rice";
                String crop_type_choice="rice";
                Cursor dbres12 = helper.getAllDataCropSpes(crop_type_choice);
                while (dbres12.moveToNext()) {

                    String crop = String.format(dbres12.getString(1));
                    String variety2 = String.format(dbres12.getString(3));

                    if(!crop_variety_checker.equals(crop + "(" + variety2 + ")")) {
                        spinnerArrayCrop.add(crop + "(" + variety2 + ")");
                    }

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);


                }
            }
            else if(crop_type.equalsIgnoreCase("onion"))
            {
                rice.setChecked(false);
                onion.setChecked(true);

                String crop_type_choice="onion";
                crop_type_checker="onion";

                Cursor dbres17 = helper.getAllDataCropSpes(crop_type_choice);
                while (dbres17.moveToNext()) {

                    String crop = String.format(dbres17.getString(1));
                    String variety2 = String.format(dbres17.getString(3));

                    if(!crop_variety_checker.equals(crop + "(" + variety2 + ")")) {
                        spinnerArrayCrop.add(crop + "(" + variety2 + ")");
                    }

                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    crop_name.setAdapter(adapter2);


                }


            }

            pane.setText(event_date);
            r1.setBackgroundColor(Integer.parseInt(color));
            mPickedColor=Integer.parseInt(color);

            time.setText(event_time);
            desc.setText(event_name);

            //Message.message(getContext(),icon_choice+"");

            spinnerArray.add(event_name);
            switch (icon_choice)
            {
                case 1:
                    landPrep.setVisibility(View.GONE);
                    ops_img.setImageResource(R.drawable.mm1);
                    crop.setVisibility(View.VISIBLE);
                    care.setVisibility(View.VISIBLE);
                    pest.setVisibility(View.VISIBLE);
                    harvest.setVisibility(View.VISIBLE);
                    others.setVisibility(View.VISIBLE);

                    icon=1;
                    checker=1;
                    task_title.setText("Land Preparation");
                    String[] event1 = new String[3];
                    event1[0]="Plowing";
                    event1[1]="Harrowing";
                    event1[2]="Levelling";
                    for(int i=0;i<event1.length;i++)
                    {
                        if(!event_name.equalsIgnoreCase(event1[i]))
                        {
                            spinnerArray.add(event1[i]);
                        }
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 2:
                    ops_img.setImageResource(R.drawable.mm2);
                    landPrep.setVisibility(View.VISIBLE);
                    crop.setVisibility(View.GONE);
                    care.setVisibility(View.VISIBLE);
                    pest.setVisibility(View.VISIBLE);
                    harvest.setVisibility(View.VISIBLE);
                    others.setVisibility(View.VISIBLE);

                    icon=2;
                    checker=1;

                    task_title.setText("Crop Operation");
                    String[] event2 = new String[4];


                    if(crop_type.equalsIgnoreCase("rice"))
                    {
                        event2[0]="Transplanting";
                        event2[1]="Soaking";
                        event2[2]="Pulling of Seedlings";
                        event2[3]="Direct Seeding";
                    }
                    else
                    {
                        event2[0]="Transplanting";
                        event2[1]="Seedlings Planting";
                        event2[2]="Direct Seeding";
                    }

                    for(int i=0;i<event2.length;i++)
                    {
                        if(!event_name.equalsIgnoreCase(event2[i]))
                        {
                            spinnerArray.add(event2[i]);
                        }
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);


                    break;

                case 3:
                    ops_img.setImageResource(R.drawable.mm3);
                    landPrep.setVisibility(View.VISIBLE);
                    crop.setVisibility(View.VISIBLE);
                    care.setVisibility(View.GONE);
                    pest.setVisibility(View.VISIBLE);
                    harvest.setVisibility(View.VISIBLE);
                    others.setVisibility(View.VISIBLE);

                    icon=3;
                    checker=1;
                    task_title.setText("Water and Maintenance");
                    String[] event3 = new String[3];
                    event3[0]="Irrigation";
                    event3[1]="Water";
                    event3[2]="Weeding";
                    for(int i=0;i<event3.length;i++)
                    {
                        if(!event_name.equalsIgnoreCase(event3[i]))
                        {
                            spinnerArray.add(event3[i]);
                        }
                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 4:
                    ops_img.setImageResource(R.drawable.mm4);
                    landPrep.setVisibility(View.VISIBLE);
                    crop.setVisibility(View.VISIBLE);
                    care.setVisibility(View.VISIBLE);
                    pest.setVisibility(View.GONE);
                    harvest.setVisibility(View.VISIBLE);
                    others.setVisibility(View.VISIBLE);

                    icon=4;
                    checker=1;
                    task_title.setText("Pest and Disease Control");
                    String[] event4 = new String[3];
                    event4[0]="Molluscicide";
                    event4[1]="Herbicide";
                    event4[2]="Fungicide";
                    for(int i=0;i< event4.length;i++)
                    {
                        String check = event4[i];
                        if(!check.equals(event_name))
                        {
                            spinnerArray.add(event4[i]);
                        }

                    }
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 5:
                    ops_img.setImageResource(R.drawable.mm5);
                    landPrep.setVisibility(View.VISIBLE);
                    crop.setVisibility(View.VISIBLE);
                    care.setVisibility(View.VISIBLE);
                    pest.setVisibility(View.VISIBLE);
                    harvest.setVisibility(View.GONE);
                    others.setVisibility(View.VISIBLE);

                    icon=5;
                    checker=1;
                    task_title.setText("Harvest and Post Harvest");

                    if(crop_type.equalsIgnoreCase("rice"))
                    {
                        String[] event5 = new String[5];
                        event5[0] = "Reaping";
                        event5[1] = "Threshing";
                        event5[2] = "Transportation";
                        event5[3] = "Drying";
                        event5[4] = "Milling";
                        for (int i = 0; i <event5.length; i++) {

                            String check = event5[i];
                            if (!check.equals(event_name)) {
                                spinnerArray.add(event5[i]);
                            }
                        }
                    }


                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    desc_choice.setAdapter(adapter);
                    break;

                case 6:
                    ops_img.setImageResource(R.drawable.mm6);
                    landPrep.setVisibility(View.VISIBLE);
                    crop.setVisibility(View.VISIBLE);
                    care.setVisibility(View.VISIBLE);
                    pest.setVisibility(View.VISIBLE);
                    harvest.setVisibility(View.VISIBLE);
                    others.setVisibility(View.GONE);

                    icon=6;
                    task_title.setText("Others");
                    desc.setVisibility(View.VISIBLE);
                    desc_choice.setVisibility(View.GONE);
                    checker=0;
                    break;
            }








        }
        else
        {
            flag=0;

            Cursor dbres2 = helper.getStartDate(date_id);

            while (dbres2.moveToNext()) {


                request_code=String.format(dbres2.getString(0));
                id= String.format(dbres2.getString(6));
                color = String.format(dbres2.getString(2));
                event_name = String.format(dbres2.getString(3));
                event_date_start = String.format(dbres2.getString(4));
                event_time = String.format(dbres2.getString(5));



            }

            Cursor dbres3 = helper.getEndDate(date_id);

            while (dbres3.moveToNext()) {

                event_date_end = String.format(dbres3.getString(4));


            }

            pane.setText(event_date_start);
            r1.setBackgroundColor(Integer.parseInt(color));
            mPickedColor=Integer.parseInt(color);

            time.setText(event_time);
            desc.setText(event_name);

            spinnerArray.add(event_name);


            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            desc_choice.setAdapter(adapter);




        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start_date=pane.getText().toString();
                start_time=time.getText().toString();
                String temp_amount=amount.getText().toString();


                if(checker==0)
                {
                    title=desc.getText().toString();
                }
                else
                {
                    title=desc_choice.getSelectedItem().toString();
                }

                if(temp_amount.equals(""))
                {
                    amount1=0.00;

                }
                if (!temp_amount.equals(""))
                {
                    amount1=Double.parseDouble(temp_amount);
                }


                String f_timestamp=start_date+" "+start_time;
                long millisSinceEpoch =epochConverter(f_timestamp);

                String crop_name1 = crop_name.getSelectedItem().toString();

                int crop_id=getCropId(crop_name1);
                String res_id=getOperationID(title);
                String[] res_temp_id = res_id.split("-");

                final String operation_id = res_temp_id[0];
                final String priority = res_temp_id[1];

                String operation_status="";
                Calendar cal2 = Calendar.getInstance();
                long dateChecker2 = cal2.getTimeInMillis();
                if(dateChecker2<millisSinceEpoch)
                {
                    operation_status="0";
                    String new_status = getCropStatus(title);
                    int a = helper.updateCropStatus(String.valueOf(crop_id), new_status);
                    if (a <= 0) {
                        Message.message(getApplicationContext(), "Unsuccessful");

                    } else {
                        Message.message(getApplicationContext(), "Success");
                    }
                }
                else
                {
                    operation_status="1";
                }



                int c= helper.updateEvent( millisSinceEpoch, mPickedColor,title,start_date,start_time,id,icon,crop_id,operation_id,operation_status,priority,amount1);
                if(c<=0)
                {
                    Message.message(getApplicationContext(),"Unsuccessful");

                } else {

                    Calendar cal = Calendar.getInstance();
                    long dateChecker = cal.getTimeInMillis();

                    if(dateChecker<millisSinceEpoch)
                    {
                        try
                        {
                            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                            PendingIntent broadcast2 = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(request_code) , myIntent, PendingIntent.FLAG_ONE_SHOT);
                            broadcast2.cancel();
                            alarmManager.cancel(broadcast2);
                            myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);

                            Cursor dbres15 = helper.getCropData(String.valueOf(crop_id));

                            if(dbres15.getCount()!=0)
                            {

                                while (dbres15.moveToNext()) {

                                    String crop_name = String.format(dbres15.getString(1));
                                    String crop=String.format(dbres15.getString(2));
                                    String temp_title=title+" of "+crop+"("+crop_name+")";

                                    //NOTIF*************

                                    myIntent.putExtra("param", temp_title);
                                    myIntent.putExtra("event_id", request_code);

                                    broadcast2 = PendingIntent.getBroadcast(getApplicationContext(),Integer.parseInt(request_code), myIntent, PendingIntent.FLAG_ONE_SHOT);
                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, millisSinceEpoch, broadcast2);

                                    //Message.message(getContext(),request_code);

                                    //END OF NOTIF*************

                                }
                            }





                        }
                        catch (Exception e)
                        {
                            Message.message(getApplicationContext(),e.getMessage());
                        }

                    }

                    Message.message(getApplicationContext(), "Event Updated");

                }
                //Message.message(getApplicationContext(),f_timestamp+" "+millisSinceEpoch);





                finish();//cho

               /* Bundle bundle = new Bundle();
                Calendar_pane nextFragment = new Calendar_pane();
                nextFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Two");
                fragmentTransaction.commit();(*/



            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CANCEL NOTIFICATION
                //Message.message(getContext(),request_code);
                try
                {
                    AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    myIntent.putExtra("event_id", request_code);
                    PendingIntent broadcast2 = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(request_code) , myIntent, PendingIntent.FLAG_ONE_SHOT);
                    broadcast2.cancel();
                    alarmManager.cancel(broadcast2);
                }
                catch (Exception e)
                {
                    Message.message(getApplicationContext(),e.getMessage());
                }


                //CANCEL NOTIFICATION



                helper.delete(id);




                Message.message(getApplication(),"Event Deleted");
                finish();//cho

               /* Bundle bundle = new Bundle();
                Calendar_pane nextFragment = new Calendar_pane();
                nextFragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Two");
                fragmentTransaction.commit();*/

            }
        });


    }

    public String getCropStatus(String title)
    {
        String status="";

        switch (title)
        {
            case "Plowing":
                status="To be plowed";
                break;
            case "Harrowing":
                status="To be harrowed";
                break;
            case "Levelling":
                status="To be levelled";
                break;
            case "Transplanting":
                status="To be transplanted";
                break;
            case "Soaking":
                status="To be soaked";
                break;
            case "Pulling of Seedlings":
                status="To be pulled of seedlings";
                break;

            case "Seedlings Planting":
                status="To be planted with seedlings";
                break;

            case "Direct Seeding":
                status="To be directly seeded";
                break;
            case "Irrigation":
                status="To be irrigated";
                break;
            case "Water Pump":
                status="To be pumped with water";
                break;
            case "Weeding":
                status="To be weeded";
                break;
            case "Molluscicide":
                status="To be sprayed with pesticide";
                break;
            case "Herbicide":
                status="To be sprayed with pesticide";
                break;
            case "Fungicide":
                status="To be sprayed with pesticide";
                break;
            case "Reaping":
                status="To be reaped";
                break;
            case "Threshing":
                status="To be threshed";
                break;
            case "Transportation":
                status="To be transported";
                break;
            case "Drying":
                status="To be dried";
                break;
            case "Harvesting":
                status="To be harvested";
                break;
            case "Milling":
                status="To be milled";
                break;
        }

        return status;
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

    public String getOperation(String temp)
    {
        String ops="";

        switch (temp)
        {
            case "1":
                ops="Land Preparation";
                break;

            case "2":
                ops="Crop Operation";
                break;

            case "3":
                ops="Water and Maintenance";
                break;

            case "4":
                ops="Pest and Disease Control";
                break;

            case "5":
                ops="Harvest and Post harvest";
                break;

            case "6":
                ops="Others";
                break;

        }

        return ops;
    }

    public int getCropId(String crop){

        /*String temp1 = crop.replace(")","");
        String temp2 = temp1.replace("(","-");
        String[] temp3 = temp2.split("-");
        String aa = temp3[0];
        aa=aa.replace(" ","");*/


        int flag=0;
        String str="";
        String[] temp_crop=crop.split("");
        for(int i=0;i<=temp_crop.length;i++)
        {
            if(!temp_crop[i].equals("("))
            {
                str=str+temp_crop[i];
            }
            else if(temp_crop[i].equals("("))
            {
                break;
            }

        }

        Cursor dbres5 = helper.getCropId(str);
        String temp_id="";

        while (dbres5.moveToNext()) {

            temp_id=String.format(dbres5.getString(0));

        }
        int a = Integer.parseInt(temp_id);

        return a;

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
}
