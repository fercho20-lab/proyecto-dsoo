public class DatosDemo {
    public static void cargar(Biblioteca biblioteca) {
        Artista soda = new Artista("Soda Stereo");
        Album dynamo = new Album("Dynamo", 1992);
        Cancion c1 = new Cancion("En la ciudad de la furia", 340, Genero.ROCK, soda);
        Cancion c2 = new Cancion("De musica ligera", 213, Genero.ROCK, soda);
        dynamo.agregarCancion(c1);
        dynamo.agregarCancion(c2);
        soda.agregarAlbum(dynamo);

        Artista gilda = new Artista("Gilda");
        Album mix = new Album("Corazon Valiente", 1996);
        Cancion c3 = new Cancion("No me arrepiento de este amor", 252, Genero.CUMBIA, gilda);
        mix.agregarCancion(c3);
        gilda.agregarAlbum(mix);

        Podcast p1 = new Podcast("Historias de Arequipa", 1800, "Ana Torres", 1);

        biblioteca.agregarArtista(soda);
        biblioteca.agregarArtista(gilda);
        biblioteca.agregarContenido(c1);
        biblioteca.agregarContenido(c2);
        biblioteca.agregarContenido(c3);
        biblioteca.agregarContenido(p1);

        Usuario fernando = new Usuario("Fernando");
        Usuario invitado = new Usuario("Invitado");
        biblioteca.agregarUsuario(fernando);
        biblioteca.agregarUsuario(invitado);
        Playlist favoritas = fernando.crearPlaylist("Para estudiar");
        favoritas.agregar(c2);
        favoritas.agregar(c3);
    }
}
