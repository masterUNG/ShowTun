package phansa.phaiboon.showtun.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import phansa.phaiboon.showtun.MainActivity;
import phansa.phaiboon.showtun.R;
import phansa.phaiboon.showtun.manager.MyAlert;
import phansa.phaiboon.showtun.manager.PostNewUser;

/**
 * Created by masterung on 8/23/2017 AD.
 */

public class SignUpFragment extends Fragment{

    //Explicit
    private String nameString, userString, passwordString, rePasswordString, genderString;
    private boolean genderABoolean = true;  // true ==> Not Choose Gender



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

        //Gender Controller
        genderController();

    }   // onActivityCreate

    private void genderController() {
        RadioGroup radioGroup = getView().findViewById(R.id.ragGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                genderABoolean = false;
                switch (i) {
                    case R.id.radMale:
                        genderString = "Male";
                        break;
                    case R.id.radFemale:
                        genderString = "Female";
                        break;
                    default:
                        genderString = "n/a";
                        break;
                }   // switch

            }   // onChecked
        });
    }

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
                } else if (!passwordString.equals(rePasswordString)) {
                    //Password Not Math
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Password Not Math", "Please Fill Same Password");
                } else if (genderABoolean) {
                    //Not Choose Gender
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Not Choose Gender", "Please Choose Gender");

                } else {
                    uploadValueToServer();
                }



            }   // onClick
        });
    }

    private void uploadValueToServer() {

        String tag = "24AugV1";
        Log.d(tag, "Name ==> " + nameString);
        Log.d(tag, "User ==> " + userString);
        Log.d(tag, "Pass ==> " + passwordString);
        Log.d(tag, "Gender ==> " + genderString);

        try {

            PostNewUser postNewUser = new PostNewUser(getActivity());
            postNewUser.execute(nameString, userString, passwordString, genderString);
            String result = postNewUser.get();
            Log.d(tag, "result ==> " + result);

            //Check Result
            if (Boolean.parseBoolean(result)) {
                //Success Upload
                welcome();
            } else {
                //UnSuccess
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("Cannot Upload New User To Server",
                        "Please Try Again");
            }

        } catch (Exception e) {
            Log.d(tag, "e upload ==> " + e.toString());
        }


    }   // upload

    private void welcome() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.my_alert);
        builder.setTitle("Success Upload");
        builder.setMessage("Please OK to Login");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().getSupportFragmentManager().popBackStack();
                dialogInterface.dismiss();
            }
        });
        builder.show();

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
