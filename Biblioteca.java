import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Contenido> contenidos;
    private ArrayList<Artista> artistas;
    private ArrayList<Album> albumes;
    private ArrayList<Usuario> usuarios;

    public Biblioteca() {
        this.contenidos = new ArrayList<>();
        this.artistas = new ArrayList<>();
        this.albumes = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void agregarContenido(Contenido contenido) {
        if (contenido == null || existeTitulo(contenido.getTitulo())) return;
        contenidos.add(contenido);
    }

    public void agregarArtista(Artista artista) {
        if (artista == null || buscarArtista(artista.getNombre()) != null) return;
        artistas.add(artista);
    }

    public void agregarAlbum(Album album) {
        if (album != null && !albumes.contains(album)) albumes.add(album);
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario == null || buscarUsuario(usuario.getCorreo()) != null) return;
        usuarios.add(usuario);
    }

    public Contenido buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) return null;
        String buscado = titulo.trim().toLowerCase();
        for (Contenido c : contenidos) if (c.getTitulo().toLowerCase().equals(buscado)) return c;
        for (Contenido c : contenidos) if (c.getTitulo().toLowerCase().contains(buscado)) return c;
        return null;
    }

    public Artista buscarArtista(String nombre) {
        if (nombre == null) return null;
        for (Artista a : artistas) if (a.getNombre().equalsIgnoreCase(nombre.trim())) return a;
        return null;
    }

    public Usuario buscarUsuario(String correo) {
        if (correo == null) return null;
        for (Usuario u : usuarios) if (u.getCorreo().equalsIgnoreCase(correo.trim())) return u;
        return null;
    }

    public ArrayList<Contenido> filtrarPorGenero(Genero genero) {
        ArrayList<Contenido> resultado = new ArrayList<>();
        for (Contenido c : contenidos) if (c.getGenero() == genero) resultado.add(c);
        return resultado;
    }

    public ArrayList<Contenido> getContenidos() { return new ArrayList<>(contenidos); }

    public int contarCanciones() {
        int total = 0;
        for (Contenido c : contenidos) if (c instanceof Cancion) total++;
        return total;
    }

    public int contarPodcasts() {
        int total = 0;
        for (Contenido c : contenidos) if (c instanceof Podcast) total++;
        return total;
    }

    public int contarReproduccionesTotales() {
        int total = 0;
        for (Contenido c : contenidos) total += c.getVecesReproducido();
        return total;
    }

    public Contenido obtenerMasReproducido() {
        Contenido mejor = null;
        for (Contenido c : contenidos) {
            if (c.getVecesReproducido() > 0 && (mejor == null || c.getVecesReproducido() > mejor.getVecesReproducido())) mejor = c;
        }
        return mejor;
    }

    public boolean existeTitulo(String titulo) {
        if (titulo == null) return false;
        for (Contenido c : contenidos) if (c.getTitulo().equalsIgnoreCase(titulo.trim())) return true;
        return false;
    }

    public boolean puedeAgregarAPlaylist(Playlist playlist, Contenido contenido) {
        if (playlist == null || contenido == null) return false;
        return contenidos.contains(contenido) && !playlist.getContenidos().contains(contenido);
    }
}