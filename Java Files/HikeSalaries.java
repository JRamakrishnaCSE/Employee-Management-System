import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class HikeSalaries implements ActionListener
{
	JFrame root;
	JLabel title,sub_title;
	JLabel lb_empID;
	JTextField tf_empID;
	JButton bt_fetch;
	HikeSalaries()
	{
		root = new JFrame("Employee Salaries Increment");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,350);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(80,0,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		sub_title = new JLabel("Hike Employee Salaries");
			sub_title.setBounds(145,30,250,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		lb_empID = new JLabel("Enter Employee ID    :");
			lb_empID.setBounds(20,80,150,30);
			lb_empID.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_empID);

		tf_empID = new JTextField();
			tf_empID.setBounds(170,80,200,30);
			tf_empID.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_empID);

		bt_fetch = new JButton("Fetch");
			bt_fetch.setBounds(380,80,85,30);
			bt_fetch.addActionListener(this);
			bt_fetch.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_fetch);

		lb_empName = new JLabel("Employee Name        : ");
			lb_empName.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_empName);

		output_name = new JLabel("Ramakrishna");
			output_name.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(output_name);

		lb_cur_salary = new JLabel("");
			lb_cur_salary.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_cur_salary);

		output_cur_salary = new JLabel("Rs. 4000000 /-");
			output_cur_salary.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(output_cur_salary);

		tf_hike = new JTextField();
			tf_hike.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_hike);

		bt_add = new JButton("+ Hike");
			bt_add.addActionListener(this);
			bt_add.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_add);

		bt_reset = new JButton("Reset");
			bt_reset.addActionListener(this);
			bt_reset.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_reset);

		bt_update = new JButton("Update");
			bt_update.addActionListener(this);
			bt_update.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_update);
	}
	private int uid;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	private int present_salary,new_salary;

	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if (command.equals("Fetch"))
		{
			try
			{
				if(tf_empID.getText().trim().length()==0)
				{
					throw new Exception("Invalid Employee ID !");
				}
				uid = Integer.parseInt(tf_empID.getText().trim());
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Invalid Employee ID or Couldn't find Data with Such ID !");
				return;
			}
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
				stmt = con.createStatement();

				rs = stmt.executeQuery("select empName from emp where empID="+uid+";");
				rs.next();

				output_name.setText(""+rs.getString(1));

				rs = stmt.executeQuery("select salary from salaries where empID="+uid+";");
				rs.next();

				present_salary = rs.getInt(1);
				new_salary = rs.getInt(1);
				output_cur_salary.setText("Rs. "+rs.getInt(1)+" /-");

				tf_empID.setEditable(false);
				lb_cur_salary.setText("Currently Paying        : ");
				tf_hike.setText("");
				lb_empName.setBounds(20,120,150,30);
				output_name.setBounds(170,120,100,30);
				lb_cur_salary.setBounds(20,160,150,30);
				output_cur_salary.setBounds(170,160,100,30);
				tf_hike.setBounds(300,160,60,30);
				bt_add.setBounds(370,160,80,28);
				bt_reset.setBounds(120,220,100,40);
				bt_update.setBounds(240,220,100,40);
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Invalid Employee ID or Couldn't find Data with Such ID !");
				return;
			}
		}
		else if(command.equals("+ Hike"))
		{
			int temp;
			try
			{
				temp = Integer.parseInt(tf_hike.getText().trim());
				new_salary=new_salary+temp;
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Invalid Hike Amount !");
				return;
			}
			lb_cur_salary.setText("Will  be  Paying            :");
			output_cur_salary.setText("Rs. "+new_salary+" /-");
		}
		else if(command.equals("Reset"))
		{
			tf_empID.setText("");
			tf_hike.setText("");
			tf_empID.setEditable(true);
			lb_empName.setBounds(0,0,0,0);
			output_name.setBounds(0,0,0,0);
			lb_cur_salary.setBounds(0,0,0,0);
			output_cur_salary.setBounds(0,0,0,0);
			tf_hike.setBounds(0,0,0,0);
			bt_add.setBounds(0,0,0,0);
			bt_reset.setBounds(0,0,0,0);
			bt_update.setBounds(0,0,0,0);
		}else if(command.equals("Update"))
		{
			try
			{
				int ec = stmt.executeUpdate("update salaries set salary="+new_salary+" where empID="+uid+";");
				if(ec==1)
				{
					lb_cur_salary.setText("Currently Paying        : ");
					JOptionPane.showMessageDialog(null,"Salary Updated Successfully !");
					return;
				}else {
					JOptionPane.showMessageDialog(null,"Unable to Update right now. Please try later !");
					return;
				}
			}catch(Exception e){

			}
		}
	}
	JLabel lb_empName,output_name,lb_cur_salary,output_cur_salary;
	JTextField tf_hike;
	JButton bt_add,bt_update,bt_reset;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new HikeSalaries();
			}
		});
	}
}