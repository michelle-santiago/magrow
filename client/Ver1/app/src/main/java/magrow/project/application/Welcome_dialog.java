package magrow.project.application;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import magrow.project.application.R;

public class Welcome_dialog extends DialogFragment {



    private static final String TAG = "WelcomeDialog";


    myDbAdapter helper;
    Button ok;

    //widgets


    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_welcome, container, false);

        helper = new myDbAdapter(getActivity());
        ok=(Button)view.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

}
