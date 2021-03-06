package website;

import papers.Paper;
import paperToHTML.ResultsList;
import paperToChart.ChartMaker;
import searchDB.SearchDB;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

// Class for storing the HTML of the websites.
// Includes function for searching for results in the database
public class Websites {
    private HashMap<String, WebHtml> websites;

    public Websites (){
        // each website mapped to an id
        websites = new HashMap<>();
        websites.put("index", new WebIndex());
        websites.put("results", new WebResults());
        websites.put("register", new WebRegister());
        websites.put("signin", new WebSignin());
        websites.put("result", new WebResult());
        websites.put("stylesheets_common", new WebStylesheetCommon());
        websites.put("scripts_resultsChartTemplate", new WebResultsChartTemplate());
    }

    // Function to access websites HashMap
    public String get(String website){
        return websites.get(website).toString();
    }

    // Function to search the database. Takes parameters from website URL
    public String getSearch(String SearchBar, String Filter1, String Filter2) {
        try {
            // Class for searching database
            SearchDB search = new SearchDB();
            ArrayList<Paper> results = new ArrayList<>();
            try {
                results = search.Searchdb(SearchBar, Filter1);
            }catch (Exception ignored) {}
            if (results.isEmpty()){
                String chartBuilder = "";
                chartBuilder = websites.get("scripts_resultsChartTemplate").toString().replace("TIMELABELS", "");
                chartBuilder = chartBuilder.replace("DATASETS", "");

                // String for modyfing results html to add the results from ResultsList and the chart from chartBuilder
                String resultsBuilder = websites.get("results").toString().replace("RESULTS", "<div class=\"mx-auto\" style=\"width: 100px;\">\n" +
                        "                No results\n" +
                        "              </div>");
                resultsBuilder = resultsBuilder.replace("SCRIPT", chartBuilder);

                // Closes connection to database
                search.closeConn();

                return resultsBuilder;
            } else {
                // Creates a list of results in html using the SearchDB class and function
                ResultsList cardResults = new ResultsList(results);
                // Class for creating javascript chart using SearchDB class and function
                ChartMaker chart = new ChartMaker(results);

                // String for modifying chart template to include time axis and datasets from ChartMAker class.
                String chartBuilder = "";
                chartBuilder = websites.get("scripts_resultsChartTemplate").toString().replace("DATASETS", chart.getDatasets());

                // String for modyfing results html to add the results from ResultsList and the chart from chartBuilder
                String resultsBuilder = websites.get("results").toString().replace("RESULTS", cardResults.toString());
                resultsBuilder = resultsBuilder.replace("SCRIPT", chartBuilder);

                // Closes connection to database
                search.closeConn();

                return resultsBuilder;
            }
        } catch (SQLException | URISyntaxException e) {
            // Prints if there is a problem with the database
            System.out.println(e);
            return "502 Server error";
        }
    }
}
