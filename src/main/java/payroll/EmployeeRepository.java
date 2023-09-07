package payroll;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;

interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
