package com.example.jbfinal.controller;

import com.example.jbfinal.dataservice.ConvictService;
import com.example.jbfinal.dataservice.CriminalOrganizationService;
import com.example.jbfinal.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class MainController {

    private final ConvictService convictService;
    private final CriminalOrganizationService criminalOrganizationService;

    private final HairColorRepository hairColorRepository;
    private final EyeColorRepository eyeColorRepository;
    private final CountryRepository countryRepository;
    private final LanguageRepository languageRepository;

    @GetMapping("/search-convicts")
    public String searchConvicts(Model model) {

        model.addAttribute("hairColorList", hairColorRepository.findAll());
        model.addAttribute("eyeColorList", eyeColorRepository.findAll());
        model.addAttribute("countryList", countryRepository.findAll());
        model.addAttribute("languageList", languageRepository.findAll());
        model.addAttribute("criminalOrganizationList",
                criminalOrganizationService.getAllCriminalOrganizations());

        return "search_convicts";
    }

    @PostMapping("/search-convicts")
    public String searchConvicts(@RequestBody String body, Model model) {
        System.out.println(body);

        model.addAttribute("convicts", convictService.getConvicts(body));

        model.addAttribute("hairColorList", hairColorRepository.findAll());
        model.addAttribute("eyeColorList", eyeColorRepository.findAll());
        model.addAttribute("countryList", countryRepository.findAll());
        model.addAttribute("languageList", languageRepository.findAll());
        model.addAttribute("criminalOrganizationList",
                criminalOrganizationService.getAllCriminalOrganizations());

        System.out.println(model.asMap());

        return "search_convicts";
    }

    @GetMapping("/convict")
    public String convict(@RequestParam("id") Long id, Model model) {

        System.out.println(id);

        model.addAttribute("convict", convictService.getConvictById(id));

        return "convict";
    }

    @GetMapping("/search-criminal-organizations")
    public String searchCriminalOrganizations() {

        return "search_criminal_organizations";
    }

    @PostMapping("/search-criminal-organizations")
    public String searchCriminalOrganizations(@RequestBody String body, Model model) {

        model.addAttribute("criminalOrganizations",
                criminalOrganizationService.getCriminalOrganizations(body));

        return "search_criminal_organizations";
    }

    @GetMapping("/criminal-organization")
    public String criminalOrganization(@RequestParam("id") Long id, Model model) {

        System.out.println(id);

        model.addAttribute("criminalOrg", criminalOrganizationService.getCriminalOrganizationById(id));

        System.out.println(model.asMap());

        return "criminal_organization";
    }

}
