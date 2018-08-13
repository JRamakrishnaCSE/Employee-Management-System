import java.util.*;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.sql.*;


class Extra 
{
	 Calendar cal = Calendar.getInstance();
	public  String getDate()
	{
		// if (true) {
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mill","root","Raakhi@123");
				Statement stmt = con.createStatement();
				ResultSet  rs;
				rs = stmt.executeQuery("select date(now());");
				rs.next();
				System.out.println(rs.getString(1));
				return rs.getString(1);
			}catch(Exception e)
			{
				return null;
			}
		// }
		// String predate = ""+cal.get(Calendar.YEAR)+"-0"+(1+cal.get(Calendar.MONTH))+"-"+cal.get(Calendar.DATE);
		// return predate;
	}

	public  String getTime()
	{
		String pretime = ""+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		return pretime;
	}

	public  String getSHA256(int data)
	{
		return getSHA256(""+data);
	}
	public  String getSHA256(String data)
	{
		try{
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(data.getBytes());
	
	        byte byteData[] = md.digest();
	
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	
	        //System.out.println("Hex format : " + sb.toString());
	
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	return ""+hexString.toString();
	    	//System.out.println("Hex format : " + hexString.toString());
	    }catch(Exception e)
	    {
	    	return null;
	    }
	}
	public  String getMD5(int data)
	{
		return ""+getMD5(""+data);
	}
	public  String getMD5(String input)
	{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	public  String SHA1(String data)
	{
		return null;
	}
}