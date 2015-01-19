/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocks.teammolise.myunimol.webapp.news;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import rocks.teammolise.myunimol.api.APIConsumer;
import rocks.teammolise.myunimol.webapp.UserInfo;

/**
*
* @author Pasquale
*/
@WebServlet(name = "GetNewsBoardServlet", urlPatterns = {"/GetNewsBoardServlet"})
public class GetNewsBoardServlet extends HttpServlet {
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

       response.setContentType("application/json; charset=UTF-8");
       PrintWriter out = response.getWriter();

       try {
           if (request.getSession().getAttribute("userInfo") == null) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
               return;
           }

           UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

           String username = userInfo.getUsername();
           String password = userInfo.getPassword();
           String cdl = "";
           if(userInfo.getCourse().matches("(?i:.*informatica.*)")) {
        	   cdl = "informatica";
           } else if(userInfo.getCourse().matches("(?i:.*scienze biologiche.*)")) {
        	   cdl = "scienzeBiologiche";
           } else {
	           JSONObject noCourse = new JSONObject();
	           noCourse.put("result", "failure");
	           noCourse.put("msg", "Spiacenti ma MyUnimol non supporta ancora l'estrazione delle notizie per "+userInfo.getCourse()+"...");
	           out.print(noCourse);
	           return;
           }
           
           Map<String, Object> parameters = new HashMap<String, Object>();
           parameters.put("course", cdl);
           JSONObject newsBoardJSON = new APIConsumer().consume("getNewsBoard", username, password, parameters);
           out.print(newsBoardJSON);
       } catch (UnirestException ex) {
    	   ex.printStackTrace();
           response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
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
