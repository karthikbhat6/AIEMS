package JIRA_Adapter.Utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.sis.internal.jdk7.StandardCharsets;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import JIRA_Adapter.AccountSpecific.ConstelliumSpecific;
import JIRA_Adapter.CRUD.JiraRestClient;
import JIRA_Adapter.model.Issues;

public class ParseHTMLWithTika 
{
	public static Map<String, String> splitToMap(String source, String entriesSeparator, String keyValueSeparator) 
	{
		Map<String, String> map = new HashMap<String, String>();
		String[] entries = source.split(entriesSeparator);
		for (String entry : entries) 
		{
			if (!entry.isEmpty() && (entry.contains("\n") || entry.contains("\t")))
			{
				entry = entry.replaceAll("\n", " ");
				entry = entry.replaceAll("\t", " ");
				System.out.println("");
			}
			
			if (!entry.isEmpty() && entry.contains(keyValueSeparator.replace("\\", ""))) 
			{
				String[] keyValue = entry.split(keyValueSeparator);
				map.put(keyValue[0].trim(), (keyValue.length > 1 ? keyValue[1].trim() : ""));
			}
		}
		// Constellium specific:
		/*if (map.get("Customer Company").equalsIgnoreCase(" Constellium"))
		{
			return new ConstelliumSpecific().extractingNotesAndIncidentID(map, entries, source);
		}
		else*/
			return map;
	}

	//public static void main(String args[]) throws Exception
	public void Html2TextParser(String htmlContent) throws IOException 
	{
		Issues issue = new Issues();
		AdapterUtils Utils = new AdapterUtils();
		JiraRestClient callJiraRestClient = new JiraRestClient();
		EmailValidator emailValidator = new EmailValidator();
		//InputStream is = null;
		InputStream stream = new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
		int i = 0;
		try 
		{
			//is = new FileInputStream("Resources/Test_HTML.html");

			ContentHandler contenthandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			Parser parser = new AutoDetectParser();
			parser.parse(stream, contenthandler, metadata, new ParseContext());
			Map<String, String> extractedData = new HashMap<>();

			/** Testing */
			System.out.println(contenthandler.toString());
			//System.out.println(splitToMap(contenthandler.toString(), "\n", ":"));
			//System.out.println(splitToMap(contenthandler.toString(), "\\|", "="));

			/**
			 * --> HelpDesk Work Area - Outlook Form
			 * */
/*
			extractedData = splitToMap(contenthandler.toString(), "\\|\\|", ":");
			for(Map.Entry text : extractedData.entrySet())
			{
				System.out.println(i + " - " + text.getKey() + " = " + text.getValue());
				i++;
			}
			
			issue.setSummary(extractedData.get("Summary"));
			issue.setDescription(extractedData.get("Description"));
			issue.setPriority(Utils.getPriorityIDFromPriorityNameAndAccountName("SGM", extractedData.get("Priority")));
			issue.setComponent(Utils.getComponentIDFromComponentNameAndAccountName("SGM", extractedData.get("Component")));
			issue.setProjectID(Utils.getProjectIdFromAccountName("SGM"));

			callJiraRestClient.CreateIssueInJIRA(issue);
			*/
			
			/**
			 * --> HelpDesk Work Area - InfoPath
			 * */

		/*	extractedData = splitToMap(contenthandler.toString(), "\\|", ":");
			for(Map.Entry text : extractedData.entrySet())
			{
				System.out.println(i + " - " + text.getKey() + " = " + text.getValue());
				i++;
			}
			
			issue.setSummary(extractedData.get("Summary�"));
			issue.setDescription(extractedData.get("Notes"));
			issue.setComponent(Utils.getComponentIDFromComponentNameAndAccountName("SGM", extractedData.get("Assigned to")));
			issue.setPriority(Utils.getPriorityIDFromPriorityNameAndAccountName("SGM", extractedData.get("Priority")));
			issue.setProjectID(Utils.getProjectIdFromAccountName("SGM"));

			callJiraRestClient.CreateIssueInJIRA(issue);*/
			
			/**
			 * --> Constillium Work Area 
			 * */
			/*

			extractedData = splitToMap(contenthandler.toString(), "\n", ":");

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
*/


			/** 
			 * -->DE Log Analyser Work Area 
			 * */ 

			/**--> Sample
			 *  Summary="Summary123"|Description="Desc123"|Issue Type=Bug|Component=LogEvent|Priority=2 */

		 extractedData = splitToMap(contenthandler.toString(), "\\|", "\\^"); 
         for(Map.Entry text : extractedData.entrySet())
         {
        	 System.out.println(i + " - " + text.getKey() + " = " + text.getValue());
        	 i++;
         }
         //issue.setSummary(extractedData.get("\nSummary"));
         issue.setSummary(extractedData.get("Summary"));
         issue.setDescription(extractedData.get("Description"));

         //System.out.println("DE."+extractedData.get("Priority"));
         issue.setPriority(Utils.getPriorityIDFromPriorityNameAndAccountName("DE", extractedData.get("Priority").replaceAll("\\n|\\r", " ").trim()));
         issue.setComponent(Utils.getComponentIDFromComponentNameAndAccountName("DE", extractedData.get("Component").trim()));

         issue.setProjectID(Utils.getProjectIdFromAccountName("UNIPERRUN"));//ELSASP-ENI // AXPOP-AXPO // ELUSP-Uniper // UNIPERRUN-Uniper UN ELDCO-Constellium // ELSDE-DE // IMSP-AIeMS Demo // AISP-Enbridge // CIAS-Cheveron S15 //UNIPERRUN
         issue.setLabels(extractedData.get("labels").trim());
         callJiraRestClient.CreateIssueInJIRA(issue);

		}
		catch (Exception e) 
		{
			System.out.println("No Data could be parsed out of this mail: -  check for the parser configured or look at the mail for the expected content availablity");
			e.printStackTrace();
		}
		finally {
			if (stream != null) stream.close();
		}
	}
}