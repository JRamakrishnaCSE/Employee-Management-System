import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;

public class EmployeePage implements ActionListener
{
	JFrame root;
	Font font;
	JLabel title,lb1,lb_request,lb_subject,lb_description;
	JButton bt_logout,bt_choose,bt_mark,bt_clear,bt_post;
	JTextField tf_path,tf_subject,tf_desctiption;
	JTextArea jta;

	// File f;
	FileWriter fw;
	File inFile;
	JLabel lb_notice,lb_notif_id,lb_notice_subject,lb_posted_on;
	JButton bt_view_all;

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private int uid;
	private int year,month,day;
	boolean flag=false;
	EmployeePage(int user)
	{
		this.uid=user;
		try
		{
			connect();
			rs = stmt.executeQuery("select status,year(date(now())),month(date(now())),day(date(now())) from attendance where empID="+user+" and date=date(now());");
			rs.next();
			if(rs.getString(1).equals("absent"))
			{
				year = rs.getInt(2);
				month = rs.getInt(3);
				day = rs.getInt(4);

				String fileName="MSS_"+user+"_"+year+"_"+month+"_"+day;
				fw = new FileWriter("C://Users//CoDeCoDer//Desktop//Intern//EmpManagementSystem//"+fileName+".txt");
				inFile = new File(fileName+".txt");

				prepareFile(fw,inFile,user,fileName);
			}else if(rs.getString(1).equals("present"))
			{
				flag=true;
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Please Login Again !");
			return;
		}

		root = new JFrame("Employee Page");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(500,500);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		font = new Font("Bahnschrift",Font.PLAIN,18);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(90,5,420,40);
			title.setFont(font);
			root.add(title);

		lb1 = new JLabel("Logged In as @ "+user);
			lb1.setBounds(250,40,150,30);
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb1);

		bt_logout = new JButton("Logout");
			bt_logout.setFont(new Font("Calibri",Font.ITALIC,15));
			bt_logout.setBounds(380,45,80,22);
			bt_logout.addActionListener(this);
			root.add(bt_logout);

		tf_path = new JTextField();
			tf_path.setBounds(30,90,283,30);
			tf_path.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(tf_path);

		bt_choose = new JButton("Choose File");
			bt_choose.setBounds(315,90,145,30);
			bt_choose.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			bt_choose.addActionListener(this);
			root.add(bt_choose);

		bt_mark = new JButton("Mark Attendance");
			bt_mark.setBounds(105,130,145,30);
			bt_mark.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			bt_mark.addActionListener(this);
			root.add(bt_mark);

		if(flag)
		{
			bt_mark.setEnabled(false);
			bt_mark.setText("Already Marked");
		}

		lb_notice = new JLabel("Recent Notifications :");
			lb_notice.setBounds(30,180,200,30);
			lb_notice.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_notice);

		bt_view_all = new JButton("View All Notices");
			bt_view_all.setBounds(305,183,155,25);
			bt_view_all.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			bt_view_all.addActionListener(this);
			root.add(bt_view_all);

