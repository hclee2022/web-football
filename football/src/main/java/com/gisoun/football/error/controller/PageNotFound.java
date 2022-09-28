package com.gisoun.football.error.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageNotFound implements ErrorController {
    
    // private final String ERROR_PATH = "/:pathMatch(.*)*";
    private final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String redirectRoot() {
        return "index.html";
    }
    
    public String getErrorPath() {
        // return null;
        return "/error";
    }

}
