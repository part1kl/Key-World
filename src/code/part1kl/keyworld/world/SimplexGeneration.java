package code.part1kl.keyworld.world;

import java.util.Random;

import code.part1kl.keyworld.utils.Float3;
import code.part1kl.keyworld.utils.Float4;
import code.part1kl.keyworld.utils.SimplexNoise;
import code.part1kl.keyworld.voxel.Voxel;

public class SimplexGeneration {

	Random r = new Random();
	
	public SimplexGeneration() {}
	
	public Sector generate(int x, int y, int z) {
		Sector out = new Sector(x, y, z);
		
		for(int xi=0; xi<Sector.SIZE; xi++) {
			for(int zi=0; zi<Sector.SIZE; zi++) {
				for(int yi=Sector.SIZE-1; yi>=0; yi--) {
					double h = SimplexNoise.noise(x+(xi*.05), y+(yi*.05), z+(zi*.05));
					h += (yi<World.WORLD_HEIGHT ? -.5:.5);
//					System.out.println("SIMPLEX_RESULT"+h);
					byte[] materials = new byte[64];
					byte e;
					if(h>0)
						e = (byte) (r.nextBoolean() ? 1:2);
					else
						e = 0;
//					System.out.println("_"+e);
					for(int i=0; i<64; i++) {
						materials[i]=World.materials[e].ident;
					}
					Voxel v = new Voxel(materials);
					v.color = new Float4((float)(1-h), (float)(1-h), (float)(1-h), 1);
					out.setVoxel(xi, yi, zi, v);
					System.out.println("Voxel ["+xi+", "+yi+", "+zi+"] set");
				}
			}
		}
		
		return out;
	}
	
	
	
	
	
	public class SimplexNode extends Float3 {

		public SimplexNode(float x, float z, float val) {
			super(x, val, z);
		}
		
	}
}
