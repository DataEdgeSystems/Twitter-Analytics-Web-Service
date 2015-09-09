package servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Extraction {
	public static void main(String args[]) throws IOException, ParseException{
		File file = new File("cc_sample.txt");
		BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
		String inputLine;
		StringBuffer content = new StringBuffer();
	    if ((inputLine = bufferedreader.readLine()) != null) {
			content.append(inputLine);
		}
		bufferedreader.close();

		String content_to_string = content.toString();
		System.out.println(content_to_string);
		JSONParser parser = new JSONParser();
	    Object obj;
		obj = parser.parse(content_to_string);
		JSONObject first_layer = (JSONObject)obj;
		String time = first_layer.get("created_at").toString();
		System.out.println(time);
		String tweet_id = first_layer.get("id").toString();
		System.out.println(tweet_id);
		String text = first_layer.get("text").toString();
		System.out.println(text);
		JSONObject user = (JSONObject)first_layer.get("user");
		String user_id = user.get("id").toString();
		System.out.println(user_id);
		
		
		
	}
	
}
