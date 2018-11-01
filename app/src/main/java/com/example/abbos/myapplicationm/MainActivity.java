package com.example.abbos.myapplicationm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
       int s=Show();
        if(s==0){
            Kirit();
             Intent intent = new Intent(this, Welcome.class);
             startActivity(intent);
             finish();
        }else {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
            finish();
        }


    }

    //Bazade
    private  Realm realm;
    public void  Kirit(){

        //   editText =findViewById(R.id.editText);
        realm = Realm.getDefaultInstance();

        // All writes are wrapped in a transaction
        // to facilitate safe multi threading
        realm.beginTransaction();

        // Add a person
        Person person = realm.createObject(Person.class);

        person.setName("87");
        person.setAge(14);

        realm.commitTransaction();
    }
    public int Show(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();


        RealmResults<Person> realmCities= realm.where(Person.class).findAllAsync();
        //fetching the data
        realmCities.load();


        String string= String.valueOf(realmCities.size());
        for(Person person:realmCities){
            string=string + person.getName();
        }




        realm.commitTransaction();
        return Integer.parseInt(String.valueOf(realmCities.size()));

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void DeleteAll(){
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Person.class)
                .findAll()
                .deleteAllFromRealm();
        realm.commitTransaction();
    }


}
