package com.anushka.androidtutz.contactmanager.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Contact.class, version = 1)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDao();

}
