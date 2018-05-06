package org.softcits.pc.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.softcits.pc.core.exception.PC4XXException;
import org.softcits.pc.core.model.MbgComputer;
import org.softcits.pc.core.model.MbgComputerForm;
import org.softcits.pc.core.model.PCPager;
import org.softcits.pc.core.service.ComputerService;
@RestController
public class ComputerController {
	
	@Autowired
	private ComputerService computerService;

	/**
	 * 
	 * @param pageSize 每页显示的条数
	 * @param pageNum 当前页码
	 * @return
	 */
	@RequestMapping(path="/computer/all", method = RequestMethod.GET)
	public ResponseEntity<PCPager<MbgComputer>> getAllComputers(@RequestParam String pageSize, @RequestParam String pageNum){
		PCPager<MbgComputer> pcPager = computerService.getAllComputers(pageSize, pageNum);
		return new ResponseEntity<PCPager<MbgComputer>>(pcPager, HttpStatus.OK);
	}
	
	@RequestMapping(path="/computer/{cid}/delete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteById(@PathVariable String cid){
		return new ResponseEntity<String>(computerService.deleteById(cid),HttpStatus.OK);
	}
	
	@RequestMapping(path="/computer/add", method = RequestMethod.POST)
	public ResponseEntity<Map> computerAdd(@RequestBody @Valid MbgComputerForm mbgComputerForm,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			throw new PC4XXException(bindingResult.getFieldError().getDefaultMessage());
		}
		
		computerService.add(mbgComputerForm);
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", "Created Successfully");
		return new ResponseEntity<Map>(result, HttpStatus.CREATED);
	}
}
