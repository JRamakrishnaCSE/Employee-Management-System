import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class UpdateContactInfo implements ActionListener
{
	JFrame root;

	JTextField tf_empID,tf_dno,tf_village,tf_mandal,tf_district,tf_state,tf_pincode,tf_phone;
	JLabel lb_empID,lb_dno,lb_village,lb_mandal,lb_district,lb_state,lb_pincode,lb_phone;

	JButton bt_fetch,bt_cancel,bt_reset,bt_update;
	JLabel title,sub_title;

	UpdateContactInfo()
	{
		root = new JFrame("Update Contact Information");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,500);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(80,0,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		sub_title = new JLabel("Update Contact Information");
			sub_title.setBounds(135,30,250,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		lb_empID = new JLabel("Employee ID                           :");
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

		lb_dno = new JLabel("Door Number                       :");
			lb_dno.setBounds(40,110,150,30);
			lb_dno.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_dno);

		tf_dno = new JTextField();
			tf_dno.setBounds(190,110,190,30);
			tf_dno.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_dno);

		lb_village = new JLabel("Village / Town                      :");
			lb_village.setBounds(40,150,150,30);
			lb_village.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_village);

		tf_village = new JTextField();
			tf_village.setBounds(190,150,190,30);
			tf_village.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_village);

		lb_mandal = new JLabel("Mandal / City                         :");
			lb_mandal.setBounds(40,190,150,30);
			lb_mandal.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_mandal);

		tf_mandal = new JTextField();
			tf_mandal.setBounds(190,190,190,30);
			tf_mandal.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_mandal);

		lb_district = new JLabel("District                                         :");
			lb_district.setBounds(40,230,150,30);
			lb_district.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_district);

		tf_district = new JTextField();
			tf_district.setBounds(190,230,190,30);
			tf_district.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_district);

		lb_state = new JLabel("State                                               :");
			lb_state.setBounds(40,270,150,30);
			lb_state.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_state);

		tf_state = new JTextField();
			tf_state.setBounds(190,270,190,30);
			tf_state.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_state);

		lb_pincode = new JLabel("Pincode                                      :");
			lb_pincode.setBounds(40,310,150,30);
			lb_pincode.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_pincode);

		tf_pincode = new JTextField();
			tf_pincode.setBounds(190,310,190,30);
			tf_pincode.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_pincode);

		lb_phone = new JLabel("Phone Number                :");
			lb_phone.setBounds(40,350,150,30);
			lb_phone.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_phone);

		tf_phone = new JTextField();
			tf_phone.setBounds(190,350,190,30);
			tf_phone.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(tf_phone);

		bt_cancel = new JButton("Cancel");
			bt_cancel.setBounds(70,400,100,40);
			bt_cancel.addActionListener(this);
			bt_cancel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_cancel);

		bt_reset = new JButton("Reset");
			bt_reset.setBounds(190,400,100,40);
			bt_reset.addActionListener(this);
			bt_reset.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_reset);

		bt_update = new JButton("Update");
			bt_update.setBounds(310,400,100,40);
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

	private int uid=0;

	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Fetch"))
		{
			{
				try
				{
					uid = Integer.parseInt(""+tf_empID.getText().trim());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"Please Enter a Valid Employee ID !");
					return;
				}
				try
				{
					String query = "select phone,DNO,village,mandal,district,state,pincode from address where empID="+uid+";";
					rs = stmt.executeQuery(query);
					rs.next();

					tf_phone.setText(""+rs.getLong(1));
					tf_dno.setText(""+rs.getString(2));
					tf_village.setText(""+rs.getString(3));
					tf_mandal.setText(""+rs.getString(4));
					tf_district.setText(""+rs.getString(5));
					tf_state.setText(""+rs.getString(6));
					tf_pincode.setText(""+rs.getInt(7));

					tf_empID.setEditable(false);
					bt_update.setEnabled(true);
					return;
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"No Data found with that ID !");
				}
			}
		}else if(command.equals("Cancel") || command.equals("Enough"))
		{
			root.dispose();
		}else if(command.equals("Reset"))
		{
			clearAllFields();
		}else if(command.equals("Update"))
		{
			Data obj = new Data();
			{
				obj.uid=uid;
				obj.dno = tf_dno.getText().trim()+"";
				obj.village = tf_village.getText().trim()+"";
				obj.mandal = tf_mandal.getText().trim()+"";
				obj.district = tf_district.getText().trim()+"";
				obj.state = tf_state.getText().trim()+"";

				if(!(obj.dno.length()>2))
				{
					JOptionPane.showMessageDialog(null,"Invalid Door Number !");
					return;
				}
				if(!(obj.village.length()>2))
				{
					JOptionPane.showMessageDialog(null,"Invalid Village or Town name !");
					return;
				}
				if(!(obj.mandal.length()>2))
				{
					JOptionPane.showMessageDialog(null,"Invalid Mandal or City name !");
					return;
				}
				if(!(obj.district.length()>2))
				{
					JOptionPane.showMessageDialog(null,"Invalid District name !");
					return;
				}
				if(!(obj.state.length()>1))
				{
					JOptionPane.showMessageDialog(null,"Invalid State name !");
					return;
				}
				try
				{
					if(!(tf_pincode.getText().trim().length()==6))
					{
						throw new Exception("Invalid Pin Code !");
					}
					obj.pincode = Integer.parseInt(tf_pincode.getText().trim()+"");
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"Invalid Pin Code");
					return;
				}
				try
				{
					if (!(tf_phone.getText().trim().length()==10)) {
						throw new Exception("Invalid Phone Number !");
					}
					obj.phone = Long.parseLong(tf_phone.getText().trim());
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,""+e.getMessage());
					return;
				}
				updateDetails(obj);
				return;
				// JOptionPane.showMessageDialog(null,"Done");
			}
		}
	}

	private void updateDetails(Data obj)
	{
		try
		{
			String query = "update address set phone="+obj.phone+",dno='"+obj.dno+"',village='"+obj.village+"',";
			query = query + "mandal='"+obj.mandal+"',district='"+obj.district+"',state='"+obj.state+"',pincode="+obj.pincode+" ";
			query = query + "where empID="+obj.uid+";";
			int ec = stmt.executeUpdate(query);
			if(ec==1)
			{
				JOptionPane.showMessageDialog(null,"Data Updated Successfully !");
				clearAllFields();
				bt_cancel.setText("Enough");
				return;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Unable to update right now. Please try after sometime !");
				return;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Unable to update right now. Please try after sometime !"+e);
			return;
		}
	}

	private void clearAllFields()
	{
		tf_empID.setEditable(true);
		bt_update.setEnabled(false);
		tf_empID.setText("");
		tf_dno.setText("");
		tf_village.setText("");
		tf_mandal.setText("");
		tf_district.setText("");
		tf_state.setText("");
		tf_pincode.setText("");
		tf_phone.setText("");
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new UpdateContactInfo();
			}
		});
	}
}
class Data
{
	String dno,village,mandal,district,state;
	int pincode,uid;
	long phone;
}