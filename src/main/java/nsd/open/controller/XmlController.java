package nsd.open.controller;

import lombok.RequiredArgsConstructor;
import nsd.open.ConvertToLatex;
import nsd.open.HmlParser;
import nsd.open.dto.Answer;
import nsd.open.dto.HtmlRender;
import nsd.open.service.XmlParseService;
import org.jsoup.Jsoup;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import static nsd.open.ConvertToLatex.parseXml;


@Controller
@RequiredArgsConstructor
public class XmlController {

    private final   XmlParseService xmlParseService;
    private final  ResourceLoader resourceLoader;
    @GetMapping("/latex")
    public String renderXml(Model model, @RequestParam(value = "fileName",required = false) String fileName) throws IOException, ParserConfigurationException, SAXException {
        String filePath = "C:\\Users\\jsol7\\Downloads\\open\\open\\src\\main\\resources\\xml\\";
//        String filePath= "classpath:xml/"+fileName;

        File directory = new File(filePath);
//        File[] files = directory.listFiles((dir, name) -> name.(fileName));
//        File[] files = directory.listFiles((dir, name) -> name.matches(fileName));
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (files != null) {
            for (File file : files) {
                try (FileReader rw = new FileReader(file)) {
                    System.out.println("Reading file: " + file.getName());

                    StringBuilder content = new StringBuilder();
                    int i;
                    while ((i = rw.read()) != -1) {
                        content.append((char) i);
                    }
                    HtmlRender htmlRender = xmlParseService.renderHtml(content.toString());
                    System.out.println(htmlRender.latex().latex().toString());
                    model.addAttribute("latexContent", htmlRender.latex().latex());
                    model.addAttribute("QuestionContent", htmlRender.question().question());
                }

                    catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            return "latex";

        } else {
            System.out.println("No .hml files found in the directory.");
        }

        return "latex";
    }
}
