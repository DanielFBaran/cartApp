package com.cartoes.api.repositories;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
 
import com.cartoes.api.entities.Regras;
 
public interface RegraRepository extends JpaRepository<Regras, Integer> {
   	
   	@Transactional(readOnly = true)
   	Regras findByNome(String nome);
   	
}
