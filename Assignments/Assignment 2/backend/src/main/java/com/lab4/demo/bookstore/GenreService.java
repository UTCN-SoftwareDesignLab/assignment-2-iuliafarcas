package com.lab4.demo.bookstore;

import com.lab4.demo.bookstore.mapper.GenreMapper;
import com.lab4.demo.bookstore.model.Genre;
import com.lab4.demo.bookstore.model.dto.GenreDTO;
import com.lab4.demo.bookstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<GenreDTO> findAll(){
        return genreRepository.findAll().stream()
                .map(genreMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GenreDTO create(GenreDTO genreDTO){
        return  genreMapper.toDTO(genreRepository.save(genreMapper.fromDTO(genreDTO)));
    }

    public GenreDTO updtae(Long id, GenreDTO genreDTO){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Couldn't find genre"));
        genre.setGenre(genre.getGenre());
        return genreMapper.toDTO(genreRepository.save(genre));
    }

    public void delete(Long id){
        genreRepository.deleteById(id);
    }
}
