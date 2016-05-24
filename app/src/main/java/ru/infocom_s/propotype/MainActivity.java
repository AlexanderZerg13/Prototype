package ru.infocom_s.propotype;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends DrawerActivity implements FragmentLogin.DoLogin, FragmentNews.DescriberNews {


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
                fragment = new FragmentCardNews();

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
        //tv.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(DrawerActivity.KEY_LOGIN, ""));
        tv.setText("Петров Я.Д.");
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

            //((TextView) findViewById(R.id.textViewName)).setText(login);

            Fragment fragment = new FragmentCardNews();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
        } else {
            Toast.makeText(this, R.string.error_wrong_login_or_password, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showDescribeNews(UUID id) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = FragmentNewsDescribe.newInstance(id);
        fm.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack("")
                .commit();
    }
}
