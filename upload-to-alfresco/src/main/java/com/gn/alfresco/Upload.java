package com.gn.alfresco;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

public class Upload {
	private final static String alfrescoUrl = "http://192.168.188.128:8080/alfresco";
	
	public static void uploadDocument(String authTicket, File fileobj, String filename, String filetype,
			String description, String destination) {
		try {
			String urlString = alfrescoUrl + "/service/api/upload?alf_ticket=" + authTicket;
			System.out.println("The upload url:::" + urlString);
			HttpClient client = new HttpClient();
			PostMethod mPost = new PostMethod(urlString);
			// File f1 =fileobj;
			Part[] parts = { 
					new FilePart("filedata", filename, fileobj, filetype, null),
					new StringPart("filename", filename), 
					// new StringPart("destination", destination)
					new StringPart("description", description), 
					new StringPart("siteid","swsdp"),
					new StringPart("containerid", "documentLibrary"), 
					new StringPart("uploaddirectory", "/Presentations")

			};
			mPost.setRequestEntity(new MultipartRequestEntity(parts, mPost.getParams()));
			int statusCode1 = client.executeMethod(mPost);
			System.out.println("statusLine>>>" + statusCode1 + "......" + mPost.getStatusLine() + mPost.getResponseBodyAsString());
			mPost.releaseConnection();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static  String getTicket(String username,String password)  {
		String alfrescoTiccketURL = alfrescoUrl + "/service/api/login?u=" + username + "&pw=" + password;

		String ticketURLResponse = invokeWebScriptgetRequest(alfrescoTiccketURL);

		// ticketURLResponse =
		int startindex = ticketURLResponse.indexOf("<ticket>") + 8;
		int endindex = ticketURLResponse.indexOf("</ticket>");

		return  ticketURLResponse.substring(startindex, endindex);		
	}

	public static String invokeWebScriptgetRequest(String url) {

		// Create an instance of HttpClient.
		HttpClient client = new HttpClient();

		// Create a method instance.
		GetMethod method = new GetMethod(url);
		/*
		 * // Provide custom retry handler is necessary
		 * method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new
		 * DefaultHttpMethodRetryHandler(3, false));
		 */
		String response = null;
		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
			response = new String(responseBody);
			System.out.println(response);

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return response;

	}
}
