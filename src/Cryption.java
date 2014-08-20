
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Cryption {
	private static SecretKeySpec key;
	private static File encryptedFile = new File("PasswordKeeper.txt");
	
	public String toMD5(String password)
	{
		String passwordToHash = password;
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes 
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		
		return generatedPassword;
	}//end toMD5 method
	
	public void toSHA (String password)
	{
		String passphrase = password;
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		digest.update(passphrase.getBytes());
		key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
	}
	
	  public String readFromFile()
	  {
		//Reading from a file into an array of bytes
		FileInputStream fileInputStream = null;
		String clearText = null;
		byte[] bFile = new byte[(int) encryptedFile.length()];
		try {
			fileInputStream = new FileInputStream(encryptedFile);
			fileInputStream.read(bFile);
			fileInputStream.close();
		}catch(Exception e){
	        e.printStackTrace();
	    }
		
		clearText = decryptByteArray(bFile);
		return clearText;
	  }
	  
	  public String decryptByteArray(byte[] bFile)
	  {
		  Cipher aes;
		  String clearText=null;

			try {
				aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
				aes.init(Cipher.DECRYPT_MODE, key);
				clearText = new String(aes.doFinal(bFile));
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
				System.out.println("Error in decryptByteArray");
				e.printStackTrace();
			}
			return clearText;
	  }
	  
	  public void encryptString(String clearText)
	  {
		  Cipher aes;
		  byte[] cipherText = null;
			try {
				aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
				aes.init(Cipher.ENCRYPT_MODE, key);
				cipherText = aes.doFinal(clearText.getBytes());
			} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
				System.out.println("error in encryptString");
				e.printStackTrace();
			}
			
			writeToFile(cipherText);
	  }
	  
	  private void writeToFile(byte[] cipherText)
	  {
		//taking an array of bytes(cipherText) and writing to a file
			FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(encryptedFile);
			    fileOutputStream.write(cipherText);
			    fileOutputStream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
	  }
}