		lb_notif_id = new JLabel("Notice ID");
			lb_notif_id.setBounds(30,210,70,40);
			lb_notif_id.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_notif_id);

		lb_notice_subject = new JLabel("Subject");
			lb_notice_subject.setBounds(110,210,250,40);
			lb_notice_subject.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_notice_subject);

		lb_posted_on = new JLabel("Posted On");
			lb_posted_on.setBounds(390,210,100,40);
			lb_posted_on.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(lb_posted_on);

		fetchNewNotices();

		lb_services = new JLabel("Services Available : ");
			lb_services.setBounds(30,310,170,40);
			lb_services.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			root.add(lb_services);

		bt_send_request = new JButton("Send a Request ");
			bt_send_request.setBounds(50,355,180,30);
			bt_send_request.addActionListener(this);
			bt_send_request.setActionCommand("Request");
			bt_send_request.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_send_request);

		bt_change_pass = new JButton("Change My Password");
			bt_change_pass.setBounds(250,355,180,30);
			bt_change_pass.addActionListener(this);
			bt_change_pass.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(bt_change_pass);
	}
	JLabel lb_services;
	JButton bt_send_request,bt_change_pass;
	private void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();
		}catch(Exception e){}
	}
	private void fetchNewNotices()
	{
		try
		{
			rs = stmt.executeQuery("select * from notification order by notice_id desc limit 2;");
			int i=30,y=30;
			while(rs.next())
			{
				JLabel lb_temp_notice_id = new JLabel(rs.getInt(1)+"  *");
					lb_temp_notice_id.setBounds(40,i+215,70,y);
					lb_temp_notice_id.setFont(new Font("Bahnschrift",Font.PLAIN,13));
					root.add(lb_temp_notice_id);

				JLabel lb_temp_subject = new JLabel(""+rs.getString(2));
					lb_temp_subject.setBounds(110,i+215,250,y);
					lb_temp_subject.setFont(new Font("Bahnschrift",Font.PLAIN,13));
					root.add(lb_temp_subject);

				JLabel lb_temp_posted_on = new JLabel(""+rs.getDate(3));
					lb_temp_posted_on.setBounds(390,i+215,100,y);
					lb_temp_posted_on.setFont(new Font("Bahnschrift",Font.PLAIN,13));
					root.add(lb_temp_posted_on);

				i=i+30;
			}
		}catch(Exception e)
		{

		}
	}
	private void markAttendance(LogFile logFile)
	{
		try
		{
			Scanner sc = new Scanner(logFile.inputFile);
			String line;
			if(sc.hasNextLine())
			{
				line = sc.nextLine();
				if((new Extra()).getSHA256(logFile.fileName).equals(line))
				{
				}
				else{
					JOptionPane.showMessageDialog(null,"The file is Edited or Corrupted !\n Try Again !");
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null,"The file is Edited or Corrupted !\n Try Again !");
				return;
			}

			if(sc.hasNextLine())
			{
				line = sc.nextLine();
				if((new Extra()).getMD5((new Extra()).getDate()+"").equals(line))
				{
				}
				else{
					JOptionPane.showMessageDialog(null,"The file is Edited or Corrupted !\n Try Again !");
					return;
				}
			}else
			{
				JOptionPane.showMessageDialog(null,"The file is Edited or Corrupted !\n Try Again !");
				return;
			}

			if(sc.hasNextLine())
			{
				line = sc.nextLine();
				rs = stmt.executeQuery("select sha2("+logFile.user+",512);");
				rs.next();
				if(rs.getString(1).equals(line))
				{
				}
				else{
					JOptionPane.showMessageDialog(null,"The file is Edited or Corrupted !\n Try Again !");
					return;
				}
			}else
			{
				JOptionPane.showMessageDialog(null,"The file is Edited or Corrupted !\n Try Again !");
					return;
			}

			int ec = stmt.executeUpdate("update attendance set status='present' where empID="+logFile.user+";");
			if(ec==1)
			{
				bt_mark.setText("Already Marked");
				bt_mark.setEnabled(false);
				JOptionPane.showMessageDialog(null,"Attendance Marked Successfully !");
			}
		}catch(Exception e){
			String mess = "The File is not found in the directory. It might moved somewhere or \n                  Deleted Permanently. Please Login Again !";
			JOptionPane.showMessageDialog(null,mess);
		}
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Mark Attendance"))
		{
			if(tf_path.getText().trim().length()<1)
			{
				// JOptionPane.showMessageDialog(null,"Invalid File Path!");
				// return;
			}
			markAttendance(lf);
		}
		else if(command.equals("Logout"))
		{
			try
			{
				con.close();
			}catch(Exception e)
			{
			}
			root.dispose();
			new Home();
			return;
		}else if(command.equals("View All Notices"))
		{
			new Notifications();
			return;
		}else if(command.equals("Request"))
		{
			// System.out.println("Coming into send Request");
			new SendRequest(uid);
			return;
		}else if(command.equals("Change My Password"))
		{
			// System.out.println("Coming into change pass");
			new ChangePassword(uid);
			return;
		}
	}
	

	private LogFile lf;
	private void prepareFile(FileWriter fw,File inputFile,int user,String fileName)
	{
		try
		{
			fw.write(""+(new Extra()).getSHA256(fileName)+"\n");// hashing filename with sha256
			
			rs = stmt.executeQuery("select date(now());");
			rs.next();
			fw.write(""+(new Extra()).getMD5(""+rs.getDate(1))+"\n");// hashing date with md5
			
			rs = stmt.executeQuery("select sha2("+user+",512);"); 
			rs.next();
			fw.write(""+rs.getString(1)); // hashing user id with sha512

			lf = new LogFile(inputFile,fileName,user);
			fw.close();
		}catch(Exception e)
		{
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new EmployeePage(1002);
			}
		});
	}
}
class LogFile
{
	File inputFile;
	String fileName;
	int user;
	LogFile(File inputFile,String fileName,int user)
	{
		this.inputFile=inputFile;
		this.fileName=fileName;
		this.user=user;
	}
}