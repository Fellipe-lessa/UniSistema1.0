package com.universidade.controller;

import com.universidade.dao.AlunoDAO;
import com.universidade.dao.MateriaDAO;
import com.universidade.dao.TurmaDAO;
import com.universidade.model.Matricula;
import com.universidade.model.Turma;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final MateriaDAO materiaDAO = new MateriaDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    // Lista todas as turmas
    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("turmas", turmaDAO.listarTodos());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao listar turmas: " + e.getMessage());
        }
        return "turma/listar";
    }

    // Exibe formulário de cadastro
    @GetMapping("/novo")
    public String formulario(Model model) {
        try {
            model.addAttribute("materias", materiaDAO.listarTodos());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar matérias: " + e.getMessage());
        }
        return "turma/form";
    }

    // Salva nova turma
    @PostMapping("/salvar")
    public String salvar(@RequestParam String codigo,
                         @RequestParam String descricao,
                         @RequestParam Integer materiaId,
                         RedirectAttributes attrs) {
        try {
            Turma turma = new Turma();
            turma.setCodigo(codigo);
            turma.setDescricao(descricao);
            turma.setMateriaId(materiaId);
            turmaDAO.inserir(turma);
            attrs.addFlashAttribute("sucesso", "Turma cadastrada com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao cadastrar turma: " + e.getMessage());
        }
        return "redirect:/turmas";
    }

    // Consulta turma por código (mostra alunos, notas e aprovação)
    @GetMapping("/consultar")
    public String consultar(@RequestParam(required = false) String codigo, Model model) {
        if (codigo != null && !codigo.isBlank()) {
            try {
                Turma turma = turmaDAO.buscarPorCodigo(codigo);
                if (turma != null) {
                    model.addAttribute("turma", turma);
                    model.addAttribute("matriculas", turmaDAO.buscarAlunosPorTurma(turma.getId()));
                } else {
                    model.addAttribute("erro", "Turma não encontrada com o código: " + codigo);
                }
            } catch (Exception e) {
                model.addAttribute("erro", "Erro na consulta: " + e.getMessage());
            }
        }
        return "turma/consultar";
    }

    // Exibe formulário para matricular aluno na turma
    @GetMapping("/{turmaId}/matricular")
    public String formMatricular(@PathVariable Integer turmaId, Model model) {
        try {
            model.addAttribute("turma", turmaDAO.listarTodos().stream()
                    .filter(t -> t.getId().equals(turmaId)).findFirst().orElse(null));
            model.addAttribute("alunos", alunoDAO.listarTodos());
            model.addAttribute("turmaId", turmaId);
        } catch (Exception e) {
            model.addAttribute("erro", "Erro: " + e.getMessage());
        }
        return "turma/matricular";
    }

    // Salva matrícula de aluno na turma
    @PostMapping("/matricular")
    public String matricular(@RequestParam Integer alunoId,
                             @RequestParam Integer turmaId,
                             RedirectAttributes attrs) {
        try {
            turmaDAO.matricularAluno(alunoId, turmaId);
            attrs.addFlashAttribute("sucesso", "Aluno matriculado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao matricular: " + e.getMessage());
        }
        return "redirect:/turmas";
    }

    // Lança notas de um aluno na turma
    @PostMapping("/notas")
    public String lancarNotas(@RequestParam Integer matriculaId,
                              @RequestParam Double nota1,
                              @RequestParam Double nota2,
                              @RequestParam Double nota3,
                              @RequestParam String codigoTurma,
                              RedirectAttributes attrs) {
        try {
            turmaDAO.lancarNotas(matriculaId, nota1, nota2, nota3);
            attrs.addFlashAttribute("sucesso", "Notas lançadas com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao lançar notas: " + e.getMessage());
        }
        return "redirect:/turmas/consultar?codigo=" + codigoTurma;
    }

    // Deleta turma
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id, RedirectAttributes attrs) {
        try {
            turmaDAO.deletar(id);
            attrs.addFlashAttribute("sucesso", "Turma removida com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao remover turma: " + e.getMessage());
        }
        return "redirect:/turmas";
    }
}
