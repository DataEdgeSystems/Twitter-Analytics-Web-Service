package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Q4_Servlet extends HttpServlet {
	private HikariDataSource ds;

	public void init() throws ServletException {
		super.init();
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(100);
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.addDataSourceProperty("serverName", "localhost");
		config.addDataSourceProperty("port", "3306");
		config.addDataSourceProperty("databaseName", "tweet");
		config.addDataSourceProperty("user", "Gundam");
		config.addDataSourceProperty("password", "15619");
		this.ds = new HikariDataSource(config);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		Connection conn = null;
		res.setContentType("text/plain;charset=UTF-8");
		String date = req.getParameter("date");
		String location = req.getParameter("location");
		String minRank = req.getParameter("m");
		String maxRank = req.getParameter("n");
		String time_place  = new StringBuilder().append(date).append(location).toString();
		PrintWriter sos1 = res.getWriter();
		try {
			conn = this.ds.getConnection();
//			Statement st1 = conn.createStatement();
//			String query = "SELECT score FROM q5 WHERE userid= \"" + m
//					+ "\"LIMIT 1";
			PreparedStatement stmt = conn.prepareStatement("select output from q4 where time_place=? and rank>=? and rank<=?");
			stmt.setString(1, time_place);
			stmt.setInt(2, Integer.parseInt(minRank));
			stmt.setInt(3, Integer.parseInt(maxRank));
			ResultSet rs = stmt.executeQuery();
			StringBuffer sb = new StringBuffer();
			 while (rs.next()){
				 String output = rs.getString("output");
				 sb.append(output + "\n");
			 }
			sos1.print("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n"
					+ sb.toString());

		} catch (SQLException e) {
			sos1.println("error_3");
		}
		finally {
			sos1.close();
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		

	}
}
