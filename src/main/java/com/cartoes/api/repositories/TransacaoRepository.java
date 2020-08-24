package com.cartoes.api.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cartoes.api.entities.Transacao;
@Transactional(readOnly = true)
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
	@Query("SELECT trans FROM Transacao trans WHERE trans.cartao.numero = :cartaoNumero")
	List<Transacao> findByNumeroCartao(@Param("cartaoNumero") int cartaoNumero);
}
