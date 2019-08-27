package com.zarvis.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zarvis.master.repository.MasterRepository;
import com.zarvis.model.Master;

@RestController
@RequestMapping("mzarvis")
public class MasterController {
	@Autowired
//	@Qualifier("masterRepository")
	private MasterRepository masterRepository;

	@GetMapping("/master")
	public List<Master> getAllMaster() {
		return masterRepository.findAll();
	}
}
