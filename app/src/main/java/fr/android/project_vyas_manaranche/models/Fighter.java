package fr.android.project_vyas_manaranche.models;

import java.util.List;

public class Fighter {

    private int fighterId;
    private String fullname;
    private List<PlayerRoundStatistics> statistics;
    private int fightId;

    public Fighter(int fighterId, String fullname, List<PlayerRoundStatistics> listStats) {
        this.fighterId = fighterId;
        this.fullname = fullname;
        this.statistics = listStats;
    }
    public Fighter(String fullname) {
        this.fullname = fullname;
    }
    public Fighter(String fullname, int fightId) {
        this.fullname = fullname;
        this.fightId = fightId;
    }

    public List<PlayerRoundStatistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<PlayerRoundStatistics> statistics) {
        this.statistics = statistics;
    }

    public long getFighterId() {
        return fighterId;
    }

    public void setFighterId(int fighterId) {
        this.fighterId = fighterId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getFightId() {
        return fightId;
    }

    public void setFightId(int fightId) {
        this.fightId = fightId;
    }
}
