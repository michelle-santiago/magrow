package magrow.project.application.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPassword extends Fragment {

    ImageButton back;
    Button confirm;
    EditText email;
    public static String email_forgotpass="";

    String url="https://magrow.000webhostapp.com/android_checkemail.php";

    public ForgotPassword() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password,
                container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);

        back=(ImageButton)view.findViewById(R.id.back);
        confirm=(Button)view.findViewById(R.id.confirm);
        email=(EditText)view.findViewById(R.id.email);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email1=email.getText().toString();

                if(!email1.isEmpty())
                {
                    Boolean check=isEmailValid(email1);
                    final ProgressDialog pdLoading = new ProgressDialog(getContext());
                    if(check)
                    {

                        pdLoading.setMessage("\tChecking email...");
                        pdLoading.setCancelable(false);
                        pdLoading.show();

                        RequestQueue queue = Volley.newRequestQueue(getContext());
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pdLoading.dismiss();

                                //Message.message(getContext(),response);
                                if(response.equalsIgnoreCase("failed"))
                                {
                                    Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                            "Email does not exists", Snackbar.LENGTH_LONG);
                                    snackBar.show();
                                }
                                else if(response.equalsIgnoreCase("success"))
                                {
                                    email_forgotpass=email1;
                                    Bundle bundle = new Bundle();
                                    PasswordEnterKey nextFragment = new PasswordEnterKey();
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
                                map.put("email", email1);


                                return map;
                            }
                        };
                        queue.add(request);
                    }
                    else
                    {
                        Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "Invalid Email Id", Snackbar.LENGTH_LONG);
                        snackBar.show();
                    }
                }


            }
        });





        return view;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
