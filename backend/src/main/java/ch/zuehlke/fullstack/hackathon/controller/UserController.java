package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.fullstack.hackathon.model.UserInfo;
import ch.zuehlke.fullstack.hackathon.service.EmployeeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final EmployeeClient employeeClient;

    @Autowired
    public UserController(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }
    
    @GetMapping("/search")
    public ResponseEntity<UserInfo> getExample(@RequestParam String term) {
        UserInfo result;
        try {
            List<UserInfo> response = this.employeeClient.getEmployees(term);
            if(response != null && response.size() > 0) {
                result = response.get(0);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

}