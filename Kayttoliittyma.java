package laivanupotus;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Kayttoliittyma extends Application {

    public static int KORKEUS = 400;
    public static int LEVEYS = KORKEUS;
    public ArrayList<Laiva> omaLaivaLista = new ArrayList<>();
    public ArrayList<Laiva> vihuLaivaLista = new ArrayList<>();
    public ArrayList<Laiva> omatTuhotutLaivat = new ArrayList<>();
    public ArrayList<Laiva> vihunTuhotutLaivat = new ArrayList<>();
    public Grid omatLaivat = new Grid();
    public Grid vihuLaivat = new Grid();
    public boolean vihuOsunutLaivaan = false;
    public boolean suuntaSelvilla = false;
    public int suunta = 0;
    public int osumaX;
    public int osumaY;
    public int voitto;
    public int tappio;
    public int computerLevel;
    public boolean pelaajanVuoro;

    @Override
    public void start(Stage stage) {
        voitto = 4 * 1 + 3 * 2 + 2 * 3 + 1 * 4;
        tappio = 4 * 1 + 3 * 2 + 2 * 3 + 1 * 4;
        Label label = new Label("Pisteet: ");
        Text text = new Text();
        Button uusiPeliNappiBeginner = new Button("Beginner");
        Button uusiPeliNappiIntermediate = new Button("Intermediate");
        Button uusiPeliNappiExpert = new Button("Expert");
        BorderPane ylarivi = new BorderPane();
        int padding = 20;

        ylarivi.setPadding(new Insets(padding, padding, padding, padding));
        ylarivi.setLeft(label);
        ylarivi.setCenter(text);

        BorderPane nappulat = new BorderPane();
        nappulat.setLeft(uusiPeliNappiBeginner);
        nappulat.setCenter(uusiPeliNappiIntermediate);
        nappulat.setRight(uusiPeliNappiExpert);
        ylarivi.setRight(nappulat);

        Pane alaosa = new Pane();
        alaosa.setPrefSize(LEVEYS * 2, 100);
        alaosanLaivat(alaosa, omatTuhotutLaivat, 0, 0, 0, 255, 0.5);
        alaosanLaivat(alaosa, vihunTuhotutLaivat, 440, 255, 0, 0, 0.5);

        Pane oma = new Pane();
        Pane vihu = new Pane();
        Pane vali = new Pane();
        oma.setPrefSize(LEVEYS, KORKEUS);
        vihu.setPrefSize(LEVEYS, KORKEUS);
        vihu.setOnMouseClicked((event) -> {
            if (event.getSceneX() > 459 && event.getSceneY() > 89 && event.getSceneX() < 861 && event.getSceneY() < 491) {
                int x = (int) (event.getSceneX() - 460) / 40;
                int y = (int) (event.getSceneY() - 90) / 40;
                aShot(x, y, vihu, oma);
                if (voitto == 0) {
                    text.setText("VOITTO");
                }
                if (tappio == 0) {
                    text.setText("HÄVISIT");
                }
            }
        });
        vali.setPrefSize(LEVEYS / 10, KORKEUS);
        piirraGrid(oma);
        BorderPane layout = new BorderPane();
        BorderPane splitscreen = new BorderPane();
        splitscreen.setLeft(oma);
        splitscreen.setCenter(vali);
        splitscreen.setRight(vihu);
        splitscreen.setPadding(new Insets(padding, padding, padding, padding));

        layout.setTop(ylarivi);
        layout.setCenter(splitscreen);
        layout.setBottom(alaosa);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();

        uusiPeliNappiBeginner.setOnAction((event) -> {
            alkuRiviLaivat(oma, omaLaivaLista, 0.5);
            alkuRiviLaivat(vihu, vihuLaivaLista, 0);
            sekoitaLaivat(vihuLaivat, vihuLaivaLista);
            sekoitaLaivat(omatLaivat, omaLaivaLista);
            piirraGrid(vihu);
            computerLevel = 1;
        });
        uusiPeliNappiIntermediate.setOnAction((event) -> {
            alkuRiviLaivat(oma, omaLaivaLista, 0.5);
            alkuRiviLaivat(vihu, vihuLaivaLista, 0);
            sekoitaLaivat(vihuLaivat, vihuLaivaLista);
            sekoitaLaivat(omatLaivat, omaLaivaLista);
            piirraGrid(vihu);
            computerLevel = 2;
        });
        uusiPeliNappiExpert.setOnAction((event) -> {
            alkuRiviLaivat(oma, omaLaivaLista, 0.5);
            alkuRiviLaivat(vihu, vihuLaivaLista, 0);
            sekoitaLaivat(vihuLaivat, vihuLaivaLista);
            sekoitaLaivat(omatLaivat, omaLaivaLista);
            piirraGrid(vihu);
            computerLevel = 3;
        });
    }

    public void aShot(int x, int y, Pane vihu, Pane oma) {
        if (vihuLaivat.getGrid()[x][y].getAmmuttu() == false) {
            vihuLaivat.getGrid()[x][y].setAmmuttu(true);
            vihuLaivat.setShotToGrid(x, y, vihu);
            if (vihuLaivat.getGrid()[x][y].getLaiva().getPituus() != 0) {
                vihuLaivat.getGrid()[x][y].getLaiva().reduceElossa();
                voitto--;
                if (vihuLaivat.getGrid()[x][y].getLaiva().getElossa() == 0) {
                    pelaajanVuoro = true;
                    merkkaaTuhotunLaivanViereiset(vihuLaivat, vihuLaivat.getGrid()[x][y].getLaiva(), vihu);
                }
            } else { //jos elsen ottaa pois, niin ammutaan vuorotellen, nyt saa lisävuoron jos osuu
                pelaajanVuoro = false;
                if (computerLevel == 1) {
                    computerShootsLevelBeginner(oma);
                } else if (computerLevel == 2) {
                    computerShootsLevelIntermediate(oma);
                } else {
                    computerShootsLevelExpert(oma);
                }
            }
        }
    }

    public void computerShootsLevelBeginner(Pane pane) { //random ampuja
        while (true) {
            Random ran = new Random();
            int x = ran.nextInt(10);
            int y = ran.nextInt(10);
            if (omatLaivat.getGrid()[x][y].getAmmuttu() == false) {
                omatLaivat.getGrid()[x][y].setAmmuttu(true);
                omatLaivat.setShotToGrid(x, y, pane);
                if (omatLaivat.getGrid()[x][y].getLaiva().getPituus() != 0) {
                    omatLaivat.getGrid()[x][y].getLaiva().reduceElossa();
                    tappio--;
                    if (omatLaivat.getGrid()[x][y].getLaiva().getElossa() == 0) {
                        merkkaaTuhotunLaivanViereiset(omatLaivat, omatLaivat.getGrid()[x][y].getLaiva(), pane);
                    }
                } else { //jos elsen ottaa pois niin ammutaan vuorotellen, nyt saa lisävuoron jos osuu
                    break;
                }
            }
        }
    }

    public void computerShootsLevelIntermediate(Pane pane) { //tuhoaa osutun laivan kokonaan
        while (true) {
            Random ran = new Random();
            int x = ran.nextInt(10);
            int y = ran.nextInt(10);
            if (vihuOsunutLaivaan == true) {
                int kaikkiAmmuttu = 1;
                while (true) {
                    int r = ran.nextInt(4);
                    if (suuntaSelvilla == true) {
                        r = suunta;
                    }
                    if (r == 0) {
                        if (osumaX != 9) {
                            if (omatLaivat.getGrid()[osumaX + 1][osumaY].getAmmuttu() == false) {
                                x = osumaX + 1;
                                y = osumaY;
                                kaikkiAmmuttu = 0;
                                suunta = 0;
                                break;
                            }
                        }
                    }
                    if (r == 1) {
                        if (osumaX != 0) {
                            if (omatLaivat.getGrid()[osumaX - 1][osumaY].getAmmuttu() == false) {
                                x = osumaX - 1;
                                y = osumaY;
                                kaikkiAmmuttu = 0;
                                suunta = 1;
                                break;
                            }
                        }
                    }
                    if (r == 2) {
                        if (osumaY != 9) {
                            if (omatLaivat.getGrid()[osumaX][osumaY + 1].getAmmuttu() == false) {
                                x = osumaX;
                                y = osumaY + 1;
                                kaikkiAmmuttu = 0;
                                suunta = 2;
                                break;
                            }
                        }
                    }
                    if (r == 3) {
                        if (osumaY != 0) {
                            if (omatLaivat.getGrid()[osumaX][osumaY - 1].getAmmuttu() == false) {
                                x = osumaX;
                                y = osumaY - 1;
                                kaikkiAmmuttu = 0;
                                suunta = 3;
                                break;
                            }
                        }
                    }
                    kaikkiAmmuttu++;
                    if (kaikkiAmmuttu > 100) { //jos ympärillä on kaikki jo ammuttu, niin vois jäädä ikuiseen luuppiin
                        break;
                    }
                }
            }
            if (omatLaivat.getGrid()[x][y].getAmmuttu() == false) {
                omatLaivat.getGrid()[x][y].setAmmuttu(true);
                omatLaivat.setShotToGrid(x, y, pane);
                if (omatLaivat.getGrid()[x][y].getLaiva().getPituus() != 0) {
                    if (vihuOsunutLaivaan == true) { //jo toinen osuma --> suunta ok.
                        suuntaSelvilla = true;
                    }
                    vihuOsunutLaivaan = true;
                    osumaX = x;
                    osumaY = y;
                    omatLaivat.getGrid()[x][y].getLaiva().reduceElossa();
                    tappio--;
                    if (omatLaivat.getGrid()[x][y].getLaiva().getElossa() == 0) {
                        vihuOsunutLaivaan = false;
                        suuntaSelvilla = false;
                        merkkaaTuhotunLaivanViereiset(omatLaivat, omatLaivat.getGrid()[x][y].getLaiva(), pane);
                    }
                } else { //jos elsen ottaa pois niin ammutaan vuorotellen, nyt saa lisävuoron jos osuu
                    if (suuntaSelvilla) { //löydettiin laiva, mutta ohi meni, vaihdetaan toiseen suuntaan.
                        if (suunta == 0) {
                            suunta = 2;
                        } else if (suunta == 1) {
                            suunta = 3;
                        } else if (suunta == 2) {
                            suunta = 0;
                        } else if (suunta == 3) {
                            suunta = 1;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void computerShootsLevelExpert(Pane pane) { //tuhoaa osutun laivan ja haarukoi kahden-ruudun-mittaiset-laivat --> ampuu joka toiseen ruutuun
        while (true) {
            Random ran = new Random();
            int x = ran.nextInt(10);
            int y = ran.nextInt(10);

            //elikkäs ammu joka toinen ruutu (haarukoi laivat joiden pituus > 1). Etsi kumpia "jokatoisia" on enemmän ammuttu ja ammu niitä.
            int parillisiaEnemmanKuinParittomia = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (omatLaivat.getGrid()[x][y].getAmmuttu()) { //ota tietty vain ammutut huomioon
                        if ((i + j) % 2 == 0) {
                            parillisiaEnemmanKuinParittomia++;
                        } else {
                            parillisiaEnemmanKuinParittomia--;
                        }
                    }
                }
            }
            while (true) { //jos ollaan ampumassa oikeaan "jokatoiseen" (ks yllä), niin break; muuten arvotaan uudet random numerot
                if (parillisiaEnemmanKuinParittomia > 0) {
                    if ((x + y) % 2 == 0) {
                        break;
                    }
                } else {
                    if ((x + y) % 2 != 0) {
                        break;
                    }
                }
                x = ran.nextInt(10);
                y = ran.nextInt(10);
            }

            if (vihuOsunutLaivaan == true) {
                int kaikkiAmmuttu = 1;
                while (true) {
                    int r = ran.nextInt(4);
                    if (suuntaSelvilla == true) {
                        r = suunta;
                    }
                    if (r == 0) {
                        if (osumaX != 9 && omatLaivat.getGrid()[osumaX + 1][osumaY].getAmmuttu() == false) {
                            x = osumaX + 1;
                            y = osumaY;
                            kaikkiAmmuttu = 0;
                            suunta = 0;
                            break;
                        }
                    }
                    if (r == 1) {
                        if (osumaX != 0 && omatLaivat.getGrid()[osumaX - 1][osumaY].getAmmuttu() == false) {
                            x = osumaX - 1;
                            y = osumaY;
                            kaikkiAmmuttu = 0;
                            suunta = 1;
                            break;
                        }
                    }
                    if (r == 2) {
                        if (osumaY != 9 && omatLaivat.getGrid()[osumaX][osumaY + 1].getAmmuttu() == false) {
                            x = osumaX;
                            y = osumaY + 1;
                            kaikkiAmmuttu = 0;
                            suunta = 2;
                            break;
                        }
                    }
                    if (r == 3) {
                        if (osumaY != 0 && omatLaivat.getGrid()[osumaX][osumaY - 1].getAmmuttu() == false) {
                            x = osumaX;
                            y = osumaY - 1;
                            kaikkiAmmuttu = 0;
                            suunta = 3;
                            break;
                        }
                    }
                    kaikkiAmmuttu++;
                    if (kaikkiAmmuttu > 100) { //jos ympärillä on kaikki jo ammuttu, niin vois jäädä ikuiseen luuppiin
                        break;
                    }
                }
            }
            if (omatLaivat.getGrid()[x][y].getAmmuttu() == false) {
                omatLaivat.getGrid()[x][y].setAmmuttu(true);
                omatLaivat.setShotToGrid(x, y, pane);
                if (omatLaivat.getGrid()[x][y].getLaiva().getPituus() != 0) {
                    if (vihuOsunutLaivaan == true) { //jo toinen osuma --> suunta ok.
                        suuntaSelvilla = true;
                    }
                    vihuOsunutLaivaan = true;
                    osumaX = x;
                    osumaY = y;
                    omatLaivat.getGrid()[x][y].getLaiva().reduceElossa();
                    tappio--;
                    if (omatLaivat.getGrid()[x][y].getLaiva().getElossa() == 0) {
                        vihuOsunutLaivaan = false;
                        suuntaSelvilla = false;
                        merkkaaTuhotunLaivanViereiset(omatLaivat, omatLaivat.getGrid()[x][y].getLaiva(), pane);
                    }
                } else { //jos elsen ottaa pois niin ammutaan vuorotellen, nyt saa lisävuoron jos osuu
                    if (suuntaSelvilla) { //löydettiin laiva, mutta ohi meni, vaihdetaan toiseen suuntaan.
                        if (suunta == 0) {
                            suunta = 2;
                        } else if (suunta == 1) {
                            suunta = 3;
                        } else if (suunta == 2) {
                            suunta = 0;
                        } else if (suunta == 3) {
                            suunta = 1;
                        }
                    }
                    break;
                }
            }
        }
    }

    public Pane piirraGrid(Pane pane) {
        for (int x = 0; x <= LEVEYS; x = x + LEVEYS / 10) {
            pane.getChildren().add(new Line(0, x, KORKEUS, x));
            pane.getChildren().add(new Line(x, 0, x, LEVEYS));
        }
        return pane;
    }

    public void sekoitaLaivat(Grid gridi, ArrayList<Laiva> laivaLista) {
        for (Laiva laiva : laivaLista) {
            int yliMeni = 1;
            int x = 0;
            int y = 0;
            int suunta = 0;
            while (yliMeni == 1) {
                yliMeni = 0;
                Random ran = new Random();
                x = ran.nextInt(10);
                y = ran.nextInt(10);
                suunta = ran.nextInt(4);
                //check että ei mene yli reunoista
                if (suunta == 0) {
                    if (x + laiva.getPituus() > 9) {
                        yliMeni = 1;
                    }
                    for (int i = -1; i <= laiva.getPituus(); i++) {
                        for (int j = -1; j < 2; j++) {
                            if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                                if (gridi.getGrid()[x + i][y + j].getLaiva().getPituus() != 0) {
                                    yliMeni = 1; //laiva liian lähellä
                                }
                            }
                        }
                    }
                } else if (suunta == 1) {
                    if (y + laiva.getPituus() > 9) {
                        yliMeni = 1;
                    }
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j <= laiva.getPituus(); j++) {
                            if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                                if (gridi.getGrid()[x + i][y + j].getLaiva().getPituus() != 0) {
                                    yliMeni = 1; //laiva liian lähellä
                                }
                            }
                        }
                    }
                } else if (suunta == 2) {
                    if (x - laiva.getPituus() < 0) {
                        yliMeni = 1;
                    }
                    for (int i = 1; i >= -laiva.getPituus(); i--) {
                        for (int j = -1; j < 2; j++) {
                            if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                                if (gridi.getGrid()[x + i][y + j].getLaiva().getPituus() != 0) {
                                    yliMeni = 1; //laiva liian lähellä
                                }
                            }
                        }
                    }
                } else if (suunta == 3) {
                    if (y - laiva.getPituus() < 0) {
                        yliMeni = 1;
                    }
                    for (int i = -1; i < 2; i++) {
                        for (int j = 1; j >= -laiva.getPituus(); j--) {
                            if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                                if (gridi.getGrid()[x + i][y + j].getLaiva().getPituus() != 0) {
                                    yliMeni = 1; //laiva liian lähellä
                                }
                            }
                        }
                    }
                }
            }
            gridi.setLaivaToGrid(x, y, laiva, suunta, laiva.getPituus());
        }
    }

    public void luoLaiva(int x, int y, int pituus, Pane pane, ArrayList laivaLista, int r, int g, int b, double opacity) {
        Laiva laiva = new Laiva(x, y, 0.0, pituus, 0.5);
        laiva.setFill(r, g, b, opacity);
        laivaLista.add(laiva);
        pane.getChildren().add(laiva.getLaiva());
    }

    public void alaosanLaivat(Pane pane, ArrayList laivaLista, int offsetFromLeft, int r, int g, int b, double opacity) {
        luoLaiva(offsetFromLeft + 40, 20, 1, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 40, 60, 1, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 80, 20, 1, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 80, 60, 1, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 120, 20, 2, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 120, 60, 2, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 200, 20, 2, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 200, 60, 3, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 320, 60, 3, pane, laivaLista, r, g, b, opacity);
        luoLaiva(offsetFromLeft + 280, 20, 4, pane, laivaLista, r, g, b, opacity);
    }

    public Pane alkuRiviLaivat(Pane pane, ArrayList laivaLista, double opacity) {
        int b = 0;
        for (int i = 1; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                double a = (double) i;
                int m = (int) Math.abs(Math.ceil((a - 1) / a) - 1);
                b += Math.abs(m - 1) * 1;
                Laiva laiva = new Laiva(450 + 140 + (j - 1) * m * 40, 60 + b * 40, 0.0, i, opacity);
                laivaLista.add(laiva);
//                laiva.moveLaivaOnMouseDragged(scene);
//                laiva.moveLaivaOnMouseClicked(scene);
                pane.getChildren().add(laiva.getLaiva());
            }
        }
        return pane;
    }

    public void merkkaaTuhotunLaivanViereiset(Grid gridi, Laiva laiva, Pane pane) {
        int x = laiva.getX();
        int y = laiva.getY();
        int suunta = laiva.getSuunta();
        int pituus = laiva.getPituus();
        if (suunta == 0) {
            for (int i = -1; i <= laiva.getPituus(); i++) {
                for (int j = -1; j < 2; j++) {
                    if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                        if (gridi.getGrid()[x + i][y + j].getAmmuttu() == false) {
                            gridi.getGrid()[x + i][y + j].setAmmuttu(true);
                            gridi.setShotToGrid(x + i, y + j, pane);
                        }
                    }
                }
            }
        } else if (suunta == 1) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j <= laiva.getPituus(); j++) {
                    if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                        if (gridi.getGrid()[x + i][y + j].getAmmuttu() == false) {
                            gridi.getGrid()[x + i][y + j].setAmmuttu(true);
                            gridi.setShotToGrid(x + i, y + j, pane);
                        }
                    }
                }
            }
        } else if (suunta == 2) {
            for (int i = 1; i >= -laiva.getPituus(); i--) {
                for (int j = -1; j < 2; j++) {
                    if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                        if (gridi.getGrid()[x + i][y + j].getAmmuttu() == false) {
                            gridi.getGrid()[x + i][y + j].setAmmuttu(true);
                            gridi.setShotToGrid(x + i, y + j, pane);
                        }
                    }
                }
            }
        } else if (suunta == 3) {
            for (int i = -1; i < 2; i++) {
                for (int j = 1; j >= -laiva.getPituus(); j--) {
                    if (x + i >= 0 && y + j >= 0 && x + i < 10 && y + j < 10) {
                        if (gridi.getGrid()[x + i][y + j].getAmmuttu() == false) {
                            gridi.getGrid()[x + i][y + j].setAmmuttu(true);
                            gridi.setShotToGrid(x + i, y + j, pane);
                        }
                    }
                }
            }
        }
        //merkkaa myös alareunan laivat tuhotuksi
        if (pelaajanVuoro == true) {
            for (Laiva tuhottuLaiva : vihunTuhotutLaivat) {
                if (tuhottuLaiva.getPituus() == laiva.getPituus()) { //merkataan joku samanmittainen laiva tuhotuksi
                    tuhottuLaiva.setTranslateY(999);
                    //pane.getChildren().remove(tuhottuLaiva.getLaiva());
                    vihunTuhotutLaivat.remove(tuhottuLaiva);
                    break;
                }
            }
        } else {
            for (Laiva tuhottuLaiva : omatTuhotutLaivat) {
                if (tuhottuLaiva.getPituus() == laiva.getPituus()) { //merkataan joku samanmittainen laiva tuhotuksi
                    tuhottuLaiva.setTranslateY(999);
                    //pane.getChildren().remove(tuhottuLaiva.getLaiva());
                    omatTuhotutLaivat.remove(tuhottuLaiva);
                    break;
                }
            }

        }
    }

}
