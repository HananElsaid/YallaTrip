package iti.intake41.myapplication.modules.user.signup.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.R;
import iti.intake41.myapplication.modules.user.login.view.LoginActivity;
import iti.intake41.myapplication.modules.user.signup.viewmodel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {
    String email, password, userName;
    //views refrences
    TextInputEditText etEmail, etPassword,etUserName;
    Button btnSignUp;
    TextView tvlogin;

    //firebase
    FirebaseAuth auth;
    //view model
    SignUpViewModel viewModelRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                userName=etUserName.getText().toString().trim();
                checkInputs();
                if (NetworkClass.isNetworkConnected(SignUpActivity.this)) {
                    signUp();

                } else display("Pleas check your internet Connection ");
            }
        });

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openLoginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(openLoginIntent);
                SignUpActivity.this.finish();
            }
        });

    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.etemail);
        etPassword = findViewById(R.id.etpassword);
        etUserName=findViewById(R.id.etUserName);
        tvlogin = findViewById(R.id.tvopenLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        //viewModel
        viewModelRef = new ViewModelProvider(this).get(SignUpViewModel.class);


    }

    private void signUp() {
        if (!email.isEmpty() && !password.isEmpty() &&!userName.isEmpty()&&
                Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            //viewModelRef.signUp(userName,email, password);
            viewModelRef.signUp(userName,email, password).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if (s.equals("successful"))
                        Navigator.gotoScreen(SignUpActivity.this, LoginActivity.class);
                    //openLoginActivity();
                    display(s);
                }
            });
        }
    }

    public void display(String message) {

        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }

    public void openLoginActivity() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        this.finish();
    }


    private void checkInputs() {


        if (email.isEmpty()) {
            etEmail.setError("Please enter the email");
            etEmail.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            etEmail.setError("Please enter valid email");
            etEmail.requestFocus();
        }
        if (password.isEmpty()) {
            etPassword.setError("Please enter the password");
            etPassword.requestFocus();
        }

        if (userName.isEmpty()) {
            etUserName.setError("Please enter your Name");
            etUserName.requestFocus();
        }

    }


}