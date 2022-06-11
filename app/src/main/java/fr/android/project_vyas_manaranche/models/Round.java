package fr.android.project_vyas_manaranche.models;

public class Round {

    private long roundId;
    private int duration;

    public Round(long roundId, int duration) {
        this.roundId = roundId;
        this.duration = duration;
    }

    public long getRoundId() {
        return roundId;
    }

    public void setRoundId(long roundId) {
        this.roundId = roundId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
