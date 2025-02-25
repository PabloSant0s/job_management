package br.com.rocketseat.job_management.modules.company.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "tb_job")
public class JobEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "level é obrigatório")
  private String level;
  private String benefits;
  private String description;

  @ManyToOne()
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private CompanyEntity company;

  @NotNull(message = "Campo obrigatório")
  @Column(name = "company_id")
  private UUID companyId;

  @CreationTimestamp
  @Column(name = "created_at")
  private Instant createdAt;
}
