package JIRA_Adapter.CRUD;


/**
 * README: https://docs.atlassian.com/jira/REST/latest/
 * Dependencies: [org.apache.commons] commons-codec-1.6, [com.sun.jersey] jersey-client-1.19, [org.json] json-20090211 and their dependencies.
 */


import org.json.JSONException;

import JIRA_Adapter.JSONHandler.string2FileWriter;
import JIRA_Adapter.Utils.JSON2CSV;
import JIRA_Adapter.model.Issues;

public class JiraRestClient 
{
	/**
	 *
	 * @param parsedIssue
	 * @throws Exception 
	 */
	//public static void main(String... args) throws Exception 
	public void CreateIssueInJIRA(Issues parsedIssue) throws Exception 
	{
		try
		{
			/**
			 * AI Integration
			 */
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "sacha5", "Hello12345678");
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_eims", "Sapient_123");
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_aims", "Sapient_123");
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_uims", "Sapient_123");
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_sims", "Hello@1234");
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_cims", "Hello@1234");
				//JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_dims", "Hello@1234");
				JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "s_ademo", "Sapient_123");

				
				String issue = jrc.createIssue(parsedIssue.getProjectID(), 7, parsedIssue.getSummary(), parsedIssue.getDescription(), parsedIssue.getPriority(), parsedIssue.getComponent(), parsedIssue.getLabels());
				System.out.println(issue);

				
				//String issue = jrc.getIssue("SGM-11");
				//String issue = jrc.createIssue(43305, 7, "testing with jira-REST-client", "worked :) Sudharshan",1,58523);//(ProjectID,IssueType/Story,Summary,description,priority,component/INFRA)
			
				//String search = jrc.SearchJIRAForIssue("project=sgm%20AND%20labels=INCGB0046374912");
				//System.out.println(search);
				//https://tools.publicis.sapient.com/jira/rest/api/2/issue/AXPOP-1?jql=project%20%3D%20AXPOP
			
				
/*				
				*//**
				 * KPI Analytics
				 *//*
				
				string2FileWriter s2fw1 = new string2FileWriter();
				JSON2CSV j2c = new JSON2CSV();
				JiraServices jrc = new JiraServices("https://tools.publicis.sapient.com/jira/", "sacha5", "Hello1234567");
				while (true)
				{
					Thread.currentThread();
					Thread.sleep(1000);
					String jqlReturn = jrc.SearchJIRAForIssue("project%20=%20IMSP");
					j2c.JSON2CSVconverter(jqlReturn);
					s2fw1.writer(jqlReturn);
				}
*/	
			} 
			catch (JSONException ex) 
			{
				System.err.println(ex);
			}
		}
	}