package iti.intake41.myapplication.viewmodel;

import android.content.Context;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.models.FirebaseRepoDelegate;
import iti.intake41.myapplication.models.user.User;
import iti.intake41.myapplication.models.user.UserRepo;

public class UserViewModel extends ViewModel{

    public MediatorLiveData<User> user = new MediatorLiveData();

    private UserRepo repo;
    private static UserViewModel instance;
    Context context;

    private UserViewModel(){
        this.repo = new UserRepo();
        getUser();
    }

    public static UserViewModel getInstance(Context context){
        if(instance == null){
            synchronized (UserViewModel.class){
                if(instance == null){
                    instance = new UserViewModel();
                }
            }
        }

        instance.context = context;
        return instance;
    }

    public void getUser(){
        repo.getUser(new FirebaseRepoDelegate() {
            @Override
            public <T> void getObjSuccess(T obj) {
                user.setValue((User) obj);
            }
        });
    }
}