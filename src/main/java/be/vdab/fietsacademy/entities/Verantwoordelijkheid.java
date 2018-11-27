package be.vdab.fietsacademy.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "verantwoordelijkheden")
public class Verantwoordelijkheid implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;

	@ManyToMany
	@JoinTable(name = "docentenverantwoordelijkheden", joinColumns = @JoinColumn(name = "verantwoordelijkheidid"), inverseJoinColumns = @JoinColumn(name = "docentid"))
	private Set<Docent> docenten = new LinkedHashSet<>();

	public Verantwoordelijkheid(String naam) {
		this.naam = naam;
	}

	protected Verantwoordelijkheid() {
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Verantwoordelijkheid)) {
			return false;
		}
		if (naam == null) {
			return false;
		}
		return naam.equalsIgnoreCase(((Verantwoordelijkheid) object).naam);
	}

	@Override
	public int hashCode() {
		return naam == null ? 0 : naam.toLowerCase().hashCode();
	}

	public boolean add(Docent docent) {
		// throw new UnsupportedOperationException();
		boolean toegevoegd = docenten.add(docent);
		if (!docent.getVerantwoordelijkheden().contains(this)) {
			docent.add(this);
		}
		return toegevoegd;
	}

	public boolean remove(Docent docent) {
		// throw new UnsupportedOperationException();
		boolean verwijderd = docenten.remove(docent);
		if (docent.getVerantwoordelijkheden().contains(this)) {
			docent.remove(this);
		}
		return verwijderd;
	}

	public Set<Docent> getDocenten() {
		// throw new UnsupportedOperationException();
		return Collections.unmodifiableSet(docenten);
	}
}