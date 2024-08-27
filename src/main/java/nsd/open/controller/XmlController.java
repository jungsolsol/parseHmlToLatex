package nsd.open.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import nsd.open.dto.HtmlRenderDto;
import nsd.open.dto.ParseQuestionDetailDto;
import nsd.open.dto.ParseQuestionDto;
import nsd.open.dto.QuestionDto;
import nsd.open.payload.model.RequestQType;
import nsd.open.service.ParseService;
import nsd.open.service.QuestionService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/xml")
public class XmlController {


    private final ParseService parseService;

    private final QuestionService questionService;


//    private final  ResourceLoader resourceLoader;

    @Operation(summary = "문항 저장", description = "문항 저장",tags = "hml to WC")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HtmlRenderDto.class)))
    })
    @PostMapping
    @ResponseBody
    public ResponseEntity<?> insertQuestion() throws IOException {

        String filePath = "C:\\Users\\jsol7\\Downloads\\open\\open\\src\\main\\resources\\xml\\";
        File directory = new File(filePath);
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        List<ParseQuestionDto> questionList = new ArrayList<>();
        List<ParseQuestionDetailDto> questionDetailDtoList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                try (FileReader rw = new FileReader(file)) {

                    StringBuilder content = new StringBuilder();
                    int i;
                    while ((i = rw.read()) != -1) {
                        content.append((char) i);
                    }
                    Document doc = parseService.getDocument(content.toString());
                    Elements elements = doc.getElementsByTag("BODY");
                    questionList = parseService.parseQuestion(elements);

                }
                catch (Exception e) {
                }
            }
        }

//        TODO requestParam 에서 천재교육, 대교 별로 타입 확인
//          1. 대교 일시 API 요청하여 문항 삽입
//          2. 천재교육일시 excel 읽어서 문항 삽입


        if (RequestQType.천재교육.getQTypeId() == 1) {
            questionDetailDtoList = parseService.readFilter("C:\\Users\\jsol7\\Downloads\\open\\open\\src\\main\\resources\\xlsx\\수학1(이준열)_4-1-1_문항속성.xlsx");

        }

        questionService.insertParseXmlQuestion(questionList, questionDetailDtoList);
        return new ResponseEntity<>(questionList, HttpStatus.OK);
    }

    @Operation(summary = "문항 검색", description = "문항 검색",tags = "")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionDto.class)))
    })
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getQuestion() {


        return new ResponseEntity<>("response", HttpStatus.OK);
    }

    @GetMapping("/latex")
    public String renderXml(Model model, @RequestParam(value = "fileName",required = false) String fileName) throws IOException, ParserConfigurationException, SAXException {
        String filePath = "C:\\Users\\jsol7\\Downloads\\open\\open\\src\\main\\resources\\xml\\";
        File directory = new File(filePath);
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
                    HtmlRenderDto htmlRender = parseService.renderHtml(content.toString());
                    model.addAttribute("latexContent", htmlRender.getLatex().getLatex());
                    model.addAttribute("QuestionContent", htmlRender.getParseQuestion().getQuestionText());
                }

                    catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            List<QuestionDto> allQuestion = questionService.getAllQuestion();
//            model.addAttribute("QuestionByDB", allQuestion);


        } else {
            System.out.println("No .hml files found in the directory.");
        }


        return "latex";
    }
}
