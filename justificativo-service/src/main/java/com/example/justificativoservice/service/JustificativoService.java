package com.example.justificativoservice.service;

import com.example.justificativoservice.model.DatarelojModel;
import com.example.justificativoservice.entity.JustificativoEntity;
import com.example.justificativoservice.repository.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JustificativoService {

    @Autowired
    private JustificativoRepository justificativoRepository;

    public List<JustificativoEntity> listarJustificativos(){
        return justificativoRepository.findAll();
    }

    public JustificativoEntity guardarJustificativo(JustificativoEntity justificativo){
        return justificativoRepository.save(justificativo);
    }

    public void actualizarJustificativo(int just, Long id){
        justificativoRepository.actualizarJustificativo(just,id);
    }

    public void calcularInasistencias(List<DatarelojModel> marcasReloj){

        int i = 0;
        while(i < marcasReloj.size()){
            if( ((marcasReloj.get(i).getHora().getHours() >= 9) && (marcasReloj.get(i).getHora().getMinutes() > 10)) || ((marcasReloj.get(i).getHora().getHours() >=10) && (marcasReloj.get(i).getHora().getHours() < 14))){
                JustificativoEntity nuevoJustificativo = new JustificativoEntity();

                nuevoJustificativo.setRutEmpleado(marcasReloj.get(i).getRutEmpleadoReloj());
                nuevoJustificativo.setFecha(marcasReloj.get(i).getFecha());
                nuevoJustificativo.setJustificada(0);

                guardarJustificativo(nuevoJustificativo);

            }
            i = i + 1;
        }
    }

    public void eliminarInasistencias(){
        justificativoRepository.deleteAll();
    }
}