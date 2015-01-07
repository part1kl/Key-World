package code.part1kl.keyworld.voxel;

public class VoxelType {

	/**The size of one {@link code.part1kl.keyworld.voxel.Voxel Voxel} (used for rendering)*/
	public final int SIZE=1;
	private int ident;
	private String name;
	
	public VoxelType(int id, String type) {
		ident = id;
		name = type;
	}
}
