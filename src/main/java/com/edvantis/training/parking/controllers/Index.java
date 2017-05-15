package com.edvantis.training.parking.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by taras.fihurnyak on 5/5/2017.
 */

@Controller
public class Index {
    @RequestMapping(value = "/", method = GET)
    public String homePage() {
        return "index";
    }
}
