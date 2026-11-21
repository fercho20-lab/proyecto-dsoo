public abstract class Contenido implements Reproducible, Favoritable {
    private String titulo;
    private int duracionSegundos;
    private int reproducciones;
    private boolean favorito;

    public Contenido(String titulo, int duracionSegundos) {
        this.titulo = titulo;
        this.duracionSegundos = duracionSegundos;
        reproducciones = 0;
        favorito = false;
    }

    public void reproducir() {
        reproducciones = reproducciones + 1;
    }

    public void marcarFavorito() {
        favorito = !favorito;
    }

    public boolean esFavorito() {
        return favorito;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public int getReproducciones() {
        return reproducciones;
    }

    public String getDuracionTexto() {
        int minutos = duracionSegundos / 60;
        int segundos = duracionSegundos % 60;
        if (segundos < 10) {
            return minutos + ":0" + segundos;
        }
        return minutos + ":" + segundos;
    }

    public abstract String getTipo();

    public abstract String getDetalle();

    public String toString() {
        return getTipo() + " - " + titulo;
    }
}
