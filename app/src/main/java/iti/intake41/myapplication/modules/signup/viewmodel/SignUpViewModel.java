package iti.intake41.myapplication.modules.signup.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.modules.signup.model.SignUpModelInterface;
import iti.intake41.myapplication.modules.signup.model.SignUpRepository;
import iti.intake41.myapplication.modules.signup.view.SignUpActivity;

public class SignUpViewModel extends ViewModel {
    SignUpActivity viewRef;
    SignUpModelInterface repoRef = null;

    MutableLiveData<String> myrespnse;

    public SignUpViewModel( ) {
        repoRef =new SignUpRepository();

    }


    public MutableLiveData<String> signUp(String userName,String email, String password) {
        return repoRef.signUp(userName, email, password);
    }

   /* public MutableLiveData<String> getResponse() {
        if (myrespnse == null) {
            myrespnse = new MutableLiveData<String>();
        }
        return myrespnse;
    }*/

}
