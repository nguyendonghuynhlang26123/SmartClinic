import * as express from 'express';
import { TreatmentService } from './treatment.service';
const router = express.Router();

const treatmentService: TreatmentService = new TreatmentService();

router.get('/:treatment_id', async (req, res) => {
  const treatment = await treatmentService.getTreatmentById(
    req.params.treatment_id
  );
  res.json(treatment);
});

router.get('/', async (req, res) => {
  try {
    const treatments = await treatmentService.getTreatments(req.query);
    res.json(treatments);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.post('/', async (req, res) => {
  try {
    const treatment = await treatmentService.createTreatment(req.body);
    res.json(treatment);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});
router.post('/register', async (req, res) => {
  try {
    const treatment = await treatmentService.registerTreatment(req.body);
    res.json(treatment);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.delete('/:treatment_id', async (req, res) => {
  try {
    const result = await treatmentService.deleteTreatment(
      req.params.treatment_id
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const TreatmentController = router;
