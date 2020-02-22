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

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import net.ozar.training.springboot.countrycity.model.City;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import net.ozar.training.springboot.countrycity.repo.CityRepository;

/**
 *
 * @author Ozar <gosocial2@ozar.net>
 */
@RestController
@RequestMapping("/api/cities")
public class CityRestController {
    
    @Autowired
    CityRepository repo;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CityRestController.class);
    
   
    @RequestMapping(value="/list/", method = RequestMethod.GET)
    public List<City> list() {
        List<City> cList = repo.findAll();
        cList.forEach((c) -> {
            System.out.println("City : "+c);
        });
        return cList;
    }
    
    // @GetMapping("/{id}")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public City get(@PathVariable("id") Integer id) {
        LOGGER.info("Looking up city by id (as ID): "+id);
        Optional<City> foundCity = null;
        try {
            foundCity = repo.findById(id);
        } catch (Exception e) {
            LOGGER.error("Some error occurred while looking up city: \n"+e.getMessage());
        }
        City result;
        if (foundCity == null || !foundCity.isPresent()) {
            System.out.println("No city with id "+id);
            LOGGER.info("No city with ID: "+id);
            result = new City();
        } else {
            result = foundCity.get();
        }
        return result;
    }
    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody City input) {
        return null;
    }
    */
    @RequestMapping(value="/", method = RequestMethod.PUT)
    public City updateCity(@RequestBody City city) {
        return repo.save(city);
    }
    
    // @PostMapping
    // public ResponseEntity<?> post(@RequestBody City input) {
    @RequestMapping(value="/", method = RequestMethod.POST)
    public City createProduct(@RequestBody City city) {
        City savedResult = repo.save(city);
        return savedResult;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return null;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public Object handleError(HttpServletRequest req, Exception ex) {
        Object errorObject = new Object();
        return errorObject;
    }
    
}
