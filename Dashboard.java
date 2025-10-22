package Dashboard;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    // GUI-Komponenten: JFrame mit Texteingabe und Bestätigungsbutton
    private final JTextField eingabeFeld = new JTextField(10);
    JButton bestaetigung = new JButton("Wert hinzufügen");
    JButton speicherButton = new JButton("Speichern");
    JButton ladeButton = new JButton("Laden");
    JButton loeschButton = new JButton("Löschen");
    private final JLabel durchschnittsAnzeige = new JLabel("Durchschnitt: N/A", SwingConstants.CENTER);
    // Platzhalter für das Diagramm
    private XYSeries datenSerie;
    private JFreeChart linienDiagramm;
    // Datenspeicher
    private ArrayList<Double> werte = new ArrayList<>();

    public Dashboard() {
        // 1. Fenster erstellen
        super("Mein Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(255,255,255));
        setForeground(new Color(0,0,0));

        // 2. Diagramm-Objekte erstellen, bevor sie verwendet werden
        initialisiereDiagramm();

        // 3. Layout für die Eingabeelemente erstellen
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Neuer Wert:"));
        inputPanel.add(eingabeFeld);
        inputPanel.add(bestaetigung);
        inputPanel.add(speicherButton);
        inputPanel.add(ladeButton);
        inputPanel.add(loeschButton);

        styleButton(bestaetigung);
        styleButton(speicherButton);
        styleButton(ladeButton);
        styleButton(loeschButton);

        inputPanel.setBackground(new Color(240,240,240));
        durchschnittsAnzeige.setForeground(new Color(0,102,204));
        durchschnittsAnzeige.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));

        // 4. Haupt-Layout des Fensters festlegen und den ChartPanel hinzufügen
        setLayout(new BorderLayout(10, 10));
        ChartPanel chartPanel = new ChartPanel(linienDiagramm); 
        add(chartPanel, BorderLayout.CENTER);            
        add(inputPanel, BorderLayout.SOUTH);
        add(durchschnittsAnzeige, BorderLayout.NORTH);

        // 5. ActionListener erstellen
        // Bestätigungsbutton
        bestaetigung.addActionListener(_ -> {
            try {
                String inputText = eingabeFeld.getText();
                double neuerWert = Double.parseDouble(inputText);

                werte.add(neuerWert);
                System.out.println("Werte-Liste: " + werte);

                eingabeFeld.setText("");
                updateDashboard();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Speicherbutton
        speicherButton.addActionListener(_ -> {
            try {
                Speicher.listeSpeichern(werte, "werte.txt");
                JOptionPane.showMessageDialog(this,
                        "Liste erfolgreich in werte.txt gespeichert!",
                        "Erfolg",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                        "Fehler beim Speichern der Datei: "
                        + ex.getMessage(), "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ladebutton
        ladeButton.addActionListener(_ -> {
            try {
                // Die geladene Liste in die Arbeitsliste übergeben
                werte = Speicher.listeLaden("werte.txt");

                updateDashboard();

                JOptionPane.showMessageDialog(this,
                        "Liste erfolgreich aus werte.txt geladen!",
                        "Erfolg",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this,
                        "Fehler beim Laden der Datei: "
                                + ex.getMessage(), "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Löschbutton
        loeschButton.addActionListener(_ -> {
            werte.clear();

            updateDashboard();

            JOptionPane.showMessageDialog(this,
                    "Werte erfolgreich gelöscht!",
                    "Erfolg",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // Diagramm erstellen.
    private void initialisiereDiagramm() {
        // Koordinaten in Serie speichern
        datenSerie = new XYSeries("Messwerte");

        // Die Serie einer Sammlung hinzufügen
        XYSeriesCollection dataset = new XYSeriesCollection(datenSerie);

        // Das Diagramm mit der ChartFactory erstellen
        linienDiagramm = ChartFactory.createXYLineChart(
                "Werteverlauf",      // Titel des Diagramms
                "Eingabe Nr.",       // X-Achsen-Beschriftung
                "Wert",              // Y-Achsen-Beschriftung
                dataset,             // Das Dataset
                PlotOrientation.VERTICAL,
                true,                // Legende anzeigen?
                true,                // Tooltips?
                false                // URLs?
        );
        // Zeichenbereich des Diagramms
        XYPlot plot = linienDiagramm.getXYPlot();

        // X-Achse (Domain Axis) zu einer NumberAxis casten
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();

        // Tick Units auf einen Standard, der nur Ganzzahlen verwendet setzen
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    }

    // Dashboard updaten
    private void updateDashboard() {
        // Durchschnitt berechnen und anzeigen
        double summe = 0;
        for (double wert : werte) {
            summe += wert;
        }
        double durchschnitt = werte.isEmpty() ? 0 : summe / werte.size();
        durchschnittsAnzeige.setText(String.format("Durchschnitt: %.2f", durchschnitt));

        // Diagramm-Daten aktualisieren
        datenSerie.clear();
        // Alle Werte aus unserer Liste der Serie hinzufügen
        for (int i = 0; i < werte.size(); i++) {
            // Die X-Koordinate ist die Eingabenummer (1, 2, 3, ...), Y ist der Wert selbst
            datenSerie.add(i + 1, werte.get(i));
        }
        // Der ChartPanel aktualisiert sich automatisch, wenn sich das Dataset ändert.
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(194, 197, 204));
        button.setForeground(new Color(0, 102, 204));
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
    }
}

