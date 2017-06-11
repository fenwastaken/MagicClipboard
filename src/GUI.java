import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class GUI extends JFrame{

	//should be good
	
	public static String version = "1.4";

	public static int w = 0;
	public static int h = 0;
	public static double width;
	public static double height;
	public static int arbitraryWidth = 287;

	public static boolean alwaysOnTop = true;
	public static JPanel  pan = new JPanel();
	public static JScrollPane span = new JScrollPane(pan);
	public JPanel zoneClient = (JPanel) this.getContentPane();
	public static Vector<Bullet> vecBullet = new Vector<Bullet>();


	JMenuBar bar = new JMenuBar();

	JMenu menuOptions = new JMenu("Options");
	JMenu menuFolder = new JMenu("Folder");
	JMenu menuWindow = new JMenu("Window");
	JMenu menuAbout = new JMenu("About");
	public static JMenu menuCounter = new JMenu("0");

	JMenuItem mClear = new JMenuItem("Clear");
	JMenuItem mClearNf = new JMenuItem("Clear not flagged");
	JMenuItem mSave = new JMenuItem("Save");
	JMenuItem mAbout = new JMenuItem("About");
	JMenuItem mNtxt = new JMenuItem("About .ntxt");
	JMenuItem mOpenFolder = new JMenuItem("Open folder");
	JMenuItem mResizeRight = new JMenuItem("Resize to the right");
	JMenuItem mResizeSmall = new JMenuItem("Resize to small");
	JCheckBoxMenuItem mAlways = new JCheckBoxMenuItem("Always on Top");


	public GUI(){

		URL imgURL = getClass().getResource("/icon/wand.png");
		Image image = null;
		try {
			image = ImageIO.read(imgURL);
		} catch (IOException e) {}
		this.setIconImage(image);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();

		w = arbitraryWidth;
		h = (int) height;



		this.setTitle("Magic Clipboard");
		this.setSize(w, h);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		//this.setLocationRelativeTo(null);
		this.setLocation((int)(width - w), 0);
		this.setAlwaysOnTop(alwaysOnTop);
		initControles();
	}

	public void initControles(){

		menuOptions.setMnemonic('o');
		menuFolder.setMnemonic('f');
		menuAbout.setMnemonic('a');
		menuWindow.setMnemonic('w');

		mAlways.setMnemonic('a');
		mAlways.setSelected(true);
		mClear.setMnemonic('c');
		mClearNf.setMnemonic('n');
		mSave.setMnemonic('s');
		mResizeSmall.setMnemonic('s');
		mResizeRight.setMnemonic('r');

		mOpenFolder.setMnemonic('o');

		mAbout.setMnemonic('a');
		mNtxt.setMnemonic('n');



		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		menuOptions.add(mClearNf);
		menuOptions.add(mClear);
		menuOptions.add(mSave);

		menuAbout.add(mAbout);
		menuAbout.add(mNtxt);

		menuFolder.add(mOpenFolder);

		menuWindow.add(mAlways);
		menuWindow.add(mResizeRight);
		menuWindow.add(mResizeSmall);

		bar.add(menuOptions);
		bar.add(menuFolder);
		bar.add(menuWindow);
		bar.add(menuAbout);


		bar.add(menuCounter);
		menuCounter.setEnabled(false);

		mSave.addActionListener(new appActionListener());
		mClear.addActionListener(new appActionListener());
		mClearNf.addActionListener(new appActionListener());
		mAbout.addActionListener(new appActionListener());
		mOpenFolder.addActionListener(new appActionListener());
		mNtxt.addActionListener(new appActionListener());
		menuOptions.addMenuListener(new appMenuListener());
		mAlways.addActionListener(new appActionListener());
		mResizeSmall.addActionListener(new appActionListener());
		mResizeRight.addActionListener(new appActionListener());

		this.setJMenuBar(bar);
		zoneClient.add(span);

		span.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
			public void adjustmentValueChanged(AdjustmentEvent e) {  
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
			}
		});

		ClipBoardListener cl = new ClipBoardListener();
		cl.run();
	}

	class appMenuListener implements MenuListener{

		@Override
		public void menuSelected(MenuEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == menuOptions){
				if(vecBullet.size() < 1){
					mSave.setEnabled(false);
					mClear.setEnabled(false);
					mClearNf.setEnabled(false);
				}
				else{
					mSave.setEnabled(true);
					mClear.setEnabled(true);
					mClearNf.setEnabled(true);
				}
			}
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void menuCanceled(MenuEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class appActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if(e.getSource() == mAbout){
				popupMaker(
						"Magic Clipboard Version " + version  
						+ "\n\n-Copy to cliboard with LMB. [red is currently copied]"
						+ "\n-View with RMB."
						+ "\n-Flag with wheel click."
						+ "\n-Hover to see when the copy was made."
						+ "\n\n Made by JL");
			}

			if(e.getSource() == mClear){

				if(jdiagMaker("Do you really want to clear everything?")){
					clearBullets();
				};
			}

			if(e.getSource() == mClearNf){

				if(jdiagMaker("Do you really want to clear unflagged bullets?")){
					clearBulletsNf();
				};
			}

			if(e.getSource() == mSave){
				if(jdiagMaker("Do you really want to save everything?")){
					saveBullets();
				};
			}

			if(e.getSource() == mOpenFolder){
				openFolder();
			}			

			if(e.getSource() == mNtxt){
				popupMaker(".ntxt is just a regular txt,\nassociate it with Notepad++ so your text structure is displayed correctly.");
			}			

			if(e.getSource() == mAlways){
				mAlways.setSelected(!alwaysOnTop);
				mAlways.setSelected(!alwaysOnTop);
				alwaysOnTop = !alwaysOnTop;
				GUI.this.setAlwaysOnTop(alwaysOnTop);
			}			

			if(e.getSource() == mResizeSmall){
				GUI.this.setSize(arbitraryWidth, 300);
				GUI.this.setLocationRelativeTo(null);
			}			

			if(e.getSource() == mResizeRight){
				GUI.this.setSize(w, h);
				GUI.this.setLocation((int)(width - w), 0);
			}			
		}

	}

	public void openFolder(){
		File f = new File("\\ClipBoardFiles");
		if(f.exists()){
			try {
				Desktop.getDesktop().open(new File("\\ClipBoardFiles\\"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			popupMaker("Folder doesn't exist");
		}
	}

	public void popupMaker(String text){
		JOptionPane.showMessageDialog(zoneClient, text);
	}

	public boolean jdiagMaker(String text){
		final JOptionPane optionPane = new JOptionPane("",
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_OPTION);

		int result = JOptionPane.showConfirmDialog(this, text);

		boolean ok = false;
		if(result == 0){
			ok = true;
		}

		return ok;
	}

	public void clearBullets(){
		System.out.println(vecBullet.size() + " bullets cleared.");

		vecBullet.removeAllElements();
		pan.removeAll();
		span.revalidate();
		span.repaint();
		menuCounter.setText(GUI.vecBullet.size()+"");
	}

	public void clearBulletsNf(){
		System.out.println(vecBullet.size() + " bullets cleared.");
		Vector<Bullet> vb = new Vector<>();

		for(Bullet blt : vecBullet){
			if(blt.isFlagged()){
				vb.add(blt);
			}
			else{
				pan.remove(blt);
			}
		}
		vecBullet.removeAllElements();
		vecBullet = vb;
		span.revalidate();
		span.repaint();
		menuCounter.setText(GUI.vecBullet.size()+"");
	}

	public void saveBullets(){
		Filemaker fm = new Filemaker();
		fm.MakeFiles(this);
	}

}
