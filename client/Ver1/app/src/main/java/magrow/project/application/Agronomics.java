package magrow.project.application;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import magrow.project.application.R;

public class Agronomics extends DialogFragment {

    TextView crop_variety,source;
    ImageView crop_type;

    String ave_yield="";
    String max_yield="";
    String height="";
    String maturity="";

    String ave_yield2="";
    String max_yield2="";
    String height2="";
    String maturity2="";

    String envi="";
    int days=0;


    TextView title1,title2,a_yield1,a_yield2,m_yield1,m_yield2,h_crop1,h_crop2,m_crop1,m_crop2,environment;
    LinearLayout pane1,pane2,envi_pane;
    Button close;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_agronomics, container, false);

        crop_variety=(TextView)view.findViewById(R.id.crop_variety);
        crop_type=(ImageView)view.findViewById(R.id.crop_type);

        title1=(TextView)view.findViewById(R.id.title1);
        title2=(TextView)view.findViewById(R.id.title2);
        a_yield1=(TextView)view.findViewById(R.id.a_yield1);
        a_yield2=(TextView)view.findViewById(R.id.a_yield2);
        m_yield1=(TextView)view.findViewById(R.id.m_yield1);
        m_yield2=(TextView)view.findViewById(R.id.m_yield2);
        h_crop1=(TextView)view.findViewById(R.id.h_crop1);
        h_crop2=(TextView)view.findViewById(R.id.h_crop2);
        m_crop1=(TextView)view.findViewById(R.id.m_crop1);
        m_crop2=(TextView)view.findViewById(R.id.m_crop2);
        environment=(TextView)view.findViewById(R.id.environment);

        source=(TextView)view.findViewById(R.id.source);

        pane1=(LinearLayout)view.findViewById(R.id.pane1);
        pane2=(LinearLayout)view.findViewById(R.id.pane2);
        envi_pane=(LinearLayout)view.findViewById(R.id.envi_pane);

        close=(Button)view.findViewById(R.id.close);



        Bundle mArgs = getArguments();
        String variety = mArgs.getString("variety");
        String crop = mArgs.getString("crop_type");

        crop_variety.setText(variety);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(crop.equalsIgnoreCase("rice"))
        {
            crop_type.setImageResource(R.drawable.rice_white);

        }

        else if(crop.equalsIgnoreCase("onion"))
        {
            crop_type.setImageResource(R.drawable.onion_white);

        }

        int stats=getVarietyData(variety);
        if(stats==3)
        {
            title1.setText("Normal Condition");
            a_yield1.setText(ave_yield);
            m_yield1.setText(max_yield);
            h_crop1.setText(height);
            m_crop1.setText(maturity+"");

            title2.setText("Submerged Condition");
            a_yield2.setText(ave_yield2);
            m_yield2.setText(max_yield2);
            h_crop2.setText(height2);
            m_crop2.setText(maturity2+"");

            environment.setText(envi);

        }
        else if(stats==2)
        {
            title1.setText("Transplanted");
            a_yield1.setText(ave_yield);
            m_yield1.setText(max_yield);
            h_crop1.setText(height);
            m_crop1.setText(maturity+"");

            title2.setText("Direct Seeded");
            a_yield2.setText(ave_yield2);
            m_yield2.setText(max_yield2);
            h_crop2.setText(height2);
            m_crop2.setText(maturity2+"");

            environment.setText(envi);
        }
        else if(stats==1)
        {
            title1.setText("");
            a_yield1.setText(ave_yield);
            m_yield1.setText(max_yield);
            h_crop1.setText(height);
            m_crop1.setText(maturity+"");

            title2.setVisibility(View.GONE);
            pane2.setVisibility(View.GONE);


            environment.setText(envi);
        }
        else if(stats==0)
        {
            title1.setText("No data available");
            pane1.setVisibility(View.GONE);

            title2.setVisibility(View.GONE);
            pane2.setVisibility(View.GONE);

            envi_pane.setVisibility(View.GONE);
        }



        return view;
    }

    public int getVarietyData(String variety)
    {

        int status=0;

        switch (variety)
        {

            //RICE************************************************************************************
            case "NSIC Rc160":
                status=2;
                source.setText("PhilRice, 2007");
                maturity="122 days";
                maturity2="107 days";

                ave_yield="-";
                max_yield="-";
                height="-";

                ave_yield2="5.6t/ha";
                max_yield2="8.2t/ha";
                height2="96cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc222":
                status=2;
                source.setText("IRRI, 2009");
                maturity="114 days";
                maturity2="106 days";

                ave_yield="6.1t/ha";
                max_yield="10t/ha";
                height="101cm";

                ave_yield2="5.7t/ha";
                max_yield2="7.9t/ha";
                height2="98cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc238":
                status=1;
                source.setText("IRRI, 2011");
                maturity="110 days";

                ave_yield="6.4t/ha";
                max_yield="10.6t/ha";
                height="104cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc216":
                status=2;
                source.setText("PhilRice, 2009");
                maturity="112 days";
                maturity2="104 days";

                ave_yield="6t/ha";
                max_yield="9.7t/ha";
                height="96cm";

                ave_yield2="5.7t/ha";
                max_yield2="9.3t/ha";
                height2="92cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc9":
                status=1;
                source.setText("IRRI, 2001");
                maturity="119 days";

                ave_yield="2.9t/ha";
                max_yield="5.5t/ha";
                height="98cm";

                envi="Upland";
                break;

            case "PSB Rc14":
                status=1;
                source.setText("UPLB, 1992");
                maturity="110 days";

                ave_yield="6.4t/ha";
                max_yield="10.6t/ha";
                height="104cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc18":
                status=1;
                source.setText("IRRI, 1994");
                maturity="123 days";

                ave_yield="6.4t/ha";
                max_yield="10.6t/ha";
                height="104cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc68":
                status=1;
                source.setText("IRRI, 1997");
                maturity="116 days";

                ave_yield="3.4t/ha";
                max_yield="4.4t/ha";
                height="116cm";

                envi="Rainfed Lowland";
                break;

            case "NSIC Rc194":
                status=3;
                source.setText("IRRI &amp; PhilRice, 2009");
                maturity="112 days";
                maturity2="125 days";

                ave_yield="3.5t/ha";
                max_yield="-";
                height="97cm";

                ave_yield2="2.5t/ha";
                max_yield2="-";
                height2="93cm";

                envi="Rainfed Lowland";
                break;

            case "NSIC Rc300":
                status=2;
                source.setText("PhilRice, 2012");
                maturity="115 days";
                maturity2="105 days";

                ave_yield="5.7t/ha";
                max_yield="10.4t/ha";
                height="98cm";

                ave_yield2="5.3t/ha";
                max_yield2="9t/ha";
                height2="96cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc402":
                status=2;
                source.setText("PhilRice, 2015");
                maturity="114 days";
                maturity2="107 days";

                ave_yield="-";
                max_yield="-";
                height="-";

                ave_yield2="5.5t/ha";
                max_yield2="14t/ha";
                height2="95cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc354":
                status=1;
                source.setText("PhilRice, 2014");
                maturity="112 days";

                ave_yield="5.4t/ha";
                max_yield="9t/ha";
                height="95cm";

                envi="Irrigated Lowland";
                break;

            case "NSIC Rc218":
                status=1;
                source.setText("PhilRice, 2009");
                maturity="120 days";

                ave_yield="3.8t/ha";
                max_yield="8t/ha";
                height="106cm";

                envi="Irrigated Lowland";
                break;

            case "PSB Rc10":
                status=1;
                source.setText("IRRI, 1992");
                maturity="106 days";

                ave_yield="4.8t/ha";
                max_yield="7.5t/ha";
                height="77cm";

                envi="Irrigated Lowland";
                break;

            //ONION************************************************************************************

            case "CX-12":
                status=1;
                maturity="110 days";
                source.setText("PSIA Seed Catalogue, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Grannex 429":
                status=1;
                maturity="95 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Red Creole":
                status=1;
                maturity="110 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Red Pinoy":
                status=1;
                maturity="110 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Rio Bravo":
                status=1;
                maturity="95 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Rio Hondo":
                status=1;
                maturity="95 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Rio Raji Red":
                status=1;
                maturity="110 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Rio Tinto":
                status=1;
                maturity="80 days";
                source.setText("Allied Botanical Corporation leaflet, 2013");
                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Super Pinoy":
                status=2;
                source.setText("Allied Botanical Corporation leaflet, 2013");
                maturity="125 days";
                maturity2="95 days";

                ave_yield="-";
                max_yield="-";
                height="-";

                ave_yield2="-";
                max_yield2="-";
                height2="-";

                envi="-";
                break;

            case "SuperX":
                status=1;
                source.setText("PSIA Seed Catalogue, 2013");
                maturity="160 days";

                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Texas Grano":
                status=1;
                source.setText("Allied Botanical Corporation leaflet, 2013");
                maturity="95 days";

                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            case "Yellow Grannex":
                status=1;
                source.setText("PSIA Seed Catalogue, 2013");
                maturity="95 days";

                ave_yield="-";
                max_yield="-";
                height="-";

                envi="-";
                break;

            default:
                status=0;
                break;


        }

        return status;
    }
}
