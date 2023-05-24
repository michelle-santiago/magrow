package magrow.project.application.fragment;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.AlarmReceiver;
import magrow.project.application.ColorPicker;
import magrow.project.application.DatePickerFragment;
import magrow.project.application.MainActivity;
import magrow.project.application.Message;

import magrow.project.application.MyCustomDialog;
import magrow.project.application.R;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Display;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import magrow.project.application.SeedMaturityDialog;
import magrow.project.application.TimePickerFragment;
import magrow.project.application.WarningSched;
import magrow.project.application.myDbAdapter;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import android.app.AlarmManager;
import android.app.PendingIntent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEvent extends Fragment {

    TextView ops_name;
    ImageView ops_img;
    ImageButton back;

    myDbAdapter helper;
    TextView pane,pane2,r1,time,desc,activity;
    TextView task_title,title2;
    Button save,cancel;
    RadioGroup crop_type;
    RadioButton onion,rice;
    String crop_type_choice="rice";
    String crop_type_checker="";
    String variety="";


    Spinner desc_choice,crop_name;
    ImageButton landPrep,crop,care,pest,harvest,others;
    int checker=1;
    int icon=1;
    String request_code="";
    int flagSched=0;
    String tokenChecker="0";
    EditText amount;

    double amount1=0.00;

    private Context mContext;
    public static int mPickedColor = Color.WHITE;

    public static String title="n/a";

    public static String start_date;
    public static String start_time;


    public static AlarmManager alarmManager;
    public static PendingIntent broadcast;
    public static Intent notificationIntent;


    public AddEvent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_add_event,
                container, false);

        AndroidThreeTen.init(getContext());

        ((MainActivity) getActivity())
                .setActionBarTitle(" ");

        ((MainActivity)getActivity()).showUpButton();

        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);

        ops_name=(TextView) view.findViewById(R.id.ops_name);
        activity=(TextView) view.findViewById(R.id.activity);
        ops_img=(ImageView)  view.findViewById(R.id.ops_img);

        helper = new myDbAdapter(getContext());
        save=(Button) view.findViewById(R.id.save);
        cancel=(Button) view.findViewById(R.id.cancel);
        pane = (TextView)  view.findViewById(R.id.textView);

        time = (TextView)  view.findViewById(R.id.time);
        r1 = (TextView)  view.findViewById(R.id.r1);

        desc = (EditText)  view.findViewById(R.id.desc);
        amount = (EditText)view.findViewById(R.id.amount);

        task_title = (TextView) view.findViewById(R.id.task_title);
        //title2 = (TextView) view.findViewById(R.id.title2);


        desc_choice = (Spinner) view.findViewById(R.id.desc_choice);
        crop_name = (Spinner) view.findViewById(R.id.crop_name);

        crop_type = (RadioGroup) view.findViewById(R.id.crop_type);

        onion = (RadioButton) view.findViewById(R.id.onion);
        rice = (RadioButton) view.findViewById(R.id.rice);

        back = (ImageButton)view.findViewById(R.id.back);


        String date_event = this.getArguments().getString("date_event");
        final String temp_ops_id  = this.getArguments().getString("ops_id");
        //pane.setText(date_event);
        final String operation=getOperation(temp_ops_id);

