package com.pub.lookup.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pub.lookup.domain.GeoResult;
import com.pub.lookup.domain.GeoSearch;
import com.pub.lookup.domain.Point;
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
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }
    
    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    public String getResults(Model model, @RequestParam("search") String search) {
        
        Point point = null;
        
        try {
            point = pubProviderServise.getPubInfo(search);
        } catch (Exception e) {
            LOGGER.error("can't get data", e);
            return "home";
        }
        model.addAttribute("distances", point.getDistances());
        model.addAttribute("search", search);
        return "home";
    }
    
    @RequestMapping(value = "/specify", method = RequestMethod.GET)
    public String specifyResults(Model model, @RequestParam("search") String search) {
        search = geoApiService.getSearchStringByPriority(search);
        model.addAttribute("search", search);
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

}
