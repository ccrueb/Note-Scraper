import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Program {
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("http://pages.cs.wisc.edu/~cs367-1/resources/outlines/").get();
			Elements links = doc.select("a[href$=.pdf]");
			for (Element link : links) {
				System.out.println(link.attr("href"));
			}
			
			Elements divs = doc.select("div");
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
