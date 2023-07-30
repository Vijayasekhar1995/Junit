package com.sbms.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employee")
@XmlRootElement
@RequiredArgsConstructor
public class Employee implements Comparable<Employee>, Serializable {
	
	@SequenceGenerator(name="logical_name", sequenceName = "employee_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logical_name")
	@Id
	private Integer eid;
	@NonNull
	private String ename;
	@NonNull
	private Double esal;
	@NonNull
	private String ecity;

	@Override
	public int compareTo(Employee employee) {
		return this.eid<employee.eid?-1:this.eid>employee.eid?1:0;
	}
}
