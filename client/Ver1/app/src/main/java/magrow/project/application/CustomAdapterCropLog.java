package magrow.project.application;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import magrow.project.application.R;

import java.util.ArrayList;

public class CustomAdapterCropLog extends ArrayAdapter<DataModelCropLog>{

    private ArrayList<DataModelCropLog> dataSet;
    Context mContext;

    private static class ViewHolder {

        TextView timestamp,time;
        TextView operation;
        TextView operation_label;
        TextView operation_status;
        ImageView operation_img;

    }

    public CustomAdapterCropLog(ArrayList<DataModelCropLog> data, Context context) {
        super(context, R.layout.crop_log_row, data);
        this.dataSet = data;
        this.mContext=context;

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModelCropLog dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.crop_log_row, parent, false);
            viewHolder.timestamp = (TextView) convertView.findViewById(R.id.timestamp);
            viewHolder.operation = (TextView) convertView.findViewById(R.id.operation);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.operation_label = (TextView) convertView.findViewById(R.id.operation_label);
            viewHolder.operation_status = (TextView) convertView.findViewById(R.id.operation_status);
            viewHolder.operation_img = (ImageView) convertView.findViewById(R.id.operation_img);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        String[] datetime=dataModel.getDatetimecrop().split(" ");
        viewHolder.timestamp.setText(datetime[0]);
        viewHolder.time.setText(datetime[1]);
        viewHolder.operation.setText(dataModel.getOperation_name());
        String op_status = dataModel.getOperation_status();
        if (op_status.equals("0"))
        {
            viewHolder.operation_status.setText("Not done");
            viewHolder.operation_status.setTextColor(Color.parseColor("#FA8000"));
        }
        else
        {
            viewHolder.operation_status.setText("Done");
            viewHolder.operation_status.setTextColor(Color.parseColor("#01bea2"));
        }

        String ops_img=dataModel.getOperation();
        switch (ops_img)
        {
            case "1":
               viewHolder.operation_img.setImageResource(R.drawable.mm1);
                break;

            case "2":
                viewHolder.operation_img.setImageResource(R.drawable.mm2);
                break;

            case "3":
                viewHolder.operation_img.setImageResource(R.drawable.mm3);
                break;

            case "4":
                viewHolder.operation_img.setImageResource(R.drawable.mm4);
                break;

            case "5":
                viewHolder.operation_img.setImageResource(R.drawable.mm5);
                break;

            case "6":
                viewHolder.operation_img.setImageResource(R.drawable.mm6);
                break;

            case "7":
                viewHolder.operation_img.setImageResource(R.drawable.mm7);
                break;
        }



        // Return the completed view to render on screen
        return convertView;
    }

    public String setOps_img(String title)
    {
        String status="";

        switch (title)
        {
            case "Plowing":
                status="1";
                break;
            case "Harrowing":
                status="1";
                break;
            case "Levelling":
                status="1";
                break;
            case "Transplanting":
                status="2";
                break;
            case "Soaking":
                status="2";
                break;
            case "Pulling of Seedlings":
                status="2";
                break;
            case "Direct Seeding":
                status="2";
                break;
            case "Seedlings Planting":
                status="2";
                break;
            case "Irrigation":
                status="3";
                break;
            case "Water Pump":
                status="3";
                break;
            case "Weeding":
                status="3";
                break;
            case "Molluscicide":
                status="4";
                break;
            case "Herbicide":
                status="4";
                break;
            case "Fungicide":
                status="4";
                break;
            case "Reaping":
                status="5";
                break;
            case "Threshing":
                status="5";
                break;
            case "Transportation":
                status="5";
                break;
            case "Drying":
                status="5";
                break;
            case "Harvesting":
                status="5";
                break;
            case "Milling":
                status="5";
                break;
                default:
                    status="6";
                break;
        }

        return status;
    }
}
