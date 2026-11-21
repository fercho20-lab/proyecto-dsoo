import java.util.ArrayList;

public class Artista {
    private String nombre;
    private ArrayList<Album> albumes;

    public Artista(String nombre) {
        this.nombre = nombre;
        albumes = new ArrayList<Album>();
    }

    public void agregarAlbum(Album album) {
        albumes.add(album);
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Album> getAlbumes() {
        return albumes;
    }

    public String toString() {
        return nombre;
    }
}
