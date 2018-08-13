import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DisplayAllEmps
{
	JFrame root;
	JLabel idLabel,nameLabel,joinLabel;
	JLabel lb1,lb2;
	DisplayAllEmps()
	{
		root = new JFrame("Display Employees");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(390,400);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		lb1 = new JLabel("Millennium Software Solutions");
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,22));
			lb1.setBounds(40,10,300,30);
			root.add(lb1);

		lb2 = new JLabel("Employees");
			lb2.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			lb2.setBounds(140,40,300,30);
			root.add(lb2);

		idLabel = new JLabel("Employee ID");
			idLabel.setBounds(30,80,100,30);
			idLabel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(idLabel);

		nameLabel = new JLabel("Employee Name");
			nameLabel.setBounds(140,80,130,30);
			nameLabel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(nameLabel);

		joinLabel = new JLabel("Joined On");
			joinLabel.setBounds(275,80,150,30);
			joinLabel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(joinLabel);

		fetch();

	}
	public void fetch()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/mill";
			Connection con = DriverManager.getConnection(url,"root","Raakhi@123");
			Statement stmt = con.createStatement();

			String query="select empID,empName,joiningDate from emp;";
			ResultSet rs = stmt.executeQuery(query);
			int i=80;
			while(rs.next())
			{
				JLabel tempID = new JLabel(""+rs.getInt(1));
					tempID.setBounds(30,i+30,100,30);
					tempID.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(tempID);
				
				JLabel tempName = new JLabel(rs.getString(2));
					tempName.setBounds(140,i+30,100,30);
					tempName.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(tempName);
				JLabel tempDate = new JLabel(""+rs.getDate(3));
					tempDate.setBounds(275,i+30,100,30);
					tempDate.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(tempDate);
				i=i+30;
			}
			try{
				con.close();
			}
			catch(Exception e){}
		}
		catch(Exception e)
		{
		}
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new DisplayAllEmps();
			}
		});
	}
}