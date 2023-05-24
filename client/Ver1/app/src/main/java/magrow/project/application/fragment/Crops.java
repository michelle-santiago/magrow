package magrow.project.application.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.AddCrop2;
import magrow.project.application.MainActivity;
import magrow.project.application.Message;

import magrow.project.application.R;
import magrow.project.application.RecordDetails;
import magrow.project.application.Sync_Record;
import magrow.project.application.myDbAdapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Crops extends Fragment {

    static final int NUM_ITEMS = 2;
    ViewPager viewPager;
    private ViewPagerAdapter mAdapter;
    private String[] tabs = { "Onion", "Rice"};
    FloatingActionButton fab,fab7;
    myDbAdapter helper;
    String operation1="";
    String crop_type="";

    public Crops() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_activity_crops,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle("Crops");
        ((MainActivity)getActivity()).SetNavigationVisibiltity(true);

        ((MainActivity)getActivity()).hideUpButton();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        helper = new myDbAdapter(getContext());

//////////////////////////CHANGE STATUS OF OPERATIONS

        changeStatus();

////////////////////////














        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Message.message(getContext(),String.valueOf(position));
                if(position==0)
                {
                    crop_type="rice";
                }
                else if(position==1)
                {
                    crop_type="onion";
                }
            }

            @Override
            public void onPageSelected(int position) {
                //Message.message(getContext(),String.valueOf(position));
                if(position==0)
                {
                    crop_type="rice";
                }
                else if(position==1)
                {
                    crop_type="onion";
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab=(FloatingActionButton)view.findViewById(R.id.fab);
        //fab7=view.findViewById(R.id.fab7);


        try
        {
            String crop_type= this.getArguments().getString("crop_type");
            String status= this.getArguments().getString("status");
            if(status.equals("success"))
            {
                //Snackbar.make(view.findViewById(android.R.id.content),"Crop Updated",Snackbar.LENGTH_SHORT).show();

                Snackbar snack = Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                        "Crop Updated", Snackbar.LENGTH_LONG);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                        snack.getView().getLayoutParams();
                params.setMargins(0, 0, 0, 0);
                snack.getView().setLayoutParams(params);
                snack.show();
            }
            if(crop_type.equals("onion"))
            {
                viewPager.setCurrentItem(1);
            }

        }
        catch (NullPointerException e)
        {

        }



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("crop",crop_type); // set your parameteres
                AddCrop nextFragment = new AddCrop();
                nextFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Replace fragment one with fragment two, the second fragment tag name is "Fragment Two".
                // This action will remove Fragment one and add Fragment two.
                fragmentTransaction.replace(R.id.container, nextFragment, "Fragment Five");

                // Add fragment one in back stack.So it will not be destroyed. Press back menu can pop it up from the stack.
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();

                /*Intent intent = new Intent(v.getContext(), AddCrop2.class);
                intent.putExtra("crop",crop_type);
                v.getContext().startActivity(intent);*/
            }
        });

        /*fab7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Sync_Record.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });*/
        return view;
    }

   private void setupViewPager(ViewPager viewPager)
   {
       ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
    viewPagerAdapter.addFragment(new Crop_pane(), "Rice");
    viewPagerAdapter.addFragment(new Onion_pane(), "Onion");

    viewPager.setAdapter(viewPagerAdapter);
    viewPager.setOffscreenPageLimit(1);
   }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }

    }

    public void changeStatus()
    {
        String operation1="";
        Calendar cal = Calendar.getInstance();
        long dateChecker = cal.getTimeInMillis();
        String epochTime=String.valueOf(dateChecker);

        Cursor dbres_update = helper.getEventList(epochTime,"0");
        if(dbres_update.getCount() != 0)
        {
            while (dbres_update.moveToNext()) {

                String trans_log_id1=String.format(dbres_update.getString(0));
                String crop_id1=String.format(dbres_update.getString(8));
                operation1=String.format(dbres_update.getString(3));

                int a= helper.updateEventLog(trans_log_id1,"1");
                if(a<=0)
                {
                //    Message.message(getContext(),"Unsuccessful");
                }
                else
                {
              //      Message.message(getContext(),"Success");
                }
                String new_status="Completed "+operation1;
                int b= helper.updateCropStatus(crop_id1,new_status);
                if(b<=0)
                {
                 //   Message.message(getContext(),"Unsuccessful");
                }
                else
                {
               //     Message.message(getContext(),"Success");
                }

            }

        }
    }

}

