import java.awt.Color;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class GUI extends JFrame{

	public static JPanel  pan = new JPanel();
	public static JScrollPane span = new JScrollPane(pan);
	public JPanel zoneClient = (JPanel) this.getContentPane();
	public static Vector<Bullet> vecBullet = new Vector<Bullet>();

	public GUI(){
		this.setTitle("Magic Clipboard");
		this.setSize(274, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		initControles();
	}

	public void initControles(){

		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		zoneClient.add(span);
		
		ClipBoardListener cl = new ClipBoardListener();
		cl.run();
	}

}
