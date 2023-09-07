package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class EmployeeController {

	private final EmployeeRepository repository;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all() {

		List<EntityModel<Employee>> employees = repository.findAll().stream()
				//.filter(x -> name.equalsIgnoreCase(x.getFirstname()) || name.equalsIgnoreCase(x.getLastname()))
				.map(employee -> EntityModel.of(employee))
				.collect(Collectors.toList());

		return CollectionModel.of(employees);
	}
	// end::get-aggregate-root[]

	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/employees/{id}")
	EntityModel<Employee> one(@PathVariable Long id) {

		Employee employee = repository.findById(id) //
				.orElseThrow(() -> new EmployeeNotFoundException(id));

		return EntityModel.of(employee);
	}
	// end::get-single-item[]

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		return repository.findById(id) //
				.map(employee -> {
					employee.setFirstname(newEmployee.getFirstname());
					employee.setLastname(newEmployee.getLastname());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
				}) //
				.orElseGet(() -> {
					newEmployee.setId(id);
					return repository.save(newEmployee);
				});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/employees/name/{name}")
	CollectionModel<EntityModel<Employee>> findByName(@PathVariable String name) {

		List<EntityModel<Employee>> employees = repository.findAll().stream()
				.filter(x -> name.equalsIgnoreCase(x.getFirstname()) || name.equalsIgnoreCase(x.getLastname()))
				.map(employee -> EntityModel.of(employee))
				.collect(Collectors.toList());

		return CollectionModel.of(employees);
	}
	@GetMapping("/employees/search")
	CollectionModel<EntityModel<Employee>> searchByName(@RequestParam(value="name") String name) {

		List<EntityModel<Employee>> employees = repository.findAll().stream()
				.filter(x -> name.equalsIgnoreCase(x.getFirstname()) || name.equalsIgnoreCase(x.getLastname()))
				.map(employee -> EntityModel.of(employee))
				.collect(Collectors.toList());

		return CollectionModel.of(employees);
	}
}
