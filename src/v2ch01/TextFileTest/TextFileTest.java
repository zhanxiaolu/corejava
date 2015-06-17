package v2ch01.TextFileTest;

import java.io.*;
import java.util.*;

/**
 * 文件输出示例，使用printwriter和scanner实现读写文件
 * 
 * @location page14
 * @version 1.12 2007-06-22
 * @author Cay Horstmann
 */
public class TextFileTest {
	public static void main(String[] args) {
		Employee[] staff = new Employee[3];

		staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
		staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

		try {
			// save all employee records to the file employee.dat
			String dataPath = TextFileTest.class.getResource("").getPath()
					+ "employee.dat";
			PrintWriter out = new PrintWriter(dataPath);
			writeData(staff, out);
			out.close();

			// retrieve all records into a new array
			Scanner in = new Scanner(new FileReader(dataPath));
			Employee[] newStaff = readData(in);
			in.close();

			// print the newly read employee records
			for (Employee e : newStaff)
				System.out.println(e);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Writes all employees in an array to a print writer
	 * 
	 * @param employees
	 *            an array of employees
	 * @param out
	 *            a print writer
	 */
	private static void writeData(Employee[] employees, PrintWriter out)
			throws IOException {
		// write number of employees
		out.println(employees.length);

		for (Employee e : employees)
			e.writeData(out);
	}

	/**
	 * Reads an array of employees from a scanner
	 * 
	 * @param in
	 *            the scanner
	 * @return the array of employees
	 */
	private static Employee[] readData(Scanner in) {
		// retrieve the array size
		int n = in.nextInt();
		in.nextLine(); // consume newline

		Employee[] employees = new Employee[n];
		for (int i = 0; i < n; i++) {
			employees[i] = new Employee();
			employees[i].readData(in);
		}
		return employees;
	}
}

/**
 * 客户类:name、salary、hireDay(雇佣日期)
 * 
 * @author Administrator
 * 
 */
class Employee {
	public Employee() {
	}

	public Employee(String n, double s, int year, int month, int day) {
		name = n;
		salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		hireDay = calendar.getTime();
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public Date getHireDay() {
		return hireDay;
	}

	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

	public String toString() {
		return getClass().getName() + "[name=" + name + ",salary=" + salary
				+ ",hireDay=" + hireDay + "]";
	}

	/**
	 * Writes employee data to a print writer
	 * 
	 * @param out
	 *            the print writer
	 */
	public void writeData(PrintWriter out) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(hireDay);
		out.println(name + "|" + salary + "|" + calendar.get(Calendar.YEAR)
				+ "|" + (calendar.get(Calendar.MONTH) + 1) + "|"
				+ calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Reads employee data from a buffered reader
	 * 
	 * @param in
	 *            the scanner
	 */
	public void readData(Scanner in) {
		String line = in.nextLine();
		String[] tokens = line.split("\\|");
		name = tokens[0];
		salary = Double.parseDouble(tokens[1]);
		int y = Integer.parseInt(tokens[2]);
		int m = Integer.parseInt(tokens[3]);
		int d = Integer.parseInt(tokens[4]);
		GregorianCalendar calendar = new GregorianCalendar(y, m - 1, d);
		hireDay = calendar.getTime();
	}

	private String name;
	private double salary;
	private Date hireDay;
}