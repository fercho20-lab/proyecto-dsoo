import javax.swing.*;
import java.awt.*;

public class VentanaEstadisticas extends JFrame {
    private static final int WIDTH = 380;
    private static final int HEIGHT = 240;
    private Biblioteca biblioteca;

    public VentanaEstadisticas(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Estadisticas");
        setSize(WIDTH, HEIGHT);
        setLayout(new GridLayout(5, 1, 0, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createContents();
        setVisible(true);
    }

    private void createContents() {
        add(new JLabel("  Usuario actual: " + biblioteca.getUsuarioActual()));
        add(new JLabel("  Canciones: " + biblioteca.contarCanciones()));
        add(new JLabel("  Podcasts: " + biblioteca.contarPodcasts()));
        add(new JLabel("  Reproducciones totales: " + biblioteca.contarReproduccionesTotales()));
        Contenido masReproducido = biblioteca.obtenerMasReproducido();
        if (masReproducido == null) {
            add(new JLabel("  Mas reproducido: -"));
        } else {
            add(new JLabel("  Mas reproducido: " + masReproducido.getTitulo()));
        }
    }
}
