package ru.infocom_s.propotype;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends DrawerActivity implements FragmentLogin.DoLogin {


    @Override
    protected int getResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationView navigationView = (NavigationView) findViewById(R.id.n_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            if (checkLogin()) {
                fragment = new FragmentNews();

                navigationView.getMenu().findItem(R.id.nav_news).setChecked(true);
            } else {
                setDrawerState(false);
                fragment = new FragmentLogin();
            }

            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

        TextView tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewName);
        tv.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(DrawerActivity.KEY_LOGIN, ""));
    }
}
