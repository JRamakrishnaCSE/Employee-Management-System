import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddAdmin implements ActionListener,ItemListener
{
	JFrame root;
	JLabel title,sub_title;
	private int added_by;
	AddAdmin(int added_by)
	{
		this.added_by=added_by;
		connect();
		root = new JFrame("Add New Admin");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,450);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(95,5,420,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,21));
			root.add(title);

		sub_title = new JLabel("Add a New Admin");
			sub_title.setBounds(170,40,150,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,17));
			root.add(sub_title);

		lb2 = new JLabel("New Admin ID              :");
			lb2.setBounds(40,80,180,30);
			lb2.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb2);

		tf_new_addmin_id = new JTextField();
			tf_new_addmin_id.setBounds(200,80,200,30);
			tf_new_addmin_id.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			tf_new_addmin_id.setEditable(false);
			root.add(tf_new_addmin_id);

		lb1 = new JLabel("Adding by                        :");
			lb1.setBounds(40,120,180,30);
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb1);

		tf_adding_by = new JTextField();
			tf_adding_by.setBounds(200,120,200,30);
			tf_adding_by.setText(""+added_by);
			tf_adding_by.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			tf_adding_by.setEditable(false);
			root.add(tf_adding_by);

		lb3 = new JLabel("New Admin Name     :");
			lb3.setBounds(40,160,180,30);
			lb3.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb3);

		tf_admin_name = new JTextField();
			tf_admin_name.setBounds(200,160,200,30);
			tf_admin_name.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_admin_name);

		cb = new JCheckBox("  I having admin id "+added_by+" agreeing that I'll be answerable ");
			cb.setBounds(40,205,400,30);
			cb.addItemListener(this);
			cb.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(cb);

		lb3 = new JLabel(" for the future actions of this new admin .");
			lb3.setBounds(65,235,350,30);
			lb3.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb3);

		bt_cancel = new JButton("Cancel");
			bt_cancel.setBounds(105,285,100,40);
			bt_cancel.addActionListener(this);
			bt_cancel.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_cancel);

		bt_add = new JButton(" + Commit");
			bt_add.setBounds(230,285,100,40);
			bt_add.addActionListener(this);
			bt_add.setActionCommand("Commit");
			bt_add.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			bt_add.setEnabled(false);
			root.add(bt_add);

		try
		{
			rs = stmt.executeQuery("select max(adminID)+1 from admin;");
			rs.next();
			tf_new_addmin_id.setText(""+rs.getInt(1));
		}catch(Exception e){
			System.out.println(e);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Commit")){
			if(tf_admin_name.getText().trim().length()<3)
			{
				JOptionPane.showMessageDialog(null,"Invalid new admin name or too small !");
				return;
			}
			if(tf_admin_name.getText().trim().indexOf('\'')!=-1)
			{
				JOptionPane.showMessageDialog(null,"Name Should not Consists of Special Characters !");
				return;
			}
			try
			{
				int newID=Integer.parseInt(tf_new_addmin_id.getText().trim());
				String name=tf_admin_name.getText().trim();
				String query = "insert into admin values("+newID+",sha2(123,256),'"+name+"','',date(now()),'Admin');";
				int ec = stmt.executeUpdate(query);
				if(ec==1){
					query = "insert into metadata values("+newID+","+added_by+",now());";
					stmt.executeUpdate(query);
					
					query = "insert into admin_logs(adminID) values("+newID+");";
					stmt.executeUpdate(query);
					JOptionPane.showMessageDialog(null,"New admin is added successfully.");
					root.dispose();
					return;
				}
			}catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Unable to add Right now !");
				return;
			}
		}
		else if(command.equals("Cancel"))
		{
			root.dispose();
			return;
		}
	}
	public void itemStateChanged(ItemEvent ie)
	{
		if(cb.isSelected())
		{
			bt_add.setEnabled(true);
		}else
		{
			bt_add.setEnabled(false);
		}
	}
	
	JTextField tf_new_addmin_id,tf_adding_by,tf_admin_name;
	JLabel lb1,lb2,lb3,lb4;
	JCheckBox cb;
	JButton bt_cancel,bt_add;

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new AddAdmin(101);
			}
		});
	}
	private void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
		}catch(Exception e){}
	}
}