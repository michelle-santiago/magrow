package magrow.project.application;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Summary extends AppCompatActivity {

    private ArrayList<SummaryDataModel> landprepArrayList = new ArrayList<>();
    private ArrayList<SummaryDataModel> crop_opsArrayList = new ArrayList<>();
    private ArrayList<SummaryDataModel> water_maintArrayList = new ArrayList<>();
    private ArrayList<SummaryDataModel> pestsArrayList = new ArrayList<>();
    private ArrayList<SummaryDataModel> harvestArrayList = new ArrayList<>();
    private ArrayList<SummaryDataModel> othersArrayList = new ArrayList<>();
    private ArrayList<SummaryDataModel> fertilizerArrayList = new ArrayList<>();
    private ListView landprep_lv,crop_ops_lv,water_maint_lv,pests_lv,harvest_lv,others_lv,fertilizer_lv;
    TextView landprep_no,crop_ops_no,water_maint_no,pests_no,harvest_no,others_no,fertilizer_no,overall_total,landprep_total,crop_ops_total,water_maint_total,pests_total,harvest_total,others_total,fertilizer_total;
    TextView crop_label;
    ImageButton landPrep,crop,care,pest,harvest,others,fertilizer;
    myDbAdapter helper;
    LinearLayout landPrep_ll,crop_ll,care_ll,pest_ll,harvest_ll,others_ll,fertilizer_ll;
    String id_crop="";

    private static DecimalFormat df2 = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        crop_label=(TextView)findViewById(R.id.crop_label);
        landprep_lv = findViewById(R.id.landprep_lv);
        crop_ops_lv = findViewById(R.id.crop_ops_lv);
        water_maint_lv = findViewById(R.id.water_maint_lv);
        pests_lv = findViewById(R.id.pests_lv);
        harvest_lv=findViewById(R.id.harvest_lv);
        others_lv=findViewById(R.id.others_lv);
        fertilizer_lv=findViewById(R.id.fertilizer_lv);
        helper = new myDbAdapter(getApplicationContext());

        landprep_no=findViewById(R.id.landprep_no);
        water_maint_no=findViewById(R.id.water_maint_no);
        crop_ops_no=findViewById(R.id.crop_ops_no);
        harvest_no=findViewById(R.id.harvest_no);
        pests_no=findViewById(R.id.pests_no);
        others_no=findViewById(R.id.others_no);
        fertilizer_no=findViewById(R.id.fertilizer_no);
        landprep_total=findViewById(R.id.landprep_total);
        water_maint_total=findViewById(R.id.water_maint_total);
        crop_ops_total=findViewById(R.id.crop_ops_total);
        harvest_total=findViewById(R.id.harvest_total);
        pests_total=findViewById(R.id.pests_total);
        others_total=findViewById(R.id.others_total);
        fertilizer_total=findViewById(R.id.fertilizer_total);
        overall_total=findViewById(R.id.overall_total);
        landPrep = findViewById(R.id.landPrep);
        crop = findViewById(R.id.crop);
        care = findViewById(R.id.care);
        pest = findViewById(R.id.pest);
        harvest = findViewById(R.id.harvest);
        others = findViewById(R.id.others);
        fertilizer = findViewById(R.id.fertilizer);
        landPrep_ll = findViewById(R.id.landPrep_ll);
        crop_ll = findViewById(R.id.crop_ll);
        care_ll = findViewById(R.id.care_ll);
        pest_ll = findViewById(R.id.pest_ll);
        harvest_ll = findViewById(R.id.harvest_ll);
        others_ll = findViewById(R.id.others_ll);
        fertilizer_ll = findViewById(R.id.fertilizer_ll);

        Intent intent = getIntent();
        id_crop=intent.getStringExtra("crop_id");
        String name_crop=intent.getStringExtra("crop_name");
        String variety_crop=intent.getStringExtra("crop_variety");
        String status=intent.getStringExtra("status");
        crop_label.setText(name_crop+" ("+variety_crop+")");

        if(status.equalsIgnoreCase("disable"))
        {
            landprep_lv.setEnabled(false);
            crop_ops_lv.setEnabled(false);
            water_maint_lv.setEnabled(false);
            pests_lv.setEnabled(false);
            harvest_lv.setEnabled(false);
            others_lv.setEnabled(false);
            fertilizer_lv.setEnabled(false);
        }

        setPlayersDataAdapter();

        landprep_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel = landprepArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel.getEvent_id());
                intent.putExtra("date_id",dataModel.getDate_id());
                startActivity(intent);

            }

        });
        landPrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                crop.setBackgroundColor(Color.parseColor("#FFFFFF"));
                care.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                harvest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                others.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                landPrep_ll.setVisibility(View.VISIBLE);
                crop_ll.setVisibility(View.GONE);
                care_ll.setVisibility(View.GONE);
                pest_ll.setVisibility(View.GONE);
                harvest_ll.setVisibility(View.GONE);
                others_ll.setVisibility(View.GONE);
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.GONE);
            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setBackgroundColor(Color.parseColor("#FFFFFF"));
                crop.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                care.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                harvest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                others.setBackgroundColor(Color.parseColor("#FFFFFF"));
                landPrep_ll.setVisibility(View.GONE);
                crop_ll.setVisibility(View.VISIBLE);
                care_ll.setVisibility(View.GONE);
                pest_ll.setVisibility(View.GONE);
                harvest_ll.setVisibility(View.GONE);
                others_ll.setVisibility(View.GONE);
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.GONE);
            }
        });
        care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setBackgroundColor(Color.parseColor("#FFFFFF"));
                crop.setBackgroundColor(Color.parseColor("#FFFFFF"));
                care.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                pest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                harvest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                others.setBackgroundColor(Color.parseColor("#FFFFFF"));
                landPrep_ll.setVisibility(View.GONE);
                crop_ll.setVisibility(View.GONE);
                care_ll.setVisibility(View.VISIBLE);
                pest_ll.setVisibility(View.GONE);
                harvest_ll.setVisibility(View.GONE);
                others_ll.setVisibility(View.GONE);
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.GONE);
            }
        });
        pest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setBackgroundColor(Color.parseColor("#FFFFFF"));
                crop.setBackgroundColor(Color.parseColor("#FFFFFF"));
                care.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pest.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                harvest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                others.setBackgroundColor(Color.parseColor("#FFFFFF"));
                landPrep_ll.setVisibility(View.GONE);
                crop_ll.setVisibility(View.GONE);
                care_ll.setVisibility(View.GONE);
                pest_ll.setVisibility(View.VISIBLE);
                harvest_ll.setVisibility(View.GONE);
                others_ll.setVisibility(View.GONE);
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.GONE);
            }
        });
        harvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setBackgroundColor(Color.parseColor("#FFFFFF"));
                crop.setBackgroundColor(Color.parseColor("#FFFFFF"));
                care.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                harvest.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                others.setBackgroundColor(Color.parseColor("#FFFFFF"));
                landPrep_ll.setVisibility(View.GONE);
                crop_ll.setVisibility(View.GONE);
                care_ll.setVisibility(View.GONE);
                pest_ll.setVisibility(View.GONE);
                harvest_ll.setVisibility(View.VISIBLE);
                others_ll.setVisibility(View.GONE);
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.GONE);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landPrep.setBackgroundColor(Color.parseColor("#FFFFFF"));
                crop.setBackgroundColor(Color.parseColor("#FFFFFF"));
                care.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                harvest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                others.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                landPrep_ll.setVisibility(View.GONE);
                crop_ll.setVisibility(View.GONE);
                care_ll.setVisibility(View.GONE);
                pest_ll.setVisibility(View.GONE);
                harvest_ll.setVisibility(View.GONE);
                others_ll.setVisibility(View.VISIBLE);
                fertilizer.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.GONE);
            }
        });
        crop_ops_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel1 = crop_opsArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel1.getEvent_id());
                intent.putExtra("date_id",dataModel1.getDate_id());
                startActivity(intent);

            }

        });
        water_maint_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel2 = water_maintArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel2.getEvent_id());
                intent.putExtra("date_id",dataModel2.getDate_id());
                startActivity(intent);

            }

        });
        pests_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel2 = pestsArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel2.getEvent_id());
                intent.putExtra("date_id",dataModel2.getDate_id());
                startActivity(intent);

            }

        });
        harvest_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel2 = harvestArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel2.getEvent_id());
                intent.putExtra("date_id",dataModel2.getDate_id());
                startActivity(intent);

            }

        });
        others_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel2 = othersArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel2.getEvent_id());
                intent.putExtra("date_id",dataModel2.getDate_id());
                startActivity(intent);

            }

        });
        fertilizer_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub

                SummaryDataModel dataModel = fertilizerArrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), Event_Summary.class);
                intent.putExtra("event_id",dataModel.getEvent_id());
                intent.putExtra("date_id",dataModel.getDate_id());
                startActivity(intent);

            }

        });
        fertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fertilizer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                landPrep.setBackgroundColor(Color.parseColor("#FFFFFF"));
                crop.setBackgroundColor(Color.parseColor("#FFFFFF"));
                care.setBackgroundColor(Color.parseColor("#FFFFFF"));
                pest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                harvest.setBackgroundColor(Color.parseColor("#FFFFFF"));
                others.setBackgroundColor(Color.parseColor("#FFFFFF"));
                fertilizer_ll.setVisibility(View.VISIBLE);
                landPrep_ll.setVisibility(View.GONE);
                crop_ll.setVisibility(View.GONE);
                care_ll.setVisibility(View.GONE);
                pest_ll.setVisibility(View.GONE);
                harvest_ll.setVisibility(View.GONE);
                others_ll.setVisibility(View.GONE);
            }
        });

    }


    private void setPlayersDataAdapter() {
        landprepArrayList.clear();
        crop_opsArrayList.clear();
        water_maintArrayList.clear();
        pestsArrayList.clear();
        harvestArrayList.clear();
        othersArrayList.clear();
        fertilizerArrayList.clear();
        Cursor dbres = helper.getEventLog(id_crop);
        if(dbres.getCount() == 0)
        {

        }
        else {


            String event;
            String time_event="";
            String crop="";
            String crop_name="";
            String variety="";
            Double total_land=0.0;
            Double total_crop_ops=0.0;
            Double total_other=0.0;
            Double total_water=0.0;
            Double total_pest=0.0;
            Double total_harvest=0.0;
            Double total_fertilizer=0.0;
            Double overall=0.0;
            while (dbres.moveToNext()) {

                String event_name=String.format(dbres.getString(3));
                String event_time=String.format(dbres.getString(5));
                String date=String.format(dbres.getString(4));
                String event_id=String.format(dbres.getString(0));
                String date_id=String.format(dbres.getString(6));
                String color2=String.format(dbres.getString(2));
                int color=Integer.parseInt(color2);
                String temp_icon=String.format(dbres.getString(7));
                int icon=Integer.parseInt(temp_icon);

                String temp_crop_id=String.format(dbres.getString(8));
                //Double amount2=Double.parseDouble(dbres.getString(12));
                Double temp_amount=Double.parseDouble(dbres.getString(12));
                String amount1=df2.format(temp_amount);
                Cursor dbres8 = helper.getCropData(temp_crop_id);
                while (dbres8.moveToNext()) {

                    crop=String.format(dbres8.getString(2));
                    crop_name=String.format(dbres8.getString(1));
                    variety=String.format(dbres8.getString(3));

                }
                overall =overall+temp_amount;
                if(temp_icon.equals("1")) {

                    total_land =total_land+temp_amount;
                    landprepArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety, amount1));
                }
                if(temp_icon.equals("2")) {
                    crop_opsArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety,  amount1));
                    total_crop_ops =total_crop_ops+temp_amount;
                }
                if(temp_icon.equals("3")) {
                    water_maintArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety,  amount1));
                    total_water =total_water+temp_amount;
                }
                if(temp_icon.equals("4")) {
                   pestsArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety,  amount1));
                   total_pest =total_pest+temp_amount;
                }
                if(temp_icon.equals("5")) {
                    harvestArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety,  amount1));
                    total_harvest =total_harvest+temp_amount;
                }
                if(temp_icon.equals("6")) {
                    othersArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety,  amount1));
                    total_other =total_other+temp_amount;
                }
                if(temp_icon.equals("7")) {
                    fertilizerArrayList.add(new SummaryDataModel(event_name, event_time, event_id, date_id, date, color, icon, crop, crop_name, variety,  amount1));
                    total_fertilizer =total_fertilizer+temp_amount;
                }
            }

           landprep_total.setText("\u20B1"+df2.format(total_land));
            crop_ops_total.setText("\u20B1"+df2.format(total_crop_ops));
            pests_total.setText("\u20B1"+df2.format(total_pest));
            water_maint_total.setText("\u20B1"+df2.format(total_water));
            harvest_total.setText("\u20B1"+df2.format(total_harvest));
            others_total.setText("\u20B1"+df2.format(total_other));
            fertilizer_total.setText("\u20B1"+df2.format(total_fertilizer));
            overall_total.setText("\u20B1"+df2.format(overall));

        }
        if(landprepArrayList != null) {

            //landprep_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter = new SummaryAdapter(getApplicationContext(), landprepArrayList);
            landprep_lv.setAdapter(recordAdapter);
        }
        else{
            //landprep_no.setVisibility(View.VISIBLE);
        }
        if(crop_opsArrayList != null) {
           // crop_ops_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter1 = new SummaryAdapter(getApplicationContext(), crop_opsArrayList);
            crop_ops_lv.setAdapter(recordAdapter1);
        }
        else{
           // crop_ops_no.setVisibility(View.VISIBLE);
        }
        if(water_maintArrayList != null) {
            //water_maint_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter2 = new SummaryAdapter(getApplicationContext(), water_maintArrayList);
            water_maint_lv.setAdapter(recordAdapter2);
        }
        else{
            //water_maint_no.setVisibility(View.VISIBLE);
        }
        if(pestsArrayList != null) {
            //pests_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter2 = new SummaryAdapter(getApplicationContext(), pestsArrayList);
            pests_lv.setAdapter(recordAdapter2);
        }
        else{
           // pests_no.setVisibility(View.VISIBLE);
        }
        if(harvestArrayList != null) {
            //harvest_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter2 = new SummaryAdapter(getApplicationContext(), harvestArrayList);
            harvest_lv.setAdapter(recordAdapter2);
        }
        else{
            //harvest_no.setVisibility(View.VISIBLE);
        }
        if(othersArrayList != null) {
            //others_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter2 = new SummaryAdapter(getApplicationContext(), othersArrayList);
            others_lv.setAdapter(recordAdapter2);
        }
        else{
            //others_no.setVisibility(View.VISIBLE);
        }
        if(fertilizerArrayList != null) {
            //fertilizer_no.setVisibility(View.GONE);
            SummaryAdapter recordAdapter2 = new SummaryAdapter(getApplicationContext(), fertilizerArrayList);
            fertilizer_lv.setAdapter(recordAdapter2);
        }
        else{
            //fertilizer_no.setVisibility(View.VISIBLE);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){

        finish();
        return true;
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        setPlayersDataAdapter();
    }
/*
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter, R.anim.exit);

    }*/



}
