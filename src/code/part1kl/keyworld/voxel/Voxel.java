package code.part1kl.keyworld.voxel;

/**
 * 
 * @author part1kl
 *
 */
public class Voxel {
//TODO: add mass and other attributes
	/**The size of one Voxel (used for rendering)*/
	public final int SIZE=1;
	private int ident;
	private String name;
	
	public Voxel(int id, String type) {
		ident = id;
		name = type;
	}
	
	
	/**Voxel identifier (based on type)*/
	public int ID() { return ident; }
}
