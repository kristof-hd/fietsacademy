package be.vdab.fietsacademy.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import be.vdab.fietsacademy.entities.Docent;
import be.vdab.fietsacademy.queryresults.AantalDocentenPerWedde;
import be.vdab.fietsacademy.queryresults.IdEnEmailAdres;

@Repository
class JpaDocentRepository implements DocentRepository {

	private final EntityManager manager; 
	
	JpaDocentRepository(EntityManager manager) {
		this.manager=manager; 
	}
	
	@Override
	public Optional<Docent> read(long id) {
		//throw new UnsupportedOperationException(); 
		return Optional.ofNullable(manager.find(Docent.class, id)); 
	}
	
	@Override
	public void create(Docent docent) {
		//throw new UnsupportedOperationException(); 
		manager.persist(docent);
	}
	
	@Override
	public void delete(long id) {
		//throw new UnsupportedOperationException(); 
		read(id).ifPresent(docent -> manager.remove(docent));
	}
	
	@Override
	public List<Docent> findAll() {
		//throw new UnsupportedOperationException(); 
		return manager.createQuery("select d from Docent d order by d.wedde", Docent.class).getResultList(); 
	}
	
	@Override
	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot){
		//throw new UnsupportedOperationException(); 
		//return manager.createQuery("select d from Docent d where d.wedde between :van and :tot", Docent.class)
		//					.setParameter("van", van)
		//					.setParameter("tot", tot)
		//					.getResultList(); 
		return manager.createNamedQuery("Docent.findByWeddeBetween", Docent.class)
							.setParameter("van", van)
							.setParameter("tot", tot)
							.setHint("javax.persistence.loadgraph", manager.createEntityGraph("Docent.metCampus"))
							.getResultList(); 
	}
	
	@Override
	public List<String> findEmailAdressen() {
		//throw new UnsupportedOperationException(); 
		return manager.createQuery("select d.emailAdres from Docent d", String.class).getResultList(); 
	}
	
	@Override
	public List<IdEnEmailAdres> findIdsEnEmailAdressen() {
		//throw new UnsupportedOperationException(); 
		return manager.createQuery("select new be.vdab.fietsacademy.queryresults.IdEnEmailAdres(d.id, d.emailAdres)" + "from Docent d", IdEnEmailAdres.class).getResultList(); 
	}
	
	@Override
	public BigDecimal findGrootsteWedde() {
		//throw new UnsupportedOperationException(); 
		return manager.createQuery("select max(d.wedde) from Docent d", BigDecimal.class).getSingleResult(); 
	}
	
	@Override
	public List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
		//throw new UnsupportedOperationException(); 
		return manager.createQuery("select new be.vdab.fietsacademy.queryresults.AantalDocentenPerWedde(" + "d.wedde, count(d)) from Docent d group by d.wedde", AantalDocentenPerWedde.class).getResultList(); 
	}
	
	@Override
	public int algemeneOpslag(BigDecimal percentage) {
		//throw new UnsupportedOperationException(); 
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100))); 
		return manager.createNamedQuery("Docent.algemeneOpslag")
				.setParameter("factor", factor)
				.executeUpdate(); 
	}
}