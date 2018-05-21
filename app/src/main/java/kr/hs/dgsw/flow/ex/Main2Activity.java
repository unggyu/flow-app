package kr.hs.dgsw.flow.ex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import kr.hs.dgsw.flow.R;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.deleteRealm(configuration);
        Realm realm = Realm.getInstance(configuration);

        realm.beginTransaction();
        User user = realm.createObject(User.class, 1);
        user.setEmail("테스트");
        user.setEmail("test@gmail.com");
        realm.commitTransaction();

        RealmResults<User> result = realm.where(User.class).findAll();

        Log.e("result", result.toString());
    }
}
