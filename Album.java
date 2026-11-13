import java.util.ArrayList;

public class Album {
    private String titulo;
    private int anio;
    private Artista artista;
    private ArrayList<Cancion> canciones;

    public Album(String titulo, int anio, Artista artista) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo del album no puede estar vacio");
        }
        if (artista == null) {
            throw new IllegalArgumentException("El album debe tener un artista");
        }
        this.titulo = titulo.trim();
        this.anio = anio;
        this.artista = artista;
        this.canciones = new ArrayList<>();
        artista.agregarAlbum(this);
    }

    public String getTitulo() { return titulo; }
    public int getAnio() { return anio; }
    public Artista getArtista() { return artista; }

    public ArrayList<Cancion> getCanciones() {
        return new ArrayList<>(canciones);
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion == null || canciones.contains(cancion)) return;
        if (cancion.getArtista() != artista) {
            throw new IllegalArgumentException("La cancion '" + cancion.getTitulo() + "' no pertenece al artista del album");
        }
        canciones.add(cancion);
        cancion.setAlbum(this);
    }

    public int getDuracionTotal() {
        int total = 0;
        for (Cancion c : canciones) total += c.getDuracionSegundos();
        return total;
    }
}