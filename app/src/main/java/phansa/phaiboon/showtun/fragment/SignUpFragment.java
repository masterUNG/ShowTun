package phansa.phaiboon.showtun.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import phansa.phaiboon.showtun.MainActivity;
import phansa.phaiboon.showtun.R;
import phansa.phaiboon.showtun.manager.MyAlert;

/**
 * Created by masterung on 8/23/2017 AD.
 */

public class SignUpFragment extends Fragment{

    //Explicit
    private String nameString, userString, passwordString, rePasswordString;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        return view;
    }   // onCreateView

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Create ToolBar
        createToolBar();

        //SignUp Controller
        signUpController();

    }   // onActivityCreate

    private void signUpController() {
        Button button = getView().findViewById(R.id.btnSignUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Initial EditText view
                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);
                EditText rePasswordEditText = getView().findViewById(R.id.edtRePassword);

                //Get Value From EditText
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                rePasswordString = rePasswordEditText.getText().toString().trim();

                //Check Space
                if (nameString.equals("") ||
                        userString.equals("") ||
                        passwordString.equals("") || rePasswordString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog(getString(R.string.title_have_space),
                            getString(R.string.message_have_space));
                }



            }   // onClick
        });
    }

    private void createToolBar() {
        //SetUp Toolbar
        Toolbar toolbar = getView().findViewById(R.id.toolBarSignUp);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.register);
        ((MainActivity)getActivity()).getSupportActionBar().setSubtitle(getString(R.string.sub_register));


        //SetUp Navagation Icon
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager()
                        .popBackStack();

            }   // onClick
        });

    }

}   // Main Class
