package JIRA_Adapter.JSONHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class string2FileWriter 
{
	public void writer(String JQLReturn) 
	{
		try 
		{  
        // TimeStamping the JSON Name 
        	String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
       	// Writing to a file  
            File file=new File("\\\\BLRsacha512418\\Shared_Space\\JIRA_2_KPI_DataDump\\TestJSON_"+timeStamp+".json");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            //System.out.print(JQLReturn);
  
            fileWriter.write(JQLReturn.toString());  
            fileWriter.flush();  
            fileWriter.close();  
          } 
		catch (IOException e) 
		{  
            e.printStackTrace();  
        }  
	}
}