package com.pub.lookup.web;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pub.lookup.components.CachedSearch;
import com.pub.lookup.components.CachedSearchComponent;
import com.pub.lookup.domain.GeoSearch;
import com.pub.lookup.domain.Point;
import com.pub.lookup.domain.PubEntity;
import com.pub.lookup.service.GeoApiService;
import com.pub.lookup.service.PubProviderService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(HomeController.class);

    @Autowired
    private PubProviderService pubProviderServise;
    
    @Autowired
    private GeoApiService geoApiService;
    
    @Qualifier("cachedSearch")
    @Autowired
    private CachedSearchComponent<CachedSearch> cachedSearch;
    
    @Qualifier("cachedRequest")
    @Autowired
    private CachedSearchComponent<String> cachedRequest;
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }
    
    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    public String getResults(Model model, @RequestParam("search") String search,
            @RequestParam("search_view") String searchView) {
        
        Point point = null;
        
        try {
            point = pubProviderServise.getPubInfo(search);
        } catch (Exception e) {
            LOGGER.error("can't get data", e);
            return "home";
        }
        
        cachedRequest.add(search);
        
        model.addAttribute("distances", point.getDistances());
        model.addAttribute("search", search);
        model.addAttribute("search_view", searchView);
        model.addAttribute("cached_search", cachedSearch.getCache());
        model.addAttribute("cached_request", cachedRequest.getCache());
        return "home";
    }
    
    @RequestMapping(value = "/specify", method = RequestMethod.GET)
    public String specifyResults(Model model, @RequestParam("search") String search) {
        String validSearch = geoApiService.getSearchStringByPriority(search);
        String searchView = geoApiService.getSearchView(search);
        model.addAttribute("search", validSearch);
        model.addAttribute("search_view", searchView);
        cachedSearch.add(new CachedSearch(Integer.valueOf(search), searchView));
        return "redirect:/pub";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String lookup(Model model, @ModelAttribute("search") String search) {
        
        if(search == null || search.isEmpty()) {
            return "home";
        }
        
        GeoSearch validatedSearch = null;
        
        try {
            validatedSearch = geoApiService.validateSearch(search);
        } catch (Exception e) {
            LOGGER.error("error search string validation", e);
            return "home";
        }
        
        model.addAttribute("result", validatedSearch.getGeoResults());
        
        return "specify";
    }
    
    @RequestMapping(value = "/picture/{pubId}", method = RequestMethod.GET)
    public void getPicture(HttpServletResponse response , @PathVariable("pubId") String pubId) {
        PubEntity pub = pubProviderServise.getPubById(pubId);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        
        if(pub == null) {
            return;
        }
        
        byte[] buffer = pub.getPicture();
        
        if(buffer == null) {
            return;
        }
        
        InputStream in = new ByteArrayInputStream(buffer);
        try {
            IOUtils.copy(in, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("image copy error", e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("can't close stream" ,e);
                }
            }
        } 
    }
}
