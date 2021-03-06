import * as express from 'express';
import { DoctorService } from './doctor.service';
const router = express.Router();

const doctorService: DoctorService = new DoctorService();

router.get('/', async (req, res) => {
  const doctors = await doctorService.getAllDoctor(req.query);
  res.json(doctors);
});

router.get('/:doctor_id', async (req, res) => {
  const doctor = await doctorService.getDoctorById(req.params.doctor_id);
  res.json(doctor);
});

router.post('/', async (req, res) => {
  try {
    const doctor = await doctorService.createDoctor(req.body);
    res.json(doctor);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put('/:doctor_id', async (req, res) => {
  try {
    const result = await doctorService.updateDoctorById(
      req.params.doctor_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete('/:doctor_id', async (req, res) => {
  try {
    const result = await doctorService.deleteDoctor(req.params.doctor_id);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const DoctorController = router;
