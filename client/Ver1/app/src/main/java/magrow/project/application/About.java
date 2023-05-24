package magrow.project.application;

import android.app.FragmentManager;
import android.media.Image;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import magrow.project.application.BuildConfig;

public class About extends AppCompatActivity {

    TextView version,libary,reference;
    ImageButton librarybtn,referencebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        version=(TextView)findViewById(R.id.version);
        libary=(TextView)findViewById(R.id.library);
        librarybtn=(ImageButton)findViewById(R.id.librarybtn);
        reference=(TextView)findViewById(R.id.reference);
        referencebtn=(ImageButton)findViewById(R.id.referencebtn);

        libary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Library dialog = new Library();
                dialog.show(getSupportFragmentManager(), "tag");
            }
        });

        librarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Library dialog = new Library();
                dialog.show(getSupportFragmentManager(), "tag");

            }
        });

        reference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reference dialog = new Reference();
                dialog.show(getSupportFragmentManager(), "tag");
            }
        });

        referencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reference dialog = new Reference();
                dialog.show(getSupportFragmentManager(), "tag");
            }
        });











        String versionCode = BuildConfig.VERSION_NAME;
        version.setText(versionCode);

    }

    public boolean onOptionsItemSelected(MenuItem item){

        finish();
        return true;
    }
}
