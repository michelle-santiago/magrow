package magrow.project.application;


import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import magrow.project.application.R;

import java.util.ArrayList;

public class Crop_log extends AppCompatActivity {


    myDbAdapter helper;

    ArrayList<DataModelCropLog> dataModels;
    ListView listView;
    TextView crop_label;
    private static CustomAdapterCropLog adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_log);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        helper = new myDbAdapter(getApplicationContext());
        Intent intent = getIntent();
        String id_crop=intent.getStringExtra("crop_id");
        String name_crop=intent.getStringExtra("crop_name");
        String variety_crop=intent.getStringExtra("crop_variety");
        //Toast.makeText(getApplicationContext(),id_crop,Toast.LENGTH_LONG).show();

        crop_label=(TextView)findViewById(R.id.crop_label);
        listView=(ListView)findViewById(R.id.crop_log_list);

        dataModels= new ArrayList<>();

        crop_label.setText(name_crop+" ("+variety_crop+")");

        Cursor dbres2 = helper.getEventLog(id_crop);
            String timestamp_crop="";
            String date_crop="";
            String time_crop="";
            String operation="";
            String operation_status="";
            String operation_name="";

            if(dbres2.getCount()==0)
            {
                Snackbar.make(findViewById(android.R.id.content),"No crop activities yet",Snackbar.LENGTH_SHORT).show();
            }
            else
            {
                while (dbres2.moveToNext()) {

                    date_crop = String.format(dbres2.getString(4));
                    time_crop = String.format(dbres2.getString(5));
                    operation= String.format(dbres2.getString(7));
                    operation_name= String.format(dbres2.getString(3));
                    operation_status = String.format(dbres2.getString(10));

                    dataModels.add(new DataModelCropLog(date_crop+" "+time_crop,operation,operation_status,operation_name));

                }
            }
            //Message.message(getApplicationContext(),operation_status);





        adapter= new CustomAdapterCropLog(dataModels,getApplicationContext());
        listView.setAdapter(adapter);

    }

    public boolean onOptionsItemSelected(MenuItem item){

        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();

    }


}
