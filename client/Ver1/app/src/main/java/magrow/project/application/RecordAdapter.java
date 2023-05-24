package magrow.project.application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import magrow.project.application.R;

import java.util.ArrayList;


public class RecordAdapter extends ArrayAdapter<DataModelRecordList> {
    public RecordAdapter(@NonNull Context context, ArrayList<DataModelRecordList> datamodels2) {
        super(context, 0, datamodels2);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DataModelRecordList record = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_row, parent, false);
        }

        TextView crop_name = convertView.findViewById(R.id.crop_name);
        TextView crop_variety = convertView.findViewById(R.id.crop_variety);
        TextView status_content = convertView.findViewById(R.id.status_content);
        //Button crop_log = convertView.findViewById(R.id.crop_log);


        crop_name.setText(record.getCrop_name());
        crop_variety.setText(record.getVariety());
        status_content.setText(record.getStatus());
        //maxTextView.setText("Max :\t"+recordgetMaxTemp()+" °C");

        return convertView;

    }
}
