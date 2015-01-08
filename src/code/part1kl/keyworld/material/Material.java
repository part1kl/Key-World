package code.part1kl.keyworld.material;

import code.part1kl.keyworld.utils.Float3;

/**Materials hold properties that define how {@link code.part1kl.keyworld.voxel.Voxel Voxels} interact and behave
 * 
 * @author part1kl
 *
 */
public class Material {

	public byte ident;
	public float MASS;
	public String NAME;
	public boolean FLAMMABLE;
	public boolean ORE;
	public boolean NATURAL;
	public Float3 COLOR;
	
	public Material(byte id, float mass, String name, boolean flammable, boolean ore, boolean natural, Float3 color) {
		ident = id;
		MASS = mass;
		NAME = name;
		FLAMMABLE = flammable;
		ORE = ore;
		NATURAL = natural;
		COLOR = color;
	}
}
