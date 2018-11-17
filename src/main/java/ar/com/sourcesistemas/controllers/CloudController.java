package ar.com.sourcesistemas.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudController {


    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(String name){
        return "hello "+name;
    }
    @RequestMapping(value = "test2",method = RequestMethod.POST)
    public String test2(@RequestBody String json){
        return "hello2 "+json;
    }



}
