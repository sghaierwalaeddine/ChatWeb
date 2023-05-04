/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import EntityBean.Conversation;
import EntityBean.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import EntityBeans.*;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Asus
 */
@WebServlet(name = "GetConversation", urlPatterns = {"/GetConversation"})
public class GetConversation extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private ConversationFacadeLocal conversationFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        int id = Integer.parseInt(request.getParameter("id"));
        int user1 = Integer.parseInt(request.getParameter("user1"));
        int user2 = Integer.parseInt(request.getParameter("user2"));

        try ( PrintWriter out = response.getWriter()) {

            String json = conversationFacade.getConversation(id);
             int idConv = conversationFacade.getIdConversation(user1, user2);
            if (json.equals("]") && id==0 && idConv==0 ) {
                Conversation conv = new Conversation();
                Utilisateur u1 = new Utilisateur();
                u1.setUserid(user1);
                Utilisateur u2 = new Utilisateur();
                u2.setUserid(user2);
                conv.setUser1(u1);
                conv.setUser2(u2);
                conversationFacade.create(conv);
                int newidConv = conversationFacade.getIdConversation(user1, user2);
                System.out.print("-/-/*//**-***********************************************************");
                json = conversationFacade.getConversation(newidConv);

            }
            out.print(json);
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
