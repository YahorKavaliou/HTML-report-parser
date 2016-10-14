package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HTMLReader {

    //private static final String path = "d:/TRI/url/01.26.52/feature-overview.html";
    //private static final String pathToFile = "d:/TRI/url/19.38.28/redirectForDocumentsFirst-feature.html";
    //private static final String pathToFile = "d:/TRI/url/19.38.28/redirectForDocumentsSecond-feature.html";
    //private static final String pathToFile = "d:/TRI/url/01.26.52/redirectForDocumentsSeconds-feature.html";

    //private static final String pathToFile = "d:/TRI/url/01.26.52/redirectForDocumentsThird-feature.html";

    //private static final String pathToFile = "d:/TRI/url/01.26.52/redirectForDocumentsForth-feature.html";

    public static List<Scenario> readFile(String pathToFile) throws IOException {
        Document htmlFile = null;

        List<Scenario> allScenarios = new ArrayList<>();

        try {
            htmlFile = Jsoup.parse(new File(pathToFile), "UTF-8");
            Elements links = htmlFile.getElementsByAttributeValue("style", "color:black;");

            Iterator<Element> lineIterator = links.get(0).children().iterator();

            while (lineIterator.hasNext()) {
                Element scenario = lineIterator.next();
                String className = scenario.className();
                if (className.equals("passed") || className.equals("failed")) {
                    if (scenario.children().hasClass("scenario-keyword")) {
                        Scenario model = new Scenario();

                        model.setStatus(className);

                        Element givenElement = lineIterator.next();
                        String given = givenElement.getElementsByClass("step-name").get(0).text();
                        model.setGiven(given);

                        lineIterator.next();

                        Element then1Element = lineIterator.next();
                        String then1 = then1Element.getElementsByClass("step-name").get(0).text();
                        model.setThen1(then1);

                        Elements exceptions = then1Element.getElementsByClass("step-error-message");
                        if (!exceptions.isEmpty()) {
                            String error = exceptions.get(0).getElementsByTag("label").text();
                            model.setError1(error);
                        }

                        lineIterator.next();

                        Element then2Element = lineIterator.next();
                        String then2 = then2Element.getElementsByClass("step-name").get(0).text();
                        model.setThen2(then2);

                        exceptions = then2Element.getElementsByClass("step-error-message");
                        if (!exceptions.isEmpty()) {
                            String error = exceptions.get(0).getElementsByTag("label").text();
                            model.setError2(error);
                        }

                        allScenarios.add(model);
                    }
                } else if (className.equals("output-data")) {
                    Elements outputDateDIvs = scenario.getElementsByTag("div");
                    if (!outputDateDIvs.isEmpty()) {
                        String url = outputDateDIvs.get(0).text();
                        allScenarios.get(allScenarios.size() - 1).setUrl(url);
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return allScenarios;
    }

    public List<String> getFeatureList(String html) {
        Document htmlFile = null;

        List<String> listOfFeatures = new ArrayList<String>();

        //listOfFeatures = htmlFile.getAllElements();

        Elements links = htmlFile.select("a[href]");
        //String result = Xsoup.compile("//a/@href").evaluate(document).get();
        return listOfFeatures;
    }




}
