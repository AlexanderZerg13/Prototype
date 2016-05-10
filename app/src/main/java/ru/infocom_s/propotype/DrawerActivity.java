package ru.infocom_s.propotype;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static String KEY_LOGIN = "login";
    public static String KEY_PASSWORD = "password";

    protected static String LOGIN = "user";
    protected static String PASSWORD = "123";

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getResource());

        Toolbar myToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //NavDrawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open_menu, R.string.close_menu);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logOut() {
        setDrawerState(false);
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().remove(KEY_LOGIN).remove(KEY_PASSWORD).commit();
        Fragment fragment = new FragmentLogin();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    protected boolean checkLogin() {
        SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);
        if (shp.getString(KEY_LOGIN, "").equals(LOGIN) && shp.getString(KEY_PASSWORD, "").equals(PASSWORD)) {
            return true;
        }
        return false;
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;

        switch (id) {
            case R.id.nav_news:
                fragment = new FragmentNews();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
//                if (!(this instanceof MainActivity)) {
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.nav_schedule:
                fragment = new FragmentSchedule();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
//                if (!(this instanceof  ScheduleActivity)) {
//                    Intent intent = new Intent(this, ScheduleActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.nav_session:
                fragment = new FragmentSession();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;
            case R.id.nav_progress:
                fragment = new FragmentProgressLesson();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;
            case R.id.nav_publication:
                fragment = new FragmentPublication();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit();
                break;
            case R.id.nav_campaign:
                break;
            case R.id.nav_logout:
                logOut();
                ((NavigationView) findViewById(R.id.n_view))
                        .getMenu().findItem(R.id.nav_news).setChecked(true);;
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();
        }
        else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            mDrawerToggle.syncState();
        }
    }

    protected abstract int getResource();
}
