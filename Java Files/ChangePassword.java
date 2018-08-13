import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ChangePassword implements ActionListener
{
	JFrame root;
	JLabel lb0,lb1,lb2,lb3,lb4;
	JTextField tf_uid,tf_con_pass;
	JPasswordField pf_old_pass,pf_new_pass;
	JButton change,reset;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	public void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
		}catch(Exception e){}
	}
	ChangePassword()
	{
		root = new JFrame("Change Password");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(385,350);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		lb0 = new JLabel("Change Password");
			lb0.setBounds(120,10,150,40);
			lb0.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			root.add(lb0);

		lb1 = new JLabel("User ID                                          :");
			lb1.setBounds(30,60,150,40);
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb1);

		tf_uid = new JTextField();
			tf_uid.setBounds(180,65,160,30);
			tf_uid.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_uid);

		lb2 = new JLabel("Old Password                     :");
			lb2.setBounds(30,100,150,40);
			lb2.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb2);

		pf_old_pass = new JPasswordField();
			pf_old_pass.setBounds(180,105,160,30);
			root.add(pf_old_pass);

		lb3 = new JLabel("New Password                 :");
			lb3.setBounds(30,140,150,40);
			lb3.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb3);

		pf_new_pass = new JPasswordField();
			pf_new_pass.setBounds(180,145,160,30);
			root.add(pf_new_pass);

		lb4 = new JLabel("Confirm Password      :");
			lb4.setBounds(30,180,150,40);
			lb4.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb4);

		tf_con_pass = new JTextField();
			tf_con_pass.setBounds(180,185,160,30);
			tf_con_pass.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_con_pass);

		reset = new JButton("Reset");
			reset.setBounds(60,240,100,40);
			reset.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			reset.addActionListener(this);
			root.add(reset);

		change = new JButton("Change");
			change.setBounds(180,240,100,40);
			change.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			change.addActionListener(this);
			root.add(change);

		connect();
	}
	private void setUID(int uid)
	{
		tf_uid.setText(uid+"");
		tf_uid.setEditable(false);
	}
	ChangePassword(int uid)
	{
		ChangePassword cp = new ChangePassword();
		cp.setUID(uid);
		cp.reset.setActionCommand("From:User_Level");
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		String type="";
		if(command.equals("Reset"))
		{
			tf_uid.setText("");
			tf_con_pass.setText("");
			pf_old_pass.setText("");
			pf_new_pass.setText("");
		}
		else if(command.equals("From:User_Level"))
		{
			tf_con_pass.setText("");
			pf_old_pass.setText("");
			pf_new_pass.setText("");
		}
		else if(command.equals("Change"))
		{
			String old = pf_old_pass.getText();
			String new_pass = pf_new_pass.getText();
			String con_pass = tf_con_pass.getText();
			int uid = 0;
			try{
				uid=Integer.parseInt(tf_uid.getText().trim()+"");
			}catch(Exception e){
				if(tf_uid.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null,"Input Fields Should not be Empty!");
				}else
				{
					JOptionPane.showMessageDialog(null,"Invalid User ID");
				}
				return;
			}

			try
			{
				rs = stmt.executeQuery("select password,'"+(new Extra()).getSHA256(""+old)+"' from admin where adminID="+uid+";");
				rs.next();
				if((rs.getString(1)).equals((rs.getString(2))))
				{
					type="admin";
					// System.out.println("Here 2");
				}else
				{
					JOptionPane.showMessageDialog(null,"Invalid Old Password! admin");
					return;
				}
			}catch(Exception e)
			{
				try
				{
					rs = stmt.executeQuery("select password,'"+(new Extra()).getSHA256(""+old)+"' from emp where empID="+uid+";");
					rs.next();
					if((rs.getString(1)).equals((rs.getString(2))))
					{
						type="emp";
						// System.out.println("Here 2");
					}else
					{
						JOptionPane.showMessageDialog(null,"Invalid Old Password !");
						return;
					}
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null,"User might not existed !");
					return ;
				}
			}
			// JOptionPane.showMessageDialog(null,"Change");

			if(new_pass.equals(con_pass))
			{
				if(new_pass.length()>2)
				{
					// JOptionPane.showMessageDialog(null,"Match");
					commitChange(uid,con_pass,type);
					return;
				}
				else {
					JOptionPane.showMessageDialog(null,"Minimum Length of Password is 3");
					return;
				}
			}
			else JOptionPane.showMessageDialog(null,"Confirm Password should be Same as New Password!");
		}
	}
	public void commitChange(int id,String pass,String type)
	{
		try
		{
			int ec = stmt.executeUpdate("update "+type+" set password=sha2('"+pass+"',256) where "+type+"ID="+id+";");
			// System.out.println("here");
			if(ec==1)
			{
				stmt.executeUpdate("update "+type+"_logs set password_changed_on=date(now()),password_changed_time=time(now()) where "+type+"ID="+id+";");
				// ProgressBar pb =  new ProgressBar();
				// pb.createProgressBar("Checking","Please Wait!");
				JOptionPane.showMessageDialog(null,"Password Changed Successfully!");
			}else {
				JOptionPane.showMessageDialog(null,"User might not existed!");
				return;
			}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,e+"");
		}
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ChangePassword();
			}
		});
	}
}