package com.city.autocomplete.application.api;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(description = "Controller to return rendered home page")
public class HomeController {

    @GetMapping("/")
    public String renderIndex() {
        return "index";
    }
}
