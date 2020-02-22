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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import net.ozar.training.springboot.countrycity.model.City;
import net.ozar.training.springboot.countrycity.model.Country;
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
import net.ozar.training.springboot.countrycity.repo.CityRepository;
import net.ozar.training.springboot.countrycity.repo.CountryRepository;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Gökhan Ozar <gosocial2@ozar.net>
 */
@Controller
@RequestMapping("/city")
public class CityWebController {

    @Autowired
    CityRepository repo;
    
    @Autowired
    CountryRepository countryRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(CityWebController.class);

    @ExceptionHandler({Exception.class})
    public String databaseError() {
        return "error";
    }

    @RequestMapping("/list")
    public ModelAndView getCities() {
        ModelAndView mav = new ModelAndView("city/cityList");

        List<City> cities = repo.findAll();

        mav.addObject("cities", cities);
        mav.addObject("pageTitle", "City List");
        mav.addObject("moduleName", "city");
        return mav;
    }
    
    @GetMapping("new")
    public ModelAndView displayNewCityForm() {
        ModelAndView mav = new ModelAndView("city/newCity");
        
        List<Country> countries = countryRepo.findAll();
        
        mav.addObject("city", new City());
        mav.addObject("countryList", countries);
        mav.addObject("pageTitle", "Adding a New City");
        mav.addObject("moduleName", "city");
        
        return mav;
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editCity(@PathVariable(value = "id") Integer id) {
        ModelAndView mav = new ModelAndView("city/editCity");
        LOGGER.info("Looking up city by id (as ID): " + id);
        // City city = repo.getOne(id);
        Optional<City> foundCity = null;
        try {
            foundCity = repo.findById(id);
        } catch (Exception e) {
            LOGGER.error("Some error occurred while looking up city: \n" + e.getMessage());
        }
        City city;
        if (foundCity == null || !foundCity.isPresent()) {
            System.out.println("No city with id " + id);
            LOGGER.info("No city with ID: " + id);
            mav = new ModelAndView(new RedirectView("/city/new"));
            city = new City();
        } else {
            city = foundCity.get();
        }
        if (city != null) {
            System.out.println("Found city : " + city);
            LOGGER.info("Found city : " + city);
        } else {
            System.out.println("city is null !!!!");
            LOGGER.error("city is null !!!!");
            city = new City();
        }
        List<Country> countries = countryRepo.findAll();
        if (countries == null) {
            countries = new ArrayList<>();
        }
        
        mav.addObject("city", city);
        mav.addObject("countryList", countries);
        mav.addObject("moduleName", "city");
        if (city.getId() != null)
            mav.addObject("pageTitle", "Edit City "+city.getCityName());
        return mav;
    }

    @PostMapping("update/{id}")
    public String updateCity(@PathVariable("id") Integer id, @Valid City city, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            city.setId(id);
            return "redirect:/city/edit/"+id;
        }

        repo.save(city);

        return "redirect:/city/list";
    }

    @GetMapping("delete/{id}")
    public String deleteCity(@PathVariable("id") Integer id, Model model) {
        City city = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid city Id:" + id));
        repo.delete(city);

        return "redirect:/city/list";
    }

    @PostMapping("create")
    public String createCity(@Valid City city, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "city/newCity";
        }
        repo.save(city);
        return "redirect:/city/list";
    }

}
