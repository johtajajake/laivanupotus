package laivanupotus;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Grid {

    private Piste[][] grid;

    public Grid() {
        this.grid = new Piste[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Piste piste = new Piste(i, j);
                piste.setLaiva(new Laiva(i, j, 0, 0, 0));
                this.grid[i][j] = piste;
            }
        }
    }

    public Pane setShotToGrid(int x, int y, Pane vihu) {
        double dx = 60 + (x - 1) * 40;
        double dy = 60 + (y - 1) * 40;
        if (this.grid[x][y].getLaiva().getPituus() == 0) {
            Pallo pallo = new Pallo(dx, dy);
            vihu.getChildren().add(pallo.getPallo());
        } else {
            Ruksi ruksi = new Ruksi(dx, dy);
            vihu.getChildren().add(ruksi.getRuksi());
        }
        return vihu;
    }

    public void setLaivaToGrid(int x, int y, Laiva laiva, int suunta, int pituus) {
        double dx = x * 40.0 + 20;
        double dy = y * 40.0 + 20;
        if (suunta == 0) {
            dx = dx;
        } else if (suunta == 1) {
            dx = dx - (pituus - 1) * 20;
            dy = dy + (pituus - 1) * 20;
        } else if (suunta == 2) {
            dx = dx - (pituus - 1) * 20;
            dx = dx - (pituus - 1) * 20;
        } else if (suunta == 3) {
            dx = dx - (pituus - 1) * 20;
            dy = dy - (pituus - 1) * 20;
        }
        laiva.setTranslateX(dx);
        laiva.setTranslateY(dy);
        laiva.setSuunta(suunta);
        laiva.setX(x);
        laiva.setY(y);
        for (int i = 0; i < pituus; i++) {
            grid[x][y].setLaiva(laiva);
            grid[x][y].setLaivanPiste(i);
            if (suunta == 0) {
                x++;
            } else if (suunta == 1) {
                y++;
            } else if (suunta == 2) {
                x--;
            } else if (suunta == 3) {
                y--;
            }
        }
    }

    public Piste[][] getGrid() {
        return grid;
    }
}
