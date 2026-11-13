public abstract class Contenido implements Reproducible, Favoritable {
    protected String titulo;
    protected int duracionSegundos;
    protected Genero genero;
    protected boolean favorito;
    protected int vecesReproducido;

    public Contenido(String titulo, int duracionSegundos, Genero genero) {
        if (titulo == null || titulo.trim().isEmpty()) throw new IllegalArgumentException("El titulo no puede estar vacio");
        if (duracionSegundos <= 0) throw new IllegalArgumentException("La duracion debe ser mayor a 0 segundos");
        this.titulo = titulo.trim();
        this.duracionSegundos = duracionSegundos;
        this.genero = (genero == null) ? Genero.OTRO : genero;
        this.favorito = false;
        this.vecesReproducido = 0;
    }

    public String getTitulo() { return titulo; }
    public int getDuracionSegundos() { return duracionSegundos; }
    public Genero getGenero() { return genero; }
    public int getVecesReproducido() { return vecesReproducido; }
    public abstract String obtenerDetalle();

    public static String formatearDuracion(int segundos) {
        int h = segundos / 3600;
        int m = (segundos % 3600) / 60;
        int s = segundos % 60;
        if (h > 0) return String.format("%d:%02d:%02d", h, m, s);
        return String.format("%d:%02d", m, s);
    }

    public void reproducir() {
        vecesReproducido++;
        System.out.println(">> Reproduciendo: " + obtenerDetalle());
    }

    public void pausar() { System.out.println("|| Pausado: " + titulo); }
    public void marcarFavorito() { favorito = true; }
    public void quitarFavorito() { favorito = false; }
    public boolean esFavorito() { return favorito; }
    public String toString() { return obtenerDetalle(); }
}