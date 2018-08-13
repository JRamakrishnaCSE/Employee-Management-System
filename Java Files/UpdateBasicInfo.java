import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class UpdateBasicInfo implements ActionListener
{
	JFrame root;

	JTextField tf_empID,tf_empName,tf_email,tf_joiningDate,tf_dept;
	JLabel lb_empID,lb_empName,lb_email,lb_joiningDate,lb_dept;

	JButton bt_fetch,bt_cancel,bt_reset,bt_update;
	JLabel title,sub_title;

	UpdateBasicInfo()
	{
		root = new JFrame("Update Basic Information");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,400);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(80,0,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		sub_title = new JLabel("Update Basic Information");
			sub_title.setBounds(135,30,250,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		lb_empID = new JLabel("Employee ID                  :");
			lb_empID.setBounds(40,70,150,30);
			lb_empID.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_empID);

		tf_empID = new JTextField();
			tf_empID.setBounds(190,70,190,30);
			tf_empID.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_empID);

		bt_fetch = new JButton("Fetch");
			bt_fetch.setBounds(400,70,80,30);
			bt_fetch.addActionListener(this);
			bt_fetch.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(bt_fetch);

		lb_empName = new JLabel("Employee Name      :");
			lb_empName.setBounds(40,110,150,30);
			lb_empName.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_empName);

		tf_empName = new JTextField();
			tf_empName.setBounds(190,110,190,30);
			tf_empName.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_empName);

		lb_email = new JLabel("E-mail ID                            :");
			lb_email.setBounds(40,150,150,30);
			lb_email.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_email);

		tf_email = new JTextField();
			tf_email.setBounds(190,150,190,30);
			tf_email.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_email);

		lb_joiningDate = new JLabel("Joining Date                 :");
			lb_joiningDate.setBounds(40,190,150,30);
			lb_joiningDate.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_joiningDate);

		tf_joiningDate = new JTextField();
			tf_joiningDate.setBounds(190,190,190,30);
			tf_joiningDate.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_joiningDate);

		lb_dept = new JLabel("Department                  :");
			lb_dept.setBounds(40,230,150,30);
			lb_dept.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_dept);

		tf_dept = new JTextField();
			tf_dept.setBounds(190,230,190,30);
			tf_dept.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_dept);

		bt_cancel = new JButton("Cancel");
			bt_cancel.setBounds(70,285,100,40);
			bt_cancel.addActionListener(this);
			bt_cancel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_cancel);

		bt_reset = new JButton("Reset");
			bt_reset.setBounds(190,285,100,40);
			bt_reset.addActionListener(this);
			bt_reset.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_reset);

		bt_update = new JButton("Update");
			bt_update.setBounds(310,285,100,40);
			bt_update.addActionListener(this);
			bt_update.setEnabled(false);
			bt_update.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_update);

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Unable to connnect to the Database !");
		}
	}
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	int uid=0;
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Fetch"))
		{
			try
			{
				uid = Integer.parseInt(""+tf_empID.getText().trim());
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Please Enter a Valid Employee ID !");
				return;
			}
			String query = "select empName,email,joiningDate,department from emp where empID="+uid+";";
			try
			{
				rs = stmt.executeQuery(query);
				rs.next();
				
				tf_empName.setText(""+rs.getString(1));
				tf_email.setText(""+rs.getString(2));
				tf_joiningDate.setText(""+rs.getDate(3));
				tf_dept.setText(""+rs.getString(4));

				tf_empID.setEditable(false);
				bt_update.setEnabled(true);
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"No Data Found with such Employee ID !");
				tf_empID.setText("");
				tf_empID.setEditable(true);
			}
		}else if(command.equals("Cancel") || command.equals("Enough"))
		{
			root.dispose();
		}else if(command.equals("Reset"))
		{
			clearAllFields();
			bt_update.setEnabled(false);
			tf_empID.setEditable(true);
		}else if(command.equals("Update"))
		{
			updateDetails(uid);
		}
	}

	private void updateDetails(int uid)
	{
		String empName;
		String email;
		String joiningDate;
		String department;
		String query;
		// validating name
		try
		{
			empName = tf_empName.getText().trim();
			if(!(empName.length()>2))
			{
				JOptionPane.showMessageDialog(null,"Employee Name is too small !");
				return;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Employee Name Should not be empty !");
			return;
		}
		//validating email
		try
		{
			email = tf_email.getText().trim();
			if(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")){}
			else
			{
				JOptionPane.showMessageDialog(null,"Please Enter a valid Email ID !\n");
				return;
			}
			if(!(email.length()>12))
			{
				JOptionPane.showMessageDialog(null,"Email should have at least 13 Characters !");
				return;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Email Should not be empty maintenance !");
			return;
		}
		// validating date
		try
		{
			joiningDate = tf_joiningDate.getText().trim();
			if(!(joiningDate.length()==10)){
				JOptionPane.showMessageDialog(null,"Joining Date format should be ( YYYY-MM-DD ) !");
				return;
			}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Joining Date should not be empty !");
			return;
		}
		// validating department
		try
		{
			department = tf_dept.getText().trim();
			if(!
				(
					(department.equalsIgnoreCase("Crew")) ||
					(department.equalsIgnoreCase("admin")) ||
					(department.equalsIgnoreCase("maintenance"))
				)
			)
			{
				JOptionPane.showMessageDialog(null,"Department is not Valid !");
				return;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Department field Should not be empty !");
			return;
		}

		query = "update emp set empName='"+empName+"',email='"+email+"',joiningDate='"+joiningDate+"',";
		query = query + "department='"+department+"' where empID="+uid+";";

		try
		{
			int ec = stmt.executeUpdate(query);
			if(ec==1)
			{
				JOptionPane.showMessageDialog(null,"Data Updated Successfully !");
				clearAllFields();
				bt_update.setEnabled(false);
				bt_cancel.setText("Enough");
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Unable to Update Right now !\n     Please try later !");
				return;
			}
		}catch(Exception e){

		}
	}

	private void clearAllFields()
	{
		tf_empID.setEditable(true);
		tf_empID.setText("");
		tf_empName.setText("");
		tf_email.setText("");
		tf_joiningDate.setText("");
		tf_dept.setText("");
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new UpdateBasicInfo();
			}
		});
	}
}