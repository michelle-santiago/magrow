package magrow.project.application;

import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import magrow.project.application.fragment.Home;

public class FarmDetails extends AppCompatActivity {

    Spinner location,la_measurement;
    EditText farm_name,farm_size;
    Button done;
    myDbAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new myDbAdapter(getApplicationContext());
        location= (Spinner)findViewById(R.id.location);
        la_measurement= (Spinner)findViewById(R.id.la_measurement);
        farm_name=(EditText)findViewById(R.id.farm_name);
        farm_size=(EditText)findViewById(R.id.farm_size);

        done=(Button)findViewById(R.id.done);


        final List<String> measurement =  new ArrayList<String>();
        measurement.add("- Select Unit of Measure -");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.spinner_item, measurement);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        la_measurement.setAdapter(adapter);


        //POPULATE LOCATION
        final List<String> locations =  new ArrayList<String>();
        locations.add("- Select Location -");

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.spinner_item, locations);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        location.setAdapter(adapter2);


        Cursor dbres2 = helper.getFarmDetails();
        while (dbres2.moveToNext()) {


            String fname = String.format(dbres2.getString(1));
            String flocation = String.format(dbres2.getString(2));
            String farea = String.format(dbres2.getString(3));
            String fmeasure = String.format(dbres2.getString(4));
            farm_name.setText(fname);
            farm_size.setText(farea);


            //POPULATE LA_MEASUREMENT
            measurement.clear();
            String[] measure = new String[2];
            measure[0]="Ha";
            measure[1]="m²";

            measurement.add(fmeasure);

            for(int i=0;i<measure.length;i++)
            {
                if(!fmeasure.equalsIgnoreCase(measure[i]))
                {
                    measurement.add(measure[i]);
                }
            }

            adapter.setDropDownViewResource(R.layout.spinner_item);
            la_measurement.setAdapter(adapter);


            //POPULATE LOCATION
            locations.clear();
            String[] nlocation = new String[32];
            nlocation[0]="ALIAGA";
            nlocation[1]="BONGABON";
            nlocation[2]="CABANATUAN CITY";
            nlocation[3]="CABIAO";
            nlocation[4]="CARRANGLAN";
            nlocation[5]="CUYAPO";
            nlocation[6]="GABALDON";
            nlocation[7]="GAPAN";
            nlocation[8]="GENERAL NATIVIDAD";
            nlocation[9]="GENERAL TINIO";
            nlocation[10]="GUIMBA";
            nlocation[11]="JAEN";
            nlocation[12]="LAUR";
            nlocation[13]="LICAB";
            nlocation[14]="LLANERA";
            nlocation[15]="LUPAO";
            nlocation[16]="MUÑOZ";
            nlocation[17]="NAMPICUAN";
            nlocation[18]="PALAYAN";
            nlocation[19]="PANTABANGAN";
            nlocation[20]="PEÑARANDA";
            nlocation[21]="QUEZON";
            nlocation[22]="RIZAL";
            nlocation[23]="SAN ANTONIO";
            nlocation[24]="SAN ISIDRO";
            nlocation[25]="SAN JOSE CITY";
            nlocation[26]="SAN LEONARDO";
            nlocation[27]="SANTA ROSA";
            nlocation[28]="SANTO DOMINGO";
            nlocation[29]="TALAVERA";
            nlocation[30]="TALUGTUG";
            nlocation[31]="ZARAGOZA";

            locations.add(flocation);
            for(int i=0;i<nlocation.length;i++)
            {
                if(!flocation.equalsIgnoreCase(nlocation[i]))
                {
                    locations.add(nlocation[i]);
                }
            }

            adapter2.setDropDownViewResource(R.layout.spinner_item);
            location.setAdapter(adapter2);
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname=farm_name.getText().toString();
                String fsize=farm_size.getText().toString();
                String flocation=location.getSelectedItem().toString();
                String fmeasurement=la_measurement.getSelectedItem().toString();

                if(fname.isEmpty()||fsize.isEmpty())
                {
                    Snackbar.make(v, "Please complete your details", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                }

                else
                {
                    double size_checker=Double.parseDouble(fsize);
                    if(size_checker<=0)
                    {

                        Snackbar.make(v, "Invalid farm size value", Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
                    }

                    else
                    {
                        int a= helper.updateFarmDetails(Home.id,fname,flocation,fsize,fmeasurement);
                        if(a<=0)
                        {

                            Snackbar.make(v, "Unsuccessful", Snackbar.LENGTH_LONG)
                                    .setAction("No action", null).show();

                        }
                        else
                        {
                            Snackbar.make(v, "Farm details Updated", Snackbar.LENGTH_LONG)
                                    .setAction("No action", null).show();

                            Home.location=locationKeys(flocation);


                        }
                    }

                }

                //Message.message(getContext(),fname+" "+flocation+" "+fsize+" "+fmeasurement);





            }
        });






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

    public boolean onOptionsItemSelected(MenuItem item){

        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}
