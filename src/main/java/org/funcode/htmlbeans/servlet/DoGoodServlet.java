package org.funcode.htmlbeans.servlet;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.mvc.ModelViewController;
import org.funcode.htmlbeans.wrappers.ObjectWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * This servlet serves all of the requests related to the Object model manipulation
 * Created with IntelliJ IDEA.
 * User: dmarkin
 * Date: 09.10.12
 * Time: 20:38
 */
public class DoGoodServlet extends HttpServlet {

    public static final String PRESENTATION_CONTROLLER_ATTRIBUTE_NAME = "PRESENTATION_CONTROLLER";
    public static final String SOURCE_ATTRIBUTE_NAME = "SOURCE";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //TODO Create a message something like there is no session sorry etc.
        ModelViewController modelViewController
                = (ModelViewController) session.getAttribute(PRESENTATION_CONTROLLER_ATTRIBUTE_NAME);
        modelViewController.applyChanges(request.getParameterMap());
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Object,Object> params = request.getParameterMap();
        System.out.println(params);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ModelViewController modelViewController
                = (ModelViewController) session.getAttribute(PRESENTATION_CONTROLLER_ATTRIBUTE_NAME);
        Object source = session.getAttribute(SOURCE_ATTRIBUTE_NAME);
        if (source == null || modelViewController == null) {
            source = new XStream().fromXML(
                    DoGoodServlet.class.getResourceAsStream("/house.init.xml")
            );
            session.setAttribute(SOURCE_ATTRIBUTE_NAME, source);
            try {
                modelViewController = new ModelViewController(new ObjectWrapper().doGood(source));
                session.setAttribute(PRESENTATION_CONTROLLER_ATTRIBUTE_NAME, modelViewController);
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //TODO Create an error message, for the GUI
            }
        }

        response.setContentType("text/json");
        response.getWriter().print(new Gson().toJson(modelViewController.getModel()));
        response.getWriter().flush();
    }


}
