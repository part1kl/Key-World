package code.part1kl.keyworld.world;

import code.part1kl.keyworld.start.Launch;
import code.part1kl.keyworld.utils.Float3;
import code.part1kl.keyworld.voxel.Voxel;

/**Container that holds {@link code.part1kl.keyworld.voxel.Voxels}. Synonymous with Minecraft "Chunks"
 * 
 * @author part1kl
 *
 */
public class Sector {

	/**The size of Sectors in {@link code.part1kl.keyworld.voxel.Voxel Voxels}*/
	public final static int SIZE = 20;
	
	/**Holds all Voxels in the sector
	 */
	public Voxel[][][] voxels;
	public int xL, yL, zL;
	
	public Sector(int x, int y, int z) {
		voxels = new Voxel[SIZE][SIZE][SIZE];
		xL = x;
		yL = y;
		zL = z;
	}
	
	public void setVoxel(int x, int y, int z, Voxel v) {
		voxels[x][y][z] = v;
	}
	
	public Voxel getVoxel(int x, int y, int z) {
		return voxels[x][y][z];
	}
	
	public void subtract(Sector in) {
		for(int xi=0; xi<Sector.SIZE; xi++) {
			for(int zi=0; zi<Sector.SIZE; zi++) {
				for(int yi=0; yi<Sector.SIZE; yi++) {
					byte[] materials = new byte[64];
					for(int i=0; i<64; i++) {
						materials[i]=World.materials[0].ident;
					}
					if(this.getVoxel(xi, yi, zi).matTypes[0]!=0 && in.getVoxel(xi, yi, zi).matTypes[0]!=0)
						this.setVoxel(xi, yi, zi, new Voxel(materials));
				}
			}
		}
	}
	
	
	 public boolean[] checkFaces(int x, int y, int z) {
	    	int[] surrounding = {-2, -2, -2, -2, -2, -2};
			
	    	Float3 minPos = new Float3(0,0,0);
	    	Float3 maxPos = new Float3(World.SIZE-1, World.SIZE-1, World.SIZE-1);
	    	if(Launch.WORLD.getSector(xL , yL, zL).getVoxel(x, y, z).matTypes[0]==0) {
	    		return new boolean[6];
	    	}
			if(isPosOnEdge(x, y, z)) {
				if(x == 0) {
					if(xL == minPos.a)	surrounding[2] = -1;
					else surrounding[2] = Launch.WORLD.getSector(xL - 1, yL, zL).getVoxel(SIZE - 1, y, z).matTypes[0];
				}  
				
				if(y == 0) {
					if(yL == minPos.b)	surrounding[4] = -1;
					else surrounding[4] = Launch.WORLD.getSector(xL, yL - 1, zL).getVoxel(x, SIZE - 1, z).matTypes[0];
				}  
				
				if(z == 0) {
					if(zL == minPos.c)	surrounding[0] = -1;
					else surrounding[0] = Launch.WORLD.getSector(xL, yL, zL - 1).getVoxel(x, y, SIZE - 1).matTypes[0];
				}  
				
				if(x == SIZE - 1) {
					if(xL == maxPos.a)	surrounding[3] = -1;
					else surrounding[3] = Launch.WORLD.getSector(xL + 1, yL, zL).getVoxel(0, y, z).matTypes[0];
				}  

				if(y == SIZE - 1) {
					if(yL == maxPos.b)	surrounding[5] = -1;
					else surrounding[5] = Launch.WORLD.getSector(xL, yL + 1, zL).getVoxel(x, 0, z).matTypes[0];
				} 

				if(z == SIZE - 1) {
					if(zL == maxPos.c)	surrounding[1] = -1;
					else surrounding[1] = Launch.WORLD.getSector(xL, yL, zL + 1).getVoxel(x, y, 0).matTypes[0];
				} 
			}
			
			if(surrounding[2] == -2) surrounding[2] = getVoxel(x - 1, y, z).matTypes[0]; // 0 -x			1				z         
			if(surrounding[1] == -2) surrounding[1] = getVoxel(x, y, z + 1).matTypes[0]; // 1 +z		0	C	2		-x	c	x     
			if(surrounding[3] == -2) surrounding[3] = getVoxel(x + 1, y, z).matTypes[0]; // 2 +x			3			   -z         
			if(surrounding[0] == -2) surrounding[0] = getVoxel(x, y, z - 1).matTypes[0]; // 3 -z					4				+y
			if(surrounding[5] == -2) surrounding[5] = getVoxel(x, y + 1, z).matTypes[0]; // 4 +y					C				 c
			if(surrounding[4] == -2) surrounding[4] = getVoxel(x, y - 1, z).matTypes[0]; // 5 -y					5				-y
			
			boolean[] out = new boolean[6];
			
			for(int i=0; i< out.length; i++) {
				out[i] = surrounding[i]==0 || surrounding[i]==-1;
			}
			
	    	return out;
	    }
	 
		public boolean isPosOnEdge(int x, int y, int z) {
			return x == 0 || y == 0 || z == 0 ||
					x == SIZE - 1 || y == SIZE - 1 || z == SIZE - 1;
		}
}
