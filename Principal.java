/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.neatbeans.ejercicios;

/**
 *
 * @author ACER
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class Principal extends JFrame {
    
    private Usuario usuarioActual;
    private Biblioteca biblioteca;

    private JTextArea areaTexto;

    public Principal() {

        biblioteca = new Biblioteca();
        cargarDatosIniciales();

        iniciarSesion();

        setTitle("MI SPOTIFY");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
    }
    private void crearInterfaz() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("MI SPOTIFY", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel usuario = new JLabel(
                "Usuario: " + usuarioActual.getNombre(),
                JLabel.CENTER
        );
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));
        panelSuperior.add(titulo);
        panelSuperior.add(usuario);

        JPanel panelBotones = new JPanel(new GridLayout(6, 2, 10, 10));

        agregarBoton(panelBotones, "Ver catalogo", 1);
        agregarBoton(panelBotones, "Buscar por titulo", 2);
        agregarBoton(panelBotones, "Filtrar por genero", 3);
        agregarBoton(panelBotones, "Discografia de artista", 4);
        agregarBoton(panelBotones, "Reproducir contenido", 5);
        agregarBoton(panelBotones, "Crear playlist", 6);
        agregarBoton(panelBotones, "Agregar a playlist", 7);
        agregarBoton(panelBotones, "Quitar de playlist", 8);
        agregarBoton(panelBotones, "Mis playlists", 9);
        agregarBoton(panelBotones, "Marcar favorito", 10);
        agregarBoton(panelBotones, "Mis favoritos", 11);
        agregarBoton(panelBotones, "Estadisticas", 12);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane desplazamiento = new JScrollPane(areaTexto);

        JButton salir = new JButton("SALIR");
        salir.addActionListener(e -> System.exit(0));

        JPanel panelInferior = new JPanel();
        panelInferior.add(salir);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(desplazamiento, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
    private void agregarBoton(JPanel panel, String nombre, int opcion) {

        JButton boton = new JButton(nombre);
        boton.addActionListener(e -> ejecutarOpcion(opcion));
        panel.add(boton);
    }
    private void ejecutarOpcion(int opcion) {

        switch (opcion) {

            case 1:
                listar("CATALOGO", biblioteca.getContenidos());
                break;

            case 2:
                buscarTitulo();
                break;

            case 3:
                filtrarGenero();
                break;

            case 4:
                buscarArtista();
                break;

            case 5:
                reproducirContenido();
                break;

            case 6:
                crearPlaylist();
                break;

            case 7:
                agregarPlaylist();
                break;

            case 8:
                quitarPlaylist();
                break;

            case 9:
                mostrarPlaylists();
                break;

            case 10:
                marcarFavorito();
                break;

            case 11:
                listar("MIS FAVORITOS", usuarioActual.getFavoritos());
                break;

            case 12:
                mostrarEstadisticas();
                break;
        }
    }
    private void listar(String titulo, ArrayList<Contenido> lista) {

        areaTexto.setText("--- " + titulo + " ---\n\n");

        if (lista.isEmpty()) {
            areaTexto.append("No hay contenido disponible.");
        }
        for (int i = 0; i < lista.size(); i++) {
            areaTexto.append((i + 1) + ". ");
            areaTexto.append(lista.get(i).obtenerDetalle());
            areaTexto.append("\n");
        }
    }
    private void buscarTitulo() {

        String texto = JOptionPane.showInputDialog(this, "Ingrese el titulo:");

        if (texto == null || texto.trim().isEmpty()) {
            return;
        }
        Contenido c = biblioteca.buscarPorTitulo(texto.trim());

        if (c == null) {

            JOptionPane.showMessageDialog(this, "Contenido no encontrado.");

        } else {

            areaTexto.setText("CONTENIDO ENCONTRADO\n\n");
            areaTexto.append(c.obtenerDetalle());
        }
    }
    private void filtrarGenero() {

        Genero[] generos = Genero.values();

        Genero g = (Genero) JOptionPane.showInputDialog(
                this,
                "Seleccione un genero:",
                "GENEROS",
                JOptionPane.QUESTION_MESSAGE,
                null,
                generos,
                generos[0]
        );

        if (g != null) {
            listar("GENERO " + g, biblioteca.filtrarPorGenero(g));
        }
    }
    private void buscarArtista() {

        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del artista:");

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        Artista a = biblioteca.buscarArtista(nombre.trim());

        if (a == null) {

            JOptionPane.showMessageDialog(this, "Artista no encontrado.");

        } else {

            areaTexto.setText(a.mostrarDiscografia());
        }
    }
    private Contenido elegirContenido(ArrayList<Contenido> lista) {

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay contenido disponible.");
            return null;
        }
        String[] opciones = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            opciones[i] = lista.get(i).getTitulo();
        }
        String seleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione un contenido:",
                "CATALOGO",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        if (seleccionado == null) {
            return null;
        }
        for (int i = 0; i < lista.size(); i++) {

            if (opciones[i].equals(seleccionado)) {
                return lista.get(i);
            }
        }
        return null;
    }
    private void reproducirContenido() {

        Contenido c = elegirContenido(biblioteca.getContenidos());

        if (c != null) {

            usuarioActual.reproducir(c);
            JOptionPane.showMessageDialog(this,"Reproduciendo: " + c.getTitulo());
            c.pausar();
        }
    }
    private void crearPlaylist() {

        String nombre = JOptionPane.showInputDialog(this, "Nombre de la nueva playlist:");
        if (nombre == null) {
            return;
        }
        Playlist p = usuarioActual.crearPlaylist(nombre);
        if (p == null) {

            JOptionPane.showMessageDialog(this, "No se pudo crear la playlist.");
        } else {
            JOptionPane.showMessageDialog(this, "Playlist creada: " + p.getNombre());
        }
    }
    private Playlist elegirPlaylist() {

        ArrayList<Playlist> lista = usuarioActual.getPlaylists();

        if (lista.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Primero debes crear una playlist.");
            return null;
        }
        String[] opciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            opciones[i] = lista.get(i).getNombre();
        }
        String seleccion = (String) JOptionPane.showInputDialog(this,"Seleccione una playlist:","MIS PLAYLISTS",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        if (seleccion == null) {
            return null;
        }
        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).getNombre().equals(seleccion)) {
                return lista.get(i);
            }
        }
        return null;
    }
    private void agregarPlaylist() {

        Playlist p = elegirPlaylist();
        if (p == null) {
            return;
        }
        Contenido c = elegirContenido(biblioteca.getContenidos());

        if (c == null) {
            return;
        }
        if (biblioteca.puedeAgregarAPlaylist(p, c)
                && p.agregarContenido(c)) {

            JOptionPane.showMessageDialog(this, "Contenido agregado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo agregar el contenido.");
        }
    }
    private void quitarPlaylist() {

        Playlist p = elegirPlaylist();

        if (p == null) {
            return;
        }
        Contenido c = elegirContenido(p.getContenidos());

        if (c != null) {
            p.eliminarContenido(c);
            JOptionPane.showMessageDialog(this, "Contenido eliminado.");
        }
    }
    private void mostrarPlaylists() {
        ArrayList<Playlist> lista = usuarioActual.getPlaylists();

        areaTexto.setText("MIS PLAYLISTS\n\n");

        if (lista.isEmpty()) {
            areaTexto.append("No tienes playlists.");
        }

        for (Playlist p : lista) {
            areaTexto.append(p.mostrar());
            areaTexto.append("\n");
        }
    }
    private void marcarFavorito() {

        Contenido c = elegirContenido(biblioteca.getContenidos());

        if (c == null) {
            return;
        }
        if (c.esFavorito()) {
            usuarioActual.quitarFavorito(c);
            JOptionPane.showMessageDialog(this, "Quitado de favoritos.");
        } else {
            usuarioActual.agregarFavorito(c);
            JOptionPane.showMessageDialog(this, "Agregado a favoritos.");
        }
    }
    private void mostrarEstadisticas() {
        areaTexto.setText("ESTADISTICAS DE LA BIBLIOTECA\n\n");
        areaTexto.append("Canciones: " + biblioteca.contarCanciones() + "\n");
        areaTexto.append("Podcasts: " + biblioteca.contarPodcasts() + "\n");
        areaTexto.append("Reproducciones totales: "+ biblioteca.contarReproduccionesTotales() + "\n");

        Contenido top = biblioteca.obtenerMasReproducido();
        if (top == null) {
            areaTexto.append("Mas reproducido: Ninguno.");
        } else {
            areaTexto.append("Mas reproducido: " + top.getTitulo()+" (" + top.getVecesReproducido() + " veces)");
        }
    }
    private void iniciarSesion() {
        while (usuarioActual == null) {

            String nombre = JOptionPane.showInputDialog(
                    null, "Ingrese su nombre:"
            );
            if (nombre == null) {
                System.exit(0);
            }
            String correo = JOptionPane.showInputDialog(null, "Ingrese su correo:");
            if (correo == null) {
                System.exit(0);
            }
            Usuario existente = biblioteca.buscarUsuario(correo);
            if (existente != null) {
                usuarioActual = existente;
            } else {
                try {
                    Usuario nuevo = new Usuario(nombre, correo);
                    biblioteca.registrarUsuario(nuevo);
                    usuarioActual = nuevo;
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Datos invalidos: " + e.getMessage());
                }
            }
        }
    }
    private void cargarDatosIniciales() {
        Artista soda = new Artista("Soda Stereo");
        Artista mirlos = new Artista("Los Mirlos");
        Artista lavoe = new Artista("Hector Lavoe");
        Artista bad = new Artista("Bad Bunny");

        biblioteca.agregarArtista(soda);
        biblioteca.agregarArtista(mirlos);
        biblioteca.agregarArtista(lavoe);
        biblioteca.agregarArtista(bad);

        Album album = new Album("Cancion Animal", 1990, soda);

        Cancion c1 = new Cancion("De musica ligera", 212, Genero.ROCK, soda);

        Cancion c2 = new Cancion("Un millon de anios luz", 273, Genero.ROCK, soda);
        album.agregarCancion(c1);
        album.agregarCancion(c2);
        biblioteca.agregarAlbum(album);
        Cancion c3 = new Cancion("La danza de los Mirlos", 198, Genero.CUMBIA, mirlos);
        Cancion c4 = new Cancion("El cantante", 540, Genero.SALSA, lavoe);
        Cancion c5 = new Cancion("Titi me pregunto", 243, Genero.REGGAETON, bad);
        Podcast p1 = new Podcast("Charlas de Codigo", 1800, Genero.OTRO, "Ana Torres", 1);
        Podcast p2 = new Podcast("Historias del Rock", 2400, Genero.ROCK, "Luis Paredes", 7);
        biblioteca.agregarContenido(c1);
        biblioteca.agregarContenido(c2);
        biblioteca.agregarContenido(c3);
        biblioteca.agregarContenido(c4);
        biblioteca.agregarContenido(c5);
        biblioteca.agregarContenido(p1);
        biblioteca.agregarContenido(p2);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Principal ventana = new Principal();
            ventana.setVisible(true);
        });
    }
}