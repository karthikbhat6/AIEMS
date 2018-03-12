package JIRA_Adapter.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import JIRA_Adapter.CRUD.JiraServices;

public class CSVtoArrayList 
{
	public static void main(String[] args) throws Exception 
	{
		BufferedReader Buffer = null;
		try 
		{
			String fileLine;
			Buffer = new BufferedReader(new FileReader("D:\\Tickets_Update.csv"));

			while ((fileLine = Buffer.readLine()) != null) 
			{
				System.out.println("Raw CSV data: " + fileLine);
				ArrayList<String> arrayList = fileCSVtoArrayList(fileLine);
				
				String [] fileResult = arrayList.toArray(new String[arrayList.size()]);
				
				System.out.println(arrayList2JSONObject(fileResult));
				
				JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_ademo", "Sapient_123");

				String issue = jrc.amendIssueWithFreeJSON("ELSDE-255", arrayList2JSONObject(fileResult));
				System.out.println(issue);
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (Buffer != null) Buffer.close();
			} 
			catch (IOException Exception) 
			{
				Exception.printStackTrace();
			}
		}
	}
	
	// Utility which converts CSV to ArrayList using Split Operation
	public static ArrayList<String> fileCSVtoArrayList(String fileCSV) 
	{
		ArrayList<String> fileResult = new ArrayList<String>();

		if (fileCSV != null) 
		{
			String[] splitData = fileCSV.split("\\s*,\\s*");
			for (int i = 0; i < splitData.length; i++) 
			{
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) 
				{
					fileResult.add(splitData[i].trim());
				}
			}
		}
		return fileResult;
	}
	
	public static String arrayList2JSONObject(String [] fileResult)
	{
		JSONObject JSONResult = new JSONObject();
		JSONResult.put("priority",new JSONObject().put("id", fileResult[4].toString()));
/*		JSONResult.put("assignee", fileResult[5]);
		JSONResult.put("versions", fileResult[6]);
		JSONResult.put("fixVersions", fileResult[7]);
		JSONResult.put("components",new JSONArray().put(new JSONObject().put("id",fileResult[8].toString())));
		JSONResult.put("customfield_10105", fileResult[11]); // Risk/ Issue Category
		JSONResult.put("customfield_10107", fileResult[12]); // Internal/ External
		JSONResult.put("customfield_12300", fileResult[13]); // Status Detail
*/		
		return JSONResult.toString();
	}
}