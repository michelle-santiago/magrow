package magrow.project.application;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import magrow.project.application.R;

import java.util.ArrayList;

public class CustomAdapterListView extends RecyclerView.Adapter<CustomAdapterListView.PlayerViewHolder>{


    public ArrayList<DataModelEventList> events;

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView title,desc;


        public PlayerViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            desc = (TextView) view.findViewById(R.id.desc);
        }
    }

    public CustomAdapterListView(ArrayList<DataModelEventList> players) {
        this.events = players;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_viewpager, parent, false);

        return new PlayerViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {


        DataModelEventList player = events.get(position);

        holder.title.setText(player.getEvent_name());
        holder.desc.setText(player.getEvent_date());


    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    public String getMonthName (int month){
        String monthName="";

        switch (month)
        {
            case 1:
                monthName="January";
                break;

            case 2:
                monthName="February";
                break;

            case 3:
                monthName="March";
                break;

            case 4:
                monthName="April";
                break;

            case 5:
                monthName="May";
                break;

            case 6:
                monthName="June";
                break;

            case 7:
                monthName="July";
                break;

            case 8:
                monthName="August";
                break;

            case 9:
                monthName="September";
                break;

            case 10:
                monthName="October";
                break;

            case 11:
                monthName="November";
                break;

            case 12:
                monthName="December";
                break;
            default:
                monthName="No Month";
                break;

        }

        return monthName;
    }
}

