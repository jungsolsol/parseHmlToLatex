package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dto.*;
import nsd.open.dto.enums.Question.DiffCode;
import nsd.open.utils.StringSplit;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ParseService {

//    private final QuestionService questionService;

    public HtmlRenderDto renderHtml(String doc) throws ParserConfigurationException, IOException, SAXException {
        org.jsoup.nodes.Document parsed = Jsoup.parse(doc, "", Parser.xmlParser());
        org.jsoup.nodes.Document document = parsed.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(true).indentAmount(4));

        Elements pTags = document.getElementsByTag("p");
        List<LatexDto> latexList = extractLatexData(pTags);
        LatexDto combinedLatex = combineLatex(latexList);
        List<ParseQuestionDto> questionList = extractQuestion(pTags);
        ParseQuestionDto question = combineQueestion(questionList);

        return   HtmlRenderDto.builder()
                .answer(AnswerDto.builder().content("AAA").build())
                .parseQuestion(question)
                .latex(combinedLatex).build();}

    public Document getDocument(String doc) {
        org.jsoup.nodes.Document parsed = Jsoup.parse(doc, "", Parser.xmlParser());
        org.jsoup.nodes.Document document = parsed.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(true).indentAmount(4));
        return document;
    }
        public List<ParseQuestionDto> parseQuestion (Elements elements){
            List<ParseQuestionDto> questionList = new ArrayList<>();

            if (elements == null || elements.isEmpty()) {
                return questionList;
            }

            for (Element elem : elements) {
                String text = elem.text();
                if (text == null || text.trim().isEmpty()) {
                    continue;
                }

                StringSplit stringSplit = extractQuestionId(text);

                while (stringSplit != null) {
                    String questionId = stringSplit.getExtracted();
                    String remainingText = stringSplit.getRemaining();
                    String questionText = extractQuestionText(remainingText);
                    String answer = extractAnswer(remainingText);

                    if (questionId != null && !questionId.trim().isEmpty() &&
                            questionText != null && !questionText.trim().isEmpty() &&
                            answer != null && !answer.trim().isEmpty()) {
                        questionList.add(ParseQuestionDto.builder()
                                .questionId(questionId)
                                .questionText(questionText)
                                .answer(answer)
                                .build());
                    }

                    stringSplit = extractQuestionId(remainingText);
                }
            }

            return questionList;
        }

    private StringSplit extractQuestionId(String text) {

        int startIdx = text.indexOf("문항 ID :");
        if (startIdx != -1) {
            startIdx += "문항 ID :".length();
            int endIdx = text.indexOf("(문제)", startIdx);
            if (endIdx != -1) {
                String extracted = text.substring(startIdx, endIdx).trim();
                String remaining = text.substring(endIdx).trim();
                return new StringSplit(extracted, remaining);
            }
        }
        return null;
    }

    private String extractQuestionText(String text) {

        int startIdx = text.indexOf("(문제)");
        if (startIdx != -1) {
            startIdx += "(문제)".length();
            int endIdx = text.indexOf("(정답)", startIdx);
            if (endIdx != -1) {
                return text.substring(startIdx, endIdx).trim();
            }
        }
        return null;
    }

    private String extractAnswer(String text) {

        int startIdx = text.indexOf("(정답)");
        if (startIdx != -1) {
            startIdx += "(정답)".length();
            int endIdx = text.indexOf("(해설)", startIdx);
            if (endIdx != -1) {
                return text.substring(startIdx, endIdx).trim();
            }
        }
        return null;
    }


    private List<ParseQuestionDto> extractQuestion(Elements elements) {
        List<ParseQuestionDto> questionList = new ArrayList<>();

        for (org.jsoup.nodes.Element p : elements) {
            Elements scripts = p.getElementsByTag("CHAR");

            for (org.jsoup.nodes.Element script : scripts) {
                String scriptContent = script.html();
                ParseQuestionDto question = ParseQuestionDto.builder().questionText(scriptContent).build();
                questionList.add(question);
            }
        }

        return questionList;
    }
    private List<LatexDto> extractLatexData(Elements elements) {
        List<LatexDto> latexList = new ArrayList<>();

        for (org.jsoup.nodes.Element p : elements) {
            Elements scripts = p.getElementsByTag("script");

            for (org.jsoup.nodes.Element script : scripts) {
                String scriptContent = script.html();
                LatexDto latex = LatexDto.builder().latex("$" + scriptContent + "$").build();

                latexList.add(latex);
            }
        }

        return latexList;
    }

    private LatexDto combineLatex(List<LatexDto> latexList) {
        StringBuilder combinedLatex = new StringBuilder();
        for (LatexDto latex : latexList) {
            combinedLatex.append(latex.getLatex()).append("\n");
        }
        return LatexDto.builder().latex(combinedLatex.toString()).build();
    }

    private ParseQuestionDto combineQueestion(List<ParseQuestionDto> questionList) {
        StringBuilder combinedQuestion = new StringBuilder();
        for (ParseQuestionDto question : questionList) {
            combinedQuestion.append(question.getQuestionText()).append("\n");
        }
        return ParseQuestionDto.builder().questionText(combinedQuestion.toString()).build();
    }


    public List<ParseQuestionDetailDto> readExcel(XSSFWorkbook workbook) {

        int rowNo = 0;
        int cellIndex = 0;

        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        for(rowNo = 0; rowNo < rows; rowNo++){
            XSSFRow row = sheet.getRow(rowNo);
            if(row != null){
                int cells = row.getPhysicalNumberOfCells();
                for(cellIndex = 0; cellIndex <= cells; cellIndex++){
                    XSSFCell cell = row.getCell(cellIndex);
                    String value = "";
                    if(cell == null){
                        continue;
                    }else{
                        switch (cell.getCellType()){
                            case XSSFCell.CELL_TYPE_FORMULA:
                                value = cell.getCellFormula();
                                break;
                            case XSSFCell.CELL_TYPE_NUMERIC:
                                value = cell.getNumericCellValue() + "";
                                break;
                            case XSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue() + "";
                                break;
                            case XSSFCell.CELL_TYPE_BLANK:
                                value = cell.getBooleanCellValue() + "";
                                break;
                            case XSSFCell.CELL_TYPE_ERROR:
                                value = cell.getErrorCellValue() + "";
                                break;
                        }
                    }
                    rowNo = 0 ;
                    System.out.println( rowNo + "번 행 : " + cellIndex + "번 열 값은: " + value);
                }
            }
        }
    return null;
    }
    public ArrayList<ArrayList<String>> readFilter(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        List<ParseQuestionDetailDto> questionDetailDtoList = new ArrayList<>();

        @SuppressWarnings("resource")
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int rowindex = 0;
        int columnindex = 0;
        ArrayList<ArrayList<String>> filters = new ArrayList<ArrayList<String>>();

        int sheetCn = workbook.getNumberOfSheets();	// 시트 수
        for(int sheetnum=0; sheetnum<sheetCn; sheetnum++) {	// 시트 수만큼 반복

            int sheetnum2=sheetnum+1;
            System.out.println("sheet = " + sheetnum2);

            XSSFSheet sheet = workbook.getSheetAt(sheetnum);	// 읽어올 시트 선택
            int rows = sheet.getPhysicalNumberOfRows();    // 행의 수
            XSSFRow row = null;

            for (rowindex = 1; rowindex < rows; rowindex++) {	// 행의 수만큼 반복

                row = sheet.getRow(rowindex);	// rowindex 에 해당하는 행을 읽는다
                ArrayList<String> filter = new ArrayList<String>();	// 한 행을 읽어서 저장할 변수 선언

                if (row != null) {
                    int cells = 13;	// 셀의 수
                    cells = row.getPhysicalNumberOfCells();    // 열의 수
                    for (columnindex = 0; columnindex <= cells; columnindex++) {	// 열의 수만큼 반복
                        XSSFCell cell_filter = row.getCell(columnindex);	// 셀값을 읽는다
                        String value = "";
                        // 셀이 빈값일경우를 위한 널체크
                        if (cell_filter == null) {
                            continue;
                        } else {
                            // 타입별로 내용 읽기
                            switch (cell_filter.getCellType()) {
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    value = cell_filter.getCellFormula();
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    value = cell_filter.getNumericCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = cell_filter.getStringCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_BLANK:
                                    value = cell_filter.getBooleanCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_ERROR:
                                    value = cell_filter.getErrorCellValue() + "";
                                    break;
                            }
                        }
                        filter.add(value);
                    }
                }
                filters.add(filter);
            }
        }
        fis.close();


        for (ArrayList arrayList: filters) {

            String diffCodeString = (String) arrayList.get(4);
            DiffCode diffCode = Arrays.stream(DiffCode.values())
                    .filter(dc -> dc.getCode().equals(diffCodeString))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid diffCode: " + diffCodeString));

            ParseQuestionDetailDto parseQuestionDetailDto = ParseQuestionDetailDto.builder()
                    .questionId((String) arrayList.get(0))
                    .achCode((String) arrayList.get(45))
                    .diffCode(diffCode.getDiffCode())
                    .unit((String) arrayList.get(31))
                    .session((String) arrayList.get(33))
                    .sub1session((String) arrayList.get(35))
                    .topic((String) arrayList.get(37))
                    .build();

            questionDetailDtoList.add(parseQuestionDetailDto);
        }
        for (ParseQuestionDetailDto dtos : questionDetailDtoList ) {
            System.out.println(dtos);
        }

        return filters;
    }

}
