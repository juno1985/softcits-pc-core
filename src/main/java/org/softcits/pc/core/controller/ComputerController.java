package org.softcits.pc.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.softcits.pc.core.model.MbgComputer;
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
}
