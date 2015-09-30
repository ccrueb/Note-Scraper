import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Notes {

	private List = new ArrayList

			public Notes(String date) {

	}




	public boolean saveNotes() {

		return false;
	}

	public static boolean scapeForNotes() {
		InputStream input = null;
		OutputStream output = null;
		HttpURLConnection connection = null;
		try {
			Document doc = Jsoup.connect("http://pages.cs.wisc.edu/~cs367-1/resources/outlines/").get();
			Elements links = doc.select("a[href$=.pdf]");
			for (Element link : links) {
				System.out.println(link.attr("href"));
			}




			String pdfUrl = "http://pages.cs.wisc.edu/~cs367-1/resources/outlines/" + links.get(1).attr("href");
			URL url = new URL(pdfUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();

			input = connection.getInputStream();
			output = new FileOutputStream("test.pdf");

			byte data[] = new byte[4096];
			int count;
			while ((count = input.read(data)) != -1) {
				output.write(data, 0, count);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
