import java.util.ArrayList;

public class Playlist {
    private String nombre;
    private ArrayList<Contenido> contenidos;

    public Playlist(String nombre) {
        this.nombre = nombre;
        contenidos = new ArrayList<Contenido>();
    }

    public boolean agregar(Contenido contenido) {
        if (contenidos.contains(contenido)) {
            return false;
        }
        contenidos.add(contenido);
        return true;
    }

    public boolean quitar(Contenido contenido) {
        return contenidos.remove(contenido);
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Contenido> getContenidos() {
        return contenidos;
    }

    public int getDuracionTotal() {
        int total = 0;
        int i = 0;
        while (i < contenidos.size()) {
            total = total + contenidos.get(i).getDuracionSegundos();
            i = i + 1;
        }
        return total;
    }

    public String toString() {
        return nombre + " (" + contenidos.size() + ")";
    }
}
