package br.udesc.edu.demandatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import br.udesc.edu.demandatech.model.entity.*;
import br.udesc.edu.demandatech.repository.*;
import br.udesc.edu.demandatech.service.*;

@SpringBootApplication
public class TestInsert {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TestInsert.class, args);
        DemandaRepository repo = ctx.getBean(DemandaRepository.class);
        
        System.out.println("--- TESTING DEMANDA ---");
        Demanda d = new Demanda();
        d.setTitulo("Teste");
        d = repo.save(d);
        System.out.println("Saved ID: " + d.getIdDemanda());
        
        boolean exists = repo.findById(d.getIdDemanda()).isPresent();
        System.out.println("Exists by findById: " + exists);
        
        System.exit(0);
    }
}
