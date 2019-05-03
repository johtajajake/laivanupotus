
package laivanupotus;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ruksi {
    private Polygon ruksi;
    
    public Ruksi (double x, double y) {
        this.ruksi = new Polygon(-9,-10, 0,-1, 9,-10, 10,-9, 1,0, 10,9, 9,10, 0,1, -9,10, -10,9, -1,0, -10,-9);
        this.ruksi.setTranslateX(x);
        this.ruksi.setTranslateY(y);
        this.ruksi.setFill(Color.RED);
    }

    public Polygon getRuksi() {
        return ruksi;
    }
}
