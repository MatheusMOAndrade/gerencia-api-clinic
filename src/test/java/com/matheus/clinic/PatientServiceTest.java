package com.matheus.clinic;

import com.matheus.clinic.model.Patient;
import com.matheus.clinic.repository.PatientRepository;
import com.matheus.clinic.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPatients() {

        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setNome("Anakin");
        patient1.setSobrenome("Skywalker");
        patient1.setSexo("M");
        patient1.setNascimento(LocalDate.of(1990, 1, 1));
        patient1.setAltura(1.80);
        patient1.setPeso(80.0);
        patient1.setCpf("12345678900");

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setNome("Padme");
        patient2.setSobrenome("Amidala");
        patient2.setSexo("F");
        patient2.setNascimento(LocalDate.of(1992, 2, 2));
        patient2.setAltura(1.70);
        patient2.setPeso(60.0);
        patient2.setCpf("98765432100");

        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void testGetPatientById() {

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setNome("Anakin");
        patient.setSobrenome("Skywalker");
        patient.setSexo("M");
        patient.setNascimento(LocalDate.of(1990, 1, 1));
        patient.setAltura(1.80);
        patient.setPeso(80.0);
        patient.setCpf("12345678900");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.getPatientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Anakin", result.get().getNome());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddPatient() {

        Patient patient = new Patient();
        patient.setNome("Anakin");
        patient.setSobrenome("Skywalker");
        patient.setSexo("M");
        patient.setNascimento(LocalDate.of(1990, 1, 1));
        patient.setAltura(1.80);
        patient.setPeso(80.0);
        patient.setCpf("12345678900");

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        Patient result = patientService.addPatient(patient);

        assertNotNull(result);
        assertEquals("Anakin", result.getNome());
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void testUpdatePatient() {
        Patient existingPatient = new Patient();
        existingPatient.setId(1L);
        existingPatient.setNome("Anakin");
        existingPatient.setSobrenome("Skywalker");
        existingPatient.setSexo("M");
        existingPatient.setNascimento(LocalDate.of(1990, 1, 1));
        existingPatient.setAltura(1.80);
        existingPatient.setPeso(80.0);
        existingPatient.setCpf("12345678900");

        Patient updatedPatient = new Patient();
        updatedPatient.setNome("Ben");
        updatedPatient.setSobrenome("Kenobi");
        updatedPatient.setSexo("M");
        updatedPatient.setNascimento(LocalDate.of(1990, 1, 1));
        updatedPatient.setAltura(1.80);
        updatedPatient.setPeso(80.0);
        updatedPatient.setCpf("12345678900");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient result = patientService.updatePatient(1L, updatedPatient);

        assertNotNull(result);
        assertEquals("Ben", result.getNome());
        assertEquals("Kenobi", result.getSobrenome());
        verify(patientRepository, times(1)).findById(1L);
        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    public void testDeletePatient() {

        Long patientId = 1L;

        patientService.deletePatient(patientId);

        verify(patientRepository, times(1)).deleteById(patientId);
    }
}
