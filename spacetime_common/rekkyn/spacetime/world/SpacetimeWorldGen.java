package rekkyn.spacetime.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import rekkyn.spacetime.Spacetime;
import cpw.mods.fml.common.IWorldGenerator;

public class SpacetimeWorldGen implements IWorldGenerator {
    
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
                break;
        }
    }
    
    private void generateEnd(World world, Random rand, int chunkX, int chunkZ) {
    }
    
    private void generateSurface(World world, Random rand, int chunkX, int chunkZ) {
        for (int k = 0; k < /*3*/ 5; k++) {
            int posX = chunkX + rand.nextInt(16);
            int posY = rand.nextInt(128);
            int posZ = chunkZ + rand.nextInt(16);
            
            new WorldGenFluctuation(Spacetime.spacetimeOre.blockID, 4).generate(world, rand, posX, posY, posZ);
        }
    }
    
    private void generateNether(World world, Random rand, int chunkX, int chunkZ) {
    }
    
}
