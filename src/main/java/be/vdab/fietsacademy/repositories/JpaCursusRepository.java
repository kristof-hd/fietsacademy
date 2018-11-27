package be.vdab.fietsacademy.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import be.vdab.fietsacademy.entities.Cursus;

@Repository
public class JpaCursusRepository implements CursusRepository {

	private final EntityManager manager; 
	
	JpaCursusRepository(EntityManager manager) {
		this.manager=manager; 
	}
	
	@Override
	public Optional<Cursus> read(long id) {
		//throw new UnsupportedOperationException(); 
		return Optional.ofNullable(manager.find(Cursus.class, id)); 
	}
	
	@Override
	public void create(Cursus cursus) {
		//throw new UnsupportedOperationException(); 
		manager.persist(cursus);
	}
	
}
