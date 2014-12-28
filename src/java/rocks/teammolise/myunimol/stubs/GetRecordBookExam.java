package rocks.teammolise.myunimol.stubs;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rocks.teammolise.myunimol.webapp.configuration.ConfigurationManagerHandler;

/**
 *
 * @author Vincenzo
 */
@WebServlet(name = "getRecordBookExam", urlPatterns = {"/getRecordBookExam"})

public class GetRecordBookExam extends HttpServlet {
	private static final long serialVersionUID = 973581641895155357L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        String token = request.getParameter("token");
        String id = request.getParameter("id");
        String realToken = ConfigurationManagerHandler.getInstance().getToken();
        
        try {
            if (token != null && token.equals(realToken)) {
                out.println("{\"name\":\"Matematica\", \"cfu\":\"12\", \"vote\":\"28\", \"date\":\"28/11/2014\", \"year\":\"2014/2015\", \"id\":\"" + id + "\", "
                		+ "\"details\":["
                		+ "{\"name\":\"Matematica discreta\", \"cfu\":\"6\", \"hours\":\"48\", \"area\":\"MAT/01\"},"
                		+ "{\"name\":\"Analisi matematica\", \"cfu\":\"6\", \"hours\":\"48\", \"area\":\"MAT/01\"}"
                		+ "]}");
            } else {
                out.println("{\"result\": \"unauthorized\"}");
            }
   
        } finally {
            out.close();
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
