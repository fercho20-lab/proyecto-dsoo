import javax.swing.UIManager;

public class Principal {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // si falla se usa el aspecto por defecto
        }
        Biblioteca biblioteca = new Biblioteca();
        DatosDemo.cargar(biblioteca);
        new VentanaPrincipal(biblioteca);
    }
}
