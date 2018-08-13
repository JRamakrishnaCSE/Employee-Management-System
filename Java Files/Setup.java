import java.util.*;
import java.sql.*;
import javax.swing.*;

public class Setup 
{
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	Setup()
	{
		try
		{
			System.out.println("Please wait ...");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();

			// for emp table
			String query = "create table if not exists emp(empID int,password varchar(65),empName varchar(20),";
			query =  query+"email varchar(40),joiningDate date,department varchar(20),primary key(empID));";

			stmt.executeUpdate(query);
			
			// for admin table
			query = "create table if not exists admin(adminID int,password varchar(65),adminName varchar(20),";
			query =  query+"email varchar(40),joiningDate date,department varchar(20),primary key(adminID));";

			stmt.executeUpdate(query);

			System.out.println("We are Dealing with Database tables !");

			// for admin logs
			query = "create table if not exists admin_logs(adminID int,last_login_date date,last_login_time time,";
			query =  query + "last_login_failed_date date,last_login_failed_time time,password_changed_on date,";
			query = query + "password_changed_time time,foreign key(adminID) references admin(adminID));";

			stmt.executeUpdate(query);

			// for emp logs
			query = "create table if not exists emp_logs(empID int,last_login_date date,last_login_time time,";
			query =  query + "last_login_failed_date date,last_login_failed_time time,password_changed_on date,";
			query = query + "password_changed_time time,foreign key(empID) references emp(empID));";

			stmt.executeUpdate(query);

			try
			{
				stmt.executeUpdate("insert into emp(empID) values(1001);");
				stmt.executeUpdate("insert into emp_logs(empID) values(1001);");
			}catch(Exception e){
				System.out.println("1001 is Already existed!");
			}

			try
			{
				stmt.executeUpdate("insert into admin(adminID) values(101);");
				stmt.executeUpdate("insert into admin_logs(adminID) values(101);");
			}catch(Exception e){
				System.out.println("101 is Already existed!");
			}

			// for account table
			query = "create table if not exists account(empID int,acc_num bigint,acc_holder varchar(30),";
			query  = query + "bank_name varchar(30),ifsc varchar(20),foreign key(empID) references emp(empID));";

			stmt.executeUpdate(query);

			// for address table
			query = "create table if not exists address(empID int,phone bigint unique,DNO varchar(10),village varchar(30),";
			query = query + "mandal varchar(30),district varchar(30),state varchar(30),pincode int,foreign key(empID) references emp(empID));";

			stmt.executeUpdate(query);

			// for attendance table
			query = "create table if not exists attendance(empID int,date date,";
			query = query+ "status enum('present','absent'),foreign key(empID) references emp(empID),primary key(empID,date));";

			stmt.executeUpdate(query);

			// for deleted_emps table
			query = "create table if not exists deleted_emp(empID int,phone bigint,foreign key(empID) references emp(empID),";
			query = query + "foreign key(phone) references address(phone));";

			stmt.executeUpdate(query);

			// for notification table
			query = "create table if not exists notification(notice_id int auto_increment,subject varchar(90),";
			query = query + "posted_on datetime,primary key(notice_id));";
			stmt.executeUpdate(query);

			//  for notice_descriptions
			query = "create table if not exists notice_desc(notice_id int,description varchar(50),";
			query = query + "foreign key(notice_id) references notification(notice_id));";
			stmt.executeUpdate(query);

			// for requests table
			query = "create table if not exists requests(empID int,requestID varchar(19),requested_on datetime,status enum('pending','resolved'),";
			query = query + "subject varchar(60),description varchar(150),foreign key(empID) references emp(empID));";
			stmt.executeUpdate(query);

			query = "create table if not exists metadata(newID int primary key,added_by int,added_on datetime,";
			query = query + "foreign key(newID) references admin(adminID),";
			query = query + "foreign key(added_by) references admin(adminID));";

			stmt.executeUpdate(query);

			query = "create table if not exists salaries(empID int primary key,salary int,foreign key(empID) references emp(empID));";

			System.out.println("Executed Successfully!");

			try
			{
				query = "select empID from emp;";
				rs = stmt.executeQuery(query);
				int arr[] = new int[100];
				int i=0,j=0;
				while(rs.next())
				{
					arr[i]=rs.getInt(1);
					i++;
				}
				while(j<i)
				{
					query = "insert into attendance values("+arr[j]+",date(now()),'absent');";
					stmt.executeUpdate(query);
					j++;
				}
			}catch(Exception e)
			{
				System.out.println("Already Inserted !"+e);
			}
			con.close();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		new Setup();
	}
}