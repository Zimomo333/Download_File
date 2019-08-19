package Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3P0Utils {

    //通过标识名来创建相应连接池
    static ComboPooledDataSource dataSource=new ComboPooledDataSource();
    //从连接池中取用一个连接
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //释放连接回连接池
    public static void close(Connection conn, PreparedStatement pst, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Exception in C3p0Utils!");
                e.getErrorCode();
            }
        }
        if(pst!=null){
            try {
                pst.close();
            } catch (SQLException e) {
                System.out.println("Exception in C3p0Utils!");
                e.getErrorCode();
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Exception in C3p0Utils!");
                e.getErrorCode();
            }
        }
    }
    //调用上面的close
    private static void realseSource( Connection _conn, PreparedStatement _st,ResultSet _rs){
        close(_conn,_st,_rs);
    }
    //调用上面的realseSourse()
    public static void realseSource(DBUtil_BO _vo){
        if(_vo!=null){
            realseSource(_vo.conn, _vo.st, _vo.rs);
        }
    }
    //操作
    //注意：查询操作完成后，因为还需提取结果集中信息，所以仍保持连接，在结果集使用完后才通过DBUtils.realseSource()手动释放连接
    public static void executeQuery(DBUtil_BO vo)
    {
        try{
            vo.rs = vo.st.executeQuery();
        }catch (SQLException e){
            realseSource(vo);
            e.getErrorCode();
        }
    }

    //而update操作完成后就可以直接释放连接了，所以在方法末尾直接调用了realseSourse()
    public static  void executeUpdate(DBUtil_BO vo)
    {

        Connection conn = vo.conn;
        PreparedStatement st = vo.st;
        try {
            st.executeUpdate();
        } catch (SQLException e) {
            realseSource(conn, st, null);
            e.getErrorCode();
        }
        realseSource(conn, st,null );

    }
}
