package com.songr.songr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class application {
    @Autowired
    private AlbumRepository albumRepository ;

    @Autowired
    private SongRepository songRepository;

    @GetMapping("/hello")
    public String helloWorld(@RequestParam(name="name", required = false, defaultValue = "hello world") String name, Model model) {
        return "helloWorld";
    }

    @GetMapping("/capitalize/hello")
    public String helloWorldCapitalize(){
        return "capitalize";
    }



    @GetMapping("/albums")
    public String getAlbum(Model model){

        List<Album> albums= albumRepository.findAll();
        model.addAttribute("albumm", new Album());
        model.addAttribute("albums",albums);
        return "albums";
    }

    @PostMapping("/albums")

    public String addAlbum (@ModelAttribute Album albums, Model model){
           albumRepository.save(albums);
        List<Album> album= albumRepository.findAll();
        model.addAttribute("albumm", new Album());
        model.addAttribute("albums", album);
        return "addalbum";

    }

    @GetMapping("/songs")
    public String getSongs(Model model){

        List<Song> songs= songRepository.findAll();
//        model.addAttribute("songs", new Album());
        model.addAttribute("songs",songs);
        return "songs";
    }

    @GetMapping("/albums/{id}")
    public String getAlbumSongs(@PathVariable Long id, Model model){
        Album album = albumRepository.findById(id).orElseThrow();
        model.addAttribute("song",new Song());
        model.addAttribute("album",album);
        model.addAttribute("songs",album.getSongs());

        return "songs";
    }

    @PostMapping("/albums/{id}")
    public String addAlbumSongs(@PathVariable Long id,@ModelAttribute Song song, Model model){
        Album album = albumRepository.findById(id).orElseThrow();
        song.setAlbum(album);
        songRepository.save(song);
        model.addAttribute("song",new Song());
        model.addAttribute("album",album);
        model.addAttribute("songs",album.getSongs());

        return "albumsong";
    }
}
