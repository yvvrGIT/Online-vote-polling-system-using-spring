package com.election.project.controllers;

import com.election.project.entity.Candidate;
import com.election.project.entity.User;
import com.election.project.entity.Vote;
import com.election.project.repository.CandidateRepository;
import com.election.project.repository.UserRepository;
import com.election.project.repository.VoteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class VoteController {

    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;

    public VoteController(UserRepository userRepository, CandidateRepository candidateRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/voting-page")
    public String votingPage(Model model) {
        List<Candidate> candidates = candidateRepository.findAll();
        model.addAttribute("candidates", candidates);

        return "voting-page";
    }

    @PostMapping("/cast-vote")
    public String castVote(@RequestParam(name = "id") Long id, Principal principal) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()) {
            User user = userRepository.findByUsername(principal.getName());
            Vote vote = new Vote();
            vote.setCandidate(candidate.get());
            vote.setUser(user);
            voteRepository.save(vote);
        }
        return "redirect:/home";
    }
}
