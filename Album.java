import java.util.ArrayList;

public class Album {
    private String titulo;
    private int anio;
    private ArrayList<Cancion> canciones;

    public Album(String titulo, int anio) {
        this.titulo = titulo;
        this.anio = anio;
        canciones = new ArrayList<Cancion>();
    }

    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAnio() {
        return anio;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public String toString() {
        return titulo + " (" + anio + ")";
    }
}
