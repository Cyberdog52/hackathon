package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Skill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final InsightClient insightClient;

    public SkillService(InsightClient insightClient) {
        this.insightClient = insightClient;
    }

    public List<Skill> getSkills(String userId) {
        return insightClient.getSkills(userId);
    }
}