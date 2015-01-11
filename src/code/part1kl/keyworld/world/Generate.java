package code.part1kl.keyworld.world;

import java.util.Random;

import code.part1kl.keyworld.start.Launch;
import code.part1kl.keyworld.voxel.Voxel;

public class Generate {
	
	Random r = new Random();
	SimplexGeneration generateSim;
	TerrainGeneration generateTer;
	
	public Generate() {
		generateSim = new SimplexGeneration();
		generateTer = new TerrainGeneration();
	}
	
//	public Sector generate(int x, int y, int z) {
//		Sector out = new Sector(x, y, z);
//		
//		for(int a=0; a<Sector.SIZE; a++) {
//			for(int b=0; b<Sector.SIZE; b++) {
//				for(int c=0; c<Sector.SIZE; c++) {
//					byte[] materials = new byte[64];
//					byte e =(byte) (r.nextBoolean() ? 0:(r.nextBoolean() ? 1:2));
////					System.out.println("_"+e);
//					for(int i=0; i<64; i++) {
//						materials[i]=Launch.WORLD.materials[e].ident;
//					}
//					Voxel v = new Voxel(materials);
//					out.setVoxel(a, b, c, v);
////					System.out.println("Voxel ["+a+", "+b+", "+c+"] set");
//				}
//			}
//		}
//		
//		System.out.println("Sector ["+x+", "+y+", "+z+"] created");
//		return out;
//	}
	
	public Sector generate(int x, int y, int z) {
		Sector caves = generateSim.generate(x, y, z);
		Sector out = generateTer.generate(x, y, z);
		out.subtract(caves);
		return out;
	}
}
