package magrow.project.application.fragment;

import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import magrow.project.application.Agronomics;
import magrow.project.application.MainActivity;
import magrow.project.application.Message;
import magrow.project.application.R;
import magrow.project.application.myDbAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Crop_edit extends Fragment {

    String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

    myDbAdapter helper;
    Button update;
    RadioGroup crop,season;
    Spinner variety;
    EditText crop_name,other_variety;
    RadioButton onion,rice,wet,dry;
    String crop_type="rice";
    String season_type="wet";
    int variety_checker=0;
    int flag=0;
    String crop_name2="";
    TextView season_value;
    ImageButton back;

    public Crop_edit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_crop_edit,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle(" ");

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //((MainActivity)getActivity()).showUpButton();


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);

        helper = new myDbAdapter(getContext());

        update = (Button)view.findViewById(R.id.update);

        crop = (RadioGroup)view.findViewById(R.id.crop);
        season = (RadioGroup) view.findViewById(R.id.season);

        variety = (Spinner)view.findViewById(R.id.variety);

        onion = (RadioButton)view.findViewById(R.id.onion);
        rice = (RadioButton)view.findViewById(R.id.rice);
        wet = (RadioButton)view.findViewById(R.id.wet);
        dry = (RadioButton)view.findViewById(R.id.dry);

        crop_name = (EditText)view.findViewById(R.id.crop_name);
        other_variety = (EditText)view.findViewById(R.id.other_variety);

        season_value = (TextView) view.findViewById(R.id.season_value);
        back = (ImageButton)view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        final String crop_id= this.getArguments().getString("crop_id");

        final List<String> spinnerArray =  new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        variety.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String item = variety.getSelectedItem().toString();
                if(item.equalsIgnoreCase("Other"))
                {
                    //Message.message(getContext(),String.valueOf(item));
                    other_variety.setVisibility(View.VISIBLE);
                    variety_checker=1;
                    String cropname=setCropName(item);
                    crop_name.setText("");
                }
                else
                {
                    other_variety.setVisibility(View.GONE);
                    variety_checker=0;

                    if(flag==1)
                    {
                        showDialogChecker(item,crop_type);
                        String cropname=setCropName(item);
                        crop_name.setText(cropname);
                    }
                    flag=1;
                }


            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_type="onion";

                if(wet.isChecked())
                {
                    season_type="wet";
                }
                else
                {
                    season_type="dry";
                }

                spinnerArray.clear();
                spinnerArray.add("BGS 95");
                spinnerArray.add("Cal 120");
                spinnerArray.add("Cal 202");
                spinnerArray.add("Capri");
                spinnerArray.add("CX-12");
                spinnerArray.add("Grannex 429");
                spinnerArray.add("Hybrid Red Orient");
                spinnerArray.add("Liberty");
                spinnerArray.add("Red Creole");
                spinnerArray.add("Red Pinoy");
                spinnerArray.add("Rio Bravo");
                spinnerArray.add("Rio Hondo");
                spinnerArray.add("Rio Raji Red");
                spinnerArray.add("Rio Tinto");
                spinnerArray.add("Super Pinoy");
                spinnerArray.add("SuperX");
                spinnerArray.add("Texas Grano");
                spinnerArray.add("Yellow Grannex");
                spinnerArray.add("Other");


                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);

            }
        });

        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crop_type="rice";
                if(wet.isChecked())
                {
                    season_type="wet";
                    spinnerArray.clear();
                    spinnerArray.add("NSIC Rc9");
                    spinnerArray.add("NSIC Rc14");
                    spinnerArray.add("PSB Rc18");
                    spinnerArray.add("PSB Rc68");
                    spinnerArray.add("NSIC Rc194");
                    spinnerArray.add("NSIC Rc222");
                    spinnerArray.add("NSIC Rc300");
                    spinnerArray.add("NSIC Rc160");
                    spinnerArray.add("NSIC Rc238");
                    spinnerArray.add("NSIC Rc216");
                    spinnerArray.add("NSIC Rc402");
                    spinnerArray.add("NSIC Rc354");
                    spinnerArray.add("NSIC Rc218");
                    spinnerArray.add("PSB Rc10");
                    spinnerArray.add("Other");
                }
                else
                {
                    season_type="dry";
                    spinnerArray.clear();
                    spinnerArray.add("NSIC Rc160");
                    spinnerArray.add("NSIC Rc222");
                    spinnerArray.add("NSIC Rc238");
                    spinnerArray.add("NSIC Rc216");
                    spinnerArray.add("NSIC Rc402");
                    spinnerArray.add("NSIC Rc354");
                    spinnerArray.add("NSIC Rc218");
                    spinnerArray.add("PSB Rc10");
                    spinnerArray.add("Other");
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);
            }
        });

        dry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                season_type="dry";

                if(rice.isChecked())
                {
                    crop_type="rice";
                    spinnerArray.clear();
                    spinnerArray.add("NSIC Rc160");
                    spinnerArray.add("NSIC Rc222");
                    spinnerArray.add("NSIC Rc238");
                    spinnerArray.add("NSIC Rc216");
                    spinnerArray.add("NSIC Rc402");
                    spinnerArray.add("NSIC Rc354");
                    spinnerArray.add("NSIC Rc218");
                    spinnerArray.add("PSB Rc10");
                    spinnerArray.add("Other");
                }
                else
                {
                    crop_type="onion";
                    spinnerArray.clear();
                    spinnerArray.add("BGS 95");
                    spinnerArray.add("Cal 120");
                    spinnerArray.add("Cal 202");
                    spinnerArray.add("Capri");
                    spinnerArray.add("CX-12");
                    spinnerArray.add("Grannex 429");
                    spinnerArray.add("Hybrid Red Orient");
                    spinnerArray.add("Liberty");
                    spinnerArray.add("Red Creole");
                    spinnerArray.add("Red Pinoy");
                    spinnerArray.add("Rio Bravo");
                    spinnerArray.add("Rio Hondo");
                    spinnerArray.add("Rio Raji Red");
                    spinnerArray.add("Rio Tinto");
                    spinnerArray.add("Super Pinoy");
                    spinnerArray.add("SuperX");
                    spinnerArray.add("Texas Grano");
                    spinnerArray.add("Yellow Grannex");
                    spinnerArray.add("Other");

                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);

            }
        });

        wet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                season_type="wet";

                if(rice.isChecked())
                {
                    crop_type="rice";
                    spinnerArray.clear();
                    spinnerArray.add("NSIC Rc9");
                    spinnerArray.add("NSIC Rc14");
                    spinnerArray.add("PSB Rc18");
                    spinnerArray.add("PSB Rc68");
                    spinnerArray.add("NSIC Rc194");
                    spinnerArray.add("NSIC Rc222");
                    spinnerArray.add("NSIC Rc300");
                    spinnerArray.add("NSIC Rc160");
                    spinnerArray.add("NSIC Rc238");
                    spinnerArray.add("NSIC Rc216");
                    spinnerArray.add("NSIC Rc402");
                    spinnerArray.add("NSIC Rc354");
                    spinnerArray.add("NSIC Rc218");
                    spinnerArray.add("PSB Rc10");
                    spinnerArray.add("Other");
                }
                else
                {
                    crop_type="onion";
                    spinnerArray.clear();
                    spinnerArray.add("BGS 95");
                    spinnerArray.add("Cal 120");
                    spinnerArray.add("Cal 202");
                    spinnerArray.add("Capri");
                    spinnerArray.add("CX-12");
                    spinnerArray.add("Grannex 429");
                    spinnerArray.add("Hybrid Red Orient");
                    spinnerArray.add("Liberty");
                    spinnerArray.add("Red Creole");
                    spinnerArray.add("Red Pinoy");
                    spinnerArray.add("Rio Bravo");
                    spinnerArray.add("Rio Hondo");
                    spinnerArray.add("Rio Raji Red");
                    spinnerArray.add("Rio Tinto");
                    spinnerArray.add("Super Pinoy");
                    spinnerArray.add("SuperX");
                    spinnerArray.add("Texas Grano");
                    spinnerArray.add("Yellow Grannex");
                    spinnerArray.add("Other");

                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);
            }
        });


        String crop_type2="";
        String variety2="";
        String season2="";
        Cursor dbres = helper.getCropData(crop_id);
        while (dbres.moveToNext()) {

            crop_name2 = String.format(dbres.getString(1));
            crop_type2 = String.format(dbres.getString(2));
            variety2 = String.format(dbres.getString(3));
            season2 = String.format(dbres.getString(4));



        }

        crop_name.setText(crop_name2);

        switch(crop_type2){

            case "rice":
                crop_type="rice";
                spinnerArray.clear();
                spinnerArray.add(variety2);
                //rice.setChecked(true);
                String[] crop_rice = new String[14];
                if(season2.equals("wet"))
                {
                    season_type="wet";
                    //wet.setChecked(true);
                    season_value.setText("Wet Season "+year);
                    crop_rice[0]="NSIC Rc9";
                    crop_rice[1]="NSIC Rc14";
                    crop_rice[2]="PSB Rc18";
                    crop_rice[3]="PSB Rc68";
                    crop_rice[4]="NSIC Rc194";
                    crop_rice[5]="NSIC Rc222";
                    crop_rice[6]="NSIC Rc160";
                    crop_rice[7]="NSIC Rc238";
                    crop_rice[8]="NSIC Rc216";
                    crop_rice[9]="NSIC Rc402";
                    crop_rice[10]="NSIC Rc354";
                    crop_rice[11]="NSIC Rc218";
                    crop_rice[12]="NSIC Rc10";
                    crop_rice[13]="Other";
                }
                else
                {
                    season_type="dry";
                    //dry.setChecked(true);
                    season_value.setText("Dry Season "+year);
                    crop_rice[0]="NSIC Rc160";
                    crop_rice[1]="NSIC Rc222";
                    crop_rice[2]="NSIC Rc238";
                    crop_rice[3]="NSIC Rc216";
                    crop_rice[4]="NSIC Rc402";
                    crop_rice[5]="NSIC Rc354";
                    crop_rice[6]="NSIC Rc218";
                    crop_rice[7]="NSIC Rc10";
                    crop_rice[8]="Other";
                }


                for(int i=0;i<crop_rice.length;i++)
                {
                    if(crop_rice[i]!=null)
                    {
                        if(!variety2.equalsIgnoreCase(crop_rice[i]))
                        {
                            spinnerArray.add(crop_rice[i]);
                        }
                    }
                    else
                    {
                        break;
                    }




                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);

                break;

            case "onion":
                crop_type="onion";
                spinnerArray.clear();
                spinnerArray.add(variety2);
                onion.setChecked(true);

                if(season2.equals("wet"))
                {
                    season_type="wet";
                    wet.setChecked(true);
                }
                else
                {
                    season_type="dry";
                    dry.setChecked(true);

                }

                String[] crop_onion = new String[19];
                crop_onion[0]="BGS 95";
                crop_onion[1]="Cal 120";
                crop_onion[2]="Cal 202";
                crop_onion[3]="Capri";
                crop_onion[4]="CX-12";
                crop_onion[5]="Grannex 429";
                crop_onion[6]="Hybrid Red Orient";
                crop_onion[7]="Liberty";
                crop_onion[8]="Red Creole";
                crop_onion[9]="Red Pinoy";
                crop_onion[10]="Rio Bravo";
                crop_onion[11]="Rio Hondo";
                crop_onion[12]="Rio Raji Red";
                crop_onion[13]="Rio Tinto";
                crop_onion[14]="Super Pinoy";
                crop_onion[15]="SuperX";
                crop_onion[16]="Texas Grano";
                crop_onion[17]="Yellow Grannex";
                crop_onion[18]="Other";

                for(int i=0;i<crop_onion.length;i++)
                {
                    if(!variety2.equalsIgnoreCase(crop_onion[i]))
                    {
                        spinnerArray.add(crop_onion[i]);
                    }
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                variety.setAdapter(adapter);

                break;


        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crop_name_a = crop_name.getText().toString();
                String crop_variety ="";
                Boolean flag=true;
                if(variety_checker==0)
                {
                    crop_variety = variety.getSelectedItem().toString();
                }
                else
                {
                    crop_variety = other_variety.getText().toString();
                }

                Cursor dbres = helper.getCropIdUpdate(crop_name_a,crop_id);
                if(dbres.getCount() >=1)
                {

                   // Snackbar.make(findViewById(android.R.id.content),"Crop name already exists",Snackbar.LENGTH_SHORT).show();

                    Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                            "Crop name already exists", Snackbar.LENGTH_LONG);
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                            snack.getView().getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    snack.getView().setLayoutParams(params);
                    snack.show();
                    flag=false;

                }

                else if(crop_name_a.isEmpty())
                {
                    Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                            "Crop name cannot be empty", Snackbar.LENGTH_LONG);
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                            snack.getView().getLayoutParams();
                    params.setMargins(0, 0, 0, 0);
                    snack.getView().setLayoutParams(params);
                    snack.show();
                    flag=false;
                }

                else if(variety_checker!=0)
                {
                    if(other_variety.getText().toString().equals(""))
                    {
                        showSnackBar("Please enter crop variety");
                        flag=false;
                    }

                }

                if(flag)
                {
                    try
                    {
                        long result = helper.updateCrop(crop_id,crop_name_a,crop_type,crop_variety,season_type);

                        if(result != -1)
                        {
                           /* Intent intent = new Intent(getApplicationContext(),Crop_pane.class);
                            intent.putExtra("crop_type",crop_type);
                            intent.putExtra("status","success");
                            startActivity(intent);
                            finish();*/

                            Bundle bundle = new Bundle();
                            bundle.putString("crop_type",crop_type);
                            bundle.putString("status","success");// set your parameteres

                            Crops nextFragment = new Crops();
                            nextFragment.setArguments(bundle);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                fragmentManager.addToBackStack(null);*/


                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                            // This action will remove Fragment one and add Fragment two.
                            fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Four");

                            // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.

                            fragmentTransaction.commit();


                        }

                        else
                        {
                            Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                    "Updating Failed", Snackbar.LENGTH_LONG);
                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                    snack.getView().getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            snack.getView().setLayoutParams(params);
                            snack.show();
                        }
                           // Snackbar.make(findViewById(android.R.id.content),"Updating Failed",Snackbar.LENGTH_SHORT).show();




                    }
                    catch (Exception e)
                    {
                        Message.message(getContext(),e.getMessage());
                    }

                }



            }
        });











        return view;
    }

    public void showDialogChecker(String variety,String crop_type)
    {

        Agronomics dialog2 = new Agronomics();
        Bundle args = new Bundle();
        args.putString("variety", variety);
        args.putString("crop_type", crop_type);
        dialog2.setTargetFragment(Crop_edit.this,0);
        dialog2.setArguments(args);
        dialog2.show(getFragmentManager(),"SeedMaturityDialog");
        dialog2.setCancelable(true);

    }

    public String setCropName(String breed)
    {
        String name="";
        switch (breed)
        {
            case "NSIC Rc160":
                name="Tubigan 14";
                break;

            case "NSIC Rc222":
                name="Tubigan 18";
                break;

            case "NSIC Rc238":
                name="Tubigan 21";
                break;

            case "NSIC Rc216":
                name="Tubigan 17";
                break;

            case "NSIC Rc9":
                name="Apo";
                break;

            case "NSIC Rc14":
                name="Rio Grande";
                break;

            case "NSIC Rc18":
                name="Ala";
                break;

            case "NSIC Rc68":
                name="Sacobia";
                break;

            case "NSIC Rc194":
                name="Submarino 1";
                break;

            case "NSIC Rc300":
                name="Tubigan 24";
                break;

            case "NSIC Rc402":
                name="Tubigan 36";
                break;

            case "NSIC Rc354":
                name="Tubigan 28";
                break;

            case "NSIC Rc218":
                name="Mabango 3";
                break;

            case "PSB Rc10":
                name="Pagsanjan";
                break;

            case "BGS 95":
                name="F1 Hybrid";
                break;

            default:
                name=breed;
                break;

        }


        return name;
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

}
