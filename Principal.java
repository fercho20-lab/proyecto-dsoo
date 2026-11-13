import java.util.*;

public class Principal {
    private static Usuario usuarioActual;

    public static void mostrarMenu() {
        System.out.println();
        System.out.println("MI SPOTIFY");
        if (usuarioActual != null) System.out.println("Usuario: " + usuarioActual.getNombre());
        System.out.println(" 1. Ver catalogo");
        System.out.println(" 2. Buscar por titulo");
        System.out.println(" 3. Filtrar por genero");
        System.out.println(" 4. Ver discografia de un artista");
        System.out.println(" 5. Reproducir contenido");
        System.out.println(" 6. Crear playlist");
        System.out.println(" 7. Agregar contenido a una playlist");
        System.out.println(" 8. Quitar contenido de una playlist");
        System.out.println(" 9. Ver mis playlists");
        System.out.println("10. Marcar / quitar favorito");
        System.out.println("11. Ver mis favoritos");
        System.out.println("12. Estadisticas de la biblioteca");
        System.out.println(" 0. Salir");
    }

    public static void ejecutarOpcion(int opcion, Biblioteca biblioteca, Scanner sc) {
        switch (opcion) {
            case 1:
                listar("CATALOGO", biblioteca.getContenidos());
                break;
            case 2: {
                String texto = leerTexto(sc, "Titulo a buscar: ");
                Contenido c = biblioteca.buscarPorTitulo(texto);
                if (c == null) System.out.println("No se encontro contenido con ese titulo.");
                else System.out.println("Encontrado: " + c.obtenerDetalle());
                break;
            }
            case 3: {
                Genero g = elegirGenero(sc);
                if (g != null) listar("GENERO " + g, biblioteca.filtrarPorGenero(g));
                break;
            }
            case 4: {
                String nombre = leerTexto(sc, "Nombre del artista: ");
                Artista a = biblioteca.buscarArtista(nombre);
                if (a == null) System.out.println("Artista no encontrado.");
                else System.out.print(a.mostrarDiscografia());
                break;
            }
            case 5: {
                Contenido c = elegirContenido(sc, biblioteca.getContenidos(), "Elige que reproducir");
                if (c != null) {
                    usuarioActual.reproducir(c);
                    System.out.print("Presiona ENTER para pausar...");
                    sc.nextLine();
                    c.pausar();
                }
                break;
            }
            case 6: {
                String nombre = leerTexto(sc, "Nombre de la nueva playlist: ");
                Playlist p = usuarioActual.crearPlaylist(nombre);
                if (p == null) System.out.println("No se pudo crear: el nombre esta vacio o ya existe.");
                else System.out.println("Playlist '" + p.getNombre() + "' creada.");
                break;
            }
            case 7: {
                Playlist p = elegirPlaylist(sc);
                if (p == null) break;
                Contenido c = elegirContenido(sc, biblioteca.getContenidos(), "Elige que agregar");
                if (c == null) break;
                if (biblioteca.puedeAgregarAPlaylist(p, c) && p.agregarContenido(c))
                    System.out.println("Agregado a '" + p.getNombre() + "'.");
                else System.out.println("No se pudo agregar (ya estaba en la playlist).");
                break;
            }
            case 8: {
                Playlist p = elegirPlaylist(sc);
                if (p == null) break;
                Contenido c = elegirContenido(sc, p.getContenidos(), "Elige que quitar");
                if (c != null) {
                    p.eliminarContenido(c);
                    System.out.println("Eliminado de '" + p.getNombre() + "'.");
                }
                break;
            }
            case 9: {
                ArrayList<Playlist> lista = usuarioActual.getPlaylists();
                if (lista.isEmpty()) System.out.println("Aun no tienes playlists.");
                for (Playlist p : lista) System.out.print(p.mostrar());
                break;
            }
            case 10: {
                Contenido c = elegirContenido(sc, biblioteca.getContenidos(), "Elige el contenido");
                if (c == null) break;
                if (c.esFavorito()) {
                    usuarioActual.quitarFavorito(c);
                    System.out.println("Quitado de favoritos: " + c.getTitulo());
                } else {
                    usuarioActual.agregarFavorito(c);
                    System.out.println("Agregado a favoritos: " + c.getTitulo());
                }
                break;
            }
            case 11:
                listar("MIS FAVORITOS", usuarioActual.getFavoritos());
                break;
            case 12: {
                System.out.println("ESTADISTICAS");
                System.out.println("Canciones: " + biblioteca.contarCanciones());
                System.out.println("Podcasts: " + biblioteca.contarPodcasts());
                System.out.println("Reproducciones totales: " + biblioteca.contarReproduccionesTotales());
                Contenido top = biblioteca.obtenerMasReproducido();
                if (top == null) System.out.println("Mas reproducido: (aun no reproduces nada)");
                else System.out.println("Mas reproducido: " + top.getTitulo() + " (" + top.getVecesReproducido() + " veces)");
                break;
            }
            default:
                System.out.println("Opcion no valida.");
        }
    }

