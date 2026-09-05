package com.salon.backend.Services.Auth;

import com.salon.backend.DTOs.ApiResponse;
import com.salon.backend.DTOs.Auth.Login.LoginRequest;
import com.salon.backend.DTOs.Auth.Login.LoginResponse;
import com.salon.backend.DTOs.Auth.Rigester.RigesterRequest;
import com.salon.backend.DTOs.Auth.Rigester.RigesterResponse;
import com.salon.backend.Entities.users.User;
import com.salon.backend.Entities.users.UserRole;
import com.salon.backend.Entities.users.UserStatus;
import com.salon.backend.Repositories.User.UserRepo;
import com.salon.backend.Services.Auth.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final List<String> ALLOWED_EMAIL_DOMAINS = List.of(
            "gmail.com",
            "hotmail.com",
            "outlook.com",
            "yahoo.com",
            "icloud.com",
            "jomap.com"
    );
    @Override
    public ApiResponse<RigesterResponse> Rigester(RigesterRequest request) {
        if(request.getFirstName()==null || request.getLastName()==null){
            return ApiResponse.error("First Name or Last Name can not be Null , Please fill them .");
        }
        if(request.getFirstName().length()<3 ||  request.getLastName().length()<3){
            return ApiResponse.error("First Name or Last Name can not be Less Than 3 Characters , Please fill them .");
        }
        if(request.getUserName()==null || !request.getUserName().matches("^[a-z](?!(?:.*_){2})(?!(?:.*\\.){2})[a-z0-9_.]*$")){
            return ApiResponse.error("User Name can not be null , or contain spaces or - .");
        }
        String normalizedEmail=request.getEmail().toLowerCase(Locale.ROOT);
        if( !isAllowedEmailDomain(normalizedEmail)){
            return ApiResponse.error("Email Address can not be Null , and must be with valid domain .");
        }
        if (request.getPhoneNumber() == null ||
                (!request.getPhoneNumber().matches("^\\+9627\\d{8}$")
                        && !request.getPhoneNumber().matches("^07\\d{8}$"))) {
            return ApiResponse.error("Phone number must be like +9627XXXXXXXX or 07XXXXXXXX");
        }
        if(request.getPassword()==null
                ||
                !request.getPassword().matches("^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$")){

            return ApiResponse.error("The password can not be null or less than 8 chars , must be contain one capital letter , one symbol , one number .");
        }
        if(userRepo.existsByemail(normalizedEmail)){
            return ApiResponse.error("Email Address already exists .");
        }
        if(userRepo.existsByphoneNumber(request.getPhoneNumber())){
            return ApiResponse.error("Phone Number already exists .");
        }
        if(userRepo.existsByuserName(request.getUserName())){
            return ApiResponse.error("Username already exists .");
        }
        User newUser = new User();
        newUser.setEmail(normalizedEmail);
        newUser.setUserName(request.getUserName().toLowerCase(Locale.ROOT));
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole(UserRole.USER);
        newUser.setStatus(UserStatus.Active);
        userRepo.save(newUser);

        RigesterResponse response = new RigesterResponse();
        response.setFirstName(request.getFirstName());
        response.setLastName(request.getLastName());
        response.setEmail(normalizedEmail);
        response.setUserName(request.getUserName());
        response.setId(newUser.getId());
        response.setPhoneNumber(request.getPhoneNumber());


        return ApiResponse.success("Rigestered Successfully , Go to login ." , response);
    }

    @Override
    public ApiResponse<LoginResponse> Login(LoginRequest request) {
        String normalizedEmail=request.getEmail().toLowerCase(Locale.ROOT);
        User user=userRepo.findByemail(normalizedEmail);
        if(user==null){
            return ApiResponse.error("User with this email dose not exist .");
        }
        User saveduser=user;
        if(!bCryptPasswordEncoder.matches(request.getPassword(),saveduser.getPassword())){
            return ApiResponse.error("Incorrect Password . Please Try Again .");
        }
        String token=jwtService.generateToken(normalizedEmail);
        LoginResponse response=new LoginResponse(
                saveduser.getId(),
                saveduser.getUserName(),
                token,
                "Bearer",
                saveduser.getRole(),
                normalizedEmail

        );


        return ApiResponse.success("Loged Successfully , Loading ...  ." , response);
    }

    @Override
    public ApiResponse<ResponseEntity> Logout() {
        return null;
    }

    private boolean isAllowedEmailDomain(String email) {

        int atIndex = email.lastIndexOf("@");

        if (atIndex == -1 || atIndex == email.length() - 1) {
            return false;
        }

        String domain = email.substring(atIndex + 1).toLowerCase(Locale.ROOT);

        return ALLOWED_EMAIL_DOMAINS.contains(domain);
    }



}
