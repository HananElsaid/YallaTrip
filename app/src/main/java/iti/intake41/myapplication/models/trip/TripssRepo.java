package iti.intake41.myapplication.models.trip;

import iti.intake41.myapplication.models.FirebaseRepo;

public class TripssRepo extends FirebaseRepo{
//    DatabaseReference tripRef;
//
//    public TripRepo() {
//        tripRef = mDatabase.child("trips").child(getUid());
//    }
//
//    public String getTripID() {
//        return tripRef.push().getKey();
//    }
//
//    @Override
//    public void getTrips(FirebaseRepoDelegate delegate) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                tripRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                            List<Trip> trips = new ArrayList();
//                            task.getResult().getChildren().forEach(i -> {
//                                trips.add(i.getValue(Trip.class));
//                            });
//
//                            System.out.println("trips: " + task.getResult().getChildren());
//                            delegate.getListSuccess(trips);
//                        } else {
//                            Log.e("firebase", "Error getting data", task.getException());
//                            //delegate.failed(task.getException().getLocalizedMessage());
//                        }
//                    }
//                });
//                //
//            }
//        }).start();
//    }
//
//
//    @Override //#done
//    public void addTrip(Trip trip, FirebaseRepoDelegate delegate) {
//        String tripId;
//        if (trip.getId() == null) { //Create
//            tripId = getTripID();
//            trip.setId(tripId);
//        } else { //Update
//            tripId = trip.getId();
//        }
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/trips/" + getUid() + "/" + tripId, trip.toMap());
//
//        mDatabase.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    delegate.success("Trip added Successfully");
//                } else {
//                    Log.e("firebase", "Error getting data", task.getException());
//                    delegate.failed(task.getException().getLocalizedMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void updateTrip(Trip trip, FirebaseRepoDelegate delegate) {
//        String tripId = trip.getId();
//        String path = "/trips/" + getUid() + "/" + tripId;
//        mDatabase.child(path).setValue(trip.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    delegate.success("Trip updated Successfully");
//                } else {
//                    Log.e("firebase", "Error getting data", task.getException());
//                    delegate.failed(task.getException().getLocalizedMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void deleteTrip(String id, FirebaseRepoDelegate delegate) {
//        String path = "/trips/" + getUid() + "/" + id;
//
//        mDatabase.child(path).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    delegate.success("Trip updated Successfully");
//                } else {
//                    Log.e("firebase", "Error getting data", task.getException());
//                    delegate.failed(task.getException().getLocalizedMessage());
//                }
//            }
//        });
//    }
//
//    @Override
//    public void getTripDetails(String id, FirebaseRepoDelegate delegate) {
//        tripRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    delegate.getObjSuccess(task.getResult().getValue(Trip.class));
//                } else {
//                    Log.e("firebase", "Error getting data", task.getException());
//                    delegate.failed(task.getException().getLocalizedMessage());
//                }
//            }
//        });
//    }

}