package code.part1kl.keyworld.world;

import java.util.Random;

import code.part1kl.keyworld.utils.SimplexNoise;
import code.part1kl.keyworld.voxel.Voxel;

public class TerrainGeneration {

	Random r = new Random();
	
	public TerrainGeneration() {}
	
	public Sector generate(int x, int y, int z) {
		Sector out = new Sector(x, y, z);
		
		for(int xi=0; xi<Sector.SIZE; xi++) {
			for(int zi=0; zi<Sector.SIZE; zi++) {
				double elevation = SimplexNoise.noise((double)(x*Sector.SIZE+xi)/Sector.SIZE/8, (double)(z*Sector.SIZE+zi)/Sector.SIZE/8);
//				double roughness = SimplexNoise.noise((double)(x*Sector.SIZE+xi)/Sector.SIZE/4, (double)(z*Sector.SIZE+zi)/Sector.SIZE/4)*2;
//				double detail = SimplexNoise.noise((double)(x*Sector.SIZE+xi)/Sector.SIZE, (double)(z*Sector.SIZE+zi)/Sector.SIZE);
				double h = elevation;
				h = (h/2)*Sector.SIZE;
				for(int yi=0; yi<Sector.SIZE; yi++) {
					byte[] materials = new byte[Voxel.MATERIAL_AMOUNT];
					byte e;
					if(y*Sector.SIZE+yi<h+World.WORLD_HEIGHT)
						e = (byte) (r.nextBoolean() ? 1:2);
					else
						e = 0;
					for(int i=0; i<Voxel.MATERIAL_AMOUNT; i++) {
						materials[i]=World.materials[e].ident;
					}
					Voxel v = new Voxel(materials);
					out.setVoxel(xi, yi, zi, v);
				}
			}
		}
		
		return out;
	}
}
