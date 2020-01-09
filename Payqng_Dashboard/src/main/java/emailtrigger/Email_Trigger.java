package emailtrigger;

import java.io.File;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailException;
public class Email_Trigger
{
	public static String Start_Date;
	public static String End_Date;
	public static String Pass;
	public static String Fail;
	public static String Skip;
	public static String Total;
	
	public static void mailtrigger() throws ParserConfigurationException, SAXException, Exception 
	{
        
		File xmlFile = new File(System.getProperty("user.dir")+"/target/surefire-reports/testng-results.xml");
		DocumentBuilderFactory docbuildFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docbuildFactory.newDocumentBuilder();
		Document document = docBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();

		System.out.println("Root element name :- " + document.getDocumentElement().getNodeName());

		NodeList nodeList1 = document.getElementsByTagName("suite");
		for (int i = 0; i < nodeList1.getLength(); i++) {

			Node node = nodeList1.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;
				Start_Date=element.getAttribute("started-at");
				End_Date=element.getAttribute("finished-at");
			}
		}
		NodeList nodeList = document.getElementsByTagName("testng-results");
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			System.out.println("\nCurrent element name :- " + node.getNodeName());
			System.out.println("-------------------------------------");

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				System.out.println("Passed :- " + element.getAttribute("passed"));
				System.out.println("Failed :- " + element.getAttribute("failed"));
				System.out.println("Total :- " + element.getAttribute("total"));
				System.out.println("Skip :- " + element.getAttribute("skipped"));
				Pass=element.getAttribute("passed");
				Fail=element.getAttribute("failed");
				Total=String.valueOf((Integer.parseInt(Pass)+Integer.parseInt(Fail)));
				Skip=element.getAttribute("skipped");

				Properties props = new Properties();

				// this will set host of server- you can change based on your requirement 
				props.put("mail.smtp.host", "smtp.gmail.com");
				// set the port of socket factory 
				props.put("mail.smtp.socketFactory.port", "465");

				// set socket factory
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

				// set the authentication to true
				props.put("mail.smtp.auth", "true");

				// set the port of SMTP server
				props.put("mail.smtp.port", "465");

				// This will handle the complete authentication
				Session session = Session.getDefaultInstance(props,

						new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {

						return new PasswordAuthentication("nasar.067@gmail.com", "Nasar@0310");

					}

				});

				try {

					// Create object of MimeMessage class
					Message message = new MimeMessage(session);

					// Set the from address
					message.setFrom(new InternetAddress("nasar.067@gmail.com"));

					// Set the recipient address
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("nasar@macappstudio.com"));
					//message.setRecipients(Message.RecipientType.CC,InternetAddress.parse("swathip@vee2it.com"));

				

					// Add the subject link
					message.setSubject("PayQng-Regression Testing Report");

					// Create object to add multimedia type content	
					BodyPart messageBodyPart1 = new MimeBodyPart();

					// Set the body of email
					messageBodyPart1.setContent("<center style=\"padding-bottom:24px\">\r\n" + 
							"        <table width=\"600\" style=\"width:600px\">\r\n" + 
							"						            <tbody>\r\n" + 
							"						                <tr height=\"101\" style=\"padding-top:24px;padding-bottom:24px; background: black;\">\r\n"  + 
							"						                   <td width=\"50%\" valign=\"left\" style=\"width:50%;padding:0\"><img style=\"width: 300px;  \" src=\"http://172.20.2.3/medstar/Images/medstar.png\"\r\n"  + 
							"						                            alt=\"Coding Assitance Platform\"></td>\r\n" + 
							"						                   <td width=\"50%\" valign=\"middle\" style=\"width:50%;vertical-align:middle;padding:0\">\r\n" + 
							"						                        <h2 style=\"margin:0;font-size:18px;color:#04a0dc;text-align:centre\">Test Suite Execution Report</h2>\r\n" + 
							"						                   </td>\r\n" + 
							"						               </tr>\r\n"+ 
							"				               <tr style=\"background-color:#fff;align=center\">\r\n" + 
							"				                    <td style=\"border:1px solid #dddee1;padding:24px;word-break:break-word;word-wrap:break-word\" colspan=\"2\">\r\n"+ 
							"				                       <p>Dear Team,<br><br>PayQng Regression test suite has been scuessfully Completed . Here is the summary report.</p>\r\n" + 
							"				                        	<table class=\"border\" width=\"400\" border=\"1\" bgcolor=\"#f5f7fa\" style=\"width:100%;background-color:#f5f7fa;border:1px solid #dddee1\">\r\n"+ 
							"				                           	 <tbody>\r\n" + 
							"				                            <tr>\r\n" + 
							"				                                <tr> <td colspan=\"8\" align=\"center\">Automation_Testing_Report</td></tr>\r\n" + 
							"				                             	<tr> <td>Project Name</td>"+"<td colspan=\"8\">"+"PayQng Automation"+"</td></tr>\r\n" + 
							"				                                <tr> <td>Test Suite</td>"+"<td colspan=\"8\">"+"Regression_Test"+"</td></tr>\r\n"+ 
							"				                                <tr> <td>Browser</td>"+"<td colspan=\"8\">"+"Chrome_Browser"+"</td></tr>\r\n" + 
							"				                                <tr> <td>Started Date</td>"+"<td colspan=\"8\">"+Start_Date+"</td></tr>\r\n" + 
							"				                                <tr> <td>End Date</td>"+"<td colspan=\"8\">"+End_Date+"</td></tr>\r\n"+ 
							"				                                <tr> <td width=\"20%\">Result</td>"+"<td width=\"10%\" style=\"color:Green\">Pass : "+Pass+"</td>"+ 
							"				                                <td width=\"10%\" style=\"color:red\">Fail : "+Fail+"</td>\r\n" + 
							"				                                <td width=\"10%\" style=\"color:blue\">Skip : "+Skip+"</td>\r\n" + 
							"				                                <td width=\"10%\" style=\"color:blue\">Total : "+Total+"</td></tr>\r\n" + 
							"				                              </tr>\r\n" + 
							"				                            </tbody>\r\n" + 
							"				                        </table>\r\n" + 
							"				                       <p>Kindly find the below attachement for your Reference \r\n" + 
							"				                        <br>\r\n" + 
							"				                       <br>This email was sent automatically by AppTester Automation Team.<br><br>Thanks,<br>AppTester Automation Team</p>\r\n" + 
							"				                    </td>\r\n" + 
							"				                </tr>\r\n"+ 
							"				            </tbody>\r\n"+
							"        </table>\r\n" + 
							"    </center>" , "text/html");

					// Create another object to add another content
					MimeBodyPart messageBodyPart2 = new MimeBodyPart();

					// Mention the file which you want to send
					String filename = "/Users/nasar/eclipse-workspace/Payqng_Dashboard/Extent Report/ExtentReport.html";

					// Create data source and pass the filename
					DataSource source = new FileDataSource(filename);

					// set the handler
					messageBodyPart2.setDataHandler(new DataHandler(source));

					// set the file
					messageBodyPart2.setFileName(filename);

					
					// Create object of MimeMultipart class
					Multipart multipart = new MimeMultipart();

					// add body part 1
					multipart.addBodyPart(messageBodyPart2);

					// add body part 2
					multipart.addBodyPart(messageBodyPart1);

					// set the content
					message.setContent(multipart);

					// finally send the email
					Transport.send(message);

					Reporter.log("*********************************************Email Sent******************************************************",true);

				} catch (MessagingException e) {

					throw new RuntimeException(e);

				}
			}	 
		}
	}
}
