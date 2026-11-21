import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaArtistas extends JFrame {
    private static final int WIDTH = 560;
    private static final int HEIGHT = 400;
    private Biblioteca biblioteca;
    private JTextArea areaLista;
    private JButton botonAgregarArtista;
    private JButton botonAgregarAlbum;

    public VentanaArtistas(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Artistas y Albumes");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createContents();
        actualizar();
        setVisible(true);
    }

    private void createContents() {
        areaLista = new JTextArea();
        areaLista.setEditable(false);
        areaLista.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(areaLista), BorderLayout.CENTER);

        JPanel sur = new JPanel(new FlowLayout());
        botonAgregarArtista = new JButton("Agregar artista");
        botonAgregarAlbum = new JButton("Agregar album");
        botonAgregarArtista.addActionListener(new Listener());
        botonAgregarAlbum.addActionListener(new Listener());
        sur.add(botonAgregarArtista);
        sur.add(botonAgregarAlbum);
        add(sur, BorderLayout.SOUTH);
    }

    private void actualizar() {
        String texto = "";
        int i = 0;
        while (i < biblioteca.getArtistas().size()) {
            Artista artista = biblioteca.getArtistas().get(i);
            texto = texto + artista.getNombre() + "\n";
            int j = 0;
            while (j < artista.getAlbumes().size()) {
                Album album = artista.getAlbumes().get(j);
                String palabra = " canciones";
                if (album.getCanciones().size() == 1) {
                    palabra = " cancion";
                }
                texto = texto + "    " + album + " - " + album.getCanciones().size() + palabra + "\n";
                j = j + 1;
            }
            texto = texto + "\n";
            i = i + 1;
        }
        areaLista.setText(texto);
    }

    private void agregarArtista() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del artista:");
        if (nombre == null) {
            return;
        }
        nombre = nombre.trim();
        if (nombre.length() == 0) {
            Aviso.mensaje(this, "Escribe el nombre");
        } else if (biblioteca.existeArtista(nombre)) {
            Aviso.mensaje(this, "Ese artista ya existe");
        } else {
            biblioteca.agregarArtista(new Artista(nombre));
            actualizar();
        }
    }

    private void agregarAlbum() {
        if (biblioteca.getArtistas().size() == 0) {
            Aviso.mensaje(this, "Primero registra un artista");
            return;
        }
        JComboBox<Artista> artista = new JComboBox<Artista>();
        int i = 0;
        while (i < biblioteca.getArtistas().size()) {
            artista.addItem(biblioteca.getArtistas().get(i));
            i = i + 1;
        }
        JTextField titulo = new JTextField(15);
        JTextField anio = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Artista:"));
        panel.add(artista);
        panel.add(new JLabel("Titulo del album:"));
        panel.add(titulo);
        panel.add(new JLabel("Anio:"));
        panel.add(anio);
        int respuesta = JOptionPane.showConfirmDialog(this, panel, "Agregar album", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }
        if (titulo.getText().trim().length() == 0) {
            Aviso.mensaje(this, "Escribe el titulo del album");
            return;
        }
        int numero;
        try {
            numero = Integer.parseInt(anio.getText().trim());
        } catch (NumberFormatException ex) {
            Aviso.mensaje(this, "El anio debe ser un numero");
            return;
        }
        if (numero < 1900 || numero > 2100) {
            Aviso.mensaje(this, "Anio no valido");
            return;
        }
        Artista elegido = (Artista) artista.getSelectedItem();
        Album album = new Album(titulo.getText().trim(), numero);
        // Las canciones del artista que aun no estan en un album pasan a este album nuevo
        int j = 0;
        while (j < biblioteca.getContenidos().size()) {
            Contenido c = biblioteca.getContenidos().get(j);
            if (c instanceof Cancion && ((Cancion) c).getArtista() == elegido && !estaEnAlbum(elegido, (Cancion) c)) {
                album.agregarCancion((Cancion) c);
            }
            j = j + 1;
        }
        elegido.agregarAlbum(album);
        actualizar();
    }

    private boolean estaEnAlbum(Artista artista, Cancion cancion) {
        int i = 0;
        while (i < artista.getAlbumes().size()) {
            if (artista.getAlbumes().get(i).getCanciones().contains(cancion)) {
                return true;
            }
            i = i + 1;
        }
        return false;
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botonAgregarArtista) {
                agregarArtista();
            } else if (e.getSource() == botonAgregarAlbum) {
                agregarAlbum();
            }
        }
    }
}
