package magrow.project.application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import magrow.project.application.R;
import magrow.project.application.fragment.Crop_pane;
import magrow.project.application.fragment.Onion_pane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Sync_Record extends AppCompatActivity {
    Button syncToWeb,syncMobile,copy,edit,web;
    TextView privacy;
    myDbAdapter helper;
    TextInputEditText link;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    public static final String privacyKey= "privacyKey";
    private ProgressDialog pdLoading = null;
    String url="https://magrow.000webhostapp.com/android_sync.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync__record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        syncMobile=findViewById(R.id.syncMobile);
        syncToWeb=findViewById(R.id.syncToWeb);
        copy=findViewById(R.id.copy);
        link=findViewById(R.id.link);
        web=findViewById(R.id.web);
        privacy=findViewById(R.id.privacy);
        edit=findViewById(R.id.edit);
        helper = new myDbAdapter(getApplicationContext());
        Cursor dbres_privacy = helper.checkPrivacyTb();
        if (dbres_privacy.getCount() == 0) {
            privacy.setText("Not Set");

        }
        else
        {  while (dbres_privacy.moveToNext()) {
            String check = String.format(dbres_privacy.getString(0));
            if(check.equals("0")){
                privacy.setText("Private");
            }
            else {
                privacy.setText("Public");
            }

            edit.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(),"hey",Toast.LENGTH_SHORT).show();
        }


        }
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://magrow.000webhostapp.com"));
                startActivity(intent);

            }
        });
        helper = new myDbAdapter(getApplicationContext());
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        copy.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        myClip = ClipData.newPlainText("text", link.getText());
                                        myClipboard.setPrimaryClip(myClip);
                                        Toast.makeText(getApplicationContext(), "Copied",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
        Context context= Sync_Record.this;

        final SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        final String usernameKey=sharedPref.getString("usernameKey", null);
        String emailKey=sharedPref.getString("emailKey", null);
        String passwordKey=sharedPref.getString("passwordKey", null);
        if(emailKey==null&&usernameKey==null&&passwordKey==null){
            Toast.makeText(getApplication(), "Sign in First", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //intent.putExtra("status",status_content1);
            //getApplicationContext().startActivity(intent);
            startActivity(intent);
            finish();
            //redirect to login or dialog login?

        }




        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                        v.getContext(), R.style.MyAlertDialogTheme2);

                alertDialog.setTitle("Data Sharing");

                final TextView mess = new TextView(v.getContext());
                mess.setText("\t\t\t Do you want to share your \n\t\t\t data for public viewing?");
                mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6F, v.getContext().getResources().getDisplayMetrics()));

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mess.setLayoutParams(lp);
                alertDialog.setView(mess);

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long result = helper.updatePrivacy("1");
                        if(result != -1)
                        {

                            long result2 = helper.updatePrivacyTb("1");
                            if(result2!=-1){
                                Toast.makeText(getApplication(), "Privacy set to Public", Toast.LENGTH_SHORT).show();
                                privacy.setText("Public");
                            }
                        }
                        else{
                            Toast.makeText(getApplication(), "Privacy not set", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                alertDialog.setNegativeButton("No",

                        new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog,

                                                int which) {
                                dialog.cancel();
                                long result = helper.updatePrivacy("0");
                                if(result != -1)
                                {
                                    long result2 = helper.updatePrivacyTb("0");
                                    if(result2!=-1){
                                        Toast.makeText(getApplication(), "Privacy set to Private", Toast.LENGTH_SHORT).show();
                                        privacy.setText("Private");
                                    }


                                }
                                else{
                                    Toast.makeText(getApplication(), "Privacy not set", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });
                alertDialog.show();
            }
        });
        syncToWeb.setOnClickListener(new View.OnClickListener() {


            ProgressDialog pdLoading = new ProgressDialog(Sync_Record.this);

            @Override
            public void onClick(View v) {
                String priv = privacy.getText().toString();
                //String privacyKey2=sharedPref.getString("privacyKey", null);
                if (priv.equals("Not Set")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(

                            v.getContext(), R.style.MyAlertDialogTheme2);

                    alertDialog.setTitle("Data Sharing");

                    final TextView mess = new TextView(v.getContext());
                    mess.setText("\t\t\t Do you want to share your \n\t\t\t data for public viewing?");
                    mess.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 6F, v.getContext().getResources().getDisplayMetrics()));

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    mess.setLayoutParams(lp);
                    alertDialog.setView(mess);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            long result = helper.updatePrivacy("1");
                            if(result != -1)
                            {


                                privacy.setText("Public");
                                long result2=helper.insertPrivacyTb("Public");
                                if(result2!=-1){
                                    Toast.makeText(getApplication(), "Privacy set to Public", Toast.LENGTH_SHORT).show();
                                }
                                edit.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(getApplication(), "Privacy not set", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    alertDialog.setNegativeButton("No",

                            new DialogInterface.OnClickListener() {

                                @Override

                                public void onClick(DialogInterface dialog,

                                                    int which) {

                                    long result = helper.updatePrivacy("0");
                                    if(result != -1)
                                    {

                                        privacy.setText("Private");
                                        long result2=helper.insertPrivacyTb("Private");
                                        if(result2!=-1){
                                            Toast.makeText(getApplication(), "Privacy set to Private", Toast.LENGTH_SHORT).show();
                                        }
                                        edit.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        Toast.makeText(getApplication(), "Privacy not set", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                    alertDialog.show();
                }
                else{
                    Cursor dbres1 = helper.getFarmDetails();
                    JSONObject farmObj = new JSONObject();
                    while (dbres1.moveToNext()) {
                        String fname = String.format(dbres1.getString(1));
                        String flocation = String.format(dbres1.getString(2));
                        Double farea = Double.parseDouble(dbres1.getString(3));
                        String fmeasure = String.format(dbres1.getString(4));
                        try {
                            farmObj.put("fname", fname);
                            farmObj.put("flocation", flocation);
                            farmObj.put("farea", farea);
                            farmObj.put("fmeasure", fmeasure);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    Cursor dbres2 = helper.getCrops();
                    final JSONArray jsonCropArray = new JSONArray();
                    while (dbres2.moveToNext()) {
                        int c_id = Integer.parseInt(dbres2.getString(0));
                        String crop_name = String.format(dbres2.getString(1));
                        String crop = String.format(dbres2.getString(2));
                        String variety = String.format(dbres2.getString(3));
                        String season= String.format(dbres2.getString(4));
                        String date_added= String.format(dbres2.getString(8));
                        int privacy= Integer.parseInt(dbres2.getString(9));
                        JSONObject cropObj = new JSONObject();
                        try {
                            cropObj.put("c_id", c_id);
                            cropObj.put("crop_name", crop_name);
                            cropObj.put("crop", crop);
                            cropObj.put("variety", variety);
                            cropObj.put("season",season );
                            cropObj.put("date_added",date_added );
                            cropObj.put("privacy",privacy );
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonCropArray.put(cropObj);
                        //System.out.println("jsoncroparray: "+jsonCropArray);

                    }
                    Cursor dbres3 = helper.getAllRecordLog();
                    final JSONArray jsonCropRecordArray = new JSONArray();
                    while (dbres3.moveToNext()) {
                        int r_id = Integer.parseInt(dbres3.getString(0));
                        int r_cid = Integer.parseInt(dbres3.getString(1));
                        Double area = Double.parseDouble(dbres3.getString(4));
                        String ah_m = String.format(dbres3.getString(5));
                        Double area_planted= Double.parseDouble(dbres3.getString(2));
                        String ap_m= String.format(dbres3.getString(3));
                        Double no_of_cavans_harvested= Double.parseDouble(dbres3.getString(6));
                        Double weight_per_cavan= Double.parseDouble(dbres3.getString(7));
                        JSONObject cropRecordObj = new JSONObject();
                        try {
                            cropRecordObj.put("r_id", r_id);
                            cropRecordObj.put("r_cid", r_cid);
                            cropRecordObj.put("area_planted",area_planted);
                            cropRecordObj.put("ap_m",ap_m);
                            cropRecordObj.put("area", area);
                            cropRecordObj.put("ah_m", ah_m);
                            cropRecordObj.put("no_of_cavans", no_of_cavans_harvested);
                            cropRecordObj.put("weight", weight_per_cavan);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonCropRecordArray.put(cropRecordObj);
                    }
                    Cursor dbres4 = helper.getAllData();
                    final JSONArray jsonCropCostRecordArray = new JSONArray();
                    while (dbres4.moveToNext()) {
                        int eid = Integer.parseInt(dbres4.getString(0));
                        int ecid = Integer.parseInt(dbres4.getString(8));
                        double date_time = Double.parseDouble(dbres4.getString(1));
                        int color = Integer.parseInt(dbres4.getString(2));
                        String event= String.format(dbres4.getString(3));
                        String date_start= String.format(dbres4.getString(4));
                        String time_event= String.format(dbres4.getString(5));
                        int date_id = Integer.parseInt(dbres4.getString(6));
                        int icon = Integer.parseInt(dbres4.getString(7));
                        String operation_id= String.format(dbres4.getString(9));
                        String operation_status= String.format(dbres4.getString(10));
                        String priority= String.format(dbres4.getString(11));
                        Double amount= Double.parseDouble(dbres4.getString(12));
                        JSONObject cropCostRecordObj = new JSONObject();
                        try {
                            cropCostRecordObj.put("eid", eid);
                            cropCostRecordObj.put("ecid", ecid);
                            cropCostRecordObj.put("date_time", date_time);
                            cropCostRecordObj.put("color", color);
                            cropCostRecordObj.put("event", event);
                            cropCostRecordObj.put("date_start",date_start);
                            cropCostRecordObj.put("time_event",time_event);
                            cropCostRecordObj.put("date_id",date_id);
                            cropCostRecordObj.put("icon",icon);
                            cropCostRecordObj.put("operation_id",operation_id);
                            cropCostRecordObj.put("operation_status",operation_status);
                            cropCostRecordObj.put("priority",priority);
                            cropCostRecordObj.put("amount",amount);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        jsonCropCostRecordArray.put(cropCostRecordObj);

                    }
                    final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JSONObject Obj = new JSONObject();
                    try{
                        Obj.put("Username", usernameKey);
                        Obj.put("Farm", farmObj);
                        Obj.put("Crop", jsonCropArray);
                        Obj.put("Record", jsonCropRecordArray);
                        Obj.put("Event", jsonCropCostRecordArray);
                        //Obj.put("Test", 1);


                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    final String jsonStr = Obj.toString();

                    System.out.println("jsonString: "+jsonStr);
                    //Toast.makeText(getApplication(), ""+jsonStr, Toast.LENGTH_SHORT).show();
                    //Log.i("LOG_VOLLEY", response);
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();
                /*if (jsonCropArray.length() == 0 || jsonCropRecordArray.length() == 0) {
                    pdLoading.dismiss();
                    Toast.makeText(getApplication(), "Add Record First for Syncing", Toast.LENGTH_SHORT).show();
                } else {*/
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pdLoading.dismiss();
                            Log.i("LOG_VOLLEY", response);
                            Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                    response, Snackbar.LENGTH_LONG);
                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                    snack.getView().getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            snack.getView().setLayoutParams(params);
                            snack.show();
                            /*try {
                                JSONObject jsonarray = new JSONObject(response);
                                System.out.println("hm "+jsonarray);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String id = jsonobject.getString("id");
                                    //System.out.println("id: "+id);

                                    //(Toast.makeText(getApplicationContext(), "huy" + id+jsonarray.length(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }*/

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pdLoading.dismiss();
                            Log.e("LOG_VOLLEY", error.toString());
                            Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                    "Failed to connect to Network", Snackbar.LENGTH_LONG);
                            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                    snack.getView().getLayoutParams();
                            params.setMargins(0, 0, 0, 0);
                            snack.getView().setLayoutParams(params);
                            snack.show();
                            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return jsonStr == null ? null : jsonStr.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonStr, "utf-8");
                                Snackbar snack = Snackbar.make(findViewById(R.id.coordinatorLayout),
                                        "Unsupported Encoding", Snackbar.LENGTH_LONG);
                                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                                        snack.getView().getLayoutParams();
                                params.setMargins(0, 0, 0, 0);
                                snack.getView().setLayoutParams(params);
                                snack.show();
                                return null;
                            }
                        }

                    };
                    queue.add(request);


                //}
            }
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
