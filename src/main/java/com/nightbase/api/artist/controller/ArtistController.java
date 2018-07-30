package com.nightbase.api.artist.controller;

import com.nightbase.api.artist.repository.ArtistRepository;
import com.nightbase.api.artist.model.Artist;
import com.nightbase.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("artist")
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/id/{id}")
    public Artist getArtistById(@PathVariable(value = "id") Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistId));
    }


    @GetMapping("name/{name}")
    public ResponseEntity<Artist> getArtistByName(@PathVariable("name") String name) {
        Artist artist;
        if(artistRepository.existsByName(name)){
            artist = artistRepository.findArtistByName(name);
        } else {
            throw new ResourceNotFoundException("Artist", "name", name);
        }
        return new ResponseEntity<Artist>(artist, HttpStatus.OK);
    }

    @GetMapping("allartists")
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @PostMapping("add")
    public Artist createArtist(@Valid @RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @PutMapping("/update/{id}")
    public Artist updateArtist(@PathVariable(value = "id") Long artistId,
                           @Valid @RequestBody Artist artistDetails) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistId));

        artistDetails.setName(artist.getName());
        artistDetails.setCountry(artist.getCountry());
        artistDetails.setFacebook(artist.getFacebook());
        artistDetails.setInstagram(artist.getInstagram());
        artistDetails.setResidentadvisor(artist.getResidentadvisor());
        artistDetails.setSoundcloud(artist.getSoundcloud());
        artistDetails.setTwitter(artist.getTwitter());

        Artist updatedArtist = artistRepository.save(artist);
        return updatedArtist;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable(value = "id") Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistId));

        artistRepository.delete(artist);

        return ResponseEntity.ok().build();
    }
}
