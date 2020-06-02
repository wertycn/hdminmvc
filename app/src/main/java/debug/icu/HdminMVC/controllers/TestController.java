package debug.icu.HdminMVC.controllers;

import debug.icu.HdminMVC.web.mvc.Controller;
import debug.icu.HdminMVC.web.mvc.RequestParam;
import debug.icu.HdminMVC.web.mvc.RequtstMapping;

@Controller
public class TestController {
    @RequtstMapping("/get_salary.json")
    public String getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return "this is test controller ";
    }
}
