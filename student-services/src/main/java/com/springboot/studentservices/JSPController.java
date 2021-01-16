package com.springboot.studentservices;



import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.text.WordUtils;
import org.hibernate.search.exception.EmptyQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.studentservices.entities.Article;
import com.springboot.studentservices.entities.Companies;
import com.springboot.studentservices.entities.EpayCredentials;
import com.springboot.studentservices.entities.ReservationHistory;
import com.springboot.studentservices.entities.Role;
import com.springboot.studentservices.entities.Stock;
import com.springboot.studentservices.entities.StockGroup;
import com.springboot.studentservices.entities.User;
import com.springboot.studentservices.errors.SignupException;
import com.springboot.studentservices.errors.StockGroupAdditionException;
import com.springboot.studentservices.repositories.ArticleRepository;
import com.springboot.studentservices.repositories.CompaniesRepository;
import com.springboot.studentservices.repositories.EpayCredentialsRepository;
import com.springboot.studentservices.repositories.ResHistoryRepository;
import com.springboot.studentservices.repositories.RoleRepository;
import com.springboot.studentservices.repositories.StockGroupRepository;
import com.springboot.studentservices.repositories.StockRepository;
import com.springboot.studentservices.repositories.StockSearchRepository;
import com.springboot.studentservices.repositories.UserRepository;
import com.springboot.studentservices.userservices.FileStorage;
import com.springboot.studentservices.utils.FormatterDate;
import com.springboot.studentservices.utils.SHA1RFC2104HMAC;
import com.springboot.studentservices.validators.PasswordValidator;


@Controller
@PropertySource("classpath:application.properties")
public class JSPController {
	
		private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// Create repositories for our user entered data
		@Autowired
		private CompaniesRepository companyRepo;
		
		@Autowired
		private StockRepository stockRepo;
		
		@Autowired
		private UserRepository userRepo;
		
		@Autowired
		private RoleRepository roleRepo;
		
		@Autowired
		private StockGroupRepository groupRepo;
		
		@Autowired
		private FileStorage fileStorage;
		
		@Autowired
		private StockSearchRepository stockSearchRepo; 
		
		@Autowired
		private ArticleRepository articleRepo;
		
		@Autowired
		private ResHistoryRepository resHisRepo;
		
		@Autowired
		private EpayCredentialsRepository epayRepo;
		
		// For ePay integration
	    @Autowired
	    private HttpServletRequest request;
		
		// Not @Autowired variables
		private PasswordValidator passValidator = new PasswordValidator();
		
		// Create FullTextSession for Hibernate Search initialization
		

	
		
		@RequestMapping(value="/signup",method=RequestMethod.GET)
		public String signupPage(ModelMap model) {
			model.addAttribute("companies", new Companies());
			return "signup";
		}
		
		@RequestMapping("/admin") 
		public String adminPage(ModelMap model) {
			model.addAttribute("companiesInfo",companyRepo.findAll());
			return "admin";
		}
		
		 
		    

