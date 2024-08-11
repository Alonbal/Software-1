package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class CrewWoman implements CrewMember {

	private String name;
	private int age;
	private int yearsInService;
	
	public CrewWoman(int age, int yearsInService, String name){
		this.name = name;
		this.age = age;
		this.yearsInService = yearsInService;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public int getYearsInService() {
		return this.yearsInService;
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
		CrewWoman other = (CrewWoman) obj;
		return this.getName().equals(other.getName());
	}
}
