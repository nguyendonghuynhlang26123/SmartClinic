import * as express from "express";
import { AppointmentService } from "./appointment.service";
const router = express.Router();

const appointmentService: AppointmentService = new AppointmentService();

router.get("/", async (req, res) => {
  const appointments = await appointmentService.getAllAppointment();
  res.json(appointments);
});

router.get("/:appointment_id", async (req, res) => {
  const appointment = await appointmentService.getAppointmentById(
    req.params.appointment_id
  );
  res.json(appointment);
});

export const AppointmentController = router;
