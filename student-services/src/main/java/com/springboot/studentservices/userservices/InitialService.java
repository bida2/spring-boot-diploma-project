/**
 * 
 */
package com.springboot.studentservices.userservices;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.studentservices.entities.Article;
import com.springboot.studentservices.entities.Companies;
import com.springboot.studentservices.entities.Role;
import com.springboot.studentservices.entities.Stock;
import com.springboot.studentservices.entities.StockGroup;
import com.springboot.studentservices.entities.User;
import com.springboot.studentservices.repositories.ArticleRepository;
import com.springboot.studentservices.repositories.CompaniesRepository;
import com.springboot.studentservices.repositories.EpayCredentialsRepository;
import com.springboot.studentservices.repositories.ResHistoryRepository;
import com.springboot.studentservices.repositories.RoleRepository;
import com.springboot.studentservices.repositories.StockGroupRepository;
import com.springboot.studentservices.repositories.StockRepository;
import com.springboot.studentservices.repositories.UserRepository;


/**
 * @author my
 *
 */
@Service
public class InitialService {

	
	@Autowired
	private CompaniesRepository companyRepo;
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private FileStorageImpl fileStorage;
	
	@Autowired 
	private StockGroupRepository groupRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Autowired
	private ResHistoryRepository resHisRepo;
	
	@Autowired
	private EpayCredentialsRepository epayRepo;
	
