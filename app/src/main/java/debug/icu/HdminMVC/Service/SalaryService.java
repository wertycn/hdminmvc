package debug.icu.HdminMVC.Service;

import debug.icu.HdminMVC.beans.Bean;

@Bean
public class SalaryService {
    public Integer calSalary(Integer experience) {
        return experience * 5000;
    }
}
