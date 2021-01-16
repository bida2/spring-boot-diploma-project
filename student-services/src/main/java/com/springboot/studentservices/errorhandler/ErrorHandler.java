package com.springboot.studentservices.errorhandler;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.springboot.studentservices.entities.Companies;
import com.springboot.studentservices.entities.StockGroup;
import com.springboot.studentservices.errors.SignupException;
import com.springboot.studentservices.errors.StockGroupAdditionException;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(StockGroupAdditionException.class)
	public String fieldExistsError(StockGroupAdditionException e,Model model) {
		model.addAttribute("result", "<p class='mt-2 text-danger'>The stock group with the entered name already exists!</p>" );
		model.addAttribute("stockGroup",new StockGroup());
		return "addStockGroup";
	}
	
	@ExceptionHandler(SignupException.class)
	public String signupFieldExistsError(SignupException e,Model model) {
		model.addAttribute("status", "<p class='mt-2 text-danger'>Company Name AND/OR E-Mail is already registered! Please choose a different company name and/or e-mail! </p>" );
		model.addAttribute("companies",new Companies());
		return "signup";
	}
	

	
}
