package magrow.project.application.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import magrow.project.application.MainActivity;
import magrow.project.application.R;
import magrow.project.application.Utils;

import java.util.HashMap;
import java.util.Map;

import static magrow.project.application.fragment.Login.emailKey;
import static magrow.project.application.fragment.Login.idKey;
import static magrow.project.application.fragment.Login.nameKey;
import static magrow.project.application.fragment.Login.usernameKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_Profile extends Fragment {
    Button edit_account;
    EditText name,username,email;
    String name2,username2,email2;
    String url="https://magrow.000webhostapp.com/android_edit_account.php";
    TextInputLayout etPasswordLayout, etEmailLayout, etFullnameLayout,etUsernameLayout;
    ImageButton back;

    public Edit_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(magrow.project.application.R.layout.fragment_activity_edit__profile,
                container, false);


        ((MainActivity) getActivity())
                .setActionBarTitle("\t\t\t\tEdit Account");

        //((AppCompatActivity) getActivity()).getSupportActionBar().show();

        //((MainActivity)getActivity()).showUpButton();

        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);


        name = view.findViewById(magrow.project.application.R.id.fullname);
        username=view.findViewById(magrow.project.application.R.id.username);
        email=view.findViewById(magrow.project.application.R.id.email);
        etFullnameLayout = view.findViewById(magrow.project.application.R.id.etFullnameLayout);
        etUsernameLayout = view.findViewById(magrow.project.application.R.id.etUsernameLayout);
        etEmailLayout = view.findViewById(magrow.project.application.R.id.etEmailLayout);
        etPasswordLayout =  view.findViewById(magrow.project.application.R.id.etPasswordLayout);
        edit_account = view.findViewById(magrow.project.application.R.id.edit_account);
        back = (ImageButton)view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        final SharedPreferences sharedPref = this.getActivity().getSharedPreferences("MyPrefs",0);
        //String check=sharedPref.getString("usernameKey", null); // getting String
        //Toast.makeText(getContext(), "check "+check, Toast.LENGTH_SHORT).show();
        //register.setEnabled(false);
        final String id1=sharedPref.getString("idKey", null);
        String username1=sharedPref.getString("usernameKey", null);
        String email1=sharedPref.getString("emailKey", null);
        String name1=sharedPref.getString("nameKey", null);
        username.setText(username1);
        email.setText(email1);
        name.setText(name1);
        edit_account.setOnClickListener(new View.OnClickListener() {
            ProgressDialog pdLoading = new ProgressDialog(getContext());
            @Override
            public void onClick(View v) {

                name2 = name.getText().toString();
                username2 = username.getText().toString();
                email2 = email.getText().toString();


                if(validate(name2,username2,email2)){

                    //this method will be running on UI thread
                    pdLoading.setMessage("\tLoading...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pdLoading.dismiss();
                            if(response.equalsIgnoreCase("Username and Email Already Exists!"))
                            {
                                Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                                Log.i("My success", "" + response);
                                etUsernameLayout.setError("That username is taken. Try another.");
                                etEmailLayout.setError("That email is taken. Try another.");
                            }
                            else if(response.equalsIgnoreCase("Username Already Exists!"))
                            {
                                Log.i("My success", "" + response);
                                etUsernameLayout.setError("That username is taken. Try another.");
                            }
                            else if(response.equalsIgnoreCase("Email Already Exists!"))
                            {
                                //Toast.makeText(Register.this, "That email is taken. Try another.", Toast.LENGTH_SHORT).show();
                                etEmailLayout.setError("That email is taken. Try another.");
                                Log.i("My success", "" + response);
                            }
                            else if(response.equalsIgnoreCase("Successfully Updated"))
                            {
                                Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(idKey, id1);
                                editor.putString(usernameKey, username2);
                                editor.putString(emailKey, email2);
                                editor.putString(nameKey, name2);
                                editor.commit();
                                Log.i("My success", "" + response);
                                Bundle bundle = new Bundle();
                                //bundle.putString("crop_type","onion"); // set your parameteres
                                //bundle.putString("status","failed");
                                Logged_in nextFragment = new Logged_in();
                                nextFragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                                fragmentManager.addToBackStack(null);*/


                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                                // This action will remove Fragment one and add Fragment two.
                                fragmentTransaction.replace(magrow.project.application.R.id.container, nextFragment, "Fragment Two");

                                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                                //fragmentTransaction.addToBackStack(null);

                                fragmentTransaction.commit();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pdLoading.dismiss();
                            Toast.makeText(getContext(), "Failed to connect to Network", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<String, String>();
                            map.put("id", id1);
                            map.put("name", name2);
                            map.put("username", username2);
                            map.put("email", email2);

                            return map;
                        }
                    };
                    queue.add(request);
                }
                else{

                }


            }
        });
        return view;
    }

    private boolean validate(String name,String username,String email) {

        // Reset errors.
        etFullnameLayout.setError(null);
        etUsernameLayout.setError(null);
        etEmailLayout.setError(null);



        if (Utils.isEmpty(name)) {
            etFullnameLayout.setError("Name is required");
            return false;
        }
        else if (Utils.isEmpty(username)) {
            etUsernameLayout.setError("Username is required");
            return false;
        }
        else if (Utils.isEmpty(email)) {
            etEmailLayout.setError("Email is required");
            return false;
        } else if (!Utils.isEmailValid(email)) {
            etEmailLayout.setError("Enter a valid email");
            return false;
        }

        return true;
    }

}
