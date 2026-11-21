import java.util.ArrayList;

public class Biblioteca {
    private ArrayList<Contenido> contenidos;
    private ArrayList<Artista> artistas;
    private ArrayList<Usuario> usuarios;
    private Usuario usuarioActual;

    public Biblioteca() {
        contenidos = new ArrayList<Contenido>();
        artistas = new ArrayList<Artista>();
        usuarios = new ArrayList<Usuario>();
        usuarioActual = null;
    }

    public void agregarContenido(Contenido contenido) {
        contenidos.add(contenido);
    }

    public void agregarArtista(Artista artista) {
        artistas.add(artista);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        if (usuarioActual == null) {
            usuarioActual = usuario;
        }
    }

    public ArrayList<Contenido> getContenidos() {
        return contenidos;
    }

    public ArrayList<Artista> getArtistas() {
        return artistas;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public ArrayList<Contenido> buscarPorTitulo(String texto) {
        ArrayList<Contenido> resultado = new ArrayList<Contenido>();
        String buscado = texto.toLowerCase();
        int i = 0;
        while (i < contenidos.size()) {
            if (contenidos.get(i).getTitulo().toLowerCase().contains(buscado)) {
                resultado.add(contenidos.get(i));
            }
            i = i + 1;
        }
        return resultado;
    }

    public ArrayList<Cancion> filtrarPorGenero(Genero genero) {
        ArrayList<Cancion> resultado = new ArrayList<Cancion>();
        int i = 0;
        while (i < contenidos.size()) {
            if (contenidos.get(i) instanceof Cancion) {
                Cancion cancion = (Cancion) contenidos.get(i);
                if (cancion.getGenero() == genero) {
                    resultado.add(cancion);
                }
            }
            i = i + 1;
        }
        return resultado;
    }

    public int contarCanciones() {
        int total = 0;
        int i = 0;
        while (i < contenidos.size()) {
            if (contenidos.get(i) instanceof Cancion) {
                total = total + 1;
            }
            i = i + 1;
        }
        return total;
    }

    public int contarPodcasts() {
        int total = 0;
        int i = 0;
        while (i < contenidos.size()) {
            if (contenidos.get(i) instanceof Podcast) {
                total = total + 1;
            }
            i = i + 1;
        }
        return total;
    }

    public int contarReproduccionesTotales() {
        int total = 0;
        int i = 0;
        while (i < contenidos.size()) {
            total = total + contenidos.get(i).getReproducciones();
            i = i + 1;
        }
        return total;
    }

    public Contenido obtenerMasReproducido() {
        Contenido mejor = null;
        int i = 0;
        while (i < contenidos.size()) {
            Contenido c = contenidos.get(i);
            if (c.getReproducciones() > 0 && (mejor == null || c.getReproducciones() > mejor.getReproducciones())) {
                mejor = c;
            }
            i = i + 1;
        }
        return mejor;
    }

    public boolean existeTitulo(String titulo) {
        int i = 0;
        while (i < contenidos.size()) {
            if (contenidos.get(i).getTitulo().equalsIgnoreCase(titulo.trim())) {
                return true;
            }
            i = i + 1;
        }
        return false;
    }

    public boolean existeArtista(String nombre) {
        int i = 0;
        while (i < artistas.size()) {
            if (artistas.get(i).getNombre().equalsIgnoreCase(nombre.trim())) {
                return true;
            }
            i = i + 1;
        }
        return false;
    }

    public boolean puedeAgregarAPlaylist(Playlist playlist, Contenido contenido) {
        return playlist != null && contenido != null && !playlist.getContenidos().contains(contenido);
    }

    // Elimina el contenido de la biblioteca y de todo lugar donde estuviera (playlists, favoritos, albumes)
    public void eliminarContenido(Contenido contenido) {
        contenidos.remove(contenido);
        int i = 0;
        while (i < usuarios.size()) {
            Usuario u = usuarios.get(i);
            u.getFavoritos().remove(contenido);
            int j = 0;
            while (j < u.getPlaylists().size()) {
                u.getPlaylists().get(j).quitar(contenido);
                j = j + 1;
            }
            i = i + 1;
        }
        i = 0;
        while (i < artistas.size()) {
            int j = 0;
            while (j < artistas.get(i).getAlbumes().size()) {
                artistas.get(i).getAlbumes().get(j).getCanciones().remove(contenido);
                j = j + 1;
            }
            i = i + 1;
        }
    }
}
