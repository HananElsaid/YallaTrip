package iti.intake41.myapplication.models;

import java.util.List;

public interface FirebaseRepoDelegate {
    default <T>void getObjSuccess(T obj){}
    default <T> void getListSuccess(List<T> list){}
    default void success(String message){}
    default void failed(String message){}
}
