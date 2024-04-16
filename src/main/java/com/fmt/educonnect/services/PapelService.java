package com.fmt.educonnect.services;

import com.fmt.educonnect.datasource.entities.PapelEntity;
import com.fmt.educonnect.datasource.repositories.PapelRepository;
import com.fmt.educonnect.interfaces.PapelInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PapelService implements PapelInterface {

    @Autowired
    private PapelRepository papelRepository;

    public Optional<PapelEntity> buscarPapelPorId(Long id){
        return papelRepository.findById(id);
    }

}
