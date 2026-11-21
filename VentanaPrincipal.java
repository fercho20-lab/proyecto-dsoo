import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {
    private static final int WIDTH = 420;
    private static final int HEIGHT = 300;
    private Biblioteca biblioteca;
    private JComboBox<Usuario> selectorUsuario;
    private JButton botonContenido;
    private JButton botonArtistas;
    private JButton botonPlaylists;
    private JButton botonEstadisticas;

    public VentanaPrincipal(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Biblioteca Musical");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        setVisible(true);
    }

    private void createContents() {
        JPanel norte = new JPanel(new FlowLayout());
        norte.add(new JLabel("Usuario:"));
        selectorUsuario = new JComboBox<Usuario>();
        int i = 0;
        while (i < biblioteca.getUsuarios().size()) {
            selectorUsuario.addItem(biblioteca.getUsuarios().get(i));
            i = i + 1;
        }
        selectorUsuario.setSelectedItem(biblioteca.getUsuarioActual());
        selectorUsuario.addActionListener(new Listener());
        norte.add(selectorUsuario);
        add(norte, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(2, 2, 10, 10));
        botonContenido = new JButton("Canciones y Podcasts");
        botonArtistas = new JButton("Artistas y Albumes");
        botonPlaylists = new JButton("Playlists");
        botonEstadisticas = new JButton("Estadisticas");
        botonContenido.addActionListener(new Listener());
        botonArtistas.addActionListener(new Listener());
        botonPlaylists.addActionListener(new Listener());
        botonEstadisticas.addActionListener(new Listener());
        centro.add(botonContenido);
        centro.add(botonArtistas);
        centro.add(botonPlaylists);
        centro.add(botonEstadisticas);
        add(centro, BorderLayout.CENTER);
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectorUsuario) {
                biblioteca.setUsuarioActual((Usuario) selectorUsuario.getSelectedItem());
            } else if (e.getSource() == botonContenido) {
                new VentanaCanciones(biblioteca);
            } else if (e.getSource() == botonArtistas) {
                new VentanaArtistas(biblioteca);
            } else if (e.getSource() == botonPlaylists) {
                new VentanaPlaylists(biblioteca);
            } else if (e.getSource() == botonEstadisticas) {
                new VentanaEstadisticas(biblioteca);
            }
        }
    }
}
