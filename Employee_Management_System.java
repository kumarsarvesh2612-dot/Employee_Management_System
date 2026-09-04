
import java.util.*;

public class Employee_Management_System {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        EmployeeManager mgr = new EmployeeManager();

        mgr.addDepartment("Engineering");
        mgr.addDepartment("HR");
        mgr.addEmployee("Ram", "Engineer", 75000);
        mgr.addEmployee("Ramesh", "HR Manager", 65000);

        while (true) {
            printMenu();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1":
                    addEmployee(mgr);
                    break;
                case "2":
                    listEmployees(mgr);
                    break;
                case "3":
                    searchEmployees(mgr);
                    break;
                case "4":
                    updateEmployee(mgr);
                    break;
                case "5":
                    removeEmployee(mgr);
                    break;
                case "6":
                    addDepartment(mgr);
                    break;
                case "7":
                    listDepartments(mgr);
                    break;
                case "8":
                    assignDepartment(mgr);
                    break;
                case "9":
                    listByDepartment(mgr);
                    break;
                case "10":
                    raiseSalary(mgr);
                    break;
                case "11":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }
        private static void printMenu() {
            System.out.println("=== Employee Management System ===");
            System.out.println("1. Add employee");
            System.out.println("2. List employees");
            System.out.println("3. Search employees by name");
            System.out.println("4. Update employee");
            System.out.println("5. Remove employee");
            System.out.println("6. Add department");
            System.out.println("7. List departments");
            System.out.println("8. Assign department to employee");
            System.out.println("9. List employees by department");
            System.out.println("10. Raise salary");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
        }

        private static void addEmployee(EmployeeManager mgr) {
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Position: ");
            String pos = sc.nextLine().trim();
            System.out.print("Salary: ");
            double sal = parseDoubleInput(sc.nextLine().trim(), 0);
            Employee e = mgr.addEmployee(name, pos, sal);
            System.out.println("Added: " + e);
        }

        private static void listEmployees(EmployeeManager mgr) {
            System.out.println("Employees:");
            for (Employee e : mgr.listEmployees())
                System.out.println("  " + e + (e.getDepartmentId() != null ? (" [dept:" + e.getDepartmentId() + "]") : ""));
        }

        private static void searchEmployees(EmployeeManager mgr) {
            System.out.print("Query: ");
            String q = sc.nextLine().trim();
            List<Employee> res = mgr.searchByName(q);
            if (res.isEmpty()) System.out.println("No matches.");
            else for (Employee e : res) System.out.println("  " + e);
        }

