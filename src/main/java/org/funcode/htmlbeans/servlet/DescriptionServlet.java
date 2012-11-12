package org.funcode.htmlbeans.servlet;

import com.google.gson.Gson;
import org.funcode.htmlbeans.mvc.ModelViewController;
import org.funcode.htmlbeans.wrappers.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * This Servlet provides description of nulled object structures by their ID.
 * User: dmarkin
 * Date: 12.11.12
 * Time: 17:22
 */
public class DescriptionServlet extends HttpServlet {

    public static final String ID_REQUEST_ATTRIBUTE = "id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration parameterNames = req.getParameterNames();
        String requestedId = (String) req.getParameterValues(ID_REQUEST_ATTRIBUTE)[0];
        HttpSession session = req.getSession(false);
        ModelViewController modelViewController = (ModelViewController) session.getAttribute(DoGoodServlet.PRESENTATION_CONTROLLER_ATTRIBUTE_NAME);
        Element element = modelViewController.getModelBindingMap().get(requestedId);
        resp.setContentType("text/json");
        resp.getWriter().print(new Gson().toJson(element));
        resp.getWriter().flush();
    }
}
