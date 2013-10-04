package org.funcode.htmlbeans.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * This servlet is created to find all non abstract members of the class hierarchy, staring from the given class name.
 * User: amatveev
 * Date: 22.11.12
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class SubClassesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parentClassString = request.getParameter("parent");
        if (parentClassString != null) {
            Field f = null;
            try {
                Class parentClass = Class.forName(parentClassString);
                Set<String> childClasses = new HashSet<String>();
                // Each class loader has classes field containing Vector of loaded classes
                f = ClassLoader.class.getDeclaredField("classes");
                f.setAccessible(true);
                Vector<Class> classes = (Vector<Class>) f.get(Thread.currentThread().getContextClassLoader());
                for (Class clazz : classes) {
                    if (parentClass.isAssignableFrom(clazz)) {
                        childClasses.add(clazz.getName());
                    }
                }

                response.setContentType("text/json");
                response.getWriter().print(new Gson().toJson(childClasses));
            } catch (NoSuchFieldException e) {
                response.getWriter().print(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                response.getWriter().print(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ClassNotFoundException e) {
                response.getWriter().print(e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        response.getWriter().close();
    }
}
