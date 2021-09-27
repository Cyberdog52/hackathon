package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.fullstack.hackathon.model.UserInfo;
import ch.zuehlke.fullstack.hackathon.service.InsightClient;
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

    private final InsightClient insightClient;

    @Autowired
    public UserController(InsightClient insightClient) {
        this.insightClient = insightClient;
    }
    
    @GetMapping("/search")
    public ResponseEntity<UserInfo> getExample(@RequestParam String term) {
        UserInfo result;
        try {
            List<UserInfo> response = this.insightClient.getEmployees(term);
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