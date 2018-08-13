import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ViewDescription
{
	JFrame root;
	JLabel title,sub_title;
	JLabel lb_notice_id,lb_subject,lb_desc,lb_posted_on;
	JLabel lb_notice_id_output,lb_subject_output,lb_desc_output,lb_posted_on_output;
	ViewDescription(int notice_id)
	{
		root = new JFrame("Notice Description");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(560,400);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(120,5,350,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,22));
			root.add(title);

		sub_title = new JLabel("Notification "+notice_id);
			sub_title.setBounds(220,35,350,40);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		lb_notice_id = new JLabel("Notice ID                    :");
			lb_notice_id.setBounds(40,80,110,40);
			lb_notice_id.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_notice_id);

		lb_notice_id_output = new JLabel("0000");
			lb_notice_id_output.setBounds(160,85,100,30);
			lb_notice_id_output.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_notice_id_output);

		lb_posted_on = new JLabel("Posted On                :");
			lb_posted_on.setBounds(40,120,250,40);
			lb_posted_on.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_posted_on);

		lb_posted_on_output = new JLabel("0000-00-00");
			lb_posted_on_output.setBounds(160,125,100,30);
			lb_posted_on_output.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_posted_on_output);

		lb_subject = new JLabel("Subject                        :");
			lb_subject.setBounds(40,160,110,40);
			lb_subject.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_subject);

		lb_subject_output = new JLabel("abcd : Efgh");
			lb_subject_output.setBounds(160,165,400,30);
			lb_subject_output.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_subject_output);

		lb_desc = new JLabel("Description           :");
			lb_desc.setBounds(40,200,100,40);
			lb_desc.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_desc);

		lb_desc_output = new JLabel("abc def ghi jkl mno pqrs tuv wxyz");
			lb_desc_output.setBounds(160,205,400,30);
			lb_desc_output.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_desc_output);

		fetchData(notice_id);
	}
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	public void fetchData(int notice_id)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();

			rs = stmt.executeQuery("select subject,posted_on from notification where notice_id="+notice_id+";");
			rs.next();
			lb_subject_output.setText(""+rs.getString(1));
			lb_posted_on_output.setText(""+rs.getDate(2));

			rs = stmt.executeQuery("select description from notice_desc where notice_id="+notice_id+";");
			rs.next();
			lb_desc_output.setText(""+rs.getString(1));
			lb_notice_id_output.setText(""+notice_id);

			try{
				con.close();
			}
			catch(Exception e){}
		}catch(Exception e){}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ViewDescription(1);
			}
		});
	}
}