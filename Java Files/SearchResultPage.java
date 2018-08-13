import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.*;
public class SearchResultPage
{
	JFrame root;

	JLabel lb_search_by,lb_search_by_output;
	JLabel lb_basic_Info,lb_empID,lb_empName,lb_joinedOn,lb_email;
	JLabel lb_contact_Info,lb_dno,lb_village,lb_mandal,lb_district,lb_state,lb_pincode,lb_phone;
	JLabel lb_account_Info,lb_acc_num,lb_acc_holder,lb_bank_name,lb_ifsc;

	JLabel title,sub_title;
	JLabel lb_keyword,lb_keyword_output;

	SearchResultPage(String searched_by,String keyword)
	{
		root = new JFrame("Search Results");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(600,700);
		root.setLayout(null);
		root.setResizable(false);
		// root.setVisible(true);

		title = new JLabel("Millennium Software Solutions");
			title.setBounds(130,0,520,40);
			title.setFont(new Font("Bahnschrift",Font.PLAIN,23));
			root.add(title);

		sub_title = new JLabel("Search Page");
			sub_title.setBounds(240,30,200,30);
			sub_title.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			root.add(sub_title);

		lb_search_by = new JLabel("Searched By        : ");
			lb_search_by.setBounds(10,70,140,30);
			lb_search_by.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_search_by);

