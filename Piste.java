
package laivanupotus;

public class Piste {
    private int x;
    private int y;
    private boolean ammuttu;
    private boolean osuttu;
    private Laiva laiva;
    private int laivanPiste;
    
    public Piste(int x, int y) {
        this.x = x;
        this.y = y;
        this.ammuttu = false;
        this.osuttu = false;
        this.laiva = null;
        this.laivanPiste = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getAmmuttu() {
        return ammuttu;
    }

    public void setAmmuttu(boolean ammuttu) {
        this.ammuttu = ammuttu;
    }

    public boolean isOsuttu() {
        return osuttu;
    }

    public void setOsuttu(boolean osuttu) {
        this.osuttu = osuttu;
    }

    public Laiva getLaiva() {
        return laiva;
    }

    public void setLaiva(Laiva laiva) {
        this.laiva = laiva;
    }

    public int getLaivanPiste() {
        return laivanPiste;
    }

    public void setLaivanPiste(int laivanPiste) {
        this.laivanPiste = laivanPiste;
    }
    
    
}
