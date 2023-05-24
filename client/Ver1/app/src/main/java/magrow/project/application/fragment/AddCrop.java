package magrow.project.application.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import magrow.project.application.Agronomics;
import magrow.project.application.MainActivity;
import magrow.project.application.Message;
import magrow.project.application.R;
import magrow.project.application.myDbAdapter;

import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddCrop extends Fragment {

    String date_season = new SimpleDateFormat("MMM", Locale.getDefault()).format(new Date());
    String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
    String date_added = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    myDbAdapter helper;
    Button add_crop,view_crop;
    RadioGroup crop,season;
    Spinner variety;
    EditText crop_name,other_variety;
    RadioButton onion,rice,wet,dry;
    String crop_type="";
    String season_type="";
    TextView season_txt,crop_typef;
    ImageView crop_icon;
    ImageButton back;
    int variety_checker=0;
    public AddCrop() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_add_crop,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle(" ");

        //((AppCompatActivity) getActivity()).getSupportActionBar().show();

        //((MainActivity)getActivity()).showUpButton();


         getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);


        helper = new myDbAdapter(getContext());

        crop_name = (EditText)view.findViewById(R.id.crop_name);
        other_variety = (EditText)view.findViewById(R.id.other_variety);

        add_crop = (Button)view.findViewById(R.id.add_crop);
        //view_crop = (Button)view.findViewById(R.id.button3);
        crop = (RadioGroup)view.findViewById(R.id.crop);
        variety = (Spinner)view.findViewById(R.id.variety);

        wet = (RadioButton)view.findViewById(R.id.wet);
        dry = (RadioButton)view.findViewById(R.id.dry);

        season_txt= (TextView) view.findViewById(R.id.season_txt);
        String temp_season=setSeason_txt(date_season);

        crop_icon=(ImageView)view.findViewById(R.id.crop_icon);

        back = (ImageButton)view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        season_txt.setText(temp_season+" "+year);
        season_type=setSeasonvar(date_season);

        crop_type= this.getArguments().getString("crop");
        //Message.message(getContext(),crop_type);

        final List<String> spinnerArray =  new ArrayList<String>();

        if(crop_type.equalsIgnoreCase("rice"))
        {
            crop_icon.setImageResource(R.drawable.ricev2);
            if(season_type.equalsIgnoreCase("wet"))
            {
                spinnerArray.add("--Select variety--");
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


            else if(season_type.equalsIgnoreCase("dry"))
            {
                spinnerArray.add("--Select variety--");
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
        }

        else
        {

            crop_icon.setImageResource(R.drawable.onionv2);
            spinnerArray.clear();
            spinnerArray.add("--Select variety--");
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


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        variety.setAdapter(adapter);

        /*view_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = helper.getDataCrop();
                Message.message(getContext(),data);

            }
        });*/

        variety.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                String item = variety.getSelectedItem().toString();
                if(item.equalsIgnoreCase("Other"))
                {
                    //Message.message(getContext(),String.valueOf(item));
                    other_variety.setVisibility(View.VISIBLE);
                    variety_checker=1;
                    String cropname=setCropName(item);
                    if(!cropname.equalsIgnoreCase("none"))
                    {
                        crop_name.setText("");
                    }

                }
                else
                {
                    other_variety.setVisibility(View.GONE);
                    variety_checker=0;
                    String cropname=setCropName(item);
                    if(!cropname.equalsIgnoreCase("none"))
                    {
                        crop_name.setText(cropname);
                    }

                    if(!item.equalsIgnoreCase("--Select variety--"))
                    {
                        showDialogChecker(item,crop_type);
                    }
                }


            }
            public void onNothingSelected(AdapterView<?> arg0) { }
        });

        /*crop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rice:
                        crop_type="rice";

                        if(season_type.equalsIgnoreCase("wet"))
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
                            spinnerArray.add("Other");
                        }
                        else if(season_type.equalsIgnoreCase("dry"))
                        {
                            season_type="dry";
                            spinnerArray.clear();
                            spinnerArray.add("NSIC Rc160");
                            spinnerArray.add("NSIC Rc222");
                            spinnerArray.add("NSIC Rc238");
                            spinnerArray.add("NSIC Rc216");
                            spinnerArray.add("Other");
                        }


                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        variety.setAdapter(adapter);

                        break;

                    case R.id.onion:
                        crop_type="onion";

                        if(season_type.equalsIgnoreCase("wet"))
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

                        break;




                }
            }

        });*/

        /*season.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.wet:
                        season_type="wet";

                        if(rice.isChecked())
                        {
                            crop_type="rice";
                            spinnerArray.clear();
                            spinnerArray.add("Rc9");
                            spinnerArray.add("Rc14");
                            spinnerArray.add("Rc18 (Ala)");
                            spinnerArray.add("Rc68");
                            spinnerArray.add("Rc194 (Submarino)");
                            spinnerArray.add("Rc222 (Triple 2)");
                            spinnerArray.add("Rc300");
                            spinnerArray.add("Rc160");
                            spinnerArray.add("Rc238");
                            spinnerArray.add("Rc216");
                            spinnerArray.add("Other");
                        }
                        else
                        {
                            crop_type="onion";
                            spinnerArray.clear();
                            spinnerArray.add("BGS 95 (F1 Hybrid)");
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

                        break;

                    case R.id.dry:
                        season_type="dry";

                        if(rice.isChecked())
                        {
                            crop_type="rice";
                            spinnerArray.clear();
                            spinnerArray.add("Rc160");
                            spinnerArray.add("Rc222 (Triple 2)");
                            spinnerArray.add("Rc238");
                            spinnerArray.add("Rc216");
                            spinnerArray.add("Other");
                        }
                        else
                        {
                            crop_type="onion";
                            spinnerArray.clear();
                            spinnerArray.add("BGS 95 (F1 Hybrid)");
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

                        break;




                }
            }

        });*/



        add_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crop_name_a = crop_name.getText().toString();
                String crop_variety="";
                Boolean flag=true;

                if(crop_name_a.isEmpty())
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
                    if(variety_checker==0)
                    {
                        crop_variety = variety.getSelectedItem().toString();
                    }
                    else
                    {
                        crop_variety = other_variety.getText().toString();
                    }

                    if(crop_variety.equalsIgnoreCase("--Select variety--"))
                    {
                        Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                "Please select variety", Snackbar.LENGTH_LONG);
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                snack.getView().getLayoutParams();
                        params.setMargins(0, 0, 0, 0);
                        snack.getView().setLayoutParams(params);
                        snack.show();
                    }
                    else
                    {
                        Cursor dbres = helper.getCropId(crop_name_a);
                        if(dbres.getCount() >=1)
                        {

                            //Snackbar.make(getActivity().findViewById(android.R.id.content),"Crop name already exists",Snackbar.LENGTH_SHORT).show();


                            Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                    "Crop name already exists", Snackbar.LENGTH_LONG);
                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                    snack.getView().getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            snack.getView().setLayoutParams(params);
                            snack.show();


                        }
                        else
                        {
                            try
                            {
                                long result = helper.insertCrop(crop_name_a,crop_type,crop_variety,season_type,"Production hasnt started yet",date_added);

                                if(result != -1)
                                {
                                    //Snackbar.make(getActivity().findViewById(android.R.id.content),"Crop Added",Snackbar.LENGTH_SHORT).show();

                                    Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                            "Crop Added", Snackbar.LENGTH_LONG);
                                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                            snack.getView().getLayoutParams();
                                    params.setMargins(0, 0, 0, 0);
                                    snack.getView().setLayoutParams(params);
                                    snack.show();

                                    getActivity().onBackPressed();

                                    crop_name.setText(null);
                                    crop.check(R.id.rice);
                                }

                                else
                                {
                                    // Snackbar.make(getActivity().findViewById(android.R.id.content),"Failed",Snackbar.LENGTH_SHORT).show();

                                    Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
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
                                //Message.message(getContext(),e.getMessage());
                            }
                        }
                    }


                }






            }
        });











      //  pane.setText(this.getArguments().getString("key"));


        return view;
    }

    public String setSeason_txt (String month)
    {
        String season="";
        switch (month)
        {
            case "Jan":
                season="Dry Season";
                break;
            case "Feb":
                season="Dry Season";
                break;
            case "Mar":
                season="Dry Season";
                break;
            case "Apr":
                season="Dry Season";
                break;
            case "May":
                season="Dry Season";
                break;
            case "Jun":
                season="Wet Season";
                break;
            case "Jul":
                season="Wet Season";
                break;
            case "Aug":
                season="Wet Season";
                break;
            case "Sep":
                season="Wet Season";
                break;
            case "Oct":
                season="Wet Season";
                break;
            case "Nov":
                season="Wet Season";
                break;
            case "Dec":
                season="Dry Season";
                break;
        }


        return season;
    }

    public String setSeasonvar (String month)
    {
        String season="";
        switch (month)
        {
            case "Jan":
                season="dry";
                break;
            case "Feb":
                season="dry";
                break;
            case "Mar":
                season="dry";
                break;
            case "Apr":
                season="dry";
                break;
            case "May":
                season="dry";
                break;
            case "Jun":
                season="wet";
                break;
            case "Jul":
                season="wet";
                break;
            case "Aug":
                season="wet";
                break;
            case "Sep":
                season="wet";
                break;
            case "Oct":
                season="wet";
                break;
            case "Nov":
                season="wet";
                break;
            case "Dec":
                season="dry";
                break;
        }


        return season;
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

            case "--Select variety--":
                name="";
                break;

            case "Other":
             name="";
             break;

             default:
                    name=breed;
                    break;

        }


        return name;
    }

    public void showDialogChecker(String variety,String crop_type)
    {

        Agronomics dialog2 = new Agronomics();
        Bundle args = new Bundle();
        args.putString("variety", variety);
        args.putString("crop_type", crop_type);
        dialog2.setTargetFragment(AddCrop.this,0);
        dialog2.setArguments(args);
        dialog2.show(getFragmentManager(),"SeedMaturityDialog");
        dialog2.setCancelable(true);

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

