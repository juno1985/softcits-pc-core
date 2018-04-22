package org.softcits.pc.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.softcits.pc.core.model.Greeting;
@RestController
public class GreetingController {

	    @RequestMapping(path="/greeting", method = RequestMethod.GET)
	    public Greeting greeting() {
	        Greeting greeting = new Greeting(1, "Fist SpringBoot App!");
	        return greeting;
	    }

}
