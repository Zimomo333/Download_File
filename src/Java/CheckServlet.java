import Utils.C3P0Utils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String Number = request.getParameter("Number");

        QueryRunner runner = new QueryRunner();
        List<CDK> list =null;
        Connection connection = C3P0Utils.getConnection();
        try {
            list=runner.query(connection, "select Number,Times,Used from CDK where Number='"+Number+"'", new BeanListHandler<>(CDK.class));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            C3P0Utils.close(connection,(PreparedStatement)null, (ResultSet)null);
        }
        String string=null;
        if(list==null || list.size() ==0){
            string = JSON.toJSONString("0");
        } else {
            string = JSON.toJSONString(list.get(0).getTimes()+"");
        }
        response.getWriter().write(string);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
