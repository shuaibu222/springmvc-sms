package com.shuaibu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.shuaibu.dto.SportHouseDto;
import com.shuaibu.model.SportHouseModel;
import com.shuaibu.service.SportHouseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sportHouses")
public class SportHouseController {
    
    private SportHouseService sportHouseService;

    public SportHouseController(SportHouseService sportHouseService) {
        this.sportHouseService = sportHouseService;
    }

    @GetMapping
    public String listSportHouses(Model model) {
        model.addAttribute("sportHouse", new SportHouseModel());
        model.addAttribute("sportHouses", sportHouseService.getAllSportHouses());
        return "sportHouses/list";
    }

    @GetMapping("/new")
    public String createSportHouseForm(Model model) {
        model.addAttribute("sportHouse", new SportHouseModel());
        return "sportHouses/new";
    }

    @PostMapping("/create")
    public String saveSportHouse(@Valid @ModelAttribute("sportHouse") SportHouseDto sportHouse, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sportHouse", sportHouse);
            return "sportHouses/new";
        }
        sportHouseService.saveSportHouse(sportHouse);
        return "redirect:/sportHouses";
    }

    @GetMapping("/edit/{id}")
    public String updateSportHouseForm(@PathVariable Long id, Model model) {
        SportHouseDto sportHouse = sportHouseService.getSportHouseById(id);
        model.addAttribute("sportHouse", sportHouse);
        return "sportHouses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSportHouse(@PathVariable Long id,
                                @Valid @ModelAttribute("sportHouse") SportHouseDto sportHouse, 
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("sportHouse", sportHouse);
            return "sportHouses/edit";
        }
        sportHouse.setId(id);
        sportHouseService.updateSportHouse(sportHouse);
        return "redirect:/sportHouses";
    }

    @GetMapping("/delete/{id}")
    public String deleteSportHouse(@PathVariable Long id) {
        sportHouseService.deleteSportHouse(id);
        return "redirect:/sportHouses";
    }
}
