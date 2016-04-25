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
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class FragmentSchedule extends Fragment implements FragmentLessons.changeViewPagerState {

    private static final int REQUEST_DATE = 0;

    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.title_schedule);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        mViewPager = (ViewPager) v.findViewById(R.id.scheduleViewPager);
        mViewPager.setAdapter(new ScheduleViewPager(getChildFragmentManager()));

        Calendar calendar = Calendar.getInstance();
        mViewPager.setCurrentItem((7 + calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7);
        return v;
    }

    @Override
    public void setPage(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar1 = Calendar.getInstance();
        int add = 0;
        if (calendar1.get(Calendar.WEEK_OF_MONTH) < calendar.get(Calendar.WEEK_OF_MONTH)) {
            add++;
        }
//        Toast.makeText(getActivity(), calendar.get(Calendar.DAY_OF_WEEK) + " " + ((7 + calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7 + add * 6), Toast.LENGTH_SHORT).show();

        mViewPager.setCurrentItem((7 + calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7 + add * 6);
    }

    private class ScheduleViewPager extends FragmentPagerAdapter {

        public ScheduleViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FragmentLessons fragmentLessons = FragmentLessons.newInstance(position);
            fragmentLessons.setTargetFragment(FragmentSchedule.this, REQUEST_DATE);
            return fragmentLessons;
        }

        @Override
        public int getCount() {
            return 12;
        }
    }
}
