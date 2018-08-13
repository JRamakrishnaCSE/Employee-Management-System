import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Notifications implements ActionListener
{
	JFrame root;
	JLabel title,lb_notif,lb_notif_id,lb_subject,lb_posted_on;
	Font font;

	Connection con;
	Statement stmt;
	ResultSet rs;

	Notifications()
	{
		root = new JFrame("Notifications");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(650,600);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		font = new Font("Bahnschrift",Font.PLAIN,20);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(200,5,300,40);
			title.setFont(font);
			root.add(title);

		lb_notif =  new JLabel("Notifications Area");
			lb_notif.setBounds(275,30,300,40);
			lb_notif.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_notif);

		lb_notif_id = new JLabel("Notice ID");
			lb_notif_id.setBounds(30,80,70,40);
			lb_notif_id.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_notif_id);

		lb_subject = new JLabel("Subject");
			lb_subject.setBounds(120,80,250,40);
			lb_subject.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_subject);

		lb_posted_on = new JLabel("Posted On");
			lb_posted_on.setBounds(390,80,100,40);
			lb_posted_on.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_posted_on);

		fetch();
	}
	public void fetch()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
			// JOptionPane.showMessageDialog(null,"Connected!");
			rs = stmt.executeQuery("select * from notification order by notice_id desc limit 10;");
			int i=40;
			while(rs.next())
			{
				JLabel lb_temp_notice_id = new JLabel(""+rs.getInt(1));
					lb_temp_notice_id.setBounds(55,i+80,20,40);
					lb_temp_notice_id.setFont(new Font("Bahnschrift",Font.PLAIN,15));
					root.add(lb_temp_notice_id);

				JLabel lb_temp_subject = new JLabel(""+rs.getString(2));
					lb_temp_subject.setBounds(120,i+80,250,40);
					lb_temp_subject.setFont(new Font("Bahnschrift",Font.PLAIN,15));
					root.add(lb_temp_subject);

				JLabel lb_temp_posted_on = new JLabel(""+rs.getDate(3));
					lb_temp_posted_on.setBounds(390,i+80,100,40);
					lb_temp_posted_on.setFont(new Font("Bahnschrift",Font.PLAIN,15));
					root.add(lb_temp_posted_on);

				JButton bt_view_desc = new JButton("View Description");
					bt_view_desc.setBounds(490,i+84,140,27);
					bt_view_desc.addActionListener(this);
					bt_view_desc.setActionCommand(""+rs.getInt(1));
					bt_view_desc.setFont(new Font("Bahnschrift",Font.PLAIN,13));
					root.add(bt_view_desc);

				i=i+40;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Exception"+e);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		//JOptionPane.showMessageDialog(null,""+ae.getActionCommand());
		new ViewDescription(Integer.parseInt(ae.getActionCommand()));
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Notifications();
			}
		});
	}
}