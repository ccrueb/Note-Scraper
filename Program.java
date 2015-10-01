import java.io.FileOutputStream;
import java.util.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Program {
	
	public static Scanner in = null;
	public static Timer timer = null;
	
	public static void main(String[] args) {
		
		loadInformation();
		System.out.println("Welcome to Note Scraper.\n");
		while (true) {
		if (scrapeDate() > 0 && scrapeDate() < 5) {
			System.out.println("Time to scrape!");
			
			
			Notes.scapeForNotes();
			try {
				//Pause program for 6 seconds
				Thread.sleep(6*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		} else {
		
			timer = new Timer();
			timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
				    //nothing we just need the pause
				  }
				}, (8 - scrapeDate())*24*60*60*1000);
		}
		//Loop check if Monday through Thursday
//		while (true) {
//			
//			
//			System.out.print(scrapeDate());
//		}
			
		}
	

		
	}
	
	public static void loadInformation() {
		
		Notes.loadSavedNotes();
		Email.readInEmailInfo();
		
		
	}
	
	private static int scrapeDate() {

		DateFormat dateFormat = new SimpleDateFormat("u");
		Date date = new Date();
		return Integer.parseInt(dateFormat.format(date));
	}
	
	
	
}