package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class Q5_Sevlet extends HttpServlet {
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
		Connection conn = null;
		String m = req.getParameter("m");
		String n = req.getParameter("n");
		ServletOutputStream sos1 = res.getOutputStream();
		try {
			conn = this.ds.getConnection();
			Statement st1 = conn.createStatement();
			String query = "SELECT score FROM q5 WHERE userid= \"" + m
					+ "\"LIMIT 1";
			ResultSet rs1 = st1.executeQuery(query);
			rs1.next();
			String grade1 = rs1.getString("score");
			Statement st2 = conn.createStatement();
			String query2 = "SELECT score FROM q5 WHERE userid= \"" + n
					+ "\"LIMIT 1";
			ResultSet rs2 = st2.executeQuery(query2);
			rs2.next();
			String grade2 = rs2.getString("score");
			String[] scores1 = grade1.split(" ");
			String[] scores2 = grade2.split(" ");
			// sos1.print(m + "\t" + n + "\t" + "WINNER\n");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 4; i++) {
				int value1 = Integer.parseInt(scores1[i]);
				int value2 = Integer.parseInt(scores2[i]);
				sb.append(scores1[i] + "\t" + scores2[i] + "\t");
				if (value1 > value2) {
					sb.append(m + "\n");
				} else if (value1 < value2) {
					sb.append(n + "\n");
				} else {
					sb.append("X\n");
				}

			}
			sos1.print("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n"
					+ m + "\t" + n + "\t" + "WINNER\n" + sb.toString());

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
// http://webservice_public_dns/q5?m=12345678&n=987654321