		    @RequestMapping(value = "/login", method = RequestMethod.GET)
		    public String login(Model model, String error, String logout) {
		        if (error != null)
		            model.addAttribute("error", "Your username and password is invalid.");

		        if (logout != null)
		            model.addAttribute("message", "You have been logged out successfully.");

		        return "login";
		    }
		    // This GET Mapping is for attacker-inserted reserve buttons in all
		    // Stock Browse pages -> it redirects to the referer
		    @RequestMapping(value="/reserve", method = RequestMethod.GET)
		    public String redirectToReferrer(Model model) {
		    	return "redirect:" + request.getHeader("referer");
		    }
		    


		    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
		    public String welcome(Model model) {
		    	// Get all articles for the home page and add them to /welcome
		    	List<Article> articles = articleRepo.findAll();
		    	model.addAttribute("articles", articles);
		        return "welcome";
		    }
	
		
		@RequestMapping(value="/addCompany",method=RequestMethod.POST)
		public String submit(@Valid @ModelAttribute("companies") Companies company,BindingResult result,ModelMap model) {
			if (result.hasErrors()) {
				model.addAttribute("status", "<span class='text-capitalize text-danger mt-2'>Registration failed due to missing/insufficient information! Check your user data and try again!</span>");
				return "signup";
			}
			// Check if password is valid - has at least 8 characters
			// has at least 1 digit, 1 uppercase character and 1 lowercase character
			if (!passValidator.validate(company.getPassword())) {
				model.addAttribute("status", "<span class='text-capitalize text-danger mt-2'>Submitted password does not match the criteria!</span>");
				return "signup";
			}
			// Create a new user 
			User user = new User();
			// create a new user roles set
			Set<Role> roles = new HashSet<Role>();
			// Create a new role for the user ---> this will be a USER Role
			// In the future, a separate ADMIN role registration page may be developed
			Role role = new Role();
			role.setName("USER");
			// add this to the set
			roles.add(role);
	        // Set password to its hashed equivalent
	        // for storage in the database
			String encPass = encoder.encode(company.getPassword());
	        company.setPassword(encPass);
	        // This is supposed to be our foreign key to the Users table...
	        company.setUser(user);
	        user.setPassword(encPass);
	        user.setUsername(company.getEmail());
	        user.setRoles(roles);
	        if (userRepo.existsByUsername(user.getUsername())) {
	        	throw new SignupException("<p class='mt-2 text-danger'>User already exists!</p>");
	        } else {
	        	userRepo.save(user);
		        companyRepo.save(company);
		        // Save all attached user roles to Role table
		        roleRepo.saveAll(roles);
		        model.addAttribute("status","<span class='text-capitalize text-success mt-2'>Registration successful!</span>");
		        // Used to reset the form 
		        model.addAttribute("companies",new Companies());
	        }
	        return "signup";
		}
		
		@RequestMapping(value="/addStock",method=RequestMethod.GET)
		public String addStockPage(Model model) {
			List<StockGroup> stockGroups = groupRepo.findAll();
			model.addAttribute("groupList",stockGroups);
			model.addAttribute("stock",new Stock());
			return "addStock";
		}
		
		@RequestMapping(value="/addStockResult",method=RequestMethod.POST)
		public String submit(@Param("stockGroup") String stockGroup,@ModelAttribute("file") MultipartFile file,@Valid @ModelAttribute("stock") Stock stock,BindingResult result,ModelMap model) {
			if (result.hasErrors()) {
				model.addAttribute("status", "<span class='text-capitalize text-danger mt-2'>Stock addition failed! Check your user data and try again!</span>");
				model.addAttribute("message", result.getFieldError());
				return "addStock";
			}
	        // Check if user is authenticated and if yes, get username from user session
			String currentUserName = "";
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!(authentication instanceof AnonymousAuthenticationToken)) {
			    currentUserName = authentication.getName();
			}

