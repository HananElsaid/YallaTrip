package iti.intake41.myapplication.modules.main.home;

import iti.intake41.myapplication.models.Trip;

public interface HomeAdapterDelegate {
    void itemClicked(Trip trip);
    void startClicked(Trip trip);
}
