import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipBoardListener extends Thread implements ClipboardOwner{
	Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();  


	@Override
	public void run() {
		Transferable trans = sysClip.getContents(this);  
		TakeOwnership(trans);       

	}  

	@Override
	public void lostOwnership(Clipboard c, Transferable t) {  

		try {  
			ClipBoardListener.sleep(250);  //waiting e.g for loading huge elements like word's etc.
		} catch(Exception e) {  
			System.out.println("Exception: " + e);  
		}  
		Transferable contents = sysClip.getContents(this);  
		try {
			process_clipboard(contents, c);
		} catch (Exception ex) {
			Logger.getLogger(ClipBoardListener.class.getName()).log(Level.SEVERE, null, ex);
		}
		TakeOwnership(contents);


	}  

	void TakeOwnership(Transferable t) {  
		sysClip.setContents(t, this);  
	}  


	public void process_clipboard(Transferable t, Clipboard c) { //your implementation
		String tempText;
		Transferable trans = t;

		try {
			if (trans != null?trans.isDataFlavorSupported(DataFlavor.stringFlavor):false) {
				tempText = (String) trans.getTransferData(DataFlavor.stringFlavor);
				System.out.println(tempText);  

				makeBullet(tempText);


			}

		} catch (Exception e) {
		}
	}
	
	public void makeBullet(String str){
		boolean exists = false;
		Bullet bull = null;
		for(Bullet blt : GUI.vecBullet){
			if(blt.getContent().equals(str)){
				exists = true;
				bull = blt;
			}
		}
		
		if(!exists){
			Bullet bullet = new Bullet(str);
		}
		else{
			bull.paintItRed();
		}
	}

}