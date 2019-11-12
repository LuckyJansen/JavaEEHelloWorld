import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Welcome extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){

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
        int pageSize=5; //每页行数
        int pageCount=0; //一共多少页
        int pageNow=1; //当前页

        try{
            //中文乱码
            res.setContentType("text/html;charset=utf-8");

            String u = req.getParameter("uname");
            String p = req.getParameter("upass");
            String sex= req.getParameter("usex");
            String pn= req.getParameter("pageNow");
            if(pn != null)pageNow=Integer.parseInt(pn);

            //获取session
            HttpSession hs = req.getSession();
            String val = (String) hs.getAttribute("pass");
            String username = (String) hs.getAttribute("session_username");
            if(val == null){ //如果没有session，就是非法登录
                res.sendRedirect("login");
            }

            PrintWriter pw = res.getWriter();
            //返回欢迎界面
            pw.print("<html>");
            pw.print("<body>");
            pw.print(u);
            pw.print("<h1>Welcome J2EE World."+username+".密码是"+p+".性别是"+sex+"<h1><br>");
            pw.print("<img src=view.jpg><br>");







            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            ResultSet rs;
            sql = "SELECT count(1) FROM test_db.users;";
            rs = stmt.executeQuery(sql);
            if(rs.next()) {
                //有记录
                pw.println("进入count这里了，有记录");
                rowCount = rs.getInt(1);
                if (rowCount % pageSize == 0) {
                    pageCount = rowCount / pageSize;
                } else {
                    pageCount = rowCount / pageSize + 1;
                }
                pw.print("出去count这里了");
            }


            //这种写法，删除中间记录后，再用id出数会有问题
            //sql = "SELECT id,username,passwd FROM test_db.users where id >("+pageNow+"-1)* "+pageSize+" limit "+pageSize;

            //这样写可以避免主机自增中间记录被删除的问题，但是记录数多了速度会慢
            int rowNum=pageSize*(pageNow-1);
            sql = "SELECT id,username,passwd FROM test_db.users \n" +
                    "where id not in (select id from (select id from test_db.users limit  "+rowNum+") as t )\n" +
                    "limit "+pageSize ;

            rs = stmt.executeQuery(sql);

            pw.print("<table border=1>");
            pw.print("<tr>");
            pw.print("<th>ID</th>");
            pw.print("<th>用户名</th>");
            pw.print("<th>密码</th>");
            pw.print("</tr>");
            while(rs.next()){
                pw.print("<tr>");
                pw.print("<td>"+rs.getInt(1)+"</td>");
                pw.print("<td>"+rs.getString(2)+"</td>");
                pw.print("<td>"+rs.getString(3)+"</td>");
                pw.print("</tr>");
            }
            pw.print("</table>");

            //显示超链接
            //上一页
            if(pageNow!=1)
            pw.println("<a href=welcome?pageNow="+(pageNow-1)+">上一页</a>");
            for(int i=pageNow; i<=pageNow+9 && i<=pageCount; i++){
                pw.println("<a href=welcome?pageNow="+i+">"+i+"</a>");
            }
            //下一页
            System.out.println(pageCount);
            if(pageNow < pageCount)
            pw.println("<a href=welcome?pageNow="+(pageNow+1)+">下一页</a>");




            pw.print("</body>");
            pw.print("</html>");

        }catch (Exception e){}

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        this.doGet(req, res);
    }
}
