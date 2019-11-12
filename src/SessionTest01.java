import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class SessionTest01 extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){

        try{
            //中文乱码
            res.setContentType("text/html;charset=utf-8");

            //获取session
            HttpSession hs = req.getSession(true);
            String sid = hs.getId();
            hs.setAttribute("name","高建新");
            hs.setMaxInactiveInterval(30); //设置为0，删除整个session；设置为负值，session永不过期。

            PrintWriter pw = res.getWriter();

            pw.print("当前Session id:"+sid);


            //创建cookie、设置cookie存在时间、将cookie写到客户端
            Cookie myCookie = new Cookie("color","red");
            myCookie.setMaxAge(30);
            res.addCookie(myCookie);
            pw.print("<br>已经创建了cookie.");

            //创建ServletContext
            ServletContext sc = this.getServletContext();
            sc.setAttribute("content","hello,this is a ServletContext");
            pw.print("<br>已经创建了ServletContext.");

            //读取文件
            FileReader fr = new FileReader("D:\\IdeaProjects\\words.txt");
            BufferedReader br = new BufferedReader(fr);
            String str = null;
            while((str = br.readLine()) != null){
                //System.out.println(str);//此时str就保存了一行字符串
                pw.print("<br>"+str);
            }
            br.close();
            fr.close();



        }catch (Exception e){}

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        this.doGet(req, res);
    }
}
