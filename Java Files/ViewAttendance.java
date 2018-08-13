import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class ViewAttendance implements ActionListener
{
	JFrame root;
	JLabel title,sub_title;
	JLabel lb_empID,lb_empName,lb_date,lb_status;
	ViewAttendance()
	{
		root = new JFrame("Login Area");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(510,500);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setFont(new Font("Bahnschrift",Font.PLAIN,22));
			title.setBounds(65,5,300,30);
			root.add(title);

		sub_title = new JLabel("Attendance");
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			sub_title.setBounds(160,35,300,30);
			root.add(sub_title);

		lb_empID = new JLabel("Employee ID");
			lb_empID.setBounds(30,80,95,30);
			lb_empID.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_empID);

		lb_empName = new JLabel("Employee Name");
			lb_empName.setBounds(150,80,155,30);
			lb_empName.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_empName);

		lb_date = new JLabel("Date");
			lb_date.setBounds(310,80,100,30);
			lb_date.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_date);

		lb_status = new JLabel("Status");
			lb_status.setBounds(410,80,80,30);
			lb_status.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_status);

		fetchData();
	}
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private void fetchData()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();

			rs = stmt.executeQuery("select attendance.empID,empName,date,status from attendance,emp where attendance.empID=emp.empID and attendance.date=date(now()) order by status desc;");
			int i=40;
			while(rs.next())
			{
				JLabel lb_temp_id = new JLabel(""+rs.getInt(1));
					lb_temp_id.setBounds(30,i+80,95,30);
					lb_temp_id.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(lb_temp_id);

				JLabel lb_temp_name = new JLabel(""+rs.getString(2));
					lb_temp_name.setBounds(150,i+80,95,30);
					lb_temp_name.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(lb_temp_name);

				JLabel lb_temp_date = new JLabel(""+rs.getDate(3));
					lb_temp_date.setBounds(310,i+80,95,30);
					lb_temp_date.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(lb_temp_date);
				JLabel lb_temp_status = new JLabel(""+rs.getString(4));
					lb_temp_status.setBounds(410,i+80,80,30);
					lb_temp_status.setFont(new Font("Bahnschrift",Font.PLAIN,16));
					root.add(lb_temp_status);

				i+=40;
			}
		}catch(Exception e){}
	}
	public void actionPerformed(ActionEvent ae)
	{

	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ViewAttendance();
			}
		});
	}
}