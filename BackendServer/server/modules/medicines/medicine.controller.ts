import * as express from "express";
import { MedicineService } from "./medicine.service";
const router = express.Router();

const medicineService: MedicineService = new MedicineService();

router.get("/", async (req, res) => {
  const medicines = await medicineService.getAllMedicine();
  res.json(medicines);
});

router.get("/:medicine_id", async (req, res) => {
  const medicine = await medicineService.getMedicineById(
    req.params.medicine_id
  );
  res.json(medicine);
});

router.post("/", async (req, res) => {
  try {
    const medicine = await medicineService.createMedicine(req.body);
    res.json(medicine);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

export const MedicineController = router;