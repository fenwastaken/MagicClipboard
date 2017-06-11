import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Bullet extends JPanel {

	JLabel labContent = new JLabel();
	String content = "";
	String Time = "";
	boolean flagged = false;
	boolean numbered = false;	
	JTextArea ta;

	public Bullet(String str){
		String trunc = str;
		if(trunc.length() > 25){
			trunc = trunc.substring(0, 25) + "...";
		}

		content = str;
		labContent.setText(trunc + "  (" + content.length() + "ch)");

		Dimension dim = new Dimension(250, 35);

		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		this.add(labContent);
		labContent.addMouseListener(new appMouseListener());
		this.addMouseListener(new appMouseListener());
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setAlignmentX(LEFT_ALIGNMENT);
		initControles();
	}

	public void initControles(){
		GUI.vecBullet.add(this);
		GUI.pan.add(this);
		GUI.menuCounter.setText(GUI.vecBullet.size()+"");
		System.out.println(GUI.vecBullet.size() + " bullets");

		paintItRed();

		String hour = LocalDateTime.now().getHour() + "";
		String minute = LocalDateTime.now().getMinute() + "";
		Time = "Time: " + hour + ":" + minute;
		labContent.setToolTipText(Time);

		GUI.span.revalidate();
		GUI.span.repaint();
	}

	public void paintItRed(){
		for(Bullet blt : GUI.vecBullet){
			blt.getLabContent().setForeground(Color.black);
		}

		Bullet.this.getLabContent().setForeground(new Color(150, 0, 0));
	}

	public void display(){
		JDialog diag = new JDialog();

		diag.setModal(true);
		diag.setLocation(0, 0);
		diag.setSize((int)(GUI.width - GUI.w), GUI.h);
		diag.setTitle(Time + " " + labContent.getText());
		diag.setAlwaysOnTop(true);

		URL imgURL = getClass().getResource("/icon/wand.png");
		Image image = null;
		try {
			image = ImageIO.read(imgURL);
		} catch (IOException e) {}
		diag.setIconImage(image);
		
		ta = new JTextArea(content);
		JScrollPane span = new JScrollPane(ta);
		diag.add(span);
		ta.setEditable(false);
		ta.setFont(new Font(ta.getFont().getFontName(), ta.getFont().getStyle(), ta.getFont().getSize() + 2));
		ta.addKeyListener(new appKeyListener());
		diag.setVisible(true);
	}

	public JLabel getLabContent() {
		return labContent;
	}

	public void setLabContent(JLabel labContent) {
		this.labContent = labContent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean flagged) {
		this.flagged = flagged;
	}



	class appMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton() == MouseEvent.BUTTON1){

				Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection stringSelec = new StringSelection(Bullet.this.getContent());

				if(!Bullet.this.getContent().equals(stringSelec)){
					sysClip.setContents(stringSelec, null);
					paintItRed();
				}
			}

			if(e.getButton() == MouseEvent.BUTTON3){
				display();
			}

			if(e.getButton() == MouseEvent.BUTTON2){
				if(labContent.isOpaque()){
					labContent.setOpaque(false);
					labContent.setBackground(null);
					Bullet.this.setFlagged(false);
				}
				else{
					labContent.setBackground(new Color(0,180,0));
					labContent.setOpaque(true);
					Bullet.this.setFlagged(true);

				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class appKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println(e);
			System.out.println(e.getKeyCode());
			System.out.println(KeyEvent.VK_PLUS);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

			if(e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_PLUS){
				ta.setFont(new Font(ta.getFont().getFontName(), ta.getFont().getStyle(), ta.getFont().getSize() + 2));
				System.out.println("plus");
			}


			if(e.getKeyCode() == KeyEvent.VK_SUBTRACT || e.getKeyCode() == KeyEvent.VK_MINUS){
				ta.setFont(new Font(ta.getFont().getFontName(), ta.getFont().getStyle(), ta.getFont().getSize() - 2));
				System.out.println("moins");
			}


			if(e.getKeyCode() == KeyEvent.VK_MULTIPLY){

				String tatext = ta.getText();

				String[] lines = tatext.split("\r\n|\r|\n");

				ta.setText("");

				if(numbered){

					for(int i = 0; i < lines.length; i++){

						lines[i] = lines[i].substring(lines[i].indexOf("|    ") + 5)  + "\n";
						ta.append(lines[i]);

					}

					numbered = false;
				}
				else{


					for(int i = 0; i < lines.length; i++){

						String space = "";

						if(i < 10){
							space = "       ";
						}

						else if(i < 100){
							space = "     ";
						}

						else if(i < 1000){
							space = "   ";
						}

						else{
							space = "";
						}

						lines[i] = i + space + "|    " + lines[i] + "\n"; 
						ta.append(lines[i]);
						numbered = true;
					}
				}



			}

		}


	}



}
