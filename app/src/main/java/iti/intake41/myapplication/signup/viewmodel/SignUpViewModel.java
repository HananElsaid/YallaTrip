package iti.intake41.myapplication.signup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.signup.model.SignUpRepository;
import iti.intake41.myapplication.signup.view.SignUpActivity;

public class SignUpViewModel extends ViewModel {
    SignUpActivity viewRef;
    SignUpRepository repoRef = null;

    MutableLiveData<String> myrespnse;

    public SignUpViewModel( ) {
        repoRef =new SignUpRepository(this);

    }


    public void signUp(String email, String password) {
        repoRef.signUp(email, password);
    }

    public MutableLiveData<String> getResponse() {
        if (myrespnse == null) {
            myrespnse = new MutableLiveData<String>();
        }
        return myrespnse;
    }

}
