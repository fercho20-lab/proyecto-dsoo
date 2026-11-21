import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaPlaylists extends JFrame {
    private static final int WIDTH = 560;
    private static final int HEIGHT = 400;
    private Biblioteca biblioteca;
    private JComboBox<Playlist> selectorPlaylist;
    private JTextArea areaContenido;
    private JButton botonCrear;
    private JButton botonAgregar;
    private JButton botonQuitar;
    private boolean cargando;

    public VentanaPlaylists(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Playlists de " + biblioteca.getUsuarioActual());
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createContents();
        cargarPlaylists();
        setVisible(true);
    }

    private void createContents() {
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        norte.add(new JLabel("Playlist:"));
        selectorPlaylist = new JComboBox<Playlist>();
        selectorPlaylist.addActionListener(new Listener());
        norte.add(selectorPlaylist);
        add(norte, BorderLayout.NORTH);

        areaContenido = new JTextArea();
        areaContenido.setEditable(false);
        areaContenido.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(areaContenido), BorderLayout.CENTER);

        JPanel sur = new JPanel(new FlowLayout());
        botonCrear = new JButton("Crear playlist");
        botonAgregar = new JButton("Agregar contenido");
        botonQuitar = new JButton("Quitar contenido");
        botonCrear.addActionListener(new Listener());
        botonAgregar.addActionListener(new Listener());
        botonQuitar.addActionListener(new Listener());
        sur.add(botonCrear);
        sur.add(botonAgregar);
        sur.add(botonQuitar);
        add(sur, BorderLayout.SOUTH);
    }

    private void cargarPlaylists() {
        cargando = true;
        selectorPlaylist.removeAllItems();
        Usuario usuario = biblioteca.getUsuarioActual();
        int i = 0;
        while (i < usuario.getPlaylists().size()) {
            selectorPlaylist.addItem(usuario.getPlaylists().get(i));
            i = i + 1;
        }
        cargando = false;
        mostrarContenido();
    }

    private void mostrarContenido() {
        Playlist playlist = (Playlist) selectorPlaylist.getSelectedItem();
        if (playlist == null) {
            areaContenido.setText("El usuario no tiene playlists");
            return;
        }
        String texto = "";
        int i = 0;
        while (i < playlist.getContenidos().size()) {
            Contenido c = playlist.getContenidos().get(i);
            texto = texto + String.format("%-32s %-8s %s\n", c.getTitulo(), c.getTipo(), c.getDuracionTexto());
            i = i + 1;
        }
        areaContenido.setText(texto);
    }

    private void crearPlaylist() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre de la nueva playlist:");
        if (nombre == null) {
            return;
        }
        nombre = nombre.trim();
        if (nombre.length() == 0) {
            Aviso.mensaje(this, "Escribe un nombre");
            return;
        }
        Usuario usuario = biblioteca.getUsuarioActual();
        int i = 0;
        while (i < usuario.getPlaylists().size()) {
            if (usuario.getPlaylists().get(i).getNombre().equalsIgnoreCase(nombre)) {
                Aviso.mensaje(this, "Ya tienes una playlist con ese nombre");
                return;
            }
            i = i + 1;
        }
        Playlist nueva = usuario.crearPlaylist(nombre);
        cargarPlaylists();
        selectorPlaylist.setSelectedItem(nueva);
    }

    private void agregarContenido() {
        Playlist playlist = (Playlist) selectorPlaylist.getSelectedItem();
        if (playlist == null) {
            Aviso.mensaje(this, "Primero crea una playlist");
            return;
        }
        ArrayList<Contenido> disponibles = new ArrayList<Contenido>();
        int i = 0;
        while (i < biblioteca.getContenidos().size()) {
            if (biblioteca.puedeAgregarAPlaylist(playlist, biblioteca.getContenidos().get(i))) {
                disponibles.add(biblioteca.getContenidos().get(i));
            }
            i = i + 1;
        }
        if (disponibles.size() == 0) {
            Aviso.mensaje(this, "No hay mas contenido para agregar");
            return;
        }
        Object elegido = JOptionPane.showInputDialog(this, "Contenido a agregar:", "Agregar a " + playlist.getNombre(), JOptionPane.PLAIN_MESSAGE, null, disponibles.toArray(), disponibles.get(0));
        if (elegido != null) {
            playlist.agregar((Contenido) elegido);
            cargarPlaylists();
            selectorPlaylist.setSelectedItem(playlist);
        }
    }

    private void quitarContenido() {
        Playlist playlist = (Playlist) selectorPlaylist.getSelectedItem();
        if (playlist == null || playlist.getContenidos().size() == 0) {
            Aviso.mensaje(this, "La playlist no tiene contenido");
            return;
        }
        Object elegido = JOptionPane.showInputDialog(this, "Contenido a quitar:", "Quitar de " + playlist.getNombre(), JOptionPane.PLAIN_MESSAGE, null, playlist.getContenidos().toArray(), playlist.getContenidos().get(0));
        if (elegido != null) {
            playlist.quitar((Contenido) elegido);
            cargarPlaylists();
            selectorPlaylist.setSelectedItem(playlist);
        }
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectorPlaylist) {
                if (!cargando) {
                    mostrarContenido();
                }
            } else if (e.getSource() == botonCrear) {
                crearPlaylist();
            } else if (e.getSource() == botonAgregar) {
                agregarContenido();
            } else if (e.getSource() == botonQuitar) {
                quitarContenido();
            }
        }
    }
}
