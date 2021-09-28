package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Badge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadgeService {

    public List<Badge> getBadges(String userId) {
        return List.of(
                new Badge(1, String.format("%s's first badge for accessing our awesome app!", userId)));
    }

}