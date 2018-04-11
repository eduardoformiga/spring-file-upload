package com.example.videoapi.demo.service;

import com.example.videoapi.demo.model.Evento;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IEventoService {

	boolean save(Evento evento, MultipartFile fotos, HttpServletRequest request);


}
