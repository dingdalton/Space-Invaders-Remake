package spaceinvaders;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainMenu {
  public static void main(String[] argv) throws Exception {
    //Read from an input stream
    InputStream is = new BufferedInputStream(new FileInputStream("mainmenu.png"));
    Image image = ImageIO.read(is);

    JFrame frame = new JFrame();
    ImageIcon imageIcon = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT, Image.SCALE_DEFAULT));
    JLabel label = new JLabel(imageIcon);
    label.addMouseListener(new MouseAdapter()  
    {  
        public void mouseClicked(MouseEvent e)  
        {  
        	frame.dispose();
        	new GameFrame().runGame();
        }  
    }); 
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.pack();
    frame.setLocationRelativeTo(null); // center the application window
    frame.setResizable(false);
    frame.setSize(Utils.CANVAS_WIDTH, Utils.CANVAS_HEIGHT);
    frame.setVisible(true);
  }
}