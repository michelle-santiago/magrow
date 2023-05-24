package magrow.project.application.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {
    Button register;
    EditText name,username,email,password,c_password;
    String name2,username2,email2,password2,c_password2;
    String url="https://magrow.000webhostapp.com/android_register.php";
    TextInputLayout etPasswordLayout, etEmailLayout, etFullnameLayout,etUsernameLayout,etc_PasswordLayout;
    ImageButton back;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(magrow.project.application.R.layout.fragment_activity_register,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle(" ");

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((MainActivity)getActivity()).showUpButton();

        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);

        ((MainActivity)getActivity()).showUpButton();
        name = view.findViewById(magrow.project.application.R.id.fullname);
        username=view.findViewById(magrow.project.application.R.id.username);
        email=view.findViewById(magrow.project.application.R.id.email);
        password=view.findViewById(magrow.project.application.R.id.password);
        c_password=view.findViewById(magrow.project.application.R.id.c_password);
        etFullnameLayout = view.findViewById(magrow.project.application.R.id.etFullnameLayout);
        etUsernameLayout = view.findViewById(magrow.project.application.R.id.etUsernameLayout);
        etEmailLayout = view.findViewById(magrow.project.application.R.id.etEmailLayout);
        etPasswordLayout =  view.findViewById(magrow.project.application.R.id.etPasswordLayout);
        etc_PasswordLayout = view.findViewById(magrow.project.application.R.id.etc_PasswordLayout);
        register = view.findViewById(magrow.project.application.R.id.register);
        back = (ImageButton)view.findViewById(R.id.back);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        //String check=sharedPref.getString("usernameKey", null); // getting String
        //Toast.makeText(getContext(), "check "+check, Toast.LENGTH_SHORT).show();
        //register.setEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            ProgressDialog pdLoading = new ProgressDialog(getContext());
            @Override
            public void onClick(View v) {

                name2 = name.getText().toString();
                username2 = username.getText().toString();
                email2 = email.getText().toString();
                password2 = md5(password.getText().toString());
                c_password2 = md5(c_password.getText().toString());
                if(validate(name2,username2,email2,password.getText().toString(),c_password.getText().toString())){

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
                            else if(response.equalsIgnoreCase("Successfully Registered"))
                            {
                                Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                                Log.i("My success", "" + response);
                                Bundle bundle = new Bundle();
                                //bundle.putString("crop_type","onion"); // set your parameteres
                                //bundle.putString("status","failed");
                                Login nextFragment = new Login();
                                nextFragment.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                /*fragmentManager.beginTransaction().replace(R.id.container, nextFragment);
                                fragmentManager.addToBackStack(null);*/


                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                                // This action will remove Fragment one and add Fragment two.
                                fragmentTransaction.replace(magrow.project.application.R.id.container, nextFragment, "Fragment Two");

                                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                                //
                                // fragmentTransaction.addToBackStack(null);

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
                            map.put("name", name2);
                            map.put("username", username2);
                            map.put("email", email2);
                            map.put("password", password2);

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
    public static String md5(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }


    private boolean validate(String name,String username,String email, String password, String c_password) {

        // Reset errors.
        etFullnameLayout.setError(null);
        etUsernameLayout.setError(null);
        etEmailLayout.setError(null);
        etPasswordLayout.setError(null);
        etc_PasswordLayout.setError(null);

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

        if (Utils.isEmpty(password)) {
            etPasswordLayout.setError("Password is required");

            return false;
        } else if (!Utils.isPasswordValid(password)) {
            etPasswordLayout.setError("Password must contain at least 8 characters");
            return false;
        }
        else if (Utils.isEmpty(c_password)) {
            etc_PasswordLayout.setError("Confirm your password");

            return false;
        }

        else if (!password.equals(c_password)) {
            etc_PasswordLayout.setError("Passwords didn't match. Try again");

            return false;
        }
        return true;
    }

}
