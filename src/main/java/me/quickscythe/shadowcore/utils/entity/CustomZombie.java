package me.quickscythe.shadowcore.utils.entity;

import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class CustomZombie extends Zombie implements CustomMob {
    public CustomZombie(Level world) {
        super(world);
    }
}
