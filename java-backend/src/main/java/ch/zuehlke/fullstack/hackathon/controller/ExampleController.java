package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.fullstack.hackathon.model.ExampleDto;
import ch.zuehlke.fullstack.hackathon.model.MessageOfTheDayDto;
import ch.zuehlke.fullstack.hackathon.service.ExampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/example")
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final ExampleService exampleService;

    @Operation(summary = "Example demo DTO",
            description = "This can be used to enrich swagger documentation")
    @ApiResponse(responseCode = "200", description = "Successfully returned example")
    @ApiResponse(responseCode = "500", description = "Something failed internally")
    @GetMapping("/")
    public ResponseEntity<ExampleDto> getExample() {
        ExampleDto result;
        try {
            result = this.exampleService.getExampleDto();
        } catch (Exception exception) {
            log.error("Example could not be fetched", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Example message of the day DTO",
            description = "This can be used to enrich swagger documentation")
    @ApiResponse(responseCode = "200", description = "Successfully returned example")
    @ApiResponse(responseCode = "500", description = "Something failed internally")
    @GetMapping("/motd")
    public ResponseEntity<MessageOfTheDayDto> getMessageOfTheDayExample() {
        MessageOfTheDayDto result;
        try {
            result = this.exampleService.getMessageOfTheDayDto();
        } catch (Exception exception) {
            log.error("Message of the day could not be fetched", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
