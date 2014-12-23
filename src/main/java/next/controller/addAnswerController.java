package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.AbstractController;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class addAnswerController extends AbstractController {
	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();
	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");
		long questionId =ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		String contents = ServletRequestUtils.getRequiredStringParameter(request, "contents");
		Answer answer = new Answer(writer, contents, questionId);
		
		answerDao.insert(answer);
		questionDao.updateCountOfComment(questionId);
		
		ModelAndView mav = new ModelAndView(new JsonView());

		return mav;
	}

}
