import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminPage extends JFrame implements ItemListener,ActionListener
{
	JFrame root;
	JLabel title;
	Font font;

	JLabel lb1,lb2,lb3,lb4,lb5;
	JButton bt_logout,bt_search;

	JTextField tf_search;

	CheckboxGroup cbg;
	Checkbox id,name,joinDate;

	Font bahnschriftPLAIN16 = new Font("Bahnschrift",Font.PLAIN,16);
	Font bahnschriftBOLD16 = new Font("Bahnschrift",Font.BOLD,16);
	String aid;
	AdminPage(String user)
	{
		this.aid=user;
		root = new JFrame("Admin Millennium Software Solutions");
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		root.setSize(650,550);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		font = new Font("Bahnschrift",Font.PLAIN,30);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(80,10,520,40);
			title.setFont(font);
			root.add(title);

		lb1 = new JLabel("Logged In as @ "+user);
			lb1.setBounds(420,55,200,30);
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb1);

		prepareSystem();

		// new LoginActivityDetails(101,"","","","");
		if(user.equals("Guest"))
		{
			disable();
		}
	}
	JButton changePass,addEmp,delEmp,updateEmp,showAll,postNotice,viewRequests,viewAttendance;
	JButton showNotifications,addAdmin;
	public void prepareSystem()
	{
		bt_logout = new JButton("Logout");
			bt_logout.setBounds(553,58,78,24);
			bt_logout.setFont(new Font("Calibri",Font.ITALIC,16));
			bt_logout.addActionListener(this);
			root.add(bt_logout);

		tf_search = new JTextField();
			tf_search.setBounds(40,110,420,40);
			tf_search.setFont((new Font("Bahnschrift",Font.PLAIN,19)));
			root.add(tf_search);

		bt_search = new JButton("Search");
			bt_search.setBounds(470,110,100,40);
			bt_search.setFont(bahnschriftPLAIN16);
			bt_search.addActionListener(this);
			root.add(bt_search);

		lb2 = new JLabel("Search By");
			lb2.setBounds(40,160,80,40);
			lb2.setFont(bahnschriftPLAIN16);
			root.add(lb2);

		cbg = new CheckboxGroup();

		id = new Checkbox(" Employee ID",cbg,true);
			id.setBounds(145,162,120,40);
			id.setFont(bahnschriftBOLD16);
			id.addItemListener(this);
			root.add(id);

		name = new Checkbox(" Employee Name",cbg,false);
			name.setBounds(295,162,180,40);
			name.setFont(bahnschriftBOLD16);
			name.addItemListener(this);
			root.add(name);

		// joinDate = new Checkbox(" Joining Date",cbg,false);
		// 	joinDate.setBounds(355,162,120,40);
		// 	joinDate.setFont(bahnschriftBOLD16);
		// 	joinDate.addItemListener(this);
		// 	root.add(joinDate);

		lb3 = new JLabel("Services Available");
			lb3.setFont(new Font("Bahnschrift",Font.PLAIN,20));
			lb3.setBounds(40,210,180,30);
			root.add(lb3);

		changePass = new JButton("Change Password");
			changePass.setBounds(40,250,190,30);
			changePass.setFont(bahnschriftPLAIN16);
			changePass.addActionListener(this);
			changePass.setActionCommand("changePass");
			root.add(changePass);

		postNotice = new JButton("Post Notifications");
			postNotice.setBounds(240,250,190,30);
			postNotice.setFont(bahnschriftPLAIN16);
			postNotice.addActionListener(this);
			postNotice.setActionCommand("postNotice");
			root.add(postNotice);

		addAdmin = new JButton("+ Add Admin");
			addAdmin.setBounds(440,250,190,30);
			addAdmin.setFont(bahnschriftPLAIN16);
			addAdmin.addActionListener(this);
			addAdmin.setActionCommand("addAdmin");
			root.add(addAdmin);

		addEmp = new JButton("+ Add New Employee");
			addEmp.setBounds(40,290,190,30);
			addEmp.setFont(bahnschriftPLAIN16);
			addEmp.addActionListener(this);
			addEmp.setActionCommand("addEmp");
			root.add(addEmp);

		viewRequests = new JButton("View Requests");
			viewRequests.setBounds(240,290,190,30);
			viewRequests.setFont(bahnschriftPLAIN16);
			viewRequests.addActionListener(this);
			viewRequests.setActionCommand("viewRequests");
			root.add(viewRequests);

		delEmp = new JButton("-     Delete Employee");
			delEmp.setBounds(40,330,190,30);
			delEmp.addActionListener(this);
			delEmp.setActionCommand("delEmp");
			delEmp.setFont(bahnschriftPLAIN16);
			root.add(delEmp);

		showNotifications = new JButton("Show Notifications");
			showNotifications.setBounds(240,330,190,30);
			showNotifications.addActionListener(this);
			showNotifications.setActionCommand("showNotifications");
			showNotifications.setFont(bahnschriftPLAIN16);
			root.add(showNotifications);

		updateEmp = new JButton("^     Update Employee");
			updateEmp.setBounds(40,370,190,30);
			updateEmp.addActionListener(this);
			updateEmp.setActionCommand("updateEmp");
			updateEmp.setFont(bahnschriftPLAIN16);
			root.add(updateEmp);

		viewAttendance = new JButton("View Attendance");
			viewAttendance.setBounds(240,370,190,30);
			viewAttendance.addActionListener(this);
			viewAttendance.setActionCommand("viewAttendance");
			viewAttendance.setFont(bahnschriftPLAIN16);
			root.add(viewAttendance);

		showAll = new JButton("Show All Employees");
			showAll.setBounds(40,410,190,30);
			showAll.setFont(bahnschriftPLAIN16);
			showAll.addActionListener(this);
			showAll.setActionCommand("showAll");
			root.add(showAll);

		hikeSalaries = new JButton("Hike Salaries");
			hikeSalaries.setBounds(240,410,190,30);
			hikeSalaries.setFont(bahnschriftPLAIN16);
			hikeSalaries.addActionListener(this);
			hikeSalaries.setActionCommand("hikeSalaries");
			root.add(hikeSalaries);

	}
	JButton hikeSalaries;
	public void disable()
	{
		tf_search.setEnabled(false);
		showAll.setEnabled(false);
		updateEmp.setEnabled(false);
		delEmp.setEnabled(false);
		addEmp.setEnabled(false);
		changePass.setEnabled(false);
		bt_search.setEnabled(false);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Logout"))
		{
			root.dispose();
			Home h = new Home();
			return;
		}
		else if(command.equals("addAdmin"))
		{
			new AddAdmin(Integer.parseInt(aid));
			return;
		}
		else if(command.equals("Search"))
		{
			String selected_cb = cbg.getSelectedCheckbox().getLabel()+"";
			// System.out.println(selected_cb);
			String keyword;
			if(selected_cb.equals(" Employee ID"))
			{
				// JOptionPane.showMessageDialog(null,"Comming here");
				keyword = tf_search.getText().trim();
				try
				{
					Integer.parseInt(keyword);
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,"Invalid Employee ID to Search !");
					return;
				}
				new SearchResultPage("Employee ID",keyword);
			}else if(selected_cb.equals(" Employee Name"))
			{
				JOptionPane.showMessageDialog(null,"Sorry ! we not yet ready !");
				return;
			}
		}
		else if(command.equals("hikeSalaries"))
		{
			new HikeSalaries();
		}
		else if(command.equals("updateEmp"))
		{
			new SelectUpdateType();
		}
		else if(command.equals("viewRequests"))
		{
			new ViewRequests();
		}
		else if(command.equals("showNotifications"))
		{
			new Notifications();
		}
		else if(command.equals("viewAttendance"))
		{
			new ViewAttendance();
		}
		else if(command.equals("postNotice"))
		{
			new PostNotification(Integer.parseInt(aid));
		}
		else if(command.equals("changePass"))
		{
			new ChangePassword();
		}
		else if(command.equals("addEmp"))
		{
			new AddNewEmployee();
		}
		else if(command.equals("showAll"))
		{
			new DisplayAllEmps();
		}
		else if(command.equals("delEmp"))
		{
			new DeleteEmployee();
		}
	}

	public void itemStateChanged(ItemEvent ie)
	{
		String activeCheckbox = cbg.getSelectedCheckbox().getLabel();
		if(activeCheckbox.equals(" Employee ID"))
		{
			
		}else if(activeCheckbox.equals(" Employee Name"))
		{
			
		}
		// }else if(activeCheckbox.equals(" Joining Date"))
		// {
		// 	name.setEnabled(false);
		// 	id.setEnabled(false);
		// 	JOptionPane.showMessageDialog(null,"You are about search By Joining Date !");
		// 	name.setEnabled(true);
		// 	id.setEnabled(true);
		// }
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new AdminPage("101");
			}
		});
	}
}