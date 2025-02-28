package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Meeting;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/api/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    ParticipantService participantService;
    
 // Pobieranie listy spotkań
    // GET http://localhost:8080/api/meetings
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {

        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
    }
    
 // Dodawanie spotkań
 	// POST http://localhost:8080/api/meetings + JSON
 	@RequestMapping(value = "", method = RequestMethod.POST) 
 	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting){
 		if (meetingService.findById(meeting.getId()) != null){
 			return new ResponseEntity<String>("Unable to create. Meeting with id '" + meeting.getId() + "' already exists", HttpStatus.CONFLICT);
 		}
 		meetingService.add(meeting);
 		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
 	}

 	// Pobieranie pojedynczego spotkania
 	// GET http://localhost:8080/api/meetings/2
 	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
 	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
 	    Meeting meeting = meetingService.findById(id);
 		if (meeting == null) { 
 			return new ResponseEntity(HttpStatus.NOT_FOUND);
 		} 
 		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK); 
 	}

}
