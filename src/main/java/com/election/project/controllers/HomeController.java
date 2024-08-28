package com.election.project.controllers;

import com.election.project.entity.*;
import com.election.project.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;

    public HomeController(UserRepository userRepository ,PartyRepository partyRepository, CandidateRepository candidateRepository, ElectionRepository electionRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.partyRepository = partyRepository;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/")
    public String entryRoute(Principal principal) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("name", user.getFullname());

        if (Objects.equals(user.getRole(), "ADMIN")) {
            model.addAttribute("isAdmin", true);
        }

        List<Election> temp = electionRepository.findByStatus(Election.ElectionStatus.ACTIVE);
        if (!temp.isEmpty()) {
            model.addAttribute("electionActive", true);

            List<Vote> votes = voteRepository.findByUser(user);
            if (votes.isEmpty()) model.addAttribute("voted", false);
            else model.addAttribute("voted", true);
        }

        return "home";
    }

    @GetMapping("/parties")
    public String listParties(Model model) {
        List<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "list-parties";
    }

    @GetMapping("/candidates")
    public String listCandidates(Model model) {
        List<Candidate> candidates = candidateRepository.findAll();
        model.addAttribute("candidates", candidates);
        return "list-candidates";
    }

    @GetMapping("/results")
    public String listElections(Model model) {
        List<Election> elections = electionRepository.findAll();
        model.addAttribute("elections", elections);
        model.addAttribute("active", Election.ElectionStatus.ACTIVE);
        return "list-elections";
    }
}
