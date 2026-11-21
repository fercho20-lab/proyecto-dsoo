public class Podcast extends Contenido {
    private String anfitrion;
    private int episodio;

    public Podcast(String titulo, int duracionSegundos, String anfitrion, int episodio) {
        super(titulo, duracionSegundos);
        this.anfitrion = anfitrion;
        this.episodio = episodio;
    }

    public String getAnfitrion() {
        return anfitrion;
    }

    public int getEpisodio() {
        return episodio;
    }

    public String getTipo() {
        return "Podcast";
    }

    public String getDetalle() {
        return anfitrion + " (episodio " + episodio + ")";
    }
}
