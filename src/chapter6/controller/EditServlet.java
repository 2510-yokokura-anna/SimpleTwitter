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
import chapter6.exception.NoRowsUpdatedRuntimeException;
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

	 	HttpSession session = request.getSession();
      	List<String> errorMessages = new ArrayList<String>();

      	try {
      		Message messageForm = getMessage(request);
		    int id = messageForm.getId();
		    Message userIdConfirmation = new MessageService().reference(id);
		    if(userIdConfirmation != null) {
	            try {
	            	Message message = new MessageService().select(id);
	                request.setAttribute("message", message);
	            } catch (NoRowsUpdatedRuntimeException e) {
	            	log.warning("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
	                errorMessages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
	            }
		    }else {
		    	errorMessages.add("不正なパラメータが入力されました");
		    }

      	} catch (NumberFormatException  e) {
      		errorMessages.add("不正なパラメータが入力されました");
      	}

        if (errorMessages.size() != 0) {
            request.setAttribute("errorMessages", errorMessages);
            request.getRequestDispatcher("./").forward(request, response);
            return;
        }
        request.getRequestDispatcher("edit.jsp").forward(request, response);

    }

    // メッセージ編集（データベース上書き）
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        List<String> errorMessages = new ArrayList<String>();

        Message message = getMessage(request);

        if (!isValid(message, errorMessages)) {
            session.setAttribute("errorMessages", errorMessages);
            session.setAttribute("id", message.getId());
            session.setAttribute("text", message.getText());
            session.setAttribute("param", message.getId());
            request.setAttribute("id", message.getId());
            request.setAttribute("text", message.getText());
            request.setAttribute("param", message.getId());
            //response.sendRedirect("edit?id=" + request.getParameter("id") + "");
            request.getRequestDispatcher("edit.jsp").forward(request, response);
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));
        String text = request.getParameter("text");

        new MessageService().update(id, text);

        response.sendRedirect("./");
    }

    // メッセージ編集（フォーム内データ取得）
    private Message getMessage(HttpServletRequest request) throws IOException, ServletException {


	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	  String test = request.getParameter("id");
	  String test2 = request.getParameter("text");

	  	Message message = new Message();
	  	message.setId(Integer.parseInt(request.getParameter("id")));
	  	message.setText(request.getParameter("text"));
        return message;
    }

    // メッセージ編集（バリデーション）
    private boolean isValid(Message message, List<String> errorMessages) {

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	    int id = message.getId();
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