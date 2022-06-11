package fr.android.project_vyas_manaranche.models;

public class PlayerRoundStatistics {

    private int nbHook;
    private int nbOvercute;
    private int nbDirect;
    private int nbKick;

    public PlayerRoundStatistics(int nbHook, int nbOvercute, int nbDirect, int nbKick) {
        this.nbHook = nbHook;
        this.nbOvercute = nbOvercute;
        this.nbDirect = nbDirect;
        this.nbKick = nbKick;
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

    public void setNbKick(int nbKick) {
        this.nbKick = nbKick;
    }
}
