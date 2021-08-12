package com.anushka.androidtutz.contactmanager.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.anushka.androidtutz.contactmanager.model.db.Contact;
import com.anushka.androidtutz.contactmanager.model.db.MyRoomDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactRepository {

    private Application mApplication;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MyRoomDatabase myRoomDatabase;
    private MutableLiveData<List<Contact>> mLiveData = new MutableLiveData<>();

    private long createId;

    public ContactRepository(Application application){

        this.mApplication = application;
        myRoomDatabase = Room.databaseBuilder(mApplication.getApplicationContext(),MyRoomDatabase.class, "CONTACT_DB").build();

        getLiveData();


    }

    public LiveData<List<Contact>> getLiveData() {

        compositeDisposable.add(myRoomDatabase.getContactDao().getAllContact().subscribeOn(Schedulers.computation()) // We are waiting for sth is changed in our contacts
                // and then, contactArrayList wil be changed automatically
                // We are using "Consumer" because we need a callback from DB
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Contact>>() {
                    @Override
                    public void accept(List<Contact> contacts) throws Throwable {

                        mLiveData.postValue(contacts);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                }));

        return mLiveData;

    }


    public void createContact(String name, String email) {


        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                createId = myRoomDatabase.getContactDao().insertContact(new Contact(0, name, email));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("MainActivity", "Successful add " + String.valueOf(createId));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                }));


    }

    public void deleteContact(Contact contact) {

        compositeDisposable.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                myRoomDatabase.getContactDao().deleteContact(contact);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("MainActivity", "Successful delete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                }));



    }

    public void updateContact(Contact contact) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Throwable {
                myRoomDatabase.getContactDao().updateContact(contact);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d("MainActivity", "Successful update");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void clearCompositeDisposable(){

        compositeDisposable.clear();

    }




}
