package org.funcode.htmlbeans.servlet;

import com.google.gson.Gson;
import org.funcode.htmlbeans.mvc.ModelViewController;
import org.funcode.htmlbeans.wrappers.Element;
import org.funcode.htmlbeans.wrappers.ObjectWrapper;

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

    public static final String ID_CLASS_TO_DESCRIBE = "class";

    private ObjectWrapper objectWrapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String classAsString = req.getParameterValues(ID_CLASS_TO_DESCRIBE)[0];
        if (objectWrapper == null) {
            objectWrapper = new ObjectWrapper();
        }
        resp.setContentType("text/json");
        try {
            Class<?> clazz = Class.forName(classAsString);
            resp.getWriter().print(new Gson().toJson(objectWrapper.doGood(null, clazz)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //TODO add the error handling
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        resp.getWriter().flush();
    }
}
