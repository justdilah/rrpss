package controller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class StoreController {
	
	public static List read(String filename) throws FileNotFoundException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(filename));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}
	
	public static void write(String filename, List data) throws IOException {
//		PrintWriter out = new PrintWriter(new FileWriter(filename));
		BufferedWriter out = new BufferedWriter(new FileWriter(filename, true));
		try {
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i)+"\n");
			}
		} finally {
			out.close();
		}
	}
	
	public static void replace(String filename, List data) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		try {
			for (int i = 0; i < data.size(); i++) {
				
				out.write((String) data.get(i) + "\n");
			}
		} finally {
			out.close();
		}
	}
}
