package com.matheus.clinic.service;

import com.matheus.clinic.model.Patient;
import com.matheus.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient addPatient(Patient patient) {
        calculatePatientAttributes(patient);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Optional<Patient> existingPatientOpt = patientRepository.findById(id);
        if (existingPatientOpt.isPresent()) {
            Patient existingPatient = existingPatientOpt.get();
            existingPatient.setNome(updatedPatient.getNome());
            existingPatient.setSobrenome(updatedPatient.getSobrenome());
            existingPatient.setSexo(updatedPatient.getSexo());
            existingPatient.setNascimento(updatedPatient.getNascimento());
            existingPatient.setAltura(updatedPatient.getAltura());
            existingPatient.setPeso(updatedPatient.getPeso());
            existingPatient.setCpf(updatedPatient.getCpf());
            calculatePatientAttributes(existingPatient);
            return patientRepository.save(existingPatient);
        } else {
            throw new RuntimeException("Patient not found");
        }
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    private void calculatePatientAttributes(Patient patient) {
        patient.setIdade(calculateAge(patient.getNascimento()));
        patient.setImc(calculateIMC(patient.getPeso(), patient.getAltura()));
    }

    private double calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private double calculateIMC(double weight, double height) {
        return weight / (height * height);
    }

    public String obterSituacaoIMC(double imc) {
        if (imc < 17) {
            return "Muito abaixo do peso";
        } else if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Acima do peso";
        } else if (imc < 35) {
            return "Obesidade I";
        } else if (imc < 40) {
            return "Obesidade II (severa)";
        } else {
            return "Obesidade III (mórbida)";
        }
    }

    public String obterCpfOfuscado(String cpf) {
        return "***." + cpf.substring(4, 7) + ".***-**";
    }

    public boolean validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            int v1 = 0, v2 = 0;
            for (int i = 0; i < 9; i++) {
                v1 += digits[i] * (10 - i);
                v2 += digits[i] * (11 - i);
            }
            v1 = (v1 % 11) < 2 ? 0 : 11 - (v1 % 11);
            v2 += v1 * 2;
            v2 = (v2 % 11) < 2 ? 0 : 11 - (v2 % 11);

            return digits[9] == v1 && digits[10] == v2;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
