import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class PostNotification implements ActionListener
{
	JFrame root;
	JLabel title,lb_post,lb_posting_by,lb_id,lb_subject,lb_desc,lb_admin;
	JTextField tf_id,tf_subject;
	TextArea ta_desc;

	JButton bt_clear,bt_post,bt_cancel;
	PostNotification(int posted_by)
	{
		root = new JFrame("Admin Millennium Software Solutions");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,450);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(85,5,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		lb_post = new JLabel("Post Notifications");
			lb_post.setBounds(170,35,150,40);
			lb_post.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(lb_post);

		lb_id = new JLabel("Notice ID           : ");
			lb_id.setBounds(30,80,100,40);
			lb_id.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_id);

		tf_id = new JTextField();
			tf_id.setBounds(145,85,300,30);
			tf_id.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			tf_id.setEditable(false);
			root.add(tf_id);

		lb_subject = new JLabel("Subject              : ");
			lb_subject.setBounds(30,130,100,40);
			lb_subject.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_subject);

		tf_subject = new JTextField();
			tf_subject.setBounds(145,135,300,30);
			tf_subject.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_subject);

		lb_desc = new JLabel("Description     : ");
			lb_desc.setBounds(30,180,110,40);
			lb_desc.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_desc);

		ta_desc = new TextArea();
			ta_desc.setBounds(146,189,300,100);
			ta_desc.setFont(new Font("Bahnschrift",Font.BOLD,15));
			root.add(ta_desc);

		lb_desc = new JLabel("Posting by        : ");
			lb_desc.setBounds(30,295,110,40);
			lb_desc.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_desc);

		lb_admin = new JLabel(""+posted_by);
			lb_admin.setBounds(147,295,100,40);
			lb_admin.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_admin);

		bt_clear = new JButton("Clear");
			bt_clear.setBounds(145,340,95,30);
			bt_clear.addActionListener(this);
			bt_clear.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_clear);

		bt_post = new JButton("Post");
			bt_post.setBounds(250,340,95,30);
			bt_post.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			bt_post.addActionListener(this);
			root.add(bt_post);

		bt_cancel = new JButton("Cancel");
			bt_cancel.setBounds(355,340,95,30);
			bt_cancel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			bt_cancel.addActionListener(this);
			root.add(bt_cancel);

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
			rs = stmt.executeQuery("select max(notice_id)+1 from notification;");
			rs.next();
			nid = rs.getInt(1);
			tf_id.setText(nid+"");
		}catch(Exception e){
			tf_id.setText("1");
		}
	}
	int nid=1;
	private Connection con;
	private Statement stmt;;
	private ResultSet rs;
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Clear"))
		{
			tf_subject.setText("");
			ta_desc.setText("");
			return;
		}else if(command.equals("Post"))
		{
			try
			{
				if(tf_subject.getText().trim().length()<10)
				{
					JOptionPane.showMessageDialog(null,"Notification Subject is too small !");
					return;
				}
				if(ta_desc.getText().trim().length()<20)
				{
					JOptionPane.showMessageDialog(null,"Description length Should be atleast 20 Characters !");
					return;
				}
				if(ta_desc.getText().trim().length()>100)
				{
					JOptionPane.showMessageDialog(null,"Description Cannot exceed 100 Characters !");
					return;
				}
			}catch (Exception e) {}
			try
			{
				String sub = tf_subject.getText().trim();
				String desc = ta_desc.getText().trim();
				int ec = stmt.executeUpdate("insert into notification values("+nid+",'"+sub+"',now());");
				int ecc = stmt.executeUpdate("insert into notice_desc values("+nid+",'"+desc+"');");
				if(ec==1 && ecc==1)
				{
					JOptionPane.showMessageDialog(null,"Notification Posted Successfully !");
					nid=nid+1;
					bt_cancel.setText("Enough");
					tf_id.setText(nid+"");
					tf_subject.setText("");
					ta_desc.setText("");
					return;
				}else {
					JOptionPane.showMessageDialog(null,"Unable to Post the Notification !");
				}
			}catch(Exception e){}

		}else if(command.equals("Cancel") || command.equals("Enough"))
		{
			try{
				con.close();
			}
			catch(Exception e){

			}
			root.dispose();
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new PostNotification(101);
			}
		});
	}
}