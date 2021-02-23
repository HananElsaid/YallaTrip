package iti.intake41.myapplication.modules.view.home;

import iti.intake41.myapplication.models.Trip;

public interface HomeAdapterDelegate {
    public void itemClicked(Trip trip);
    public void startClicked(Trip trip);
}
