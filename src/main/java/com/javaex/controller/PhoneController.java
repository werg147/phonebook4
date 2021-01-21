package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value = "/phone")
public class PhoneController {

	// 필드
	@Autowired
	private PhoneDao phoneDao;
	
	//생성자/메소드gs 생략

	/** 메소드 활용**메소드 단위로 기능을 정의 -> 기능마다 url부여 **/
	// 테스트

	// 리스트
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("list");

		// dao -> 리스트 가져오기
		List<PersonVo> personList = phoneDao.getPersonList();

		// model -> data를 보내는 방법 -> 담아놓으면 된다
		model.addAttribute("pList", personList);
		// System.out.println(personList.toString()); 테스트

		return "list";
	}

	// 등록폼
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {
		System.out.println("writeForm");

		return "writeForm";
	}

	// http://localhost:8088/phonebook3/phone/write?name=테스트hp=010-1111-1111&company=02-2222-2222
	// 등록
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@RequestParam("name") String name, @RequestParam("hp") String hp,
			@RequestParam("company") String company) {

		System.out.println("write");

		PersonVo personVo = new PersonVo(name, hp, company);
		System.out.println(personVo.toString());

		phoneDao.personInsert(personVo);

		return "redirect:/phone/list";
	}

	// 수정폼 -> modifyForm
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(Model model, @RequestParam("personId") String personId) {
		System.out.println("modifyForm");

		// 파라미터 id값 읽기
		int personID = Integer.parseInt(personId);

		// dao -> 수정할 데이터 가져오기(1명)
		PersonVo person = phoneDao.getPerson(personID);

		// model 데이터 담기
		model.addAttribute("personVo", person);

		return "modifyForm";
	}

	// http://localhost:8088/phonebook3/phone/modifyForm?name=리다이렉트&hp=010-0000-2222&company=02-0000-2222
	// 수정 -> modify @RequestParam
	@RequestMapping(value = "/modify2", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify2(@RequestParam("name") String name, 
						  @RequestParam("hp") String hp,
						  @RequestParam("company") String company,
						  @RequestParam("personId") int personId) {

		System.out.println("modify");

		// 파라미터값 묶기
		PersonVo personVo = new PersonVo(personId, name, hp, company);

		// dao -> 수정
		phoneDao.personUpdate(personVo);

		return "redirect:/phone/list";
	}

	// 수정 -> modify @ModelAttribute
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute PersonVo personVo) { //@ModelAttribute생략가능

		System.out.println("modify");
		
		System.out.println(personVo.toString());

		// dao -> 수정
		phoneDao.personUpdate(personVo);

		return "redirect:/phone/list";
	}

	// 삭제 ->dlelte
	@RequestMapping(value = "/delete2", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete2(@RequestParam("personId") int personId) {
		System.out.println("delete");

		// dao -> 삭제
		phoneDao.personDelete(personId);

		return "redirect:/phone/list";
	}
	
	// 삭제 ->dlelte @RequestMapping 약식표현
	@RequestMapping(value = "/delete/{personId}", method = { RequestMethod.GET, RequestMethod.POST })
	public String delete(@PathVariable("personId") int personId) {
		System.out.println("delete");
		System.out.println(personId);

		// dao -> 삭제
		phoneDao.personDelete(personId);

		return "redirect:/phone/list";
	}

} 
