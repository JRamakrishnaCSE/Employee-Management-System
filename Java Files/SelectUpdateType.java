import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectUpdateType implements ActionListener
{
	JFrame root;
	JButton bt_basic,bt_contact,bt_account;
	JLabel lb;
	SelectUpdateType()
	{
		root = new JFrame("Select Update Type");
		root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		root.setSize(348,250);
		root.setLayout(null);
		root.setResizable(false);
		root.setVisible(true);

		lb = new JLabel("Selec Update Type");
			lb.setBounds(100,10,150,30);
			lb.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb);

		bt_basic = new JButton("Basic Information");
			bt_basic.setBounds(10,60,150,40);
			bt_basic.addActionListener(this);
			bt_basic.setActionCommand("Basic");
			bt_basic.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(bt_basic);

		bt_contact = new JButton("Contact Information");
			bt_contact.setBounds(170,60,160,40);
			bt_contact.addActionListener(this);
			bt_contact.setActionCommand("Contact");
			bt_contact.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(bt_contact);

		bt_account = new JButton("Account / Billing Information");
			bt_account.setBounds(40,110,260,40);
			bt_account.addActionListener(this);
			bt_account.setActionCommand("Account");
			bt_account.setFont(new Font("Bahnschrift",Font.PLAIN,14));
			root.add(bt_account);
	}
	public void actionPerformed(ActionEvent ae)
	{
		String command = ae.getActionCommand();
		if(command.equals("Basic"))
		{
			root.dispose();
			new UpdateBasicInfo();
		}else if(command.equals("Contact"))
		{
			root.dispose();
			new UpdateContactInfo();
		}else if(command.equals("Account"))
		{
			root.dispose();
			new UpdateBillingInfo();
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new SelectUpdateType();
			}
		});
	}
}