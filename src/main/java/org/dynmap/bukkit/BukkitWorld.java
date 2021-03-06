package org.dynmap.bukkit;
/**
 * Bukkit specific implementation of DynmapWorld
 */
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.dynmap.DynmapChunk;
import org.dynmap.DynmapLocation;
import org.dynmap.DynmapWorld;
import org.dynmap.utils.MapChunkCache;

public class BukkitWorld extends DynmapWorld {
    private World world;
    private Permission perm;
    private World.Environment env;
    private boolean skylight;
    
    public BukkitWorld(World w) {
        super(w.getName(), w.getMaxHeight(), w.getSeaLevel());
        world = w;
        env = world.getEnvironment();
        skylight = (env == World.Environment.NORMAL);
        perm = new Permission("dynmap.world." + getName(), "Dynmap access for world " + getName(), PermissionDefault.OP);
    }
    /* Test if world is nether */
    @Override
    public boolean isNether() {
        return env == World.Environment.NETHER;
    }
    /* Get world spawn location */
    @Override
    public DynmapLocation getSpawnLocation() {
        DynmapLocation dloc = new DynmapLocation();
        Location sloc = world.getSpawnLocation();
        dloc.x = sloc.getBlockX(); dloc.y = sloc.getBlockY();
        dloc.z = sloc.getBlockZ(); dloc.world = normalizeWorldName(sloc.getWorld().getName());
        return dloc;
    }
    /* Get world time */
    @Override
    public long getTime() {
        return world.getTime();
    }
    /* World is storming */
    @Override
    public boolean hasStorm() {
        return world.hasStorm();
    }
    /* World is thundering */
    @Override
    public boolean isThundering() {
        return world.isThundering();
    }
    /* World is loaded */
    @Override
    public boolean isLoaded() {
        return (world != null);
    }
    /* Get light level of block */
    @Override
    public int getLightLevel(int x, int y, int z) {
        return world.getBlockAt(x, y, z).getLightLevel();
    }
    /* Get highest Y coord of given location */
    @Override
    public int getHighestBlockYAt(int x, int z) {
        return world.getHighestBlockYAt(x, z);
    }
    /* Test if sky light level is requestable */
    @Override
    public boolean canGetSkyLightLevel() {
        return skylight;
    }
    /* Return sky light level */
    @Override
    public int getSkyLightLevel(int x, int y, int z) {
        return world.getBlockAt(x, y, z).getLightFromSky();
    }
    /**
     * Get world environment ID (lower case - normal, the_end, nether)
     */
    @Override
    public String getEnvironment() {
        return env.name().toLowerCase();
    }
    /**
     * Get map chunk cache for world
     */
    @Override
    public MapChunkCache getChunkCache(List<DynmapChunk> chunks) {
        NewMapChunkCache c = new NewMapChunkCache();
        c.setChunks(this, chunks);
        return c;
    }
    
    public World getWorld() {
        return world;
    }
}
