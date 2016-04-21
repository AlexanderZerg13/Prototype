package ru.infocom_s.propotype;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

public class FragmentSchedule extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.title_schedule);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        ViewPager mViewPager = (ViewPager) v.findViewById(R.id.scheduleViewPager);
        mViewPager.setAdapter(new ScheduleViewPager(getChildFragmentManager()));

        Calendar calendar = Calendar.getInstance();
        mViewPager.setCurrentItem((7 + calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7);
        return v;
    }

    private class ScheduleViewPager extends FragmentPagerAdapter {

        public ScheduleViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentLessons.newInstance(position);
        }

        @Override
        public int getCount() {
            return 12;
        }
    }
}
