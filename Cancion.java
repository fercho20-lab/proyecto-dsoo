public class Cancion extends Contenido {
    private Artista artista;
    private Album album;

    public Cancion(String titulo, int duracionSegundos, Genero genero, Artista artista) {
        super(titulo, duracionSegundos, genero);
        if (artista == null) throw new IllegalArgumentException("La cancion debe tener un artista");
        this.artista = artista;
        this.album = null;
        artista.agregarCancion(this);
    }

    public Artista getArtista() { return artista; }
    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }

    public String obtenerDetalle() {
        String detalle = "[Cancion] " + titulo + " - " + artista.getNombre();
        if (album != null) detalle += " (Album: " + album.getTitulo() + ")";
        detalle += " | " + genero + " | " + formatearDuracion(duracionSegundos);
        if (favorito) detalle += " <3";
        return detalle;
    }
}