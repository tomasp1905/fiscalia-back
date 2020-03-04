package com.fiscalia.springboot.backend.apirest.models.dao;



import org.springframework.data.jpa.repository.JpaRepository;



import com.fiscalia.springboot.backend.apirest.models.entity.ResumenNormativo;

public interface IResumenNormativoDao  extends JpaRepository<ResumenNormativo, Long> {
	
//	@Query("select p from ResumenNormativo p where p.anio like ?1")
//	public List<ResumenNormativo> findByAnio(String term);
	

}