		lb_search_by_output = new JLabel(""+searched_by);
			lb_search_by_output.setBounds(150,70,140,30);
			lb_search_by_output.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_search_by_output);

		lb_keyword = new JLabel("Keyword  :");
			lb_keyword.setBounds(410,70,80,30);
			lb_keyword.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_keyword);

		lb_keyword_output = new JLabel(""+keyword);
			lb_keyword_output.setBounds(490,70,140,30);
			lb_keyword_output.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb_keyword_output);


		lb_basic_Info = new JLabel("Basic Information           :");
			lb_basic_Info.setBounds(10,110,220,30);
			lb_basic_Info.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			root.add(lb_basic_Info);

		lb_empID = new JLabel("Employee  ID                 : ");
			lb_empID.setBounds(50,140,220,30);
			lb_empID.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_empID);

		lb_empName = new JLabel("Employee  Name        : ");
			lb_empName.setBounds(50,170,220,30);
			lb_empName.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_empName);

		lb_joinedOn = new JLabel("Joined  On                      : ");
			lb_joinedOn.setBounds(50,200,220,30);
			lb_joinedOn.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_joinedOn);

		lb_email = new JLabel("E-mail                              : ");
			lb_email.setBounds(50,230,220,30);
			lb_email.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_email);

		lb_contact_Info = new JLabel("Contact Information     :");
			lb_contact_Info.setBounds(10,260,220,30);
			lb_contact_Info.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			root.add(lb_contact_Info);

		lb_dno = new JLabel("Door Number               : ");
			lb_dno.setBounds(50,290,220,30);
			lb_dno.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_dno);

		lb_village = new JLabel("Village / Town              : ");
			lb_village.setBounds(50,320,220,30);
			lb_village.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_village);

		lb_mandal = new JLabel("Mandal / City                : ");
			lb_mandal.setBounds(50,350,220,30);
			lb_mandal.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_mandal);

		lb_district = new JLabel("District                            : ");
			lb_district.setBounds(50,380,220,30);
			lb_district.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_district);

		lb_state = new JLabel("State                                 : ");
			lb_state.setBounds(50,410,220,30);
			lb_state.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_state);

		lb_pincode = new JLabel("Pincode                           : ");
			lb_pincode.setBounds(50,440,220,30);
			lb_pincode.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_pincode);

		lb_phone = new JLabel("Phone Number            : ");
			lb_phone.setBounds(50,470,220,30);
			lb_phone.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_phone);

		lb_account_Info = new JLabel("Account Information   :");
			lb_account_Info.setBounds(10,500,220,30);
			lb_account_Info.setFont(new Font("Bahnschrift",Font.PLAIN,18));
			root.add(lb_account_Info);

		lb_acc_num = new JLabel("Account Number        : ");
			lb_acc_num.setBounds(50,530,220,30);
			lb_acc_num.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_acc_num);

		lb_acc_holder = new JLabel("Account Holder           : ");
			lb_acc_holder.setBounds(50,560,220,30);
			lb_acc_holder.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_acc_holder);

		lb_bank_name = new JLabel("Bank Name                   : ");
			lb_bank_name.setBounds(50,590,220,30);
			lb_bank_name.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_bank_name);

		lb_ifsc = new JLabel("IFSC Code                       : ");
			lb_ifsc.setBounds(50,620,220,30);
			lb_ifsc.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb_ifsc);

		fetchData("Employee ID",keyword);
	}
	void fetchData(String searched_by,String keyword)
	{
		String query1 = "";
		String query2 = "";
		String query3 = "";
		if(searched_by.equals("Employee ID"))
		{
			int uid = Integer.parseInt("0"+keyword);
			query1 = "select * from emp where empID="+uid+";";
			query2 = "select dno,village,mandal,district,state,pincode,phone from address where empID="+uid+";";
			query3 = "select acc_num,acc_holder,bank_name,ifsc from account where empID="+uid+";";
		}else if(searched_by.equals("Employee Name"))
		{
			String empName=searched_by.trim();
			query1 = "select * from emp where empName like('%"+empName+"%');";
			query2 = "select * from address where empID="+3+";";
			query3 = "select * from account where empID="+4+";";
		}else if(searched_by.equals("Joining Date"))
		{

		}

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
			stmt = con.createStatement();

			// Basic Info setting
			{
				rs = stmt.executeQuery(query1);
				
				try{
					rs.next();

					// System.out.print("Comming here");
					JLabel temp_empID = new JLabel(""+rs.getInt(1));
						temp_empID.setBounds(210,140,220,30);
						temp_empID.setFont(new Font("Bahnschrift",Font.BOLD,15));
						root.add(temp_empID);

					JLabel temp_empName = new JLabel(""+rs.getString(3));
						temp_empName.setBounds(210,170,220,30);
						temp_empName.setFont(new Font("Bahnschrift",Font.BOLD,15));
						root.add(temp_empName);

					JLabel temp_joinedOn = new JLabel(""+rs.getDate(5));
						temp_joinedOn.setBounds(210,200,220,30);
						temp_joinedOn.setFont(new Font("Bahnschrift",Font.BOLD,15));
						root.add(temp_joinedOn);

					JLabel temp_email = new JLabel(""+rs.getString(4));
						temp_email.setBounds(210,230,220,30);
						temp_email.setFont(new Font("Bahnschrift",Font.BOLD,15));
						root.add(temp_email);

					root.setVisible(true);
				}catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,"No Data Found With that ID !");
					return;
				}
			}
			// Contact Info setting
			{
				rs = stmt.executeQuery(query2);
				rs.next();

				JLabel temp_dno = new JLabel(""+rs.getString(1));
					temp_dno.setBounds(210,290,220,30);
					temp_dno.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_dno);

				JLabel temp_village = new JLabel(""+rs.getString(2));
					temp_village.setBounds(210,320,220,30);
					temp_village.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_village);

				JLabel temp_mandal = new JLabel(""+rs.getString(3));
					temp_mandal.setBounds(210,350,220,30);
					temp_mandal.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_mandal);

				JLabel temp_district = new JLabel(""+rs.getString(4));
					temp_district.setBounds(210,380,220,30);
					temp_district.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_district);

				JLabel temp_state = new JLabel(""+rs.getString(5));
					temp_state.setBounds(210,410,220,30);
					temp_state.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_state);

				JLabel temp_pincode = new JLabel(""+rs.getInt(6));
					temp_pincode.setBounds(210,440,220,30);
					temp_pincode.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_pincode);

				JLabel temp_phone = new JLabel(""+rs.getLong(7));
					temp_phone.setBounds(210,470,220,30);
					temp_phone.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_phone);
			}
			// Account Info setting
			{
				rs = stmt.executeQuery(query3);
				rs.next();

				JLabel temp_acc_num = new JLabel(""+rs.getLong(1));
					temp_acc_num.setBounds(210,530,220,30);
					temp_acc_num.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_acc_num);

				JLabel temp_acc_holder = new JLabel(""+rs.getString(2));
					temp_acc_holder.setBounds(210,560,220,30);
					temp_acc_holder.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_acc_holder);

				JLabel temp_bank_name = new JLabel(""+rs.getString(3));
					temp_bank_name.setBounds(210,590,220,30);
					temp_bank_name.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_bank_name);

				JLabel temp_ifsc = new JLabel(""+rs.getString(4));
					temp_ifsc.setBounds(210,620,220,30);
					temp_ifsc.setFont(new Font("Bahnschrift",Font.BOLD,15));
					root.add(temp_ifsc);
			}

		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new SearchResultPage("Employee ID","1001");
			}
		});
	}
}