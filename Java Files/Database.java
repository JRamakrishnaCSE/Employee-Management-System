import java.util.*;
import java.sql.*;
import javax.swing.*;
public class Database
{
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public boolean registerNewEmp(Address obj)
	{
		int newid;
		try
		{
			rs = stmt.executeQuery("select max(empID)+1 from emp;");
			rs.next();
			newid = rs.getInt(1);

			String query = "insert into emp(empID,password,empName,email,joiningDate,department) ";
			query = query+ "values("+newid+",sha2(12345,256),'"+obj.fullname+"','"+obj.email+"',";
			query = query + "'"+obj.joinDate+"','"+obj.dept+"');";

			// System.out.println("here");


			int ec = stmt.executeUpdate(query);
			// System.out.println(ec);

			if(ec==1)
			{
				stmt.executeUpdate("insert into emp_logs(empID) values("+newid+");");

				query = "insert into address values("+newid+","+obj.phone+",'"+obj.dno+"','"+obj.village+"',";
				query = query + "'"+obj.mandal+"','"+obj.district+"','"+obj.state+"',"+obj.pincode+");";
				stmt.executeUpdate(query);

				query = "insert into account(empID) values("+newid+");";
				stmt.executeUpdate(query);

				query = "insert into salaries(empID) values("+newid+");";
				stmt.executeUpdate(query);

				stmt.executeUpdate("insert into attendance values("+newid+",date(now()),'absent')");
				JOptionPane.showMessageDialog(null,"You have successfully Registered !\n Your MSS Employee ID is "+newid);
				return true;
			}
		}catch(Exception e)
		{
			System.out.println(""+e);
			if(e.getMessage().indexOf("Data truncation: Incorrect date value:")!=-1)
			{
				String mess = "Invalid Joining Date ! or \nDate format should be in the form of (YYYY-MM-DD)";
				JOptionPane.showMessageDialog(null,mess);
				return false;
			}
			// JOptionPane.showMessageDialog(null,""+e);
		}
		return false;
	}
	Database()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
		}catch(Exception e){}
	}
}