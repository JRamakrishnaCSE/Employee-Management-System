import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class UpdateBillingInfo implements ActionListener
{
	JFrame root;

	JTextField tf_empID,tf_acc_num,tf_acc_holder,tf_bank_name,tf_ifsc;
	JLabel lb_empID,lb_acc_num,lb_acc_holder,lb_bank_name,lb_ifsc;

	JButton bt_fetch,bt_cancel,bt_reset,bt_update;
	JLabel title,sub_title;

	UpdateBillingInfo()
	{
		root = new JFrame("Update Billing Information");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,400);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(80,0,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		sub_title = new JLabel("Update Billing Information");
			sub_title.setBounds(135,30,250,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		lb_empID = new JLabel("Employee ID                         :");
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

		lb_acc_num = new JLabel("Account Number           :");
			lb_acc_num.setBounds(40,110,150,30);
			lb_acc_num.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_acc_num);

		tf_acc_num = new JTextField();
			tf_acc_num.setBounds(190,110,190,30);
			tf_acc_num.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_acc_num);

		lb_acc_holder = new JLabel("Account Holder               :");
			lb_acc_holder.setBounds(40,150,150,30);
			lb_acc_holder.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_acc_holder);

		tf_acc_holder = new JTextField();
			tf_acc_holder.setBounds(190,150,190,30);
			tf_acc_holder.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_acc_holder);

		lb_bank_name = new JLabel("Bank Name                             :");
			lb_bank_name.setBounds(40,190,150,30);
			lb_bank_name.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_bank_name);

		tf_bank_name = new JTextField();
			tf_bank_name.setBounds(190,190,190,30);
			tf_bank_name.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_bank_name);

		lb_ifsc = new JLabel("IFSC Code                               :");
			lb_ifsc.setBounds(40,230,150,30);
			lb_ifsc.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_ifsc);

		tf_ifsc = new JTextField();
			tf_ifsc.setBounds(190,230,190,30);
			tf_ifsc.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_ifsc);

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
			// System.out.println("Database Connection success !");
		}catch(Exception e)
		{
			// System.out.println("Database Connection success !");
			JOptionPane.showMessageDialog(null,"Unable to connnect to the Database !");
		}

	}

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	private int uid=1001;

	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Fetch"))
		{
			try
			{
				uid = Integer.parseInt(tf_empID.getText().trim());
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Please Enter a Valid Employee ID !");
				return;
			}
			String query = "select acc_num,acc_holder,bank_name,ifsc from account where empID="+uid+";";
			try
			{
				rs = stmt.executeQuery(query);
				rs.next();

				tf_acc_num.setText(""+rs.getLong(1));
				tf_acc_holder.setText(""+rs.getString(2));
				tf_bank_name.setText(""+rs.getString(3));
				tf_ifsc.setText(""+rs.getString(4));

				tf_empID.setEditable(false);
				bt_update.setEnabled(true);

			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"No Data Found with such Employee ID !");
				return;
			}
		}else if(command.equals("Cancel") || command.equals("Enough"))
		{
			root.dispose();
		}else if(command.equals("Reset"))
		{
			bt_update.setEnabled(false);
			clearAllFields();
		}else if(command.equals("Update"))
		{
			updateDetails(uid);
		}
	}

	public void updateDetails(int uid)
	{
		long acc_num;
		String acc_holder;
		String bank_name;
		String ifsc;
		{
			// validating acc number
			try
			{
				if (tf_acc_num.getText().trim().length()<5) {
					JOptionPane.showMessageDialog(null,"Account Number Seems too small or Invalid !");
					return;
				}
				acc_num=Long.parseLong(""+tf_acc_num.getText().trim());
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Invalid Account Number !");
				return;
			}
			// validating acc holder name
		
			try
			{
				acc_holder = tf_acc_holder.getText().trim();
				if(acc_holder.length()<5)
				{
					JOptionPane.showMessageDialog(null,"Account Holder name seems too small !");
					return;
				}
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Account Holder should not be empty !");
				return;
			}
			// validating bank_name
			try
			{
				bank_name = tf_bank_name.getText().trim();
				if(bank_name.length()<2)
				{
					JOptionPane.showMessageDialog(null,"Bank name is too small !");
					return;
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Bank name should not be empty !");
				return;
			}
			//validating ifsc
			try
			{
				ifsc = tf_ifsc.getText().trim();
				if(ifsc.length()<5)
				{
					JOptionPane.showMessageDialog(null,"IFSC Code seems too small or Invalid !");
					return;
				}
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"IFSC Code should not be empty !");
				return;
			}
		}

		try
		{
			String query = "update account set acc_num="+acc_num+",acc_holder='"+acc_holder+"',bank_name='"+bank_name+"',";
			query = query + "ifsc='"+ifsc+"' where empID="+uid+";";
			
			int ec = stmt.executeUpdate(query);
			if(ec==1)
			{
				JOptionPane.showMessageDialog(null,"Data has been Updated Successully !");
				clearAllFields();
				bt_cancel.setText("Enough");
				return;
			}else {
				JOptionPane.showMessageDialog(null,"Unable to Update the Data. Please try Again !");
				return;
			}
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"Unable to Update the Data. Please try Again !");
			return;
		}
	}

	private void clearAllFields()
	{
		tf_empID.setEditable(true);
		bt_update.setEnabled(false);
		tf_empID.setText("");
		tf_acc_num.setText("");
		tf_acc_holder.setText("");
		tf_bank_name.setText("");
		tf_ifsc.setText("");
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new UpdateBillingInfo();
			}
		});
	}
}