package com.example.spontecular;

import com.example.spontecular.dto.Classes;
import com.example.spontecular.dto.HierarchyItem;
import com.example.spontecular.dto.formDtos.ClassItem;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.parser.OutputParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.spontecular")
public class SpontecularApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpontecularApplication.class, args);

        OutputParser op1 = new BeanOutputParser(ClassItem.class);
        OutputParser op2 = new BeanOutputParser(HierarchyItem.class);

        System.out.println("Bean1: " + op1.getFormat());
        System.out.println("Bean2: " + op2.getFormat());
    }
}
