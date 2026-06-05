package com.universidade.controller;

import com.universidade.dao.AlunoDAO;
import com.universidade.dao.MateriaDAO;
import com.universidade.dao.TurmaDAO;
import com.universidade.model.Matricula;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final MateriaDAO materiaDAO = new MateriaDAO();

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Matricula> ultimas = turmaDAO.buscarUltimasMatriculas(5);
            model.addAttribute("ultimasMatriculas", ultimas);
        } catch (Exception e) {
            model.addAttribute("ultimasMatriculas", List.of());
        }
        try {
            model.addAttribute("totalAlunos", alunoDAO.listarTodos().size());
        } catch (Exception e) {
            model.addAttribute("totalAlunos", 0);
        }
        try {
            model.addAttribute("totalMaterias", materiaDAO.listarTodos().size());
        } catch (Exception e) {
            model.addAttribute("totalMaterias", 0);
        }
        try {
            model.addAttribute("totalTurmas", turmaDAO.listarTodos().size());
        } catch (Exception e) {
            model.addAttribute("totalTurmas", 0);
        }
        return "index";
    }
}