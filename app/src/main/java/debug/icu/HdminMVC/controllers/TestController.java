package debug.icu.HdminMVC.controllers;

import debug.icu.HdminMVC.Service.SalaryService;
import debug.icu.HdminMVC.beans.AutoWired;
import debug.icu.HdminMVC.web.mvc.Controller;
import debug.icu.HdminMVC.web.mvc.RequestParam;
import debug.icu.HdminMVC.web.mvc.RequestMapping;

@Controller
public class TestController {
    @AutoWired
    private SalaryService salaryService;

    @RequestMapping("/get_salary.json")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience) {
        return salaryService.calSalary(Integer.parseInt(experience));
    }
}
