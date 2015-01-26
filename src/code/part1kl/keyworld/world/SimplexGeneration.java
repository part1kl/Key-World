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
					double elevation = SimplexNoise.noise((double)(x*Sector.SIZE+xi)/Sector.SIZE*2, (double)(y*Sector.SIZE+yi)/Sector.SIZE*2, (double)(z*Sector.SIZE+zi)/Sector.SIZE*2)/2;
					
					double h = elevation;
//					h += (y*Sector.SIZE+yi>World.WORLD_HEIGHT ? -.2:.2);
//					h-=.25;
//					System.out.println("SIMPLEX_RESULT"+h);
					byte[] materials = new byte[Voxel.MATERIAL_AMOUNT];
					byte e;
					if(h>0)
						e = (byte) (r.nextBoolean() ? 1:2);
					else
						e = 0;
//					System.out.println("_"+e);
					for(int i=0; i<Voxel.MATERIAL_AMOUNT; i++) {
						materials[i]=World.materials[e].ident;
					}
					Voxel v = new Voxel(materials);
//					v.color = new Float4((float)(h), (float)(h), (float)(h), 1);
					out.setVoxel(xi, yi, zi, v);
//					System.out.println("Voxel ["+xi+", "+yi+", "+zi+"] set");
				}
			}
		}
		
		return out;
	}
}
