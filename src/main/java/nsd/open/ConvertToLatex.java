package nsd.open;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertToLatex {

    private static JsonNode convertMap;
    private static Map<String,String> jsonConvertMap;
    private static Map<String,String> varHashMap;
    private static Map<String,String> middleHashMap;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File("src/main/resources/convertMap.json");
            convertMap = objectMapper.readTree(jsonFile);

            JsonNode mapNode = convertMap.get("convertMap");
            JsonNode varNode = convertMap.get("BarConvertMap");
            JsonNode middleNode = convertMap.get("middleConvertMap");
            varHashMap = objectMapper.convertValue(varNode, new TypeReference<Map<String, String>>(){});
            middleHashMap = objectMapper.convertValue(middleNode, new TypeReference<Map<String, String>>(){});
            jsonConvertMap = objectMapper.convertValue(mapNode, new TypeReference<Map<String, String>>(){});


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int symbolToNumber(String symbol) {

        Map<String, Integer> symbolToNumberMap = new HashMap<>();
        symbolToNumberMap.put("①", 1);
        symbolToNumberMap.put("②", 2);
        symbolToNumberMap.put("③", 3);
        symbolToNumberMap.put("④", 4);
        symbolToNumberMap.put("⑤", 5);
        if (symbolToNumberMap.containsKey(symbol)) {
            int number = symbolToNumberMap.get(symbol);
            System.out.println(number);
            return number;
        } else {
            System.out.println("Symbol not found in the map.");
            return -1;
        }
    }
    /**
     * hml -> xml로 변경
     *
     */
    public static void parseXml(String xmlContent, File originFile) {

        org.jsoup.nodes.Document parsed = Jsoup.parse(xmlContent, "", Parser.xmlParser());
        org.jsoup.nodes.Document document = parsed.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(true).indentAmount(4));
        Elements pTags = document.getElementsByTag("P");

        for (Element p : pTags) {
            Elements scripts = p.getElementsByTag("script");
            for (Element script : scripts) {
                String newStr = replaceToLatex(script);
                script.html(newStr);
            }
        }
        saveXML(parsed.toString(),originFile);
    }


    private static void saveXML(String prettyXML, File originalFile) {

        //xml 저장 경로 설정
        String outputDirectory = "src/main/resources/xml/";

        File dir = new File(outputDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String newFileName = outputDirectory + originalFile.getName().replace(".hml", "_parsed.xml");

        try (FileWriter fileWriter = new FileWriter(newFileName)) {
            fileWriter.write(prettyXML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * xml -> latex 변환
     * */
    public static String replaceToLatex(Element element) {
        String hmlEqStr = element.text();
        String strConverted = "";
        String[] strList;

        strConverted = hmlEqStr.replace("`"," ");
        strConverted = strConverted.replace("{", " { ");
        strConverted = strConverted.replace("}", " } ");
        strConverted = strConverted.replace("&", " & ");

        strList = strConverted.split(" ");


        strList = convertStrings(strList, jsonConvertMap,middleHashMap);

//        strList = convertMiddleString(strList, middleHashMap);
        List<String> filteredList = new ArrayList<>();
        for (String string : strList) {
            if (string.length() != 0) {
                filteredList.add(string);
            }
        }
        strList = filteredList.toArray(new String[0]);

        strList = replaceBracket(strList);

        strConverted = String.join(" ", strList);
        strConverted = replaceFrac(strConverted);
        strConverted = strConverted.replace("#", "\\\\");
        strConverted = replaceRootOf(strConverted);

//        replaceAllMatrix(strConverted);
//        replaceAllBrace(strConverted);
        strConverted = strConverted.replace("\\\\times", "\\times");
        strConverted = strConverted.replaceAll("(\\\\div)([a-zA-Z])", "$1 $2");
        strConverted = replaceFrac(replaceOver(strConverted));

        //무한루프

//        strConverted = replaceAllBar(strConverted);
//        strConverted = replaceLeftOrRight(strConverted);

        strConverted = strConverted.replaceAll("HULKBAR","\\\\overline");
        strConverted = strConverted.replaceAll("HULKVEC","\\\\overrightarrow");
        strConverted = strConverted.replaceAll("HULKDYAD","\\\\overleftrightarrow");
        strConverted = strConverted.replaceAll("it","");
        strConverted = strConverted.replaceAll("therefore","\\\\therefore");

        strConverted = removeUnmatchedBraces(strConverted);

        return strConverted;
    }

    private static String[] convertMiddleString(String[] strList, Map<String, String> middleHashMap) {
        for (int i = 0; i < strList.length; i++) {
            String candidate = strList[i];
            for (Map.Entry<String, String> entry : middleHashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (candidate.contains(key)) {
                    candidate = candidate.replace(key, value);
                }
            }
            strList[i] = candidate;
        }
        return strList;
    }

//    private static String replaceLeftOrRight(String strConverted) {
//        Pattern left = Pattern.compile("(\\S+)\\s*left\\s*(\\S+)", Pattern.CASE_INSENSITIVE);
//        Pattern right = Pattern.compile("(\\S+)\\s*right\\s*(\\S+)", Pattern.CASE_INSENSITIVE);
//
//    }

//    private static String replaceOver(String strConverted) {
//        strConverted = strConverted.replaceAll("(?i)(\\S)(?=over)", "$1 ");
//        strConverted = strConverted.replaceAll("(?i)(?<=over)(\\S)", " $1");
//
//        String[] overs = strConverted.split("over");
//
//        String strConvertedAddbrace ="";
//
//        if (overs.length > 0) {
//            if (!(overs[0].contains("{") && overs[0].contains("}"))) {
//                strConvertedAddbrace += "{" + overs[0] + "}";
//            } else {
//                strConvertedAddbrace += overs[0];
//            }
//            for (int i = 1; i < overs.length; i++) {
//                strConvertedAddbrace += overs[i];
//            }
//            return strConvertedAddbrace;
//        }
//        else {
//            return strConverted;
//        }
//    }

    private static String replaceOver(String strConverted) {
        Pattern pattern = Pattern.compile("(\\S+)\\s*over\\s*(\\S+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(strConverted);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String beforeOver = matcher.group(1);
            String afterOver = matcher.group(2);

            String replacement = "{" + beforeOver + "} over {" + afterOver + "}";
            matcher.appendReplacement(result, replacement);
        }

        matcher.appendTail(result);

        return result.toString();

    }

    private static String[] convertStrings(String[] strList, Map<String, String> convertMap, Map<String, String> middleMap) {
        for (int i = 0; i < strList.length; i++) {
            String candidate = strList[i];
            for (Map.Entry<String, String> entry : convertMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (candidate.contains(key)) {
                    candidate = candidate.replace(key, value);
                } else if (middleMap.containsKey(key) && candidate.contains(key)) {
                    System.out.println(key);
                    candidate = candidate.replace(key, middleMap.get(key));
                }
            }



            strList[i] = candidate;
        }
        return strList;
    }

    private static void replaceAllBrace(String strConverted) {
    }

    public static String replaceAllBar(String eqString) {


        eqString = eqString.replace("HULKBAR", "\\overline");
    return eqString;
    }
//        for (Map.Entry<String, String> entry : varHashMap.entrySet()) {
//            eqString = replaceBar(eqString, entry.getKey(), entry.getValue());
//        }
//        return eqString;
//
//    }
//    private static String replaceBar(String eqString, String barStr, String barElem) {
//        int cursor = 0;
//
//
//        while (cursor != -1) {
//            cursor = eqString.indexOf(barStr, cursor);
//            if (cursor == -1) {
//                break;
//            }
//
//            try {
//                int[] brackets = findBrackets(eqString, cursor, 1);
//                int eStart = brackets[0];
//                int eEnd = brackets[1];
//
//                int[] outerBrackets = findOutterBrackets(eqString, cursor);
//                int bStart = outerBrackets[0];
//                int bEnd = outerBrackets[1];
//
//                String elem = eqString.substring(eStart, eEnd);
//                String beforeBar = eqString.substring(0, bStart);
//                String afterBar = eqString.substring(bEnd);
//
//                eqString = beforeBar + barElem + elem + afterBar;
//
//                cursor = beforeBar.length() + barElem.length() + elem.length();
//            } catch (Exception e) {
//                return eqString;
//            }
//        }
//        return eqString;
//    }

    private static int[] findOutterBrackets(String eqString, int startIdx) throws Exception {
        int idx = startIdx;
        while (true) {
            idx -= 1;
            if (eqString.charAt(idx) == '{') {
                break;
            }
            if (idx < 0) {
                throw new Exception("No matching '{' found.");
            }
        }
        return findBrackets(eqString, idx, 1);
    }

    private static void replaceAllMatrix(String strConverted) {
    }

    /**
     * 괄호 추가 열린괄호만있고 닫힌괄호는없는경우.. 괄호 추가
     * @param input
     * @return
     */
    public static String removeUnmatchedBraces(String input) {
        StringBuilder sb = new StringBuilder();
        int openBraceCount = 0;

        for (char c : input.toCharArray()) {
            if (c == '{') {
                openBraceCount++;
                sb.append(c);
            } else if (c == '}') {
                if (openBraceCount > 0) {
                    openBraceCount--;
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
//        String result = sb.toString();
        //괄호 제거
//        while (openBraceCount > 0) {
//            int lastOpenBraceIndex = result.lastIndexOf('{');
//            if (lastOpenBraceIndex >= 0) {
//                result = result.substring(0, lastOpenBraceIndex) + result.substring(lastOpenBraceIndex + 1);
//                openBraceCount--;
//            } else {
//                break;
//            }
//        }
        //닫힌 괄호 추가
        while (openBraceCount > 0) {
            sb.append('}');
            openBraceCount--;
        }
        return sb.toString();
    }

    /**
     *  CONVERT
     *  ex)
     *  root {1} of {2}` -> `\sqrt[1]{2}
     */
    private static String replaceRootOf(String eqString) {

        String rootStr = "root";
        String ofStr = "of";

        while (true) {
            int rootCursor = eqString.indexOf(rootStr);
            if (rootCursor == -1) {
                break;
            }
            try {

                int ofCursor = eqString.indexOf(ofStr);

                int[] elem1 = findBrackets(eqString, rootCursor, 1);
                int[] elem2 = findBrackets(eqString, ofCursor, 1);

                String e1 = eqString.substring(elem1[0] + 1, elem1[1] - 1);
                String e2 = eqString.substring(elem2[0] + 1, elem2[1] - 1);

                String beforeRoot = eqString.substring(0, rootCursor);
                String afterRoot = eqString.substring(elem2[1]);

                eqString = beforeRoot + "\\sqrt[" + e1 + "]{" + e2 + "}" + afterRoot;

            } catch (Exception e) {
                return eqString;
            }
        }
        return eqString;
    }


    /**
     *  CONVERT
     *  ex)
     *  {1} over {2}` -> `\frac{1}{2}`
     */
    public static String replaceFrac(String eqString) {

        String hmlFracString = "over";
        String latexFracString = "\\frac";


        while (true) {
                int cursor = eqString.indexOf(hmlFracString);
                if (cursor == -1) {
                    break;
                }
                try {
                    int[] brackets = findBrackets(eqString, cursor,0);
                    int numStart = brackets[0];
                    int numEnd = brackets[1];

                    String numerator = eqString.substring(numStart, numEnd);

                    String beforeFrac = eqString.substring(0, numStart);
                    String afterFrac = eqString.substring(cursor + hmlFracString.length());

                    eqString = beforeFrac + latexFracString + numerator + afterFrac;
                } catch (Exception e) {
                    return eqString;
                }
            }

            return eqString;
        }

    private static String[] replaceBracket(String[] strList) {

        for (int i = 0; i < strList.length; i++) {
            String string = strList[i];
            if (string.equals("{")) {
                if (i > 0 && strList[i - 1].equals("\\left"))  {
                    strList[i] = "\\{";
                }
            } else if (string.equals("}")) {
                if (i > 0 && strList[i - 1].equals("\\right")) {
                    strList[i] = "\\}";
                }
            }
        }
        return strList;
    }

    public static int[] findBrackets(String eqString, int startIdx, int direction) {
        if (direction == 1) {
            int startCur = eqString.indexOf('{', startIdx);
            if (startCur == -1) {
                throw new IllegalArgumentException("Cannot find opening bracket.");
            }

            int bracketCount = 1;
            for (int i = startCur + 1; i < eqString.length(); i++) {
                char ch = eqString.charAt(i);
                if (ch == '{') {
                    bracketCount++;
                } else if (ch == '}') {
                    bracketCount--;
                }

                if (bracketCount == 0) {
                    return new int[] { startCur, i + 1 };
                }
            }
        } else {
            StringBuilder reversedString = new StringBuilder(eqString).reverse();
            for (int i = 0; i < reversedString.length(); i++) {
                char ch = reversedString.charAt(i);
                if (ch == '{') {
                    reversedString.setCharAt(i, '}');
                } else if (ch == '}') {
                    reversedString.setCharAt(i, '{');
                }
            }

            int newStartIdx = reversedString.length() - (startIdx + 1);
            int[] result = findBrackets(reversedString.toString(), newStartIdx, 1);
            return new int[] { reversedString.length() - result[1], reversedString.length() - result[0] };
        }

        throw new IllegalArgumentException("Cannot find bracket.");
    }

    public static String replaceFrac2(String eqString) {
        String hmlFracString = "over";
        String latexFracString = "\\frac";

        while (true) {
            int cursor = eqString.toLowerCase().indexOf(hmlFracString);
            if (cursor == -1) {
                break;
            }
            try {
                int numStart = findNumeratorStart(eqString, cursor);
                int numEnd = cursor;

                int denStart = cursor + hmlFracString.length();
                int denEnd = findDenominatorEnd(eqString, denStart);

                String numerator = eqString.substring(numStart, numEnd).trim();
                String denominator = eqString.substring(denStart, denEnd).trim();

//                if (!numerator.isEmpty() && !denominator.isEmpty()) {

                    String beforeFrac = eqString.substring(0, numStart);
                    String afterFrac = eqString.substring(denEnd);
                String s = extractBetweenSpaces(afterFrac);
                numerator = wrapWithBraces(beforeFrac);
                denominator = wrapWithBraces(s);
                int i = afterFrac.indexOf(s);
                String afterSub = afterFrac.substring(i + 1);

                eqString = latexFracString + numerator + denominator + afterSub ;
//                } else {
//                    break;
//                }
            } catch (Exception e) {
                return eqString;
            }
        }

        return eqString;
    }
    public static String extractBetweenSpaces(String input) {
        int firstSpaceIndex = input.indexOf(' ');  // 첫 번째 공백의 위치 찾기
        if (firstSpaceIndex == -1) return "";  // 공백이 없으면 빈 문자열 반환

        int secondSpaceIndex = input.indexOf(' ', firstSpaceIndex + 1);  // 두 번째 공백의 위치 찾기
        if (secondSpaceIndex == -1) return "";  // 두 번째 공백이 없으면 빈 문자열 반환

        return input.substring(firstSpaceIndex + 1, secondSpaceIndex).trim();
    }


    private static int findNumeratorStart(String eqString, int cursor) {
        for (int i = cursor - 1; i >= 0; i--) {
            char ch = eqString.charAt(i);
            if (Character.isWhitespace(ch) || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '(' || ch == ')') {
                return i + 1;
            }
        }
        return 0;
    }


    private static int findDenominatorEnd(String eqString, int startIdx) {
        for (int i = startIdx; i < eqString.length(); i++) {
            char ch = eqString.charAt(i);
            if (Character.isWhitespace(ch) || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '=' || ch == '(' || ch == ')') {
                return i;
            }
        }
        return eqString.length();
    }

    private static String wrapWithBraces(String input) {
        // 중괄호로 감싸기
        return "{" + input + "}";
    }

}
