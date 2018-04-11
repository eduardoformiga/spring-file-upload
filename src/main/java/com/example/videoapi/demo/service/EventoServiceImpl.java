package com.example.videoapi.demo.service;


import com.example.videoapi.demo.model.Evento;
import com.example.videoapi.demo.model.Video;
import com.example.videoapi.demo.repository.EventoRepository;
import com.example.videoapi.demo.repository.FotoRepository;
import com.example.videoapi.demo.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Paths;

@Service
@Transactional
public class EventoServiceImpl implements IEventoService {
    private EventoRepository eventoRepository;
    private FotoRepository fotoRepository;

    @Autowired
    public EventoServiceImpl() {
      this.eventoRepository = eventoRepository;
      this.fotoRepository = fotoRepository;
    }

    @Override
    public boolean save(Evento evento, MultipartFile fotos, HttpServletRequest request) {
      //  evento = eventoRepository.save(evento);
        armazenarArquivos(evento, fotos, request, Constantes.DIRETORIO_COMPLETO);
        return true;
    }

    private String armazenarArquivos(Evento evento, MultipartFile file, HttpServletRequest request, String pasta) {
        Video video = new Video();
        String fileName;
        if (file.getOriginalFilename().contains("/")) {
            throw new RuntimeException("Folder separators not allowed");
        }

        if (!file.isEmpty()) {
            try {
                // String realPath = "file:///" + request.getSession().getServletContext().getRealPath("") + pasta;
                String realPath = "file:///" + pasta;
                URL url = new URL(realPath);
                File dir = Paths.get(url.toURI()).toFile();
                if (!dir.exists()) {
                    dir.mkdir();
                }
                String fileExtension = file.getOriginalFilename()
                        .split("\\.")[file.getOriginalFilename().split("\\.").length - 1];

                fileName = java.util.UUID.randomUUID() + "." + fileExtension;
                String filePath = dir.toString() + File.separator + fileName;

                url = new URL("file:///" + filePath);
                File arquivo = Paths.get(url.toURI()).toFile();

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(arquivo));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();


                video.setEvento(evento);
                video.setCaminho(Constantes.DIRETORIO_FOTOS_EVENTOS + "/" + Constantes.FOLDER_FOTOS_EVENTOS + "/" + fileName);
                //fotoRepository.save(video);

                //arquivo.delete();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException(
                    "You failed to upload " + file.getOriginalFilename() + " because the file was empty");
        }
        return fileName;
    }

}
