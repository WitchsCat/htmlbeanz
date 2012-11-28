package org.funcode.htmlbeans.servlet;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.funcode.htmlbeans.mvc.ModelViewController;
import org.funcode.htmlbeans.wrappers.Element;
import org.funcode.htmlbeans.wrappers.ObjectWrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This servlet serves all of the requests related to the Object model manipulation
 * Created with IntelliJ IDEA.
 * User: dmarkin
 * Date: 09.10.12
 * Time: 20:38
 */
public class DoGoodServlet extends HttpServlet {

    public static final String PRESENTATION_CONTROLLER_ATTRIBUTE_NAME = "PRESENTATION_CONTROLLER";

    /**
     * Source (or target) Java Object that is going to be wrapped.
     */
    private Object source;

    /**
     * Object Wrapper instance.
     */
    private ObjectWrapper objectWrapper;

    /**
     * Posts all the model or it's part to the server.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //TODO Create a message something like there is no session sorry etc.
        ModelViewController modelViewController
                = (ModelViewController) session.getAttribute(PRESENTATION_CONTROLLER_ATTRIBUTE_NAME);
        modelViewController.applyChanges(request.getParameterMap());
        try {
            objectWrapper.doReverse(modelViewController.getModel(), source);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchFieldException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     * Puts (replaces) all the model or it's part from the server.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        BufferedReader jsonReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder jsonObject = new StringBuilder();
        String st;
        while ((st = jsonReader.readLine()) != null) {
            jsonObject.append(st);
        }
        jsonReader.close();

        // creating parameter map from http parameter string
        Map<String, String> putMap = new HashMap<String, String>();
        StringTokenizer parameterTokenizer = new StringTokenizer(jsonObject.toString(), "&");
        while (parameterTokenizer.hasMoreTokens()) {
            String oneParameter = parameterTokenizer.nextToken();
            String[] parameter = oneParameter.split("=");
            putMap.put(parameter[0], parameter[1]);
        }

        if (!StringUtils.isEmpty(jsonObject.toString())) {
            ModelViewController modelViewController
                    = (ModelViewController) session.getAttribute(PRESENTATION_CONTROLLER_ATTRIBUTE_NAME);
            modelViewController.putElements(putMap);
        } else {
            // TODO create an error message 'nothing to put'
        }
    }

    /**
     * Gets all the model or it's part from the server.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        ModelViewController modelViewController
                = (ModelViewController) session.getAttribute(PRESENTATION_CONTROLLER_ATTRIBUTE_NAME);
        if (source == null) {
            source = new XStream().fromXML(
                    DoGoodServlet.class.getResourceAsStream("/house.init.xml")
            );
        }
        if (modelViewController == null) {
            try {
                objectWrapper = new ObjectWrapper();
                modelViewController = new ModelViewController(objectWrapper.doGood(source));
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
