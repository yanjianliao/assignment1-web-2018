package webdev.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.BaseExamQuestion;
import webdev.models.EssayExamQuestion;
import webdev.models.Exam;
import webdev.models.MultipleChoiceExamQuestion;
import webdev.repositories.BaseExamQuestionRepository;
import webdev.repositories.EssayExamQuestionRepository;
import webdev.repositories.ExamRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EssayExamQuestionService {
	
	@Autowired
	EssayExamQuestionRepository repository;
	
	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("/api/essay")
	public List<EssayExamQuestion> findAllMultipleChoiceExamQuestions() {
		return (List<EssayExamQuestion>) repository.findAll();
	}
	
	@GetMapping("/api/exam/{eId}/essay")
	public List<EssayExamQuestion> findEssayExamQuestionForExam(@PathVariable("eId") int id) {
		Optional<Exam> data = examRepository.findById(id);
		List<EssayExamQuestion> list = new ArrayList<>();
		if(data.isPresent()) {
			Exam exam = data.get();
			for(BaseExamQuestion q : exam.getBaseExamQuestions()) {
				if(q instanceof EssayExamQuestion) {
					list.add((EssayExamQuestion) q);
				}
			}
		}
		return list;
	}
	
	@PostMapping("/api/exam/{eId}/essay")
	public EssayExamQuestion createForExam(@PathVariable("eId") int id, @RequestBody EssayExamQuestion question) {
		Optional<Exam> data = examRepository.findById(id);
		if(data.isPresent()) {
			Exam exam = data.get();
			question.setExam(exam);
			repository.save(question);
		}
		return question;
	}
	
	@PutMapping("/api/essay/{eId}")
	public EssayExamQuestion updateById(@PathVariable("eId") int id, @RequestBody EssayExamQuestion question) {
		Optional<EssayExamQuestion> data = repository.findById(id);
		if(data.isPresent()) {
			EssayExamQuestion old = data.get();
			old.setTitle(question.getTitle());
			old.setDescription(question.getDescription());
			old.setPoints(question.getPoints());
			old.setSubtitle(question.getSubtitle());
			repository.save(old);
		}
		return question;
	}
	
	
	@DeleteMapping("/api/essay/{cId}")
	public void deleteById(@PathVariable("cId") int id) {
		repository.deleteById(id);
	}
}
