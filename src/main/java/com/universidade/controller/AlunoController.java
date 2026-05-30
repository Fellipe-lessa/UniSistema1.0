package com.universidade.controller;

import com.universidade.dao.AlunoDAO;
import com.universidade.model.Aluno;
import com.universidade.model.Matricula;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoDAO alunoDAO = new AlunoDAO();

    // Lista todos os alunos
    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("alunos", alunoDAO.listarTodos());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao listar alunos: " + e.getMessage());
        }
        return "aluno/listar";
    }

    // Exibe formulário de cadastro
    @GetMapping("/novo")
    public String formulario() {
        return "aluno/form";
    }

    // Salva novo aluno
    @PostMapping("/salvar")
    public String salvar(@RequestParam String nome,
                         @RequestParam String endereco,
                         @RequestParam String matricula,
                         @RequestParam String dataIngresso,
                         RedirectAttributes attrs) {
        try {
            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setEndereco(endereco);
            aluno.setMatricula(matricula);
            aluno.setDataIngresso(LocalDate.parse(dataIngresso));
            alunoDAO.inserir(aluno);
            attrs.addFlashAttribute("sucesso", "Aluno cadastrado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao cadastrar aluno: " + e.getMessage());
        }
        return "redirect:/alunos";
    }

    // Busca aluno por matrícula e mostra suas matérias
    @GetMapping("/consultar")
    public String consultar(@RequestParam(required = false) String matricula, Model model) {
        if (matricula != null && !matricula.isBlank()) {
            try {
                Aluno aluno = alunoDAO.buscarPorMatricula(matricula);
                if (aluno != null) {
                    List<Matricula> materias = alunoDAO.buscarMateriasPorAluno(aluno.getId());
                    model.addAttribute("aluno", aluno);
                    model.addAttribute("materias", materias);
                } else {
                    model.addAttribute("erro", "Aluno não encontrado com a matrícula: " + matricula);
                }
            } catch (Exception e) {
                model.addAttribute("erro", "Erro na consulta: " + e.getMessage());
            }
        }
        return "aluno/consultar";
    }

    // Deleta aluno
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id, RedirectAttributes attrs) {
        try {
            alunoDAO.deletar(id);
            attrs.addFlashAttribute("sucesso", "Aluno removido com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao remover aluno: " + e.getMessage());
        }
        return "redirect:/alunos";
    }
}
