package com.example.imobiliaria.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
@Entity
@Table(name = "clientes")
public class Cliente{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @NotBlank(message = "O nome do cliente é obrigatório")
   @Column(name = "nome_cliente", nullable = false)
        private String nomeCliente;

    @NotBlank(message = "O CPF é obrigatório")
    @Column( nullable = false, length = 11)
        private String cpf;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(nullable = false, length = 12)
    private String telefone;

    @Email(message = "Formato de e-mail inválido")
    @Column (length = 100)
    private String email;

    @Column (name = "dt_nascimento")
    private LocalDate dtNascimento;

    public Cliente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
