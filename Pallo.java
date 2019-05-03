
package laivanupotus;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pallo {
    private Circle pallo;
    
    public Pallo (double x, double y) {
        this.pallo = new Circle(x, y, 10);
        this.pallo.setFill(Color.BLUE);
    }

    public Circle getPallo() {
        return pallo;
    }
}
