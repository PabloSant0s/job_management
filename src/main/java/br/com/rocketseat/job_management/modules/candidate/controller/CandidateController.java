package br.com.rocketseat.job_management.modules.candidate.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rocketseat.job_management.dto.AuthResponseDTO;
import br.com.rocketseat.job_management.exceptions.CandidateNotFoundException;
import br.com.rocketseat.job_management.exceptions.UserFoundException;
import br.com.rocketseat.job_management.modules.candidate.CandidateEntity;
import br.com.rocketseat.job_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.rocketseat.job_management.modules.candidate.dto.ProfileCandidateDTO;
import br.com.rocketseat.job_management.modules.candidate.useCase.AuthenticateCandidateUseCase;
import br.com.rocketseat.job_management.modules.candidate.useCase.CreateCandidateUseCase;
import br.com.rocketseat.job_management.modules.candidate.useCase.ListAllJobsByFilterUseCase;
import br.com.rocketseat.job_management.modules.candidate.useCase.ProfileCandidateUseCase;
import br.com.rocketseat.job_management.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {
  
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;;

  @Autowired 
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private AuthenticateCandidateUseCase authenticateCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase; 
  @PostMapping
  @Operation(summary = "Cadastro do candidato", description = "Essa função é responsável por cadastrar um novo candidato")
  @ApiResponses({
    @ApiResponse(responseCode = "201", content = {
      @Content(schema = @Schema(implementation = ProfileCandidateDTO.class))
    }),
    @ApiResponse(responseCode = "400", description = "Usuário já Existe")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
      try {
        return new ResponseEntity<>(this.createCandidateUseCase.execute(candidate),HttpStatus.CREATED);
      } catch (UserFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PreAuthorize("hasRole('CANDIDATE')")
  @GetMapping
  @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = ProfileCandidateDTO.class))
    }),
    @ApiResponse(responseCode = "400", description = "User not found")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
        UUID candidateId = UUID.fromString(request.getAttribute("candidate_id").toString());
        return ResponseEntity.ok().body(profileCandidateUseCase.execute(candidateId));
      } catch (CandidateNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
  }

  @PostMapping("/auth")
  @Operation(summary = "Autenticar Candidato", description = "Essa função é responsável por autenticar o candidato")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = AuthResponseDTO.class))
    }),
    @ApiResponse(responseCode = "401", description = "Username/password incorrect")
  })
  public ResponseEntity<Object> authCandidate (@RequestBody AuthCandidateDTO authCandidateDTO) {
      try {
        AuthResponseDTO result = authenticateCandidateUseCase.execute(authCandidateDTO);
        return ResponseEntity.ok().body(result);
      } catch (UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
      }
  } 

  @PreAuthorize("hasRole('CANDIDATE')")
  @GetMapping("/job/filter")
  @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
    })
  })
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> getMethodName(@RequestParam(required = false, defaultValue = "") String description) {
      return listAllJobsByFilterUseCase.execute(description);
  }
  
}
