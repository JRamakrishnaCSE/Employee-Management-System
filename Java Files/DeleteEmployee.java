import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DeleteEmployee implements ActionListener
{
	JFrame root;
	JLabel idLabel,nameLabel,joinLabel;
	JLabel lb1,lb2;
	DeleteEmployee()
	{
		root = new JFrame("Delete Employees");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(490,500);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		lb1 = new JLabel("Millennium Software Solutions");
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,22));
			lb1.setBounds(85,10,300,30);
			root.add(lb1);

		lb2 = new JLabel("Manage Employees");
			lb2.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			lb2.setBounds(150,40,300,30);
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
	Connection con;
	Statement stmt;
	public void fetch()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/mill";
			con = DriverManager.getConnection(url,"root","Raakhi@123");
			stmt = con.createStatement();

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

				JButton tempBt = new JButton("Delete");
					tempBt.setBounds(375,i+30,80,28);
					tempBt.setFont(new Font("Bahnschrift",Font.PLAIN,15));
					tempBt.addActionListener(this);
					tempBt.setActionCommand(""+rs.getInt(1));
					root.add(tempBt);

				i=i+40;
			}
		}
		catch(Exception e)
		{
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		int id=Integer.parseInt(ae.getActionCommand());
		try
		{
			int k = JOptionPane.showConfirmDialog(null,"Do you Confirm to delete ?");
			if(k==1 || k==2) return;// yes=0,no=1,cancel=2;
			int ec = stmt.executeUpdate("delete from emp_logs where empID="+id+";");
			
			stmt.executeUpdate("delete from attendance where empID="+id+";");
			System.out.println("Here");
			stmt.executeUpdate("delete from account where empID="+id+";");
			stmt.executeUpdate("delete from address where empID="+id+";");
			stmt.executeUpdate("delete from requests where empID="+id+";");
			stmt.executeUpdate("delete from salaries where empID="+id+";");
			int ecc = stmt.executeUpdate("delete from emp where empID="+id+";");
			JOptionPane.showMessageDialog(null,"Employee Record Has been Deleted!");
			return;
		}catch(Exception e){}
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new DeleteEmployee();
			}
		});
	}
}