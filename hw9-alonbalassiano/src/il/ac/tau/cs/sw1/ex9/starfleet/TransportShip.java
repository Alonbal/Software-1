package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;
import java.util.Set;

public class TransportShip implements Spaceship{
	
	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<? extends CrewMember> crewMembers;
	private int cargoCapacity;
	private int passengerCapacity;
	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
	}
	
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
		return 10;
	}

	@Override
	public Set<? extends CrewMember> getCrewMembers() {
		return crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return 3000 + 5 * cargoCapacity + 3 * passengerCapacity;
	}
	
	public int getCargoCapacity() {
		return cargoCapacity;
	}
	
	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	
	@Override
	public String toString() {
		String str = "TransportShip";
		str += "\n\tName=" + this.getName();
		str += "\n\tCommissionYear=" + this.getCommissionYear();
		str += "\n\tMaximalSpeed=" + this.getMaximalSpeed();
		str += "\n\tFirePower=" + this.getFirePower();
		str += "\n\tCrewMembers=" + this.getCrewMembers().size();
		str += "\n\tAnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();
		str += "\n\tCargoCapacity=" + this.getCargoCapacity();
		str += "\n\tPassengerCapacity=" + this.getPassengerCapacity();
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
		TransportShip other = (TransportShip) obj;
		return this.getName().equals(other.getName());
	}
	
	

}
