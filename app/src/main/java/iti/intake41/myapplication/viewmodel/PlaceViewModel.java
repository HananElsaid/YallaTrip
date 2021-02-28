package iti.intake41.myapplication.viewmodel;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import iti.intake41.myapplication.models.Place.model.Place;
import iti.intake41.myapplication.models.Place.model.PlaceRepo;
import iti.intake41.myapplication.models.Place.model.PlaceRepoInterface;

public class PlaceViewModel extends ViewModel {

    public MediatorLiveData<List<Place>> itemsList = new MediatorLiveData();
    public MediatorLiveData<Place> selectedItem = new MediatorLiveData();
    private PlaceRepoInterface repo;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    final PublishSubject<String> subject = PublishSubject.create();

    public PlaceViewModel(){
        this.repo = new PlaceRepo();
        addSubject();
    }

//    public void getItems(String text){
//        if(itemsList.getValue() == null){
//            repo.getItemsList(text)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<List<Place>>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//                            compositeDisposable.add(d);
//                        }
//
//                        @Override
//                        public void onNext(@NonNull List<Place> places) {
//                            itemsList.setValue(places);
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//                            System.out.println("onError");
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            System.out.println("onComplete");
//                        }
//                    });
//        }
//    }

    public void onQueryTextSubmit(String s) {
        subject.onComplete();
    }

    public void onQueryTextChange(String text) {
        subject.onNext(text);
    }

    public void addSubject(){
        subject.debounce(100, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, Observable<List<Place>>>() {
                    @Override
                    public Observable<List<Place>> apply(String query){
                        return  repo.getItemsList(query)
                                .subscribeOn(Schedulers.io());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Place>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Place> places) {
                        itemsList.setValue(places);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void dispose() {
        compositeDisposable.dispose();
    }
}
