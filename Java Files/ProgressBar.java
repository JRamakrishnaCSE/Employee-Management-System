import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.applet.*;


public class ProgressBar implements Runnable
{
	Thread t;
	public boolean status=true;
	public void close()
	{
		status = false;
		root.dispose();
	}
	private String context;
	private String message;
	public void setContext(String context)
	{
		this.context=context;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public void createProgressBar(String context,String message)
	{
		this.context = context;
		this.message = message;
		root = new JFrame("ProgressBar");
			root.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			root.setSize(350,170);
			root.setLayout(null);
			root.setResizable(false);
			root.setVisible(true);

		lb1 = new JLabel(context);
			lb1.setBounds(20,10,200,30);
			lb1.setFont(new Font("Bahnschrift",Font.PLAIN,15));
			root.add(lb1);

		array = new JLabel(s);//[] = new JLabel[100];
			array.setBounds(20,55,280,30);
			array.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(array);
		percent = new JLabel("0 %");
			percent.setBounds(285,55,40,30);
			percent.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(percent);

		lb2 = new JLabel(message);
			lb2.setBounds(20,100,300,30);
			lb2.setFont(new Font("Bahnschrift",Font.PLAIN,16));
			root.add(lb2);

		t = new Thread(this,"Child Thread");
		t.start();
		// System.out.println("At the end");
	}
	// ProgressBar(String context,String message)
	// {
	// }
	boolean flag=false;
	JFrame root;
	JLabel lb1,lb2,percent;
	String s="- - - - - - - - - - - - - - - - - - - - - - - -";
	JLabel array;
	public void run()
	{
		String s="+ ";
		try
		{
			for (int i=0;i<24;i+=1)
			{
				array.setText(s);
				s=s+"+ ";
				percent.setText((int)(i*4.35)+"%");
				Thread.sleep(50);
			}
			// flag=true;
			close();
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		(new ProgressBar()).createProgressBar("context","message");
		// SwingUtilities.invokeLater(new Runnable()
		// {
		// 	public void run()
		// 	{
		// 		ProgressBar pb = new ProgressBar("Registering New Employee","Please wait , We have almost Completed !");
		// 	}
		// });
	}
}