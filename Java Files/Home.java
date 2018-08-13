import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
interface MyInterFace
{
	public boolean connect(String d,String u,String p);
	public String randString(int n);
	public void init();
	public void prepareSystem();
}
public class Home extends JFrame implements MyInterFace,ActionListener
{
	JFrame root;

	private JTextField username,captcha,dup;
	private JPasswordField password;
	private	JLabel lb1,lb2,lb3;
	private	JButton login,reset,refresh;

	private Font currentFont;

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	Extra extra;
	Font bahnschriftPLAIN16 = new Font("Bahnschrift",Font.PLAIN,16);

	Home()
	{
		root = new JFrame("Login Area");
		root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		root.setSize(350,400);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		extra = new Extra();

		prepareSystem();
	}
	public void prepareSystem()
	{
		init();
		boolean status = connect("mill","root","Raakhi@123");
		if (status) return;
		if(!status) JOptionPane.showMessageDialog(null,"Database Connection Error!");
		else JOptionPane.showMessageDialog(null,"Connection! Success!");
	}
	public void init()
	{
		lb1 = new JLabel("Username");
		lb1.setBounds(70,30,100,30);
		lb1.setFont(bahnschriftPLAIN16);
		root.add(lb1);

		username = new JTextField();
		username.setBounds(70,60,200,30);
		username.setFont(bahnschriftPLAIN16);

		root.add(username);

		lb2 = new JLabel("Password");
		lb2.setBounds(70,90,100,30);
		lb2.setFont(bahnschriftPLAIN16);
		root.add(lb2);

		password = new JPasswordField();
		password.setBounds(70,120,200,30);
		root.add(password);

		dup = new JTextField();
		dup.setBounds(70,160,100,40);
		// dup.setEditable(false);
		dup.setEnabled(false);
		dup.setText("  "+randString(7));
		dup.setFont(new Font("Bahnschrift",Font.PLAIN,17));
		root.add(dup);

		refresh =  new JButton("Refresh");
		refresh.setBounds(170,160,100,40);
		refresh.addActionListener(this);
		refresh.setFont(bahnschriftPLAIN16);
		root.add(refresh);
		
		lb3 = new JLabel("Enter above Captch here");
		lb3.setBounds(70,200,200,30);
		lb3.setFont(bahnschriftPLAIN16);
		root.add(lb3);

		captcha = new JTextField();
		captcha.setBounds(70,230,200,40);
		captcha.setFont(bahnschriftPLAIN16);
		root.add(captcha);

		login = new JButton("Login");
		login.setBounds(70,280,100,40);
		login.addActionListener(this);
		login.setFont(bahnschriftPLAIN16);
		root.add(login);

		reset = new JButton("Reset");
		reset.setBounds(170,280,100,40);
		reset.addActionListener(this);
		reset.setFont(bahnschriftPLAIN16);
		root.add(reset);
	}

	public void actionPerformed(ActionEvent ae)
	{
		//if(true) return;
		String command = ae.getActionCommand();
		if(command.equals("Refresh")) dup.setText("  "+randString(7));
		else if(command.equals("Login"))
		{
			int u=000;
			String pass="";
			String query="";
			if((dup.getText().trim()).equals(captcha.getText())){}
			else
			{
				JOptionPane.showMessageDialog(null,"Captch dosen't matched!");
				dup.setText("  "+randString(7));
				return ;
			}
			
			try
			{
				pass = password.getText();
				u=Integer.parseInt(username.getText());
			}catch(Exception e)
			{
				dup.setText("  "+randString(7));
				JOptionPane.showMessageDialog(null,"Invalid Username or Password!");
				return;
			}

			try
			{
				if(checkLoginWith(u,pass,"emp"))
				{
					try
					{
						Log objLog = getLoginActivityDetails(u,"emp");
						onSuccess_UpdateDateAndTime(u,"emp");
						JOptionPane.showMessageDialog(null,"Login Success!");
						root.dispose();
						EmployeePage ep = new EmployeePage(u);
						LoginActivityDetails lad = new LoginActivityDetails(u,"emp",objLog.login,objLog.change,objLog.fail);
						con.close();
					}
					catch(Exception e)
					{

					}
				}else if(checkLoginWith(u,pass,"admin"))
				{
					try
					{
						Log objLog = getLoginActivityDetails(u,"admin");
						onSuccess_UpdateDateAndTime(u,"admin");
						JOptionPane.showMessageDialog(null,"Login Success!");
						root.dispose();
						AdminPage ap = new AdminPage(u+"");
						LoginActivityDetails lad = new LoginActivityDetails(u,"admin",objLog.login,objLog.change,objLog.fail);
						con.close();
					}
					catch(Exception e)
					{

					}
				}
				else
				{
					onFail_UpdateDateAndTime(u,getType(u));
					dup.setText("  "+randString(7));
					JOptionPane.showMessageDialog(null,"Invalid Username or Password!");
					return;
				}
			}catch(Exception e){
				dup.setText("  "+randString(7));
				JOptionPane.showMessageDialog(null,"Input Fields Should not be Empty!");
			}
		}
		else if(command.equals("Reset"))
		{
			username.setText("");
			password.setText("");
			captcha.setText("");
			dup.setText("  "+randString(7));
		}
	}
	
