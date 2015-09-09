package servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
		String info = req.getQueryString();
		String value = info.substring(4);
		String X = "6876766832351765396496377534476050002970857483815262918450355869850085167053394672634315391224052153";
		BigDecimal val = new BigDecimal(value);
		BigDecimal primeX = new BigDecimal(X);
		BigDecimal result = val.divide(primeX);
		String resultvalue = result.toString();
		String teamInfo = "GUNDAM,6838-9567-0525,6024-8213-6870,1743-9149-4421";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String final_result = resultvalue + "\n" + teamInfo + "\n" + time + "\n";
		ServletOutputStream sos = res.getOutputStream();
		sos.println(final_result);
		
		
	}
}