    public static void cargarDatosIniciales(Biblioteca biblioteca) {
        Artista soda = new Artista("Soda Stereo");
        Artista mirlos = new Artista("Los Mirlos");
        Artista lavoe = new Artista("Hector Lavoe");
        Artista bad = new Artista("Bad Bunny");
        biblioteca.agregarArtista(soda);
        biblioteca.agregarArtista(mirlos);
        biblioteca.agregarArtista(lavoe);
        biblioteca.agregarArtista(bad);

        Album cancionAnimal = new Album("Cancion Animal", 1990, soda);
        Cancion c1 = new Cancion("De musica ligera", 212, Genero.ROCK, soda);
        Cancion c2 = new Cancion("Un millon de anios luz", 273, Genero.ROCK, soda);
        cancionAnimal.agregarCancion(c1);
        cancionAnimal.agregarCancion(c2);
        biblioteca.agregarAlbum(cancionAnimal);

        Cancion c3 = new Cancion("La danza de los Mirlos", 198, Genero.CUMBIA, mirlos);
        Cancion c4 = new Cancion("El cantante", 540, Genero.SALSA, lavoe);
        Cancion c5 = new Cancion("Titi me pregunto", 243, Genero.REGGAETON, bad);
        Podcast p1 = new Podcast("Charlas de Codigo", 1800, Genero.OTRO, "Ana Torres", 1);
        Podcast p2 = new Podcast("Historias del Rock", 2400, Genero.ROCK, "Luis Paredes", 7);

        biblioteca.agregarContenido(c1);
        biblioteca.agregarContenido(c2);
        biblioteca.agregarContenido(c3);
        biblioteca.agregarContenido(c4);
        biblioteca.agregarContenido(c5);
        biblioteca.agregarContenido(p1);
        biblioteca.agregarContenido(p2);
    }

    private static void listar(String titulo, ArrayList<Contenido> lista) {
        System.out.println("--- " + titulo + " ---");
        if (lista.isEmpty()) System.out.println("(vacio)");
        for (int i = 0; i < lista.size(); i++)
            System.out.println((i + 1) + ". " + lista.get(i).obtenerDetalle());
    }

    private static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un numero valido.");
            }
        }
    }

    private static Contenido elegirContenido(Scanner sc, ArrayList<Contenido> lista, String mensaje) {
        if (lista.isEmpty()) {
            System.out.println("No hay contenido disponible.");
            return null;
        }
        listar("OPCIONES", lista);
        int n = leerEntero(sc, mensaje + " (0 = cancelar): ");
        if (n < 1 || n > lista.size()) {
            if (n != 0) System.out.println("Numero fuera de rango.");
            return null;
        }
        return lista.get(n - 1);
    }

    private static Playlist elegirPlaylist(Scanner sc) {
        ArrayList<Playlist> lista = usuarioActual.getPlaylists();
        if (lista.isEmpty()) {
            System.out.println("Aun no tienes playlists. Crea una con la opcion 6.");
            return null;
        }
        for (int i = 0; i < lista.size(); i++) System.out.println((i + 1) + ". " + lista.get(i).getNombre());
        int n = leerEntero(sc, "Elige playlist (0 = cancelar): ");
        if (n < 1 || n > lista.size()) {
            if (n != 0) System.out.println("Numero fuera de rango.");
            return null;
        }
        return lista.get(n - 1);
    }

    private static Genero elegirGenero(Scanner sc) {
        Genero[] generos = Genero.values();
        for (int i = 0; i < generos.length; i++) System.out.println((i + 1) + ". " + generos[i]);
        int n = leerEntero(sc, "Elige genero (0 = cancelar): ");
        if (n < 1 || n > generos.length) {
            if (n != 0) System.out.println("Numero fuera de rango.");
            return null;
        }
        return generos[n - 1];
    }

    private static Usuario iniciarSesion(Biblioteca biblioteca, Scanner sc) {
        while (true) {
            String nombre = leerTexto(sc, "Tu nombre: ");
            String correo = leerTexto(sc, "Tu correo: ");
            Usuario existente = biblioteca.buscarUsuario(correo);
            if (existente != null) return existente;
            try {
                Usuario nuevo = new Usuario(nombre, correo);
                biblioteca.registrarUsuario(nuevo);
                return nuevo;
            } catch (IllegalArgumentException e) {
                System.out.println("Datos invalidos: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        cargarDatosIniciales(biblioteca);
        System.out.println("Bienvenido a Mi Spotify");
        try {
            usuarioActual = iniciarSesion(biblioteca, sc);
            int opcion;
            do {
                mostrarMenu();
                opcion = leerEntero(sc, "Elige una opcion: ");
                if (opcion != 0) ejecutarOpcion(opcion, biblioteca, sc);
            } while (opcion != 0);
        } catch (NoSuchElementException e) {
            // La entrada termino (Ctrl+D / Ctrl+Z): salimos sin error.
        }
        System.out.println("Hasta pronto!");
        sc.close();
    }
}