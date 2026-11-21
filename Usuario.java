import java.util.ArrayList;

public class Usuario {
    private String nombre;
    private ArrayList<Playlist> playlists;
    private ArrayList<Contenido> favoritos;

    public Usuario(String nombre) {
        this.nombre = nombre;
        playlists = new ArrayList<Playlist>();
        favoritos = new ArrayList<Contenido>();
    }

    public Playlist crearPlaylist(String nombrePlaylist) {
        Playlist playlist = new Playlist(nombrePlaylist);
        playlists.add(playlist);
        return playlist;
    }

    public void reproducir(Contenido contenido) {
        contenido.reproducir();
    }

    public void marcarFavorito(Contenido contenido) {
        contenido.marcarFavorito();
        if (contenido.esFavorito()) {
            favoritos.add(contenido);
        } else {
            favoritos.remove(contenido);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public ArrayList<Contenido> getFavoritos() {
        return favoritos;
    }

    public String toString() {
        return nombre;
    }
}
