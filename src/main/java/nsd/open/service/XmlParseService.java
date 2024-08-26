package nsd.open.service;

import lombok.RequiredArgsConstructor;
import nsd.open.dto.Answer;
import nsd.open.dto.HtmlRender;
import nsd.open.dto.Latex;
import nsd.open.dto.Question;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
    private List<Question> extractQuestion(Elements elements) {
        List<Question> questionList = new ArrayList<>();

        for (org.jsoup.nodes.Element p : elements) {
            Elements scripts = p.getElementsByTag("CHAR");

            for (org.jsoup.nodes.Element script : scripts) {
                String scriptContent = script.html();
                Question question = Question.builder().question( scriptContent).build();
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
