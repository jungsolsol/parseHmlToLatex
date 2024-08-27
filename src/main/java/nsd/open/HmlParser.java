package nsd.open;

import java.io.*;

import lombok.extern.slf4j.Slf4j;
import nsd.open.utils.ConvertToLatex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

import static nsd.open.utils.ConvertToLatex.parseXml;


@Slf4j
@SpringBootApplication
public class HmlParser {

	public static void main(String[] args) throws IOException {

		String s="①";
		ConvertToLatex.symbolToNumber(s);

		SpringApplication.run(HmlParser.class, args);

	}
	@PostConstruct
	private static void makeXmlFile() {
		String directoryPath = "C:\\Users\\jsol7\\Downloads\\open\\open\\";
		File[] files = getFiles(directoryPath);

		if (files != null) {
			for (File file : files) {
				try (FileReader rw = new FileReader(file)) {
					log.info("Reading file: " + file.getName());
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
			log.warn("No .hml files found in the directory.");
		}
	}

	public static File[] getFiles(String directoryPath) {
		File directory = new File(directoryPath);

		// .hml 파일을 필터링하여 목록 가져오기
		File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".hml"));
		return files;
	}
}
