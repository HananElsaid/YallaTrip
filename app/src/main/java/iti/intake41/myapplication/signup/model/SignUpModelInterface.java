package iti.intake41.myapplication.signup.model;

import androidx.lifecycle.MutableLiveData;

public interface SignUpModelInterface {
    public MutableLiveData<String> signUp(final String userName, final String email, final String password);
}
