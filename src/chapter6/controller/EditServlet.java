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

	  log.info(new Object(){}.getClass().getEnclosingClass().getName() +
        " : " + new Object(){}.getClass().getEnclosingMethod().getName());

	    int id = Integer.parseInt(request.getParameter("id"));

        List<String> errorMessages = new ArrayList<String>();
	    Message messageForm = getMessage(request);

        if (isValid(messageForm, errorMessages)) {
            try {
            	Message message = new MessageService().select(id);
                request.setAttribute("message", message);
            } catch (NoRowsUpdatedRuntimeException e) {
		    log.warning("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
                errorMessages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
            }
        }

        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }

    // メッセージ編集（データベース上書き）
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        String text = request.getParameter("text");
        new MessageService().update(id, text);

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

        int id = message.getId();
        String text = message.getText();
        Message userIdConfirmation = new MessageService().select(id);

        if (userIdConfirmation == null || userIdConfirmation.getId() != id) {
        	errorMessages.add("不正なパラメータが入力されました");
        }

        if (errorMessages.size() != 0) {
            return false;
        }
        return true;
    }
}