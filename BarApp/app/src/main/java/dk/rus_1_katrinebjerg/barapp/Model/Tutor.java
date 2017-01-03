package dk.rus_1_katrinebjerg.barapp.Model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Tutor extends RealmObject{
    public int id;
    public String name;
    public String streetName;
    public RealmList<BarItem> BarItemsBought;
}
