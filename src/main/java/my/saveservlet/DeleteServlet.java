package my.saveservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import my.common.Database;

/**
 *
 * @author duyne
 */
public class DeleteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // B1: Lấy giá trị tham số từ client
        String id = request.getParameter("id");

        Connection conn = null;
        PreparedStatement ps = null;

        try (PrintWriter out = response.getWriter()) {
            conn = Database.getConnection();
            // B3: Tạo đối tượng thi hành truy vấn
            ps = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            // Truyền giá trị cho tham số trong câu lệnh SQL
            ps.setInt(1, Integer.parseInt(id));

            // B4: Thi hành truy vấn
            int kq = ps.executeUpdate();

            // B5: Xử lý kết quả trả về
            if (kq > 0) {
                out.println("<h2>Đã xoá 1 user thành công</h2>");
            } else {
                out.println("<h2>Thao tác xoá user thất bại</h2>");
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.toString());
            try (PrintWriter out = response.getWriter()) {
                out.println("<h2>Thao tác xoá user thất bại</h2>");
            }
        } finally {
            // Đóng kết nối
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.toString());
            }
        }
        // Chèn nội dung của ViewServlet vào kết nối hồi đáp
        request.getRequestDispatcher("ViewServlet").include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
