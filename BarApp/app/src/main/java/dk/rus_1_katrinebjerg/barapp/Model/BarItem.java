package dk.rus_1_katrinebjerg.barapp.Model;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class BarItem extends RealmObject{
    @PrimaryKey
    public int id;
    public String name;
    public double price;

    @LinkingObjects("barItems")
    private final RealmResults<Trip> trips = null;

    @LinkingObjects("barItemsBought")
    private final RealmResults<Tutor> tutors = null;
}
