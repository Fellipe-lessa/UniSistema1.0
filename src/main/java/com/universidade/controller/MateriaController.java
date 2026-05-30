package com.universidade.controller;

import com.universidade.dao.MateriaDAO;
import com.universidade.model.Materia;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaDAO materiaDAO = new MateriaDAO();

    // Lista todas as matérias
    @GetMapping
    public String listar(Model model) {
        try {
            model.addAttribute("materias", materiaDAO.listarTodos());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao listar matérias: " + e.getMessage());
        }
        return "materia/listar";
    }

    // Exibe formulário de cadastro
    @GetMapping("/novo")
    public String formulario() {
        return "materia/form";
    }

    // Salva nova matéria
    @PostMapping("/salvar")
    public String salvar(@RequestParam String codigo,
                         @RequestParam String nome,
                         @RequestParam String ementa,
                         RedirectAttributes attrs) {
        try {
            Materia materia = new Materia();
            materia.setCodigo(codigo);
            materia.setNome(nome);
            materia.setEmenta(ementa);
            materiaDAO.inserir(materia);
            attrs.addFlashAttribute("sucesso", "Matéria cadastrada com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao cadastrar matéria: " + e.getMessage());
        }
        return "redirect:/materias";
    }

    // Busca matéria por código
    @GetMapping("/consultar")
    public String consultar(@RequestParam(required = false) String codigo, Model model) {
        if (codigo != null && !codigo.isBlank()) {
            try {
                Materia materia = materiaDAO.buscarPorCodigo(codigo);
                if (materia != null) {
                    model.addAttribute("materia", materia);
                } else {
                    model.addAttribute("erro", "Matéria não encontrada com o código: " + codigo);
                }
            } catch (Exception e) {
                model.addAttribute("erro", "Erro na consulta: " + e.getMessage());
            }
        }
        return "materia/consultar";
    }

    // Deleta matéria
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id, RedirectAttributes attrs) {
        try {
            materiaDAO.deletar(id);
            attrs.addFlashAttribute("sucesso", "Matéria removida com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao remover matéria: " + e.getMessage());
        }
        return "redirect:/materias";
    }
}
