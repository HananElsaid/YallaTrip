package iti.intake41.myapplication.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
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
import iti.intake41.myapplication.login.LoginModelInterface;
import iti.intake41.myapplication.login.viewmodel.LoginViewModel;
import iti.intake41.myapplication.modules.main.MainActivity;
import iti.intake41.myapplication.signup.view.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements LoginModelInterface {
    //views refrences
    TextInputEditText edLoginEmail, edLoginPassword;
    Button btnLogin;
    TextView tvSignUp;

    //viewModel class refrence
    LoginViewModel viewModel;

    //
    String email, password;
    //firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = edLoginEmail.getText().toString().trim();
                password = edLoginPassword.getText().toString().trim();
                checkInputs();
                if (NetworkClass.isNetworkConnected(LoginActivity.this)) {
                    if (!email.isEmpty() && !password.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(edLoginEmail.getText().toString()).matches()) {
                        viewModel.login(email, password);
                    }
                } else display("Pleas check your internet Connection ");
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });



    }
    private void register () {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        this.finish();
    }

    private void init() {
        edLoginEmail = findViewById(R.id.etemail);
        edLoginPassword = findViewById(R.id.etpassword);

        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);
        //viewModel= new LoginViewModel(this);
        //viewModel = new  ViewModelProvider(this).get(LoginViewModel.class);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    void checkInputs() {
        if (email.isEmpty()) {
            edLoginEmail.setError("Please enter the email");
            edLoginEmail.requestFocus();
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edLoginEmail.getText().toString()).matches()) {
            edLoginEmail.setError("Please enter valid email");
            edLoginEmail.requestFocus();
        }
        if (password.isEmpty()) {
            edLoginPassword.setError("Please enter the password");
            edLoginPassword.requestFocus();
        }


    }
    @Override
    public void display(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void openHome() {
        Navigator.gotoScreen(LoginActivity.this, MainActivity.class);
        finish();
    }


    class LoginViewModelFactory implements ViewModelProvider.Factory {

        LoginModelInterface loginActivity;

        LoginViewModelFactory(LoginModelInterface loginActivity) {
            this.loginActivity = loginActivity;

        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

            return (T) new LoginViewModel(loginActivity);
        }
    }
}