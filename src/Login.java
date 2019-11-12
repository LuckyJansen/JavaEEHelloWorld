import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Login extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){

        try{
            //中文乱码
            res.setContentType("text/html;charset=utf-8");
            PrintWriter pw = res.getWriter();
            String info =req.getParameter("info");
            String s = "男人";
            //返回登录界面
            pw.print("<html>");
            pw.print("<body>");
            if(info != null){
                pw.println("<h1>用户或密码错误！</h1>");
            }
            pw.print("<h1>登录界面</h1>");

            pw.print("<form action=logincl method=post>");
            pw.print("用户名：<input type=text name=username><br>");
            pw.print("密码：<input type=password name=passwd><br>");
            pw.print("<input type=hidden name=sex value="+s+">");
            pw.print("<input type=submit value=login><br>");
            pw.print("</form>");
            pw.print("</body>");
            pw.print("</html>");
            System.out.println("print it.");
        }catch (Exception e){}

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        this.doGet(req, res);
    }
}
