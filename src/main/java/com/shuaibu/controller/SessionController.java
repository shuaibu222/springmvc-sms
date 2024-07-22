package com.shuaibu.controller;

import com.shuaibu.repository.SessionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SessionDto;
import com.shuaibu.model.SessionModel;
import com.shuaibu.service.SessionService;

import jakarta.validation.Valid;

import java.util.Arrays;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("/sessions")
@PreAuthorize("hasRole('ADMIN')")
public class SessionController {
    
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;

    public SessionController(SessionService sessionService, SessionRepository sessionRepository) {
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public String listSessions(Model model) {
        model.addAttribute("academicSession", new SessionModel());
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        model.addAttribute("sessions", sessionRepository.findAll());
        return "sessions/list";
    }

    @PostMapping
    public String saveListSession(@Valid @ModelAttribute("academicSession") SessionDto session, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            model.addAttribute("sessions", sessionRepository.findAll());
            return "sessions/list";
        }
        sessionService.saveOrUpdateSession(session);
        return "redirect:/sessions";
    }

    @GetMapping("/new")
    public String createSessionForm(Model model) {
        model.addAttribute("academicSession", new SessionModel());
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        return "sessions/new";
    }

    @PostMapping("/create")
    public String saveSession(@Valid @ModelAttribute("academicSession") SessionDto session, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            return "sessions/new";
        }
        sessionService.saveOrUpdateSession(session);
        return "redirect:/sessions";
    }

    @GetMapping("/edit/{id}")
    public String updateSessionForm(@PathVariable Long id, Model model) {
        SessionDto academicSession = sessionService.getSessionById(id);
        model.addAttribute("academicSession", academicSession);
        model.addAttribute("isActive", Arrays.asList("True", "False"));
        return "sessions/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSession(@PathVariable Long id,
                                @Valid @ModelAttribute("academicSession") SessionDto session,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isActive", Arrays.asList("True", "False"));
            return "sessions/edit";
        }
        session.setId(id);
        sessionService.saveOrUpdateSession(session);
        return "redirect:/sessions";
    }

    @GetMapping("/delete/{id}")
    public String deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return "redirect:/sessions";
    }
}
