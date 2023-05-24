package magrow.project.application;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import magrow.project.application.R;

import java.text.DecimalFormat;

public class RecordDetails extends AppCompatActivity {

    TextView crop_name,crop_variety,status_content,areaHarvested,area_planted,no_of_cavans_harvested,weight_per_cavan,ave_weight,seeds1,irrig_fee1,fertilizer1,pesticides1,labor1,rental1,transport1,other1,total1;
    Button add;
    Double area,yields,seeds,irrig_fee,fertilizer,pesticides,labor,rental,transport,other,area_harvested,area_planted1,weight_per_cavan1,ave_weight1;
    String ap_measurement,ah_measurement;
    int no_of_cavans_harvested1;
    FloatingActionButton fab,fab2;
    private static final String TAG = "RecordDetailsDialog";
    String crop_name1,crop_variety1,status_content1;
    String crop_id1;
    myDbAdapter helper;
    private static DecimalFormat df2 = new DecimalFormat("0.00");
    String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new myDbAdapter(getApplicationContext());
        // helper = new myDbAdapter(getContext());
        crop_name = (TextView)findViewById(R.id.crop_name);
        crop_variety = (TextView)findViewById(R.id.crop_variety);
        add = (Button)findViewById(R.id.add_crop);
        status_content = (TextView)findViewById(R.id.status_content);
        areaHarvested=findViewById((R.id.areaHarvested));
        area_planted=findViewById(R.id.area_planted);
        no_of_cavans_harvested=findViewById(R.id.no_of_cavans_harvested);
        weight_per_cavan=findViewById(R.id.weight_per_cavan);
        ave_weight=findViewById(R.id.ave_weight);
       /* seeds1=findViewById(R.id.seeds1);
        irrig_fee1=findViewById(R.id.irrig_fee1);
        fertilizer1=findViewById(R.id.fertilizer1);
        pesticides1=findViewById(R.id.pesticides1);
        labor1=findViewById(R.id.labor1);
        rental1=findViewById(R.id.rental1);
        transport1=findViewById(R.id.transport1);
        other1=findViewById(R.id.other1);
        total1=findViewById(R.id.total);*/

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab2=(FloatingActionButton)findViewById(R.id.fab2);
        Intent intent = getIntent();
        crop_id1=intent.getStringExtra("crop_id");
        crop_name1=intent.getStringExtra("crop_name");
        crop_variety1=intent.getStringExtra("crop_variety");
        status=intent.getStringExtra("status");
        //status_content1 =intent.getStringExtra("status");
        //Toast.makeText(getApplicationContext(),"why"+crop_id1,Toast.LENGTH_LONG).show();
        crop_name.setText(crop_name1);
        crop_variety.setText(crop_variety1);
        status_content.setText(status_content1);


