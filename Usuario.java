import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private String correo;
    private ArrayList<Playlist> playlists;
    private ArrayList<Contenido> favoritos;

    public Usuario(String nombre, String correo) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre del usuario no puede estar vacio");
        if (correo == null || !correo.contains("@")) throw new IllegalArgumentException("El correo no es valido");
        this.nombre = nombre.trim();
        this.correo = correo.trim();
        this.playlists = new ArrayList<>();
        this.favoritos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public ArrayList<Playlist> getPlaylists() { return new ArrayList<>(playlists); }
    public ArrayList<Contenido> getFavoritos() { return new ArrayList<>(favoritos); }

    public Playlist crearPlaylist(String nombrePlaylist) {
        if (nombrePlaylist == null || nombrePlaylist.trim().isEmpty()) return null;
        for (Playlist p : playlists) if (p.getNombre().equalsIgnoreCase(nombrePlaylist.trim())) return null;
        Playlist nueva = new Playlist(nombrePlaylist);
        playlists.add(nueva);
        return nueva;
    }

    public boolean agregarFavorito(Contenido contenido) {
        if (contenido == null || favoritos.contains(contenido)) return false;
        favoritos.add(contenido);
        contenido.marcarFavorito();
        return true;
    }

    public boolean quitarFavorito(Contenido contenido) {
        if (contenido == null || !favoritos.remove(contenido)) return false;
        contenido.quitarFavorito();
        return true;
    }

    public void reproducir(Contenido contenido) {
        if (contenido != null) contenido.reproducir();
    }
}