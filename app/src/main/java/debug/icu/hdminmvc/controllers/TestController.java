package debug.icu.hdminmvc.controllers;

import debug.icu.hdminmvc.Service.SalaryService;
import debug.icu.hdminmvc.beans.AutoWired;
import debug.icu.hdminmvc.web.mvc.Controller;
import debug.icu.hdminmvc.web.mvc.RequestParam;
import debug.icu.hdminmvc.web.mvc.RequestMapping;

@Controller
public class TestController {
    @AutoWired
    private SalaryService salaryService;

    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return salaryService.calSalary(Integer.parseInt(experience));
    }

    @RequestMapping("/welcome")
    public String welcome() {
        System.out.println("request welcome action");
        return "{\"code\":\"0\",\"welcome request hdmin mini mvc framework\"}";
    }

    @RequestMapping("/test")
    public String test() {
        System.out.println("request test action");
        return "{\"code\":\"0\",\"welcome request hdmin mini mvc framework\"}";
    }

}
