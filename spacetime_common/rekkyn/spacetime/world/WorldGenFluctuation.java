package rekkyn.spacetime.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFluctuation extends WorldGenerator {
    
    public int blockID;
    public int radius;
    public List affectedBlockPositions = new ArrayList();
    
    public WorldGenFluctuation(int blockID, int radius) {
        this.blockID = blockID;
        this.radius = radius;
    }
    
    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (y > world.getPrecipitationHeight(x, z) + 25) { return false; }
        for (int lmnop = -1; lmnop <= 1; lmnop++) {
            for (int qrst = -1; qrst <=1; qrst++){
                for (int wxyz = -1; wxyz <=1; wxyz++) {
                    world.setBlockToAir(x+lmnop, y+qrst, z+wxyz);
                }
            }
        }
        Explosion explosion = new Explosion(world, null, x, y, z, radius);
        explosion.doExplosionA();
        affectedBlockPositions = explosion.affectedBlockPositions;
        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        int l;
        iterator = affectedBlockPositions.iterator();
        while (iterator.hasNext()) {
            chunkposition = (ChunkPosition) iterator.next();
            i = chunkposition.x;
            j = chunkposition.y;
            k = chunkposition.z;
            l = world.getBlockId(i, j, k);
            if (l > 0) {
                world.setBlockToAir(i, j, k);
            }
        }
        world.setBlock(x, y, z, blockID);
        return true;
    }
    
}
