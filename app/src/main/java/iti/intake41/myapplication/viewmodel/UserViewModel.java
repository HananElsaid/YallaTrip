package iti.intake41.myapplication.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.user.User;
import iti.intake41.myapplication.models.user.UserRepo;

public class UserViewModel extends ViewModel{

    public MediatorLiveData<User> user = new MediatorLiveData();

    private UserRepo repo;

    public UserViewModel(){
        this.repo = new UserRepo();
    }

    public void getUser(){
        if(user.getValue() == null){
            repo.getUser(new FirebaseRepoDelegate() {
                @Override
                public <T> void getObjSuccess(T obj) {
                    user.setValue((User) obj);
                }
            });
        }
    }
}