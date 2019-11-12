import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.*;

public class SessionTest02 extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){

        try{
            //中文乱码
            res.setContentType("text/html;charset=utf-8");

            //获取session
            HttpSession hs = req.getSession(true);
            String sid = hs.getId();
            String name = (String) hs.getAttribute("name");

            PrintWriter pw = res.getWriter();

            pw.println("当前Session id:"+sid+",当前Session id中名字是:"+name);
            hs.removeAttribute("name");
            name = (String) hs.getAttribute("name");
            pw.println("当前Session id:"+sid+",删除name属性后，当前Session id中名字是:"+name);

            //从客户端得到所有cookie
            Cookie [] allcookie = req.getCookies();
            int i;
            if(allcookie != null){
                for (i=0; i<allcookie.length;i++){
                    Cookie tmp = allcookie[i];
                    if(tmp.getName().equals("color")){
                        pw.print("<br>color="+tmp.getValue());
                        break;
                    }
                }
                if(i == allcookie.length){
                    pw.print("<br>cookie过期了。");
                }
            }else {
                pw.print("<br>当前无cookie");
            }

            //创建ServletContext
            ServletContext sc = this.getServletContext();
            String s = (String) sc.getAttribute("content");
            pw.print("<br>从ServletContext取出的值为：" + s);

            //读取文件
            FileWriter fr = new FileWriter("D:\\IdeaProjects\\write_words.txt");
            BufferedWriter bw = new BufferedWriter(fr);
            String str = "hello world!!!\n";
            bw.write(str);
            pw.print("<br>已写入文件内容：" + str);
            bw.close();
            fr.close();


        }catch (Exception e){}

    }
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        this.doGet(req, res);
    }
}
