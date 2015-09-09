package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Q1_Servlet extends HttpServlet {
	public static HashMap<String, String> hm = new HashMap<String, String>();
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String X = "6876766832351765396496377534476050002970857483815262918450355869850085167053394672634315391224052153";
		BigDecimal primeX = new BigDecimal(X);
		String teamInfo = "GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String resultvalue;
		StringBuffer sb = new StringBuffer();
		String info = req.getQueryString();
		String value = info.substring(4);
		if (!hm.containsKey(value)) {
			BigDecimal val = new BigDecimal(value);
			BigDecimal result = val.divide(primeX);
			resultvalue = result.toString();
			hm.put(value, resultvalue);
		} else {
			resultvalue = hm.get(value);
		}
			String time = sdf.format(new Date());
			sb.append(resultvalue);
			sb.append("\n");
			sb.append(teamInfo);
			sb.append("\n");
			sb.append(time);
			sb.append("\n");
			ServletOutputStream sos1 = res.getOutputStream();
			sos1.println(sb.toString());
			sb.delete(0, sb.length());
			sos1.close();
		}

	}
