package Dashboard;

import java.io.*;
import java.util.ArrayList;

public class Speicher {

    // Eingegebene Werte in einer .txt ablegen
    public static void listeSpeichern(ArrayList<Double> werte, String dateiPfad) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dateiPfad))) {
            oos.writeObject(werte);
        }
    }

    // Gespeicherte .txt laden und in Liste übergeben
    public static ArrayList<Double> listeLaden(String dateiPfad) throws IOException, ClassNotFoundException {
        ArrayList<Double> geladeneListe;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dateiPfad))) {
            @SuppressWarnings("unchecked")
            ArrayList<Double> tempListe = (ArrayList<Double>) ois.readObject();
            geladeneListe = tempListe;
        }
        return geladeneListe;
    }
}
