package cn.stuManger.admin.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.stuManger.admin.domain.Notice;
import cn.stuManger.admin.service.NoticeService;
import cn.stuManger.pager.PageBean;

/**
 * Servlet implementation class NoticeServlet
 */
@SuppressWarnings("all")
@WebServlet("/NoticeServlet")
public class NoticeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    NoticeService service = new NoticeService();
  //奖学金信息管理
  		public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
  			//1.获得当前页码
  			int pd = getPd(request);
  			//2.创建PageBean
  			PageBean<Notice> pb = service.findAllNotice(pd);
  			//3.设置pb的url
  			String url = getUrl(request);
  			pb.setUrl(url);
  			request.setAttribute("pb", pb);
  			return "/jsps/admin/noticelist.jsp";
  		}
  		//获得url
  		private String getUrl(HttpServletRequest request) {
  			String uri = request.getRequestURI();//资源路径
  			String queryString = request.getQueryString();//参数
  			
  			//减去页码，有页面来决定
  			if(queryString.contains("&pd")){
  			    int index = queryString.indexOf("&pd");
  				queryString = queryString.substring(0,index);
  			}
  			//处理中文乱码
  			try {
  				queryString = new String(queryString.getBytes("iso-8859-1"),"utf-8");
  			} catch (UnsupportedEncodingException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
  			return uri+"?"+queryString;
  		}
  		//获得当前页码
  		private int getPd(HttpServletRequest request) {
  			String pd = request.getParameter("pd");
  			if(pd==null || pd.trim().isEmpty()){
  				return 1;
  			}
  			return Integer.valueOf(pd);
  		}
  		//对奖学金进行管理
  		//删除
  		public void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
  			String ids = request.getParameter("ids");
  			service.del(ids);
  			response.getWriter().print(true);
  		}
}
