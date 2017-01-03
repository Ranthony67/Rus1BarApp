package dk.rus_1_katrinebjerg.barapp.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Trip extends RealmObject {
    @PrimaryKey
    public int id;
    public String name;

    public RealmList<Tutor> Tutors;
    public RealmList<BarItem> BarItems;
}
