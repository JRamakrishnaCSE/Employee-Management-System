import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class ViewRequests implements ActionListener
{
	Thread t;
	JFrame root;
	JLabel title,sub_title,lb_empID,lb_empName,lb_date,lb_status,lb_subject;
	ViewRequests()
	{
		root = new JFrame("Requests");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(700,600);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setFont(new Font("Bahnschrift",Font.PLAIN,24));
			title.setBounds(150,5,400,30);
			root.add(title);

		sub_title = new JLabel("Requests or Suggestions");
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,19));
			sub_title.setBounds(205,35,300,30);
			root.add(sub_title);

		lb_empID = new JLabel("Employee ID");
			lb_empID.setBounds(30,80,95,30);
			lb_empID.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_empID);

		lb_subject = new JLabel("Subject");
			lb_subject.setBounds(150,80,205,30);
			lb_subject.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_subject);

		lb_date = new JLabel("Date on Requested");
			lb_date.setBounds(360,80,150,30);
			lb_date.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_date);

		lb_status = new JLabel("Status");
			lb_status.setBounds(520,80,80,30);
			lb_status.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_status);

		fetchData();
	}
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	public void fetchData()
	{
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();

			rs = stmt.executeQuery("select requests.empID,subject,requested_on,status,requestID from requests,emp where requests.empID=emp.empID order by status asc;");
			int i=40;
			while(rs.next())
			{
				JLabel temp_empID = new JLabel(""+rs.getInt(1));
					temp_empID.setBounds(30,i+80,65,30);
					temp_empID.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_empID);
				
				JLabel temp_subject = new JLabel(""+rs.getString(2));
					temp_subject.setBounds(150,i+80,255,30);
					temp_subject.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_subject);

				JLabel temp_date = new JLabel(rs.getDate(3)+"  "+rs.getTime(3));
					temp_date.setBounds(360,i+80,150,30);
					temp_date.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_date);

				JLabel temp_status = new JLabel(""+rs.getString(4));
					temp_status.setBounds(520,i+80,80,30);
					temp_status.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_status);

				JButton bt = new JButton("View");
					bt.addActionListener(this);
					bt.setActionCommand(rs.getString(5));
					bt.setBounds(600,i+84,90,25);
					bt.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(bt);

				i=i+40;
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		// JOptionPane.showMessageDialog(null,""+ae.getActionCommand());
		new ViewRequestDescription(""+ae.getActionCommand().trim());
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ViewRequests();
			}
		});
	}
}