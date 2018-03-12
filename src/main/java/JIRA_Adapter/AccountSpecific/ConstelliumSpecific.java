package JIRA_Adapter.AccountSpecific;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstelliumSpecific 
{
	public Map<String, String> extractingNotesAndIncidentID(Map<String, String> map,String[] entries, String source) 
	{
			for (String entry : entries) 
			{
				if (!entry.isEmpty() && entry.contains("Notes:"))
				{
					Matcher matcher = Pattern.compile(
							Pattern.quote("Notes:")
							+ "(.*?)"
							+ Pattern.quote("Status: Assigned")
							).matcher(source.replaceAll("\n|\r", " "));
					while(matcher.find())
					{
						String match = matcher.group(1);
						System.out.println("Notes Value : "+match);
						map.put("Notes", match);
					}
				}
			}
			Matcher matcher = Pattern.compile(
					Pattern.quote("Incident")
					+ "(.*?)"
					+ Pattern.quote("is assigned to you")
					).matcher(source.replaceAll("\n|\r", " "));
			while(matcher.find())
			{
				String match = matcher.group(1);
				System.out.println("Incident Number : "+match);
				map.putIfAbsent("Incident_Key", match.trim());
			}
		return map;
	}
	
	public int validateEmail(String mailString)
	{
		/**
		 * =========================
		 * Response Codes
		 * =========================
		 * 1 -> Create
		 * 2 -> Update
		 * 0 -> Invalid Email 
		 * =========================
		 */
		if (mailString.contains("Incident * is assigned to your group, *, to resolve."))
		{
			return 1;
		}
		else
		{
			return 2;
		}
		
	}
	
	
	/**
	 * --> Constillium Work Area 
	 * */

	/*
	 * 
		Issues issue = new Issues();
		AdapterUtils Utils = new AdapterUtils();
		JiraRestClient callJiraRestClient = new JiraRestClient();
		ParseHTMLWithTika parseHTML = new ParseHTMLWithTika();
		
		public void createPOSTCall(Map<String, String> extractedData,ContentHandler contenthandler)
		{
			int i =0;
			try
			{
			extractedData = parseHTML.splitToMap(contenthandler.toString(), "\n", ":");

			for(Map.Entry text : extractedData.entrySet())
			{
				System.out.println(i + " - " + text.getKey() + " = " + text.getValue());
				i++;
			}
			issue.setSummary(extractedData.get("Summary").trim());
			issue.setDescription(extractedData.get("Notes"));
			issue.setPriority(Utils.getPriorityIDFromPriorityNameAndAccountName(extractedData.get("Customer Company").trim(), extractedData.get("Priority").trim()));
			issue.setLabels(extractedData.get("Incident_Key").trim());
			issue.setProjectID(Utils.getProjectIdFromAccountName(extractedData.get("Customer Company").trim()));

			callJiraRestClient.CreateIssueInJIRA(issue);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		*/
}
