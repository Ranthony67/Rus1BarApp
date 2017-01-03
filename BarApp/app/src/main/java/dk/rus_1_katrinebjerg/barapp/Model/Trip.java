package dk.rus_1_katrinebjerg.barapp.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Trip extends RealmObject {
    public String name;

    public RealmList<Tutor> Tutors;
    public RealmList<BarItem> BarItems;
}
