package fr.android.project_vyas_manaranche.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Fight {

    private int fightId;
    private Date date;
    private int duration;
    private String streetName;
    private String city;

    private List<Fighter> listFighter;
    private List<Round> listRounds;

    public Fight(int fightId,
                 Date date,
                 int duration,
                 String streetName,
                 String city,
                 List<Fighter> listFighters,
                 List<Round> listRounds) {
        this.fightId = fightId;
        this.date = date;
        this.duration = duration;
        this.streetName = streetName;
        this.city = city;
        this.listFighter = listFighters;
        this.listRounds = listRounds;
    }
    public Fight(Date date,
                 String streetName,
                 String city,
                 List<Fighter> listFighters) {
        this.date = date;
        this.streetName = streetName;
        this.city = city;
        this.listFighter = listFighters;
    }

    public List<Fighter> getListFighter() {
        return listFighter;
    }

    public void setListFighter(List<Fighter> listFighter) {
        this.listFighter = listFighter;
    }

    public List<Round> getListRounds() {
        return listRounds;
    }

    public void setListRounds(List<Round> listRounds) {
        this.listRounds = listRounds;
    }

    public int getFightId() {
        return fightId;
    }

    public void setFightId(int fightId) {
        this.fightId = fightId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



}
