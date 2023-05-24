package magrow.project.application.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import magrow.project.application.ChangePassword;
import magrow.project.application.MainActivity;
import magrow.project.application.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Logged_in extends Fragment {

    Button logout,edit_account,change_pass;
    TextView username,name,email;
    //ImageButton back;
    public Logged_in() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(magrow.project.application.R.layout.fragment_activity_logged_in,
                container, false);
        username = view.findViewById(magrow.project.application.R.id.username1);
        name = view.findViewById(magrow.project.application.R.id.name1);
        email = view.findViewById(magrow.project.application.R.id.email1);
        logout=view.findViewById(magrow.project.application.R.id.logout);
        //back = (ImageButton)view.findViewById(R.id.back);


        ((MainActivity) getActivity())
                .setActionBarTitle("\t\t\t\tAccount Details");

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((MainActivity)getActivity()).hideUpButton();

        ((MainActivity)getActivity()).SetNavigationVisibiltity(true);

       /* back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });*/

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        String usernameKey=sharedPref.getString("usernameKey", null);
        String emailKey=sharedPref.getString("emailKey", null);
        String passwordKey=sharedPref.getString("passwordKey", null);
        if(emailKey==null&&usernameKey==null&&passwordKey==null){
            Bundle bundle = new Bundle();
            Login nextFragment = new Login();
            nextFragment.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
            // This action will remove Fragment one and add Fragment two.
            fragmentTransaction.replace(magrow.project.application.R.id.container, nextFragment, "Fragment Two");

            // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
            //fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
        else{
            String username1=sharedPref.getString("usernameKey", null);
            String email1=sharedPref.getString("emailKey", null);
            String name1=sharedPref.getString("nameKey", null);
            username.setText(username1);
            email.setText(email1);
            name.setText(name1);

        }

        edit_account=view.findViewById(magrow.project.application.R.id.edit_account);
        change_pass=view.findViewById(magrow.project.application.R.id.change_pass);




        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangePassword.username=username.getText().toString();
                ChangePassword dialog = new ChangePassword();
                dialog.setTargetFragment(Logged_in.this,0);
                dialog.show(getFragmentManager(),"WelcomeDialog");
                dialog.setCancelable(true);
            }
            });


        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Edit_Profile nextFragment = new Edit_Profile();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(magrow.project.application.R.id.container, nextFragment, "Fragment Two");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.remove("idKey");
                editor.remove("usernameKey"); // will delete key name
                editor.remove("emailKey");
                editor.remove("nameKey"); // will delete key name
                editor.remove("passwordKey");
                editor.commit();
                Bundle bundle = new Bundle();
                Login nextFragment = new Login();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(magrow.project.application.R.id.container, nextFragment, "Fragment Two");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                //fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }

        });
        return view;
    }



}
