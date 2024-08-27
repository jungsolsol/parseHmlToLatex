package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dto.*;
import nsd.open.utils.StringSplit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;


@Service
@RequiredArgsConstructor
public class XmlParseService {

//    private final QuestionService questionService;

    public HtmlRender renderHtml(String doc) throws ParserConfigurationException, IOException, SAXException {
        org.jsoup.nodes.Document parsed = Jsoup.parse(doc, "", Parser.xmlParser());
        org.jsoup.nodes.Document document = parsed.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(true).indentAmount(4));

        Elements pTags = document.getElementsByTag("p");
        List<Latex> latexList = extractLatexData(pTags);
        Latex combinedLatex = combineLatex(latexList);
        List<Question> questionList = extractQuestion(pTags);
        Question question = combineQueestion(questionList);

        return new HtmlRender(new Answer("A"), combinedLatex, question);
    }

    public Document getDocument(String doc) {
        org.jsoup.nodes.Document parsed = Jsoup.parse(doc, "", Parser.xmlParser());
        org.jsoup.nodes.Document document = parsed.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(true).indentAmount(4));
        return document;
    }

//    public List<Question> parseQuestion(Elements elements) {
//        List<Question> questionList = new ArrayList<>();
//        for (Element elem : elements) {
//            String text = elem.text();
//            StringSplit stringSplit = extractQuestionId(text);
//            String questionId = stringSplit.getExtracted();
//            String remainingText = stringSplit.getRemaining();
//            String questionText = extractQuestionText(remainingText);
//            String answer = extractAnswer(remainingText);
//
//            System.out.println(questionId);
//            System.out.println(questionText);
//            System.out.println(answer);
////            if (questionId != null && questionText != null && answer != null) {
//                questionList.add(Question.builder()
//                        .questionId(questionId)
//                        .questionText(questionText)
//                        .answer(answer)
//                        .build());
////            }
//
//            while (remainingText != null && !remainingText.isEmpty()) {
//                stringSplit = extractQuestionId(remainingText);
//                questionId = stringSplit.getExtracted();
//                remainingText = stringSplit.getRemaining();
//                questionText = extractQuestionText(remainingText);
//                answer = extractAnswer(remainingText);
//
//                System.out.println(questionId);
//                System.out.println(questionText);
//                System.out.println(answer);
////                if (questionId != null && questionText != null && answer != null) {
//                    questionList.add(Question.builder()
//                            .questionId(questionId)
//                            .questionText(questionText)
//                            .answer(answer)
//                            .build());
////                }
//            }
//        }
//
//        return questionList;
//    }
        public List<ParseQuestion> parseQuestion (Elements elements){
            List<ParseQuestion> questionList = new ArrayList<>();

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
                        questionList.add(ParseQuestion.builder()
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


    private List<Question> extractQuestion(Elements elements) {
        List<Question> questionList = new ArrayList<>();

        for (org.jsoup.nodes.Element p : elements) {
            Elements scripts = p.getElementsByTag("CHAR");

            for (org.jsoup.nodes.Element script : scripts) {
                String scriptContent = script.html();
                Question question = Question.builder().question(scriptContent).build();
                questionList.add(question);
            }
        }

        return questionList;
    }
    private List<Latex> extractLatexData(Elements elements) {
        List<Latex> latexList = new ArrayList<>();

        for (org.jsoup.nodes.Element p : elements) {
            Elements scripts = p.getElementsByTag("script");

            for (org.jsoup.nodes.Element script : scripts) {
                String scriptContent = script.html();
                Latex latex = Latex.builder().latex("$" + scriptContent + "$").build();

                latexList.add(latex);
            }
        }

        return latexList;
    }

    private Latex combineLatex(List<Latex> latexList) {
        StringBuilder combinedLatex = new StringBuilder();
        for (Latex latex : latexList) {
            combinedLatex.append(latex.latex()).append("\n");
        }
        return Latex.builder().latex(combinedLatex.toString()).build();
    }

    private Question combineQueestion(List<Question> questionList) {
        StringBuilder combinedQuestion = new StringBuilder();
        for (Question question : questionList) {
            combinedQuestion.append(question.question()).append("\n");
        }
        return Question.builder().question(combinedQuestion.toString()).build();
    }



}
