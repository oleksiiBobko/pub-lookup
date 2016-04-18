package com.pub.lookup.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSection;
import com.pub.lookup.dao.interfaces.DistanceDao;
import com.pub.lookup.dao.interfaces.PointDao;
import com.pub.lookup.dao.interfaces.PubDao;
import com.pub.lookup.domain.Distance;
import com.pub.lookup.domain.GeoResult;
import com.pub.lookup.domain.GeoSearch;
import com.pub.lookup.domain.Point;
import com.pub.lookup.domain.PubEntity;
import com.pub.lookup.service.GeoApiService;
import com.pub.lookup.service.PubProviderService;

@Service
@Transactional
public class PubProviderServiceImpl implements PubProviderService {

    private static final Logger LOGGER = Logger.getLogger(PubProviderServiceImpl.class);
    
    private static final String DISTANCE_PATTERN = "([0-9]*\\.[0-9]*).*\\(([0-9]*\\.[0-9]*)";
    
    private static Pattern distancePattern = Pattern.compile(DISTANCE_PATTERN);
    
    @Value("${pub.source.url}")
    private String url;
    
    @Value("${pub.source.uri}")
    private String uri;
    
    @Value("${pub.source.params}")
    private String params;
    
    @Value("${browser.user.agent}")
    private String userAgent;
    
    @Autowired
    private GeoApiService geoApiService;
    
    @Autowired
    private PointDao pointDao;
    
    @Autowired
    private PubDao pubDao;
    
    @Autowired
    private DistanceDao distanceDao;
    
    @Override
    public Point getPubInfo(String search) throws Exception {
        
        GeoSearch geoSearch = geoApiService.validateSearch(search);
        
        Point result = pointDao.find(geoSearch.getSearchId());
        if(result != null) {
            return result;
        }
        
        String formatedParams = String.format(params, URLEncoder.encode(geoSearch.getSearchId(), "UTF-8"));
        String request = url + "/" + uri + "?" + formatedParams;
        return doGetRequest(request, geoSearch);
    }

    private Point doGetRequest(String url, GeoSearch search) throws Exception {
        
        Point point = new Point();
        point.setPointId(search.getSearchId());
        pointDao.add(point);
        
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
            
            webClient.waitForBackgroundJavaScript(10000);
//            List<HtmlSection> sections = (List<HtmlSection>) page.getByXPath("//section[@class=\"pub\"]");
//            List<HtmlImage> img = (List<HtmlImage>) page.getByXPath("//img[@class=\"pub_image\"]");
            
            List<HtmlDivision> details = (List<HtmlDivision>) page.getByXPath("//div[@class=\"pub_details\"]");
            for (HtmlDivision div : details) {
                
                List<String> rawValues = new ArrayList<String>();
                for (DomElement element : div.getChildElements()) {
                    rawValues.add(element.asText());
                }
                
                String pubName = rawValues.get(0);
                String distance = rawValues.get(1);
                String[] d = rawValues.get(2).split("\n");
                String address = d[0];
                String city = "";
                for(int i = 1 ; i < d.length - 1; i++) {
                    city = city + " " + d[i];
                }
                city = city.trim();
                String postCode = d[d.length - 1];
                
                Matcher m = distancePattern.matcher(distance);
                
                PubEntity pub = null;
                
                if(m.find() && pubName != null && !pubName.isEmpty() && city != null && !city.isEmpty()) {
                    pub = pubDao.find(pubName + city);
                    if(pub == null) {
                        pub = new PubEntity(pubName, city);
                        GeoSearch pubInfoList = geoApiService.validateSearch(city);
                        GeoResult pubInfo = pubInfoList.getGeoResults().get(0);
                        pub.setCountry(pubInfo.getAdministrativeAreaLevel1());
                        pub.setDistrict(pubInfo.getAdministrativeAreaLevel3());
                        pub.setCity(city);
                        pub.setPostCode(postCode);
                        pub.setAddress(address);
                        pubDao.add(pub);
                    }
                    Distance dist = new Distance();
                    dist.setDistance(distance);
                    dist.setDistanceMiles(Double.valueOf(m.group(1)));
                    dist.setDistanceKilometers(Double.valueOf(m.group(2)));
                    pub.getDistances().add(dist);
                    dist.setPub(pub);
                    dist.setPoint(point);
                    point.getDistances().add(dist);
                    distanceDao.add(dist);
                }
            }
            
        } catch (IOException e) {
            LOGGER.error("can't render html", e);
        } finally {
            webClient.close();
        }
        return point;
   }
    
}
