import javax.swing.*;
import java.awt.event.*;
public class JFrameL extends JFrame
{
    /** Creates a new instance of JFrameL */
    public JFrameL(String title) {
        super(title);
        FrameListener listener = new FrameListener();
        addWindowListener(listener);
    }
    private class FrameListener extends WindowAdapter
    {

        public void windowClosing(WindowEvent e) {
            int confirm;
            if (!Main.saved)
            {
                String  message = "The data in the application is not saved.\n"+
                        "Would you like to save it before exiting the application?";
                confirm = JOptionPane.showConfirmDialog (null, message);
                if (confirm == JOptionPane.YES_OPTION)
                    Main.chooseFile(2);
            }
            JFrame frame = (JFrame) e.getSource();
            frame.setVisible(false);
            System.exit(0);
        }
    }
}