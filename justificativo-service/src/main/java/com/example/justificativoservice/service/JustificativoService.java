package com.example.justificativoservice.service;

import com.example.justificativoservice.model.DatarelojModel;
import com.example.justificativoservice.entity.JustificativoEntity;
import com.example.justificativoservice.repository.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JustificativoService {

    @Autowired
    private JustificativoRepository justificativoRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<JustificativoEntity> listarJustificativos(){
        return justificativoRepository.findAll();
    }

    public JustificativoEntity guardarJustificativo(JustificativoEntity justificativo){
        return justificativoRepository.save(justificativo);
    }

    public void actualizarJustificativo(int just, Long id){
        justificativoRepository.actualizarJustificativo(just,id);
    }

    public void calcularInasistencias(DatarelojModel[] marcasReloj){

        int i = 0;
        while(i < marcasReloj.length){
            if( ((marcasReloj[i].getHora().getHours() >= 9) && (marcasReloj[i].getHora().getMinutes() > 10)) || ((marcasReloj[i].getHora().getHours() >=10) && (marcasReloj[i].getHora().getHours() < 14))){
                JustificativoEntity nuevoJustificativo = new JustificativoEntity();

                nuevoJustificativo.setRutEmpleado(marcasReloj[i].getRutEmpleadoReloj());
                nuevoJustificativo.setFecha(marcasReloj[i].getFecha());
                nuevoJustificativo.setJustificada(0);

                guardarJustificativo(nuevoJustificativo);

            }
            i = i + 1;
        }
    }

    public void eliminarInasistencias(){
        justificativoRepository.deleteAll();
    }

    public DatarelojModel[] getMarcasReloj(){
        DatarelojModel[] marcasReloj = restTemplate.getForObject("http://datareloj-service/datareloj", DatarelojModel[].class);
        return marcasReloj;
    }
}