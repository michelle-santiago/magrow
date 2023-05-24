package magrow.project.application;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import magrow.project.application.R;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends DialogFragment {


    private static final String TAG = "ChangePassword";
    String url="https://magrow.000webhostapp.com/android_changepassword.php";
    Button cancel,confirm;
    TextInputLayout et_curr_pass,et_new_pass1,et_new_pass2;
    EditText curr_pass,new_pass1,new_pass2;
    public static String username="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_change_password, container, false);


        cancel=(Button)view.findViewById(R.id.cancel);
        confirm=(Button)view.findViewById(R.id.confirm);

        curr_pass=view.findViewById(R.id.curr_pass);
        new_pass1=view.findViewById(R.id.new_pass1);
        new_pass2=view.findViewById(R.id.new_pass2);

        et_curr_pass=view.findViewById(R.id.et_curr_pass);
        et_new_pass1=view.findViewById(R.id.et_new_pass1);
        et_new_pass2=view.findViewById(R.id.et_new_pass2);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String curr=curr_pass.getText().toString();
                final String new1=new_pass1.getText().toString();
                final String new2=new_pass2.getText().toString();

                //Message.message(getContext(),username);
                final ProgressDialog pdLoading = new ProgressDialog(getContext());
                if(validate(curr,new1,new2))
                {
                    //this method will be running on UI thread
                    pdLoading.setMessage("\tProcessing...");
                    pdLoading.setCancelable(false);
                    pdLoading.show();

                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pdLoading.dismiss();

                            if(response.equalsIgnoreCase("Incorrect Password"))
                            {

                                et_curr_pass.setError("Incorrect Password");
                                Message.message(getContext(),response);
                            }

                            else if(response.equalsIgnoreCase("Password has been changed"))
                            {

                                Message.message(getContext(),response);
                                dismiss();
                            }

                            if(response.equalsIgnoreCase("Failed"))
                            {
                                Message.message(getContext(),"Something went wrong, Please try again");
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
                            map.put("username", username);
                            map.put("password", curr);
                            map.put("password2", new1);

                            return map;
                        }
                    };
                    queue.add(request);
                }


            }

        });

return view;
    }

    private boolean validate(String curr_password, String password, String c_password) {

        // Reset errors.
        et_curr_pass.setError(null);
        et_new_pass1.setError(null);
        et_new_pass2.setError(null);


        if (Utils.isEmpty(curr_password)) {
            et_curr_pass.setError("Password is required");
            return false;
        }


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