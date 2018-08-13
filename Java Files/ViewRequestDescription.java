import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class ViewRequestDescription
{
	Thread t;
	JFrame root;
	JLabel title,sub_title,lb_empID,lb_empName,lb_date,lb_status,lb_subject;
	JLabel lb_desc,lb,lb_requestID;
	ViewRequestDescription(String requestID)
	{
		root = new JFrame("Request Description");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(650,500);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setFont(new Font("Bahnschrift",Font.PLAIN,24));
			title.setBounds(150,5,400,30);
			root.add(title);

		sub_title = new JLabel("Request Description");
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,19));
			sub_title.setBounds(225,35,300,30);
			root.add(sub_title);

		lb = new JLabel("Request Details   : ");
			lb.setBounds(30,80,155,30);
			lb.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb);

		lb_empID = new JLabel("Employee ID                       : ");
			lb_empID.setBounds(60,120,175,30);
			lb_empID.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_empID);

		lb_requestID = new JLabel("Request ID                            :");
			lb_requestID.setBounds(60,160,205,30);
			lb_requestID.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_requestID);

		lb_empName = new JLabel("Employee Name              : ");
			lb_empName.setBounds(60,200,180,30);
			lb_empName.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_empName);

		lb_date = new JLabel("Date on Requested         : ");
			lb_date.setBounds(60,240,170,30);
			lb_date.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_date);

		lb_status = new JLabel("Status                                       : ");
			lb_status.setBounds(60,280,205,30);
			lb_status.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_status);

		lb_subject = new JLabel("Subject                                    : ");
			lb_subject.setBounds(60,320,205,30);
			lb_subject.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_subject);

		lb_desc = new JLabel("Description                          : ");
			lb_desc.setBounds(60,360,205,30);
			lb_desc.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_desc);

		fetchData(requestID);
	}
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	public void fetchData(String requestID)
	{
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();

			String query = "select requests.empID,requestID,empName,requested_on,status,subject,description from requests,";
			query = query+"emp where requests.empID=emp.empID and requestID='"+requestID+"' order by status asc";
			rs = stmt.executeQuery(query);
			int i=40;
			while(rs.next())
			{
				JLabel temp_empID = new JLabel(""+rs.getInt(1));
					temp_empID.setBounds(250,120,175,30);
					temp_empID.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_empID);

				JLabel temp_requestID = new JLabel(""+rs.getString(2));
					temp_requestID.setBounds(250,160,175,30);
					temp_requestID.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_requestID);

				JLabel temp_name = new JLabel(""+rs.getString(3));
					temp_name.setBounds(250,200,175,30);
					temp_name.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_name);

				JLabel temp_date = new JLabel(rs.getDate(4)+"  "+rs.getTime(4));
					temp_date.setBounds(250,240,175,30);
					temp_date.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_date);

				JLabel temp_status = new JLabel(""+rs.getString(5));
					temp_status.setBounds(250,280,175,30);
					temp_status.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_status);

				JLabel temp_subject = new JLabel(""+rs.getString(6));
					temp_subject.setBounds(250,320,275,30);
					temp_subject.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_subject);

				JLabel temp_desc = new JLabel(""+rs.getString(7));
					temp_desc.setBounds(250,360,375,30);
					temp_desc.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_desc);
				String s = rs.getString(7);
				if(s.length()>50)
				{
					temp_desc.setText(""+s.substring(0,s.indexOf("\n")));

					JLabel temp_desc2 = new JLabel(""+s.substring(s.indexOf("\n")+1,s.length()));
					temp_desc2.setBounds(250,400,375,30);
					temp_desc2.setFont(new Font("Bahnschrift",Font.PLAIN,14));
					root.add(temp_desc2);
				}
				i=i+40;
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ViewRequestDescription("20180713203524");
			}
		});
	}
}