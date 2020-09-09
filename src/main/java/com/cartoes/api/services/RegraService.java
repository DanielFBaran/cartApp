package com.cartoes.api.services;
 
import java.util.List;
import java.util.Optional;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cartoes.api.entities.Regras;
import com.cartoes.api.repositories.RegraRepository;
import com.cartoes.api.utils.ConsistenciaException;
 
@Service
public class RegraService {
 
   	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
 
   	@Autowired
   	private RegraRepository regraRepository;
 
   	public Optional<Regras> buscarPorId(int id) throws ConsistenciaException {
 
          	log.info("Service: buscando uma regra com o id: {}", id);
 
          	Optional<Regras> regra = regraRepository.findById(id);
 
          	if (!regra.isPresent()) {
 
                 	log.info("Service: Nenhuma regra com id: {} foi encontrada", id);
                 	throw new ConsistenciaException("Nenhuma regra com id: {} foi encontrada", id);
 
          	}
 
          	return regra;
 
   	}
 
   	public Optional<Regras> buscarPorNome(String nome) throws ConsistenciaException {
 
          	log.info("Service: buscando uma regra com o nome: '{}'", nome);
 
          	Optional<Regras> regra = Optional.ofNullable(regraRepository.findByNome(nome));
 
          	if (!regra.isPresent()) {
 
                 	log.info("Service: Nenhuma regra com nome: '{}' foi encontrada", nome);
                 	throw new ConsistenciaException("Nenhuma regra com nome: '{}' foi encontrada", nome);
 
          	}
 
          	return regra;
 
   	}
 
   	public Optional<List<Regras>> buscarTodasAsRegras() throws ConsistenciaException {
 
          	log.info("Service: buscando todas as regras");
 
          	Optional<List<Regras>> regras = Optional.ofNullable(regraRepository.findAll());
 
          	if (!regras.isPresent() || regras.get().size() < 1) {
 
                 	log.info("Service: Nenhuma regra foi encontrada");
                 	throw new ConsistenciaException("Nenhuma regra foi encontrada");
 
          	}
 
          	return regras;
 
   	}
}
