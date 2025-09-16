package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.Artista;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {
    @Autowired
    private IDiscoRepository discoRepo;
    @Autowired
    private IArtistaRepository artistaRepo;

    //Metodo post Disco Request
    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco dis){
            if(artistaRepo.existsById(dis.idArtista)){
            Object res = discoRepo.insert(dis);
            return new ResponseEntity<>(res,null,HttpStatus.CREATED);
        }

    Object res = discoRepo.insert(dis);
    return new ResponseEntity<>(res,null,HttpStatus.CREATED);
    }

    //---Metodo Get Discos Request
    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE ) 
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest(){
        return new ResponseEntity<>(discoRepo.findAll(),null,HttpStatus.OK);
    }
    //Metodo Get Disco Request
    @GetMapping(value = "/disco/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id){
        Optional<Disco> optDisco = discoRepo.findById(id);
        if(!optDisco.isPresent()){
            return new ResponseEntity<>(null,null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optDisco.get(),null,HttpStatus.OK);
    }
    //Metodo Get Discos por artista Request
    @GetMapping(value = "/artista/{id}/discos",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id){
        Optional<Artista> optArtista = artistaRepo.findById(id);
        if (!optArtista.isPresent()) {
        return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
        List<Disco> discos = discoRepo.findDiscosByIdArtista(id);
        return new ResponseEntity<>(discos,null, HttpStatus.OK);
    }

}
