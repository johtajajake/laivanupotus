package laivanupotus;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import static laivanupotus.Kayttoliittyma.LEVEYS;

public class Laiva {

    private Polygon laiva;
    private int elossa;
    private long aika;
    private int x;
    private int y;
    private int suunta;
    private int pituus;

    public Laiva(int x, int y, double suunta, int pituus, double opacity) {
        int p = pituus - 1;
        this.laiva = new Polygon(-10, -10, 0, -10, (p * 40), -10, (p * 40 + 10), 0, (p * 40), 10, -10, 10);
        this.laiva.setFill(Color.rgb(0, 0, 255, opacity));
        this.laiva.setTranslateX(x);
        this.laiva.setTranslateY(y);
        this.laiva.setRotate(suunta);
        this.elossa = pituus;
        this.aika = 0;
        this.x = x;
        this.y = y;
        this.suunta = (int) suunta;
        this.pituus = pituus;
    }

    public void setFill(int r, int g, int b, double o) {
        laiva.setFill(Color.rgb(r, g, b, o));
    }
    
    public Polygon getLaiva() {
        return this.laiva;
    }

    public void setTranslateX(double x) {
        this.laiva.setTranslateX(x);
    }

    public void setTranslateY(double y) {
        this.laiva.setTranslateY(y);
    }

    public double getTranslateX() {
        return this.laiva.getTranslateX();
    }

    public double getTranslateY() {
        return this.laiva.getTranslateY();
    }

    public double getRotate() {
        return this.laiva.getRotate();
    }

    public void setRotate(int suunta) {
        this.laiva.setRotate(suunta * 90);
    }
    
    

//    public void moveLaivaOnMouseClicked(Scene scene) {
//        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                long t = System.currentTimeMillis();
//                if (t - aika > 10) { // jos dragatty, niin älä käännä
//                    setRotate();
//                }
//            }
//        });
//    }
//
//    public void moveLaivaOnMouseDragged(Scene scene) {
//        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                double x = laiva.getTranslateX();
//                double y = laiva.getTranslateY();
//                double r = laiva.getRotate();
//                int dx = 0;
//                int dy = 0;
//                if (r == 0 || r == 180) {
//                    dx = 20;
//                    dy = 20;
//                } else {
//                    dx = -2;
//                    dy = 0;
//                }
//                x = event.getSceneX() - LEVEYS * 1.3 - 5;
//                y = event.getSceneY() - 40;
//                x = dx + Math.round(x / 40) * 40;
//                y = dy + Math.round(y / 40) * 40;
////                laiva.setTranslateX(event.getSceneX() - LEVEYS * 1.3 - 5);
////                laiva.setTranslateY(event.getSceneY() - 40);
//                laiva.setTranslateX(x);
//                laiva.setTranslateY(y);
//                aika = System.currentTimeMillis();
//            }
//        });
//    }

    public int getElossa() {
        return elossa;
    }

    public void reduceElossa() {
        this.elossa = getElossa() - 1;
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

    public int getSuunta() {
        return suunta;
    }

    public void setSuunta(int suunta) {
        this.suunta = suunta;
        this.setRotate(suunta);
    }

    public int getPituus() {
        return pituus;
    }

    public void setPituus(int pituus) {
        this.pituus = pituus;
    }
}
