package fr.android.project_vyas_manaranche.models;

public class PlayerRoundStatistics {

    private int nbHook;
    private int nbOvercute;
    private int nbDirect;
    private int nbKick;
    private int round;

    public PlayerRoundStatistics(int nbHook, int nbOvercute, int nbDirect, int nbKick) {
        this.nbHook = nbHook;
        this.nbOvercute = nbOvercute;
        this.nbDirect = nbDirect;
        this.nbKick = nbKick;
    }
    public PlayerRoundStatistics(int nbHook, int nbOvercute, int nbDirect, int nbKick, int round) {
        this.nbHook = nbHook;
        this.nbOvercute = nbOvercute;
        this.nbDirect = nbDirect;
        this.nbKick = nbKick;
        this.round = round;
    }

    public int getNbHook() {
        return nbHook;
    }

    public void setNbHook(int nbHook) {
        this.nbHook = nbHook;
    }

    public int getNbOvercute() {
        return nbOvercute;
    }

    public void setNbOvercute(int nbOvercute) {
        this.nbOvercute = nbOvercute;
    }

    public int getNbDirect() {
        return nbDirect;
    }

    public void setNbDirect(int nbDirect) {
        this.nbDirect = nbDirect;
    }

    public int getNbKick() {
        return nbKick;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setNbKick(int nbKick) {
        this.nbKick = nbKick;
    }
}
