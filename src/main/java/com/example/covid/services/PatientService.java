package com.example.covid.services;

import com.example.covid.entity.Patient;
import com.example.covid.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.covid.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;


    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

//    method for counting the percentage of mortality for each age group
    public List<Statistics> countPercentage() {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();

        List<Patient> babies = patients.stream()
                .filter(patient -> diffInYears(patient.getBirth()) <= 2)
                .collect(Collectors.toList());
        float deadBabies = babies.stream()
                .filter(baby -> baby.getStatus().equals("dead"))
                .count();
        float percentDeadBabies = babies.isEmpty() ? 0 : Math.round((deadBabies / babies.size()) * 100);
        Statistics babyStatistic = new Statistics("Babies", percentDeadBabies);

        List<Patient> children = patients.stream()
                .filter(patient -> diffInYears(patient.getBirth()) >= 3 && diffInYears(patient.getBirth()) <= 18)
                .collect(Collectors.toList());
        float deadChildren = children.stream()
                .filter(child -> child.getStatus().equals("dead"))
                .count();
        float percentDeadChildren = children.isEmpty() ? 0 : Math.round((deadChildren / children.size()) * 100);
        Statistics childrenStatistic = new Statistics("Children", percentDeadChildren);

        List<Patient> adults = patients.stream()
                .filter(patient -> diffInYears(patient.getBirth()) >= 19 && diffInYears(patient.getBirth()) <= 55)
                .collect(Collectors.toList());
        float deadAdults = adults.stream()
                .filter(adult -> adult.getStatus().equals("dead"))
                .count();
        float percentDeadAdults = adults.isEmpty() ? 0 : Math.round((deadAdults / adults.size()) * 100);
        Statistics adultsStatistic = new Statistics("Adults", percentDeadAdults);

        List<Patient> oldAdults = patients.stream()
                .filter(patient -> diffInYears(patient.getBirth()) >= 56)
                .collect(Collectors.toList());
        float deadOldAdults = oldAdults.stream()
                .filter(adult -> adult.getStatus().equals("dead"))
                .count();
        float percentDeadOlddults = oldAdults.isEmpty() ? 0 : Math.round((deadOldAdults / oldAdults.size()) * 100);
        Statistics oldAdultsStatistic = new Statistics("Old adults", percentDeadOlddults);

        return Arrays.asList(babyStatistic, childrenStatistic, adultsStatistic, oldAdultsStatistic);

    }

//    method for counting the age of a patient
    private long diffInYears(Date birthDate) {
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nowDate = LocalDate.now();
        return birthLocalDate.until(nowDate, ChronoUnit.YEARS);
    }

}
