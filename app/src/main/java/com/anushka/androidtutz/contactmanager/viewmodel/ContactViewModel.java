package com.anushka.androidtutz.contactmanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.anushka.androidtutz.contactmanager.model.ContactRepository;
import com.anushka.androidtutz.contactmanager.model.db.Contact;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository contactRepository;
    private LiveData<List<Contact>> mLiveData;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        contactRepository = new ContactRepository(application);
    }

    public void createContactVM(String name, String email){
        contactRepository.createContact(name, email);
    }

    public void deleteContactVM(Contact contact){
        contactRepository.deleteContact(contact);
    }

    public void updateContactVM(Contact contact){
        contactRepository.updateContact(contact);
    }

    public void clearCompositeDisposableVM(){
        contactRepository.clearCompositeDisposable();
    }

    /*new comments for me*/

    /*my changes again*/

    public LiveData<List<Contact>> getLiveDataVM(){
        mLiveData = contactRepository.getLiveData();
        return mLiveData;
    }

}
