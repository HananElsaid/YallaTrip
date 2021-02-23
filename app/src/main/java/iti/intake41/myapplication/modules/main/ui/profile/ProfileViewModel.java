package iti.intake41.myapplication.modules.main.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import iti.intake41.myapplication.models.user.User;

public class ProfileViewModel extends ViewModel {
User user=new User();
    private MutableLiveData<String> nameText;
    private MutableLiveData<String> mailText;

    public ProfileViewModel() {
        nameText = new MutableLiveData<>();
        nameText.setValue(user.getName());

        mailText = new MutableLiveData<>();
        mailText.setValue(user.getEmail());
    }

    public LiveData<String> getNameText() {
        return nameText;
    }
    public LiveData<String> getEmailText() {
        return mailText;
    }
}