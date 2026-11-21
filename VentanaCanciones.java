import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VentanaCanciones extends JFrame {
    private static final int WIDTH = 760;
    private static final int HEIGHT = 420;
    private Biblioteca biblioteca;
    private ArrayList<Contenido> mostrados;
    private JTextField campoBusqueda;
    private JTextArea areaLista;
    private JComboBox<Contenido> selectorContenido;
    private JButton botonBuscar;
    private JButton botonReproducir;
    private JButton botonFavorito;
    private JButton botonAgregar;
    private JButton botonEliminar;

    public VentanaCanciones(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("Canciones y Podcasts");
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createContents();
        actualizar(biblioteca.getContenidos());
        setVisible(true);
    }

    private void createContents() {
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        norte.add(new JLabel("Buscar:"));
        campoBusqueda = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(new Listener());
        norte.add(campoBusqueda);
        norte.add(botonBuscar);
        add(norte, BorderLayout.NORTH);

        areaLista = new JTextArea();
        areaLista.setEditable(false);
        areaLista.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(areaLista), BorderLayout.CENTER);

        JPanel sur = new JPanel(new FlowLayout());
        selectorContenido = new JComboBox<Contenido>();
        botonReproducir = new JButton("Reproducir");
        botonFavorito = new JButton("Favorito");
        botonAgregar = new JButton("Agregar");
        botonEliminar = new JButton("Eliminar");
        botonReproducir.addActionListener(new Listener());
        botonFavorito.addActionListener(new Listener());
        botonAgregar.addActionListener(new Listener());
        botonEliminar.addActionListener(new Listener());
        sur.add(selectorContenido);
        sur.add(botonReproducir);
        sur.add(botonFavorito);
        sur.add(botonAgregar);
        sur.add(botonEliminar);
        add(sur, BorderLayout.SOUTH);
    }

    private void actualizar(ArrayList<Contenido> lista) {
        mostrados = lista;
        Contenido anterior = (Contenido) selectorContenido.getSelectedItem();
        selectorContenido.removeAllItems();
        String texto = String.format("%-32s %-8s %-32s %-8s %-6s %s\n", "TITULO", "TIPO", "DETALLE", "DURAC.", "REPROD", "FAV");
        int i = 0;
        while (i < lista.size()) {
            Contenido c = lista.get(i);
            String marca = "";
            if (c.esFavorito()) {
                marca = "*";
            }
            texto = texto + String.format("%-32s %-8s %-32s %-8s %-6d %s\n", c.getTitulo(), c.getTipo(), c.getDetalle(), c.getDuracionTexto(), c.getReproducciones(), marca);
            selectorContenido.addItem(c);
            i = i + 1;
        }
        areaLista.setText(texto);
        if (anterior != null && lista.contains(anterior)) {
            selectorContenido.setSelectedItem(anterior);
        }
    }

    private Contenido elegido() {
        Contenido c = (Contenido) selectorContenido.getSelectedItem();
        if (c == null) {
            Aviso.mensaje(this, "No hay contenido seleccionado");
        }
        return c;
    }

    private void agregarContenido() {
        JComboBox<String> tipo = new JComboBox<String>(new String[] {"Cancion", "Podcast"});
        JTextField titulo = new JTextField(15);
        JTextField duracion = new JTextField(15);
        JComboBox<Genero> genero = new JComboBox<Genero>(Genero.values());
        JComboBox<Artista> artista = new JComboBox<Artista>();
        int i = 0;
        while (i < biblioteca.getArtistas().size()) {
            artista.addItem(biblioteca.getArtistas().get(i));
            i = i + 1;
        }
        JTextField anfitrion = new JTextField(15);
        JTextField episodio = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));
        panel.add(new JLabel("Tipo:"));
        panel.add(tipo);
        panel.add(new JLabel("Titulo:"));
        panel.add(titulo);
        panel.add(new JLabel("Duracion (segundos):"));
        panel.add(duracion);
        panel.add(new JLabel("Genero (cancion):"));
        panel.add(genero);
        panel.add(new JLabel("Artista (cancion):"));
        panel.add(artista);
        panel.add(new JLabel("Anfitrion (podcast):"));
        panel.add(anfitrion);
        panel.add(new JLabel("Episodio (podcast):"));
        panel.add(episodio);
        int respuesta = JOptionPane.showConfirmDialog(this, panel, "Agregar contenido", JOptionPane.OK_CANCEL_OPTION);
        if (respuesta != JOptionPane.OK_OPTION) {
            return;
        }
        String nombre = titulo.getText().trim();
        if (nombre.length() == 0) {
            Aviso.mensaje(this, "Escribe el titulo");
            return;
        }
        if (biblioteca.existeTitulo(nombre)) {
            Aviso.mensaje(this, "Ya existe un contenido con ese titulo");
            return;
        }
        int segundos;
        try {
            segundos = Integer.parseInt(duracion.getText().trim());
        } catch (NumberFormatException ex) {
            Aviso.mensaje(this, "La duracion debe ser un numero entero de segundos");
            return;
        }
        if (segundos <= 0) {
            Aviso.mensaje(this, "La duracion debe ser mayor que cero");
            return;
        }
        if (tipo.getSelectedItem().equals("Cancion")) {
            if (artista.getSelectedItem() == null) {
                Aviso.mensaje(this, "Primero registra un artista");
                return;
            }
            biblioteca.agregarContenido(new Cancion(nombre, segundos, (Genero) genero.getSelectedItem(), (Artista) artista.getSelectedItem()));
        } else {
            int numero;
            try {
                numero = Integer.parseInt(episodio.getText().trim());
            } catch (NumberFormatException ex) {
                Aviso.mensaje(this, "El episodio debe ser un numero entero");
                return;
            }
            if (anfitrion.getText().trim().length() == 0) {
                Aviso.mensaje(this, "Escribe el anfitrion");
                return;
            }
            biblioteca.agregarContenido(new Podcast(nombre, segundos, anfitrion.getText().trim(), numero));
        }
        campoBusqueda.setText("");
        actualizar(biblioteca.getContenidos());
    }

    private void eliminarContenido() {
        Contenido c = elegido();
        if (c == null) {
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(this, "Eliminar \"" + c.getTitulo() + "\"?", "Eliminar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            biblioteca.eliminarContenido(c);
            campoBusqueda.setText("");
            actualizar(biblioteca.getContenidos());
        }
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == botonBuscar) {
                actualizar(biblioteca.buscarPorTitulo(campoBusqueda.getText()));
            } else if (e.getSource() == botonReproducir) {
                Contenido c = elegido();
                if (c != null) {
                    biblioteca.getUsuarioActual().reproducir(c);
                    actualizar(mostrados);
                }
            } else if (e.getSource() == botonFavorito) {
                Contenido c = elegido();
                if (c != null) {
                    biblioteca.getUsuarioActual().marcarFavorito(c);
                    actualizar(mostrados);
                }
            } else if (e.getSource() == botonAgregar) {
                agregarContenido();
            } else if (e.getSource() == botonEliminar) {
                eliminarContenido();
            }
        }
    }
}
