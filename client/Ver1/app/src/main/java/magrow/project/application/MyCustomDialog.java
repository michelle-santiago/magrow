package magrow.project.application;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import magrow.project.application.R;

public class MyCustomDialog extends DialogFragment {



    private static final String TAG = "MyCustomDialog";


    public interface OnInputSelected{
        void sendInput(String input,String input2);
    }
    public OnInputSelected mOnInputSelected;

    myDbAdapter helper;

    //widgets
    private EditText mInput;
    private TextView mActionOk, mActionCancel;
    Spinner location,la_mesurement;
    Button done;
    EditText farm_name,land_area;


    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_dialog, container, false);

        helper = new myDbAdapter(getActivity());
        farm_name=(EditText)view.findViewById(R.id.farm_name);
        land_area=(EditText)view.findViewById(R.id.land_area);

        location= (Spinner)view.findViewById(R.id.location);

        la_mesurement= (Spinner)view.findViewById(R.id.la_measurement);
        done= (Button)view.findViewById(R.id.done);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        la_mesurement.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setAdapter(adapter2);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String farmName=farm_name.getText().toString();
                String farmLocation=location.getSelectedItem().toString();
                String farmlandArea=land_area.getText().toString();
                String farmMeasurement=la_mesurement.getSelectedItem().toString();


                if(farmName.isEmpty()||farmLocation.equals("--Select Location--")||farmlandArea.isEmpty())
                {
                    Toast.makeText(getActivity(),"Please Complete the details",Toast.LENGTH_LONG).show();
                }
                else
                {
                    long result = helper.insertFarmDetails(farmName,farmLocation,farmlandArea,farmMeasurement);

                    if(result != -1) {

                        //Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                        mOnInputSelected.sendInput(farmName,farmLocation);
                        getDialog().dismiss();

                        Welcome_dialog dialog = new Welcome_dialog();
                        dialog.setTargetFragment(MyCustomDialog.this,0);
                        dialog.show(getFragmentManager(),"WelcomeDialog");
                        dialog.setCancelable(true);


                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();

                    }
                }


            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }


}
