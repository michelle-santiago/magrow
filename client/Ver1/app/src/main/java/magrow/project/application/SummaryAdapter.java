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


public class SummaryAdapter extends ArrayAdapter<SummaryDataModel> {
    public SummaryAdapter(@NonNull Context context, ArrayList<SummaryDataModel> datamodels2) {
        super(context, 0, datamodels2);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SummaryDataModel record = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.summary_row, parent, false);
        }



       /* ViewGroup.LayoutParams layoutparams = convertView.getLayoutParams();
        //Define your height here.
        layoutparams.height = 30;*/

        TextView type= convertView.findViewById(R.id.type);
        TextView date = convertView.findViewById(R.id.date);
        TextView amount = convertView.findViewById(R.id.amount);
        //Button crop_log = convertView.findViewById(R.id.crop_log);


       type.setText(record.getEvent_name());
        date.setText(record.getDate());
        amount.setText(record.getAmount());
        /*type.setText("asdas");
        date.setText("dasda");
        amount.setText("asdas");*/
        //maxTextView.setText("Max :\t"+recordgetMaxTemp()+" °C");
        //convertView.setLa;
        return convertView;

    }

}
