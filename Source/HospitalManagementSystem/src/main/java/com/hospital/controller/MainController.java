package com.hospital.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.hospital.entity.Appointment;
import com.hospital.entity.Prescription;
import com.hospital.service.AppointmentService;
import com.hospital.service.Invoiceservice;

@Controller
public class MainController {

	@Autowired
	private AppointmentService service;

	@Autowired
	public Invoiceservice invoiceService;

	@GetMapping({ "/", "/main" })
	public String showMain() {
		return "main";
	}

	@GetMapping("/patients")
	public String showPatient(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		String id = (String) model.asMap().get("appointmentId");
		model.addAttribute("appointmentId", id);
		return "patients";
	}

	@GetMapping("/doctors")
	public String showDoctors(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		model.addAttribute("username", username);
		return "doctors";
	}

	@GetMapping("/showPostLogin")
	public String showPostLogin() {

		return "postlogin";
	}

	@GetMapping("/add")
	public String newAppointment(Model model) {
		Appointment appointment = new Appointment();
		appointment.setConfirmed("Not yet confirmed");
		model.addAttribute("appointment", appointment);
		return "add.html";
	}

	@GetMapping("/save")
	public String saveProduct(@ModelAttribute("appointment") Appointment appointment, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttributes) {
		appointment.setConfirmed("Not yet confirmed");
		service.save(appointment);
		String appointmentId = String.valueOf(appointment.getAppointment_id());
		String message = "Appointment was successfully booked, your id is: " + appointmentId;
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		redirectAttributes.addFlashAttribute("appointmentId", appointmentId);
		return "redirect:/patients";

	}

	@GetMapping("/cancel")
	public String cancel(@ModelAttribute("appointment") Appointment appointment, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		Long id = Long.valueOf(appointment.getAppointment_id());
		service.delete(id);
		String message = "Appointment was successfully canceled!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/patients";

	}

	@RequestMapping("/confirm")
	public String confirm(@ModelAttribute("appointment") Appointment appointment, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		System.out.println(appointment);
		// Optional<com.company.varnaa.appointment> x =
		// service.get(appointment.getAppointment_id());
		String confirmation = "confirmed";
		Long id = Long.valueOf(appointment.getAppointment_id());
		service.setConfirmation(confirmation, id);
		System.out.println(id);
		String message = "Appointment was successfully confirmed!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/receptionist/receptionistAppointments";

	}

	@GetMapping("/confirmm")
	public String showConfirmm(Model model) {
		Appointment confirmation = new Appointment();
		model.addAttribute("confirmation", confirmation);
		return "confirm";
	}

	@GetMapping("/findbystart")
	public String showBydate(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String doctorName = auth.getName();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now).toString());
		List<Appointment> appointments = service.findByDate(dtf.format(now).toString(), doctorName);
		model.addAttribute("appointments", appointments);
		return "findbystart";
	}

	@GetMapping("/varsha")
	public String createPrescription(Model model) {
		Prescription ps = new Prescription();
		model.addAttribute("prescription", ps);
		return "varsha";
	}

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@GetMapping("/signupp")
	public String signupp(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Account created successfully");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/";
	}

	@GetMapping("/saveInvoice")
	public String saveInvoice(@ModelAttribute("invoice") com.hospital.entity.Invoice invoice, BindingResult result,
			ModelMap model, RedirectAttributes redirectAttributes) {
		invoiceService.save(invoice);
		String message = "Invoice was successfully created!";
		redirectAttributes.addFlashAttribute("message", message);
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/patients";
	}

}
