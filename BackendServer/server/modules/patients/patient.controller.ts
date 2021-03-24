import * as express from 'express';
import { PatientService } from './patient.service';
const router = express.Router();

const patientService: PatientService = new PatientService();

router.get('/', async (req, res) => {
  const patients = await patientService.getAllPatient();
  res.json(patients);
});

router.get('/:patient_id', async (req, res) => {
  const patient = await patientService.getPatientById(req.params.patient_id);
  res.json(patient);
});

router.post('/', async (req, res) => {
  try {
    const patient = await patientService.createPatient(req.body);
    res.json(patient);
  } catch (err) {
    console.log(err);
    res.status(400).json({ 'message': err.message });
  }
});

export const PatientController = router;
