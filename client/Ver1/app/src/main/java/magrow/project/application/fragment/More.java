package magrow.project.application.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import magrow.project.application.About;
import magrow.project.application.MainActivity;
import magrow.project.application.R;
import magrow.project.application.Sync_Record;

public class More extends Fragment {


    public More() {
        // Required empty public constructor
    }

    ImageButton sync1,archive1,about1;
    Button sync2,archive2,about2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_more,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle("More");
        ((MainActivity)getActivity()).SetNavigationVisibiltity(true);
        //((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((MainActivity)getActivity()).hideUpButton();

        sync1=(ImageButton)view.findViewById(R.id.sync1);
        sync2=(Button)view.findViewById(R.id.sync2);

        archive1=(ImageButton)view.findViewById(R.id.archive1);
        archive2=(Button)view.findViewById(R.id.archive2);

        about1=(ImageButton)view.findViewById(R.id.about1);
        about2=(Button)view.findViewById(R.id.about2);

        sync1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Sync_Record.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        sync2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Sync_Record.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        about1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });

        about2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);
            }
        });

        archive1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Archive nextFragment = new Archive();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Six");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        archive2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Archive nextFragment = new Archive();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Six");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });












        return view;
    }

}
