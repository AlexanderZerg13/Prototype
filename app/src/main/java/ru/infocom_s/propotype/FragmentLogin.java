package ru.infocom_s.propotype;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentLogin extends Fragment {

    EditText editTextLogin, editTextPassword;
    Button buttonEnter;

    private DoLogin doLoginContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity) {
            a = (Activity) context;
            a.setTitle(R.string.title_login);
        }

        doLoginContext = (DoLogin) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        editTextLogin = (EditText) v.findViewById(R.id.login_fragment_ETLogin);
        editTextPassword = (EditText) v.findViewById(R.id.login_fragment_ETPassword);
        buttonEnter = (Button) v.findViewById(R.id.login_fragment_BEnter);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login, password;
                login = editTextLogin.getText().toString();
                password = editTextPassword.getText().toString();
                editTextLogin.clearFocus();
                editTextPassword.clearFocus();

                doLoginContext.login(login, password);
            }
        });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        doLoginContext = null;
    }

    interface DoLogin {
        void login(String login, String password);
    }
}
