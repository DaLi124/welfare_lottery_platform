package org.springblade.modules.shijiebei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.shijiebei.entity.Employee;
import org.springblade.modules.shijiebei.mapper.EmployeeMapper;
import org.springblade.modules.shijiebei.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements
        EmployeeService {
}
