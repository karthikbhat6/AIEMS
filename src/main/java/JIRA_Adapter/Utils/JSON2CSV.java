package JIRA_Adapter.Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON2CSV {
	//public static void main(String myHelpers[]){
	public void JSON2CSVconverter(String JSONString) 
	{
		JSONObject output;
		try 
		{
			output = new JSONObject(JSONString);
			JSONArray docs = output.getJSONArray("fields");

			File file=new File("D://fromJSON.csv");
			String csv = CDL.toString(docs);
			FileUtils.writeStringToFile(file, csv);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}