//////////////////////////POPULATE ACTIVITY SPINNER
        if(!operation.equalsIgnoreCase("Others"))
        {

            desc_choice.setVisibility(View.VISIBLE);
            desc.setVisibility(View.GONE);
        }


        ops_name.setText(operation);
        setOps_img(temp_ops_id);
        pane.setText(date_event);


        final  List<String> spinnerArray =  new ArrayList<String>();

        switch (temp_ops_id)
        {
            case "1":
                spinnerArray.add("Plowing");
                spinnerArray.add("Harrowing");
                spinnerArray.add("Levelling");
                break;

            case "2":
                if(crop_type_choice.equalsIgnoreCase("rice"))
                {
                    spinnerArray.add("Transplanting");
                    spinnerArray.add("Soaking");
                    spinnerArray.add("Pulling of Seedlings");
                    spinnerArray.add("Direct Seeding");
                }
                else
                {
                    spinnerArray.add("Transplanting");
                    spinnerArray.add("Seedlings Planting");
                    spinnerArray.add("Direct Seeding");
                }

                break;

            case "3":
                spinnerArray.add("Irrigation");
                spinnerArray.add("Water Pump");
                spinnerArray.add("Weeding");
                break;

            case "4":
                spinnerArray.add("Molluscicide");
                spinnerArray.add("Herbicide");
                spinnerArray.add("Fungicide");
                break;

            case "5":
                if(crop_type_choice.equalsIgnoreCase("rice"))
                {
                    spinnerArray.add("Reaping");
                    spinnerArray.add("Threshing");
                    spinnerArray.add("Transportation");
                    spinnerArray.add("Drying");
                    spinnerArray.add("Milling");
                }
                else
                {
                    desc_choice.setAdapter(null);
                    spinnerArray.add("Harvesting");
                }

                break;

            case "6":
                desc.setVisibility(View.VISIBLE);
                desc_choice.setVisibility(View.GONE);
                checker=0;
                break;

            case "7":
                desc.setHint("Enter fertilizer name");
                activity.setText("FERTILIZER");
                desc.setVisibility(View.VISIBLE);
                desc_choice.setVisibility(View.GONE);
                checker=2;
                break;
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        desc_choice.setAdapter(adapter);


//////////////////////////CHANGE STATUS OF OPERATIONS

      changeStatus();

////////////////////////

        final List<String> spinnerArrayCrop =  new ArrayList<String>();

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArrayCrop);



        Cursor dbres4 = helper.getAllDataCropSpesArchive(crop_type_choice,"0");
        if(dbres4.getCount() == 0)
        {

            spinnerArrayCrop.clear();
            spinnerArrayCrop.add("No Crops Yet");

            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            crop_name.setAdapter(adapter2);

        }
        else {
            spinnerArrayCrop.clear();
            while (dbres4.moveToNext()) {

                String crop = String.format(dbres4.getString(1));
                variety = String.format(dbres4.getString(3));

                spinnerArrayCrop.add(crop + "(" + variety + ")");


            }
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            crop_name.setAdapter(adapter2);

        }

        crop_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rice:
                        spinnerArrayCrop.clear();
                        crop_type_choice="rice";
                        crop_type_checker="rice";
                        Cursor dbres2 = helper.getAllDataCropSpes(crop_type_choice);
                        if(dbres2.getCount() == 0)
                        {


                            spinnerArrayCrop.add("No Crops Yet");

                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                        }

                        else {
                            spinnerArrayCrop.clear();
                            while (dbres2.moveToNext()) {

                                String crop = String.format(dbres2.getString(1));
                                variety = String.format(dbres2.getString(3));

                                spinnerArrayCrop.add(crop + "(" + variety + ")");


                            }
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                            if(temp_ops_id.equalsIgnoreCase("5"))
                            {
                                    spinnerArray.clear();
                                    spinnerArray.add("Reaping");
                                    spinnerArray.add("Threshing");
                                    spinnerArray.add("Transportation");
                                    spinnerArray.add("Drying");
                                    spinnerArray.add("Milling");
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    desc_choice.setAdapter(adapter);



                            }

                            else if(temp_ops_id.equalsIgnoreCase("2"))
                            {
                                spinnerArray.clear();

                                    spinnerArray.add("Transplanting");
                                    spinnerArray.add("Soaking");
                                    spinnerArray.add("Pulling of Seedlings");
                                    spinnerArray.add("Direct Seeding");
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    desc_choice.setAdapter(adapter);



                            }


                        }

                        break;

                    case R.id.onion:
                        crop_type_choice="onion";
                        crop_type_checker="onion";
                        spinnerArrayCrop.clear();
                        Cursor dbres3 = helper.getAllDataCropSpes(crop_type_choice);
                        if(dbres3.getCount() == 0)
                        {


                            spinnerArrayCrop.add("No Crops Yet");

                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                        }
                        else {

                            while (dbres3.moveToNext()) {

                                String crop = String.format(dbres3.getString(1));
                                variety = String.format(dbres3.getString(3));

                                spinnerArrayCrop.add(crop + "(" + variety + ")");


                            }
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            crop_name.setAdapter(adapter2);

                            if(temp_ops_id.equalsIgnoreCase("5"))
                            {
                                spinnerArray.clear();


                                    desc_choice.setAdapter(null);
                                    spinnerArray.add("Harvesting");
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    desc_choice.setAdapter(adapter);


                            }

                            else if(temp_ops_id.equalsIgnoreCase("2"))
                            {
                                spinnerArray.clear();
                                    spinnerArray.add("Transplanting");
                                    spinnerArray.add("Seedlings Planting");
                                    spinnerArray.add("Direct Seeding");
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    desc_choice.setAdapter(adapter);


                            }

                        }


                        break;




                }
            }

        });

        /*rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerArrayCrop.clear();
               crop_type_choice="rice";
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
                crop_type_choice="onion";
                crop_type_checker="onion";
                Cursor dbres16 = helper.getAllDataCropSpes(crop_type_choice);
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

                    if(crop_type_checker.equalsIgnoreCase("onion"))
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
                    if(crop_type_checker.equalsIgnoreCase("onion"))
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
        });*/



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        //Message.message(getContext(),date_event+"\n"+operation);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get a GridView object from ColorPicker class
                GridView gv = (GridView) ColorPicker.getColorPicker(getContext());

                // Initialize a new AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogTheme);

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

        pane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.show(fm,"Date Picker");
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.show(fm,"TimePicker");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_date=pane.getText().toString();
                start_time=time.getText().toString();
                final String crop_name1 = crop_name.getSelectedItem().toString();
                String temp_amount=amount.getText().toString();
                Boolean flag=true;

                if(crop_name1.equals("No Crops Yet"))
                {
                    showSnackBar("No Crop Selected");
                    flag=false;

                }

                 else if(checker==0)
                {
                    if(desc.getText().toString().equals(""))
                    {
                        showSnackBar("Please enter activity");
                        flag=false;
                    }
                }
                else if(checker==2)
                {
                    if(desc.getText().toString().equals(""))
                    {
                        showSnackBar("Please enter fertilizer");
                        flag=false;
                    }
                }
                if(flag)
                {
                    if(checker==1)
                    {
                        title=desc_choice.getSelectedItem().toString();
                    }
                    else if(checker==0){
                        title=desc.getText().toString();
                    }
                    else if(checker==2){
                        title=desc.getText().toString();
                    }

                    if(temp_amount.equals(""))
                    {
                        amount1=0.00;

                    }
                    if (!temp_amount.equals(""))
                    {
                        amount1=Double.parseDouble(temp_amount);
                    }


                 String f_timestamp = start_date + " " + start_time;
                 final long millisSinceEpoch =epochConverter(f_timestamp);

                final int date_id=getDateId();

                String icon_temp=getIconId(operation);

                icon=Integer.parseInt(icon_temp);

                final int crop_id = getCropId(crop_name1);

                String res_id=getOperationID(title);
                String[] res_temp_id = res_id.split("-");

                final String operation_id = res_temp_id[0];
                final String priority = res_temp_id[1];

                 Cursor dbres15 = helper.checkEvent(millisSinceEpoch,"0");
                 if(dbres15.getCount() != 0)
                 {
                     WarningSched dialog = new WarningSched();
                     Bundle args = new Bundle();
                     args.putString("date", start_date);
                     args.putString("time", start_time);
                     dialog.setArguments(args);
                     dialog.setTargetFragment(AddEvent.this,0);
                     dialog.show(getFragmentManager(),"MyCustomDialog");
                     dialog.setCancelable(true);
                 }
                 else
                 {
                     int sched_flag=checkPendingActivity(crop_id);

                     if(sched_flag==1)
                     {
                         helper.insertData(millisSinceEpoch, mPickedColor, title, start_date, start_time, date_id, icon, crop_id,operation_id,"0",priority,amount1);

                         String new_status="";
                         if(checker==1)
                         {
                             new_status = getCropStatus(title);
                         }
                         else if(checker==2)
                         {
                             new_status="To be fertilized";
                         }
                         else if(checker==0)
                         {
                             new_status="To be "+title;
                         }

                         int a = helper.updateCropStatus(String.valueOf(crop_id), new_status);
                         if (a <= 0) {
                             Message.message(getContext(), "Unsuccessful");

                         } else {
                             Message.message(getContext(), "Success");
                         }

                         Calendar cal = Calendar.getInstance();
                         long dateChecker = cal.getTimeInMillis();
                         if(dateChecker<millisSinceEpoch)
                         {
                             schedNotif(String.valueOf(crop_id),millisSinceEpoch);
                         }

                         String var_temp=getVariety(String.valueOf(crop_id));
                         if(title.equalsIgnoreCase("Transplanting")||title.equalsIgnoreCase("Direct Seeding"))
                         {
                             if(checkVariety(var_temp))
                             {
                                 showDialogChecker(crop_id,var_temp);
                             }
                             else
                             {
                                 getActivity().onBackPressed();
                             }
                         }
                         else
                         {
                             getActivity().onBackPressed();
                         }


                     }




                     else if(sched_flag==2)
                     {
                         android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getContext(), R.style.MyAlertDialogTheme3);

                         alertDialog.setTitle("Warning");
                         alertDialog.setMessage("\n\t\tYou have pending activities, Do you wish to continue?");
                         final TextView mess = new TextView(getContext());
                         mess.setText("\n\t\tYou have pending activities, are you sure?");
                         mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 7F, getContext().getResources().getDisplayMetrics()));
                         final CheckBox box = new CheckBox(getContext());
                         box.setText("Dont ask me again");

                         LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                 LinearLayout.LayoutParams.MATCH_PARENT,
                                 LinearLayout.LayoutParams.MATCH_PARENT);
                         mess.setLayoutParams(lp);
                         alertDialog.setView(mess);
                         alertDialog.setView(box);

                         box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                             @Override
                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                 if (isChecked) {
                                     // Message.message(getContext(), "Hello");
                                     tokenChecker = "1";
                                 } else {
                                     tokenChecker = "0";
                                 }
                             }
                         });


                         alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                                 if (tokenChecker.equals("1")) {
                                     helper.insertPrefs("yes");
                                 }

                                 helper.insertData(millisSinceEpoch, mPickedColor, title, start_date, start_time, date_id, icon, crop_id, operation_id, "0", priority, amount1);

                                 String new_status="";
                                 if(checker==1)
                                 {
                                     new_status = getCropStatus(title);
                                 }
                                 else if(checker==2)
                                 {
                                     new_status="To be fertilized";
                                 }
                                 else if(checker==0)
                                 {
                                     new_status="To be "+title;
                                 }

                                 int a = helper.updateCropStatus(String.valueOf(crop_id), new_status);
                                 if (a <= 0) {
                                     Message.message(getContext(), "Unsuccessful");

                                 } else {
                                     Message.message(getContext(), "Success");
                                 }

                                 Calendar cal = Calendar.getInstance();
                                 long dateChecker = cal.getTimeInMillis();
                                 if(dateChecker<millisSinceEpoch)
                                 {
                                     schedNotif(String.valueOf(crop_id),millisSinceEpoch);
                                 }


                                 String var_temp=getVariety(String.valueOf(crop_id));
                                 if(title.equalsIgnoreCase("Transplanting")||title.equalsIgnoreCase("Direct Seeding"))
                                 {
                                     if(checkVariety(var_temp))
                                     {
                                         showDialogChecker(crop_id,var_temp);
                                     }
                                     else
                                     {
                                         getActivity().onBackPressed();
                                     }
                                 }
                                 else
                                 {
                                     getActivity().onBackPressed();
                                 }




                             }
                         });

                         alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.cancel();
                             }
                         });
                         alertDialog.show();


                     }
                 }






                }







            }
        });










        return view;
    }

    public void showDialogChecker(int crop_id,String var_temp)
    {

                SeedMaturityDialog dialog2 = new SeedMaturityDialog();
                Bundle args = new Bundle();
                args.putString("title", title);
                args.putString("crop_id", String.valueOf(crop_id));
                args.putString("start_date", start_date);
                args.putString("variety", var_temp);
                dialog2.setTargetFragment(AddEvent.this,0);
                dialog2.setArguments(args);
                dialog2.show(getFragmentManager(),"SeedMaturityDialog");
                dialog2.setCancelable(false);

    }

    public void schedNotif(String crop_id, long millisSinceEpoch)
    {
        Cursor dbres16 = helper.getLastId();
        String temp_last_id = "";

        while (dbres16.moveToNext()) {
            temp_last_id = String.format(dbres16.getString(0));
        }

        Cursor dbres15 = helper.getCropData(String.valueOf(crop_id));

        if (dbres15.getCount() != 0) {

            while (dbres15.moveToNext()) {

                String crop_name = String.format(dbres15.getString(1));
                String crop = String.format(dbres15.getString(2));
                String temp_title = title + " of " + crop + "(" + crop_name + ")";

                //NOTIF*************

                alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                notificationIntent = new Intent(getContext(), AlarmReceiver.class);

                notificationIntent.putExtra("param", temp_title);
                notificationIntent.putExtra("event_id", temp_last_id);
                broadcast = PendingIntent.getBroadcast(getContext(), Integer.parseInt(temp_last_id), notificationIntent, PendingIntent.FLAG_ONE_SHOT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, millisSinceEpoch, broadcast);

                // Message.message(getContext(),temp_last_id);


                //END OF NOTIF*************

            }
        }
    }

    public int checkPendingActivity(int crop_id)
    {
        int status=0;
        Cursor dbres_pending = helper.checkPendingCropActivity(String.valueOf(crop_id), "0");
        if (dbres_pending.getCount() == 0) {
            status=1; //no pending activites, sched na agad
        }
        else
        {
            Cursor dbres_prefs = helper.checkPrefs();
            if (dbres_prefs.getCount() == 0) {
                status=2;  //may pending and no token, labas dialog
            }
            else
            {           //may pending and may token, sched na agad
                status=1;
            }
        }


        return status;
    }

    public Boolean checkVariety(String variety)
    {
        Boolean status=false;

        switch (variety)
        {
            case "NSIC Rc160":
                status=true;
                break;

            case "NSIC Rc222":
                status=true;
                break;

            case "NSIC Rc238":
                status=true;
                break;

            case "NSIC Rc216":
                status=true;
                break;

            case "NSIC Rc9":
                status=true;
                break;

            case "PSB Rc14":
                status=true;
                break;

            case "NSIC Rc18":
                status=true;
                break;

            case "NSIC Rc68":
                status=true;
                break;

            case "NSIC Rc194":
                status=true;
                break;

            case "NSIC Rc300":
                status=true;
                break;

            case "NSIC Rc402":
                status=true;
                break;

            case "NSIC Rc354":
                status=true;
                break;

            case "NSIC Rc218":
                status=true;
                break;

            case "PSB Rc10":
                status=true;
                break;

            //ONION************************************************************************************

            case "CX-12":
                status=true;
                break;

            case "Grannex 429":
                status=true;
                break;

            case "Red Creole":
                status=true;
                break;

            case "Red Pinoy":
                status=true;
                break;

            case "Rio Bravo":
                status=true;
                break;

            case "Rio Hondo":
                status=true;
                break;

            case "Rio Raji Red":
                status=true;
                break;

            case "Rio Tinto":
                status=true;
                break;

            case "Super Pinoy":
                status=true;
                break;

            case "SuperX":
                status=true;
                break;

            case "Texas Grano":
                status=true;
                break;

            case "Yellow Grannex":
                status=true;
                break;

            default:
                status=false;
                break;
        }


        return status;
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
                ops="Harvest Management";
                break;

            case "6":
                ops="Others";
                break;

            case "7":
                ops="Fertilizer Application";
                break;

        }

        return ops;
    }

    public void setOps_img(String temp)
    {


        switch (temp)
        {
            case "1":
                ops_img.setImageResource(R.drawable.mm1);
                break;

            case "2":
                ops_img.setImageResource(R.drawable.mm2);
                break;

            case "3":
                ops_img.setImageResource(R.drawable.mm3);
                break;

            case "4":
                ops_img.setImageResource(R.drawable.mm4);
                break;

            case "5":
                ops_img.setImageResource(R.drawable.mm5);
                break;

            case "6":
                ops_img.setImageResource(R.drawable.mm6);
                break;

            case "7":
                ops_img.setImageResource(R.drawable.mm7);
                break;

        }
    }

    private Point getScreenSize(){
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        //Display dimensions in pixels
        display.getSize(size);
        return size;
    }

    // Custom method to get status bar height in pixels
    public int getStatusBarHeight() {
        int height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    public String getIconId(String title)
    {
        int status=0;

        switch (title)
        {
            case "Land Preparation":
                status=1;
                break;
            case "Crop Operation":
                status=2;
                break;
            case "Water and Maintenance":
                status=3;
                break;
            case "Pest and Disease Control":
                status=4;
                break;
            case "Harvest Management":
                status=5;
                break;
            case "Others":
                status=6;
                break;
            case "Fertilizer Application":
                status=7;
                break;
        }

        String a=String.valueOf(status);
        return a;
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

    public void showSnackBar(String msg)
    {
        Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                msg, Snackbar.LENGTH_LONG);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snack.getView().getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        snack.getView().setLayoutParams(params);
        snack.show();
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

    public int getDateId()
    {
        Cursor dbres4 = helper.getDateIdMax();
        String d_id = "";

        while (dbres4.moveToNext()) {

            d_id = String.format(dbres4.getString(0));
            if (d_id.equals("0")) {
                d_id = "0";
            }

        }

        int dd_id = Integer.parseInt(d_id);
        dd_id = dd_id + 1;

        return dd_id;
    }

    public String getVariety(String crop_id)
    {
        String variety="";

        Cursor dbres5 = helper.getVariety(crop_id);


        while (dbres5.moveToNext()) {

            variety=String.format(dbres5.getString(3));

        }

        return variety;
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
                  //  Message.message(getContext(),"Unsuccessful");
                }
                else
                {
                   // Message.message(getContext(),"Success");
                }
                String new_status="Completed "+operation1;
                int b= helper.updateCropStatus(crop_id1,new_status);
                if(b<=0)
                {
                 //   Message.message(getContext(),"Unsuccessful");
                }
                else
                {
                  //  Message.message(getContext(),"Success");
                }

            }

        }
    }

}
