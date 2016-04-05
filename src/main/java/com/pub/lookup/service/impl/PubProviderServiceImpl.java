package com.pub.lookup.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import com.pub.lookup.domain.PostCode;
import com.pub.lookup.domain.PubEntity;
import com.pub.lookup.service.PostCodePersistenceService;
import com.pub.lookup.service.PostCodeLookupService;
import com.pub.lookup.service.PubProviderService;

@Service
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
    private PostCodeLookupService postalCodeLookupService;
    
    @Autowired
    private PostCodePersistenceService postCodePersistenceService;
    
    @Override
    public List<Object> getPubInfo(String search) throws Exception {
        String formatedParams = String.format(params, URLEncoder.encode(search, "UTF-8"));
        String request = url + "/" + uri + "?" + formatedParams;
        String rawResult = doGetRequest(request);
        return cleanData(rawResult);
    }

    private String doGetRequest(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", userAgent);
        CloseableHttpResponse httpResponse;
        BufferedReader reader = null;
        StringBuffer response = null;
        
        try {
            httpResponse = httpClient.execute(httpGet);
            LOGGER.info("request url = " + url + "status = " + httpResponse.getStatusLine().getStatusCode());
 
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
 
            String inputLine;
            response = new StringBuffer();
 
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
 
        } catch (IOException e) {
            LOGGER.error("error during HTTP GET request", e);
        } finally {
            try {
                if(httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                LOGGER.error("can't closee httpClient", e);
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                LOGGER.error("can't closee reader", e);
            }
        }
        return response == null ? "" : response.toString();
    }
    
    
    private List<Object> cleanData(String data) throws UnsupportedEncodingException {
        
        List<Object> result = new ArrayList<>();
        
        Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setWraplen(Integer.MAX_VALUE);
        tidy.setXmlOut(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document doc = tidy.parseDOM(inputStream, outputStream);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = null;
        try {
            expr = xpath.compile("//div[@class=\"pub_details\"]");
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if(nl != null) {
                for (int i = 0; i < nl.getLength(); i++) {
                    String pubName = null;
                    String distance = null;
                    String locality = null;
                    Element el = (org.w3c.dom.Element) nl.item(i);
                    Node pubNode = el.getElementsByTagName("a").item(0);
                    if(pubNode != null) {
                        pubName = pubNode.getFirstChild().getNodeValue();
                    }
                    NodeList details = el.getElementsByTagName("p");
                    if (details != null) {
                        for(int j = 0; j < details.getLength(); j++) {
                            Node detailsNode = details.item(j);
                            if(detailsNode != null) {
                                NamedNodeMap attrs = detailsNode.getAttributes();
                                Node namedItem = attrs.getNamedItem("class");
                                if(namedItem != null) {
                                    if (namedItem.getNodeValue().equals("distance")) {
                                        distance = detailsNode.getFirstChild().getNodeValue();
                                    } else if (namedItem.getNodeValue().equals("pub_address")) {
                                        locality = detailsNode.getFirstChild().getNodeValue();
                                    }
                                }
                                
                            }
                        }
                    }
                    
                    Matcher m = distancePattern.matcher(distance);
                    
                    if(m.find() && pubName != null && !pubName.isEmpty() && locality != null && !locality.isEmpty()) {
                        PubEntity newPub = new PubEntity(pubName, locality);
                        newPub.setDistanceMiles(Double.valueOf(m.group(1)));
                        newPub.setDistanceKilometers(Double.valueOf(m.group(2)));
                        result.add(newPub);
                    }
                    PostCode postCode = new PostCode();
                    postCode.setPostCode("XXX XXX");
                    postCodePersistenceService.saveOrUpdate(postCode);
                }
           }
        } catch (XPathExpressionException e) {
            LOGGER.error("xPath error", e);
        }
        return result;
    }
    
}