	// Not @Autowired variables
	@PostConstruct
	public void setup() {
		// Password encoder instance
		final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// Cleanup the tables
		// Going into production, all of the deleteAll() lines must
		// be removed or they will be deleting all data
		articleRepo.deleteAllInBatch();
        companyRepo.deleteAllInBatch();
        stockRepo.deleteAllInBatch();
        roleRepo.deleteAllInBatch();
        userRepo.deleteAllInBatch();
        roleRepo.deleteAllInBatch();
        groupRepo.deleteAllInBatch();
        resHisRepo.deleteAllInBatch();
        epayRepo.deleteAllInBatch();
        
        
        // Delete the folder "filestorage" and all data in it
        // and recreate it afterwards through init
        // Going into production, these methods should be removed
        fileStorage.deleteAll();
        fileStorage.init();
       
        
        

      
      
        
        // ==========User Data Created Below============================
        
        
        // Create several stock groups for our stocks
        StockGroup groupOne = new StockGroup("Health and Hygiene");
        StockGroup groupTwo = new StockGroup("Tools");
        StockGroup groupThree = new StockGroup("Consumables");
        
        groupRepo.save(groupOne);
        groupRepo.save(groupTwo);
        groupRepo.save(groupThree);
        
        // Create an admin User instance
        User adminUser = new User();
        // Create a normal User instance
        User normalUser = new User();
        
        // Create a Set<Role> and a role for the user
        Set<Role> adminRoles = new HashSet<>();
        // Create a Set<Role> for the normal user
        Set<Role> userRoles = new HashSet<>();
        
        // Create a new user role
        // This initial account in the system will be an ADMIN account
        // It will have 2 roles --> USER and ADMIN
        Role roleOne = new Role();
        Role roleTwo = new Role();
        roleOne.setName("USER");
        roleTwo.setName("ADMIN");
        // Create Roles for the normal user
        Role roleThree = new Role();
        roleThree.setName("USER");
        
        // Add both admin roles to the Set<Role>
        adminRoles.add(roleOne);
        adminRoles.add(roleTwo);
        
        // Add user roles to the right Set<Role> structure
        userRoles.add(roleThree);
        
        // Encode our password
        // A better idea is to generate the password through a library and 
        // print it in the console for the admin
        // Rework it into that when possible
        String encPass = encoder.encode("h545G1212");

        // Create a Company --> testing admin / user roles
        Companies adminCompany = new Companies("The Wood Shavings Company",
                "BG110945830",
                "Bulgaria,Ruse,Vetovo,Zahari Stoqnov 8","0886445573","sakito1234@abv.bg",encPass);
        Companies userCompany = new Companies("The Forever Evil Company",
                "BG125678912",
                "Bulgaria,Ruse,Vetovo,Zahari Stoqnov 9","0886445587","keloman@abv.bg",encPass);
       
        
        // Create stocks for both our users
        Stock stock1 = new Stock("Head&Shoulders Shampoo","Anti-dandruff shampoo","KG",100, 45,(float) 4.5, "filestorage/headandshoulders.png");
        Stock stock2 = new Stock("Lacalut","Anti-bleeding toothpaste,designed to strengthen your teeth!","KG",(float) 4.5, 45, (float) 3.70,"filestorage/lacalut.png");

        Stock stock3 = new Stock("Beer Pirinsko","An alcoholic beverage from the Pirin mountain!","KG",100, 45,(float) 4.5, "filestorage/headandshoulders.png");
        Stock stock4 = new Stock("Smoked Sausage Naroden","A delightful smoked sausage, made with love and care!","KG",100, 45,(float) 4.5, "filestorage/headandshoulders.png");
       // Add stock groups to stocks
        stock1.getGroups().add(groupOne);
        stock2.getGroups().add(groupThree);
        stock3.getGroups().add(groupTwo);
        stock4.getGroups().add(groupTwo);
        
        // Calculate final price for stocks
        stock1.setFinalPrice(stock1.getQuantity() * stock1.getPricePerItem());
        stock2.setFinalPrice(stock2.getQuantity() * stock2.getPricePerItem());
        stock3.setFinalPrice(stock3.getQuantity() * stock3.getPricePerItem());
        stock4.setFinalPrice(stock4.getQuantity() * stock4.getPricePerItem());
        // Add stock references in the post
        adminCompany.getStocks().add(stock1);
        adminCompany.getStocks().add(stock2);
        userCompany.getStocks().add(stock3);
        userCompany.getStocks().add(stock4);

        // Add company reference in the stocks
        stock1.getCompanies().add(adminCompany);
        stock2.getCompanies().add(adminCompany);
        stock3.getCompanies().add(userCompany);
        stock4.getCompanies().add(userCompany);
        
        // Reference our user in the Companies table
        adminCompany.setUser(adminUser);
        userCompany.setUser(normalUser);
        
        
        
        // Add company data to Users table
        adminUser.setUsername(adminCompany.getEmail());
        normalUser.setUsername(userCompany.getEmail());
        adminUser.setPassword(encPass);
        normalUser.setPassword(encPass);
        adminUser.setRoles(adminRoles);
        normalUser.setRoles(userRoles);
        userRepo.save(adminUser);
        userRepo.save(normalUser);
        roleRepo.saveAll(adminRoles);
        roleRepo.saveAll(userRoles);
        
      
        
        // Save the user data to the Company table
        companyRepo.save(adminCompany);
        companyRepo.save(userCompany);

        // =======================================
        
        
     // ==========Article Data Created Below=========================
        // Get current date from Calendar
        Date currDate = Calendar.getInstance().getTime();
        // Convert into LocalDate format
        LocalDate date = currDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // Create several articles - article text must be at least 50 characters
        Article art1 = new Article("Crimes Against Humanity: The Struggle for Global Justice",
       "Crimes Against Humanity: The Struggle for Global Justice (\"CAH\") is a political,"
       + " moral, legal and polemical work: as such, it is a comprehensive attempt to address the evils that, "
       + "in the broadest sense of the concept, constitute crimes against humanity. The book is wide ranging, illuminating and entertaining. "
       + "It is written in a lively and accessible style. Robertson is not afraid to make his position clear, often with a hard-hitting adjective or "
       + "caustic aside, which gives the work a refreshing honesty that a more measured or academic approach might avoid. "
       + "The moral, though not economic or pragmatic, case Robertson makes for the universal, coherent, consistent and systematic protection of human rights across the globe is compelling, and the broad-brush strokes of his argument, "
       + "and much of its detail, is hard to disagree with. Generally speaking, I am therefore in agreement with Robertson’s objectives as set out in CAH, and the methodology he wishes to see used in order to achieve those objectives. There is much to praise in CAH, "
       + "and it is a valuable contribution to the global promotion and protection of human rights and humanitarian values.",date,adminUser);
      
        // Save our articles using the JPA Repository
        articleRepo.save(art1); 
	}

}
