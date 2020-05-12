package com.cognizant.ormlearn;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Attempt;
import com.cognizant.ormlearn.model.Attempt_Question;
import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.repository.StockRepository;
import com.cognizant.ormlearn.service.AttemptService;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.DepartmentService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

@SpringBootApplication
public class OrmLearnApplication {

	private static StockRepository stockRepository;
	private static CountryService countryService;
	private static AttemptService attemptService;

	public static StockRepository getStockRepository() {
		return stockRepository;
	}

	@Autowired
	public void setStockRepository(StockRepository stockRepository) {
		OrmLearnApplication.stockRepository = stockRepository;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
	private static EmployeeService employeeService;
	private static DepartmentService departmentService;
	private static SkillService skillService;

	private static void findByDate1() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt1 = sdf.parse("2019-09-1");
		Date dt2 = sdf.parse("2019-09-30");
		stockRepository.findByCodeAndDateBetween("FB", dt1, dt2);
	}

	private static void findByGoogle() {
		stockRepository.findByCodeAndCloseGreaterThan("GOOGL", new BigDecimal("1250"));
	}

	private static void findTopOrder() {
		stockRepository.findTop3ByOrderByVolumeDesc();
	}

	private static void findtopasc() {
		stockRepository.findTop3ByCodeOrderByClose("NFLX");
	}

	private static void testGetEmployee() {
		LOGGER.info("Start");
		Employee employee = employeeService.get(1);
		LOGGER.debug("Employee:{}", employee);
		LOGGER.debug("Department:{}", employee.getDepartment());
		LOGGER.debug("Skills:{}", employee.getSkillList());
		LOGGER.info("End");
	}

	private static void testAddEmployee() {
		Employee employee = new Employee();
		employee.setName("nisha");
		employee.setPermanent(true);
		employee.setDateOfBirth(new Date(2020 - 07 - 10));
		employee.setSalary(33000);
		Department department = departmentService.get(1);
		employee.setDepartment(department);
		employeeService.save(employee);
	}
	
	private static void testUpdateEmployee() {
		Employee employee = employeeService.get(1);
		Department department = departmentService.get(3);
		employee.setDepartment(department);
		employeeService.save(employee);
	}

	private static void testGetDepartment() {
		Department department = departmentService.get(3);
		Set<Employee> employeeList = department.getEmployeeList();
	}

	private static void testAddSkillToEmployee() {
		Employee employee = employeeService.get(4);
		Skill skill = skillService.get(5);
		Set<Skill> skillList = employee.getSkillList();
		skillList.add(skill);
		employeeService.save(employee);
	}

	private static void testGetAllCountries() {
		LOGGER.info("Start");
		List<Country> countries = countryService.getAllCountries();
		LOGGER.debug("countries={}", countries);
		LOGGER.info("End");

	}

	private static void getAllCountriesTest() throws CountryNotFoundException {
		LOGGER.info("Start");
		Country country = countryService.findCountryByCode("IN");
		LOGGER.debug("Country:{}", country);
		LOGGER.info("End");
	}

	private static void testAddCountry() {
		Country country = new Country("AA", "ABC");
		countryService.addCountry(country);
		try {
			countryService.findCountryByCode("AA");
		} catch (CountryNotFoundException e) {
		}
	}

	private static void testUpdateCountry() {
		countryService.updateCountry("AA", "BCD");
	}

	private static void testDeleteCountry() {
		countryService.deleteCountry("AA");
	}

	private static void testFindBySubString() {
		LOGGER.info("Start");
		LOGGER.info("Countries{}",countryService.namecontaining("ou"));
		LOGGER.info("Stop");
	}

	private static void testFindBySubStringasc() {
		LOGGER.info("Start");
		LOGGER.info("Countries{}",countryService.namecontainingasc("ou"));
		LOGGER.info("Stop");
	}

	private static void testFindByStartingwith() {
		LOGGER.info("Start");
		LOGGER.info("Countries{}",countryService.namestartswith("Z"));
		LOGGER.info("Stop");
	}

	public static void testGetAllPermanentEmployees() {
		LOGGER.info("Start");
		List<Employee> employees = employeeService.getAllPermanentEmployees();
		LOGGER.debug("Permanent Employees:{}", employees);
		employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
		LOGGER.info("End");
	}
	
	
	public static void testGetAverageSalary() {
		LOGGER.info("Start");
		double sal = employeeService.getAverageSalary(3);
		LOGGER.info("Salary{}",sal);
		LOGGER.info("End");
	}
	
	public static void testGetAllEmployeesNative() {
		LOGGER.info("Start");
		List<Employee> employees = employeeService.getAllEmployeesNative();
		LOGGER.debug("Permanent Employees:{}", employees);
		employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
		LOGGER.info("End");
	}
	public static void testAttemptServiceGetAttempt() {
		LOGGER.info("Start");
		Attempt attempt = attemptService.getAttempt(1, 1);
		LOGGER.debug("Attempt:{}", attempt);
		Set<Attempt_Question> attemptQuestionList = attempt.getAttemptQuestionList();
		LOGGER.debug("AttemptQuestions:{}", attemptQuestionList);
		attemptQuestionList.forEach((x) -> LOGGER.debug("AttemptQuestions:{}", x.getAttemptOptionList()));
		LOGGER.info("End");
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
		attemptService = context.getBean(AttemptService.class);
		 countryService = context.getBean(CountryService.class);
		employeeService = context.getBean(EmployeeService.class);
		 departmentService = context.getBean(DepartmentService.class);
		 skillService = context.getBean(SkillService.class);

		
		  testGetAllCountries(); 
		  try { getAllCountriesTest(); } 
		  catch
		  (CountryNotFoundException e) { e.printStackTrace(); } 
		  testAddCountry();
		  testUpdateCountry(); 
		  testDeleteCountry();
		  testFindBySubString();
		  testFindBySubStringasc();
		  testFindByStartingwith();
		  testGetEmployee();
		  testAddEmployee(); 
		  testUpdateEmployee();
		  testGetDepartment();
		  testAddSkillToEmployee(); 
		  OrmLearnApplication orm = new OrmLearnApplication();
		  
		  try { orm.findByDate1();
		  } catch (ParseException e) {
		  
		  e.printStackTrace(); }
		  orm.findByGoogle(); 
		  orm.findTopOrder();
		  orm.findtopasc();
		 
		testGetAllPermanentEmployees();
		testGetAverageSalary();
		testGetAllEmployeesNative();
		testAttemptServiceGetAttempt();

	}

}
