package com.shuaibu.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.EventDto;
import com.shuaibu.model.EventModel;
import com.shuaibu.service.EventService;

import jakarta.validation.Valid;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/events")
@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('STUDENT')")
public class EventController {

    // private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String listEvent(Model model) {
        model.addAttribute("event", new EventModel());
        model.addAttribute("events", eventService.getAllEvent());
        return "events/list";
    }

    @GetMapping("/new")
    public String createEventForm(Model model) {
        model.addAttribute("event", new EventModel());
        return "events/new";
    }

    @PostMapping("/create")
    public String saveEvent(@Valid @ModelAttribute("event") EventDto event, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("event", event);
            return "events/new";
        }

        eventService.saveOrUpdateEvent(event);


        return "redirect:/events";
    }

    @GetMapping("/edit/{id}")
    public String updateEventForm(@PathVariable Long id, Model model) {
        EventDto event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "events/edit";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable Long id,
                                @Valid @ModelAttribute("event") EventDto eventDto, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            EventDto event = eventService.getEventById(id);
            model.addAttribute("event", event);
            return "events/edit";
        }

        eventDto.setId(id);
        eventService.saveOrUpdateEvent(eventDto);

        return "redirect:/events";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }
}
