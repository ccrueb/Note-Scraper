import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Notes {
	private static Scanner in = null;

	private static List<String> notesDB = new ArrayList<String>();

	public Notes(String date) {

	}




	public boolean saveNotes() {

		return false;
	}

	public static boolean scapeForNotes() {
		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			Document doc = Jsoup.connect("http://pages.cs.wisc.edu/~cs367-1/resources/outlines/").get();
			Elements links = doc.select("a[href$=.pdf]");
			if (links.size() == notesDB.size()) {
				System.out.println("No new notes");
			} else {
				for (Element link : links) {
					if (!notesDB.contains(link.attr("href"))) {
						System.out.println("Found new note: " + link.attr("href") + " at: " + dateFormat.format(date));
						String pdfUrl = "http://pages.cs.wisc.edu/~cs367-1/resources/outlines/" + link.attr("href");
						URL url = new URL(pdfUrl);
						connection = (HttpURLConnection) url.openConnection();
						connection.connect();

						input = connection.getInputStream();
						String outputFile = link.attr("href") +".pdf";
						output = new FileOutputStream(outputFile);

						byte data[] = new byte[4096];
						int count;
						while ((count = input.read(data)) != -1) {
							output.write(data, 0, count);
						}
						output.close();
						
						notesDB.add(link.attr("href"));
						updateDB();
						Email email = new Email(outputFile);
						email.send();
						
						
					}
				}
			}





		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return false;
	}




	public static void loadSavedNotes() {

		File file = new File("postedNotes.txt");
		try {
			in = new Scanner(file);
			while (in.hasNextLine()) {
				notesDB.add(in.nextLine());
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void updateDB() {
		File file = new File("postedNotes.txt");
		try {
			PrintWriter out = new PrintWriter("postedNotes.txt");
			for (String note : notesDB) {
			out.write(note + "\n");
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