        /*if(status_content1.equals("No Record Yet")){
            areaHarvested.setText("        --      ");
            area_planted.setText("        --      ");
            seeds1.setText("        --      ");
            irrig_fee1.setText("        --      ");
            fertilizer1.setText("        --      ");
            pesticides1.setText("        --      ");
            labor1.setText("        --      ");
            rental1.setText("        --      ");
            transport1.setText("        --      ");
            other1.setText("        --      ");
        }
        else{*/
        setRecord(status);
        //}


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*AddRecordDialog dialogFragment = new AddRecordDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("crop_id",crop_id1);
                bundle.putString("crop_name",crop_name1);
                bundle.putString("crop_variety",crop_variety1);
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"AddRecordDialog");*/
                Intent intent = new Intent(getApplicationContext(), ManageRecord.class);
                intent.putExtra("operation","add");
                intent.putExtra("id",crop_id1);
                intent.putExtra("crop_name",crop_name1);
                intent.putExtra("crop_variety", crop_variety1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("status",status_content1);
                getApplicationContext().startActivity(intent);
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManageRecord.class);
                intent.putExtra("operation","manage");
                intent.putExtra("id",crop_id1);
                intent.putExtra("crop_name",crop_name1);
                intent.putExtra("crop_variety", crop_variety1);
                intent.putExtra("area", Double.toString(area_harvested));
                intent.putExtra("ah_m", ah_measurement);
                intent.putExtra("area_planted", Double.toString(area_planted1));
                intent.putExtra("area_planted_m",ap_measurement);
                intent.putExtra("no_of_cavans_harvested", Integer.toString(no_of_cavans_harvested1));
                intent.putExtra("weight_per_cavan", Double.toString(weight_per_cavan1));
               /* intent.putExtra("seeds", Double.toString(seeds));
                intent.putExtra("irrig_fee", Double.toString(irrig_fee));
                intent.putExtra("fertilizer", Double.toString(fertilizer));
                intent.putExtra("pesticides", Double.toString(pesticides));
                intent.putExtra("labor", Double.toString(labor));
                intent.putExtra("rental", Double.toString(rental));
                intent.putExtra("transport", Double.toString(transport));
                intent.putExtra("other", Double.toString(other));*/
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("status",status_content1);
                getApplicationContext().startActivity(intent);
            }
        });


    }
    private void setRecord(String status) {

        Cursor dbres1 = helper.getRecordLog(crop_id1);
        if (dbres1.getCount() != 0) {

            if(status.equalsIgnoreCase("disable"))
            {
                fab.hide();
                fab2.hide();
            }
            else
            {
                fab.hide();
                fab2.show();
            }

            status_content.setText(" ");
            while (dbres1.moveToNext()) {
                String rid = String.format(dbres1.getString(0));
                String cid = String.format(dbres1.getString(1));
                area_harvested = Double.parseDouble(dbres1.getString(4));
                ah_measurement = String.format(dbres1.getString(5));
                area_planted1 = Double.parseDouble(dbres1.getString(2));
                ap_measurement = String.format(dbres1.getString(3));
                no_of_cavans_harvested1 = Integer.parseInt(dbres1.getString(6));
                weight_per_cavan1 = Double.parseDouble(dbres1.getString(7));
                areaHarvested.setText(df2.format(area_harvested) + " " + ah_measurement);
                area_planted.setText(df2.format(area_planted1) + " " + ap_measurement);
                no_of_cavans_harvested.setText(Integer.toString(no_of_cavans_harvested1));
                weight_per_cavan.setText((df2.format(weight_per_cavan1)));
                ave_weight1=weight_per_cavan1/no_of_cavans_harvested1;
                ave_weight.setText((df2.format(ave_weight1)));
               /* Cursor dbres2 = helper.getCostRecordLog(rid);
                while (dbres2.moveToNext()) {
                    seeds = Double.parseDouble(dbres2.getString(2));
                    irrig_fee = Double.parseDouble(dbres2.getString(3));
                    fertilizer = Double.parseDouble(dbres2.getString(4));
                    pesticides = Double.parseDouble(dbres2.getString(5));
                    labor = Double.parseDouble(dbres2.getString(6));
                    rental = Double.parseDouble(dbres2.getString(7));
                    transport = Double.parseDouble(dbres2.getString(8));
                    other = Double.parseDouble(dbres2.getString(9));
                    seeds1.setText(df2.format(seeds));
                    irrig_fee1.setText(df2.format(irrig_fee));
                    fertilizer1.setText(df2.format(fertilizer));
                    pesticides1.setText(df2.format(pesticides));
                    labor1.setText(df2.format(labor));
                    rental1.setText(df2.format(rental));
                    transport1.setText(df2.format(transport));
                    other1.setText(df2.format(other));
                    Double total = seeds + irrig_fee + fertilizer + pesticides + labor + rental + transport + other;
                    total1.setText("\u20B1" + df2.format(total));
                }*/
            }
        }
        else{

            if(status.equalsIgnoreCase("disable"))
            {
                fab.hide();
                fab2.hide();
            }
            else
            {
                fab.show();
                fab2.hide();
            }

            status_content.setText("No Record Yet");
            areaHarvested.setText("        --      ");
            area_planted.setText("        --      ");
            no_of_cavans_harvested.setText("        --      ");
            weight_per_cavan.setText("        --      ");
            ave_weight.setText("        --      ");
           /* seeds1.setText("        --      ");
            irrig_fee1.setText("        --      ");
            fertilizer1.setText("        --      ");
            pesticides1.setText("        --      ");
            labor1.setText("        --      ");
            rental1.setText("        --      ");
            transport1.setText("        --      ");
            other1.setText("        --      ");
            total1.setText("        --      ");*/
        }
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        setRecord(status);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
