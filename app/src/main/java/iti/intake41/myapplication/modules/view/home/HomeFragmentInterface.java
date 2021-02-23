package iti.intake41.myapplication.modules.view.home;

import java.util.List;

import iti.intake41.myapplication.models.Trip;

public interface HomeFragmentInterface {
    void updateItems(List<Trip> items);
    void showToast(String message);
}
