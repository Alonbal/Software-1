package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Bomber implements Spaceship{
	
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	private List<Weapon> weapons;
	private int numberOfTechnicians = 0;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
		this.weapons = weapons;
		this.numberOfTechnicians = numberOfTechnicians;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getCommissionYear() {
		return commissionYear;
	}

	@Override
	public float getMaximalSpeed() {
		return maximalSpeed;
	}

	@Override
	public int getFirePower() {
		int s = 10;
		for (Weapon weapon : weapons) s += weapon.getFirePower();
		return s;
	}

	@Override
	public Set<? extends CrewMember> getCrewMembers() {
		return crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int s = 0;
		for (Weapon weapon : weapons) s += weapon.getAnnualMaintenanceCost();
		int discount = this.numberOfTechnicians * 10;
		s = (int)(((float)s) * (((float)(100-discount))/((float)100)));
		return s + 5000;
	}

	public int getNumberOfTechnicians() {
		return numberOfTechnicians;
	}
	
	public List<Weapon> getWeapon() {
		return weapons;
	}
	
	@Override
	public String toString() {
		String str = "Bomber";
		str += "\n\tName=" + this.getName();
		str += "\n\tCommissionYear=" + this.getCommissionYear();
		str += "\n\tMaximalSpeed=" + this.getMaximalSpeed();
		str += "\n\tFirePower=" + this.getFirePower();
		str += "\n\tCrewMembers=" + this.getCrewMembers().size();
		str += "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		str += "\n\tWeaponArray=" + this.getWeapon().toString();
		str += "\n\tNumberOfTechnicians=" + this.getNumberOfTechnicians();
		return str;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bomber other = (Bomber) obj;
		return this.getName().equals(other.getName());
	}

	
}
