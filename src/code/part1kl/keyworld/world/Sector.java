package code.part1kl.keyworld.world;

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
	}
	
	public void setVoxel(int x, int y, int z, Voxel v) {
		voxels[x][y][z] = v;
	}
	
	public Voxel getVoxel(int x, int y, int z) {
		return voxels[x][y][z];
	}
}
