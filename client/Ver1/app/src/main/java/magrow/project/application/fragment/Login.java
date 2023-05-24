package magrow.project.application.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import magrow.project.application.MainActivity;
import magrow.project.application.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;
    EditText ip;
    //TextView space;
    Button login,forget,register;
    //public static String network="";
    public static String username="";
    public static String password="";
    public static String res="";
    TextInputLayout etPasswordLayout,etUsernameLayout;
    public static final String idKey= "idKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String usernameKey= "usernameKey";
    public static final String passwordKey = "passwordKey";
    public static final String emailKey= "emailKey";
    public static final String nameKey= "nameKey";

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(magrow.project.application.R.layout.fragment_activity_login,
                container, false);

        //  ((MainActivity) getActivity()).setActionBarTitle("Home");

        //((MainActivity)getActivity()).showUpButton();

        //((MainActivity)getActivity()).SetNavigationVisibiltity(true);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((MainActivity)getActivity()).showUpButton();

        ((MainActivity)getActivity()).SetNavigationVisibiltity(true);



        ProgressDialog pdLoading = new ProgressDialog(getContext());
        etEmail = view.findViewById(magrow.project.application.R.id.username);
        etPassword = view.findViewById(magrow.project.application.R.id.password);
        ip =  view.findViewById(magrow.project.application.R.id.cur_net);
        login = view.findViewById(magrow.project.application.R.id.button);
        forget = view.findViewById(magrow.project.application.R.id.forget);
        register =  view.findViewById(magrow.project.application.R.id.register);
        etUsernameLayout = view.findViewById(magrow.project.application.R.id.etUsernameLayout);
        etPasswordLayout =  view.findViewById(magrow.project.application.R.id.etPasswordLayout);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                Register nextFragment = new Register();
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

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(MainActivity.this,Forget.class);
                startActivity(intent);
                MainActivity.this.finish();
                */
                Bundle bundle = new Bundle();
                ForgotPassword nextFragment = new ForgotPassword();
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
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                checkLogin();

            }
        });
        return view;
    }
    public void checkLogin(){

        // Get text from email and passord field
        final String email = etEmail.getText().toString();
        username=email;
        final String password = md5(etPassword.getText().toString());
        //Toast.makeText(MainActivity.this, password,Toast.LENGTH_SHORT).show();
        // Initialize  AsyncLogin() class with email and password
        if(validate(email,etPassword.getText().toString())){

            new AsyncLogin().execute(email, password);
        }
    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
         ProgressDialog pdLoading = new ProgressDialog(getContext());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLogging in...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("https://magrow.000webhostapp.com/android_login.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            //res=result;

             if (result.equalsIgnoreCase("Incorrect Password!"))
            {
                // If username and password does not match display a error message
                etPasswordLayout.setError("Incorrect Password!");
                //Message.message(getApplicationContext(), "Invalid username or password");

            }
            else if (result.equalsIgnoreCase("Account does not exists!"))
            {
                // If username and password does not match display a error message
                etUsernameLayout.setError("Account does not exists!");
                //Message.message(getApplicationContext(), "Invalid username or password");

            }

            else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                Toast.makeText(getContext(),"OOPs! Something went wrong. Connection Problem"+"\n"+"Please check your network",Toast.LENGTH_SHORT).show();
                //Message.message(getApplicationContext(), "OOPs! Something went wrong. Connection Problem"+"\n"+"Please check your network");

            }
            else{
                 //SharedPreferences sharedPref = getContext().getSharedPreferences("MyPREFERENCES",0);
                 SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
                try {
                    //JSONObject myJson = new JSONObject(result);
                    //String username = myJson.optString("username");
                    SharedPreferences.Editor editor = sharedPref.edit();

                    JSONArray jsonarray = new JSONArray(result);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String id = jsonobject.getString("id");
                        String username = jsonobject.getString("username");
                        String password = jsonobject.getString("password");
                        String email = jsonobject.getString("email");
                        String name = jsonobject.getString("name");
                       // String url = jsonobject.getString("url");
                        editor.putString(idKey, id);
                        editor.putString(usernameKey, username);
                        editor.putString(passwordKey, password);
                        editor.putString(emailKey, email);
                        editor.putString(nameKey, name);

                        editor.commit();
                        Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        //String check=sharedPref.getString("usernameKey", null); // getting String
                        //Toast.makeText(getContext(), "check "+check, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        Logged_in nextFragment = new Logged_in();
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
                    /**/


                }
                catch (JSONException e){
                    pdLoading.dismiss();
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }

// use myJson as needed, for example


            }
        }

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
    private boolean validate(String username, String password) {

        // Reset errors.

        etUsernameLayout.setError(null);
        etPasswordLayout.setError(null);


        if (Utils.isEmpty(username)) {
            etUsernameLayout.setError("Username is required");
            //Toast.makeText(Register.this, "Email req",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (Utils.isEmpty(password)) {
            etPasswordLayout.setError("Password is required");
            //Toast.makeText(Register.this, "Please complete form",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
