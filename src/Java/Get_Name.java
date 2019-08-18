import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;

@WebServlet("/Get_Name")
public class Get_Name extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求下载的文件名
        String urlStr = request.getParameter("url");

        String fileName = null;
        try {
            URL url = new URL(urlStr);
            URLConnection uc = url.openConnection();
            fileName = uc.getHeaderField("Content-Disposition");
            fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
            fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename*=utf-8''")+17),"UTF-8");
            System.out.println("文件名为：" + fileName + "  大小" + (uc.getContentLength()/1024)+"KB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
