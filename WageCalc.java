/**
	Themata Java 2014
	DO NOT ATTEMPT TO IMPORT TO ECLIPSE
	Kyriakos Giannakis
*/

public class WageCalc{
	private static RandomAccessFile f_emp;
	private static RandomAccessFile f_data;

	// List with all the employees.
	private List<Employee> employees = new ArrayList<Employee>();


	private static String readString(int length, RandomAccessFile rafGiven) throws IOException{
		byte b[] = new byte[length];
		rafGiven.readFully(b);
		return new String(b);
	}


	/**
		Represents an employee and his details.
	*/
	private class Employee{

		private String name, address, tel;
		private int property, children;
		private double wage;

		// Getters, setters... Do them urself :P
	}

	public static void main(String[] args) throws IOException{
		
		f_emp = new RandomAccessFile("employee.dbs","rw");
		f_data = new RandomAccessFile("data.dbs","rw");

		int index = 1;
		try{
			while(true){
				// Move the cursor to the employee record,
				// both on the employee file and data file.
				f_emp.seek((index*140) - 1);
				f_data.seek((index*16) - 1);

				Employee enew = new Employee();

				// Read Employee.dbs and extract the data to the employee object

				enew.setName(readString(60, f_emp));  // Name
				enew.setAddress(readString(50, f_emp));  // Address
				enew.setTel(readString(30, f_emp));

				// Read Data.dbs...

				enew.setProperty(f_data.readInt());  // Property
				enew.setChildren(f_data.readInt());  // Children
				enew.setWage(f_data.readDouble());  // Wage

				employees.add(enew);
				index++;
			}
		} catch(EOFException e){}

		Scanner s = new Scanner(System.in);
		System.out.println("Please give me K1: ");
		int k1 = s.nextInt();
		System.out.println("Please give me K2: ");
		int k2 = s.nextInt();

		// Too bored to check for invalid input

		try{
			FileWriter fw = new FileWriter(new File("raise.txt"));
			for (int i = k1 - 1; i < k2 - 1; i++){
				Employee e = employees.get(i);
				if (e.getChildren() == 2 && e.getWage() < 2000.0 && e.getProperty() == 0){
					fw.write(String.valueOf(i +1) + ",");  // Employee ID
					fw.write(e.getName() + ",");  // Name
					fw.write(String.valueOf(e.getChildren() + ","));  // Children
					fw.write(String.valueOf(e.getWage() + (0.1 * e.getWage())));  // New Wage (wage + wage*0.1)
					fw.write(System.lineSeparator());
				}
			}
			fw.close();
		} catch (IOException e){}
		
		

		
	}
}

