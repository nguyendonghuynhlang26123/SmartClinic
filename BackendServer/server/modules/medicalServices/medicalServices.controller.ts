import * as express from 'express';
import { MedicalServiceInterface } from './../../interfaces';
import { MedicalServicesService } from './medicalServices.service';
const router = express.Router();

const service: MedicalServicesService = new MedicalServicesService();

router.get('/', async (req, res) => {
  const servicesArray = await service.getAllServices(req.query);
  res.json(servicesArray);
});

router.get('/:service_id', async (req, res) => {
  try {
    const result = await service.getById(req.params.service_id);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.post('/', async (req, res) => {
  try {
    const result = await service.createAService(req.body);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put('/:service_id', async (req, res) => {
  try {
    const result = await service.updateServiceById(
      req.params.service_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete('/:service_id', async (req, res) => {
  try {
    const result = await service.deleteService(req.params.service_id);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const MedicalServiceController = router;
