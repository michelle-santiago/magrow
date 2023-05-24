package magrow.project.application.fragment;



import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import magrow.project.application.CustomAdapter;
import magrow.project.application.DataModel;
import magrow.project.application.Message;
import magrow.project.application.myDbAdapter;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import magrow.project.application.MainActivity;
import magrow.project.application.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Calendar_pane extends Fragment {

    myDbAdapter helper;
    public static CompactCalendarView compactCalendar;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    String date_first = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    String date_head = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(new Date());
    String date;
    String date_pass;

    TextView monthName;
    TextView label1,label2,label3,label4,label5,label6,label7;

    FloatingActionButton fab,fab1,fab2,fab3,fab4,fab5,fab6,fab7;

    ArrayList<DataModel> dataModels;
    CustomAdapter adapter;
    ImageButton next,previous;

    Boolean isFABOpen=false;
    LinearLayout linearLayout2;

    ArrayList<Long> notifDates = new ArrayList<Long>();
    ArrayList<Integer> notifID = new ArrayList<Integer>();
    ArrayList<String> notifTitles = new ArrayList<String>();


    public Calendar_pane() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_calendar,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle("Calendar");

        ((MainActivity)getActivity()).hideUpButton();
        ((MainActivity)getActivity()).SetNavigationVisibiltity(true);


        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();





        monthName = (TextView) view.findViewById(R.id.monthName);
        next = (ImageButton) view.findViewById(R.id.next);
        previous = (ImageButton) view.findViewById(R.id.previous);

        monthName.setText(date_head);

        date=date_first;
        date_pass=date;


        dataModels= new ArrayList<>();

        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        linearLayout2 = (LinearLayout)view.findViewById(R.id.linearLayout2);

        label1 = (TextView) view.findViewById(R.id.label1);
        label2 = (TextView) view.findViewById(R.id.label2);
        label3 = (TextView) view.findViewById(R.id.label3);
        label4 = (TextView) view.findViewById(R.id.label4);
        label5 = (TextView) view.findViewById(R.id.label5);
        label6 = (TextView) view.findViewById(R.id.label6);
        label7 = (TextView) view.findViewById(R.id.label7);

        compactCalendar = (CompactCalendarView) view.findViewById(R.id.compactcalendar_view);




        //show();

        compactCalendar.setUseThreeLetterAbbreviation(true);
        compactCalendar.getEvents(3223213);
        compactCalendar.shouldDrawIndicatorsBelowSelectedDays(true);
        compactCalendar.setFirstDayOfWeek(Calendar.SUNDAY);
        helper = new myDbAdapter(getContext());

        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)view.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)view.findViewById(R.id.fab3);
        fab4 = (FloatingActionButton)view.findViewById(R.id.fab4);
        fab5 = (FloatingActionButton)view.findViewById(R.id.fab5);
        fab6 = (FloatingActionButton)view.findViewById(R.id.fab6);
        fab7 = (FloatingActionButton)view.findViewById(R.id.fab7);



//////////////////////////CHANGE STATUS OF OPERATIONS

changeStatus();

