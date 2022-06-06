package com.imperva.springthreatdashboard.Controller;

import com.imperva.springthreatdashboard.entity.CveMention;
import com.imperva.springthreatdashboard.entity.CveTweets;
import com.imperva.springthreatdashboard.repository.CveMentionRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("cves")
@CrossOrigin(origins = "*")
public class CveMentionController {

    HttpClient httpClient = HttpClient.newHttpClient();

    @Autowired
    CveMentionRepository cveMentionRepository;

    @Autowired
    CveTweetsController cveTweetsController = new CveTweetsController();

    List<CveTweets> cveList = new ArrayList<>();

    Map<String, CveMention> cveMentionMap = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void initialiseCaptchaList() throws Exception{

        System.out.println("initialising cve mention controller");
        cveList = cveTweetsController.getAll();
        System.out.println("initialising cve mention controller cve mention map");
        intialiseCveMentionMap();
        updateCveMentionsFromCveTweetsList();
    }

    private void intialiseCveMentionMap(){
        List<CveMention> cveMentions = cveMentionRepository.findAll();
        int size = cveMentions == null ? 0 : cveMentions.size();
        System.out.println("found " + size + " cve mentions from repo");
        for (CveMention mention: cveMentions){
            cveMentionMap.put(mention.getId(), mention);
        }
    }

    private void updateCveMentionsFromCveTweetsList() throws Exception {
        System.out.println("initialising cve mentions from tweets, tweet count: " + cveList.size());
        for (CveTweets tweet: cveList){
            String cvesMentioned = tweet.getCvesMentioned().replace("[","");
            cvesMentioned = cvesMentioned.replace("]","");
            cvesMentioned = cvesMentioned.replace("'","");
            String[] splitCves = cvesMentioned.split(", ");

            for (String cve: splitCves){
                CveMention mention;
                if ( (mention = cveMentionMap.get(cve)) != null){
                    // already present
                    System.out.println("CVE: " + cve + " already found");
                    if (! mention.getTweetIds().contains(String.valueOf(tweet.getTweetId()))){
                        mention.addTweetId(String.valueOf(tweet.getTweetId()));

                        // check which tweet mentioned it first and update it.
                        int dateCompare = mention.getCreationDate().compareTo(tweet.getDateCreated());
                        if (dateCompare >= 0) { // 1 if date passed as param is older
                            mention.setCreationDate(tweet.getDateCreated());
                        }

                        mention.incrementMentionCount(1);
                        cveMentionRepository.save(mention);
                    }
                } else {
                    // not present
                    mention = new CveMention(cve, tweet.getDateCreated(), String.valueOf(tweet.getTweetId()));

                    System.out.println("scraping data for cve : " + cve);
                    URL url = new URL("https://www.cvedetails.com/cve-details.php?cve_id=" + cve);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    InputStream responseStream = connection.getInputStream();

                    System.out.println(cve);

                    Document content = Jsoup.parse(new String(responseStream.readAllBytes(), StandardCharsets.UTF_8));
                    connection.disconnect();
                    responseStream.close();

                    Elements tables = content.select("table");

                    if (tables.size() > 7) {
                        Element table = tables.get(8);
                        Elements tableRows = table.select("tr");

                        String affectedProduct = "";
                        String affectedProducts = "";
                        for (int i = 1; i < tableRows.size(); i++){

                            try {
                                Element tableRow = tableRows.get(i);
                                Elements columns = tableRow.select("td");

                                Element productType = columns.get(1);
                                Element vendor = columns.get(2);
                                Element product = columns.get(3);
                                Element version = columns.get(4);

                                String productTypeChild = productType.childNode(0).toString();
                                String vendorChild = vendor.childNode(1).childNode(0).toString();
                                String productChild = product.childNode(1).childNode(0).toString();
                                String versionNumber = version.childNode(0).toString();

                                affectedProduct = productTypeChild + " - " + vendorChild + " - " + productChild + ":" + versionNumber;
                            } catch (Exception e){
                            }

                            affectedProducts += affectedProduct + ", ";
                        }
                        affectedProducts = affectedProducts.substring(0, affectedProducts.length() - 2); //should remove the last ', '
                        mention.setAffectedProducts(affectedProducts);

                        cveMentionRepository.save(mention);
                        System.out.println("Saved CVE: " + cve + " to database");
                        this.cveMentionMap.put(cve, mention);
                    }
                }
            }
        }
    }

    /**
     * Latest CVE:s for a given timespan (like 1hr) with related tweets ordered by the nr of tweets (highest first)
     * @return
     */

    @GetMapping("/get")
    public CveMention get(@RequestParam(name = "id") String id ){
        System.out.println("searching for cve : " + id);
        CveMention cveMention = cveMentionMap.get(id);
        if (cveMention != null){
            System.out.println("cve mention found: " + cveMention);
        }
        return cveMention;
    }

    @GetMapping("/latest")
    public List<CveMention> latest(@RequestParam(name = "hoursFromNow", defaultValue = "1") long hoursFromNow ){
        List<CveMention> mentionsSince = new ArrayList<>();
        long hoursToMilis = 1000 * 60 * 60 * hoursFromNow ;
        Date startTimeRange = new Date(System.currentTimeMillis() - hoursToMilis);
        System.out.println("searching for latest cve mentions, date range start : " + startTimeRange);
        System.out.println("map size to check : " + cveMentionMap.size());
        for (String cve: cveMentionMap.keySet()){
            CveMention mention = cveMentionMap.get(cve);
            int compareResult = mention.getCreationDate().compareTo(startTimeRange);
            if (compareResult >= 0){
                System.out.println("cve matching date found: " + mention);
                mentionsSince.add(mention);
            }
        }
        Collections.sort(mentionsSince);
        return mentionsSince;
    }
}
