package nsd.open;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import static nsd.open.ConvertToLatex.replaceToLatex;


public class HmlParser {

    public static void main(String[] args) {

        String directoryPath = "C:\\Users\\jsol7\\OneDrive\\바탕 화면\\opensource\\hml-equation-parser\\";

        File directory = new File(directoryPath);

        // .hml 파일을 필터링하여 목록 가져오기
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".hml"));

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

    /**
     * hml -> xml로 변경
     *
     */
    private static void parseXml(String xmlContent,File originFile) {

        org.jsoup.nodes.Document parsed = Jsoup.parse(xmlContent, "", Parser.xmlParser());
        org.jsoup.nodes.Document document = parsed.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(true).indentAmount(4));
        Elements p = document.getElementsByTag("P");

        for (int i=0; i< p.size(); i++) {

            Elements script = p.get(i).getElementsByTag("script");

            for (int j=0; j < script.size(); j++) {
                //여기서 반환한 string을 현재 Script 태그의 내용이랑 바꿈
                String newStr = replaceToLatex(script.get(j));
                script.html(newStr);
            }
        }
        saveXML(parsed.toString(),originFile);
    }


    private static void saveXML(String prettyXML, File originalFile) {

        String newFileName = originalFile.getAbsolutePath().replace(".hml", "_parsed.xml");

        try (FileWriter fileWriter = new FileWriter(newFileName)) {
            fileWriter.write(prettyXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
