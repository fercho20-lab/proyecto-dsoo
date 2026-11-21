import java.awt.Component;
import javax.swing.JOptionPane;

public class Aviso {
    public static void mensaje(Component padre, String texto) {
        JOptionPane.showMessageDialog(padre, texto);
    }
}
