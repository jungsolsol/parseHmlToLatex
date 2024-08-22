package nsd.open;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertToLatex {

    private static JsonNode convertMap;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File jsonFile = new File("src/main/resources/convertMap.json");
            convertMap = objectMapper.readTree(jsonFile);
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
        System.out.println(hmlEqStr);

        strConverted = hmlEqStr.replace("`"," ");
        strConverted = strConverted.replace("{", " { ");
        strConverted = strConverted.replace("}", " } ");
        strConverted = strConverted.replace("&", " & ");

        strList = strConverted.split(" ");



        for (int i = 0; i < strList.length; i++) {
            String candidate = strList[i];
            if (convertMap.get("convertMap").has(candidate)) {
                strList[i] = convertMap.get("convertMap").get(candidate).asText();
            } else if (convertMap.get("middleConvertMap").has(candidate)) {
                strList[i] = convertMap.get("middleConvertMap").get(candidate).asText();
            }
        }
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
        strConverted = strConverted.replace("times", "\\times ");
        strConverted = strConverted.replace("\\\\times", "\\times");
        System.out.println(strConverted);

        replaceRootOf(strConverted);
        replaceAllMatrix(strConverted);
        replaceAllBar(strConverted);
        replaceAllBrace(strConverted);

        return strConverted;
    }

    private static void replaceAllBrace(String strConverted) {
    }

    private static void replaceAllBar(String strConverted) {
    }

    private static void replaceAllMatrix(String strConverted) {
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
                if (i > 0 && strList[i - 1].equals("\\left")) {
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
}