////////////////////////

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollRight();
                //compactCalendar.showCalendarWithAnimation();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendar.scrollLeft();
                //compactCalendar.showCalendarWithAnimation();
            }
        });

        Cursor dbres2 = helper.getAllDataCropArchive("0");
        int crop_id;
        if(dbres2.getCount() != 0)
        {

            while (dbres2.moveToNext()) {
                crop_id=Integer.parseInt(String.format(dbres2.getString(0)));

                Cursor dbres = helper.getAllDataEvent(String.valueOf(crop_id));
                if(dbres.getCount() == 0)
                {

                }
                else
                {
                    while (dbres.moveToNext()){

                        String a = String.format(dbres.getString(1));
                        String b = String.format(dbres.getString(2));
                        String event = String.format(dbres.getString(3));



                        long date_time=Long.parseLong(a);
                        int color=Integer.parseInt(b);


                        compactCalendar.addEvent(new Event(color, date_time, event));

                    }

                }


            }

        }










        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date_pass = dateFormat.format(dateClicked);
                boolean checker=false;
                int id=0;
                Context context = getContext();
                Cursor dbres3 = helper.getAllDataCropArchive("0");
                if(dbres3.getCount()!=0)
                {
                    while (dbres3.moveToNext()) {
                        id = Integer.parseInt(String.format(dbres3.getString(0)));

                        Cursor dbres2 = helper.getEventDataCrop(date_pass,String.valueOf(id));

                        if(dbres2.getCount() != 0) {
                            checker=true;
                            String event;
                            String time_event="";
                            String crop="";
                            String crop_name="";
                            String variety="";
                            while (dbres2.moveToNext()) {

                                String event_name = String.format(dbres2.getString(3));
                                String event_time = String.format(dbres2.getString(5));
                                String event_id = String.format(dbres2.getString(0));
                                String date_id = String.format(dbres2.getString(6));
                                String color2 = String.format(dbres2.getString(2));
                                int color = Integer.parseInt(color2);
                                String temp_icon = String.format(dbres2.getString(7));
                                int icon = Integer.parseInt(temp_icon);

                                String temp_crop_id = String.format(dbres2.getString(8));
                                Cursor dbres8 = helper.getCropData(temp_crop_id);
                                while (dbres8.moveToNext()) {

                                    crop = String.format(dbres8.getString(2));
                                    crop_name = String.format(dbres8.getString(1));
                                    variety = String.format(dbres8.getString(3));

                                }

                                dataModels.add(new DataModel(event_name, event_time, event_id, date_id, color, icon, crop, crop_name, variety));


                            }

                        }


                    }

                }


                if(checker)
                {
                    adapter= new CustomAdapter(dataModels,getContext());

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                            getContext(),R.style.MyAlertDialogTheme2);

                    alertDialog.setTitle("EVENTS");

                    LayoutInflater inflater = getLayoutInflater();

                    // create view for add item in dialog

                    View convertView = (View) inflater.inflate(R.layout.listview, null);

                    // on dialog cancel button listner

                    alertDialog.setNegativeButton("Cancel",

                            new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog,

                                                    int which) {

                                    dataModels.clear();



                                }

                            });
                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dataModels.clear();
                        }
                    });



                    // add custom view in dialog

                    alertDialog.setView(convertView);

                    ListView lv = (ListView) convertView.findViewById(R.id.mylistview);

                    final AlertDialog alert = alertDialog.create();

                    alert.setTitle("EVENTS"); // Title

                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override

                        public void onItemClick(AdapterView<?> arg0, View arg1,

                                                int position, long arg3) {


                            DataModel dataModel = dataModels.get(position);

                            //Message.message(getContext(),dataModel.getEvent_id()+" "+dataModel.getDate_id());


                            Bundle bundle = new Bundle();
                            bundle.putString("event_id",dataModel.getEvent_id());
                            bundle.putString("date_id",dataModel.getDate_id());// set your parameteres

                            Event_pane nextFragment = new Event_pane();
                            nextFragment.setArguments(bundle);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                            // This action will remove Fragment one and add Fragment two.
                            fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Six");

                            // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();


                            alert.cancel();

                        }

                    });




                    // show dialog

                    alert.show();

                }

                else
                {
                    Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                            "No Events on this day", Snackbar.LENGTH_SHORT);
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                            snack.getView().getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    snack.getView().setLayoutParams(params);
                    snack.show();
                }







            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthName.setText(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getContext(),"Hello fab1");
                Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","1"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getContext(),"Hello fab2");
                Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","2"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getContext(),"Hello fab3");
                Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","3"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getContext(),"Hello fab4");
                Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","4"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(getContext(), AddEventV2.class);
                intent.putExtra("date_event",date_pass); // set your parameteres
                intent.putExtra("ops_id","5"); // set your parameteres
                startActivity(intent);*/

                //Message.message(getContext(),"Hello fab5");
               Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","5"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getContext(),"Hello fab6");
                Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","6"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Message.message(getContext(),"Hello fab6");
                Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                bundle.putString("ops_id","7"); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isFABOpen){
                    showFABMenu();
                    linearLayout2.setAlpha(0.5f);
                    next.setAlpha(0.5f);
                    previous.setAlpha(0.5f);
                    next.setEnabled(false);
                    previous.setEnabled(false);
                    monthName.setAlpha(0.5f);
                    fab.setImageResource(R.drawable.ic_close);
                    label1.setVisibility(View.VISIBLE);
                    label2.setVisibility(View.VISIBLE);
                    label3.setVisibility(View.VISIBLE);
                    label4.setVisibility(View.VISIBLE);
                    label5.setVisibility(View.VISIBLE);
                    label6.setVisibility(View.VISIBLE);
                    label7.setVisibility(View.VISIBLE);

                }else{
                    closeFABMenu();
                    linearLayout2.setAlpha(1);
                    next.setAlpha(1f);
                    previous.setAlpha(1f);
                    next.setEnabled(true);
                    previous.setEnabled(true);
                    monthName.setAlpha(1);
                    fab.setImageResource(R.drawable.ic_add);
                    label1.setVisibility(View.GONE);
                    label2.setVisibility(View.GONE);
                    label3.setVisibility(View.GONE);
                    label4.setVisibility(View.GONE);
                    label5.setVisibility(View.GONE);
                    label6.setVisibility(View.GONE);
                    label7.setVisibility(View.GONE);
                }

                /*Bundle bundle = new Bundle();
                bundle.putString("date_event",date_pass); // set your parameteres
                AddEvent nextFragment = new AddEvent();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();*/

            }
        });


        return view;
    }


    void show(){
        compactCalendar.showCalendarWithAnimation();

    }

    private void showFABMenu(){
        isFABOpen=true;
        /*fab.animate().translationY(-getResources().getDimension(R.dimen.standard_90)).translationX(-getResources().getDimension(R.dimen.standard_72_5));

        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_50)).translationX(-getResources().getDimension(R.dimen.standard_20));
        label1.animate().translationY(-getResources().getDimension(R.dimen.standard_15)).translationX(-getResources().getDimension(R.dimen.standard_30));

        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_20)).translationX(-getResources().getDimension(R.dimen.standard_70));
        label2.animate().translationX(-getResources().getDimension(R.dimen.standard_70));

        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_50)).translationX(-getResources().getDimension(R.dimen.standard_120));
        label3.animate().translationY(-getResources().getDimension(R.dimen.standard_15)).translationX(-getResources().getDimension(R.dimen.standard_135));

        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_130)).translationX(-getResources().getDimension(R.dimen.standard_120));
        label4.animate().translationY(-getResources().getDimension(R.dimen.standard_190)).translationX(-getResources().getDimension(R.dimen.standard_135));


        fab5.animate().translationY(-getResources().getDimension(R.dimen.standard_160)).translationX(-getResources().getDimension(R.dimen.standard_70));
        label5.animate().translationY(-getResources().getDimension(R.dimen.standard_220)).translationX(-getResources().getDimension(R.dimen.standard_75));

        fab6.animate().translationY(-getResources().getDimension(R.dimen.standard_130)).translationX(-getResources().getDimension(R.dimen.standard_20));
        label6.animate().translationY(-getResources().getDimension(R.dimen.standard_190)).translationX(-getResources().getDimension(R.dimen.standard_30));

        fab7.animate().translationY(-getResources().getDimension(R.dimen.standard_130)).translationX(-getResources().getDimension(R.dimen.standard_20));
*/


        //fab.animate().translationX(-getResources().getDimension(R.dimen.standard_72_5));

        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_60));
        label1.animate().translationY(-getResources().getDimension(R.dimen.standard_80)).translationX(-getResources().getDimension(R.dimen.standard_70));

        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_120));
        label2.animate().translationY(-getResources().getDimension(R.dimen.standard_140)).translationX(-getResources().getDimension(R.dimen.standard_70));

        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_180));
        label3.animate().translationY(-getResources().getDimension(R.dimen.standard_200)).translationX(-getResources().getDimension(R.dimen.standard_70));

        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_240));
        label4.animate().translationY(-getResources().getDimension(R.dimen.standard_260)).translationX(-getResources().getDimension(R.dimen.standard_70));

        fab7.animate().translationY(-getResources().getDimension(R.dimen.standard_300));
        label7.animate().translationY(-getResources().getDimension(R.dimen.standard_320)).translationX(-getResources().getDimension(R.dimen.standard_70));

        fab5.animate().translationY(-getResources().getDimension(R.dimen.standard_360));
        label5.animate().translationY(-getResources().getDimension(R.dimen.standard_380)).translationX(-getResources().getDimension(R.dimen.standard_70));

        fab6.animate().translationY(-getResources().getDimension(R.dimen.standard_420));
        label6.animate().translationY(-getResources().getDimension(R.dimen.standard_440)).translationX(-getResources().getDimension(R.dimen.standard_70));


    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab.animate().translationY(0).translationX(0);
        fab1.animate().translationY(0).translationX(0);
        fab2.animate().translationY(0).translationX(0);
        fab3.animate().translationY(0).translationX(0);
        fab4.animate().translationY(0).translationX(0);
        fab7.animate().translationY(0).translationX(0);
        fab5.animate().translationY(0).translationX(0);
        fab6.animate().translationY(0).translationX(0);


        label1.animate().translationY(0).translationX(0);
        label2.animate().translationY(0).translationX(0);
        label3.animate().translationY(0).translationX(0);
        label4.animate().translationY(0).translationX(0);
        label7.animate().translationY(0).translationX(0);
        label5.animate().translationY(0).translationX(0);
        label6.animate().translationY(0).translationX(0);


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
//                    Message.message(getContext(),"Success");
                }
                String new_status="Completed "+operation1;
                int b= helper.updateCropStatus(crop_id1,new_status);
                if(b<=0)
                {
  //                  Message.message(getContext(),"Unsuccessful");
                }
                else
                {
    //                Message.message(getContext(),"Success");
                }

            }

        }
    }


}