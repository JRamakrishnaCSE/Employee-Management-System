import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;

public class LoginActivityDetails extends JFrame implements ActionListener
{
	JFrame root;
	JButton bt;

	Connection con;
	Statement stmt;

	JLabel lb1,lb2,lb3,lb4,lb5,lb6;
	JLabel lb7,lb8;//to show user info

	LoginActivityDetails(int user,String type,String login,String change,String fail)
	{
		root = new JFrame("Last Login Activity Details");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(450,350);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		prepare(user,type);
		init();

		lb2.setText(login);
		lb4.setText(change);
		lb6.setText(fail);
		lb8.setText(""+user);
	}
	Font lb_Font = new Font("Bahnschrift",Font.PLAIN,17),out_Font = new Font("Bahnschrift",Font.BOLD,19);
	JLabel mLab;

	public void init()
	{
		mLab = new JLabel("Last Login Activity Details");
			mLab.setBounds(90,20,330,30);
			mLab.setFont(new Font("Bahnschrift",Font.PLAIN,22));
			root.add(mLab);

		lb7 = new JLabel("User ID                                       : ");
			lb7.setBounds(30,70,270,30);
			lb7.setFont(lb_Font);
			root.add(lb7);

		lb8 = new JLabel("");
			lb8.setFont(out_Font);
			lb8.setBounds(220,70,100,30);
			root.add(lb8);

		lb1 = new JLabel("Logged In on                          : ");
			lb1.setBounds(30,110,220,30);
			lb1.setFont(lb_Font);
			root.add(lb1);
		

		lb2 = new JLabel(" ");
			lb2.setBounds(220,110,200,30);
			lb2.setFont(out_Font);
			root.add(lb2);
		
		lb3 = new JLabel("Password Changed on  : ");
			lb3.setBounds(30,150,210,30);
			lb3.setFont(lb_Font);
			root.add(lb3);
		// if (true) return;
		lb4 = new JLabel(" ");
			lb4.setBounds(220,150,200,30);
			lb4.setFont(out_Font);
			root.add(lb4);

		lb5 = new JLabel("Login Failed on                     : ");
			lb5.setBounds(30,190,220,30);
			lb5.setFont(lb_Font);
			root.add(lb5);
		lb6 = new JLabel(" ");
			lb6.setBounds(220,190,200,30);
			lb6.setFont(out_Font);
			root.add(lb6);

		bt = new JButton("Confirm");
			bt.setBounds(90,250,250,40);
			bt.setFont(lb_Font);
			bt.addActionListener(this);
			root.add(bt);
	}

	public void prepare(int user,String type)
	{
		init();
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Confirm")) root.dispose();
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new LoginActivityDetails(101,"admin","2018-07-02  06:51:08","2018-07-02  06:51:08","2018-07-02  06:51:08");
			}
		});
	}
}