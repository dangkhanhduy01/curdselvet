/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package my.saveservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import my.common.Database;

/**
 *
 * @author duyne
 */
public class EditServlet extends HttpServlet {

     /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String method = request.getMethod();
            System.out.println("method:" + method);

            if (method.equalsIgnoreCase("get")) {
                //b1. lay gia tri tham so tu client
                String id = request.getParameter("id");
                //b2. Xử lý yêu cầu (truy cập CSDL để thêm mới user)
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    conn = Database.getConnection();
                    //3. Tạo đối tượng thi hành truy vấn
                    ps = conn.prepareStatement("select * from users where id=" + id);
                    //Truyền giá trị chp các tham số trong câu lệnh sql

                    //4. Thi hành truy vấn
                    rs = ps.executeQuery();
                    //5. Xử lý kết quả trả về
                    if (rs.next()) {
                        out.println("<!DOCTYPE html>");
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>Update user</title>");
                        out.println("</head>");
                        out.println("<body>");
                        out.println("<h1>Update User</h1>");
                        out.println();
                        out.println("</body>");
                        out.println("<form action=\"EditServlet\" method=\"POST\">\n"
                                + "                        <table border =\"0\"> \n"
                                + "             <tr>\n"
                                + "                  <td>Name</td>\n"
                                + "                 <td> <input type=\"text\" name=\"uname\" value=" + rs.getString(2) + " required /></td>\n"
                                + "             </tr>\n"
                                + "             <tr>\n"
                                + "                <td>Password</td>\n"
                                + "                <td> <input type=\"password\" name=\"upass\" value=" + rs.getString(2) + " required/></td>\n"
                                + "            </tr>\n"
                                + "            <tr>\n"
                                + "                <td>Email</td>\n"
                                + "                <td> <input type=\"email\" name=\"email\" value=" + rs.getString(2) + " /></td>\n"
                                + "            </tr>\n"
                                + "           <tr>\n"
                                + "                <td>Country</td>\n"
                                + "                <td> \n"
                                + "                    <select name=\"country\">\n"
                                + "                        <option value=\"Vietnam\">Vietnam</option>\n"
                                + "                        <option value=\"USA\">USA</option>\n"
                                + "                        <option value=\"UK\">UK</option>\n"
                                + "                       <option value=\"Other\">Other</option>\n"
                                + "                   </select>\n"
                                + "               </td>\n"
                                + "           </tr>\n"
                                + "           <tr>\n"
                                + "               <td colspan=\"2\"><input type=\"submit\" value=\"Save\"/></td>\n"
                                + "           </tr>    \n"
                                + "       </table>\n"
                                + "   </form> ");

                    }
                    //6. dong ket noi
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Loi:" + e.toString());
                    out.println("<h2>Thao tác xoá user thất bại</h2>");
                }
                //Chèn nội dung của ViewServlet vào kết nối hồi đáp
                // request.getRequestDispatcher("ViewServlet").include(request, response);
            } else if (method.equalsIgnoreCase("post")) {
                //b1. Lấy giá trị tham số từ client
                String uname = request.getParameter("uname");
                String upass = request.getParameter("upass");
                String email = request.getParameter("email");
                String country = request.getParameter("country");
                String id = request.getParameter("id");
                //b2. Xử lý yêu cầu (truy cập CSDL để thêm mới user)
                Connection conn = null;
                PreparedStatement ps = null;
                try {
                    conn = Database.getConnection();
                    //3. Tạo đối tượng thi hành truy vấn
                    ps = conn.prepareStatement("update user set name=?, password=?,email=?, country=?, where id=?");
                    //Truyền giá trị cho các tham số trong câu lệnh SQL
                    ps.setString(1, uname);
                    ps.setString(2, upass);
                    ps.setString(3, email);
                    ps.setString(4, country);
                    ps.setInt(5, Integer.parseInt(id));
                    //4. Thi hành truy vấn
                    int kq = ps.executeUpdate();
                    //5. Xử lý kết quả trả về
                    if (kq > 0) {
                        out.println("<h2>Thêm user thành công</h2>");
                    } else {
                        out.println("<h2>Thêm user thất bại</h2>");
                    }
                    //6.Đóng kết nối
                    conn.close();
                } catch (Exception e) {
                    System.out.println("Loi:" + e.toString());
                    out.println("<h2>Thêm user thất bại</h2>");
                }
                //Chèn nội dung của ViewServlet vào kết nối hồi đáp
                request.getRequestDispatcher("ViewServlet").include(request, response);

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}