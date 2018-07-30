package com.nightbase.api.promoter.controller;

import com.nightbase.api.promoter.model.Promoter;
import com.nightbase.api.exception.ResourceNotFoundException;
import com.nightbase.api.promoter.repository.PromoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("promoter")
public class PromoterController {

    @Autowired
    PromoterRepository promoterRepository;

    @GetMapping("/id/{id}")
    public Promoter getPromoterById(@PathVariable(value = "id") Long promoterId) {
        return promoterRepository.findById(promoterId)
                .orElseThrow(() -> new ResourceNotFoundException("Promoter", "id", promoterId));
    }


    @GetMapping("name/{name}")
    public ResponseEntity<Promoter> getPromoterByName(@PathVariable("name") String name) {
        Promoter promoter;
        if(promoterRepository.existsByName(name)){
            promoter = promoterRepository.findPromoterByName(name);
        } else {
            throw new ResourceNotFoundException("Promoter", "name", name);
        }
        return new ResponseEntity<Promoter>(promoter, HttpStatus.OK);
    }

    @GetMapping("allpromoters")
    public List<Promoter> getAllPromoters() {
        return promoterRepository.findAll();
    }

    @PostMapping("add")
    public Promoter createPromoter(@Valid @RequestBody Promoter promoter) {
        return promoterRepository.save(promoter);
    }

    @PutMapping("/update/{id}")
    public Promoter updatePromoter(@PathVariable(value = "id") Long promoterId,
                               @Valid @RequestBody Promoter promoterDetails) {

        Promoter promoter = promoterRepository.findById(promoterId)
                .orElseThrow(() -> new ResourceNotFoundException("Promoter", "id", promoterId));

        promoterDetails.setName(promoter.getName());
        promoterDetails.setRegion(promoter.getRegion());
        promoterDetails.setResidentadvisor(promoter.getResidentadvisor());

        Promoter updatedPromoter = promoterRepository.save(promoter);
        return updatedPromoter;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePromoter(@PathVariable(value = "id") Long promoterId) {
        Promoter promoter = promoterRepository.findById(promoterId)
                .orElseThrow(() -> new ResourceNotFoundException("Promoter", "id", promoterId));

        promoterRepository.delete(promoter);

        return ResponseEntity.ok().build();
    }
}
