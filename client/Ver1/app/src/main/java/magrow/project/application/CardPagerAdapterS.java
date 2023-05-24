package magrow.project.application;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import magrow.project.application.R;

import java.util.ArrayList;
import java.util.List;

// public class CardPagerAdapterS {


public class CardPagerAdapterS extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItemString> mData;
    private float mBaseElevation;

    public CardPagerAdapterS() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItemS(CardItemString item) {
        mViews.add(null);
        mData.add(item);
    }



    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItemString item, View view) {

        ImageView status=(ImageView)view.findViewById(R.id.status);
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        TextView crop = (TextView) view.findViewById(R.id.crop);
        TextView sub = (TextView) view.findViewById(R.id.sub);
        ImageView crop_type=(ImageView)view.findViewById(R.id.crop_type);
        ImageView crop_activity=(ImageView)view.findViewById(R.id.crop_activity);


        int a = item.getActivity_type();




        String type=item.getCrop_type();

        if(type.equalsIgnoreCase("rice"))
        {
           crop_type.setImageResource(R.drawable.ricev2);
        }
        else if (type.equalsIgnoreCase("onion"))
        {
           crop_type.setImageResource(R.drawable.onionv2);
        }

        String temp=item.getTitle();

        if(!temp.equals("0"))
        {
            String ops_img=String.valueOf(item.getActivity_type());
            switch (ops_img)
            {
                case "1":
                    crop_activity.setImageResource(R.drawable.mm1);
                    break;

                case "2":
                    crop_activity.setImageResource(R.drawable.mm2);
                    break;

                case "3":
                    crop_activity.setImageResource(R.drawable.mm3);
                    break;

                case "4":
                    crop_activity.setImageResource(R.drawable.mm4);
                    break;

                case "5":
                    crop_activity.setImageResource(R.drawable.mm5);
                    break;

                case "6":
                    crop_activity.setImageResource(R.drawable.mm6);
                    break;

                case "7":
                    crop_activity.setImageResource(R.drawable.mm7);
                    break;
            }
        }


        if(a==0)
        {
            sub.setText("");
        }
        /*else if(a==1)
        {
            crop_activity.setImageResource(R.drawable.land_iconv2);

        }
        else if(a==2)
        {
            crop_activity.setImageResource(R.drawable.crop_iconv2);
        }
        else if(a==3)
        {
            crop_activity.setImageResource(R.drawable.care_iconv2);
        }
        else if(a==4)
        {
            crop_activity.setImageResource(R.drawable.pest_iconv2);
        }
        else if(a==5)
        {
            crop_activity.setImageResource(R.drawable.harvest_iconv2);
        }
        else if(a==6)
        {
            crop_activity.setImageResource(R.drawable.others_iconv2);
        }*/

        else
        {
            sub.setText("of");
        }


        crop_activity.setScaleType(ImageView.ScaleType.FIT_XY);



        if(item.getTitle().equals("0"))
        {
            titleTextView.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);
        }
        else
        {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(item.getTitle());
            status.setVisibility(View.GONE);
        }
        contentTextView.setText(item.getText());
        crop.setText(item.getCrop());
    }

    public String setOps_img(String title)
    {
        String status="";

        switch (title)
        {
            case "Plowing":
                status="1";
                break;
            case "Harrowing":
                status="1";
                break;
            case "Levelling":
                status="1";
                break;
            case "Transplanting":
                status="2";
                break;
            case "Soaking":
                status="2";
                break;
            case "Pulling of Seedlings":
                status="2";
                break;
            case "Direct Seeding":
                status="2";
                break;
            case "Seedlings Planting":
                status="2";
                break;
            case "Irrigation":
                status="3";
                break;
            case "Water Pump":
                status="3";
                break;
            case "Weeding":
                status="3";
                break;
            case "Molluscicide":
                status="4";
                break;
            case "Herbicide":
                status="4";
                break;
            case "Fungicide":
                status="4";
                break;
            case "Reaping":
                status="5";
                break;
            case "Threshing":
                status="5";
                break;
            case "Transportation":
                status="5";
                break;
            case "Drying":
                status="5";
                break;
            case "Harvesting":
                status="5";
                break;
            case "Milling":
                status="5";
                break;
            default:
                status="6";
                break;
        }

        return status;
    }

}

