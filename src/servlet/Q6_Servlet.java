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

public class Q6_Servlet extends HttpServlet {
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
			res.setContentType("text/plain;charset=UTF-8");
			String muserid = req.getParameter("m");
			String nuserid = req.getParameter("n");
			int totalnumbern = 0;
			int totalnumberm = 0;
			PrintWriter sos1 = res.getWriter();
			try {
				conn = this.ds.getConnection();
				PreparedStatement stmtn = conn.prepareStatement("select count from q6 where user_id=(select max(user_id) from q6 where user_id <=?)");
				PreparedStatement stmtm = conn.prepareStatement("select count from q6 where user_id=(select max(user_id) from q6 where user_id <?)");
				stmtn.setLong(1, Long.parseLong(nuserid));
				stmtm.setLong(1, Long.parseLong(muserid));
				ResultSet rsn = stmtn.executeQuery();
				rsn.next();
				totalnumbern = rsn.getInt("count");
				if (Long.parseLong(muserid) <= 12){
					totalnumberm = 0;}
				else{
					ResultSet rsm = stmtm.executeQuery();
					rsm.next();
					totalnumberm = rsm.getInt("count");
				}
				
				int value = totalnumbern - totalnumberm;
				sos1.print("GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421\n"
						+ value + "\n");

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