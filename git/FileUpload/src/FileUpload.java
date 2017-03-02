
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//import com.oreilly.servlet.multipart.Part;
import javax.servlet.http.Part;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet(description = "Servlet to upload data", urlPatterns = { "/FileUpload" })
@MultipartConfig  //(maxFileSize = 16177215)
public class FileUpload extends HttpServlet {
//	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	// DB Connection setting
	
	String db_url = "jdbc:oracle:thin:@10.189.8.122:1522:glsdev";
	String db_user = "nilesh";
	String db_pass = "nilesh123";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	
	{
		
		//response.setContentType("text/html;charset=UTF-8");
		
		//PrintWriter out = response.getWriter();		
		
		String no = request.getParameter("id");				
		
		Connection con = null;	
		
		InputStream is = null;
		
//		FileInputStream is = new FileInputStream("C:\\Users\\5698278\\Desktop\\isd certificate.jpg");
		
		Part fp = request.getPart("filename");
		
		
		
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		
//		ServletFileUpload sfu = new ServletFileUpload(factory);
//		
//		List items = sfu.parseRequest(request);
		
//		out.print("<h3>....Size...</h3>"+items.size());
			
		
		
		if(fp != null)
		{
//			out.println("<html><body>");
//			out.println("<h3>" + fp.getName() + "</h3><br/>");
//			out.println("<h3>" + fp.getSize() + "</h3><br/>");
//			out.println("<h3>" + fp.getContentType() + "</h3><br/>");
			
			is = fp.getInputStream();
		}
		
//		FileItem id = (FileItem) items.get(0);
//		
//		String no = id.getString();
//		
////		out.print("<h3>....no..</h3>"+no+"...2.."+items.get(2));
//		
//		FileItem file = (FileItem) items.get(1);	
//		
//		out.print("<h3>....no..</h3>"+no+"...2.."+ (int) file.getSize());
		
//		
//		
//			
		try
		{	
	
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con = DriverManager.getConnection(db_url, db_user, db_pass);
			
//			con.setAutoCommit(false);
			
			String query = "insert into cust_tmp values (?, ?)";
			
			PreparedStatement stmt = con.prepareStatement(query);
			
			stmt.setString(1, no);
			
			if(is != null)
			{
				
//				out.println("<h3> Hi...12.... </h3><br/>");
				
				stmt.setBinaryStream(2, is, is.available());			
				
				
//				.setBlob(2, is)
			}
//			else
//			{
//			out.println("<h3> File Input Stream is NULL </h3><br/>");
//			}
			
			
               int row = stmt.executeUpdate();
//			
			if (row > 0)
			{
				System.out.println("<h3>File uploaded into Database</h3><br/>");
			}
			else
			{
				System.out.println("<h3>File uploading failed</h3><br/>");
			}
//			
		}
		catch(SQLException ex)
		{
			System.out.println("<h3> 1:" + ex.getMessage() + "</h3><br/>");
			
			ex.printStackTrace();
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("<h3> 2: " + ex.getMessage() + "</h3><br/>");
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.close();
				}
				catch(SQLException ex)
				{
					System.out.println("<h3> 3: " + ex + "</h3><br/>");
					System.out.print("</body></html>");
				}
			}
			
		}
		
		
//		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
