import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Utils.C3P0Utils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

@WebServlet("/Get_File")
public class Get_File extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决ajax返回json数据中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

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
        }
        if(list==null || list.size() ==0){
            response.getWriter().write(JSON.toJSONString("无效CDK"));
        } else {
            if(list.get(0).getTimes()>0){
                String url = request.getParameter("url");
                String fileName = null;

                //下载到文件夹目录
                File file=new File("C:\\Users\\Zimomo\\IdeaProjects\\Download_File\\web\\resource");
                //判断文件夹是否存在
                if (!file.exists())
                {
                    //如果文件夹不存在，则创建新的的文件夹
                    file.mkdirs();
                }
                FileOutputStream fileOut = null;
                HttpURLConnection conn = null;
                InputStream inputStream = null;
                try
                {
                    // 建立链接
                    URL httpUrl=new URL(url);

                    //1.获取请求下载的文件名
                    URLConnection uc = httpUrl.openConnection();
                    fileName = uc.getHeaderField("Content-Disposition");
                    fileName = new String(fileName.getBytes("ISO-8859-1"), "GBK");
                    fileName = URLDecoder.decode(fileName.substring(fileName.indexOf("filename=\"")+10,fileName.indexOf("\";")),"UTF-8");
                    conn=(HttpURLConnection) httpUrl.openConnection();
                    //以Post方式提交表单，默认get方式
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    // post方式不能使用缓存
                    conn.setUseCaches(false);
                    //连接指定的资源
                    conn.connect();
                    //获取网络输入流
                    inputStream=conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    //判断文件的保存路径后面是否以/结尾
                    //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
                    System.out.println(fileName);
                    fileOut = new FileOutputStream("C:\\Users\\Zimomo\\IdeaProjects\\Download_File\\web\\resource\\"+fileName);
                    BufferedOutputStream bos = new BufferedOutputStream(fileOut);

                    byte[] buf = new byte[4096];
                    int length = bis.read(buf);
                    //保存文件
                    while(length != -1)
                    {
                        bos.write(buf, 0, length);
                        length = bis.read(buf);
                    }
                    bos.close();
                    bis.close();
                    conn.disconnect();
                    System.out.println("下载成功！！");

                    try {
                        runner.update(connection,"update CDK set Times=Times-1 where Number='"+Number+"'");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }finally {
                        C3P0Utils.close(connection,(PreparedStatement)null, (ResultSet)null);
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                    System.out.println("抛出异常！！");
                }
                response.getWriter().write(JSON.toJSONString(fileName));
            }else{
                response.getWriter().write(JSON.toJSONString("次数不足"));
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
