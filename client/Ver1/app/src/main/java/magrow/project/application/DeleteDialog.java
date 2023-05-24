package magrow.project.application;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.R;

public class DeleteDialog extends DialogFragment {



    private static final String TAG = "MyCustomDialog";


    myDbAdapter helper;

    //widgets


    //vars

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_delete, container, false);

        helper = new myDbAdapter(getActivity());



        return view;
    }

}
