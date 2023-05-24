package magrow.project.application.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.CustomAdapterCropList;
import magrow.project.application.DataModelCropList;

import magrow.project.application.R;
import magrow.project.application.SwipeController;
import magrow.project.application.myDbAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Onion_pane extends Fragment {

    ArrayList<DataModelCropList> dataModels2;
    public static CustomAdapterCropList mAdapter;

    SwipeController swipeController = null;
    myDbAdapter helper;
    RecyclerView recyclerView;

    public Onion_pane() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onion_pane,
                container, false);


        helper = new myDbAdapter(getContext());

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        dataModels2= new ArrayList<>();

        setPlayersDataAdapter();
        setupRecyclerView();



        return view;
    }

    private void setPlayersDataAdapter() {



        String crop_type= "onion";

        int crop_id;
        String crop_name;
        String crop;
        String variety;
        Cursor dbres = helper.getAllDataCropSpesArchive(crop_type,"0");
        if(dbres.getCount() == 0)
        {

            //Snackbar.make(view.findViewById(android.R.id.content),"No Crops Yet",Snackbar.LENGTH_SHORT).show();

            /*Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                    "No Onion Crops Yet", Snackbar.LENGTH_LONG);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                    snack.getView().getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            snack.getView().setLayoutParams(params);
            snack.show();*/

        }
        else {

            int a=0;
            while (dbres.moveToNext()) {

                crop_id=Integer.parseInt(String.format(dbres.getString(0)));
                crop_name=String.format(dbres.getString(1));
                crop=String.format(dbres.getString(2));
                variety=String.format(dbres.getString(3));
                String status=String.format(dbres.getString(5));
                int archive=Integer.parseInt(String.format(dbres.getString(7)));

                a++;

                dataModels2.add(new DataModelCropList(crop_id,crop,crop_name,variety,status,a,archive));

            }

            mAdapter= new CustomAdapterCropList(dataModels2);
        }

    }

    private void setupRecyclerView() {


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        /*swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                DataModelCropList dataModel = dataModels2.get(position);
                final String id=String.valueOf(dataModel.getId());
                final int a=position;

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                        getContext(),R.style.MyAlertDialogTheme2);

                alertDialog.setTitle("DELETE");
                final TextView mess = new TextView(getContext());
                mess.setText("\n\t\tAre you sure?");
                mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, getActivity().getResources().getDisplayMetrics()));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mess.setLayoutParams(lp);
                alertDialog.setView(mess);

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        Cursor dbres2 = helper.getCropChecker(id);
                        if(dbres2.getCount() == 0)
                        {
                            helper.deleteCrop(id);

                            //Snackbar.make(view.findViewById(android.R.id.content),"Crop Deleted",Snackbar.LENGTH_LONG).show();

                            Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                    "Crop Deleted", Snackbar.LENGTH_LONG);
                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                    snack.getView().getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            snack.getView().setLayoutParams(params);
                            snack.show();



                            mAdapter.crops.remove(a);
                            mAdapter.notifyItemRemoved(a);
                            mAdapter.notifyItemRangeChanged(a, mAdapter.getItemCount());



                        }
                        else {

                            // Snackbar.make(view.findViewById(android.R.id.content),"Deleting Failed, Crop is still in use",Snackbar.LENGTH_LONG).show();

                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(

                                    getContext(),R.style.MyAlertDialogTheme3);

                            alertDialog2.setTitle("Warning");

                            final TextView mess = new TextView(getContext());
                            alertDialog2.setMessage("\nNOTE: Crop is still in use\nDeleting this will remove\nAll its other scheduled activities");
                            mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, getActivity().getResources().getDisplayMetrics()));

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            mess.setLayoutParams(lp);
                            alertDialog2.setView(mess);

                            alertDialog2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });

                            alertDialog2.setPositiveButton("Continue",

                                    new DialogInterface.OnClickListener() {

                                        @Override

                                        public void onClick(DialogInterface dialog,

                                                            int which) {
                                            Message.message(getContext(),"Hello");
                                            helper.deleteCrop(id);
                                            helper.deleteEventCropId(id);
                                            helper.deleteTransLogCropID(id);

                                            mAdapter.crops.remove(a);
                                            mAdapter.notifyItemRemoved(a);
                                            mAdapter.notifyItemRangeChanged(a, mAdapter.getItemCount());

                                            Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                                    "Crop Deleted", Snackbar.LENGTH_LONG);
                                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                    snack.getView().getLayoutParams();
                                            params.setMargins(0, 0, 0, 0);
                                            snack.getView().setLayoutParams(params);
                                            snack.show();


                                        }

                                    });

                            alertDialog2.show();
                        }





                    }
                });

                alertDialog.setNegativeButton("Cancel",

                        new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog,

                                                int which) {
                                dialog.cancel();



                            }

                        });





                alertDialog.show();
            }

            @Override
            public void onLeftClicked(int position) {
                DataModelCropList dataModel = dataModels2.get(position);
                final String id=String.valueOf(dataModel.getId());
                final int a=position;

                Bundle bundle = new Bundle();
                bundle.putString("crop_id",id); // set your parameteres

                Crop_edit nextFragment = new Crop_edit();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                fragmentManager.addToBackStack(null);*/


                /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Three");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();


            }


        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });*/
    }
}
