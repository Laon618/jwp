package core.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;

public class JsonApiController extends AbstractController{
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Question> questions = questionDao.findAll();
		
		ModelAndView mav = new ModelAndView(new JsonView());
		mav.addObject("questions", questions);
		return mav;
	}

}
