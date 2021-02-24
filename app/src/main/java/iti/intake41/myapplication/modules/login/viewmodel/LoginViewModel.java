package iti.intake41.myapplication.modules.login.viewmodel;

import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.modules.login.model.LoginModelInterface;
import iti.intake41.myapplication.modules.login.model.LoginRepository;

public class LoginViewModel extends ViewModel {
    LoginModelInterface myAnInterface;
    LoginRepository repository;

    public LoginViewModel(LoginModelInterface myAnInterface) {
        this.myAnInterface = myAnInterface;
        repository = new LoginRepository(this);

    }

    public void login(String email, String password) {
        repository.login(email, password);
    }

    public void display(String message) {
        myAnInterface.display(message);
        if (message.equals("login sucessfully"))
            myAnInterface.openHome();
    }


}