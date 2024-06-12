package com.matheus.clinic;

import com.matheus.clinic.controller.PatientController;
import com.matheus.clinic.model.Patient;
import com.matheus.clinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private MockMvc mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();

    @Test
    public void testGetAllPatients() throws Exception {
        Patient patient1 = new Patient();
        patient1.setNome("Anakin");
        patient1.setSobrenome("Skywalker");
        patient1.setSexo("Masculino");
        patient1.setNascimento(LocalDate.of(1990, 3, 9));
        patient1.setAltura(1.80);
        patient1.setPeso(80.0);
        patient1.setCpf("12345678900");

        Patient patient2 = new Patient();
        patient2.setNome("Padme");
        patient2.setSobrenome("Amidala");
        patient2.setSexo("Feminino");
        patient2.setNascimento(LocalDate.of(1992, 2, 2));
        patient2.setAltura(1.70);
        patient2.setPeso(60.0);
        patient2.setCpf("98765432100");

        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient1, patient2));

        mockMvc.perform(get("/api/v1/patients/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
