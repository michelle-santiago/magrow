package magrow.project.application.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import magrow.project.application.DataModelRecordList;
import magrow.project.application.Message;
import magrow.project.application.R;
import magrow.project.application.RecordAdapter;
import magrow.project.application.RecordDetails;
import magrow.project.application.myDbAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Rice_record extends Fragment {

    private ArrayList<DataModelRecordList> recordArrayList = new ArrayList<>();
    private ListView listView;
    TextView crop_name,crop_variety,status_content;
    myDbAdapter helper;
    LinearLayout no_crops;
    FloatingActionButton fab;

    public Rice_record() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_rice_record,
                container, false);

       /*((MainActivity) getActivity())
                .setActionBarTitle("Weather");

        Message.message(getContext(),""+Home.location);*/

        listView = view.findViewById(R.id.idListView2);
        helper = new myDbAdapter(getContext());
        no_crops=view.findViewById(R.id.no_crops);
        crop_name = (TextView)view.findViewById(R.id.crop_name);
        crop_variety= (TextView)view.findViewById(R.id.crop_variety);
        status_content = (TextView)view.findViewById(R.id.status_content);

        setPlayersDataAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> arg0, View arg1,

                                    int position, long arg3) {

                // TODO Auto-generated method stub
                DataModelRecordList dataModel = recordArrayList.get(position);
                Message.message(getContext(),dataModel.getCrop_name());
                /*
                RecordDetailsDialog dialogFragment = new RecordDetailsDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("crop_id",dataModel.getId());
                bundle.putString("crop_name",dataModel.getCrop_name());
                bundle.putString("crop_variety", dataModel.getVariety());
                bundle.putString("status",dataModel.getStatus());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getFragmentManager(),"RecordDetailsDialog");*/
                Intent intent = new Intent(getActivity(), RecordDetails.class);
                intent.putExtra("id",Integer.toString(dataModel.getId()));
                intent.putExtra("crop_name",dataModel.getCrop_name());
                intent.putExtra("crop_variety", dataModel.getVariety());
                intent.putExtra("status",dataModel.getStatus());
                getActivity().startActivity(intent);
                //Activity activity = (Activity) getContext();
                // activity.overridePendingTransition(R.anim.mysplashanimation,R.anim.slide_in);
            }

        });

        return view;
    }

    private void setPlayersDataAdapter() {
        recordArrayList.clear();
        String crop_type= "rice";

        int crop_id;
        String crop_name;
        String crop;
        String variety;
        Cursor dbres = helper.getAllDataCropSpes(crop_type);
        if(dbres.getCount() == 0)
        {
            no_crops.setVisibility(View.VISIBLE);
           /* //Snackbar.make(view.findViewById(android.R.id.content),"No Crops Yet",Snackbar.LENGTH_SHORT).show();

            Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                    "No Rice Crops Yet", Snackbar.LENGTH_LONG);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                    snack.getView().getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            snack.getView().setLayoutParams(params);
            snack.show();*/

        }
        else {
            no_crops.setVisibility(View.GONE);
            String status="";
            while (dbres.moveToNext()) {

                crop_id=Integer.parseInt(String.format(dbres.getString(0)));
                crop_name=String.format(dbres.getString(1));
                crop=String.format(dbres.getString(2));
                variety=String.format(dbres.getString(3));
                Cursor dbres1 = helper.getRecordLog(Integer.toString(crop_id));
                if(dbres1.getCount() != 0) {
                    status="";
                }
                else{
                    status="No Record Yet";
                }


                recordArrayList.add(new DataModelRecordList(crop_id,crop,crop_name,variety,status));

            }

        }
        if(recordArrayList != null) {
            RecordAdapter recordAdapter = new RecordAdapter(getContext(), recordArrayList);
            listView.setAdapter(recordAdapter);
        }

    }



    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        setPlayersDataAdapter();
    }



}