package com.example.covid.controllers;

import javax.validation.Valid;

import com.example.covid.entity.Patient;
import com.example.covid.repositories.PatientRepository;
import com.example.covid.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientRepository patientRepository, PatientService patientService) {
        this.patientRepository = patientRepository;
        this.patientService = patientService;
    }

    @GetMapping
    public String basePage(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "index";
    }

    @GetMapping("/statistics")
    public String statisticPage(Model model) {
        model.addAttribute("statistics", patientService.countPercentage());
        return "statistics";
    }


    @GetMapping("/new")
    public String showSignUpForm(Patient patient) {
        return "add-patient";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        return "update-patient";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        patientRepository.delete(patient);
        model.addAttribute("patients", patientRepository.findAll());
        return "redirect:/";
    }

    @PostMapping("/addpatient")
    public String addUser(@Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-patient";
        }
        patientRepository.save(patient);
        model.addAttribute("patients", patientRepository.findAll());

        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            patient.setId(id);
            return "update-patient";
        }

        patientRepository.save(patient);
        model.addAttribute("patients", patientRepository.findAll());
        return "redirect:/";
    }


}