import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddNewEmployee implements ActionListener,Runnable
{
	Thread childThread;
	JFrame root;
	JLabel lb1,lb2;

	JLabel lb_bio,lb_empID,lb_fullname,lb_email,lb_dept,lb_joinDate;
	JLabel lb_com,lb_dno,lb_village,lb_mandal,lb_district,lb_state,lb_pin;

	JTextField tf_dno,tf_village,tf_mandal,tf_district,tf_state,tf_pin;
	JTextField tf_empID,tf_fullname,tf_email,tf_dept,tf_joinDate;

	JLabel lb_phone;
	JTextField tf_phone;

	Font bahnschrift15 = new Font("Bahnschrift",Font.PLAIN,15);
	AddNewEmployee()
	{
		root = new JFrame("Register");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(490,685);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		lb1 = new JLabel("Millennium Software Solutions");
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			lb1.setBounds(75,7,320,30);
			root.add(lb1);

		lb2 = new JLabel("New Employee Registration");
			lb2.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			lb2.setBounds(120,35,300,30);
			root.add(lb2);

		lb_bio = new JLabel("Employee Basic Details    :");
			lb_bio.setBounds(30,80,200,30);
			lb_bio.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_bio);

		lb_fullname = new JLabel("Employee Full Name  *      :");
			lb_fullname.setBounds(45,120,180,30);
			lb_fullname.setFont(bahnschrift15);
			root.add(lb_fullname);

		tf_fullname = new JTextField();
			tf_fullname.setBounds(240,120,200,30);
			tf_fullname.setFont(bahnschrift15);
			root.add(tf_fullname);

		lb_phone = new JLabel("Phone Number  *                  :");
			lb_phone.setBounds(45,160,180,30);
			lb_phone.setFont(bahnschrift15);
			root.add(lb_phone);

		tf_phone = new JTextField();
			tf_phone.setBounds(240,160,200,30);
			tf_phone.setFont(bahnschrift15);
			root.add(tf_phone);

		lb_email = new JLabel("E-mail Address  *                :");
			lb_email.setBounds(45,200,180,30);
			lb_email.setFont(bahnschrift15);
			root.add(lb_email);

		tf_email = new JTextField();
			tf_email.setBounds(240,200,200,30);
			tf_email.setFont(bahnschrift15);
			root.add(tf_email);

		lb_dept = new JLabel("Department                             :");
			lb_dept.setBounds(45,240,180,30);
			lb_dept.setFont(bahnschrift15);
			root.add(lb_dept);

		tf_dept = new JTextField();
			tf_dept.setBounds(240,240,200,30);
			tf_dept.setFont(bahnschrift15);
			root.add(tf_dept);

		lb_joinDate = new JLabel("Joining Date *                          :");
			lb_joinDate.setBounds(45,280,180,30);
			lb_joinDate.setFont(bahnschrift15);
			root.add(lb_joinDate);

		tf_joinDate = new JTextField();
			tf_joinDate.setBounds(240,280,200,30);
			tf_joinDate.setFont(bahnschrift15);
			root.add(tf_joinDate);


		lb_com = new JLabel("Communication Address    :");
			lb_com.setBounds(30,320,200,30);
			lb_com.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_com);

		lb_dno = new JLabel("Door Number                         :");
			lb_dno.setBounds(45,360,200,30);
			lb_dno.setFont(bahnschrift15);
			root.add(lb_dno);

		tf_dno = new JTextField();
			tf_dno.setBounds(240,360,200,30);
			tf_dno.setFont(bahnschrift15);
			root.add(tf_dno);

		lb_village = new JLabel("Village / Town                        :");
			lb_village.setBounds(45,400,200,30);
			lb_village.setFont(bahnschrift15);
			root.add(lb_village);

		tf_village = new JTextField();
			tf_village.setBounds(240,400,200,30);
			tf_village.setFont(bahnschrift15);
			root.add(tf_village);


		lb_mandal = new JLabel("Mandal / City                          :");
			lb_mandal.setBounds(45,440,200,30);
			lb_mandal.setFont(bahnschrift15);
			root.add(lb_mandal);

		tf_mandal = new JTextField();
			tf_mandal.setBounds(240,440,200,30);
			tf_mandal.setFont(bahnschrift15);
			root.add(tf_mandal);

		lb_district = new JLabel("District                                      :");
			lb_district.setBounds(45,480,200,30);
			lb_district.setFont(bahnschrift15);
			root.add(lb_district);

		tf_district = new JTextField();
			tf_district.setBounds(240,480,200,30);
			tf_district.setFont(bahnschrift15);
			root.add(tf_district);

		lb_state = new JLabel("State                                           :");
			lb_state.setBounds(45,520,200,30);
			lb_state.setFont(bahnschrift15);
			root.add(lb_state);

		tf_state = new JTextField();
			tf_state.setBounds(240,520,200,30);
			tf_state.setFont(bahnschrift15);
			root.add(tf_state);

		lb_pin = new JLabel("Pincode                                     :");
			lb_pin.setBounds(45,560,200,30);
			lb_pin.setFont(bahnschrift15);
			root.add(lb_pin);

		tf_pin = new JTextField();
			tf_pin.setBounds(240,560,200,30);
			tf_pin.setFont(bahnschrift15);
			root.add(tf_pin);

		bt_Clear = new JButton("Clear All");
			bt_Clear.setBounds(65,605,160,40);
			bt_Clear.setFont(bahnschrift15);
			bt_Clear.addActionListener(this);
			root.add(bt_Clear);

		bt_Register = new JButton("Register");
			bt_Register.setBounds(250,605,170,40);
			bt_Register.setFont(bahnschrift15);
			bt_Register.addActionListener(this);
			root.add(bt_Register);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Register"))
		{
			Address obj = new Address();

			obj.fullname = tf_fullname.getText().trim()+"";
			
			if(obj.fullname.length()<1)
			{
				JOptionPane.showMessageDialog(null,"Employee Full name should not be Empty !");
				return;
			}
			try
			{
				obj.phone = Long.parseLong(tf_phone.getText().trim());
				if(!((obj.phone+"").length()==10))
				{
					JOptionPane.showMessageDialog(null,"Invalid Phone Number !");
					return;
				}
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Invalid Phone Number !");
				return;
			}

			obj.email = tf_email.getText().trim()+"";
			try
			{
				if((obj.email.endsWith("@gmail.com") || obj.email.endsWith("@yahoo.com")))
				{
					if(obj.email.substring(0,obj.email.indexOf("@")).length()>2)
					{

					}else
					{
						JOptionPane.showMessageDialog(null,"Email length should be at least 3  !");
						return;
					}
				}else
				{
					JOptionPane.showMessageDialog(null,"Invalid Email !");
					return;
				}
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Email is Required !");
				return;
			}
			obj.dept = tf_dept.getText().trim()+"";

			if  (
				 obj.dept.equalsIgnoreCase("Crew") ||
				 obj.dept.equalsIgnoreCase("Maintenance") ||
				 obj.dept.equalsIgnoreCase("Admin")
				)
			{

			}else
			{
				JOptionPane.showMessageDialog(null,"Invalid Department !");
				return;
			}
			obj.joinDate = tf_joinDate.getText().trim();

			if(!(obj.joinDate.length()==10))
			{
				JOptionPane.showMessageDialog(null,"Invalid Joining Date !");
				return;
			}
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
				obj.pincode = Integer.parseInt(tf_pin.getText().trim()+"");
			}catch(Exception e){
				JOptionPane.showMessageDialog(null,"Invalid Pin code");
				return;
			}
			childThread = new Thread(this,"childThread");
			childThread.start();

			Database db = new Database();
			if(db.registerNewEmp(obj))
			{
				clearAllFields();
				return;
			}
		}else if(ae.getActionCommand().equals("Clear All"))
		{
			// JOptionPane.showMessageDialog(null,"Cleared!");
			clearAllFields();
		}
	}
	public void run()
	{
		
	}
	void clearAllFields()
	{
		tf_dno.setText("");
		tf_village.setText("");
		tf_phone.setText("");
		tf_mandal.setText("");
		tf_district.setText("");
		tf_state.setText("");
		tf_pin.setText("");
		tf_fullname.setText("");
		tf_email.setText("");
		tf_dept.setText("");
		tf_joinDate.setText("");
	}
	JButton bt_Clear,bt_Register;
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new AddNewEmployee();
			}
		});
	}
}

class Address
{
	String dno,village,mandal,district,state;
	String empID,fullname,email,dept,joinDate;
	int pincode;
	long phone;
}