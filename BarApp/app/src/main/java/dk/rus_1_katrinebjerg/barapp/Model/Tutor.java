package dk.rus_1_katrinebjerg.barapp.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tutor extends RealmObject{
    @PrimaryKey
    public int id;
    public String name;
    public String streetName;
    public String imagePath;

    public int tripId;
    public Trip trip;
    public RealmList<BarItem> BarItemsBought;
}
