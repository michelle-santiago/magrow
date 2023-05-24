package magrow.project.application;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WarningSched extends DialogFragment {



    private static final String TAG = "MyCustomDialog";


    myDbAdapter helper;


    //widgets
Button close;
TextView pane;

    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_warning_sched, container, false);

        helper = new myDbAdapter(getActivity());

        close=(Button)view.findViewById(R.id.close);
        pane=(TextView)view.findViewById(R.id.pane);

        Bundle mArgs = getArguments();
        String date = mArgs.getString("date");
        String time = mArgs.getString("time");


        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, df);

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("MMM dd yyyy");
        String expected_day=dtfOut.print(dateTime);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        String src="You already have an activity scheduled<br>for <b>"+expected_day+"</b> at <b>"+time+"</b>.<br>Please schedule it<br>to a different date and time.";
        pane.setText(Html.fromHtml(src));


        return view;
    }

}
