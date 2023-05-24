package magrow.project.application.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import magrow.project.application.Message;
import magrow.project.application.R;
import magrow.project.application.Utils;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPassword extends Fragment {

    String url="https://magrow.000webhostapp.com/android_reset_password.php";

    Button confirm;
    TextInputLayout et_new_pass1,et_new_pass2;
    EditText new_pass1,new_pass2;
    ImageButton back;

    public ResetPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password,
                container, false);


        confirm=(Button)view.findViewById(R.id.confirm);
        new_pass1=view.findViewById(R.id.new_pass1);
        new_pass2=view.findViewById(R.id.new_pass2);
        et_new_pass1=view.findViewById(R.id.et_new_pass1);
        et_new_pass2=view.findViewById(R.id.et_new_pass2);
        back=(ImageButton)view.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String new1=new_pass1.getText().toString();
                final String new2=new_pass2.getText().toString();
                final String email=ForgotPassword.email_forgotpass;

                final ProgressDialog pdLoading = new ProgressDialog(getContext());
                if(validate(new1,new2))
                {
                    //this method will be running on UI thread
                    pdLoading.setMessage("\tResetting Password...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();

                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pdLoading.dismiss();

                            Message.message(getContext(),response);
                            if(response.equalsIgnoreCase("Password has been reset"))
                            {
                                Message.message(getContext(),response);
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

                            else if(response.equalsIgnoreCase("Failed"))
                            {

                                Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                        "Failed, Please try again", Snackbar.LENGTH_LONG);
                                snackBar.show();

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
                            map.put("email", email);
                            map.put("password", new1);


                            return map;
                        }
                    };
                    queue.add(request);
                }
            }
        });

        return view;
    }

    private boolean validate(String password, String c_password) {

        // Reset errors.
        et_new_pass1.setError(null);
        et_new_pass2.setError(null);



        if (Utils.isEmpty(password)) {
            et_new_pass1.setError("Password is required");

            return false;
        }

        if (Utils.isEmpty(c_password)) {
            et_new_pass2.setError("Password is required");

            return false;
        }

        else if (!Utils.isPasswordValid(password)) {
            et_new_pass1.setError("Password must contain at least 8 characters");
            return false;
        }
        else if (!Utils.isPasswordValid(c_password)) {
            et_new_pass2.setError("Password must contain at least 8 characters");
            return false;
        }

        else if (!password.equals(c_password)) {
            et_new_pass2.setError("Passwords didn't match. Try again");

            return false;
        }
        return true;
    }

}
