package personnagesJeu;

public class Mur {
    private int x;
    private int y;

    public Mur(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean peutPasser() {
        return false;
    }

    public boolean peutPoserMine() {
        return false;
    }

    public boolean peutPoserBombe() {
        return false;
    }
}
