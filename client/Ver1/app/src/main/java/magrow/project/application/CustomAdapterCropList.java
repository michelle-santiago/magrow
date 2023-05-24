package magrow.project.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import magrow.project.application.R;

import magrow.project.application.fragment.Crop_edit;
import magrow.project.application.fragment.Crop_pane;
import magrow.project.application.fragment.Crop_pane2;
import magrow.project.application.fragment.Onion_pane;
import magrow.project.application.fragment.Onion_pane2;

import java.util.ArrayList;

public class CustomAdapterCropList extends RecyclerView.Adapter<CustomAdapterCropList.PlayerViewHolder> {



    private Context mContext;
    myDbAdapter helper;
    ArrayList<DataModelCropList> dataModels2;



    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView crop_id,crop_name,variety,pane1,pane2,status_label,status_content;
        private ImageView info;
        Button crop_log,record_log,harvest_log;
        ImageButton archive_crop,edit_crop;

        public PlayerViewHolder(View view) {
            super(view);

            helper = new myDbAdapter(view.getContext());
            info = (ImageView)view.findViewById(R.id.info);
            crop_id = (TextView) view.findViewById(R.id.crop_id);
            crop_name = (TextView) view.findViewById(R.id.crop_name);
            variety = (TextView) view.findViewById(R.id.crop_variety);
            pane1 = (TextView) view.findViewById(R.id.pane1);
            pane2 = (TextView) view.findViewById(R.id.pane2);
            status_label = (TextView) view.findViewById(R.id.status_label);
            status_content = (TextView) view.findViewById(R.id.status_content);
            crop_log=(Button) view.findViewById(R.id.crop_log);
            record_log=(Button) view.findViewById(R.id.record_log);
            harvest_log=(Button) view.findViewById(R.id.harvest_log);
            edit_crop=(ImageButton)view.findViewById(R.id.edit_crop);
            archive_crop=(ImageButton)view.findViewById(R.id.archive_crop);
        }
    }

    public CustomAdapterCropList(ArrayList<DataModelCropList> players) { this.dataModels2 = players; }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_row, parent, false);


        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, final int position) {


        final DataModelCropList player = dataModels2.get(holder.getAdapterPosition());

        holder.edit_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("crop_id",String.valueOf(player.getId())); // set your parameteres

                Crop_edit nextFragment = new Crop_edit();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                fragmentManager.addToBackStack(null);*/


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Three");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });


        if(player.getArchive()==0)
        {
            holder.archive_crop.setImageResource(R.drawable.ic_archive);
        }

        else if(player.getArchive()==1)
        {
            holder.archive_crop.setImageResource(R.drawable.ic_unarchive);
        }


        holder.archive_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(player.getArchive()==0)
                {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                        v.getContext(), R.style.MyAlertDialogTheme2);

                alertDialog.setTitle("Move to archive");

                final TextView mess = new TextView(v.getContext());
                mess.setText("\n\t\tAre you sure?");
                mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, v.getContext().getResources().getDisplayMetrics()));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mess.setLayoutParams(lp);
                alertDialog.setView(mess);

                alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String crop_type=player.getCrop();

                        Cursor dbres2 = helper.getCropChecker(String.valueOf(player.getId()));
                        if(dbres2.getCount() == 0)
                        {

                            long result = helper.updateCropArchive(String.valueOf(player.getId()),"1");
                            if(result != -1)
                            {
                                Snackbar snack = Snackbar.make(v.getRootView().findViewById(R.id.coordinatorLayout),
                                        "Crop moved to archive", Snackbar.LENGTH_LONG);
                                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                        snack.getView().getLayoutParams();
                                params.setMargins(0, 0, 0, 0);
                                snack.getView().setLayoutParams(params);
                                snack.show();
                            }

                            if(crop_type.equalsIgnoreCase("rice"))
                            {
                                Crop_pane.mAdapter.dataModels2.remove(position);
                                Crop_pane.mAdapter.notifyItemRemoved(position);
                                Crop_pane.mAdapter.notifyItemRangeChanged(position, Crop_pane.mAdapter.getItemCount());
                            }

                            else
                            {
                                Onion_pane.mAdapter.dataModels2.remove(position);
                                Onion_pane.mAdapter.notifyItemRemoved(position);
                                Onion_pane.mAdapter.notifyItemRangeChanged(position, Onion_pane.mAdapter.getItemCount());

                            }

                        }
                        else {

                            // Snackbar.make(view.findViewById(android.R.id.content),"Deleting Failed, Crop is still in use",Snackbar.LENGTH_LONG).show();

                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(

                                    v.getContext(), R.style.MyAlertDialogTheme3);

                            alertDialog2.setTitle("Warning");

                            final TextView mess = new TextView(v.getContext());
                            alertDialog2.setMessage("\nNOTE: Crop is still in use.\nAll of its scheduled activities\nand record will also be archived");
                            mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, v.getContext().getResources().getDisplayMetrics()));

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
                                            //Message.message(v.getContext(),"Hello");
                                            long result = helper.updateCropArchive(String.valueOf(player.getId()),"1");
                                            if(result != -1)
                                            {
                                                Snackbar snack = Snackbar.make(v.getRootView().findViewById(R.id.coordinatorLayout),
                                                        "Crop moved to archive", Snackbar.LENGTH_LONG);
                                                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                                        snack.getView().getLayoutParams();
                                                params.setMargins(0, 0, 0, 0);
                                                snack.getView().setLayoutParams(params);
                                                snack.show();
                                            }


                                            if(crop_type.equalsIgnoreCase("rice"))
                                            {
                                                Crop_pane.mAdapter.dataModels2.remove(position);
                                                Crop_pane.mAdapter.notifyItemRemoved(position);
                                                Crop_pane.mAdapter.notifyItemRangeChanged(position, Crop_pane.mAdapter.getItemCount());
                                            }

                                            else
                                            {
                                                Onion_pane.mAdapter.dataModels2.remove(position);
                                                Onion_pane.mAdapter.notifyItemRemoved(position);
                                                Onion_pane.mAdapter.notifyItemRangeChanged(position, Onion_pane.mAdapter.getItemCount());

                                            }



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

                else if(player.getArchive()==1)
                {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                            v.getContext(), R.style.MyAlertDialogTheme2);

                    alertDialog.setTitle("Restore archived crop");

                    final TextView mess = new TextView(v.getContext());
                    mess.setText("\n\t\tAre you sure?");
                    mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 9F, v.getContext().getResources().getDisplayMetrics()));

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    mess.setLayoutParams(lp);
                    alertDialog.setView(mess);

                    alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final String crop_type=player.getCrop();



                                long result = helper.updateCropArchive(String.valueOf(player.getId()),"0");
                                if(result != -1)
                                {
                                    Snackbar snack = Snackbar.make(v.getRootView().findViewById(R.id.coordinatorLayout),
                                            "Crop restored", Snackbar.LENGTH_LONG);
                                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                            snack.getView().getLayoutParams();
                                    params.setMargins(0, 0, 0, 0);
                                    snack.getView().setLayoutParams(params);
                                    snack.show();
                                }

                                if(crop_type.equalsIgnoreCase("rice"))
                                {
                                    Crop_pane2.mAdapter.dataModels2.remove(position);
                                    Crop_pane2.mAdapter.notifyItemRemoved(position);
                                    Crop_pane2.mAdapter.notifyItemRangeChanged(position, Crop_pane2.mAdapter.getItemCount());
                                }

                                else
                                {
                                    Onion_pane2.mAdapter.dataModels2.remove(position);
                                    Onion_pane2.mAdapter.notifyItemRemoved(position);
                                    Onion_pane2.mAdapter.notifyItemRangeChanged(position, Onion_pane2.mAdapter.getItemCount());

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

            }
        });

        holder.crop_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Crop_log.class);
                intent.putExtra("crop_id",String.valueOf(player.getId()));
                intent.putExtra("crop_name",String.valueOf(player.getCrop_name()));
                intent.putExtra("crop_variety",String.valueOf(player.getVariety()));
                v.getContext().startActivity(intent);
            }
        });

        holder.record_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Snackbar.make(v, "" +player.getCrop_name()+" "+player.getCrop(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();*/


                //Intent intent = new Intent(v.getContext(), RecordDetails.class);
                Intent intent = new Intent(v.getContext(), Summary.class);
                intent.putExtra("crop_id",String.valueOf(player.getId()));
                intent.putExtra("crop_name",String.valueOf(player.getCrop_name()));
                intent.putExtra("crop_variety",String.valueOf(player.getVariety()));
                if(player.getArchive()==1)
                {
                    intent.putExtra("status","disable");
                }
                if(player.getArchive()==0)
                {
                    intent.putExtra("status","enable");
                }
                v.getContext().startActivity(intent);



            }
        });
        holder.harvest_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Snackbar.make(v, "" +player.getCrop_name()+" "+player.getCrop(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();*/


                Intent intent = new Intent(v.getContext(), RecordDetails.class);
                //Intent intent = new Intent(v.getContext(), Summary.class);
                intent.putExtra("crop_id",String.valueOf(player.getId()));
                intent.putExtra("crop_name",String.valueOf(player.getCrop_name()));
                intent.putExtra("crop_variety",String.valueOf(player.getVariety()));
                if(player.getArchive()==1)
                {
                    intent.putExtra("status","disable");
                }
                if(player.getArchive()==0)
                {
                    intent.putExtra("status","enable");
                }
                v.getContext().startActivity(intent);

            }
        });



        holder.pane1.setText("Crop Name\t\t:");
        holder.pane2.setText("Variety\t\t\t\t\t:");
        holder.status_label.setText("Status\t\t\t\t\t:");
        holder.crop_id.setText(""+player.getId());
        holder.crop_name.setText(player.getCrop_name());
        holder.variety.setText(player.getVariety());
        holder.status_content.setText(player.getStatus());

        String crop_type=player.getCrop();

        if(crop_type.equalsIgnoreCase("rice"))
        {
            holder.info.setImageResource(R.drawable.ricev2);
        }
        else if (crop_type.equalsIgnoreCase("onion"))
        {
            holder.info.setImageResource(R.drawable.onionv2);
        }



        //holder.month.setText(getMonthName(month));



    }

    @Override
    public int getItemCount() {
        return dataModels2.size();
    }

}