        private static void updateEmployee(EmployeeManager mgr) {
            try {
                System.out.print("Employee ID to update: ");
                int id = Integer.parseInt(sc.nextLine().trim());
                System.out.print("New name (leave blank to keep): ");
                String name = sc.nextLine();
                System.out.print("New position (leave blank to keep): ");
                String pos = sc.nextLine();
                System.out.print("New salary (leave blank to keep): ");
                String salstr = sc.nextLine().trim();
                Double sal = salstr.isEmpty() ? null : Double.parseDouble(salstr);
                boolean ok = mgr.updateEmployee(id, name, pos, sal);
                System.out.println(ok ? "Updated." : "Employee not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }

        private static void removeEmployee(EmployeeManager mgr) {
            try {
                System.out.print("Employee ID to remove: ");
                int id = Integer.parseInt(sc.nextLine().trim());
                boolean ok = mgr.removeEmployee(id);
                System.out.println(ok ? "Removed." : "Employee not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }

        private static void addDepartment(EmployeeManager mgr) {
            System.out.print("Department name: ");
            String name = sc.nextLine().trim();
            Department d = mgr.addDepartment(name);
            System.out.println("Added: " + d);
        }

        private static void listDepartments(EmployeeManager mgr) {
            System.out.println("Departments:");
            for (Department d : mgr.listDepartments()) System.out.println("  " + d);
        }

        private static void assignDepartment(EmployeeManager mgr) {
            try {
                System.out.print("Employee ID: ");
                int eid = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Department ID: ");
                int did = Integer.parseInt(sc.nextLine().trim());
                boolean ok = mgr.assignDepartment(eid, did);
                System.out.println(ok ? "Assigned." : "Check IDs.");
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }

        private static void listByDepartment(EmployeeManager mgr) {
            try {
                System.out.print("Department ID: ");
                int did = Integer.parseInt(sc.nextLine().trim());
                List<Employee> list = mgr.listByDepartment(did);
                if (list.isEmpty()) System.out.println("No employees in this department.");
                else for (Employee e : list) System.out.println("  " + e);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }

        private static void raiseSalary(EmployeeManager mgr) {
            try {
                System.out.print("Employee ID: ");
                int id = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Amount to raise (positive number): ");
                double amt = Double.parseDouble(sc.nextLine().trim());
                boolean ok = mgr.raiseSalary(id, amt);
                System.out.println(ok ? "Salary updated." : "Employee not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number.");
            }
        }

        private static double parseDoubleInput(String s, double def) {
            if (s.isEmpty()) return def;
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException ex) {
                return def;
            }
        }
    
static class Employee {

    private static int nextId = 1;

    private int id;
    private String name;
    private String position;
    private double salary;
    private Integer departmentId;

    public Employee(
            String name,
            String position,
            double salary) {

        this.id = nextId++;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(
            Integer departmentId) {

        this.departmentId = departmentId;
    }

    public void setName(String name) {

        if (!name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public void setPosition(String position) {

        if (!position.trim().isEmpty()) {
            this.position = position;
        }
    }

    public void setSalary(double salary) {

        this.salary = salary;
    }

    public void raiseSalary(double amount) {

        salary += amount;
    }

    @Override
    public String toString() {

        return "ID: " + id
                + ", Name: " + name
                + ", Position: " + position
                + ", Salary: " + salary;
    }
}

static class Department {

    private static int nextId = 1;

    private int id;
    private String name;

    public Department(String name) {

        this.id = nextId++;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    @Override
    public String toString() {

        return "ID: " + id
                + ", Department: " + name;
    }
}



static class EmployeeManager {

    private List<Employee> employees =
            new ArrayList<>();

    private List<Department> departments =
            new ArrayList<>();


    public Employee addEmployee(
            String name,
            String position,
            double salary) {

        Employee e =
                new Employee(name, position, salary);

        employees.add(e);

        return e;
    }


    public List<Employee> listEmployees() {

        return employees;
    }


    public List<Employee> searchByName(
            String query) {

        List<Employee> result =
                new ArrayList<>();

        for (Employee e : employees) {

            if (e.toString()
                    .toLowerCase()
                    .contains(query.toLowerCase())) {

                result.add(e);
            }
        }

        return result;
    }


    public boolean updateEmployee(
            int id,
            String name,
            String position,
            Double salary) {

        for (Employee e : employees) {

            if (e.getId() == id) {

                e.setName(name);
                e.setPosition(position);

                if (salary != null) {

                    e.setSalary(salary);
                }

                return true;
            }
        }

        return false;
    }


    public boolean removeEmployee(int id) {

        return employees.removeIf(
                e -> e.getId() == id
        );
    }



    public Department addDepartment(
            String name) {

        Department d =
                new Department(name);

        departments.add(d);

        return d;
    }

    public List<Department> listDepartments() {

        return departments;
    }

    public boolean assignDepartment(
            int employeeId,
            int departmentId) {

        Employee employee = null;
        Department department = null;


        for (Employee e : employees) {

            if (e.getId() == employeeId) {

                employee = e;
                break;
            }
        }


        for (Department d : departments) {

            if (d.getId() == departmentId) {

                department = d;
                break;
            }
        }


        if (employee != null &&
                department != null) {

            employee.setDepartmentId(
                    departmentId
            );

            return true;
        }

        return false;
    }

    public List<Employee> listByDepartment(
            int departmentId) {

        List<Employee> result =
                new ArrayList<>();

        for (Employee e : employees) {

            if (e.getDepartmentId() != null &&
                    e.getDepartmentId() == departmentId) {

                result.add(e);
            }
        }

        return result;
    }

    public boolean raiseSalary(
            int id,
            double amount) {

        if (amount <= 0) {
            return false;
        }

        for (Employee e : employees) {

            if (e.getId() == id) {

                e.raiseSalary(amount);

                return true;
            }
        }

        return false;
    }
}
}