			// Get the stock group through the groupRepo
			StockGroup ourStockGroup = groupRepo.findByStockGroupName(stockGroup);
			// Get the filename so we can set it to stock and save to db
			stock.setThumbnail("filestorage/" + file.getOriginalFilename().toString());
			try {
				fileStorage.store(file);
				// Testing model attribute --> remove in production 
				//model.addAttribute("message", "File uploaded successfully! -> filename = " + file.getOriginalFilename());
			} catch (Exception e) {
				// Below attributes should be removed when going into production
				// They are purely for testing
				model.addAttribute("message", "Couldn't upload file " + file.getOriginalFilename() + " because an error occured during upload! <p class='text-danger'>" + e.getMessage()  + "</p>");
				model.addAttribute("status", "<span class='text-capitalize text-danger mt-2'>Stock addition failed: Missing thumbnail or incorrect format for thumbnail!</span>");
				return "addStock";
			}
			// Get company by searching for retrieved username
			// Since Companies and Users are linked and email = username
			// We can search by email adress in the Companies table
			Companies company = companyRepo.findByEmail(currentUserName);
			// Get the company's Set<Stock> stocks field and add the new stock to the Set structure
			company.getStocks().add(stock);
			stock.getGroups().add(ourStockGroup);
			// Set the final price for our new stock
			stock.setFinalPrice(stock.getPricePerItem() * stock.getQuantity());
			// Save both entities to their respective tables
	        stockRepo.save(stock);
	        companyRepo.save(company);
	        // Get all StockGroups and re-insert them into the form
	     	List<StockGroup> allGroups = groupRepo.findAll();
	     	model.addAttribute("groupList", allGroups);
	        // Add attributes to give user feedback on the page
	        model
	        .addAttribute("status","<span class='text-success mt-2'>Stock successfully added to " + company.getCompanyName() + "'s products list!</span>");
	        // Used to reset the form, must be added to every form that has modelAttribute="..." in the <form:form> tag
	        model.addAttribute("stock",new Stock());
			return "addStock";
		}
		
		@RequestMapping(value="/addArticle",method=RequestMethod.GET)
		public String addArticleView(ModelMap model) {
			model.addAttribute("article",new Article());
			return "addArticle";
		}
		
		
		@RequestMapping(value="/addArticle",method=RequestMethod.POST)
		public String addArticleResult(@Valid @ModelAttribute("article") Article article,BindingResult result,ModelMap model) {
			if (result.hasFieldErrors()) {
				model.addAttribute("result", "<p class='mt-2 text-danger'>Article title and text fields are empty!</p>");
				return "addArticle";
			}
	        // Get current date from Calendar
	        Date currDate = Calendar.getInstance().getTime();
	        // Convert into LocalDate format
	        LocalDate date = currDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        // Format date to the format "3 Dec 2011" 
			article.setPostDate(date);
			// Get username so we can set poster in the Article row that is being created
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String username = auth.getName();
			User poster = userRepo.findByUsername(username);
			article.setPoster(poster);
			articleRepo.save(article);
			model.addAttribute("article",new Article());
			return "addArticle";
		}
		
		
		@RequestMapping(value="/stockBrowse",method=RequestMethod.GET) 
		public String browse(ModelMap model,HttpServletRequest request) {
			// Get all Stocks in DB	and Companies		
			List<Companies> companies = companyRepo.findAll();
			// Use the Template Engine to put the data in the View
			model.addAttribute("companyList", companies);
			return "stockBrowse";
		}
		
		@RequestMapping(value="/stockBrowse",method=RequestMethod.POST) 
		public String browseCompanyStock(@RequestParam("compName") String company,ModelMap model) {		
			// Get all Stocks in DB	and Companies		
			List<Companies> companies = companyRepo.findAll();
			// Use the Template Engine to put the data in the View
			model.addAttribute("companyList", companies);
			Companies searchCompany = companyRepo.findByCompanyName(company);
			Set<Stock> stocks = searchCompany.getStocks();
			model.addAttribute("result",stocks);
			return "stockBrowse";
		}
		
		@RequestMapping(value="/stockBrowse/stockBrowseGroup",method=RequestMethod.GET) 
		public String browseByGroup(ModelMap model) {
			// Get all Groups from DB	
			List<StockGroup> groups = groupRepo.findAll();
			// Use the Template Engine to put the data in the View
			model.addAttribute("groupList", groups);
			return "stockBrowseGroup";
		}
		
		@RequestMapping(value="/stockBrowse/stockBrowseGroup",method=RequestMethod.POST) 
		public String browseByGroupSearch(@RequestParam("groupName") String group,ModelMap model) {		
			// Get all Groups from DB	
			List<StockGroup> groups = groupRepo.findAll();
			// Use the Template Engine to put the data in the View
			model.addAttribute("groupList", groups);
			StockGroup stockGroup = groupRepo.findByStockGroupName(group);
			Set<Stock> stocks = stockGroup.getStockGroups();
			model.addAttribute("result",stocks);
			return "stockBrowseGroup";
		}
		
		@RequestMapping(value="/addStockGroup",method=RequestMethod.GET)
		public String addStockGroupView(@Valid @ModelAttribute("stockGroup") StockGroup group,BindingResult result,ModelMap model) {
			return "addStockGroup";
		}
		
		@RequestMapping(value="/addStockGroup",method=RequestMethod.POST)
		public String addStockGroupResult(@Valid @ModelAttribute("stockGroup") StockGroup group,BindingResult result,ModelMap model) {
			if (result.hasErrors()) {
				model.addAttribute("result", "<p class='mt-2 text-danger'>Search field is empty!</p>");
				return "addStockGroup";
			}
			if (groupRepo.existsByStockGroupName(group.getStockGroupName())) {
				throw new StockGroupAdditionException("Failed to add stock group!Stock group already exists!");
			} else {
				// Sets group names to be in the format "Capitalized Fully In A Sentence" 
				group.setStockGroupName(WordUtils.capitalizeFully(group.getStockGroupName()));
				groupRepo.save(group);
				model.addAttribute("stockGroup", new StockGroup());
				model.addAttribute("result", "<p class='mt-2 text-success'>Successfully added a new Stock Group!</p>");
			}
				return "addStockGroup";	
		}
		
 		
		@RequestMapping(value="/stockBrowse/all",method=RequestMethod.GET) 
		public String browseAllStock(ModelMap model) {
			// Get all Stocks from DB
			List<Stock> stocks = stockRepo.findAll();
			model.addAttribute("result", stocks);
			return "allStocksBrowse";
		}
		
		@RequestMapping(value="/stockBrowse/searchStockName", method=RequestMethod.GET)
		public String browseByName(ModelMap model) {
			return "stockSearchName";
		}
		
		@RequestMapping(value="/stockBrowse/searchStockName", method=RequestMethod.POST)
		public String browseByNameResult(@RequestParam("stockName") String stockName,ModelMap model) throws InterruptedException {
			List<Stock> ourStocks = null;
			if (stockName == "" || stockName == null) {
				return "stockSearchName";
			}
			try {
				ourStocks = stockSearchRepo.searchByStockName(stockName);
			} catch (EmptyQueryException e) {
				throw e;
			}
			model.addAttribute("result",ourStocks);
			return "stockSearchName";
		}
		
		
		
		@RequestMapping(value="/profile",method=RequestMethod.GET) 
		public String userProfile(Principal principal,ModelMap model) {
			// Get logged in user's username
			String username = principal.getName();
			// Search by username for a company
			Companies userCompany = companyRepo.findByEmail(username);
			// Get the user's posted stocks
			Set<Stock> stocks = userCompany.getStocks();
			// Add them to the model
			model.addAttribute("stocks", stocks);
			return "profile";
		}
		
		@RequestMapping(value="/deleteStock",method=RequestMethod.GET) 
		public String deleteStock(@RequestParam("sId") Long sId,Principal principal,ModelMap model) {
			// Find stock for deletion through sId and cId parameters
			// Check if stock is reserved
			if (stockRepo.getOne(sId).getReserveDate() != null && !LocalDateTime.now().isAfter(stockRepo.getOne(sId).getReserveDate())) {
					return "redirect:/profile";
			}
			// Deletes from companies_stock 
			stockRepo.removeById(sId);
			// Delete from stocks_all_groups
			groupRepo.removeStockById(sId);
			// Deletes from stock
			stockRepo.deleteById(sId);
			// Get logged in user's username
			String username = principal.getName();
			// Search by username for a company
			Companies userCompany = companyRepo.findByEmail(username);
			// Get the user's posted stocks
			Set<Stock> stocks = userCompany.getStocks();
			// Add them to the model
			model.addAttribute("stocks", stocks);
			return "redirect:/profile";
		}
		
		@RequestMapping(value="/resStocks",method=RequestMethod.GET) 
		public String viewResStocks(Principal principal,ModelMap model,HttpServletRequest request) throws NoSuchAlgorithmException,InvalidKeyException {
			// Get company by username
			String username = principal.getName();
			Companies userComp = companyRepo.findByEmail(username);
			// Get reserved stocks of the company
			Set<Stock> stocks = userComp.getResStocks();
			// For each stock in stocks --> retrieve data for the request and do the 
			// necessary transformations on the data
			for (Stock stock : stocks) {
				// Get the epay credentials for the company that offers the stocks
				Long offerCompany = stock.getResCompanies().iterator().next().getId();
				EpayCredentials epay = epayRepo.findByMerchantCompanyId(offerCompany);
				StringBuilder data = new StringBuilder();
		        data.append("MIN=").append(epay.getMerchantId()).append('\n');
		        data.append("INVOICE=").append((int)Math.floor(Math.random()*10000000)).append('\n');
		        data.append("AMOUNT=").append(stock.getFinalPrice()).append('\n');
		        data.append("CURRENCY=").append("BGN").append('\n');
		        data.append("EXP_TIME=").append(LocalDateTime.now().plus(15, ChronoUnit.MINUTES).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).append('\n');
		        data.append("DESCR=").append("sample transaction").append('\n');

		        String encoded = new String(Base64.getEncoder().encode(data.toString().getBytes()));
		        stock.setBase64Encoded(encoded);
		        String sha1HMAC = SHA1RFC2104HMAC.calculateRFC2104HMAC(encoded, epay.getMerchantSecret());
		        stock.setSha1HMAC(sha1HMAC);
			}
			// add them to model
			model.addAttribute("userComp", userComp);
			model.addAttribute("resStocks", stocks);
			return "reservedStocks";
		}
		
		@RequestMapping(value="/epayCredentials",method=RequestMethod.GET)
		public String epayCredentialsForm(ModelMap model,@ModelAttribute("epayCred") EpayCredentials epay,BindingResult result) {
			model.addAttribute("epayCred", new EpayCredentials());
			return "epayCredentials";
		}
		
		// Do POST method for /addEpayCredentials
		@RequestMapping(value="/addEpayCredentials",method=RequestMethod.POST)
		public String setEpayCredentials(ModelMap model,Principal principal,@ModelAttribute("epayCred") EpayCredentials epay,BindingResult result) {
			// Get the name of the logged-in user(principal)
			String username = principal.getName();
			// Search for a company with a username(e-mail) of the principal
			Companies userCompany = companyRepo.findByEmail(username);
			// Use the appropriate repository to store our ePay data
			// and link the logged-in user's company
			epay.setMerchantCompany(userCompany);
			epayRepo.save(epay);
			return "redirect:/profile";
		}
		
		@RequestMapping(value="/editStock",method=RequestMethod.GET) 
		public String editStock(@RequestParam("sId") Long sId,Principal principal,ModelMap model) {
			// Find stock for editing through sId parameter
			Stock editStock = stockRepo.getOne(sId);
			// Check if stock is reserved
			if (editStock.getReserveDate() != null && !LocalDateTime.now().isAfter(editStock.getReserveDate())) {
				return "redirect:/profile";
			}
			// Get all stock groups 
			List<StockGroup> groups = groupRepo.findAll();
			model.addAttribute("groupList", groups);
			model.addAttribute("stock", editStock);
			return "editStock";
		}
		
		@RequestMapping(value="/editStock",method=RequestMethod.POST) 
		public String editStockResult(@RequestParam("sId") Long sId,@ModelAttribute("file") MultipartFile file,@Valid @ModelAttribute("stock") Stock stock,Principal principal,ModelMap model) {
			Stock updateStock = stockRepo.getOne(sId);
			if (!file.isEmpty()) 
				updateStock.setThumbnail("filestorage/" + file.getOriginalFilename().toString());
			updateStock.setStockName(stock.getStockName());
			updateStock.setMeasuringUnits(stock.getMeasuringUnits());
			updateStock.setWeightOne(stock.getWeightOne());
			updateStock.setQuantity(stock.getQuantity());
			updateStock.setPricePerItem(stock.getPricePerItem());
			updateStock.setGroups(stock.getGroups());
			updateStock.setStockDesc(stock.getStockDesc());
			stockRepo.save(updateStock);
			return "redirect:/profile";
		}
		
		@RequestMapping(value="/viewArticle",method=RequestMethod.GET)
		public String viewArticle(@RequestParam("aId") Long articleId,@RequestParam("uId") Long userId,ModelMap model) {
			// Find poster of the article and send it to the view
			User poster = userRepo.getById(userId);
			// Find the article using the article's unique ID
			Article article = articleRepo.getById(articleId);
			// Format article post date and send it to the view
			FormatterDate formatter = new FormatterDate();
			String formattedDate = formatter.formatDate(article.getPostDate());
			// Add all the attributes to the model
			model.addAttribute("formattedDate", formattedDate);
			model.addAttribute("article",article);
			model.addAttribute("poster",poster);
			return "viewArticle";
		}
		
		@RequestMapping(value="/resHistory",method=RequestMethod.GET)
		public String viewResHistory(ModelMap model,Principal name) {
			// Get name of principal 
			String username = name.getName();
			// Search in Companies table
			Companies userCompany = companyRepo.findByEmail(username);
			// Save the user company's name in a String
			String companyName = userCompany.getCompanyName();
			// Search for reserved stocks by company name
			List<ReservationHistory> resHis = resHisRepo.findByCompanyName(companyName);
			// Add all reserved stocks to the view
			model.addAttribute("resHis", resHis);
			// Add company name to view
			model.addAttribute("compName", userCompany.getCompanyName());
			return "resHistory";
		}
		
		
		
		

}
