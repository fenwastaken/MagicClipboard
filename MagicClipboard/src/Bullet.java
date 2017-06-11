import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Bullet extends JPanel {

	JLabel labContent = new JLabel();
	String content = "";
	String Time = "";
	
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
		diag.setSize(800, 600);
		diag.setModal(true);
		diag.setLocationRelativeTo(this);
		diag.setTitle(Time + " " + labContent.getText());
		diag.setAlwaysOnTop(true);
		
		
		JTextArea ta = new JTextArea(content);
		JScrollPane span = new JScrollPane(ta);
		diag.add(span);
		ta.setEditable(false);
		
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
					Toolkit.getDefaultToolkit().beep();
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
				}
				else{
					labContent.setBackground(new Color(0,180,0));
					labContent.setOpaque(true);
					
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
	
}
