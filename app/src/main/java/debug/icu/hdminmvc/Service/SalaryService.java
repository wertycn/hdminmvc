package debug.icu.hdminmvc.Service;

import debug.icu.hdminmvc.beans.Bean;

@Bean
public class SalaryService {
    public Integer calSalary(Integer experience) {
        return experience * 5000;
    }
}
