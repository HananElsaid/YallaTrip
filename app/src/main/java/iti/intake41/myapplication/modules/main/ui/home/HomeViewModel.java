//package iti.intake41.myapplication.modules.main.ui.home;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import java.util.List;
//
//import iti.intake41.myapplication.models.FirebaseRepo;
//import iti.intake41.myapplication.models.FirebaseRepoDelegate;
//import iti.intake41.myapplication.models.Trip;
//import iti.intake41.myapplication.signup.model.UserData;
//
//public class HomeViewModel extends ViewModel {
//
//    private FirebaseRepo repo;
//    private MutableLiveData<UserData> user;
//    private MutableLiveData<List<Trip>> tripList;
//
//    public HomeViewModel() {
//        user = new MutableLiveData<>();
//        tripList = new MutableLiveData<>();
//        repo = FirebaseRepo.getInstance();
//    }
//
//    public void getUser() {
//        if(user.getValue() == null){
//            repo.getUser(new FirebaseRepoDelegate() {
//                @Override
//                public void failed(String message) {
//                    showError();
//                }
//
//                @Override
//                public void getUserSuccess(UserData user) {
//
//                }
//            });
//        }
//        return userName;
//    }
//}