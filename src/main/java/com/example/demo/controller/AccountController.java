package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.config.MyUserDetails;
import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterDTO;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Controller
@RequestMapping("account")
public class AccountController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private MyUserDetails myUserDetails;

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "account/login";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDTO) throws Exception {

		authenticate(loginDTO.getEmail(), loginDTO.getPassword());

		final UserDetails userDetails = myUserDetails
				.loadUserByUsername(loginDTO.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new LoginResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

    // @PostMapping("login/authenticate")
    // public String loginAuth(LoginDTO loginDTO) {
    //     // User user = userRepository.login(loginDTO.getEmail(), loginDTO.getPassword());
    //     // if(user.getEmployee().getEmail().equals(loginDTO.getEmail())) {
    //     //     return "account/home";
    //     // }
    //     // return "account/login";

    //     Authentication authentication = authenticationManager
    //         .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
    //     SecurityContextHolder.getContext().setAuthentication(authentication);
    //     // if(authentication.isAuthenticated()){
    //         return "account/home";
    //     // }
    //     // return "account/login";

    // }

    

    // @PostMapping("login/authenticate")
    // public String loginAuth(LoginDTO loginDTO) {
    //     List<Employee> employees = new ArrayList<Employee>();
    //     employees = employeeRepository.findAll();

    //     Employee findEMP = employees.stream().filter(p -> p.getEmail().equals(loginDTO.getEmail())).findFirst().orElse(null);

    //     Boolean isFound = employeeRepository.findById(findEMP.getId()).isPresent();

    //     if(isFound) {
    //         User user = userRepository.findById(findEMP.getId()).orElse(null);
    //         if (user.getPassword().equals(loginDTO.getPassword())) {
    //             return "account/home";
    //         }
    //     }

    //     return "account/login";

    // }


    //register

    // @GetMapping("register")
    // public String form(Model model){
    //     User user = new User();
    //     Employee employee = new Employee();
    //     Role role = new Role();

    //     role.setId(3);

    //     user.setPassword(passwordEncoder.encode("defaultpassword"));
    //     user.setRole(role);

    //     employee.setEmail("alshanabilla08@gmail.com");
    //     employee.setName("alsha nabilla");
    //     employee.setPhone("08754323456");
    //     employee.setAddress("bekasi");

    //     employeeRepository.save(employee);
        
    //     user.setId(employee.getId());

    //     return "account/login";
    // }
    @GetMapping("register")
    public String register(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        model.addAttribute("roles", roleRepository.findAll());
        return "account/register";
    }

    @PostMapping("register/save")
    public String saveRegister(RegisterDTO registerDTO) {
        Employee emp = new Employee();

        emp.setName(registerDTO.getName());
        emp.setEmail(registerDTO.getEmail());
        emp.setPhone(registerDTO.getPhone());
        emp.setAddress(registerDTO.getAddress());

        employeeRepository.save(emp);
        Boolean resultemployee = employeeRepository.findById(emp.getId()).isPresent();
        if(resultemployee){
            User user = new User();
            user.setId(emp.getId());
            // user.setPassword(registerDTO.getPassword());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            user.setRole(registerDTO.getRole());

            userRepository.save(user);
            Boolean resultuser = userRepository.findById(user.getId()).isPresent();
            if(resultuser){
                return "redirect:/account/login";
            }
        }
        return "account/register";
    }

    //change password
    @GetMapping("change")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordDTO());

        return "account/changepassword";

    }

    @PostMapping("savechange")
    public String saveChange(ChangePasswordDTO changePasswordDTO) {
        List<Employee> employees = new ArrayList<Employee>();
        employees = employeeRepository.findAll();

        Employee findEMP = employees.stream().filter(p -> p.getEmail().equals(changePasswordDTO.getEmail())).findFirst().orElse(null);
        Boolean employeeExist = employeeRepository.findById(findEMP.getId()).isPresent();

        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();

        if(employeeExist){
            User findUser = users.stream().filter(p -> p.getPassword().equals(changePasswordDTO.getCurrentPassword())).findFirst().orElse(null);
            Boolean isTrue = userRepository.findById(findUser.getId()).isPresent();

            if(isTrue) {
                findUser.setPassword(changePasswordDTO.getNewPassword());
                userRepository.save(findUser);
            }
            return "account/home";

        }
        return "account/changepassword";
    }

    //forgot password
    @GetMapping("forgot")
    public String forgot(Model model) {
        // model.addAttribute("forgotPasswordDTO", new ForgotPasswordDTO());
        model.addAttribute("employee", new Employee());
        model.addAttribute("user", new User());

        return "account/forgotpassword";
    }

    @PostMapping("saveforgot")
    public String saveForgot(Employee employee, User user) throws AddressException, MessagingException {
        List<Employee> employees = new ArrayList<Employee>();
        employees = employeeRepository.findAll();

        Employee findEMP = employees.stream().filter(p -> p.getEmail().equals(employee.getEmail())).findFirst().orElse(null);
        Boolean employeeExist = employeeRepository.findById(findEMP.getId()).isPresent();

        List<User> users = new ArrayList<User>();
        users = userRepository.findAll();

        if(employeeExist){
            User findUser = users.stream().filter(p -> p.getId().equals(findEMP.getId())).findFirst().orElse(null);
            Boolean isTrue = userRepository.findById(findUser.getId()).isPresent();

            if(isTrue) {
                findUser.setPassword("defaultpassword");
                userRepository.save(findUser);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
                LocalDateTime now = LocalDateTime.now(); 
                // emailService.sendEmail(employee.getEmail(), "Reset Password", "new password 'defaultpassword'");
                MimeMessage message = mailSender.createMimeMessage();
                message.setFrom(new InternetAddress("alshanabilla08@gmail.com"));
                message.setRecipients(MimeMessage.RecipientType.TO, employee.getEmail());
                message.setSubject("[" + dtf.format(now) + "]" +" Reset Password");

                // String htmlTemplate = readFile("Email.html");

                String htmlContent = "<h1>This is a test Spring Boot email</h1>" +
                                    "new password 'defaultpassword'";
                message.setContent(htmlContent, "text/html; charset=utf-8");

                mailSender.send(message);
            }
            return "redirect:/account/login";

        }
        return "account/forgotpassword";

    }

    // @PostMapping("saveforgot")
    // public String saveForgot(ForgotPasswordDTO forgotPasswordDTO) {
    //     List<Employee> employees = new ArrayList<Employee>();
    //     employees = employeeRepository.findAll();

    //     Employee findEMP = employees.stream().filter(p -> p.getEmail().equals(forgotPasswordDTO.getEmail())).findFirst().orElse(null);
    //     Boolean employeeExist = employeeRepository.findById(findEMP.getId()).isPresent();

    //     List<User> users = new ArrayList<User>();
    //     users = userRepository.findAll();

    //     if(employeeExist){
    //         User findUser = users.stream().filter(p -> p.getId().equals(findEMP.getId())).findFirst().orElse(null);
    //         Boolean isTrue = userRepository.findById(findUser.getId()).isPresent();

    //         if(isTrue) {
    //             findUser.setPassword(forgotPasswordDTO.getNewPassword());
    //             userRepository.save(findUser);

    //             findUser.setResetToken(UUID.randomUUID().toString());
    //         }
    //         return "account/home";

    //     }
    //     return "account/forgotpassword";

    // }

}
