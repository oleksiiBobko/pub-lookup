package com.pub.lookup.web;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pub.lookup.domain.PubEntity;
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
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String lookup(Model model, @ModelAttribute("address") String address) {
        List<PubEntity> pubs = null;
        try {
            pubs = pubProviderServise.getPubInfo(address);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        model.addAttribute("pubs", pubs);
        return "home";
    }

}
