package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class Cylon implements CrewMember {
	
	private String name;
	private int age;
	private int yearsInService;
	private int modelNumber;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		this.name = name;
		this.age = age;
		this.yearsInService = yearsInService;
		this.modelNumber = modelNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getYearsInService() {
		return yearsInService;
	}
	
	public int getModelNumber() {
		return modelNumber;
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
		Cylon other = (Cylon) obj;
		return name.equals(other.getName());
	}
}
