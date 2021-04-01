import * as express from 'express';
import { AppointmentService } from './appointment.service';
const router = express.Router();

const appointmentService: AppointmentService = new AppointmentService();

router.get('/', async (req, res) => {
  try {
    const appointments = await appointmentService.getAllAppointment(req.query);
    res.json(appointments);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: 'BAD REQUEST' });
  }
});

router.get('/:appointment_id', async (req, res) => {
  const appointment = await appointmentService.getAppointmentById(
    req.params.appointment_id
  );
  res.json(appointment);
});

router.post('/', async (req, res) => {
  try {
    const appointment = await appointmentService.createAppointment(req.body);
    res.json(appointment);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put('/:appointment_id', async (req, res) => {
  try {
    const result = await appointmentService.updateAppointmentById(
      req.params.appointment_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete('/:appointment_id', async (req, res) => {
  try {
    const result = await appointmentService.deleteAppointment(
      req.params.appointment_id
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const AppointmentController = router;
