package com.anushka.androidtutz.contactmanager.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ContactDAO {

    // Insert method return the id that is a long var
    @Insert
    long insertContact (Contact contact);

    @Delete
    void deleteContact (Contact contact);

    @Update
    void updateContact (Contact contact);

    @Query("select * from contacts where contact_id == :contactId")
    Contact getContact (long contactId);

    @Query("select * from contacts")
    Flowable<List<Contact>> getAllContact ();


}
