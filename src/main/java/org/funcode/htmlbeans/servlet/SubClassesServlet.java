package org.funcode.htmlbeans.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: amatveev
 * Date: 22.11.12
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class SubClassesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Field f = null;
        try {
            // Each class loader has classes field containing Vector of loaded classes
            f = ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);
            Vector<Class> classes =  (Vector<Class>) f.get(Thread.currentThread().getContextClassLoader());
            List<String> classNames = new ArrayList<String>();
            for (Class clazz : classes) {
                classNames.add(clazz.getName());
            }
            response.setContentType("text/json");
            response.getWriter().print(new Gson().toJson(classNames));
        } catch (NoSuchFieldException e) {
            response.getWriter().print(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            response.getWriter().print(e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        response.getWriter().close();
    }
}
