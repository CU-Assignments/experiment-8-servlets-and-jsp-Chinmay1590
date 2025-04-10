import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String studentName = request.getParameter("studentName");
        String rollNumber = request.getParameter("rollNumber");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/school_db", "root", "password");
            PreparedStatement ps = con.prepareStatement("INSERT INTO attendance (name, roll_number, status) VALUES (?, ?, ?)");
            ps.setString(1, studentName);
            ps.setString(2, rollNumber);
            ps.setString(3, status);
            int i = ps.executeUpdate();

            if (i > 0) {
                out.println("<h2>Attendance recorded successfully!</h2>");
            } else {
                out.println("<h2>Error recording attendance.</h2>");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
