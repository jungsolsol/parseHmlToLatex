package nsd.open;

import java.io.*;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static nsd.open.ConvertToLatex.parseXml;


@SpringBootApplication
public class HmlParser {

	public static void main(String[] args) {
		SpringApplication.run(HmlParser.class, args);
//		String directoryPath = "C:\\Users\\jsol7\\Downloads\\open\\open\\";
//
//		File directory = new File(directoryPath);
//
//		// .hml 파일을 필터링하여 목록 가져오기
//		File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".hml"));
//
//		if (files != null) {
//			for (File file : files) {
//				try (FileReader rw = new FileReader(file)) {
//					System.out.println("Reading file: " + file.getName());
//
//					StringBuilder content = new StringBuilder();
//					int i;
//					while ((i = rw.read()) != -1) {
//						content.append((char) i);
//					}
//
//					parseXml(content.toString(),file);
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//		} else {
//			System.out.println("No .hml files found in the directory.");
//		}
	}
	@PostConstruct
	private static void makeXmlFile() {
		String directoryPath = "C:\\Users\\jsol7\\Downloads\\open\\open\\";
		File[] files = getFiles(directoryPath);

		if (files != null) {
			for (File file : files) {
				try (FileReader rw = new FileReader(file)) {
					System.out.println("Reading file: " + file.getName());

					StringBuilder content = new StringBuilder();
					int i;
					while ((i = rw.read()) != -1) {
						content.append((char) i);
					}

					parseXml(content.toString(),file);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else {
			System.out.println("No .hml files found in the directory.");
		}
	}

	public static File[] getFiles(String directoryPath) {
		File directory = new File(directoryPath);

		// .hml 파일을 필터링하여 목록 가져오기
		File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".hml"));
		return files;
	}
}
