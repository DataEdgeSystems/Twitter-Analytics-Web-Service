package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Q2_Servlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Connection conn = null;
		final String DBDRIVER = "org.gjt.mm.mysql.Driver";
		final String DBURL = "jdbc:mysql://localhost:3306/tweets";
		final String DBUSER = "Gundam";
		final String DBPASS = "15619";
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (Exception e) {
		}

		res.setContentType("text/plain;charset=UTF-8");
		String info = req.getQueryString();
		String[] input = info.split("&");
		String[] tmp1 = input[0].split("=");
		String[] tmp2 = input[1].split("=");
		String id = tmp1[1];
		String tweet_time = tmp2[1];

		PrintWriter sos = res.getWriter();
		sos.write("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n");
		try {
			// search MySQL
			Statement st = conn.createStatement();
			String query = "SELECT tweet_id,grade,text FROM info WHERE time = \""
					+ tweet_time + "\"" + "AND user_id = \"" + id + "\"";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				String tweet_id = rs.getString("tweet_id");
				String score = rs.getString("grade");
				String text = rs.getString("text");

				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				text = (String) engine.eval(text);

				sos.write(tweet_id + ":" + score + ":" + text + "\n");
			}
		} catch (Exception e) {
		} finally {
			sos.close();
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}