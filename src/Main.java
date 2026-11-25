import vista.VentanaPrincipal;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Font f = new Font("SansSerif", Font.PLAIN, 16);
        for (String k : new String[]{"Button.font", "Label.font", "TextField.font", "ComboBox.font", "Table.font",
                "TabbedPane.font", "Spinner.font", "OptionPane.messageFont", "OptionPane.buttonFont"})
            UIManager.put(k, f);
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
