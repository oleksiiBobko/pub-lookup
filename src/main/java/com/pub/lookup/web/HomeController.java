package com.pub.lookup.web;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pub.lookup.domain.PostCode;
import com.pub.lookup.domain.PubEntity;
//import com.pub.lookup.domain.PubEntity;
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
    
    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }
    
    @RequestMapping(value = "/pub", method = RequestMethod.GET)
    public String getResults(Model model, @RequestParam("search") String search) {
        
        PostCode postCode = null;
        
        try {
            postCode = pubProviderServise.getPubInfo(search);
        } catch (Exception e) {
            LOGGER.error("can\'t get data", e);
            return "home";
        }
        model.addAttribute("distances", postCode.getDistances());
        return "home";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String lookup(Model model, @ModelAttribute("search") String search) {
        
        if(search == null || search.isEmpty()) {
            return "home";
        }
        
        try {
            pubProviderServise.getPubInfo(search);
        } catch (Exception e) {
            LOGGER.error("error on data creation", e);
            return "home";
        }
        
        return "redirect:/pub";
    }

}
