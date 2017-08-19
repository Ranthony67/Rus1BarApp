package dk.rus_1_katrinebjerg.barapp.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Trip extends RealmObject {
    @PrimaryKey
    public int id;
    public String name;
    public boolean isActive;

    public RealmList<Tutor> tutors;
    public RealmList<BarItem> barItems;
}
