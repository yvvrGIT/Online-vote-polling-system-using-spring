package com.election.project.controllers.admin;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.election.project.entity.Party;
import com.election.project.repository.PartyRepository;
import com.election.project.service.S3service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class PartyController {

    private S3service s3service;
    private PartyRepository partyRepository;

    public PartyController(PartyRepository partyRepository, S3service s3service) {
        this.partyRepository = partyRepository;
        this.s3service = s3service;
    }

    @GetMapping("/admin/parties")
    public String listParties(Model model) {
        List<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "admin/list-parties";
    }

    @GetMapping("/admin/add-party")
    public String addParty(Model model, Party party) {
        model.addAttribute("party", party);
        return "admin/add-party";
    }

    @PostMapping("/admin/add-party")
    public String addPartyPost(@ModelAttribute("party") Party party, @RequestParam("photo") MultipartFile photo, Model model) {
        Party temp = partyRepository.findByName(party.getName());
        if (temp != null) {
            model.addAttribute("partyexists", party);
            return "admin/add-party";
        }

        String imageUrl = s3service.uploadFile(photo, party.getName());
        party.setImage(imageUrl);

        System.out.println(party);
        partyRepository.save(party);
        return "redirect:/admin/parties";
    }

    @PostMapping("/admin/delete-party")
    public String deleteParty(@RequestParam Long id) {
        partyRepository.deleteById(id);
        return "redirect:/admin/parties";
    }
}
