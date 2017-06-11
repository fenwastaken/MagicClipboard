import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Filemaker {

	String defaultFolder = "ClipBoardFiles";
	String path = "\\" + defaultFolder;

	public void MakeFiles(GUI gui){

		if(GUI.vecBullet.size() > 0){
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
			Date date = new Date();
			String strDate = format.format(date);

			if (!new File(path).exists())
			{
				File dir = new File(path);
				dir.mkdir();
				System.out.println("Made " + path);
			}

			if (!new File(path + "\\" + strDate).exists())
			{
				File dir2 = new File(path + "\\" + strDate);
				dir2.mkdir();
				System.out.println("Made " + path + "\\" + strDate);
			}

			for(Bullet blt : GUI.vecBullet){

				String title = blt.getLabContent().getText();
				title = title.replace("\\", "");
				title = title.replace("/", "");
				title = title.replace(".", "");
				title = title.replace(";", "");
				title = title.replace(",", "");
				title = title.replace(" ", "");
				title = title.replace("\"", "");
				title = title.replace("\'", "");
				title = title.replace("\n", "");
				title = title.replace("\t", "");
				title = title.replace("=", "");
				title = title + ".ntxt";

				if(blt.isFlagged()){
					title = "[Flagged]-" + title;
				}
				
				String filePath = path + "\\" + strDate + "\\" + title;


				if(!new File(filePath).exists()){
					File f = new File(filePath);
					try {


						BufferedReader reader = new BufferedReader(new StringReader(blt.getContent()));
						PrintWriter writer = new PrintWriter(new FileWriter(f, true));

						reader.lines().forEach(line -> writer.append(line + "\n"));
						
						writer.close();

						System.out.println("Made " + title + "\n CONTENT: \n" + blt.getContent());
					} catch (IOException e) {
						gui.popupMaker("An error occured \n" + e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
