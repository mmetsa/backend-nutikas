package ee.nutikas.games.api.game.engine.Level;

import org.springframework.stereotype.Service;

@Service
public class LevelSystem {
    private static final int MAX_LEVEL = 1000;
    private final long[] experienceTable;

    public LevelSystem() {
        experienceTable = new long[MAX_LEVEL];

        for (int level = 1; level < MAX_LEVEL; level++) {
            long experienceForNextLevel = (long) (Math.pow(level, 2) * 100);
            experienceTable[level] = experienceTable[level - 1] + experienceForNextLevel;
        }
    }

    public int getLevelForExperience(Long experience) {
        if (experience == null) {
            experience = 0L;
        }
        for (int level = 0; level < MAX_LEVEL; level++) {
            if (experienceTable[level] > experience) {
                return level;
            }
        }

        return MAX_LEVEL;
    }

    public long getExperienceRequiredForNextLevel(int currentLevel) {
        if (currentLevel + 1 <= 0 || currentLevel + 1> MAX_LEVEL) {
            throw new IllegalArgumentException("Level must be between 1 and " + MAX_LEVEL);
        }

        return experienceTable[currentLevel];
    }
}