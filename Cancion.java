public class Cancion extends Contenido {
    private Genero genero;
    private Artista artista;

    public Cancion(String titulo, int duracionSegundos, Genero genero, Artista artista) {
        super(titulo, duracionSegundos);
        this.genero = genero;
        this.artista = artista;
    }

    public Genero getGenero() {
        return genero;
    }

    public Artista getArtista() {
        return artista;
    }

    public String getTipo() {
        return "Cancion";
    }

    public String getDetalle() {
        return artista.getNombre() + " (" + genero + ")";
    }
}