	public Log getLoginActivityDetails(int uid,String type)
	{
		try
		{
			Log objLog=null;
			String login="",fail="",change="";
			{
				String query = "select last_login_date,last_login_time,last_login_failed_date,last_login_failed_time,password_changed_on,password_changed_time from "+type+"_logs where "+type+"ID="+uid+";";
				rs = stmt.executeQuery(query);
				rs.next();
				login = rs.getDate(1)+"  "+rs.getTime(2);
				fail = rs.getDate(3)+"  "+rs.getTime(4);
				change = rs.getDate(5)+"  "+rs.getTime(6);
				objLog = new Log(login,fail,change);
			}
			return objLog;
		}
		catch(Exception e){
			return null;
		}
	}

	public boolean checkLoginWith(int uid,String pass,String type)
	{
		try
		{
			String query="select * from "+type+" where "+type+"ID='"+uid+"' and ";
			query = query + "password=sha2('"+pass+"',256);";
			rs = stmt.executeQuery(query);
			try
			{
				rs.next();
				rs.getInt(1);
				return true;
			}catch(Exception ee){
				return false;
			}
		}catch(Exception e)
		{
			return false;
		}
	}
	public void onSuccess_UpdateDateAndTime(int uid,String type)
	{
		try
		{
			String query = "update "+type+"_logs set last_login_date=date(now()), last_login_time=time(now()) where "+type+"ID="+uid+";";
			int ec=stmt.executeUpdate(query);
		}catch(Exception e){}
	}
	public void onFail_UpdateDateAndTime(int uid,String type)
	{
		try
		{
			String query = "update "+type+"_logs set last_login_failed_date='"+
					(new Extra()).getDate()+"' , last_login_failed_time='"+(new Extra()).getTime()+"' where "+type+"ID="+uid+";";
			int ec=stmt.executeUpdate(query);
		}catch(Exception e){}
	}

	public static void main(String[] args) {
		new Setup();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Home();
			}
		});
	}
	private char rand()
	{
		Random r = new Random();
		int i=r.nextInt(91);
		if(i>=65 && i<=90) return (char)i;
		else return rand();
	}
	public String randString(int n)
	{
		String s="";
		int i=0;
		while(i<n)
		{
			s=s+""+rand();
			i++;
		}
		return s;
	}
	public boolean connect(String database,String user,String pass)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/"+database;
			
			con = DriverManager.getConnection(url,user,pass);
			stmt = con.createStatement();

			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public String getType(int uid)
	{
		try
		{
			rs = stmt.executeQuery("select * from emp where empID="+uid+";");
			rs.next();
			rs.getInt(1);
			return "emp";
		}catch(Exception e)
		{
			try
			{
				rs = stmt.executeQuery("select * from admin where adminID="+uid+";");
				rs.next();
				rs.getInt(1);
				return "admin";
			}catch(Exception ee){return "emp";}
		}
	}
}
class Log
{
	String login,fail,change;
	Log(String login,String fail,String change)
	{
		this.login=login;
		this.fail=fail;
		this.change=change;
	}
}