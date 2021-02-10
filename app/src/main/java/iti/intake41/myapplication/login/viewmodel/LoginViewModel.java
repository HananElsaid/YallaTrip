package iti.intake41.myapplication.login.viewmodel;

import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.login.DelegetInterface;
import iti.intake41.myapplication.login.model.LoginRepository;
import iti.intake41.myapplication.login.view.LoginActivity;

public class LoginViewModel extends ViewModel {
    DelegetInterface myAnInterface;
    LoginRepository repository;

    public LoginViewModel(DelegetInterface myAnInterface) {
        this.myAnInterface = myAnInterface;
        repository = new LoginRepository(this);

    }

    public void login(String email, String password) {
        repository.login(email, password);
    }

    public void display(String message) {
        myAnInterface.display(message);
    }

   /* public void loginSuccessfully() {
        loginActivity.openPostsActivity();
    }*/
}