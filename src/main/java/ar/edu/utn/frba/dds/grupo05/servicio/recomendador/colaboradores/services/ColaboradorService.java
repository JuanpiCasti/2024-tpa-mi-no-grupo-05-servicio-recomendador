package ar.edu.utn.frba.dds.grupo05.servicio.recomendador.colaboradores.services;

import ar.edu.utn.frba.dds.grupo05.servicio.recomendador.colaboradores.dtos.ColaboradorDTO;
import ar.edu.utn.frba.dds.grupo05.servicio.recomendador.colaboradores.mappers.ColaboradorMapper;
import ar.edu.utn.frba.dds.grupo05.servicio.recomendador.colaboradores.models.entities.Colaborador;
import ar.edu.utn.frba.dds.grupo05.servicio.recomendador.colaboradores.models.repositories.IColaboradorRepository;
import ar.edu.utn.frba.dds.grupo05.servicio.recomendador.colaboradores.utils.ColaboradorPageableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService implements IColaboradorService {

    private IColaboradorRepository colaboradorRepository;
    private ColaboradorMapper colaboradorMapper;
    private ColaboradorPageableFactory colaboradorPageableFactory;

    @Autowired
    public ColaboradorService(IColaboradorRepository colaboradorRepository,
                              ColaboradorMapper colaboradorMapper,
                              ColaboradorPageableFactory colaboradorPageableFactory) {
        this.colaboradorRepository = colaboradorRepository;
        this.colaboradorMapper = colaboradorMapper;
        this.colaboradorPageableFactory = colaboradorPageableFactory;
    }

    public void save(Colaborador colaborador) {
        colaboradorRepository.save(colaborador);
    }

    @Override
    public void bulkSave(List<ColaboradorDTO> colaboradoresInput) {
        List<Colaborador> colaboradores =  this.colaboradorMapper.toColaboradorFromInputDTO(colaboradoresInput);
        this.colaboradorRepository.saveAll(colaboradores);
    }

    public List<ColaboradorDTO> getColaboradores(Double minPuntos,
                                                 Integer minDonaciones,
                                                 Integer page,
                                                 Integer limit,
                                                 String sortString) {
        Pageable pageable = colaboradorPageableFactory.createPageableFromSortString(page, limit, sortString);

        List<Colaborador> colaboradores = colaboradorRepository
                .findByPuntosGreaterThanEqualAndDonacionesGreaterThanEqual(pageable, minPuntos, minDonaciones);

        List<ColaboradorDTO> colaboradorDTOS = colaboradorMapper.toColaboradorOutputDTOList(colaboradores);

        return colaboradorDTOS;
    }
}
