package com.springboot.studentservices;



import java.security.Principal;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.studentservices.entities.Companies;
import com.springboot.studentservices.entities.ReservationHistory;
import com.springboot.studentservices.entities.Stock;
import com.springboot.studentservices.repositories.CompaniesRepository;
import com.springboot.studentservices.repositories.ResHistoryRepository;
import com.springboot.studentservices.repositories.StockRepository;


@RestController
public class ReserveRESTController {
	
	@Autowired
	private StockRepository stockRepo;
	@Autowired
	private CompaniesRepository companyRepo;
	@Autowired
	private ResHistoryRepository resHisRepo;
	
	@GetMapping(value="/reserve",produces="application/json")
	public LocalDateTime reserveStock(@RequestParam("sId") Long sId,Principal principal,ModelMap model) {
		// Create the time for the request
		// This time is best to be determined on the back-end
		LocalDateTime now = LocalDateTime.now();
		// Get stock by id
		Stock checkStock = stockRepo.getOne(sId);
		// Get reserving company by username
		Companies userCompany = companyRepo.findByEmail(principal.getName());
		// Create an instance of the ResHistory class so we can save this reservation
		ReservationHistory resHis = new ReservationHistory();
		// If the logged-in principal's name equals the company's name, disallow the reservation
	if (principal.getName() != userCompany.getEmail()) {
		if (checkStock.getReserveDate() == null || now.isAfter(checkStock.getReserveDate())) {
			// Create the current time and add 3 hours to it
			// to create reserve date
			//LocalDateTime reserveDate = now.plusHours(3);
			LocalDateTime reserveDate = now.plusMinutes(1);
			checkStock.setReserveDate(reserveDate);
			// Add the current stock the Set of reserved stocks for the logged in company/user
			userCompany.getResStocks().add(checkStock);
			if (checkStock.getReserveDate() != null) {
				// Set the data for the stock and the company that reserves the stock
				resHis.setCompanyId(userCompany.getId());
				resHis.setCompanyName(userCompany.getCompanyName());
				resHis.setResDate(checkStock.getReserveDate());
				resHis.setStockId(checkStock.getId());
				resHis.setStockName(checkStock.getStockName());
			}	
	    }
		// Save all entities and return the date and time
				companyRepo.save(userCompany);
				stockRepo.save(checkStock);
				resHisRepo.save(resHis);
    }
		return checkStock.getReserveDate();
}
}
