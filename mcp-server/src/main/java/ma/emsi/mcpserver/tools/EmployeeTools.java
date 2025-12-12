package ma.emsi.mcpserver.tools;

import ma.emsi.mcpserver.model.Employee;
import org.springframework.ai.mcp.server.McpTool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeTools {

    // Mock data - in production, this would come from a database
    private final List<Employee> employees = List.of(
            new Employee("Hassan", 12000.0, 4),
            new Employee("Fatima", 15000.0, 6),
            new Employee("Omar", 9000.0, 2),
            new Employee("Aisha", 18000.0, 8));

    @McpTool(name = "getEmployee", description = "Get details about an employee given their name. Returns employee information including name, salary, and seniority.")
    public Employee getEmployee(String name) {
        return employees.stream()
                .filter(emp -> emp.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(new Employee("Unknown", 0.0, 0));
    }

    @McpTool(name = "getAllEmployees", description = "Get a list of all employees in the company with their details (name, salary, seniority).")
    public List<Employee> getAllEmployees() {
        return employees;
    }
}
