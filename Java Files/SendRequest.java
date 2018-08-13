import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class SendRequest implements ActionListener
{
	JLabel lb_request,lb_subject,lb_description,title,sub_title,lb_sender;
	JTextField tf_subject,tf_sender;
	TextArea jta;
	JButton bt_post,bt_clear;

	JFrame root;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	private int uid=0;
	SendRequest(int uid)
	{
		this.uid=uid;
		root = new JFrame("Employee Page");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,400);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(80,0,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		sub_title = new JLabel("Send Request Page");
			sub_title.setBounds(165,30,250,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		// 

		lb_request = new JLabel("Request / Suggestion :");
			lb_request.setBounds(30,70,180,30);
			lb_request.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_request);

		lb_sender = new JLabel("Sender ID               :");
			lb_sender.setBounds(30,110,150,30);
			lb_sender.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_sender);

		tf_sender = new JTextField();
			tf_sender.setBounds(150,110,310,30);
			tf_sender.setText(""+uid);
			tf_sender.setEditable(false);
			tf_sender.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_sender);

		lb_subject = new JLabel("Subject                     :");
			lb_subject.setBounds(30,150,100,30);
			lb_subject.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_subject);
		// if(true) return;

		tf_subject = new JTextField();
			tf_subject.setBounds(150,150,310,30);
			tf_subject.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_subject);

		lb_description = new JLabel("Description         :");
			lb_description.setBounds(30,190,100,30);
			lb_description.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_description);

		jta = new TextArea(200,3);
			jta.setBounds(150,195,310,110);
			jta.setFont(new Font("Bahnschrift",Font.BOLD,14));
			root.add(jta);
	
		bt_clear = new JButton("Clear All");
			bt_clear.setBounds(151,320,145,30);
			bt_clear.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			bt_clear.addActionListener(this);
			root.add(bt_clear);

		bt_post = new JButton("Post Request");
			bt_post.setBounds(315,320,145,30);
			bt_post.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			bt_post.addActionListener(this);
			root.add(bt_post);

		
	}

	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Post Request"))
		{
			try
			{
				if(tf_subject.getText().trim().length()<10)
				{
					JOptionPane.showMessageDialog(null,"Request Subject is Too small !");
					return;
				}
				if(jta.getText().trim().length()<10)
				{
					JOptionPane.showMessageDialog(null,"Desctiption is too small !");
					return;
				}
				if(jta.getText().trim().length()>150)
				{
					JOptionPane.showMessageDialog(null,"Desctiption Cannot be more than 150 Characters !");
					return;
				}
				postRequest(uid);
			}catch(Exception e)
			{}
		}
	}

	public void postRequest(int uid)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
			
			System.out.println("comming here");
			rs = stmt.executeQuery("select now(),cast(cast(now() as unsigned) as char);");
			rs.next();
			String requestID = rs.getString(2);
			String requested_on = rs.getDate(1)+" "+rs.getTime(1);
			String query="insert into requests values("+uid+",'"+requestID+"','"+requested_on+"','pending','";
			query=query+tf_subject.getText().trim()+"','"+jta.getText().trim()+"');";
			int ec = stmt.executeUpdate(query);
			if(ec==1){
				String mess="Your Request has been Posted Successfully !\n";
				mess = mess + "Your tracking ID is "+requestID;
				tf_subject.setText("");
				jta.setText("");
				JOptionPane.showMessageDialog(null,mess);
			}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Unable to post your Request right now !\n Please Try later");
			JOptionPane.showMessageDialog(null,""+e);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new SendRequest(1002);
			}
		});
	}
}