package br.com.rocketseat.job_management.modules.company.controller;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.rocketseat.job_management.modules.company.dto.CreateJobDTO;
import br.com.rocketseat.job_management.modules.company.entities.CompanyEntity;
import br.com.rocketseat.job_management.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.job_management.utils.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void should_be_able_to_create_a_new_job() throws Exception {
    var company = CompanyEntity.builder()
    .name("COMPANY_TEST")
    .username("COMPANYTEST")
    .email("COMPANY@TEST.COM")
    .password("12345678910")
    .description("COMPANY_DESCRIPTION")
    .build();

    company = companyRepository.saveAndFlush(company);

    var createJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .level("LEVELS_TEST")
        .description("DESCRIPTION_TEST")
        .build();

    var result = mvc.perform(MockMvcRequestBuilders.post("/company/job")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", TestUtil.generateToken(company.getId(), "JAVA_@123#"))
        .content(TestUtil.objectToJson(createJobDTO))).andExpect(MockMvcResultMatchers.status().isNoContent());

    System.out.println(result);
  }
}
