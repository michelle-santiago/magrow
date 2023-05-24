package magrow.project.application.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.MainActivity;
import magrow.project.application.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Others extends Fragment {


    public Others() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_others,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle("Others");

        return view;
    }

}
