package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//这个类用于记录一些常用的变量
public class ValidConfig {
	public static String JSFile = "";
	public static boolean RunThread = true;
	
	static
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/config.js")));
		String line = null;
		
		try {
			try{
				while((line = br.readLine()) != null)
				{
					JSFile = JSFile.concat(line);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
