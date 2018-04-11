package com.example.videoapi.demo.controller;

import com.example.videoapi.demo.model.Evento;
import com.example.videoapi.demo.service.IEventoService;
import com.example.videoapi.demo.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = Constantes.URL_BASE + "/evento")
public class EventoController {
	private IEventoService eventoService;

	@Autowired
	public EventoController(IEventoService eventoService) {
		this.eventoService = eventoService;
	}

	@RequestMapping(value = "/comAnexo/", consumes = "multipart/form-data", method = RequestMethod.POST)
	@ResponseBody
	@CrossOrigin
	public boolean save(@RequestPart("evento") Evento evento, @RequestPart("fotos") MultipartFile fotos,
						HttpServletRequest request)  {
		return eventoService.save(evento, fotos, request);
	}

}
