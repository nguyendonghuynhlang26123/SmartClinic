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

router.put("/:patient_id", async (req, res) => {
  try {
    const result = await patientService.updatePatientById(
      req.params.patient_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete("/:patient_id", async (req, res) => {
  try {
    const result = await patientService.deletePatient(
      req.params.patient_id
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const PatientController = router;
