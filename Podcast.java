public class Podcast extends Contenido {
    private String anfitrion;
    private int numeroEpisodio;

    public Podcast(String titulo, int duracionSegundos, Genero genero, String anfitrion, int numeroEpisodio) {
        super(titulo, duracionSegundos, genero);
        if (anfitrion == null || anfitrion.trim().isEmpty()) throw new IllegalArgumentException("El podcast debe tener anfitrion");
        if (numeroEpisodio <= 0) throw new IllegalArgumentException("El numero de episodio debe ser mayor a 0");
        this.anfitrion = anfitrion.trim();
        this.numeroEpisodio = numeroEpisodio;
    }

    public String getAnfitrion() { return anfitrion; }
    public int getNumeroEpisodio() { return numeroEpisodio; }

    public String obtenerDetalle() {
        String detalle = "[Podcast] " + titulo + " - Ep. " + numeroEpisodio + " con " + anfitrion + " | " + genero + " | " + formatearDuracion(duracionSegundos);
        if (favorito) detalle += " <3";
        return detalle;
    }
}