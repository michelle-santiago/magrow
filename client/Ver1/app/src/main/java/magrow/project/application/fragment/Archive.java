package magrow.project.application.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import magrow.project.application.MainActivity;
import magrow.project.application.R;
import magrow.project.application.myDbAdapter;

public class Archive extends Fragment {


    public Archive() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    myDbAdapter helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_archive,
                container, false);

        ((MainActivity)getActivity()).SetNavigationVisibiltity(false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        helper = new myDbAdapter(getContext());

        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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


        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        Archive.ViewPagerAdapter viewPagerAdapter = new Archive.ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Crop_pane2(), "Rice");
        viewPagerAdapter.addFragment(new Onion_pane2(), "Onion");

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

}