package com.zuk.conference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Serves the React app for its client side routes, so page refreshes don't end in a 404. */
@Controller
public class SpaController {

    @GetMapping({"/conference", "/authorization", "/registration", "/creatConference", "/about"})
    public String index() {
        return "forward:/index.html";
    }
}
