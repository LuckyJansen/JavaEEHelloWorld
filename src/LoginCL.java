import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.sql.*;
import java.util.Enumeration;

public class LoginCL extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try{

            String u = req.getParameter("username");
            String p = req.getParameter("passwd");
            String sex = req.getParameter("sex");


            // JDBC 驱动名及数据库 URL
            String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            String DB_URL = "jdbc:mysql://singlenode:3306/test_db";

            // 数据库的用户名与密码，需要根据自己的设置
            String USER = "root";
            String PASS = "admin";
            Connection conn = null;
            Statement stmt = null;

            //分页变量
            int rowCount; //行数
            int pageSize=1; //每页行数
            int pageCount; //一共多少页
            int pageNow; //当前页




            try{
                // 注册 JDBC 驱动
                Class.forName(JDBC_DRIVER);

                // 打开链接
                System.out.println("连接数据库...");
                conn = DriverManager.getConnection(DB_URL,USER,PASS);

                // 执行查询
                System.out.println(" 实例化Statement对象...");
                stmt = conn.createStatement();
                String sql;
                sql = "SELECT id,username,passwd FROM test_db.users where username='"+u+"';";
                ResultSet rs = stmt.executeQuery(sql);

                // 展开结果集数据库
//                while(rs.next()){
//                    // 通过字段检索
//                    int id  = rs.getInt("id");
//                    String name = rs.getString("name");
//
//
//                    // 输出数据
//                    System.out.print("ID: " + id);
//                    System.out.print(", 站点名称: " + name);
//                    System.out.print("\n");
//                }

                if(rs.next()){
                    //合法
                    //得到session空间
                    HttpSession hs = req.getSession();
                    hs.setMaxInactiveInterval(30); //发呆时间30s
                    hs.setAttribute("pass","ok");
                    hs.setAttribute("session_username",u);

                    res.sendRedirect("welcome?uname="+u+"&upass="+p+"&usex="+sex);
                }else{
                    //不合法
                    res.sendRedirect("login?info=error"); //写要跳转到的servlet的url名
                }

                // 完成后关闭
                rs.close();
                stmt.close();
                conn.close();
            }catch(SQLException se){
                // 处理 JDBC 错误
                se.printStackTrace();
            }catch(Exception e){
                // 处理 Class.forName 错误
                e.printStackTrace();
            }finally{
                // 关闭资源
                try{
                    if(stmt!=null) stmt.close();
                }catch(SQLException se2){
                }// 什么都不做
                try{
                    if(conn!=null) conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
                res.sendRedirect("login?info=error"); //写要跳转到的servlet的url名
            }





        }catch(Exception e){}
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        this.doGet(req, res); //这样写，不管调用get还是post方法，都可以被正确调用
    }

}
