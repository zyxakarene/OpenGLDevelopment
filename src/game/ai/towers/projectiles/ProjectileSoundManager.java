package game.ai.towers.projectiles;

import game.sound.Sounds;
import java.util.HashMap;

class ProjectileSoundManager
{

    private static final HashMap<ProjectileType, ProjectileSoundEntry> mappings = new HashMap<>();

    static
    {
        ProjectileSoundEntry rocketEntry = new ProjectileSoundEntry(Sounds.ROCKET_LAUNCH, Sounds.ROCKET_FLY, Sounds.EXPLOSION);
        mappings.put(ProjectileType.ROCKET, rocketEntry);
    }

    static Sounds getFireSoundFrom(ProjectileType type)
    {
        return mappings.get(type).fire;
    }

    static Sounds getFlySoundFrom(ProjectileType type)
    {
        return mappings.get(type).fly;
    }

    static Sounds getHitSoundFrom(ProjectileType type)
    {
        return mappings.get(type).hit;
    }

    private static class ProjectileSoundEntry
    {

        private final Sounds fire;
        private final Sounds fly;
        private final Sounds hit;

        public ProjectileSoundEntry(Sounds fire, Sounds fly, Sounds hit)
        {
            this.fire = fire;
            this.fly = fly;
            this.hit = hit;
        }
    }
}
