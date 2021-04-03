import * as express from 'express';
import { MedicineService } from './medicine.service';
const router = express.Router();

const medicineService: MedicineService = new MedicineService();

router.get('/category/{category_id}', async (req, res) => {
  try {
    const medicines = await medicineService.getMedicinesByCategory(
      req.params.category_id,
      req.query
    );
    res.json(medicines);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: 'Bad request! ' + err.message });
  }
});

router.get('/', async (req, res) => {
  try {
    const medicines = await medicineService.getAllMedicine(req.query);
    res.json(medicines);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: 'Bad request! ' + err.message });
  }
});

router.get('/:medicine_id', async (req, res) => {
  const medicine = await medicineService.getMedicineById(
    req.params.medicine_id
  );
  res.json(medicine);
});

router.post('/', async (req, res) => {
  try {
    const medicine = await medicineService.createMedicine(req.body);
    res.json(medicine);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put('/:medicine_id', async (req, res) => {
  try {
    const result = await medicineService.updateMedicineById(
      req.params.medicine_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete('/:medicine_id', async (req, res) => {
  try {
    const result = await medicineService.deleteMedicine(req.params.medicine_id);
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const MedicineController = router;
