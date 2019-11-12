import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HelloHttp extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try{
            PrintWriter pw = res.getWriter();
            pw.print("hello,httpServlet.!");
            System.out.println("print it.");
        }catch (Exception e){}

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        this.doGet(req, res);
    }
}
