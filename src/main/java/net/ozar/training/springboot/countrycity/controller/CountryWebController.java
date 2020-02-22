/*
 * The MIT License
 *
 * Copyright 2020 Gökhan Ozar <gosocial2@ozar.net>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.ozar.training.springboot.countrycity.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import net.ozar.training.springboot.countrycity.model.Country;
import net.ozar.training.springboot.countrycity.repo.CountryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gökhan Ozar <gosocial2@ozar.net>
 */
@Controller
@RequestMapping("/country/")
public class CountryWebController {

    @Autowired
    CountryRepository repo;

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryWebController.class);

    @ExceptionHandler({Exception.class})
    public String databaseError() {
        return "error";
    }

    @RequestMapping("list")
    public ModelAndView getCountries() {
        ModelAndView mav = new ModelAndView("country/countryList");

        List<Country> countries = repo.findAll();

        mav.addObject("countries", countries);
        mav.addObject("pageTitle", new String("Country List"));
        mav.addObject("moduleName", "country");
        return mav;
    }

    @RequestMapping(path = "edit/{isoCode}", method = RequestMethod.GET)
    public String editCountry(Model model, @PathVariable(value = "isoCode") String isoCode) {
        LOGGER.info("Looking up country by isoCode (as ID): " + isoCode);
        
        Optional<Country> foundCountry = null;
        
        // For easy debugging
        
        try {
            foundCountry = repo.findById(isoCode);
        } catch (Exception e) {
            LOGGER.error("Some error occurred while looking up country: \n" + e.getMessage());
        }
        Country country;
        if (foundCountry == null || !foundCountry.isPresent()) {
            System.out.println("No country with isoCode " + isoCode);
            LOGGER.info("No country with ID: " + isoCode);
            country = new Country();
        } else {
            country = foundCountry.get();
        }
        if (country != null) {
            System.out.println("Found country : " + country);
            LOGGER.info("Found country : " + country);
        } else {
            System.out.println("country is null !!!!");
            LOGGER.error("country is null !!!!");
            country = new Country();
        }
        model.addAttribute("country", country);
        model.addAttribute("moduleName", "country");
        return "country/editCountry";
    }

    @PostMapping("update/{isoCode}")
    public String updateCountry(@PathVariable("isoCode") String isoCode, @Valid Country country, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            country.setIsoCode(isoCode);
            return "editCountry";
        }

        repo.save(country);

        return "redirect:/country/list";
    }

    @GetMapping("delete/{id}")
    public String deleteCountry(@PathVariable("id") String id, Model model) {
        
        repo.deleteById(id);
        return "redirect:/country/list";
    }

    @PostMapping("create")
    public String createCountry(@Valid Country country, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "country/newCountry";
        }
        repo.save(country);
        return "redirect:/country/list";
    }
    
    @GetMapping("new")
    public String newCountry(Model model) {
        model.addAttribute("country", new Country());
        model.addAttribute("moduleName", "country");
        return "country/newCountry";
    }

}
