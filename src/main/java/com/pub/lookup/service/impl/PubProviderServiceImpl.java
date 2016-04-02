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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import com.pub.lookup.domain.PubEntity;
import com.pub.lookup.service.PubProviderService;

@Service
public class PubProviderServiceImpl implements PubProviderService {

    private static final Logger LOGGER = Logger.getLogger(PubProviderServiceImpl.class);
    
    @Value("${pub.source.url}")
    private String url;
    
    @Value("${pub.source.uri}")
    private String uri;
    
    @Value("${pub.source.params}")
    private String params;
    
    @Value("${browser.user.agent}")
    private String userAgent;
    
    @Override
    public List<PubEntity> getPubInfo(String search) throws Exception {
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
    
    
    private List<PubEntity> cleanData(String data) throws UnsupportedEncodingException {
        
        List<PubEntity> result = new ArrayList<>();
        
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
            if(nl != null && nl.getLength() > 0){
                for (int i = 0; i < nl.getLength(); i++) {
                    String pubName = null;
                    String area = null;
                    Element el = (org.w3c.dom.Element) nl.item(i);
                    Node pubNode = el.getElementsByTagName("a").item(0);
                    if(pubNode != null) {
                        pubName = pubNode.getFirstChild().getNodeValue();
                    }
                    
                    Node areaNode = el.getElementsByTagName("p").item(0);
                    if(areaNode != null) {
                        area = areaNode.getFirstChild().getNodeValue();
                    }
                    if(pubName != null && !pubName.isEmpty() && area != null && !area.isEmpty()) {
                        result.add(new PubEntity(pubName, area));
                    }
                }
           }
        } catch (XPathExpressionException e) {
            LOGGER.error("xPath error", e);
        }
        return result;
    }
    
}
