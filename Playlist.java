import java.util.ArrayList;

public class Playlist {
    private String nombre;
    private ArrayList<Contenido> contenidos;

    public Playlist(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre de la playlist no puede estar vacio");
        this.nombre = nombre.trim();
        this.contenidos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public ArrayList<Contenido> getContenidos() { return new ArrayList<>(contenidos); }

    public boolean agregarContenido(Contenido contenido) {
        if (contenido == null || contenidos.contains(contenido)) return false;
        return contenidos.add(contenido);
    }

    public boolean eliminarContenido(Contenido contenido) { return contenidos.remove(contenido); }
    public int getCantidad() { return contenidos.size(); }

    public int getDuracionTotal() {
        int total = 0;
        for (Contenido c : contenidos) total += c.getDuracionSegundos();
        return total;
    }

    public String mostrar() {
        StringBuilder sb = new StringBuilder();
        sb.append("Playlist: ").append(nombre).append(" (").append(getCantidad()).append(" elementos");
        if (getCantidad() > 0) sb.append(", ").append(Contenido.formatearDuracion(getDuracionTotal()));
        sb.append(")\n");
        if (contenidos.isEmpty()) sb.append("  (vacia)\n");
        for (int i = 0; i < contenidos.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(contenidos.get(i).obtenerDetalle()).append("\n");
        }
        return sb.toString();
    }
}