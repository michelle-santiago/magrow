package magrow.project.application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ManageRecord extends AppCompatActivity {

    TextView crop_name,crop_variety;
    Button add,update,delete;
    myDbAdapter helper;
    EditText area,area_planted,no_of_cavans_harvested,weight_per_cavan/*,seeds,irrig_fee,fertilizer,pesticides,labor,rental,transport,other*/;
    String area1,area_planted1,no_of_cavans_harvested1,weight_per_cavan1/*,seeds1,irrig_fee1,fertilizer1,pesticides1,labor1,rental1,transport1,other1*/;
    int no_of_cavans_harvested2,no_of_cavans_harvested3;
    Double area2,area_planted2,weight_per_cavan2/*,seeds2,irrig_fee2,fertilizer2,pesticides2,labor2,rental2,transport2,other2*/;
    Double area3,area_planted3,weight_per_cavan3/*,seeds3,irrig_fee3,fertilizer3,pesticides3,labor3,rental3,transport3,other3*/;
    int crop_id;
    TextInputLayout etAreaLayout, etNo_of_CavansLayout,etArea_PlantedLayout,etWeight_per_CavanLayout/*, etSeedsLayout,etIrrig_feeLayout,etfFertilizerLayout,etPesticidesLayout,etLaborLayout,etRentalLayout,etTransportLayout,etOtherLayout*/;
    Spinner ah_measurement,ap_measurement;
    String crop_name1,crop_variety1,status_content1,ah_Measurement,ap_Measurement,rid;
    String crop_id1,operation;
    private static final String TAG = "AddRecordDialog";
    private static DecimalFormat df2 = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_record);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        helper = new myDbAdapter(getApplicationContext());
        crop_name = (TextView)findViewById(R.id.crop_name);
        crop_variety = (TextView)findViewById(R.id.crop_variety);
        add = (Button)findViewById(R.id.add);
        update = (Button)findViewById(R.id.update);
        delete = (Button)findViewById(R.id.delete);
        //status_content = (TextView)findViewById(R.id.status_content);
        ah_measurement= (Spinner)findViewById(R.id.ah_measurement);
        ap_measurement= (Spinner)findViewById(R.id.ap_measurement);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ah_measurement.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ap_measurement, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ap_measurement.setAdapter(adapter3);
        area = findViewById(magrow.project.application.R.id.area);
        area_planted = findViewById(magrow.project.application.R.id.area_planted);
        no_of_cavans_harvested=findViewById(magrow.project.application.R.id.no_of_cavans_harvested);
        weight_per_cavan = findViewById(magrow.project.application.R.id.weight_per_cavan);
        /*seeds = findViewById(com.example.myapplication.R.id.seeds);
        irrig_fee = findViewById(com.example.myapplication.R.id.irrig_fee);
        fertilizer = findViewById(com.example.myapplication.R.id.fertilizer);
        pesticides = findViewById(com.example.myapplication.R.id.pesticides);
        labor = findViewById(com.example.myapplication.R.id.labor);
        rental = findViewById(com.example.myapplication.R.id.rental);
        transport = findViewById(com.example.myapplication.R.id.transport);
        other = findViewById(com.example.myapplication.R.id.other);*/
        etAreaLayout=findViewById(R.id.etAreaLayout);
        etArea_PlantedLayout=findViewById(R.id.etArea_PlantedLayout);
        etNo_of_CavansLayout=findViewById(R.id.etNo_of_CavansLayout);
        etWeight_per_CavanLayout=findViewById(R.id.etWeight_per_CavanLayout);
        /*etSeedsLayout=findViewById(R.id.etSeedsLayout);
        etIrrig_feeLayout=findViewById(R.id.etIrrig_feeLayout);
        etfFertilizerLayout=findViewById(R.id.etFertilizerLayout);
        etPesticidesLayout=findViewById(R.id.etPesticidesLayout);
        etLaborLayout=findViewById(R.id.etLaborLayout);
        etRentalLayout=findViewById(R.id.etRentalLayout);
        etTransportLayout=findViewById(R.id.etTransportLayout);
        etOtherLayout=findViewById(R.id.etOtherLayout);*/
        Intent intent = getIntent();
        operation=intent.getStringExtra("operation");
        crop_id1=intent.getStringExtra("id");
        crop_id=Integer.parseInt(crop_id1);
        crop_name1=intent.getStringExtra("crop_name");
        crop_variety1=intent.getStringExtra("crop_variety");
        crop_name.setText(crop_name1);
        crop_variety.setText(crop_variety1);
        if(operation.equals("manage")){
            add.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            Double area_i=Double.parseDouble(intent.getStringExtra("area"));
            area.setText(df2.format(area_i));
            ah_measurement.setSelection(adapter.getPosition(intent.getStringExtra("ah_m")));
            Double area_planted_i=Double.parseDouble(intent.getStringExtra("area_planted"));
            ap_measurement.setSelection(adapter3.getPosition(intent.getStringExtra("area_planted_m")));
            area_planted.setText(df2.format(area_planted_i));
            int no_of_cavans_harvested_i=Integer.parseInt(intent.getStringExtra("no_of_cavans_harvested"));
            no_of_cavans_harvested.setText(Integer.toString(no_of_cavans_harvested_i));
            Double weight_per_cavan_i=Double.parseDouble(intent.getStringExtra("weight_per_cavan"));
            weight_per_cavan.setText(df2.format(weight_per_cavan_i));
            /*Double seeds_i=Double.parseDouble(intent.getStringExtra("seeds"));
            seeds.setText(df2.format(seeds_i));
            Double irrig_fee_i=Double.parseDouble(intent.getStringExtra("irrig_fee"));
            irrig_fee.setText(df2.format(irrig_fee_i));
            Double fertilizer_i=Double.parseDouble(intent.getStringExtra("fertilizer"));
            fertilizer.setText(df2.format(fertilizer_i));
            Double pesticides_i=Double.parseDouble(intent.getStringExtra("pesticides"));
            pesticides.setText(df2.format(pesticides_i));
            Double labor_i=Double.parseDouble(intent.getStringExtra("labor"));
            labor.setText(df2.format(labor_i));
            Double rental_i=Double.parseDouble(intent.getStringExtra("rental"));
            rental.setText(df2.format(rental_i));
            Double transport_i=Double.parseDouble(intent.getStringExtra("transport"));
            transport.setText(df2.format(transport_i));
            Double other_i=Double.parseDouble(intent.getStringExtra("other"));
            other.setText(df2.format(other_i));*/

        }
        else {
            add.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            area.setText("0.00");
            area_planted.setText("0.00");
            no_of_cavans_harvested.setText("0");
            weight_per_cavan.setText("0.00");
            /*seeds.setText("0.00");
            irrig_fee.setText("0.00");
            fertilizer.setText("0.00");
            pesticides.setText("0.00");
            labor.setText("0.00");
            rental.setText("0.00");
            transport.setText("0.00");
            other.setText("0.00");*/

        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();
                if(validate(area1,area_planted1,no_of_cavans_harvested1,weight_per_cavan1/*,seeds1,irrig_fee1,fertilizer1,pesticides1,labor1,rental1,transport1,other1*/)){
                    parseDouble();
                    try
                    {
                        long result = helper.insertRecordLog(crop_id,area3,ah_Measurement,area_planted3,ap_Measurement,no_of_cavans_harvested3,weight_per_cavan3);
                        if(result != -1)
                        {
                            String c_id=Integer.toString(crop_id);
                            Cursor dbres1 = helper.getRecordLog(c_id);
                            if(dbres1.getCount() != 0) {
                                while (dbres1.moveToNext()) {
                                    //String rid = String.format(dbres1.getString(0));
                                    //int crid=Integer.parseInt(rid);
                                    //long result2 = helper.insertCostRecordLog(crid,seeds3,irrig_fee3,fertilizer3,pesticides3,labor3,rental3,transport3,other3);
                                    //if(result2 != -1)
                                    //{
                                        add.setVisibility(View.GONE);
                                        area.setText(df2.format(area3));
                                        area_planted.setText(df2.format(area_planted3));
                                        no_of_cavans_harvested.setText(Integer.toString(no_of_cavans_harvested3));
                                        weight_per_cavan.setText(df2.format(weight_per_cavan3));
                                       /* seeds.setText(df2.format(seeds3));
                                        irrig_fee.setText(df2.format(irrig_fee3));
                                        fertilizer.setText(df2.format(fertilizer3));
                                        pesticides.setText(df2.format(pesticides3));
                                        labor.setText(df2.format(labor3));
                                        rental.setText(df2.format(rental3));
                                        transport.setText(df2.format(transport3));
                                        other.setText(df2.format(other3));*/
                                        update.setVisibility(View.VISIBLE);
                                        delete.setVisibility(View.VISIBLE);

                                        Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                "Success", Snackbar.LENGTH_LONG);
                                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                snack.getView().getLayoutParams();
                                        params.setMargins(0, 0, 0, 0);
                                        snack.getView().setLayoutParams(params);
                                        snack.show();
                                    }
                                   /* else{
                                        Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                "Failed", Snackbar.LENGTH_LONG);
                                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                snack.getView().getLayoutParams();
                                        params.setMargins(0, 0, 0, 0);
                                        snack.getView().setLayoutParams(params);
                                        snack.show();
                                    }*/
                               // }
                            }
                        }
                        else
                        {
                            Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                    "Failed", Snackbar.LENGTH_LONG);
                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                    snack.getView().getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            snack.getView().setLayoutParams(params);
                            snack.show();
                        }

                    }
                    catch (Exception e)
                    {
                        Message.message(getApplicationContext(),e.getMessage());
                    }
                }



            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getText();
                if(validate(area1,area_planted1,no_of_cavans_harvested1,weight_per_cavan1/*,seeds1,irrig_fee1,fertilizer1,pesticides1,labor1,rental1,transport1,other1*/)){
                    parseDouble();
                    try
                    {
                        String c_id=Integer.toString(crop_id);
                        Cursor dbres1 = helper.getRecordLog(c_id);
                        if(dbres1.getCount() != 0) {
                            while (dbres1.moveToNext()) {
                               String rid = String.format(dbres1.getString(0));
                                long result = helper.updateRecordLog(rid,crop_id,area_planted3,ap_Measurement,area3,ah_Measurement,no_of_cavans_harvested3,weight_per_cavan3);
                                if(result != -1)
                                {

                                    //long result2 = helper.updateCostRecordLog(rid,seeds3,irrig_fee3,fertilizer3,pesticides3,labor3,rental3,transport3,other3);
                                    //if(result2 != -1)
                                    //{
                                        add.setVisibility(View.GONE);
                                        area.setText(df2.format(area3));
                                        area_planted.setText(df2.format(area_planted3));
                                        no_of_cavans_harvested.setText(Integer.toString(no_of_cavans_harvested3));
                                        weight_per_cavan.setText(df2.format(weight_per_cavan3));
                                        /*seeds.setText(df2.format(seeds3));
                                        irrig_fee.setText(df2.format(irrig_fee3));
                                        fertilizer.setText(df2.format(fertilizer3));
                                        pesticides.setText(df2.format(pesticides3));
                                        labor.setText(df2.format(labor3));
                                        rental.setText(df2.format(rental3));
                                        transport.setText(df2.format(transport3));
                                        other.setText(df2.format(other3));*/
                                        update.setVisibility(View.VISIBLE);
                                        delete.setVisibility(View.VISIBLE);

                                        Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                "Success", Snackbar.LENGTH_LONG);
                                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                snack.getView().getLayoutParams();
                                        params.setMargins(0, 0, 0, 0);
                                        snack.getView().setLayoutParams(params);
                                        snack.show();
                                    }
                                   /* else{
                                        Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                "Failed", Snackbar.LENGTH_LONG);
                                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                snack.getView().getLayoutParams();
                                        params.setMargins(0, 0, 0, 0);
                                        snack.getView().setLayoutParams(params);
                                        snack.show();
                                    }*/

                               // }
                            }
                        }


                    }

                    catch (Exception e)
                    {
                        Message.message(getApplicationContext(),e.getMessage());
                    }
                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ManageRecord.this);

                alertDialogBuilder.setTitle("Delete");

                alertDialogBuilder
                        .setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Confirm",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                try{
                                    String c_id=Integer.toString(crop_id);
                                    Cursor dbres1 = helper.getRecordLog(c_id);
                                    if(dbres1.getCount() != 0) {
                                        while (dbres1.moveToNext()) {
                                            String rid = String.format(dbres1.getString(0));
                                            long result = helper.deleteRecordLog(rid);
                                            if(result != -1)
                                            {
                                               // long result1 = helper.deleteCostRecordLog(rid);
                                                //if(result1 != -1)
                                               // {
                                                    Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                            "Record Deleted", Snackbar.LENGTH_LONG);
                                                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                            snack.getView().getLayoutParams();
                                                    params.setMargins(0, 0, 0, 0);
                                                    snack.getView().setLayoutParams(params);
                                                    snack.show();
                                                    add.setVisibility(View.VISIBLE);
                                                    update.setVisibility(View.GONE);
                                                    delete.setVisibility(View.GONE);
                                                    area.setText("0.00");
                                                    area_planted.setText("0.00");
                                                    no_of_cavans_harvested.setText("0");
                                                    weight_per_cavan.setText("0.00");
                                                    /*seeds.setText("0.00");
                                                    irrig_fee.setText("0.00");
                                                    fertilizer.setText("0.00");
                                                    pesticides.setText("0.00");
                                                    labor.setText("0.00");
                                                    rental.setText("0.00");
                                                    transport.setText("0.00");
                                                    other.setText("0.00");*/
                                                }
                                                /*else{
                                                    Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                            "Failed", Snackbar.LENGTH_LONG);
                                                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                            snack.getView().getLayoutParams();
                                                    params.setMargins(0, 0, 0, 0);
                                                    snack.getView().setLayoutParams(params);
                                                    snack.show();
                                                }*/
                                           // }
                                            else{
                                                Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                                        "Failed", Snackbar.LENGTH_LONG);
                                                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                        snack.getView().getLayoutParams();
                                                params.setMargins(0, 0, 0, 0);
                                                snack.getView().setLayoutParams(params);
                                                snack.show();
                                            }
                                        }
                                    }
                                }
                                catch (Exception e)
                                {
                                    Message.message(getApplicationContext(),e.getMessage());
                                }

                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();





            }

        });
    }
    private boolean validate(String area,String area_planted,String no_of_cavans_harvested,String weight_per_cavan/*,String seeds,String irrig_fee,String fertilizer,String pesticides,String labor,String rental,String transport,String other*/) {

        // Reset errors.
        etAreaLayout.setError(null);
        etArea_PlantedLayout.setError(null);
        etNo_of_CavansLayout.setError(null);
        etWeight_per_CavanLayout.setError(null);
       /* etSeedsLayout.setError(null);
        etIrrig_feeLayout.setError(null);
        etfFertilizerLayout.setError(null);
        etPesticidesLayout.setError(null);
        etLaborLayout.setError(null);
        etRentalLayout.setError(null);
        etTransportLayout.setError(null);
        etOtherLayout.setError(null);*/


        if (Utils.isEmpty(area)) {
            area1="0.00";
        }
        if (Utils.isEmpty(area_planted)) {
            area_planted1="0.00";
        }
        if (Utils.isEmpty(no_of_cavans_harvested)) {
            no_of_cavans_harvested1="0";
        }
        if (Utils.isEmpty(weight_per_cavan)) {
            weight_per_cavan1="0.00";
        }
       /* if (Utils.isEmpty(seeds)) {
            seeds1="0.00";
        }
        if (Utils.isEmpty(irrig_fee)) {
            irrig_fee1="0.00";
        }
        if (Utils.isEmpty(fertilizer)) {
            fertilizer1="0.00";
        }
        if (Utils.isEmpty(pesticides)) {
            pesticides1="0.00";
        }
        if (Utils.isEmpty(labor)) {
            labor1="0.00";
        }
        if (Utils.isEmpty(rental)) {
            rental1="0.00";
        }
        if (Utils.isEmpty(transport)) {
            transport1="0.00";
        }
        if (Utils.isEmpty(other)) {
            other1="0.00";
        }*/
        else if (Utils.isPeriod(area)) {
            etAreaLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(area_planted)) {
            etArea_PlantedLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(no_of_cavans_harvested)) {
            etNo_of_CavansLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(weight_per_cavan)) {
            etWeight_per_CavanLayout.setError("Invalid");
            return false;
        }
        /*else if (Utils.isPeriod(seeds)) {
            etSeedsLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(irrig_fee)) {
            etIrrig_feeLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(fertilizer)) {
            etfFertilizerLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(pesticides)) {
            etPesticidesLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(labor)) {
            etLaborLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(rental)) {
            etRentalLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(transport)) {
            etTransportLayout.setError("Invalid");
            return false;
        }
        else if (Utils.isPeriod(other)) {
            etOtherLayout.setError("Invalid");
            return false;
        }*/
        return true;
    }

    private void getText() {
        area1=area.getText().toString();
        area_planted1=area_planted.getText().toString();
        no_of_cavans_harvested1=no_of_cavans_harvested.getText().toString();
        weight_per_cavan1=weight_per_cavan.getText().toString();
       /* seeds1=seeds.getText().toString();
        irrig_fee1=irrig_fee.getText().toString();
        fertilizer1=fertilizer.getText().toString();
        pesticides1=pesticides.getText().toString();
        labor1=labor.getText().toString();
        rental1=rental.getText().toString();
        transport1=transport.getText().toString();
        other1=other.getText().toString();*/
    }
    public void parseDouble(){
        area2=Double.parseDouble(area1);
        String area_f=df2.format(area2);
        area3=Double.parseDouble(area_f);
        ah_Measurement=ah_measurement.getSelectedItem().toString();
        area_planted2 = Double.parseDouble(area_planted1);
        String area_planted_f=df2.format(area_planted2);
        area_planted3=Double.parseDouble(area_planted_f);
        ap_Measurement=ap_measurement.getSelectedItem().toString();
        no_of_cavans_harvested2=Integer.parseInt(no_of_cavans_harvested1);
        //String no_of_cavans_f=no_of_cavans_harvested2.to
        no_of_cavans_harvested3=no_of_cavans_harvested2;
        weight_per_cavan2=Double.parseDouble(weight_per_cavan1);
        String weight_per_cavan_f=df2.format(weight_per_cavan2);
        weight_per_cavan3=Double.parseDouble(weight_per_cavan_f);
        /*seeds2 = Double.parseDouble(seeds1);
        String seeds_f=df2.format(seeds2);
        seeds3=Double.parseDouble(seeds_f);
        irrig_fee2 =Double.parseDouble(irrig_fee1);
        String irrig_f=df2.format(irrig_fee2);
        irrig_fee3=Double.parseDouble(irrig_f);
        fertilizer2 = Double.parseDouble(fertilizer1);
        String fertilizer_f=df2.format(fertilizer2);
        fertilizer3=Double.parseDouble(fertilizer_f);
        pesticides2 = Double.parseDouble(pesticides1);
        String pesticides_f=df2.format(pesticides2);
        pesticides3=Double.parseDouble(pesticides_f);
        labor2 = Double.parseDouble(labor1);
        String labor_f=df2.format(labor2);
        labor3=Double.parseDouble(labor_f);
        rental2 = Double.parseDouble(rental1);
        String rental_f=df2.format(rental2);
        rental3=Double.parseDouble(rental_f);
        transport2 = Double.parseDouble(transport1);
        String transport_f=df2.format(transport2);
        transport3=Double.parseDouble(transport_f);
        other2 = Double.parseDouble(other1);
        String other_f=df2.format(other2);
        other3=Double.parseDouble(other_f);*/
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
