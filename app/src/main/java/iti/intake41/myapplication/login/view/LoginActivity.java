package iti.intake41.myapplication.login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.lang.Class;

import iti.intake41.myapplication.createtrip.view.CreateTrip;
import iti.intake41.myapplication.helper.Navigator;
import iti.intake41.myapplication.helper.NetworkClass;
import iti.intake41.myapplication.R;
import iti.intake41.myapplication.login.LoginModelInterface;
import iti.intake41.myapplication.login.viewmodel.LoginViewModel;
import iti.intake41.myapplication.modules.main.MainActivity;
import iti.intake41.myapplication.signup.view.SignUpActivity;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent;
import static com.google.android.gms.auth.api.signin.SignInAccount.*;

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
    DatabaseReference dbRef;

    String userEmail;
    String userName;

    Button googleSigninBtn;
    private static final String TAG = "UserData";
    private static final int RC_SIGN_IN =123 ;
    private GoogleSignInClient mGoogleSignInClient;

    public LoginActivity() {
        dbRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        mAuth = FirebaseAuth.getInstance();
        createRequest();
        googleSigninBtn.setOnClickListener(v -> {
            signIn();
        });

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
        googleSigninBtn=findViewById(R.id.btnGoogleSignUp);

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
    void uploadUserData(String username, String email) {
        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference currentUserDB = dbRef.child(userID);
        currentUserDB.child("id").setValue(userID);
        currentUserDB.child("name").setValue(username);
        currentUserDB.child("email").setValue(email);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
                Log.i(TAG, "onActivityResult: "+e.getStatusCode() );
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent= new Intent(getApplicationContext(), CreateTrip.class);
                            GoogleSignInAccount SignInAccount=GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
                            if(SignInAccount != null){
                                userName =SignInAccount.getDisplayName();
                                 userEmail =SignInAccount.getEmail();
                            }

                            uploadUserData(userName, userEmail);
                            startActivity(intent);

                            Log.i(TAG, "userName ");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "onComplete: "+task.getException().getLocalizedMessage());
                            Toast.makeText(LoginActivity.this, "Sorry, Authentication Failed Try Again", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });

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