package com.universidade;

import com.universidade.dao.TabelasDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversidadeApplication {// v2

    public static void main(String[] args) {
        SpringApplication.run(UniversidadeApplication.class, args);
        new TabelasDAO().criarTabelas();
    }
