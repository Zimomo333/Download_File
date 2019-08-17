import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Download
 */
@WebServlet("/download")
public class download extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求下载的文件名
        String filename = request.getParameter("filename");

        //2.获取文件的文件系统路径
        String filePath = request.getServletContext().getRealPath("resource/"+filename);

        //3.设置响应头，提示浏览器不要解析响应的文件数据，而是以附件(attachment)的形式解析，即下载功能
        response.setContentType(this.getServletContext().getMimeType(filename));
        response.setHeader("Content-Disposition", "attachment;filename="+filename);

        //4.读取文件的 输入流，以及响应的输出流，并将数据输出给客户端
        InputStream in = new FileInputStream(filePath);
        ServletOutputStream out = response.getOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while((len=in.read(buf))!=-1) {
            out.write(buf, 0, len);
        }

        in.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}