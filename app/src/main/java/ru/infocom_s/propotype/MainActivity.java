package ru.infocom_s.propotype;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentLogin.DoLogin, NavigationView.OnNavigationItemSelectedListener {

    private static String KEY_LOGIN = "login";
    private static String KEY_PASSWORD = "password";

    private static String LOGIN = "user";
    private static String PASSWORD = "123";

    //NavDrawer
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    //-------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(myToolBar);

        //NavDrawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.open_menu, R.string.close_menu);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.n_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            if (checkLogin()) {
                fragment = new FragmentMain();
                TextView tv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textViewName);
                tv.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_LOGIN, ""));
            } else {
                setDrawerState(false);
                fragment = new FragmentLogin();
            }

            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void login(String login, String password) {

        if (login.equals(LOGIN) && password.equals(PASSWORD)) {

            setDrawerState(true);
            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putString(KEY_LOGIN, login)
                    .putString(KEY_PASSWORD, password)
                    .commit();

            ((TextView) findViewById(R.id.textViewName)).setText(login);

            Fragment fragment = new FragmentMain();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        } else {
            Toast.makeText(this, R.string.error_wrong_login_or_password, Toast.LENGTH_SHORT).show();
        }
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

    private boolean checkLogin() {
        SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);
        if (shp.getString(KEY_LOGIN, "").equals(LOGIN) && shp.getString(KEY_PASSWORD, "").equals(PASSWORD)) {
            return true;
        }
        return false;
    }

    public void setDrawerState(boolean isEnabled) {
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_news:
                break;
            case R.id.nav_schedule:
                break;
            case R.id.nav_session:
                break;
            case R.id.nav_progress:
                break;
            case R.id.nav_publication:
                break;
            case R.id.nav_campaign:
                break;
            case R.id.nav_logout:
                logOut();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
