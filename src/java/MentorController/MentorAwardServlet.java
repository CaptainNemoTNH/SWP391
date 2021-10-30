/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MentorController;

import DAO.AccountDAO;
import DAO.AccountDTO;
import DAO.AwardDAO;
import DAO.AwardDTO;
import DAO.AwardListDAO;
import DAO.AwardListDTO;
import DAO.BlogDAO;
import DAO.BlogDTO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MentorAwardServlet", urlPatterns = {"/MentorAwardServlet"})
public class MentorAwardServlet extends HttpServlet {

    private final String BLOG_DETAIL = "blog";
    private final String AWARD_PAGE = "awardPage";
    private static final String AWARD = "award";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        ServletContext sc = request.getServletContext();
        HashMap<String, String> roadmap = (HashMap<String, String>) sc.getAttribute("ROADMAP");
        String url = null;
        String txtBlogID = request.getParameter("txtBlogID");
        String txtAwardID = request.getParameter("txtAwardID");
        String Action = request.getParameter("btnAction");
        String txtEffectiveDays = request.getParameter("txtEffectiveDays");
        String txtAwardName = request.getParameter("txtAwardName");
        int EffectiveDays = 0;
        int blogID = 0;
        int awardID = 0;

        try {
            AwardListDAO ALDao = new AwardListDAO();
            if (null != txtBlogID) {
                blogID = Integer.parseInt(txtBlogID);
                BlogDAO dao = new BlogDAO();
                BlogDTO dto = dao.getBlogFromBlogID(blogID);
                request.setAttribute("BLOG", dto);
                request.setAttribute("BLOGAWARD", ALDao.getAwardListFromBlogId(blogID));
                request.setAttribute("MENTORLIST", new AccountDAO().getAllAccountFromRole("Mentor"));
            } else {
                System.out.println("Blog ID NULL!");
            }
            if (null != txtAwardID) {
                awardID = Integer.parseInt(txtAwardID);
            } else {
                System.out.println("Award ID NULL!");
            }
            if (null != txtEffectiveDays) {
                awardID = Integer.parseInt(txtEffectiveDays);
            } else {
                System.out.println("txtEffectiveDays NULL");
            }
            if (null == txtAwardID) {
                url = roadmap.get(AWARD_PAGE);
                AwardDAO Adao = new AwardDAO();
                request.setAttribute("ALL_AWARD", Adao.getAllAward());
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (Action.equals("Create Award")) {
                AwardDAO dao = new AwardDAO();
                AwardDTO dto = new AwardDTO(txtAwardName, EffectiveDays);
                if (dao.createAward(dto)) {
                    url = roadmap.get(AWARD_PAGE);
                }
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);

            } else if (Action.equals("Award Blog")) {
                System.out.println("ok con de");
                AccountDTO account = (AccountDTO) request.getSession().getAttribute("USER");
                Date date = new Date(Calendar.getInstance().getTime().getTime());
                AwardListDTO dto = new AwardListDTO(blogID, awardID, date, account.getAccountID());
                ALDao.createAwardList(dto);
                url = roadmap.get(BLOG_DETAIL);
                RequestDispatcher rd = request.getRequestDispatcher(url + "?txtBlogID=" + blogID);
                rd.forward(request, response);

            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("AwardServlet _ SQL " + msg);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MentorAwardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(MentorAwardServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
