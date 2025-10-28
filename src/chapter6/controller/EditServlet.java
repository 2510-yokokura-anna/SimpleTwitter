package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {
    /**
    * ロガーインスタンスの生成
    */
    Logger log = Logger.getLogger("twitter");

    /**
    * デフォルトコンストラクタ
    * アプリケーションの初期化を実施する。
    */
    public EditServlet(){
        InitApplication application = InitApplication.getInstance();
        application.init();
    }

    // メッセージ編集（ページ読み込み）
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
  	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

  	  	HttpSession session = request.getSession();
      	List<String> errorMessages = new ArrayList<String>();

		if(request.getParameter("id") != null && request.getParameter("id").matches("^[0-9]*$")) {
      		Message messageForm = getMessage(request);
		    int id = messageForm.getId();
		    Message userIdConfirmation = new MessageService().select(id);
		    if(userIdConfirmation != null) {
            	Message message = new MessageService().select(id);
                request.setAttribute("message", message);
		    }else {
		    	errorMessages.add("不正なパラメータが入力されました");
		    }
		}else {
			errorMessages.add("不正なパラメータが入力されました");
		}

        if (errorMessages.size() != 0) {
        	session.setAttribute("errorMessages", errorMessages);
        	response.sendRedirect("./");
            return;
        }
        request.getRequestDispatcher("edit.jsp").forward(request, response);

    }

    // メッセージ編集（データベース上書き）
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

  	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
  	        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

        List<String> errorMessages = new ArrayList<String>();

        Message message = getMessage(request);

        if (!isValid(message, errorMessages)) {
        	request.setAttribute("errorMessages", errorMessages);
            request.setAttribute("message", message);
            request.getRequestDispatcher("edit.jsp").forward(request, response);
            return;
        }

        new MessageService().update(message);
        response.sendRedirect("./");
    }

    // メッセージ編集（フォーム内データ取得）
    private Message getMessage(HttpServletRequest request) throws IOException, ServletException {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	  	Message message = new Message();
	  	message.setId(Integer.parseInt(request.getParameter("id")));
	  	message.setText(request.getParameter("text"));
        return message;
    }

    // メッセージ編集（バリデーション）
    private boolean isValid(Message message, List<String> errorMessages) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	    String text = message.getText();

        if (StringUtils.isBlank(text)) {
            errorMessages.add("メッセージを入力してください");
        } else if (140 < text.length()) {
            errorMessages.add("140文字以下で入力してください");
        }

        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }

}