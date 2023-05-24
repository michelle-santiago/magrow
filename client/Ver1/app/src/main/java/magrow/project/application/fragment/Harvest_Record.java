package magrow.project.application.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import magrow.project.application.MainActivity;
import magrow.project.application.R;
import magrow.project.application.Sync_Record;
import magrow.project.application.myDbAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Harvest_Record extends Fragment {

    static final int NUM_ITEMS = 2;
    ViewPager viewPager;
    FloatingActionButton sync;
    private ViewPagerAdapter mAdapter;
    private String[] tabs = { "Onion", "Rice"};
    FloatingActionButton fab;
    myDbAdapter helper;
    String operation1="";

    public Harvest_Record() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_activity_harvest__record,
                container, false);

        ((MainActivity) getActivity())
                .setActionBarTitle("Crops");

        ((MainActivity)getActivity()).hideUpButton();

        helper = new myDbAdapter(getContext());

        fab=view.findViewById(R.id.fab);

        viewPager=(ViewPager)view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Sync_Record.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new Rice_record(), "Rice");
        viewPagerAdapter.addFragment(new Onion_record(), "Onion");

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