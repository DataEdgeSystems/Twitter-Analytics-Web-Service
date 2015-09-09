package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//明天要改XML  还有 具体程序
public class Q3_Servlet extends HttpServlet {
	private HikariDataSource ds;

	public void init() throws ServletException {
		super.init();
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", "localhost");
		config.addDataSourceProperty("port", "3306");
		config.addDataSourceProperty("databaseName", "tweets");
		config.addDataSourceProperty("user", "Gundam");
		config.addDataSourceProperty("password", "15619");
		this.ds = new HikariDataSource(config);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String userid = req.getParameter("userid");
		ServletOutputStream sos3 = res.getOutputStream();
		Connection conn = null;
		try {

			conn = this.ds.getConnection();
			Statement st3 = conn.createStatement();
			String query3 = "SELECT retweets FROM q3 WHERE user_id = \""
					+ userid + "\" ";
			ResultSet rs3 = st3.executeQuery(query3);
			StringBuffer sb3 = new StringBuffer();
			sb3.append("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n");
			rs3.next();
			String user_id_primitive = rs3.getString("retweets");
			String user_id = user_id_primitive.trim();
			String[] values = user_id.split(" ");
			for (int i = 0; i < values.length; i++) {
				sb3.append(values[i] + "\n");
			}
			sos3.print(sb3.toString());
		} catch (SQLException e) {
		} finally {
			sos3.close();
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

}