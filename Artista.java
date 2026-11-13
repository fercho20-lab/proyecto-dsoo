import java.util.ArrayList;

public class Artista {
    private String nombre;
    private ArrayList<Cancion> canciones;
    private ArrayList<Album> albumes;

    public Artista(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre del artista no puede estar vacio");
        this.nombre = nombre.trim();
        this.canciones = new ArrayList<>();
        this.albumes = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public ArrayList<Cancion> getCanciones() { return new ArrayList<>(canciones); }
    public ArrayList<Album> getAlbumes() { return new ArrayList<>(albumes); }

    public void agregarCancion(Cancion cancion) {
        if (cancion != null && !canciones.contains(cancion)) canciones.add(cancion);
    }

    public void agregarAlbum(Album album) {
        if (album != null && !albumes.contains(album)) albumes.add(album);
    }

    public String mostrarDiscografia() {
        StringBuilder sb = new StringBuilder();
        sb.append("Discografia de ").append(nombre).append("\n");
        if (canciones.isEmpty() && albumes.isEmpty()) {
            sb.append("  (sin contenido)\n");
            return sb.toString();
        }
        for (Album a : albumes) {
            sb.append("  Album: ").append(a.getTitulo()).append(" (").append(a.getAnio()).append(") - ").append(Contenido.formatearDuracion(Math.max(a.getDuracionTotal(), 1))).append("\n");
            for (Cancion c : a.getCanciones()) {
                sb.append("    - ").append(c.getTitulo()).append(" [").append(Contenido.formatearDuracion(c.getDuracionSegundos())).append("]\n");
            }
        }
        boolean hayDeSencillos = false;
        for (Cancion c : canciones) {
            if (c.getAlbum() == null) {
                if (!hayDeSencillos) {
                    sb.append("  Sencillos:\n");
                    hayDeSencillos = true;
                }
                sb.append("    - ").append(c.getTitulo()).append(" [").append(Contenido.formatearDuracion(c.getDuracionSegundos())).append("]\n");
            }
        }
        return sb.toString();
    }
}